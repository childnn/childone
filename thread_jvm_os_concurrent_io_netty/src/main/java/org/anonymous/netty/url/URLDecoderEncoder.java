package org.anonymous.netty.url;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see java.net.URLDecoder#decode(String, String) 解码
 * @see java.net.URLEncoder#encode(String, String) 将普通字符串转换成 application/x-www-form-urlencoded MIME 字符串
 * @since 2021/1/26 9:14
 */
public class URLDecoderEncoder {

    // UTF-8: 一个中文 3 个字节
    // GBK: 2 个字节
    public static void main(String[] args) throws UnsupportedEncodingException {
        // final String charsetName = StandardCharsets.UTF_8.name();
        final String charsetName = "GBK";
        method(charsetName);
    }

    private static void method(String charsetName) throws UnsupportedEncodingException {
        // Translates a string into {@code application/x-www-form-urlencoded}
        //     format using a specific encoding scheme.
        final String encoder = URLEncoder.encode("abc中文", charsetName);
        System.out.println(encoder);

        final String decode = URLDecoder.decode(encoder, charsetName);
        System.out.println(decode);
    }

}
