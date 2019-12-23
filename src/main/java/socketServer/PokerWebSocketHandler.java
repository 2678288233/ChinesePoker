package socketServer;



import com.google.gson.Gson;
import dao.GameInfoDao;
import dao.UserBonusDao;
import dao.UserInfoDao;
import dao.UserRelatedGameDao;
import entity.Room;
import entity.User;
import entity.UserBonus;
import entity.UserInfo;
import log.Logger;
import messages.GameChan;
import messages.GameMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import services.CardAuditService;
import services.Imp.RoomDispatch;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;


/**
 *
 * 说明：WebSocket处理器
 */
@Component("pokerWebSocketHandler")
public class PokerWebSocketHandler implements WebSocketHandler {

    public Gson getGson() {
        return gson;
    }
    public void setGson(Gson gson) {
        this.gson = gson;
    }

    private UserInfoDao userInfoDao;
    private UserBonusDao userBonusDao;

    public UserInfoDao getUserInfoDao() {
        return userInfoDao;
    }
    @Autowired
    public void setUserInfoDao(UserInfoDao userInfoDao) {
        this.userInfoDao = userInfoDao;
    }

    public UserBonusDao getUserBonusDao() {
        return userBonusDao;
    }
    @Autowired
    public void setUserBonusDao(UserBonusDao userBonusDao) {
        this.userBonusDao = userBonusDao;
    }

    private GameInfoDao gameInfoDao;
    private UserRelatedGameDao userRelatedGameDao;
    @Autowired
    public void setGameInfoDao(GameInfoDao gameInfoDao) {
        this.gameInfoDao = gameInfoDao;
    }

    @Autowired
    public void setUserRelatedGameDao(UserRelatedGameDao userRelatedGameDao) {
        this.userRelatedGameDao = userRelatedGameDao;
    }
    //userID->user
    @Resource(name = "usersCache")
    HashMap<String,User> usersCache;

    //userName->userInfo
    @Resource(name = "usersInfoCache")
    HashMap<String, UserInfo> usersInfoCache;


    @Resource(name = "gson")
    Gson gson;


    private CardAuditService cardAuditService;

    public CardAuditService getCardAuditService() {
        return cardAuditService;
    }
    @Autowired
    public void setCardAuditService(CardAuditService cardAuditService) {
        this.cardAuditService = cardAuditService;
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {


        User user=(User)webSocketSession.getAttributes().get("user");

        user.setUserRelatedGameDao(userRelatedGameDao);

        user.setWebSocketSession(webSocketSession);
        if (user.getStatus()== User.UserStatus.login){
            user.getHomeService().enterHome();
        }
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        User user=(User)webSocketSession.getAttributes().get("user");

        assert user!=null;

        GameMessage gameMessage=GameMessage.parseGameMessage((String)webSocketMessage.getPayload());
        processMessage(user,gameMessage);



        //System.out.println("receive "+webSocketMessage);
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        Logger.log("Websocket异常断开:" + webSocketSession.getId() + "已经关闭");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        Logger.log("Websocket正常断开:" + webSocketSession.getId() + "已经关闭");

        User user=(User)webSocketSession.getAttributes().get("user");
        user.logout();

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }


    private void processMessage(User user,GameMessage gameMessage){
        Logger.log("receive message from client"+gson.toJson(gameMessage));

        switch (gameMessage.getGameMessageType()){


            /* gameService*/
            case ready:user.getGameService().ready();break;
            case play:user.getGameService().play(gameMessage.getCards());break;
            case timeout:user.getGameService().timeout();break;
            case unready:user.getGameService().unready();break;
            case getLord:user.getGameService().getLord();break;
            case competeLord:user.getGameService().competeLord();break;
            case doubleScore:user.getGameService().doubleScore();break;
            case passLord:user.getGameService().pass();break;
            case noSnatchLord:user.getGameService().noNatchLord();break;
            case reDealCards:user.getGameService().reDealCards();break;
            case gameOver:user.getGameService().gameOver(gameMessage.getWin(),gameMessage.getScore());
                    handleGameOver(user,gameMessage.getWin(),gameMessage.getScore());
                    break;

            case getBaseCards:
                //user.setLord(gameMessage.getLord());

                user.getGameService().getBaseCards(gameMessage.getLordId());break;


            case emptyResponse:break;

            case reconnection:user.getGameService().reconnection();break;
            /* roomService*/
            case enterRoom:user.getRoomService().enterRoom(gameMessage.getRoomId());break;
            case getRoomInfo:user.getRoomService().getRoomInfo(gameMessage.getRoomId());break;
            case createRoom:
                Room room=new Room(String.valueOf(RoomDispatch.getRoomID()));
                room.setGameInfoDao(gameInfoDao);
                //room.setDescript(gameMessage.getRoomDescription());
                room.setGameChan(new GameChan());
                room.setCardAudit(cardAuditService.getCardAudit());
                user.getRoomService().createRoom(room);
            break;
            case leaveRoom:user.getRoomService().leaveRoom();break;


            default:throw new RuntimeException("UnKnown type");
        }
    }

    private void handleGameOver(User user,boolean win,int score){
        Logger.log(user.getUserInfo().getUSER_NAME()+" "+win+" "+score);
        if (win) {
            userInfoDao.update(user.getID(),true,score);
            user.getUserInfo().setUSER_SCORE(user.getUserInfo().getUSER_SCORE()+score);
            user.getUserInfo().setUSER_WINNUM(user.getUserInfo().getUSER_WINNUM()+1);
        }
        else {
            userInfoDao.update(user.getID(),false,-score);
            user.getUserInfo().setUSER_SCORE(user.getUserInfo().getUSER_SCORE()-score);
            user.getUserInfo().setUSER_LOSENUM(user.getUserInfo().getUSER_LOSENUM()+1);
        }

        UserBonus userBonus=new UserBonus();
        userBonus.setUSER_ID(user.getID());
        userBonus.setUSER_LAST_GAME_TIME(new Date().toString());
        if (user.isLord()){
            if (win) userBonus.setUSER_LORD_WINNUM(1);
            else userBonus.setUSER_LORD_LOSENUM(1);
        }else {
            if (win) userBonus.setUSER_PEASANT_WINNUM(1);
            else userBonus.setUSER_PEASANT_LOSENUM(1);
        }
        userBonusDao.updateDelta(userBonus);

    }



}
