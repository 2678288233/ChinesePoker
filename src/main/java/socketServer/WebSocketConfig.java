package socketServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 *
 * 说明：WebScoket配置处理器
 * 把处理器和拦截器注册到spring websocket中
 */
@Component("webSocketConfig")
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    public PokerWebSocketHandler getWebSocketHandler() {
        return webSocketHandler;
    }
    @Autowired
    public void setWebSocketHandler(PokerWebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    public PokerHandshakeInterceptor getPokerHandshakeInterceptor() {
        return pokerHandshakeInterceptor;
    }
    @Autowired
    public void setPokerHandshakeInterceptor(PokerHandshakeInterceptor pokerHandshakeInterceptor) {
        this.pokerHandshakeInterceptor = pokerHandshakeInterceptor;
    }



    PokerWebSocketHandler webSocketHandler;

    PokerHandshakeInterceptor pokerHandshakeInterceptor;

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //添加一个处理器还有定义处理器的处理路径
        registry.addHandler(webSocketHandler, "/ws").addInterceptors(pokerHandshakeInterceptor);
        /*
         * SockJS能根据浏览器能否支持websocket来提供三种方式用于websocket请求，
         * 三种方式分别是 WebSocket, HTTP Streaming以及 HTTP Long Polling
         */
        registry.addHandler(webSocketHandler, "/ws/sockjs").addInterceptors(pokerHandshakeInterceptor).withSockJS();
    }

}
