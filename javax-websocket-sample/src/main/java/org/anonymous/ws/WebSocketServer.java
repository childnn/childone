package org.anonymous.ws;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/4/26 16:45
 * @see javax.websocket.OnOpen
 * @see javax.websocket.OnMessage
 * @see javax.websocket.OnClose
 * @see javax.websocket.OnError
 */
// @Component
// @ServerEndpoint("/test/ws/{name}")
public class WebSocketServer {

    private String name;
    private Session session;

    /**
     * .@see org.apache.tomcat.websocket.server.WsPerSessionServerEndpointConfig
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig ec, @PathParam("name") String name) {
        this.name = name;
        this.session = session;
        System.out.println(ec.getClass());
        System.out.println(session.getId());

        WebSocketUtils.SERVER_CLIENTS.put(session.getId(), this);
    }

    @OnMessage
    public String onMessage(Session session, String msg) {
        String m = name + ": " + msg;
        System.out.println(name + ": " + msg);
        WebSocketUtils.sendAllMessage(msg);
        return m;
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        String id = session.getId();
        WebSocketUtils.SERVER_CLIENTS.remove(id);
        System.out.printf("Session %s closed because of %s%n", id, closeReason);
    }

    @OnError
    public void onError(Throwable e, Session session) {
        System.out.println(session.getId() + "-error: ");
        e.printStackTrace();
    }

    public String getName() {
        return name;
    }

    public Session getSession() {
        return session;
    }
}
