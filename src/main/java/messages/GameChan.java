package messages;

import entity.User;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class GameChan  {
    private BlockingQueue<GameMessage> blockingQueue=new ArrayBlockingQueue<>(3);

    public synchronized void  send(GameMessage gameMessage) throws InterruptedException {
        blockingQueue.put(gameMessage);
    }

    public synchronized void  send(User user, GameMessage gameMessage) throws InterruptedException {
        blockingQueue.put(gameMessage);
    }

    public GameMessage receive() throws InterruptedException {
        return blockingQueue.take();
    }
    public boolean isEmpty(){
        return blockingQueue.isEmpty();
    }
}
