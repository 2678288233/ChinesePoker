package entity;

import messages.GameChan;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import services.GameService;
import services.HomeService;
import services.Imp.GameServiceImp;
import services.Imp.HomeServiceImp;
import services.Imp.RoomServiceImp;
import services.RoomService;



public class User {


    public String getID() {
        return ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }

    public WebSocketSession getWebSocketSession() {
        return webSocketSession;
    }
    public void setWebSocketSession(WebSocketSession webSocketSession) {
        this.webSocketSession = webSocketSession;
    }

    public void setGameChan(GameChan gameChan) {
        this.gameChan = gameChan;
    }
    public GameChan getGameChan() {
        return gameChan;
    }


    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public GameService getGameService() {
        return gameService;
    }

    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    public HomeService getHomeService() {
        return homeService;
    }

    public void setHomeService(HomeService homeService) {
        this.homeService = homeService;
    }

    public RoomService getRoomService() {
        return roomService;
    }

    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }

    private String ID;
    private WebSocketSession webSocketSession;
    private GameChan gameChan;

    private String roomID;
    private int seat;
    private UserStatus status=UserStatus.unready;

    private GameService gameService;
    private HomeService homeService;
    private RoomService roomService;
    public User(){
        gameService=new GameServiceImp(this);
        homeService=new HomeServiceImp(this);
        roomService=new RoomServiceImp(this);
    }

    private static int suffix=0;
    public static String generaterID(){
        return String.valueOf(System.currentTimeMillis())+(suffix++);
    }

    public enum UserStatus{
        unready,ready,play
    }
}
