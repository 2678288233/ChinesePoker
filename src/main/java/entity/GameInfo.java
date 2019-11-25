package entity;

public class GameInfo {
    private String GAME_ID;
    private String GAME_START_TIME;
    private String GAME_LAST_TIME;

    public String getGAME_ID() {
        return GAME_ID;
    }

    public void setGAME_ID(String GAME_ID) {
        this.GAME_ID = GAME_ID;
    }

    public String getGAME_START_TIME() {
        return GAME_START_TIME;
    }

    public void setGAME_START_TIME(String GAME_START_TIME) {
        this.GAME_START_TIME = GAME_START_TIME;
    }

    public String getGAME_LAST_TIME() {
        return GAME_LAST_TIME;
    }

    public void setGAME_LAST_TIME(String GAME_LAST_TIME) {
        this.GAME_LAST_TIME = GAME_LAST_TIME;
    }
}
