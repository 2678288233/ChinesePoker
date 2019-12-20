package messages;

public class RoomMessage{
    public String type;
    public String status;
    public String cause;
    public String roomID;
    String seat;

    public RoomMessage(String type,String status,String cause,String roomID){this.type=type;this .cause=cause;this.status=status;this.roomID=roomID;}
    public RoomMessage(String type,String status,String cause,String roomID,String seat){
        this(type,status,cause,roomID);
        this.seat=seat;
    }
}
