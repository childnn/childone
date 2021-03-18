package org.anonymous.netty.proxy;

import java.io.IOException;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.Scanner;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/2/1 10:30
 * 关于代理服务器的常用属性名: {@link sun.net.spi.DefaultProxySelector}
 * http.proxyHost: 设置 HTTP 访问所使用的代理服务器的主机地址. 该属性名的前缀可以改为 https, ftp 等,
 *      分别用于设置 HTTPS 访问和 FTP 访问所用的代理服务器的主机地址.
 * http.proxyPort: 设置 HTTP 访问所使用的代理服务器的端口. 该属性名的前缀可以改为 https, ftp 等,
 *      分别用于设置 HTTPS 访问和 FTP 访问所用的代理服务器的端口.
 * http.nonProxyHost: 设置 HTTP 访问中不需要使用代理服务器的主机, 支持使用 * 通配符;
 *      支持指定多个地址, 多个地址用竖线(|) 分隔.
 */
public class DefaultProxySelectorTest {

    static String urlStr = "http://www.anonymous.org";

    public static void main(String[] args) throws URISyntaxException, IOException {
        // 获取系统的默认属性
        final Properties props = System.getProperties();
        // 通过系统属性设置 HTTP 访问所用的代理服务器的 HOST:PORT
        props.setProperty("http.proxyHost", "192.168.110.11");
        props.setProperty("http.proxyPort", "8080");

        // 通过系统属性设置 HTTP 访问无需使用代理服务器的主机
        // 可以使用 * 通配符, 多个地址用 | 分隔
        props.setProperty("http.nonProxyHosts", "localhost|192.168.110.*");
        // 通过系统属性设置 HTTPS 访问所用的代理服务器的 HOST:PORT
        props.setProperty("https.proxyHost", "192.168.110.96");
        props.setProperty("https.proxyPort", "443");

        /*
        * DefaultProxySelector 不支持 https.nonProxyHosts 属性
        * DefaultProxySelector 直接按 http.nonProxyHosts 的设置规则处理
        * */
        props.setProperty("ftp.proxyHost", "192.168.101.96");
        props.setProperty("ftp.proxyPort", "2121");
        // 通过系统属性设置 FTP 访问无需使用代理服务器的主机
        props.setProperty("ftp.nonProxyHosts", "localhost|192.168.110.*");
        // 通过系统属性设置 SOCKS 代理服务器的主机地址, 端口
        props.setProperty("socks.ProxyHost", "192.168.101.96");
        props.setProperty("socks.ProxyPort", "1080");
        // 获取系统默认的代理服务器
        final ProxySelector selector = ProxySelector.getDefault(); //
        System.out.println("系统默认的代理选择器: selector = " + selector);

        // 根据 URI 动态决定所使用的代理服务器
        System.out.println("系统 ftp://www.anonymous.org 选择的代理服务器为: " +
                selector.select(new URI("ftp://www.anonymous.org")));

        final URL url = new URL(urlStr);
        // 直接打开连接, 默认的代理选择器会使用 http.proxyHost, http.proxyPort 系统属性
        // 设置的代理服务器
        // 如果无法连接代理服务器, 则默认的代理服务器会尝试直接连接
        final URLConnection conn = url.openConnection();
        conn.setConnectTimeout(3000);

        final Scanner sc = new Scanner(conn.getInputStream(), StandardCharsets.UTF_8.name());
        while (sc.hasNextLine()) {
            System.out.println(sc.nextLine());
        }
    }

}
