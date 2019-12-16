package services.Imp;

import domain.RoomSnapShootDomain;
import entity.Room;
import entity.User;
import exceptions.*;
import log.Logger;
import messages.GameChan;
import messages.GameMessage;
import messages.MessageSender;
import messages.RoomMessage;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class RoomDispatch {
    private volatile static Map<String,User> userMap=new HashMap<>();
    private volatile static Map<String,User> homeUserMap=new HashMap<>();
    private volatile static Map<String,Room> roomMap=new HashMap<>();
    private volatile static Map<String,String> userRoomMap=new HashMap<>();

    //优先级  roommpLock > room.userLock > homeUsermpLock
    //
    private static final Object homeUsermpLock=new Object();
    private static final Object usermpLock=new Object();
    private static final Object roommpLock=new Object();
    //private static final Object userroommpLock=new Object();

    public static void enterHome(User user){
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
    }
    public static void leaveHome(User user) throws NotInHomeException {
        Logger.log(user.getID()+" leaveHome "+"in RoomDispatch.leaveHome");
        synchronized (homeUsermpLock){
            if(!homeUserMap.containsKey(user.getID())){
                throw new NotInHomeException("Not In Home ,cannot leave home!");
            }
            homeUserMap.remove(user.getID());
        }

    }

    public static void createRoom(User user,Room room) throws NotInHomeException {
        Logger.log(user.getID()+" createRoom "+room.getID()+" in RoomDispatch.createRoom");
        synchronized (roommpLock){
            if(roomMap.containsKey(room.getID())){
                //throw new RepetitiveRoomIDException("Repetitive Room ID");
                roomResponse(user,"createRoom","error","Repetitive Room ID");
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
            roomResponse(user,"createRoom","success","");
        }
    }

    public static void enterRoom(User user,String roomID) throws NotInHomeException {
        Logger.log(user.getID()+" enterRoom "+roomID+" in RoomDispatch.enterRoom");
        synchronized (roommpLock){
            if(!roomMap.containsKey(roomID)){
                //throw new NonexistedRoomException("NonExisted Room!");
                roomResponse(user,"enterRoom","error","NonExisted Room!");
                return;
            }
            if(roomMap.get(roomID).getPlayersNum()==3){
                roomResponse(user,"enterRoom","error","Full");
                return;
            }
            Room room=roomMap.get(roomID);
            synchronized (room.userLock) {
                if(room.getPlayersNum()>3){
                    //throw new FullRoomException("The Room is FULL!");
                    roomResponse(user,"enterRoom","error","The Room is FULL!");
                    return;
                }
                userRoomMap.put(user.getID(), room.getID());
                leaveHome(user);
                user.setGameChan(room.getGameChan());
                user.setRoomID(room.getID());
                room.addPlayer(user);
            }
            roomResponse(user,"enterRoom","success","");
        }
    }
    public static void leaveRoom(User user){
        Logger.log(user.getID()+" leaveRoom  in RoomDispatch.leaveRoom");
        String roomID=userRoomMap.get(user.getID());
        synchronized (roommpLock) {
            if (!roomMap.containsKey(roomID)) {
                //throw new NonexistedRoomException("Nonexisted Room!");
                roomResponse(user,"leaveRoom","error","NonExisted Room");
                return;
            }
            Room room = roomMap.get(roomID);
            synchronized (room.userLock) {
                room.removePlayer(user.getID());
                userRoomMap.remove(user.getID());
                user.setRoomID(null);
                user.setGameChan(null);
            }
        }
        enterHome(user);
        roomResponse(user,"leaveRoom","success","");
    }
    private static void roomResponse(User user,String type,String status,String cause){

        MessageSender.sendMsg(user.getWebSocketSession(),new RoomMessage(type,cause,status));
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

                        if (!roomMap.isEmpty()) {
                            homeUserMap.forEach((ID, user) -> {
                                try {
                                    MessageSender.sendMsg(tem, user.getWebSocketSession());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                        }
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
                        throw new RuntimeException("Invaild person number something must be wrong.");
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


        private void handleMessage(GameMessage gameMessage){

            switch (gameMessage.getGameMessageType()){
                case passLord:
                    case competeLord:
                        case doubleScore:
                            case getLord:
                                case unready:
                                    case timeout:
                                        case ready:
                                            case play:
                                                MessageSender.sendMsgToRoom(room,gameMessage);break;
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
}
