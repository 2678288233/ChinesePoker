package entity;

public class UserRelatedGame {
    private String USER_ID;
    private String GAME_ID;
    private int USER_SIGN;           //1是地主，0是农民
    private int USER_SCORE;          //0为不变，正数为加相应的分，负数为扣相应的分

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getGAME_ID() {
        return GAME_ID;
    }

    public void setGAME_ID(String GAME_ID) {
        this.GAME_ID = GAME_ID;
    }

    public int getUSER_SIGN() {
        return USER_SIGN;
    }

    public void setUSER_SIGN(int USER_SIGN) {
        this.USER_SIGN = USER_SIGN;
    }

    public int getUSER_SCORE() {
        return USER_SCORE;
    }

    public void setUSER_SCORE(int USER_SCORE) {
        this.USER_SCORE = USER_SCORE;
    }
}
