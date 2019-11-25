package entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GameRecord {
    Card[] cards=new Card[54];
    boolean[] used=new boolean[54];
    List<Record> records=new ArrayList<>();

    private void check(Card[] cards){
        throw new RuntimeException("");
    }

    public void addRecord(String userID,Date date,Card[] cards){
        check(cards);
        records.add(new Record(userID,date,cards));
    }

    static class Record{
        String userID;
        Date date;
        Card[] cards;
        Record(String userID,Date date,Card[] cards){
            this.userID=userID;
            this.date=date;
            this.cards=cards;
        }
    }
}
