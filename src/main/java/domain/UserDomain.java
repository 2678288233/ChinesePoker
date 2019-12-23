package domain;

import entity.User;

public class UserDomain {
    private int seat;
    private String UserID;
    private User.UserStatus status;
    private String name;
    private Integer score;

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public User.UserStatus getStatus() {
        return status;
    }

    public void setStatus(User.UserStatus status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
