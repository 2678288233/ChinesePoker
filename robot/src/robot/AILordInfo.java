package robot;

public class AILordInfo {
    private final String id;
    private final int score;

    public AILordInfo(String id, int score) {
        this.id = id;
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public int getScore() {
        return score;
    }
}
