package services;

import entity.Card;
import entity.User;
import org.springframework.web.socket.TextMessage;

public interface GameService {
    void ready();
    void unready();
    void timeout();
    void play(Card[] cards);
    void getLord();
    void competeLord();
    void doubleScore();
    void pass();
    void reconnection();
    void getBaseCards(String lordId);
    void noNatchLord();
    void reDealCards();
    void gameOver(Boolean win,Integer score);
}
