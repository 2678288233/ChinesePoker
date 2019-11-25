package entity;

public class UserInfo {

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getUSER_NAME() {
        return USER_NAME;
    }

    public void setUSER_NAME(String USER_NAME) {
        this.USER_NAME = USER_NAME;
    }

    public String getUSER_PWD() {
        return USER_PWD;
    }

    public void setUSER_PWD(String USER_PWD) {
        this.USER_PWD = USER_PWD;
    }

    public int getUSER_SCORE() {
        return USER_SCORE;
    }

    public void setUSER_SCORE(int USER_SCORE) {
        this.USER_SCORE = USER_SCORE;
    }

    public int getUSER_WINNUM() {
        return USER_WINNUM;
    }

    public void setUSER_WINNUM(int USER_WINNUM) {
        this.USER_WINNUM = USER_WINNUM;
    }

    public int getUSER_LOSENUM() {
        return USER_LOSENUM;
    }

    public void setUSER_LOSENUM(int USER_LOSENUM) {
        this.USER_LOSENUM = USER_LOSENUM;
    }


    private String USER_ID;
    private String USER_NAME;
    private String USER_PWD;
    private int USER_SCORE;
    private int USER_WINNUM;
    private int USER_LOSENUM;

}
