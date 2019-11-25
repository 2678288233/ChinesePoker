package services.Imp;

import entity.Card;
import entity.User;
import messages.GameChan;
import messages.GameMessage;

import org.springframework.web.socket.TextMessage;
import services.GameService;

public class GameServiceImp implements GameService {
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

    }

    @Override
    public void getLord() {

    }

    @Override
    public void competeLord() {

    }

    @Override
    public void doubleScore() {

    }

    @Override
    public void pass() {

    }

}
