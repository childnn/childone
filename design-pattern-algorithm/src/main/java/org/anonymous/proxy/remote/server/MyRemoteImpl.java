package org.anonymous.proxy.remote.server;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/10 17:36
 */
public class MyRemoteImpl extends UnicastRemoteObject implements MyRemote {
    private static final long serialVersionUID = -6027076333206274338L;

    protected MyRemoteImpl() throws RemoteException {
    }

    @Override
    public String sayHello() throws RemoteException {
        return "Server says: 'Hey'";
    }

    public static void main(String[] args) {
        server(); // 1.3-
        server3(); // 1.3+
    }

    private static void server3() {
        try {
            MyRemote remote = new MyRemoteImpl(); // 创建远程对象.
            Context namingContext = new InitialContext(); // 初始化命名内容.
            LocateRegistry.createRegistry(10990);

            namingContext.rebind("rmi://localhost/RemoteHello", remote);
        } catch (RemoteException | NamingException e) {
            e.printStackTrace();
        }
    }

    private static void server() {
        try {
            /* JDK 1.3 或更低版本, 注册方式: 本地创建并启动 RMI Service, 被创建的 Registry 服务将在指定端口, 倾听请求,
                Java 默认端口是 1099, 缺少注册表创建, 则无法绑定对象到远程注册表上. */
            // 创建 RMI 注册表, 启动 RMI 服务, 并将远程对象注册到 RMI 注册表中.
            LocateRegistry.createRegistry(1099); // 这一行注释就报错: 必须要指定端口吗?

            // 创建远程对象, -- 生成 skeleton (服务端) 和 stub (客户端)
            MyRemote service = new MyRemoteImpl();
            Naming.rebind("RemoteHello", service);
        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
