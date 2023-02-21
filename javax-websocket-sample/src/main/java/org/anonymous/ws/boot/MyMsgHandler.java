package org.anonymous.ws.boot;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see org.springframework.web.socket.WebSocketHandler 的各个方法对应 javax-websocket 的各个注解
 * @since 2021/4/26 18:44
 * 消息处理器, 收发消息
 */
public class MyMsgHandler extends TextWebSocketHandler {

    // 如果同一个人可以多地登录, 这里 value 可以用 Set 存储 session
    private static final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // super.handleTextMessage(session, message);
        System.out.println("获取到消息 >> " + getName(session)/*session.getId()*/ + ": " + message.getPayload());

        // 发送给建立连接的其他人
        sessions.forEach((id, se) -> {
            if (se.isOpen() && !se.getId().equals(session.getId())) {
                try {
                    se.sendMessage(new TextMessage(getName(session) /*+ session.getId()*/ + ": " + message.getPayload()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        // if (session.isOpen()) {
        //     session.sendMessage(new TextMessage("消息已收到"));
        // }
    }

    String getName(WebSocketSession session) throws UnsupportedEncodingException {
        URI u = session.getUri();
        // System.out.println("u = " + u);
        if (u == null) {
            return null;
        }
        String uri = u.toString();
        // return uri.substring(uri.lastIndexOf("/") + 1);
        String name = uri.substring(uri.lastIndexOf("/") + 1);
        return URLDecoder.decode(name, StandardCharsets.UTF_8.name());
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        // String s = "%E9%9F%A9%E6%A2%85%E6%A2%85";
        String s = "jack";
        // System.out.println(new String(s.getBytes(Charset.forName("unicode"))));
        System.out.println(URLDecoder.decode(s, StandardCharsets.UTF_8.name()));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // super.afterConnectionEstablished(session);
        String id = session.getId();
        // String uri = session.getUri().toString();
        // System.out.println("uri = " + uri);
        String name = getName(session); // uri.substring(uri.lastIndexOf("/") + 1);
        System.out.println("session.getUri() = " + session.getUri());
        System.out.println("session.getAttributes() = " + session.getAttributes());

        System.out.println(name + " " + id + " 连接成功!");
        sessions.put(id, session);
        Map<String, Object> attrs = session.getAttributes();
        attrs.forEach((k, v) -> System.out.println(k + "=" + v));
        session.sendMessage(new TextMessage(name + " 与服务器建立连接成功!"));

        sessions.forEach((i, se) -> {
            if (se.isOpen() && !i.equals(session.getId())) {
                try {
                    se.sendMessage(new TextMessage(getName(session)/*se.getId()*/ + " 建立连接....."));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        // super.afterConnectionClosed(session, status);
        System.out.println(session.getId() + "断开连接..." + status);
        // 移除 对应的 session
    }

}
