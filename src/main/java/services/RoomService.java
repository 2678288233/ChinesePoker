package services;

import entity.Room;
import entity.User;

public interface RoomService {

    void createRoom(Room room);
    void enterRoom(String roomID);
    void leaveRoom();
}
