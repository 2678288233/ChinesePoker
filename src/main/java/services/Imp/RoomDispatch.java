package services.Imp;

import entity.Room;
import entity.User;
import exceptions.*;
import messages.GameChan;
import messages.GameMessage;
import messages.MessageSender;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class RoomDispatch {
    private static Map<String,User> userMap=new HashMap<>();
    private static Map<String,User> homeUserMap=new HashMap<>();
    private static Map<String,Room> roomMap=new HashMap<>();
    private static Map<String,String> userRoomMap=new HashMap<>();

    //优先级  roommpLock > room.userLock > homeUsermpLock
    //
    private static final Object homeUsermpLock=new Object();
    private static final Object usermpLock=new Object();
    private static final Object roommpLock=new Object();
    //private static final Object userroommpLock=new Object();

    public static void enterHome(User user){
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
    public static void leaveHome(User user){
        synchronized (homeUsermpLock){
            if(!homeUserMap.containsKey(user.getID())){
                throw new NotInHomeException("Not In Home ,cannot leave home!");
            }
            homeUserMap.remove(user.getID());
        }

    }

    public static void createRoom(User user,Room room){
        synchronized (roommpLock){
            if(roomMap.containsKey(room.getID())){
                throw new RepetitiveRoomIDException("Repetitive Room ID");
            }
            roomMap.put(room.getID(),room);
            synchronized (room.userLock) {
                userRoomMap.put(user.getID(), room.getID());
                leaveHome(user);
                user.setGameChan(room.getGameChan());
                user.setRoomID(room.getID());
                room.addPlayer(user);
                new Thread(new RoomWorker(room)).start();
            }
        }
    }

    public static void enterRoom(User user,String roomID){

        synchronized (roommpLock){
            if(!roomMap.containsKey(roomID)){
                throw new NonexistedRoomException("Nonexisted Room!");
            }
            Room room=roomMap.get(roomID);
            synchronized (room.userLock) {
                if(room.getPlayersNum()>3){
                    throw new FullRoomException("The Room is FULL!");
                }
                userRoomMap.put(user.getID(), room.getID());
                leaveHome(user);
                user.setGameChan(room.getGameChan());
                user.setRoomID(room.getID());
                room.addPlayer(user);
            }
        }
    }
    public static void leaveRoom(User user){
        String roomID=userRoomMap.get(user.getID());
        synchronized (roommpLock) {
            if (!roomMap.containsKey(roomID)) {
                throw new NonexistedRoomException("Nonexisted Room!");
            }
            Room room = roomMap.get(roomID);
            synchronized (room.userLock) {
                room.remoevPlayer(user.getID());
                userRoomMap.remove(user.getID());
                user.setRoomID(null);
                user.setGameChan(null);
            }
        }
        enterHome(user);
    }


    static class HomeWorker implements Runnable{

        Map<String,User> homeUserMap;
        Map<String,Room> roomMap;
        static final int interval=2; //单位：s

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
            while (true){
                synchronized (RoomDispatch.homeUsermpLock) {
                    if(homeUserMap.size()==0){
                        return;
                    }
                    synchronized (RoomDispatch.roommpLock) {
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
    }

    static class RoomWorker implements Runnable{
        Room room;

        RoomWorker(Room room){
            this.room=room;
        }

        @Override
        public void run() {
            GameChan gameChan=room.getGameChan();
            while (true){

                synchronized (room.userLock)
                {
                    if(room.getPlayersNum()==0)
                        return;
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





        }
    }
}
