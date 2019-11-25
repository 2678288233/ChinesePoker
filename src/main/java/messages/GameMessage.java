package messages;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import entity.Card;

import java.util.Map;

public class GameMessage {

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

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
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

    @SerializedName("type")
    private GameMessageType gameMessageType;

    @SerializedName("roomId")
    private String roomId;

    @SerializedName("oldPwd")
    private String oldPwd;

    @SerializedName("newPwd")
    private String newPwd;

    @SerializedName("cards")
    private Card[] cards;

    @SerializedName("doubleRate")
    private Double doubleRate;

    public GameMessage(){}
    public GameMessage(GameMessageType type){}

    static Gson gson=new Gson();

    public static GameMessage parseGameMessage(String message){
        GameMessage gameMessage=gson.fromJson(message,GameMessage.class);
        return gameMessage;
    }

    public enum GameMessageType {
        ready, unready,timeout,play,
        enterRoom,createRoom,
        getUserBasicInfo,getUserDetailInfo,changePwd,
        getRoomInfo,
        //dispatchCards,
        getLord,passLord,competeLord,
        //bonusCards,
        //gameover,
        doubleScore;
    }

    @Override
    public String toString() {
        return "roomID:"+roomId+" ; type:"+gameMessageType+" newPwd:"+newPwd+" ;doubleRate:"+doubleRate;
    }
}
