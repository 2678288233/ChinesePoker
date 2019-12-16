package domain;

import entity.Card;
import entity.CardPlayedLastTurn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameSnapShootDomain {
    private Card[] cards;
    private double currentScore;
    private Card[] baseCards;
    private Map<String,Integer> userCardNum;
    private CardPlayedLastTurn lastTurns;
    public GameSnapShootDomain(){
        userCardNum=new HashMap<>();
    }

    public Card[] getCards() {
        return cards;
    }

    public void setCards(Card[] cards) {
        this.cards = cards;
    }

    public double getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(double currentScore) {
        this.currentScore = currentScore;
    }

    public Card[] getBaseCards() {
        return baseCards;
    }

    public void setBaseCards(Card[] baseCards) {
        this.baseCards = baseCards;
    }

    public Map<String, Integer> getUserCardNum() {
        return userCardNum;
    }

    public void setUserCardNum(Map<String, Integer> userCardNum) {
        this.userCardNum = userCardNum;
    }

    public CardPlayedLastTurn getLastTurns() {
        return lastTurns;
    }

    public void setLastTurns(CardPlayedLastTurn lastTurns) {
        this.lastTurns = lastTurns;
    }
}
