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

    String ID;
    WebSocketSession webSocketSession;
    GameChan gameChan;
    public GameService gameService;
    public HomeService homeService;
    public RoomService roomService;
    public User(){
        gameService=new GameServiceImp(this);
        homeService=new HomeServiceImp(this);
        roomService=new RoomServiceImp(this);
    }
}
