package socketServer;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;


/**
 * websocket的链接建立是基于http握手协议，我们可以添加一个拦截器处理握手之前和握手之后过程
 *
 */
@Component
public class PokerHandshakeInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {

        if (serverHttpRequest instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) serverHttpRequest;
            HttpSession session = servletRequest.getServletRequest().getSession(false);
            System.out.println("in if");
            if (session.getAttribute("user") != null) {
                map.put("user",session.getAttribute("user"));
                System.out.println("user login successfully");
            } else {
                System.out.println("user login failed");
                return false;
            }
        }

        System.out.println("out");
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
        System.out.println("--------------握手成功...");
    }
}
