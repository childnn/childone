package org.anonymous.ws;

import javax.websocket.Session;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/4/26 16:49
 */
public class WebSocketUtils {

    public static final Map<String, WebSocketServer> SERVER_CLIENTS = new ConcurrentHashMap<>();

    public static boolean sendMsg2User(final String name, final String msg) {
        try {
            SERVER_CLIENTS.values().forEach(s -> {
                if (Objects.equals(s.getName(), name)
                        && s.getSession() != null) {
                    s.getSession().getAsyncRemote().sendText(msg);
                }
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void sendAllMessage(String msg) {
        SERVER_CLIENTS.values().forEach(s -> {
            Session session = s.getSession();
            if (session != null && session.isOpen()) {
                session.getAsyncRemote().sendText(msg);
            }
        });
    }


}
