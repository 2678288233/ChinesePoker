package entity;

public class UserBonus {

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public int getUSER_PEASANT_WINNUM() {
        return USER_PEASANT_WINNUM;
    }

    public void setUSER_PEASANT_WINNUM(int USER_PEASANT_WINNUM) {
        this.USER_PEASANT_WINNUM = USER_PEASANT_WINNUM;
    }

    public int getUSER_PEASANT_LOSENUM() {
        return USER_PEASANT_LOSENUM;
    }

    public void setUSER_PEASANT_LOSENUM(int USER_PEASANT_LOSENUM) {
        this.USER_PEASANT_LOSENUM = USER_PEASANT_LOSENUM;
    }

    public int getUSER_LORD_WINNUM() {
        return USER_LORD_WINNUM;
    }

    public void setUSER_LORD_WINNUM(int USER_LORD_WINNUM) {
        this.USER_LORD_WINNUM = USER_LORD_WINNUM;
    }

    public int getUSER_LORD_LOSENUM() {
        return USER_LORD_LOSENUM;
    }

    public void setUSER_LORD_LOSENUM(int USER_LORD_LOSENUM) {
        this.USER_LORD_LOSENUM = USER_LORD_LOSENUM;
    }

    public String getUSER_LAST_GAME_TIME() {
        return USER_LAST_GAME_TIME;
    }

    public void setUSER_LAST_GAME_TIME(String USER_LAST_GAME_TIME) {
        this.USER_LAST_GAME_TIME = USER_LAST_GAME_TIME;
    }

    private String USER_ID;
    private int USER_PEASANT_WINNUM;
    private int USER_PEASANT_LOSENUM;
    private int USER_LORD_WINNUM;
    private int USER_LORD_LOSENUM;
    private String USER_LAST_GAME_TIME;
}
