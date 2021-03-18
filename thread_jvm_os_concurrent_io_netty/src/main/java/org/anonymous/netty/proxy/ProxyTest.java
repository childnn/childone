package org.anonymous.netty.proxy;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/2/1 8:53
 * @see java.net.Proxy 代理服务器
 * @see java.net.ProxySelector 代理选择器, 提供了对代理服务器更加灵活的控制
 *      可以对 HTTP, HTTPS, FTP, SOCKS 等进行分别配置, 而且还可以设置不需要通过代理服务器的主机和地址.
 *      通过使用 ProxySelector, 可以实现像在 IE, FireFox 等软件中设置代理服务器类似的效果.
 * 代理服务器的功能就是代理用户去取得网络信息. 当使用浏览器直接连接其他 Internet 站点取得网络信息时, 通常需要先发送请求,
 * 然后等响应到来. 代理服务器是介于浏览器和服务器之间的一台服务器, 设置了代理服务器之后, 浏览器不是直接向 Web 服务器发送请求,
 * 而是向代理服务器发送请求, 浏览器请求被先送到代理服务器, 有代理服务器向真正的 Web 服务器发送请求, 并取回浏览器所需要的信息,
 * 再送回给浏览器. 由于大部分代理服务器都具有缓冲功能, 它会不断地将新取得地数据存储到代理服务器的本地存储器上, 如果浏览器所请求的
 * 数据在它本机的存储器上已经存在而且是最新的, 那么它就无须从 Web 服务器取数据, 而直接将本地存储器上的数据送回浏览器, 这样能显著提高
 * 浏览速度.
 * 归纳起来, 代理服务器主要提供如下两个功能:
 *  1. 突破自身 IP 限制, 对外隐藏自身 IP 地址. 突破 IP 限制包括访问国外受限站点(正向代理), 访问国内特定单位, 团体的内部资源.
 *  2. 提高访问速度, 代理服务器提供的缓冲功能可以避免每个用户都直接访问远程主机, 从而提高客户端访问速度.
 * --
 * @see java.net.Proxy#Proxy(java.net.Proxy.Type, java.net.SocketAddress)
 * 参数一: 代理服务器的类型
 *     {@link java.net.Proxy.Type#DIRECT} 直连, 不使用代理
 *     {@link java.net.Proxy.Type#HTTP} 支持高级协议代理, 如 HTTP/FTP
 *     {@link java.net.Proxy.Type#SOCKS} 表示 SOCKS(v4/v5) 代理
 * 参数二: 代理服务器的地址
 * 一旦创建了 Proxy 对象之后, 程序就可以在使用 URLConnection 打开连接时, 或者创建 Socket 连接时传入一个 Proxy 对象,
 * 作为本次连接所使用的代理服务器.
 * @see java.net.URL#openConnection(java.net.Proxy) 该方法使用指定的代理服务器来打开连接; 而 Socket 则提供了一个
 *  {@link java.net.Socket#Socket(java.net.Proxy)} 构造方法, 该方法使用指定的代理服务器创建一个没有连接的 Socket 对象.
 *
 */
public class ProxyTest {

    // 请替换为实际有效的 HOST:PORT
    final String PROXY_ADDR = "129.82.12.188";
    final int PROXY_PORT = 1234;

    // 定义需要访问的网站地址
    String urlStr = "http://www.google.com";

    void init() throws IOException {
        final URL url = new URL(urlStr);
        // 创建一个代理服务器对象
        final Proxy proxy = new Proxy(Proxy.Type.HTTP,
                new InetSocketAddress(PROXY_ADDR, PROXY_PORT));
        // 使用指定的代理服务器打开连接
        final URLConnection conn = url.openConnection(proxy);
        // 设置超时时长
        conn.setConnectTimeout(3000);

        // 通过代理服务器读取数据的 Scanner
        final Scanner sc = new Scanner(conn.getInputStream());
        final PrintStream ps = new PrintStream("index.htm");

        while (sc.hasNextLine()) {
            final String line = sc.nextLine();
            // 在控制台输出网页资源内容
            System.out.println(line);
            // 将网页资源内容输出到指定输出流
            ps.println(line);
        }
    }

    public static void main(String[] args) throws IOException {
        new ProxyTest().init();
    }

}
