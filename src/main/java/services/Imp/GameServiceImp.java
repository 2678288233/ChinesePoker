package services.Imp;

import entity.User;
import messages.GameChan;
import messages.GameMessage;
import messages.GameMessageType;
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
        gameChan.send(user.getID(),new GameMessage(GameMessageType.ready));
    }

    @Override
    public void unready() {
        gameChan.send(user.getID(),new GameMessage(GameMessageType.unready));
    }

    @Override
    public void timeout() {
        gameChan.send(user.getID(),new GameMessage(GameMessageType.timeout));
    }

    @Override
    public void play(TextMessage textMessage) {
        gameChan.send(user.getID(),new GameMessage(GameMessageType.play));
    }
}
