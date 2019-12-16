package services.Imp;

import entity.Room;
import entity.User;
import exceptions.NotInHomeException;
import messages.GameChan;
import messages.GameMessage;

import services.RoomService;

public class RoomServiceImp implements RoomService {
    private User user;

    public RoomServiceImp(User user){
        this.user=user;

    }

    @Override
    public void createRoom(Room room) {
        try {
            RoomDispatch.createRoom(user,room);
        } catch (NotInHomeException e) {
            //e.printStackTrace();
        }
    }

    @Override
    public void enterRoom(String roomID) {
        try {
            RoomDispatch.enterRoom(user,roomID);
        } catch (NotInHomeException e) {
            //e.printStackTrace();
        }
    }

    @Override
    public void leaveRoom() {
        RoomDispatch.leaveRoom(user);
    }
}
