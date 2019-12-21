package services.Imp;

import entity.Card;
import entity.User;
import messages.GameChan;
import messages.GameMessage;

import services.GameService;

public class GameServiceImp implements GameService {
    private final static double DEFAULT_DOUBLE_SCORE=2.0;

    private GameChan gameChan;
    private User user;

    public GameServiceImp(User user){
        this.user=user;
        this.gameChan=user.getGameChan();
    }
    private void flushGameChan(){
        gameChan=user.getGameChan();
    }
    @Override
    public void ready() {
        try {
            flushGameChan();
            gameChan.send(user,new GameMessage(GameMessage.GameMessageType.ready));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void unready() {
        try {
            flushGameChan();
            gameChan.send(user,new GameMessage(GameMessage.GameMessageType.unready));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void timeout() {
        try{
            flushGameChan();
            gameChan.send(user,new GameMessage(GameMessage.GameMessageType.timeout));
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void play(Card[] cards) {
        try{

            GameMessage message=new GameMessage(GameMessage.GameMessageType.play);
            message.setCards(cards);
            gameChan.send(user,message);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getLord() {
        try{
            gameChan.send(user,new GameMessage(GameMessage.GameMessageType.getLord));
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void competeLord() {
        try{
            gameChan.send(user,new GameMessage(GameMessage.GameMessageType.competeLord));
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doubleScore() {
        try{
            GameMessage message=new GameMessage(GameMessage.GameMessageType.doubleScore);
            message.setDoubleRate(DEFAULT_DOUBLE_SCORE);
            gameChan.send(user,message);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pass() {
        try{
            gameChan.send(user,new GameMessage(GameMessage.GameMessageType.passLord));
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reconnection() {
        try{
            gameChan.send(user,new GameMessage(GameMessage.GameMessageType.reconnection));
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getBaseCards(String lordId) {
        try{

            GameMessage message=new GameMessage(GameMessage.GameMessageType.getBaseCards);
            message.setLordId(lordId);
            gameChan.send(user,message);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void noNatchLord() {
        try{
            gameChan.send(user,new GameMessage(GameMessage.GameMessageType.noSnatchLord));
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reDealCards() {
        try{
            gameChan.send(user,new GameMessage(GameMessage.GameMessageType.reDealCards));
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
