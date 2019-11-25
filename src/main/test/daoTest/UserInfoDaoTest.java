package daoTest;

import dao.UserBonusDao;
import dao.UserInfoDao;
import entity.UserBonus;
import entity.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@ContextConfiguration(locations = {"classpath:config/spring-db.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserInfoDaoTest {

    @Autowired
    UserInfoDao userInfoDao;
    @Autowired
    UserBonusDao userBonusDao;

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
