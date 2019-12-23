package entity;

import dao.GameInfoDao;
import domain.RoomDomain;
import messages.GameChan;
import services.Imp.CardAudit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class Room {


    private String ID;
    private List<User> users=new ArrayList<>();
    public final Object userLock=new Object();
    private GameChan gameChan;
    private CardAudit cardAudit;
    private String descript;

    private String startTime;
    private String curGameId;
    private GameInfoDao gameInfoDao;

    public Room(){}
    public Room(String id){this.ID =id;}
    public String getID() {
        return ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }
    public int getPlayersNum(){
        return users.size();
    }
    public User getRandomUser(){
        return users.get((int)(Math.random()*10)%3);
    }
    public void addPlayer(User user){
        users.add(user);
        user.setSeat(users.size());
        cardAudit.addUser(user.getID());
    }

    public void gameOver(String userID,boolean win,int score){
//        User user=getUser(userID);
//        UserInfo userInfo=user.getUserInfo();
//        if (win) userInfo.setUSER_SCORE(userInfo.getUSER_SCORE()+score);
//        else userInfo.setUSER_SCORE(userInfo.getUSER_SCORE()-score);
        cardAudit.gameOver();
        doRecordGameInfo();
        doUserRelatedGame(userID,win,score);

    }
    private void doUserRelatedGame(String userID,boolean win,int score){
        User user=getUser(userID);
        UserRelatedGame userRelatedGame=new UserRelatedGame();
        userRelatedGame.setGAME_ID(getCurGameId());
        userRelatedGame.setUSER_ID(userID);
        userRelatedGame.setUSER_SCORE(win?score:-score);
        userRelatedGame.setUSER_SIGN(user.isLord()?0:1);
        user.getUserRelatedGameDao().insert(userRelatedGame);
    }

    private synchronized void doRecordGameInfo(){
        if(startTime==null) return;
        GameInfo gameInfo=new GameInfo();
        gameInfo.setGAME_ID(getCurGameId());
        gameInfo.setGAME_START_TIME(getStartTime());
        gameInfo.setGAME_LAST_TIME(new Date().toString());
        gameInfo.setFIRST_USER_ID(users.get(0).getUserInfo().getUSER_NAME());
        gameInfo.setSECOND_USER_ID(users.get(1).getUserInfo().getUSER_NAME());
        gameInfo.setTHIRD_USER_ID(users.get(2).getUserInfo().getUSER_NAME());
        gameInfoDao.insert(gameInfo);
        startTime=null;
    }

    public GameChan getGameChan() {
        return gameChan;
    }
    public void removePlayer(String userID){
        users.removeIf((u)->u.getID().equals(userID));
        cardAudit.removeUser(userID);
    }

    public CardAudit getCardAudit() {
        return cardAudit;
    }

    public void setCardAudit(CardAudit cardAudit) {
        this.cardAudit = cardAudit;
    }

    public List<User> getUsers() {
        return users;
    }

    public GameInfoDao getGameInfoDao() {
        return gameInfoDao;
    }

    public void setGameInfoDao(GameInfoDao gameInfoDao) {
        this.gameInfoDao = gameInfoDao;
    }

    public Object getUserLock() {
        return userLock;
    }

    public void setGameChan(GameChan gameChan) {
        this.gameChan = gameChan;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public int getReadyUserNum(){
        int res=0;
        for(User user:users) {
            if (user.getStatus() == User.UserStatus.ready)
                res++;
        }
        return res;

    }
    public User getUser(String userId){
        for (User user : users) {
            if (userId.equals(user.getID()))
                return user;
        }
        return null;
    }
    public RoomDomain generator(){
        RoomDomain roomDomain=new RoomDomain();

        roomDomain.setRoomId(getID());

        users.forEach((user -> {
            roomDomain.getUsers().put(user.getID(),user.generator());
        }));
        return roomDomain;
    }
    public List<User>getOtherUsers(String userID){
        List<User> others=new ArrayList<>();
        users.forEach((user -> {
            if (!user.getID().equals(userID)){
                others.add(user);
            }
        }));
        return others;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getCurGameId() {
        return curGameId;
    }

    public void setCurGameId(String curGameId) {
        this.curGameId = curGameId;
    }
}
