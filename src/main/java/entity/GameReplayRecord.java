package entity;

public class GameReplayRecord {
    private String GAME_ID;
    private String USER_ID;
    private String CARD_RECORD;
    private String PLAY_TIME;

    public String getGAME_ID() {
        return GAME_ID;
    }

    public void setGAME_ID(String GAME_ID) {
        this.GAME_ID = GAME_ID;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getCARD_RECORD() {
        return CARD_RECORD;
    }

    public void setCARD_RECORD(String CARD_RECORD) {
        this.CARD_RECORD = CARD_RECORD;
    }

    public String getPLAY_TIME() {
        return PLAY_TIME;
    }

    public void setPLAY_TIME(String PLAY_TIME) {
        this.PLAY_TIME = PLAY_TIME;
    }
}
