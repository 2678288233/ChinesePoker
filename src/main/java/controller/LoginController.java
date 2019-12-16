package controller;

import entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
//@RequestMapping("/login")
public class LoginController {

    public Map<String, User> getCookieUserMap() {
        return cookieUserMap;
    }

    public void setCookieUserMap(Map<String, User> cookieUserMap) {
        this.cookieUserMap = cookieUserMap;
    }
    @Resource(name="cookieUserMap")
    Map<String, User> cookieUserMap;

    @RequestMapping("/login")
    public String Login(){
        System.out.println("login");
        return "login";
    }

    @RequestMapping("/loginhandle")
    public String LoginHandle(HttpServletRequest request, HttpServletResponse response)  {
        HttpSession session = request.getSession();
        String id=request.getParameter("id");
        String pwd=request.getParameter("password");
        Cookie cookie=getCookie("id",id);

        response.addCookie(cookie);

        //System.out.println(cookieUserMap);
        User user=new User();
        user.setID("123");
        cookieUserMap.put(id,user);
        session.setAttribute("user",user);
        //response.sendRedirect("/index");
        System.out.println("loginhandle");
        return "redirect:index";
    }

    @RequestMapping("/register")
    public String Register(){
        return "index";
    }

    @RequestMapping("/registerhandle")
    public String RegisteHandeler(){

        return "index";
    }

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    private Cookie getCookie(String name,String value){
        Cookie cookie=new Cookie(name,value);
        //cookie.setMaxAge(60*60*3);
        cookie.setPath("/");
        //cookie.setDomain("127.0.0.1");
        //cookie.setPath("127.0.0.1");
        return cookie;
    }
}
