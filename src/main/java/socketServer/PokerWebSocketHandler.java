package socketServer;



import com.google.gson.Gson;
import entity.Room;
import entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import services.Imp.RoomServiceImp;
import services.RoomService;

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


/*
    public RoomService getRoomService() {
        return roomService;
    }
    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }

    @Resource(name = "roomServiceImp",type = RoomServiceImp.class)
    RoomService roomService;
*/
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {


        User user=(User) webSocketSession.getAttributes().get("user");
        user.setWebSocketSession(webSocketSession);
        /*Message msg = new Message();
        msg.setKey("login");
        msg.setValue(new Date().toString());
        //将消息转换为json
        TextMessage message = new TextMessage(gson.toJson(msg));
        webSocketSession.sendMessage(message);*/
        System.out.println("afterConnectionEstablished "+"registerRoomListService");

    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        User user=(User)webSocketSession.getAttributes().get("user");
        assert user!=null;

        if("1".equals((String)webSocketMessage.getPayload())){
            Room room=new Room();
            room.setID("120");
            user.homeService.enterHome();
        }

        System.out.println("receive "+webSocketMessage);
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        System.out.println("Websocket异常断开:" + webSocketSession.getId() + "已经关闭");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        System.out.println("Websocket正常断开:" + webSocketSession.getId() + "已经关闭");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }


}
