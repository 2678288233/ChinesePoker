package entity;

import domain.RoomDomain;
import messages.GameChan;
import services.Imp.CardAudit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Room {


    private String ID;
    private List<User> users=new ArrayList<>();
    public final Object userLock=new Object();
    private GameChan gameChan;
    private CardAudit cardAudit;
    private String descript;


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
}
