package robot;

public class AICardsInfo {
    private final String id;
    private final AICard[] cards;

    public AICardsInfo(String id, AICard[] cards) {
        this.id = id;
        this.cards = cards;
    }

    public String getId() {
        return id;
    }

    public AICard[] getCards() {
        return cards;
    }
}
