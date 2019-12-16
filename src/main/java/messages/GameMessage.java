package messages;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import entity.Card;

import java.util.Map;

public class GameMessage {



    @SerializedName("type")
    private GameMessageType gameMessageType;

    @SerializedName("roomId")
    private String roomId;

    @SerializedName("userId")
    private String userID;

    @SerializedName("roomDescription")
    private String roomDescription;
//    @SerializedName("oldPwd")
//    private String oldPwd;
//
//    @SerializedName("newPwd")
//    private String newPwd;

    @SerializedName("cards")
    private Card[] cards;

    @SerializedName("doubleRate")
    private Double doubleRate;

    public GameMessage(){}
    public GameMessage(GameMessageType type){gameMessageType=type;}

    transient static Gson gson=new Gson();

    public static GameMessage parseGameMessage(String message){
        GameMessage gameMessage=gson.fromJson(message,GameMessage.class);
        return gameMessage;
    }

    public enum GameMessageType {
        ready, unready,timeout,play,
        enterRoom,createRoom,leaveRoom,

        getRoomInfo,
        //dispatchCards,
        getLord,passLord,competeLord,
        //bonusCards,
        //gameover,
        doubleScore,
        reconnection;
    }

    @Override
    public String toString() {
        return "roomID:"+roomId+" ; type:"+gameMessageType+" ;doubleRate:"+doubleRate;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public GameMessageType getGameMessageType() {
        return gameMessageType;
    }

    public void setGameMessageType(GameMessageType gameMessageType) {
        this.gameMessageType = gameMessageType;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Card[] getCards() {
        return cards;
    }

    public void setCards(Card[] cards) {
        this.cards = cards;
    }

    public Double getDoubleRate() {
        return doubleRate;
    }

    public void setDoubleRate(Double doubleRate) {
        this.doubleRate = doubleRate;
    }

    public static Gson getGson() {
        return gson;
    }

    public static void setGson(Gson gson) {
        GameMessage.gson = gson;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }
}
