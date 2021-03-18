package org.anonymous.netty.proxy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see org.anonymous.netty.proxy.ProxyTest 直接使用 Proxy 对象可以打开 URLConnection 或 Socket 时指定代理服务器,
 * 但使用这种方式每次打开连接时都需要显式地设置代理服务器, 比较麻烦. 如果希望每次打开连接时总是具有默认的代理服务器, 可以借助
 * ProxySelector 来实现.
 * @see java.net.ProxySelector#select(java.net.URI) 根据业务需要返回代理服务器列表, 如果该方法返回的集合中只包含一个 proxy,
 * 该 Proxy 将会作为默认的代理服务器.
 * @see java.net.ProxySelector#connectFailed(java.net.URI, java.net.SocketAddress, java.io.IOException)
 * 连接代理服务器失败时回调方法.
 * 默认的代理服务器重写了 connectFailed 方法, 它重写该方法的处理策略是: 当系统设置的代理服务器失败时, 默认代理选择器将会
 * 采用直连的方式连接远程资源. {@link sun.net.spi.DefaultProxySelector}
 * @see java.net.ProxySelector#getDefault()
 * --
 * 可根据需要, 实现自己的 ProxySelecotr, 调用 {@link java.net.ProxySelector#setDefault(java.net.ProxySelector)}
 * 静态方法来注册代理选择器即可.
 * @since 2021/2/1 10:10
 */
public class ProxySelectorTest {

    // 下面是代理服务器的地址和端口
    final String PROXY_ADDR = "139.82.12.188";
    final int PROXY_PORT = 1324;

    String urlStr = "http://www.crazyit.org";

    InetSocketAddress addr = new InetSocketAddress(PROXY_ADDR, PROXY_PORT);

    void init() throws IOException {
        ProxySelector.setDefault(new ProxySelector() {
            /**
             * 实现方法, 返回一个固定的代理服务器, 也就是说, 程序默认总会使用该代理服务器.
             * 因此程序在 {@code final URLConnection conn = url.openConnection();}
             * 打开连接时虽然没有指定代理服务器, 但实际上程序依然会使用代理服务器
             * 如果用户设置一个无效的代理服务器, 系统将会在连接失败时回调 {@link java.net.ProxySelector#connectFailed(java.net.URI, java.net.SocketAddress, java.io.IOException)}
             * 这可以说明代理选择器起作用了.
             */
            @Override
            public List<Proxy> select(URI uri) {
                // 本程序总是返回某个固定的代理服务器
                final ArrayList<Proxy> result = new ArrayList<>();
                result.add(new Proxy(Proxy.Type.HTTP, addr));

                return result;
            }

            @Override
            public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
                System.out.println("无法连接到指定代理服务器!");
            }
        });

        final URL url = new URL(urlStr);
        // 没有指定代理服务器, 直接打开连接
        // 实际上还是会使用代理服务器.
        final URLConnection conn = url.openConnection();
        // ....
    }

}
