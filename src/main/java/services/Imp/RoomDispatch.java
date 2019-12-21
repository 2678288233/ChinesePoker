package services.Imp;

import com.google.gson.Gson;
import domain.RoomSnapShootDomain;
import entity.Card;
import entity.Room;
import entity.User;
import exceptions.*;
import log.Logger;
import messages.GameChan;
import messages.GameMessage;
import messages.MessageSender;
import messages.RoomMessage;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class RoomDispatch {

    //所有游戏中的用户
    private volatile static Map<String,User> userMap=new HashMap<>();
    //大厅里的用户
    private volatile static Map<String,User> homeUserMap=new HashMap<>();

    private volatile static Map<String,Room> roomMap=new HashMap<>();
    private volatile static Map<String,String> userRoomMap=new HashMap<>();

    //优先级  roommpLock > room.userLock > homeUsermpLock
    //
    private static final Object homeUsermpLock=new Object();
    private static final Object usermpLock=new Object();
    private static final Object roommpLock=new Object();
    //private static final Object userroommpLock=new Object();

    static void enterHome(User user){
        Logger.log(user.getID()+" enterHome "+"in RoomDispatch.enterHome");
        synchronized (usermpLock){
            if(!userMap.containsKey(user.getID())){
                userMap.put(user.getID(),user);
            }
        }
        synchronized (homeUsermpLock){
            if(homeUserMap.containsKey(user.getID())){
                throw new ReentryHomeException("Reentry Home Error");
            }
            homeUserMap.put(user.getID(),user);

            if(homeUserMap.size()==1){
                new Thread(new HomeWorker(homeUserMap,roomMap)).start();
            }
        }
        user.setStatus(User.UserStatus.home);
    }
    static void leaveHome(User user) throws NotInHomeException {
        Logger.log(user.getID()+" leaveHome "+"in RoomDispatch.leaveHome");
        synchronized (homeUsermpLock){
            if(!homeUserMap.containsKey(user.getID())){
                throw new NotInHomeException("Not In Home ,cannot leave home!");
            }
            homeUserMap.remove(user.getID());
        }

    }

    static void createRoom(User user, Room room) throws NotInHomeException {
        Logger.log(user.getID()+" createRoom "+room.getID()+" in RoomDispatch.createRoom");
        synchronized (roommpLock){
            if(roomMap.containsKey(room.getID())){
                //throw new RepetitiveRoomIDException("Repetitive Room ID");
                roomResponse(user,"createRoom","error","Repetitive Room ID",room.getID());
                return;
            }
            roomMap.put(room.getID(),room);
            synchronized (room.userLock) {
                userRoomMap.put(user.getID(), room.getID());
                leaveHome(user);
                user.setGameChan(room.getGameChan());
                user.setRoomID(room.getID());
                room.addPlayer(user);

                Logger.log("create thread RoomID "+room.getID());
                Thread thread=new Thread(new RoomWorker(room));
                thread.setName(room.getID());
                thread.start();
            }
            roomResponse(user,"createRoom","success","",room.getID(),String.valueOf(user.getSeat()));
        }
    }

    static void enterRoom(User user, String roomID) throws NotInHomeException {
        Logger.log(user.getID()+" enterRoom "+roomID+" in RoomDispatch.enterRoom");
        synchronized (roommpLock){
            if(!roomMap.containsKey(roomID)){
                //throw new NonexistedRoomException("NonExisted Room!");
                roomResponse(user,"enterRoom","error","NonExisted Room!",roomID);
                return;
            }
            if(roomMap.get(roomID).getPlayersNum()==3){
                roomResponse(user,"enterRoom","error","Full",roomID);
                return;
            }
            Room room=roomMap.get(roomID);
            synchronized (room.userLock) {
                if(room.getPlayersNum()>3){
                    //throw new FullRoomException("The Room is FULL!");
                    roomResponse(user,"enterRoom","error","The Room is FULL!",roomID);
                    return;
                }
                userRoomMap.put(user.getID(), room.getID());
                leaveHome(user);
                user.setGameChan(room.getGameChan());
                user.setRoomID(room.getID());
                room.addPlayer(user);
            }
            user.setStatus(User.UserStatus.unready);

            roomResponse(user,"enterRoom","success","",roomID,String.valueOf(user.getSeat()));
            enterRoomResponseToOther(room,user);
        }
    }
    static void leaveRoom(User user){
        Logger.log(user.getID()+" leaveRoom  in RoomDispatch.leaveRoom");
        String roomID=userRoomMap.get(user.getID());
        Room room;
        synchronized (roommpLock) {
            if (!roomMap.containsKey(roomID)) {
                //throw new NonexistedRoomException("Nonexisted Room!");
                roomResponse(user,"leaveRoom","error","NonExisted Room",roomID);
                return;
            }
            room= roomMap.get(roomID);
            synchronized (room.userLock) {
                room.removePlayer(user.getID());
                userRoomMap.remove(user.getID());
            }
        }
        user.setRoomID(null);
        user.setGameChan(null);
        enterHome(user);
        roomResponse(user,"leaveRoom","success","",roomID);
        leaveRoomResponseToOther(room,user);
    }

    static void getRoomInfo(User user,String roomID){
        Gson gson=new Gson();
        Room room=roomMap.get(roomID);
        List<User>roomUsers=room.getUsers();
        RoomInfo info=new RoomInfo();
        roomUsers.forEach((u -> info.add(u.getID(),String.valueOf(u.getSeat()))));
        MessageSender.sendMsg(user,new TextMessage(gson.toJson(info)));
    }

    static class RoomInfo{
        String type="getRoomInfo";
        List<RoomUserInfo> userInfos=new ArrayList<>();

        void add(String userID,String seat){
            userInfos.add(new RoomUserInfo(userID,seat));
        }
    }
    static class RoomUserInfo{
        String userID;
        String seat;
        RoomUserInfo(String userID,String seat){
            this.userID=userID;
            this.seat=seat;
        }
    }
    private static void roomResponse(User user,String type,String status,String cause,String roomID){

        MessageSender.sendMsg(user.getWebSocketSession(),new RoomMessage(type,status,cause,roomID));
    }
    private static void roomResponse(User user,String type,String status,String cause,String roomID,String seat){

        MessageSender.sendMsg(user.getWebSocketSession(),new RoomMessage(type,status,cause,roomID,seat));
    }

    private static void enterRoomResponseToOther(Room room, User user){
        List<User> others=room.getOtherUsers(user.getID());
        GameMessage message=new GameMessage();
        message.setGameMessageType(GameMessage.GameMessageType.enterRoom);
        message.setUserID(user.getID());
        message.setSeat(user.getSeat());
        others.forEach((other->{
            MessageSender.sendMsg(other,message);

        }));
    }

    private static void leaveRoomResponseToOther(Room room, User user){
        List<User> others=room.getOtherUsers(user.getID());
        GameMessage message=new GameMessage();
        message.setGameMessageType(GameMessage.GameMessageType.leaveRoom);
        message.setUserID(user.getID());
        message.setSeat(user.getSeat());
        others.forEach((other->{
            MessageSender.sendMsg(other,message);

        }));
    }
    static class HomeWorker implements Runnable{

        Map<String,User> homeUserMap;
        Map<String,Room> roomMap;
        static final int interval=4; //单位：s

        HomeWorker(Map<String,User> homeUserMap, Map<String,Room> roomMap){

            this.homeUserMap = homeUserMap;
            this.roomMap=roomMap;
/*
            User user=new User();
            user.setID("342");
            Room room=new Room();
            room.setID("234");
            roomMap.put(room.getID(),room);*/

        }
        @Override
        public void run() {
            Logger.log("thread Home Started!");
            while (true){
                synchronized (RoomDispatch.homeUsermpLock) {
                    if(homeUserMap.size()==0){
                        return;
                    }
                    synchronized (RoomDispatch.roommpLock) {
                        checkRoomMap();
                        List<Room> tem=new ArrayList<>(roomMap.values());

                        //if (!roomMap.isEmpty()) {
                            homeUserMap.forEach((ID, user) -> {
                                try {
                                    MessageSender.sendMsg(tem, user.getWebSocketSession());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                       // }
                    }
                }

                try {
                    TimeUnit.SECONDS.sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
        private void checkRoomMap(){
            List<String> invalid=new ArrayList<>();
            roomMap.forEach((key,value)->{
                if (value.getPlayersNum()==0) invalid.add(key);
            });
            invalid.forEach((string -> roomMap.remove(string)));
        }
    }

    static class RoomWorker implements Runnable{
        Room room;

        RoomWorker(Room room){
            this.room=room;
        }

        @Override
        public void run() {
            Logger.log("thread RoomID "+room.getID()+" Started!");
            GameChan gameChan=room.getGameChan();
            while (true){

                synchronized (room.userLock)
                {
                    if(room.getPlayersNum()==0) {
                        Logger.log("thread RoomID "+room.getID()+" Stopped!");
                        return;
                    }
                    if(room.getPlayersNum()>3||room.getPlayersNum()<0){
                        throw new RuntimeException("Invalid person number something must be wrong.");
                    }
                    while(!gameChan.isEmpty()){
                        try {
                            handleMessage(gameChan.receive());

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        private User getUser(String userID){
            return room.getUser(userID);
        }
        private void handleMessage(GameMessage gameMessage){

            User user=getUser(gameMessage.getUserID());

            switch (gameMessage.getGameMessageType()){

                case noSnatchLord:
                    case passLord:
                        case competeLord:
                            case doubleScore:
                                case getLord:
                                    case play:
                                        MessageSender.sendMsgToRoom(room,gameMessage);break;


                case unready:
                     user.setStatus(User.UserStatus.unready);
                     MessageSender.sendMsgToRoom(room,gameMessage);break;
                case timeout:
                      user.setStatus(User.UserStatus.trusteeship);
                      MessageSender.sendMsgToRoom(room,gameMessage);break;

                case reDealCards:
                    deal(room, GameMessage.GameMessageType.reDealCards);break;

                case ready:
                     user.setStatus(User.UserStatus.ready);
                     MessageSender.sendMsgToRoom(room,gameMessage);
                     if(room.getReadyUserNum()==3){
                         deal(room, GameMessage.GameMessageType.dealCards);
                     }break;


                case getBaseCards:
                    dealBaseCards(room,gameMessage.getLordId());

                case getRoomInfo:
                    MessageSender.sendRoomDomain(room.getUser(gameMessage.getUserID()),room.generator());break;
                case reconnection:
                    RoomSnapShootDomain roomSnapShootDomain=new RoomSnapShootDomain();
                    roomSnapShootDomain.setRoomDomain(room.generator());
                    roomSnapShootDomain.setGameSnapShootDomain(room.getCardAudit().generator(gameMessage.getUserID()));
                    MessageSender.sendRoomSnapShootDomain(room.getUser(gameMessage.getUserID()),roomSnapShootDomain);break;
            }
        }
    }
    private static void dealBaseCards(Room room,String userID){
        CardAudit cardAudit=room.getCardAudit();
        Card[] baseCards=cardAudit.getBaseCard().toArray(Card[]::new);
        GameMessage gameMessage=new GameMessage();
        gameMessage.setGameMessageType(GameMessage.GameMessageType.getBaseCards);
        gameMessage.setLordId(userID);
        gameMessage.setCards(baseCards);
        MessageSender.sendMsgToRoom(room,gameMessage);

    }


    private static void deal(Room room,GameMessage.GameMessageType type){
        CardAudit cardAudit=room.getCardAudit();
        cardAudit.deal();
        Map<String,List<Card>> userCards=cardAudit.getUserCards();
        User lordStart=room.getRandomUser();
        userCards.forEach((key,val)->{
            User user=room.getUser(key);
            user.setStatus(User.UserStatus.play);
            GameMessage dealMessage=new GameMessage();
            dealMessage.setUserID(lordStart.getID());
            dealMessage.setGameMessageType(type);
            dealMessage.setCards(val.toArray(Card[]::new));
            MessageSender.sendMsg(user,dealMessage);
        });
    }



    static volatile AtomicInteger roomID=new AtomicInteger(0);
    public static int getRoomID(){
        return roomID.getAndAdd(1);
    }
}
