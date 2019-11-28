package controller;

import dao.UserInfoDao;
import entity.User;
import entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
@ContextConfiguration(locations = {"classpath:config/spring-db.xml"})
@CrossOrigin(origins = "http://localhost:8080",allowCredentials = "true")
public class LoginRestController {
    public UserInfoDao getUserInfoDao() {
        return userInfoDao;
    }
    @Autowired
    public void setUserInfoDao(UserInfoDao userInfoDao) {
        this.userInfoDao = userInfoDao;
    }


    private UserInfoDao userInfoDao;
    @PostMapping("/login")//String username,@RequestParam("password") String password
    public Map<String,String> login(@RequestBody Map<String, String> user){
        String username=user.get("username");
        String password=user.get("password");
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

    @PostMapping("/register")//@RequestParam("username")String username,@RequestParam("password")String password
    public Map<String,String> register(@RequestBody Map<String, String> user){
        String username=user.get("username");
        String password=user.get("password");

        Map<String,String>mp=new HashMap<>();
        if(userInfoDao.selectByName(username)!=null){
            mp.put("status","fail");
            mp.put("error","repetitive username");
            System.out.println("456");
            return mp;
        }
        System.out.println("123");
        UserInfo userInfo=new UserInfo();
        userInfo.setUSER_ID(User.generaterID());
        userInfo.setUSER_NAME(username);
        userInfo.setUSER_PWD(password);
        userInfoDao.insert(userInfo);
        mp.put("status","success");
        mp.put("error","");
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
        userInfo.setUSER_PWD(newPWD);
        userInfoDao.update(userInfo);
        mp.put("status","success");
        mp.put("error","");
        return mp;
    }

}
