package controller;

import dao.UserBonusDao;
import dao.UserInfoDao;
import entity.User;
import entity.UserBonus;
import entity.UserInfo;
import log.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
@ContextConfiguration(locations = {"classpath:config/spring-db.xml"})
@CrossOrigin(origins = "*",allowCredentials = "true")
public class LoginRestController {
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

    private UserInfoDao userInfoDao;
    private UserBonusDao userBonusDao;
    //userID->user
    @Resource(name = "usersCache")
    HashMap<String,User> usersCache;

    //userName->userInfo
    @Resource(name = "usersInfoCache")
    HashMap<String,UserInfo> usersInfoCache;


    @PostMapping("/login")//String username,@RequestParam("password") String password
    public Map<String,String> login(@RequestBody Map<String, String> user){
        String username=user.get("username");
        String password=user.get("password");
        UserInfo userInfo=getUserInfo(username);

        Map<String,String>mp=new HashMap<>();
        if (password.equals(userInfo.getUSER_PWD())){
            mp.put("status","success");
            mp.put("error","");
            mp.put("id",userInfo.getUSER_ID());
            checkFirstLogin(userInfo.getUSER_ID(),userInfo);
        }else{
            mp.put("status","fail");
            mp.put("error","wrong password");
        }
        return mp;
    }

    @PostMapping("/register")//@RequestParam("username")String username,@RequestParam("password")String password
    public Map<String,String> register(@RequestBody Map<String, String> user){
        String username=user.get("username");
        String password=user.get("password");

        Map<String,String>mp=new HashMap<>();
        if(userInfoDao.selectByName(username)!=null){
            mp.put("status","fail");
            mp.put("error","repetitive username");
            return mp;
        }

        UserInfo userInfo=new UserInfo();
        userInfo.setUSER_ID(User.generaterID());
        userInfo.setUSER_NAME(username);
        userInfo.setUSER_PWD(password);
        userInfoDao.insert(userInfo);
        usersInfoCache.put(username,userInfo);
        checkFirstLogin(userInfo.getUSER_ID(),userInfo);

        UserBonus userBonus=new UserBonus();
        userBonus.setUSER_LAST_GAME_TIME(new Date().toString());
        userBonus.setUSER_ID(userInfo.getUSER_ID());
        userBonusDao.insert(userBonus);
        mp.put("status","success");
        mp.put("error","");
        mp.put("id",userInfo.getUSER_ID());
        return mp;
    }
    @PostMapping("/changePWD")//@RequestParam("userID")String userID,@RequestParam("oldPWD")String oldPWD,@RequestParam("newPWD")String newPWD
    public Map<String,String >changePWD(@RequestBody Map<String, String> user){
        String userID=user.get("userID");
        String oldPWD=user.get("oldPWD");
        String newPWD=user.get("newPWD");
        UserInfo userInfo=userInfoDao.select(userID);
        Map<String,String>mp=new HashMap<>();
        if(!userInfo.getUSER_PWD().equals(oldPWD)){
            mp.put("status","fail");
            mp.put("error","wrong password");
            return mp;
        }
        if(usersInfoCache.containsKey(userInfo.getUSER_NAME())){
            usersInfoCache.get(userInfo.getUSER_NAME()).setUSER_PWD(newPWD);
        }
        userInfo.setUSER_PWD(newPWD);
        userInfoDao.update(userInfo);
        mp.put("status","success");
        mp.put("error","");
        return mp;
    }

    private UserInfo getUserInfo(String username){
        if(usersInfoCache.containsKey(username)){
            return usersInfoCache.get(username);
        }else {
            UserInfo userInfo=userInfoDao.selectByName(username);
            usersInfoCache.put(username,userInfo);
            return  userInfo;
        }
    }

    private User checkFirstLogin(String userID,UserInfo userInfo){

        User user;
        if(usersCache.containsKey(userID)){
            user=usersCache.get(userID);
            if(user.getStatus()==User.UserStatus.logout){
                user.setStatus(User.UserStatus.login);
            }
        }else {
            user=userLogin(userID);
            user.setUserInfo(userInfo);
            usersCache.put(userID,user);
            Logger.log("user "+userID+"  is registered");
            user.setStatus(User.UserStatus.login);
        }
        return user;
    }

    private User userLogin(String userID){
        User user=new User();
        user.setID(userID);
        return user;
    }
}
