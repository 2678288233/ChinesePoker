package services.Imp;

import entity.Room;
import entity.User;
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
        RoomDispatch.createRoom(user,room);
    }

    @Override
    public void enterRoom(String roomID) {
        RoomDispatch.enterRoom(user,roomID);
    }

    @Override
    public void leaveRoom() {
        RoomDispatch.leaveRoom(user);
    }
}
