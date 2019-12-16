package gameTimer;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GameTimer {



    private List<GameTimerEvent> gameTimerEvents;
    private long seconds;
    private Timer timer;
    public GameTimer(long seconds){
        timer=new Timer();
        this.seconds=seconds;
        gameTimerEvents=new ArrayList<>();
    }

    public void register(GameTimerEvent gameTimerEvent){
        gameTimerEvents.add(gameTimerEvent);

    }
    public void cancel(){
        timer.cancel();
        timer=new Timer();
    }
    public void start(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameTimerEvents.forEach(GameTimerEvent::onTime);

                GameTimer.this.cancel();

            }
        }, seconds * 1000, seconds * 1000);
    }

}
