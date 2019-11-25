package controller;

import dao.UserInfoDao;
import entity.User;
import entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
@ContextConfiguration(locations = {"classpath:config/spring-db.xml"})
public class LoginRestController {
    public UserInfoDao getUserInfoDao() {
        return userInfoDao;
    }
    @Autowired
    public void setUserInfoDao(UserInfoDao userInfoDao) {
        this.userInfoDao = userInfoDao;
    }


    private UserInfoDao userInfoDao;
    @PostMapping("/login")
    public Map<String,String> login(@RequestParam("username")String username,@RequestParam("password") String password){
        UserInfo userInfo=userInfoDao.selectByName(username);
        Map<String,String>mp=new HashMap<>();

        if (password.equals(userInfo.getUSER_PWD())){
            mp.put("status","success");
            mp.put("error","");
        }else{
            mp.put("status","fail");
            mp.put("error","wrong password");
        }
        return mp;
    }

    @PostMapping("/register")
    public Map<String,String> register(@RequestParam("username")String username,@RequestParam("password")String password){
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
        mp.put("status","successful");
        mp.put("error","");
        return mp;
    }
    @PostMapping("/changePWD")
    public Map<String,String >changePWD(@RequestParam("userID")String userID,
                                        @RequestParam("oldPWD")String oldPWD,
                                        @RequestParam("newPWD")String newPWD){
        UserInfo userInfo=userInfoDao.select(userID);
        Map<String,String>mp=new HashMap<>();
        if(!userInfo.getUSER_PWD().equals(oldPWD)){
            mp.put("status","fail");
            mp.put("error","wrong password");
            return mp;
        }
        userInfo.setUSER_PWD(newPWD);
        userInfoDao.update(userInfo);
        mp.put("status","successful");
        mp.put("error","");
        return mp;
    }

}
