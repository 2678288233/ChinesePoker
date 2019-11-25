package entity;

public class UserInfo {
    private String userID;
    private String userName;
    private String userPWD;
    private int userScore;
    private int userWinNum;
    private int UserLoseNum;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPWD() {
        return userPWD;
    }

    public void setUserPWD(String userPWD) {
        this.userPWD = userPWD;
    }

    public int getUserScore() {
        return userScore;
    }

    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }

    public int getUserWinNum() {
        return userWinNum;
    }

    public void setUserWinNum(int userWinNum) {
        this.userWinNum = userWinNum;
    }

    public int getUserLoseNum() {
        return UserLoseNum;
    }

    public void setUserLoseNum(int userLoseNum) {
        UserLoseNum = userLoseNum;
    }
}
