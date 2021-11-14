package org.anonymous.proxy.remote.client;

import org.anonymous.proxy.remote.server.MyRemote;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/10 17:39
 */
public class MyRemoteClient {
    public static void main(String[] args) {
        MyRemoteClient client = new MyRemoteClient();
        client.go();

        client.go1();
    }

    // JDK 1.3+ 调用方式.
    private void go1() {
        try {
            Context namingContext = new InitialContext();
            MyRemote remote = (MyRemote) namingContext.lookup("rmi://localhost/RemoteHello");
            String s = remote.sayHello();
            System.out.println("s = " + s);
        } catch (NamingException | RemoteException e) {
            e.printStackTrace();
        }
    }

    // JDK 1.3 版本或更低版本.
    private void go() {
        try {
            // 查找指定 ip/<name> 的远程对象.
            Remote service = Naming.lookup("rmi://localhost/RemoteHello");
            // Naming.list() // 获取所有可用的远程对象.
            String s = ((MyRemote) service).sayHello();
            System.out.println(s);
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }
    }


}
