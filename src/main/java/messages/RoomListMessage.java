package messages;

import entity.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomListMessage {

    private RoomMessageType type;
    private List<RoomMessageItem> list;

    public RoomListMessage(){list=new ArrayList<>();}

    public RoomListMessage(List<Room>rooms){
        list=new ArrayList<>();
        setType(RoomListMessage.RoomMessageType.roomlist);
        rooms.forEach((room -> {
            RoomListMessage.RoomMessageItem item=new RoomListMessage.RoomMessageItem();
            item.setRoomId(room.getID());
            item.setUserNum(room.getPlayersNum());
            item.setDescription(room.getDescript());
            add(item);
        }));

    }
    public void add(RoomMessageItem item){list.add(item);}
    public static class RoomMessageItem{
        private String roomId;
        private String description;
        private int userNum;


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

        public int getUserNum() {
            return userNum;
        }

        public void setUserNum(int userNum) {
            this.userNum = userNum;
        }


    }

    public enum RoomMessageType{
        roomlist,
    }

    public RoomMessageType getType() {
        return type;
    }

    public void setType(RoomMessageType type) {
        this.type = type;
    }

    public List<RoomMessageItem> getList() {
        return list;
    }

    public void setList(List<RoomMessageItem> list) {
        this.list = list;
    }
}
