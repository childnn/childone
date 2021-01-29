package org.anonymous.netty.url;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/1/26 14:21
 * 未测试.
 */
public class GetPost {

    /**
     * 向指定 URL 发送 GET 请求
     *
     * @param url   目的 URL
     * @param param request-string, name1=value1&name2=value2
     * @return response from the URL
     */
    public static String sendGet(String url, String param) throws IOException {
        String urlWithRequestString = url + "?" + param;

        final URL sendUrl = new URL(urlWithRequestString);
        final URLConnection conn = urlCommonSettings(sendUrl);
        // 建立实际的连接
        conn.connect();
        // 获取所有的响应头字段
        final Map<String, List<String>> headerFields = conn.getHeaderFields();
        headerFields.forEach((k, v) -> System.out.println(k + "---->" + v));

        final BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        conn.getInputStream(), StandardCharsets.UTF_8));

        StringBuilder result = new StringBuilder();
        String line;
        while (null != (line = br.readLine())) {
            result.append("\n").append(line);
        }

        return result.toString();
    }

    private static URLConnection urlCommonSettings(URL sendUrl) throws IOException {
        final URLConnection conn = sendUrl.openConnection();
        conn.setRequestProperty("Accept", "*/*");
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.163 Safari/537.36");
        return conn;
    }

    public static String sendGet(String url, Map<String, String> paramMap) throws IOException {
        StringBuilder sb = new StringBuilder();
        final Set<String> keys = paramMap.keySet();
        final Iterator<String> keyIter = keys.iterator();
        while (keyIter.hasNext()) {
            final String key = keyIter.next();
            sb.append(key).append("=").append(paramMap.get(key));
            if (keyIter.hasNext()) {
                sb.append("&");
            }
        }
        // return sb.toString(); // test
        return sendGet(url, sb.toString());
    }

    static public String sendPost(String url, String param) throws IOException {
        final URL postUrl = new URL(url);
        final URLConnection conn = urlCommonSettings(postUrl);

        // POST 请求必须设置如下两行
        conn.setDoInput(true);
        conn.setDoOutput(true);

        try (
                final PrintWriter out = new PrintWriter(conn.getOutputStream())
        ) {
            // 发送请求参数
            out.print(param);

            // flush
            out.flush();
        }

        StringBuilder result = new StringBuilder();
        try (
                // response
                final BufferedReader br = new BufferedReader(
                        new InputStreamReader(
                                conn.getInputStream(), StandardCharsets.UTF_8))
        ) {
            String line;
            while (null != (line = br.readLine())) {
                result.append("\n").append(line);
            }
        }

        return result.toString();
    }

    @SuppressWarnings("serial")
    public static void main(String[] args) throws IOException {
        System.out.println(sendGet(null, new HashMap<String, String>() {{
            put("aaa", "bbb");
            put("bbb", "hhh");
            put("ccc", "ddd");
        }}));
    }


}
