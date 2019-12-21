package messages;

import com.google.gson.Gson;
import domain.RoomDomain;
import domain.RoomSnapShootDomain;
import entity.Room;
import entity.User;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;

public class MessageSender {
    private static Gson gson=new Gson();

    public static void sendMsg(List<Room> rooms, WebSocketSession webSocketSession) throws IOException {
        assert webSocketSession != null;
        assert rooms!=null;

        RoomListMessage roomListMessage =getRoomList(rooms);
        TextMessage message=new TextMessage(gson.toJson(roomListMessage));
        if(webSocketSession.isOpen())
            webSocketSession.sendMessage(message);
    }

    public static RoomListMessage getRoomList(List<Room>rooms){
        return new RoomListMessage(rooms);
    }

    public static void sendMsgToRoom(Room room,GameMessage gameMessage){
        List<User> users=room.getUsers();

        users.forEach((user -> {
            sendMsg(user.getWebSocketSession(),gameMessage);
        }));
    }

    private static void sendMsg(WebSocketSession webSocketSession, GameMessage gameMessage)  {
        if(webSocketSession.isOpen())
            try {
                webSocketSession.sendMessage(new TextMessage(gson.toJson(gameMessage)));
            }catch (Exception e){
                e.printStackTrace();
            }

    }
    public static void sendMsg(User user, GameMessage gameMessage)  {
        sendMsg(user.getWebSocketSession(),gameMessage);
    }
    public static void sendMsg(User user, TextMessage textMessage){
        sendMsg(user.getWebSocketSession(),textMessage);
    }
    private static void sendMsg(WebSocketSession webSocketSession, TextMessage textMessage)  {
        if(webSocketSession.isOpen())
            try {
                webSocketSession.sendMessage(textMessage);
            }catch (Exception e){
                e.printStackTrace();
            }

    }
    public static<T> void sendMsg(WebSocketSession webSocketSession, T object)  {
        if(webSocketSession.isOpen())
            try {
                webSocketSession.sendMessage(new TextMessage(gson.toJson(object)));
            }catch (Exception e){
                e.printStackTrace();
            }

    }
    public static void sendRoomDomain(User user, RoomDomain roomDomain){
        sendMsg(user.getWebSocketSession(),new TextMessage(gson.toJson(roomDomain)));
    }
    public static void sendRoomSnapShootDomain(User user, RoomSnapShootDomain roomSnapShootDomain){
        sendMsg(user.getWebSocketSession(),new TextMessage(gson.toJson( roomSnapShootDomain)));
    }


}
