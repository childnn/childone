package test.emoji;

import com.vdurmont.emoji.EmojiParser;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.web.util.HtmlUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/24 17:27
 */
public class EmojiTest {
    @Test
    public void test() {
        String s = EmojiParser.parseToUnicode("😀秋香");
        System.out.println(s);
        String s1 = EmojiParser.parseToAliases("😀");
        System.out.println("s1 = " + s1);
        String s2 = EmojiParser.parseToUnicode("😀秋香");
        System.out.println("s2 = " + s2);
        String s3 = EmojiParser.parseToHtmlDecimal("😀秋香");
        System.out.println("s3 = " + s3);
        String s7 = EmojiParser.parseToHtmlHexadecimal("😀");
        System.out.println("s7 = " + s7);


        String s5 = HtmlUtils.htmlEscape("😀秋香", StandardCharsets.UTF_8.name());
        System.out.println("s5 = " + s5);
        String s4 = HtmlUtils.htmlUnescape(s7);
        System.out.println("s4 = " + s4);
    }

    @Test
    public void test3() throws UnsupportedEncodingException {
        String encode = URLEncoder.encode("😀", StandardCharsets.UTF_8.name());
        System.out.println("encode = " + encode);
        String decode = URLDecoder.decode(encode, StandardCharsets.UTF_8.name());
        System.out.println("decode = " + decode);

    }

    @Test
    public void test1() {
        byte[] bytes = Base64.encodeBase64("😀秋香".getBytes());
        String s1 = Arrays.toString(bytes);
        System.out.println("s1 = " + s1);
        String s = new String(bytes);
        System.out.println("s = " + s);

        byte[] bytes1 = Base64.decodeBase64(s.getBytes());
        String s2 = new String(bytes1);
        System.out.println("s2 = " + s2);
    }
}
