package messages;

import com.google.gson.Gson;
import entity.Room;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;

public class MessageSender {
    static Gson gson=new Gson();

    public static void sendMsg(List<Room> rooms, WebSocketSession webSocketSession) throws IOException {
        assert webSocketSession != null;
        assert rooms!=null;

        TextMessage message=new TextMessage(gson.toJson(rooms));
        if(webSocketSession.isOpen())
            webSocketSession.sendMessage(message);
    }
}
