package entity;

public class GameInfo {
    private String GAME_ID;
    private String GAME_START_TIME;
    private String GAME_LAST_TIME;
    private String FIRST_USER_ID;
    private String SECOND_USER_ID;
    private String THIRD_USER_ID;

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

    public String getFIRST_USER_ID() {
        return FIRST_USER_ID;
    }

    public void setFIRST_USER_ID(String FIRST_USER_ID) {
        this.FIRST_USER_ID = FIRST_USER_ID;
    }

    public String getSECOND_USER_ID() {
        return SECOND_USER_ID;
    }

    public void setSECOND_USER_ID(String SECOND_USER_ID) {
        this.SECOND_USER_ID = SECOND_USER_ID;
    }

    public String getTHIRD_USER_ID() {
        return THIRD_USER_ID;
    }

    public void setTHIRD_USER_ID(String THIRD_USER_ID) {
        this.THIRD_USER_ID = THIRD_USER_ID;
    }
}
