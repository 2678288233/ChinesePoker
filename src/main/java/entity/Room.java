package entity;

import messages.GameChan;

import java.util.ArrayList;
import java.util.List;

public class Room {

    public String getID() {
        return ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }
    public int getPlayersNum(){
        return users.size();
    }
    public void addPlayer(User user){
        users.add(user);
    }
    public GameChan getGameChan() {
        return gameChan;
    }
    public void remoevPlayer(String userID){
        users.removeIf((u)->u.getID().equals(userID));
    }
    String ID;
    List<User> users=new ArrayList<>();
    public final Object userLock=new Object();
    GameChan gameChan;
}
