package messages;

import entity.User;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class GameChan  {
    private static int DEFAULT_BLOCKING_QUEUE_CAPACITY=6;

    private BlockingQueue<GameMessage> blockingQueue=new ArrayBlockingQueue<>(DEFAULT_BLOCKING_QUEUE_CAPACITY);

    public synchronized void  send(GameMessage gameMessage) throws InterruptedException {
        blockingQueue.put(gameMessage);
    }

    public synchronized void  send(User user, GameMessage gameMessage) throws InterruptedException {
        gameMessage.setUserID(user.getID());
        blockingQueue.put(gameMessage);
    }

    public GameMessage receive() throws InterruptedException {
        return blockingQueue.take();
    }
    public boolean isEmpty(){
        return blockingQueue.isEmpty();
    }
}
