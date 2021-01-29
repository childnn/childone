package org.anonymous.netty.charset;

import java.nio.charset.Charset;
import java.util.SortedMap;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/1/21 16:46
 */
public class CharsetTest {

    public static void main(String[] args) {
        final String fileEncoding = System.getProperty("file.encoding");
        System.out.println("fileEncoding = " + fileEncoding);
        final SortedMap<String, Charset> charsets = Charset.availableCharsets();
        charsets.forEach((k, v) -> System.out.println(k + ": " + v + ": " + v.aliases()));
    }

}
