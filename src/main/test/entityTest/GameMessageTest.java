package entityTest;

import com.google.gson.Gson;
import controller.GameInfoController;
import entity.Card;
import entity.Room;
import messages.GameMessage;
import messages.RoomListMessage;
import messages.RoomMessage;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class GameMessageTest {


    @Test
    public void testConvertToJson(){
        Gson gson=new Gson();

        GameMessage gameMessage=new GameMessage();
        gameMessage.setGameMessageType(GameMessage.GameMessageType.ready);
        gameMessage.setDoubleRate(1.021);
        gameMessage.setRoomId("asdfgdd");

        System.out.println(gson.toJson(gameMessage));
    }

    @Test
    public void RoomList(){
        Gson gson=new Gson();

//        GameMessage gameMessage=new GameMessage();
//        RoomMessage message=new RoomMessage("enterRoom","success","" ,"123","1");
//        System.out.println(gson.toJson(message));
//        RoomListMessage message=new RoomListMessage();
//        message.setType(RoomListMessage.RoomMessageType.roomlist);
//        message.setList(new ArrayList<>());
//        System.out.println(gson.toJson(message));

        System.out.println(gson.fromJson("{\"type\":\"gameOver\",\"win\":true,\"score\":960}",GameMessage.class).getWin());
    }

    @Test
    public void ConvertToGamessage(){
        Gson gson=new Gson();
        //GameMessage gameMessage=gson.fromJson("{\"type\":\"ready\",\"roomId\":\"asdfgdd\",\"newPwd\":\"1323546\",\"doubleRate\":1.021}",GameMessage.class);
//        GameMessage message=new GameMessage();
//        message.setGameMessageType(GameMessage.GameMessageType.ready);
//        message.setUserID("123");
        Card[] cards=new Card[3];
        for (int i = 0; i <3 ; i++) {
            cards[i]=new Card(i);
        }
        GameMessage dealMessage=new GameMessage();
        //dealMessage.setGameMessageType(GameMessage.GameMessageType.dealCards);
        //dealMessage.setCards(cards);
        dealMessage.setGameMessageType(GameMessage.GameMessageType.play);
        dealMessage.setCards(cards);
        dealMessage.setUserID("123");
//        dealMessage.setLord(false);
        System.out.println(gson.toJson(dealMessage));

    }
    @Test
    public void AllMessageFromJson() {
        Gson gson=new Gson();
        assertEquals("{\"type\":\"ready\"}",gson.toJson(gson.fromJson("{\"type\":\"ready\"}",GameMessage.class)));
        assertEquals("{\"type\":\"unready\"}",gson.toJson(gson.fromJson("{\"type\":\"unready\"}",GameMessage.class)));
        assertEquals("{\"type\":\"timeout\"}",gson.toJson(gson.fromJson("{\"type\":\"timeout\"}",GameMessage.class)));
        assertEquals("{\"type\":\"getLord\"}",gson.toJson(gson.fromJson("{\"type\":\"getLord\"}",GameMessage.class)));
        assertEquals("{\"type\":\"competeLord\"}",gson.toJson(gson.fromJson("{\"type\":\"competeLord\"}",GameMessage.class)));
        assertEquals("{\"type\":\"passLord\"}",gson.toJson(gson.fromJson("{\"type\":\"passLord\"}",GameMessage.class)));
        assertEquals("{\"type\":\"doubleScore\",\"doubleRate\":2.0}",gson.toJson(gson.fromJson("{\"type\":\"doubleScore\",\"doubleRate\":2.0}",GameMessage.class)));
        assertEquals("{\"type\":\"play\",\"cards\":[{\"value\":0},{\"value\":1},{\"value\":2},{\"value\":3},{\"value\":4},{\"value\":5},{\"value\":6},{\"value\":7},{\"value\":8},{\"value\":9},{\"value\":10},{\"value\":11},{\"value\":12},{\"value\":13},{\"value\":14},{\"value\":15},{\"value\":16}]}",
                gson.toJson(gson.fromJson("{\"type\":\"play\",\"cards\":[{\"value\":0},{\"value\":1},{\"value\":2},{\"value\":3},{\"value\":4},{\"value\":5},{\"value\":6},{\"value\":7},{\"value\":8},{\"value\":9},{\"value\":10},{\"value\":11},{\"value\":12},{\"value\":13},{\"value\":14},{\"value\":15},{\"value\":16}]}",
                        GameMessage.class)));
    }

    @Test
    public void RoomMessageFromJson(){
        Gson gson=new Gson();
        assertEquals("{\"type\":\"createRoom\",\"roomId\":\"123\",\"roomDescription\":\"roomDescription\"}",
                gson.toJson(gson.fromJson("{\"type\":\"createRoom\",\"roomId\":\"123\",\"roomDescription\":\"roomDescription\"}",GameMessage.class)));
        assertEquals("{\"type\":\"enterRoom\",\"roomId\":\"111\"}",
                gson.toJson(gson.fromJson("{\"type\":\"enterRoom\",\"roomId\":\"111\"}",GameMessage.class)));
        assertEquals("{\"type\":\"leaveRoom\"}",
                gson.toJson(gson.fromJson("{\"type\":\"leaveRoom\"}",GameMessage.class)));
        assertEquals("{\"type\":\"roomlist\",\"list\":[{\"roomId\":\"123\",\"description\":\"12334\",\"userNum\":0},{\"roomId\":\"543\",\"description\":\"agfdsgshfd\",\"userNum\":0},{\"roomId\":\"fhg\",\"description\":\"dfhgdg\",\"userNum\":0}]}",
                gson.toJson(gson.fromJson("{\"type\":\"roomlist\",\"list\":[{\"roomId\":\"123\",\"description\":\"12334\",\"userNum\":0},{\"roomId\":\"543\",\"description\":\"agfdsgshfd\",\"userNum\":0},{\"roomId\":\"fhg\",\"description\":\"dfhgdg\",\"userNum\":0}]}",RoomListMessage.class)));
    }
    @Test
    public void RoomMessageToJson(){
        Gson gson=new Gson();
        GameMessage enterRoom=new GameMessage(),
                createRoom=new GameMessage(),
                leaveRoom=new GameMessage();
        enterRoom.setGameMessageType(GameMessage.GameMessageType.enterRoom);
        enterRoom.setRoomId("111");

        createRoom.setGameMessageType(GameMessage.GameMessageType.createRoom);
        createRoom.setRoomId("123");
        createRoom.setRoomDescription("roomDescription");

        leaveRoom.setGameMessageType(GameMessage.GameMessageType.leaveRoom);

        System.out.println(gson.toJson(createRoom));
        System.out.println(gson.toJson(enterRoom));
        System.out.println(gson.toJson(leaveRoom));


        List<Room>rooms=new ArrayList<>();
        Room room1=new Room();
        room1.setID("123");
        room1.setDescript("12334");
        Room room2=new Room();
        room2.setID("543");
        room2.setDescript("agfdsgshfd");
        Room room3=new Room();
        room3.setID("fhg");
        room3.setDescript("dfhgdg");

        rooms.add(room1);
        rooms.add(room2);
        rooms.add(room3);
        RoomListMessage roomListMessage =new RoomListMessage(rooms);
        roomListMessage.setType(RoomListMessage.RoomMessageType.roomlist);
        System.out.println(gson.toJson(roomListMessage));


    }
    @Test
    public void AllMessageToJson(){
        Gson gson=new Gson();
        GameMessage ready=new GameMessage(GameMessage.GameMessageType.ready),
                unready=new GameMessage(GameMessage.GameMessageType.unready),
                timeout=new GameMessage(GameMessage.GameMessageType.timeout),
                getLord=new GameMessage(GameMessage.GameMessageType.getLord),
                completeLord=new GameMessage(GameMessage.GameMessageType.competeLord),
                passLord=new GameMessage(GameMessage.GameMessageType.passLord);

        GameMessage message=new GameMessage(GameMessage.GameMessageType.play);
        Card[] cards=new Card[17];
        for (int i = 0; i <17 ; i++) {
            cards[i]=new Card(i);
        }
        message.setCards(cards);

        GameMessage douScoreMessage=new GameMessage(GameMessage.GameMessageType.doubleScore);
        douScoreMessage.setDoubleRate(2.0);

        System.out.println(gson.toJson(ready));
        System.out.println(gson.toJson(unready));
        System.out.println(gson.toJson(timeout));
        System.out.println(gson.toJson(getLord));
        System.out.println(gson.toJson(completeLord));
        System.out.println(gson.toJson(passLord));
        System.out.println(gson.toJson(message));
        System.out.println(gson.toJson(douScoreMessage));

    }
    @Test
    public void test(){
        Gson gson=new Gson();
        GameInfoController.GameRecords gameRecords=new GameInfoController.GameRecords();
        gameRecords.add("23","123","24","234","234",123);
        gameRecords.add("23","123","24","234","234",123);
        Map<String,String> mp=new HashMap<>();
        mp.put("gameRecord",gson.toJson(gameRecords));
        System.out.println(gson.toJson(mp));
    }
}
