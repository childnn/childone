package org.anonymous.ws.boot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/4/26 18:48
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myHandler(), "/test/ws/{name}") // @ServerEndpoint("/test/ws/{name}") 如果没有动态参数, 必须以 / 结尾
                .setAllowedOrigins("*") // 支持跨域
                // .setHandshakeHandler()
                .addInterceptors(handshakeInterceptor()); // 拦截器
                // .withSockJS(); // 支持 socket.js
    }

    @Bean
    public WebSocketHandler myHandler() {
        return new MyMsgHandler();
    }

    @Bean
    public HandshakeInterceptor handshakeInterceptor() {
        return new MyHandShakeInterceptor();
    }

}
