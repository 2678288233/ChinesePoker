package services.Imp;

import entity.Card;
import entity.User;
import messages.GameChan;
import messages.GameMessage;

import org.springframework.web.socket.TextMessage;
import services.GameService;

public class GameServiceImp implements GameService {
    final static double DEFAULT_DOUBLE_SCORE=2.0;

    GameChan gameChan;
    User user;
    public GameServiceImp(User user){
        this.user=user;
        this.gameChan=user.getGameChan();
    }
    @Override
    public void ready() {
        try {
            gameChan.send(user,new GameMessage(GameMessage.GameMessageType.ready));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void unready() {
        try {
            gameChan.send(user,new GameMessage(GameMessage.GameMessageType.unready));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void timeout() {
        try{
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
    public void getBaseCards() {
        try{

            gameChan.send(user,new GameMessage(GameMessage.GameMessageType.getBaseCards));
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
