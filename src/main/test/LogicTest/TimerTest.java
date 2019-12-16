package LogicTest;

import gameTimer.GameTimer;
import gameTimer.GameTimerEvent;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class TimerTest {
    @Test
    public void timerTest() throws InterruptedException {
        GameTimer gameTimer=new GameTimer(1);
        gameTimer.register(() -> System.out.println("1"));
        gameTimer.register(() -> System.out.println(2));
        gameTimer.start();
        TimeUnit.SECONDS.sleep(3);
        gameTimer.start();
        TimeUnit.SECONDS.sleep(3);
    }
}
