package socketServer;

import entity.User;
import entity.UserInfo;
import log.Logger;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * websocket的链接建立是基于http握手协议，我们可以添加一个拦截器处理握手之前和握手之后过程
 *
 */
@Component
public class PokerHandshakeInterceptor implements HandshakeInterceptor {


    @Resource(name = "usersCache")
    HashMap<String,User> usersCache;
    @Resource(name = "usersInfoCache")
    HashMap<String, UserInfo> usersInfoCache;

    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {


        Map<String,String>para=getPara(serverHttpRequest.getURI().toString());
        String userID;
        if((userID=para.get("user"))==null) {
            Logger.log("user is null!");
            return false;
        }
        if(!usersCache.containsKey(userID)) {
            Logger.log("userID "+userID+" is not login");
            return false;
        }
        User user=usersCache.get(userID);
        map.put("user",user);
        Logger.log("  Handshake   "+user.getID());
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
        Logger.log("--------------握手成功...");
    }

    private static Map<String,String> getPara(String uri){

        String para=uri.substring(uri.lastIndexOf('?')+1);
        String[] pari=para.split("&");
        Map<String,String>map=new HashMap<>();
        Arrays.stream(pari).forEach((s -> {
            int index=s.indexOf('=');
            map.put(s.substring(0,index),s.substring(index+1));
        }));
        return map;
    }

//    private Map<String,User>userMap=new HashMap<>();

}
