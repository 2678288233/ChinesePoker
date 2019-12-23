package daoTest;

import dao.GameReplayRecordDao;
import dao.UserBonusDao;
import dao.UserInfoDao;
import entity.Card;
import entity.GameReplayRecord;
import entity.UserBonus;
import entity.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import services.CardAuditService;
import services.Imp.CardAudit;

import java.util.Date;
import java.util.List;

@ContextConfiguration(locations = {"classpath:config/spring-db.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserInfoDaoTest {

    @Autowired
    UserInfoDao userInfoDao;
    @Autowired
    UserBonusDao userBonusDao;

    @Autowired
    GameReplayRecordDao gameReplayRecordDao;

    @Autowired
    CardAuditService cardAuditService;

    @Test
    public void testGameOver(){
        userInfoDao.update("10",true,11);
        userInfoDao.update("10",true,1111);
        userInfoDao.update("10",false,-11);
        userInfoDao.update("10",false,-1111);
        UserBonus userBonus=new UserBonus();
        userBonus.setUSER_ID("10");
        userBonus.setUSER_LORD_LOSENUM(1);
        userBonus.setUSER_LAST_GAME_TIME(new Date().toString());
        userBonusDao.updateDelta(userBonus);
    }

    @Test
    public void testReplayRecordService(){
        CardAudit cardAudit=cardAuditService.getCardAudit();
        if(cardAudit.addUser("123",0)&&
                cardAudit.addUser("234",2)&&
                    cardAudit.addUser("1234"))

            cardAudit.play("123",new Card[]{new Card(12),new Card(13)});

    }

    @Test
    public void testGameReplayRecordDao(){
        for(int i=10;i<20;++i){
            GameReplayRecord replayRecord=new GameReplayRecord();
            replayRecord.setCARD_RECORD(String.valueOf(i));
            replayRecord.setGAME_ID("123");
            replayRecord.setPLAY_TIME(String.valueOf(System.currentTimeMillis()));
            replayRecord.setUSER_ID(String.valueOf(i));
            gameReplayRecordDao.insert(replayRecord);
        }
        List<GameReplayRecord> list=gameReplayRecordDao.selectByGameID("123");
        list.forEach((p)-> System.out.println(p.getCARD_RECORD()+" "+p.getGAME_ID()+" "+p.getPLAY_TIME()+" "+p.getUSER_ID()));
    }


    @Test
    public void testSelectUserInfo(){
        System.out.println(userInfoDao.select("10"));
    }

    @Test
    public void testInsertUserInfo(){
        for(int i=10;i<20;++i){
            UserInfo userInfo=new UserInfo();
            userInfo.setUSER_ID(String.valueOf(i));
            userInfo.setUSER_NAME(String.valueOf(i));
            userInfo.setUSER_LOSENUM(i);
            userInfo.setUSER_WINNUM(i);
            userInfo.setUSER_SCORE(i);
            userInfo.setUSER_PWD(String.valueOf(i));
            userInfoDao.insert(userInfo);
        }

    }
    @Test
    public  void testInsertUserBonus(){
        for(int i=10;i<20;++i){
            UserBonus userBonus=new UserBonus();
            userBonus.setUSER_LAST_GAME_TIME(new Date().toString());
            userBonus.setUSER_LORD_LOSENUM(i);
            userBonus.setUSER_LORD_WINNUM(i);
            userBonus.setUSER_PEASANT_LOSENUM(i);
            userBonus.setUSER_PEASANT_WINNUM(i);
            userBonus.setUSER_ID(String.valueOf(i));
            userBonusDao.insert(userBonus);
        }
    }
}
