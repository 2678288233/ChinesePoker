package domain;

import entity.Room;
import entity.User;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomDomain {
    private Map<String, UserDomain> users;
    private String roomId;
    private String description;

    public RoomDomain(){users=new HashMap<>(); }


    public Map<String, UserDomain> getUsers() {
        return users;
    }

    public void setUsers(Map<String, UserDomain> users) {
        this.users = users;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
