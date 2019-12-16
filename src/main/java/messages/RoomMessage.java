package messages;

public class RoomMessage{
    public String type;
    public String status;
    public String cause;
    public String roomID;

    public RoomMessage(String type,String status,String cause,String roomID){this.type=type;this .cause=cause;this.status=status;this.roomID=roomID;}
}
