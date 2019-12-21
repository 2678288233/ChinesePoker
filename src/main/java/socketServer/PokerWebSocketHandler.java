package socketServer;



import com.google.gson.Gson;
import entity.Room;
import entity.User;
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
            case play:user.getGameService().play(gameMessage.getCards());
            case timeout:user.getGameService().timeout();break;
            case unready:user.getGameService().unready();break;
            case getLord:user.getGameService().getLord();break;
            case competeLord:user.getGameService().competeLord();break;
            case doubleScore:user.getGameService().doubleScore();break;
            case passLord:user.getGameService().pass();break;
            case noSnatchLord:user.getGameService().noNatchLord();break;



            case getBaseCards:
                //user.setLord(gameMessage.getLord());

                user.getGameService().getBaseCards(gameMessage.getLordId());break;


            case emptyResponse:break;

            case reconnection:user.getGameService().reconnection();break;
            /* roomService*/
            case enterRoom:user.getRoomService().enterRoom(gameMessage.getRoomId());break;
            case createRoom:
                Room room=new Room(String.valueOf(RoomDispatch.getRoomID()));
                //room.setDescript(gameMessage.getRoomDescription());
                room.setGameChan(new GameChan());
                room.setCardAudit(cardAuditService.getCardAudit());
                user.getRoomService().createRoom(room);
            break;
            case leaveRoom:user.getRoomService().leaveRoom();break;


            default:throw new RuntimeException("UnKnown type");
        }
    }



}
