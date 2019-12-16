package entity;

import java.util.LinkedList;
import java.util.List;

public class CardPlayedLastTurn {
    private final List<CardPlayed> cardsPlayed=new LinkedList<>();
    public void addCard(String userID,Card[] cards){
        if(cardsPlayed.size()==3){
            cardsPlayed.remove(0);
        }
        cardsPlayed.add(new CardPlayed(userID,cards));
    }


    static class CardPlayed{
        Card[] cards;
        String userId;
        CardPlayed(String userId,Card[] cards){
            this.cards=cards;
            this.userId=userId;
        }
    }
}
