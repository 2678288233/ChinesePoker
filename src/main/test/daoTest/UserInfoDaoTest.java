package daoTest;

import dao.UserInfoDao;
import entity.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

@ContextConfiguration(locations = {"classpath:config/spring-db.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserInfoDaoTest {

    @Autowired
    UserInfoDao userInfoDao;

    @Test
    public void testInsertUserInfo(){
        for(int i=10;i<20;++i){
           /*Map<String,String>mp=new HashMap<>();
            mp.put("USER_ID",String.valueOf(i));
            mp.put("USER_NAME",String.valueOf(i));
            mp.put("USER_PWD",String.valueOf(i));
            mp.put("USER_SCORE",String.valueOf(i));
            mp.put("USER_WINNUM",String.valueOf(i));
            mp.put("USER_LOSENUM",String.valueOf(i));
            userInfoDao.insertByMap(mp);

            */
             UserInfo userInfo=new UserInfo();
            userInfo.setUserID(String.valueOf(i));
            userInfo.setUserName(String.valueOf(i));
            userInfo.setUserLoseNum(i);
            userInfo.setUserWinNum(i);
            userInfo.setUserScore(i);
            userInfo.setUserPWD(String.valueOf(i));
            userInfoDao.insert(userInfo);
        }

    }
}
