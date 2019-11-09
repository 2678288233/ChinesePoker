package services;

import entity.User;
import org.springframework.web.socket.TextMessage;

public interface GameService {
    void ready();
    void unready();
    void timeout();
    void play(TextMessage textMessage);
}
