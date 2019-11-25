package entityTest;

import com.google.gson.Gson;
import messages.GameMessage;
import org.junit.Test;


public class GameMessageTest {


    @Test
    public void testConvertToJson(){
        Gson gson=new Gson();

        GameMessage gameMessage=new GameMessage();
        gameMessage.setGameMessageType(GameMessage.GameMessageType.ready);
        gameMessage.setDoubleRate(1.021);
        gameMessage.setNewPwd("1323546");
        gameMessage.setRoomId("asdfgdd");

        System.out.println(gson.toJson(gameMessage));
    }

    @Test
    public void ConvertToGamessage(){
        Gson gson=new Gson();
        GameMessage gameMessage=gson.fromJson("{\"type\":\"ready\",\"roomId\":\"asdfgdd\",\"newPwd\":\"1323546\",\"doubleRate\":1.021}",GameMessage.class);
        System.out.println(gameMessage);

    }
}
