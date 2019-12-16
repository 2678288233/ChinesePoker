package socketServer;

import entity.User;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

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
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {


        Map<String,String>para=getPara(serverHttpRequest.getURI().toString());
        if(para.get("user")==null) return false;
        User user=new User();
        user.setID(para.get("user"));
        map.put("user",user);
        System.out.println(user.getID());


//        if (serverHttpRequest instanceof ServletServerHttpRequest) {
//            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) serverHttpRequest;
//            HttpSession session = servletRequest.getServletRequest().getSession(false);
//            if (session.getAttribute("user") != null) {
//                map.put("user",session.getAttribute("user"));
//                System.out.println("user login successfully");
//            } else {
//                System.out.println("user login failed");
//                //return false;
//            }
//        }

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
        System.out.println("--------------握手成功...");
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
}
