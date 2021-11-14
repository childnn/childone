package org.anonymous.ws.boot;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/4/26 18:38
 * client 建立连接时的拦截器, 比 {@link org.springframework.web.socket.WebSocketHandler} 先执行
 * 只会在建立连接(handshake)时执行. 收发消息不会执行
 */
public class MyHandShakeInterceptor implements HandshakeInterceptor {

    private static volatile AtomicInteger ONLINE_COUNTER = new AtomicInteger(0);

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        // attributes.put("name", request.)
        Map<String, String[]> params = ((ServletServerHttpRequest) request).getServletRequest().getParameterMap();
        params.forEach((k, v) -> System.out.println(k + ": " + Arrays.toString(v)));
        System.out.println("attrs");
        attributes.forEach((k, v) -> System.out.println(k + ": " + v));

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
        System.out.println(wsHandler.getClass());
        System.out.println("握手成功了.....");
    }

}
