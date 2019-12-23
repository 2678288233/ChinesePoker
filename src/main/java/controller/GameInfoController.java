package controller;

import com.google.gson.Gson;
import dao.GameInfoDao;
import dao.UserRelatedGameDao;
import entity.GameInfo;
import entity.UserRelatedGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/gameInfo")
@ContextConfiguration(locations = {"classpath:config/spring-db.xml"})
@CrossOrigin(origins = "*",allowCredentials = "true")
public class GameInfoController {

    private GameInfoDao gameInfoDao;
    private UserRelatedGameDao userRelatedGameDao;
    @Autowired
    public void setGameInfoDao(GameInfoDao gameInfoDao) {
        this.gameInfoDao = gameInfoDao;
    }

    @Autowired
    public void setUserRelatedGameDao(UserRelatedGameDao userRelatedGameDao) {
        this.userRelatedGameDao = userRelatedGameDao;
    }

    Gson gson=new Gson();
    @PostMapping
    public Map<String,String> login(@RequestBody Map<String, String> para){
        String userId=para.get("userId");


         List<UserRelatedGame> relatedGames=userRelatedGameDao.selectByUser(userId);

         Map<String,GameInfo> gameInfoMap=new HashMap<>();
         relatedGames.forEach(p->{
             gameInfoMap.put(p.getGAME_ID(),gameInfoDao.select(p.getGAME_ID()));
         });
        GameRecords gameRecords=new GameRecords();

        String sep=",";
        relatedGames.forEach((userRelatedGame -> {
            GameInfo gameInfo=gameInfoMap.get(userRelatedGame.getGAME_ID());
            gameRecords.add(userRelatedGame.getGAME_ID(),gameInfo.getFIRST_USER_ID()+sep+gameInfo.getSECOND_USER_ID()+sep+gameInfo.getTHIRD_USER_ID(),
                userRelatedGame.getUSER_SCORE()>0?"win":"lose",userRelatedGame.getUSER_SIGN()==0?"lord":"peasent",gameInfo.getGAME_LAST_TIME(),userRelatedGame.getUSER_SCORE());
        }));
        Map<String,String > res=new HashMap<>();
        res.put("gameRecord",gson.toJson(gameRecords));
        return res;
    }
    public static class GameRecords{
        List<GameRecord> gameRecords=new ArrayList<>();
        public void add(String id,String players,String win,String identity,String time,int score){
            gameRecords.add(new GameRecord(id,players,win,identity,time,score));
        }
    }
    public static class GameRecord{
        private String GameId;
        private String players;
        private String win;
        private String identity;
        private String gameTime;
        private int score;
        GameRecord(String id,String players,String win,String identity,String time,int score){
            this.GameId=id;
            this.players=players;
            this.win=win;
            this.identity=identity;
            this.gameTime=time;
            this.score=score;
        }
    }
}

