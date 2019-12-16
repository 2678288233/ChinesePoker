package domain;

public class RoomSnapShootDomain {
    private RoomDomain roomDomain;
    private GameSnapShootDomain gameSnapShootDomain;

    public RoomDomain getRoomDomain() {
        return roomDomain;
    }

    public void setRoomDomain(RoomDomain roomDomain) {
        this.roomDomain = roomDomain;
    }

    public GameSnapShootDomain getGameSnapShootDomain() {
        return gameSnapShootDomain;
    }

    public void setGameSnapShootDomain(GameSnapShootDomain gameSnapShootDomain) {
        this.gameSnapShootDomain = gameSnapShootDomain;
    }
}
