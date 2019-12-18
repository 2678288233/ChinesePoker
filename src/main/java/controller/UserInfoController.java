package controller;

import com.google.gson.Gson;
import dao.UserBonusDao;
import dao.UserInfoDao;


import entity.UserBonus;
import entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/userInfo")
@ContextConfiguration(locations = {"classpath:config/spring-db.xml"})
@CrossOrigin(origins = "*",allowCredentials = "true")
public class UserInfoController {

    public UserInfoDao getUserInfoDao() {
        return userInfoDao;
    }
    @Autowired
    public void setUserInfoDao(UserInfoDao userInfoDao) {
        this.userInfoDao = userInfoDao;
    }

    public UserBonusDao getUserBonusDao() {
        return userBonusDao;
    }
    @Autowired
    public void setUserBonusDao(UserBonusDao userBonusDao) {
        this.userBonusDao = userBonusDao;
    }

    public Gson getGson() {
        return gson;
    }
    @Autowired
    public void setGson(Gson gson) {
        this.gson = gson;
    }

    private Gson gson;
    private UserInfoDao userInfoDao;
    private UserBonusDao userBonusDao;
    @PostMapping("/basic")//@RequestParam String id
    public UserInfo basicInfo(@RequestBody Map<String,String>user){
        String id=user.get("id");
        return userInfoDao.select(id);
    }
    @PostMapping("/detail")//@RequestParam String id
    public UserBonus detailInfo(@RequestBody Map<String,String>user){
        String id=user.get("id");
        return userBonusDao.select(id);
    }

}
