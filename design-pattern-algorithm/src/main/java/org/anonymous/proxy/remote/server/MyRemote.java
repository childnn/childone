package org.anonymous.proxy.remote.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/10 17:35
 */
public interface MyRemote extends Remote {
    String sayHello() throws RemoteException;
}
