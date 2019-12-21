package services.Imp;



import dao.GameReplayRecordDao;
import domain.GameSnapShootDomain;
import entity.Card;
import entity.CardPlayedLastTurn;
import entity.CardReplayRecord;
import entity.GameReplayRecord;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


public class CardAudit {
    private static final int TOTAL_CARDS_NUM=54;
    private static final int BASE_NUM=3;
    private static final int USER_NUM=3;
    private final static Random random =new Random();
    private int currentUser=0;
    private final boolean[] audit=new boolean[TOTAL_CARDS_NUM];
    private final GameReplayRecordDao gameReplayRecordDao;
    private final Map<String,List<Card>> userCards=new HashMap<>();
    private final List<Card> baseCard=new ArrayList<>();
    private final String[] users=new String[USER_NUM];
    private final CardPlayedLastTurn cardPlayedLastTurn=new CardPlayedLastTurn();
    private double scoreRate=1;

    private String gameID;
    private final List<GameReplayRecord> records=new ArrayList<>();
//    private List<CardReplayRecord>records=new ArrayList<>();

    public CardAudit(GameReplayRecordDao gameReplayRecordDao) {
        this.gameReplayRecordDao=gameReplayRecordDao;
    }

    public boolean addUser(String userID){
        if(userCards.size()==3)return false;
        int pos=searchFirstPos();
        addUser(userID,pos);
        return true;
    }


    private int searchFirstPos(){
        for (int i = 0; i <USER_NUM ; i++) {
            if (users[i]==null||users[i].trim().equals(""))return i;
        }
        return -1;
    }
    public boolean addUser(String userID,int pos){
        if(pos<0||pos>2||(users[pos]!=null&&!users[pos].equals(""))) return false;
        users[pos]=userID;
        userCards.put(userID,new ArrayList<>());
        return true;
    }

    public void doubleScore(double rate){
        this.scoreRate*=rate;
    }

    public void play(String userID,Card[] cards){
        Arrays.stream(users).forEach(System.out::println);
        if(!userID.equals(users[currentUser]))
            throw new RuntimeException("Invalid Play Order! Something maybe wrong!");

        Arrays.stream(cards).forEach((card -> {
            if(check(card))
                throw new RuntimeException("Invalid Card! Something maybe wrong!");
        }));
        currentUser=(++currentUser)%USER_NUM;
        cardPlayedLastTurn.addCard(userID,cards);
        changeCardStatus(cards);
        doPlay(userID,cards);
        removeCards(userID,cards);
    }

    public boolean removeUser(String userID){
        for (int i = 0; i <USER_NUM ; i++) {
            if(userID.equals(users[i])){
                users[i]=null;
                userCards.remove(userID);
                return true;
            }
        }
        return false;
    }
    private void removeCards(String userID,Card[] cards){
        userCards.get(userID).removeAll(Arrays.asList(cards));

    }
    private void changeCardStatus(Card[] cards){
        Arrays.stream(cards).forEach((p)->{
            audit[p.getInter()]=true;
        });
    }
    private void doPlay(String userID,Card[] cards){
        GameReplayRecord gameReplayRecord=new GameReplayRecord();
        gameReplayRecord.setUSER_ID(userID);
        gameReplayRecord.setCARD_RECORD( Arrays.stream(cards).map(card -> String.valueOf(card.getInter())).collect(Collectors.joining(",")));
        gameReplayRecord.setGAME_ID(gameID);
        gameReplayRecord.setPLAY_TIME(String.valueOf(System.currentTimeMillis()));
        records.add(gameReplayRecord);
//        gameReplayRecordDao.insert(gameReplayRecord);

    }
    private boolean check(Card card){
        return audit[card.getInter()];
    }

    public void gameOver(){
        doGameOver();
    }

    private synchronized void doGameOver(){
        if (records.isEmpty())return;
        records.forEach(gameReplayRecordDao::insert);
        records.clear();
    }


    public GameSnapShootDomain generator(String userID){
        GameSnapShootDomain gameSnapShootDomain=new GameSnapShootDomain();
        gameSnapShootDomain.setBaseCards(baseCard.toArray(Card[]::new));
        gameSnapShootDomain.setCards(userCards.get(userID).toArray(Card[]::new));
        gameSnapShootDomain.setLastTurns(cardPlayedLastTurn);
        Arrays.stream(users).forEach((user)->gameSnapShootDomain.getUserCardNum().putIfAbsent(user,userCards.get(user).size()));
        gameSnapShootDomain.setCurrentScore(scoreRate);
        return gameSnapShootDomain;
    }
    public void deal(){

        gameID=String.valueOf(getGameID());
        userCards.forEach((key,val)->val.clear());
        baseCard.clear();
        int[] randNums=randGen(TOTAL_CARDS_NUM);
        int j=0;

        for (int i = 0; i <TOTAL_CARDS_NUM-BASE_NUM ; i++) {
            userCards.get(users[j]).add(new Card(randNums[i]));
            j=++j%USER_NUM;
        }

        for (int i = TOTAL_CARDS_NUM-BASE_NUM; i <TOTAL_CARDS_NUM; i++) {
            baseCard.add(new Card(randNums[i]));
        }
    }

    private int[] randGen(int num){
        int[] res=new int[num];
        List<Integer> list=new LinkedList<>();
        for (int i = 0; i <num ; i++) {
            list.add(i);
        }
        for (int i = 0; i <num ; i++) {
            res[i]=randChoice(list);
        }
        return res;
    }
    private<T> T randChoice(List<T> nums){

        int rand=random.nextInt(nums.size());
        T tmp=nums.get(rand);
        nums.remove(rand);
        return tmp;
    }


    public List<Card> getUserCards( String userID) {
        return userCards.get(userID);
    }
    public Map<String, List<Card>> getUserCards() {
        return userCards;
    }

    public List<Card> getBaseCard() {
        return baseCard;
    }

    private static final AtomicInteger gameIdGenerate=new AtomicInteger();
    static int getGameID(){
        return gameIdGenerate.getAndAdd(1);

    }
}
