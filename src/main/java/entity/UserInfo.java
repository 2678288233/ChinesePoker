package entity;

public class UserInfo {
    String userID;
    String userName;
    String userPWD;
    int userScore;
    int userWinNum;
    int UserLoseNum;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
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
