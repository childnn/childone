package test;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2022/03/29
 */
public class CodeConvert {

    // 1)在将字符串转为16进制之前先进行一次转化,先将其转化成为Unicode编码(相当于把中文用英文字符代替),在转化成为16进制
    // 2)相反的,在十六进制转换为字符串后的得到的是Unicode编码,此时再将Unicode编码解码即可获取原始字符串

    @Test
    public void test1() {
        // 字符串转化为Unicode编码:
        String s = "中文";
        System.out.println(string2Unicode(s));
        System.out.println(strTo16(s));

        System.out.println(unicode2String(string2Unicode(s)));
        System.out.println(str2HexStr(s));

        // System.out.println(hexStringToString("0xCAFEBABE"));
        // System.out.println(hexStr2Str("0xCAFEBABE"));
        System.out.println(hexStr2Str("0xCAFEBABE"));
    }

    @Test
    public void test2() {
        log("ACC_PUBLIC", 0x0001);
        log("ACC_FINAL", 0x0010);
        log("ACC_SUPER", 0x0020);
        log("ACC_INTERFACE", 0x0200);
        log("ACC_ABSTRACT", 0x0400);
        log("ACC_SYNTHETIC", 0x1000);
        log("ACC_ANNOTATION", 0x2000);
        log("ACC_ENUM", 0x4000);
        // int i = 0x0001;
        // System.out.println("ACC_PUBLIC = " + i);
        // i = 0x0010;
        // System.out.println("ACC_FINAL = " + i);
        // i = 0x0020;
        // System.out.println("ACC_SUPER = " + i);
        // i = 0x0200;
        // System.out.println("ACC_INTERFACE = " + i);
        //
        // i = 0x0200;
        // System.out.println("ACC_INTERFACE = " + i);

        String s = "0x0001";
    }

    static void log(String modifier, int i) {
        System.out.printf("%s = %s%n", modifier, i);
    }

    /**
     * 字符串转换unicode
     */
    public static String string2Unicode(String string) {
        StringBuilder unicode = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            // 取出每一个字符
            char c = string.charAt(i);
            // 转换为unicode
            unicode.append("\\u").append(Integer.toHexString(c).toUpperCase(Locale.ROOT));
        }
        return unicode.toString();
    }

    /**
     * 字符串转化成为16进制字符串
     */
    public static String strTo16(String s) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            int ch = s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str.append(s4);
        }
        return str.toString();
    }

    /**
     * unicode 转字符串
     */
    public static String unicode2String(String unicode) {
        StringBuilder string = new StringBuilder();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            // 转换出每一个代码点
            int data = Integer.parseInt(hex[i], 16);
            // 追加成string
            string.append((char) data);
        }
        return string.toString();
    }

    /**
     * 16进制转换成为string类型字符串
     */
    public static String hexStringToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, StandardCharsets.UTF_8);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

    /**
     * 字符串转换成为16进制(无需Unicode编码)
     * ????
     */
    public static String str2HexStr(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder();
        byte[] bs = str.getBytes();
        int bit;
        for (byte b : bs) {
            bit = (b & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = b & 0x0f;
            sb.append(chars[bit]);
            sb.append(' ');
        }
        return sb.toString().trim();
    }

    /**
     * 16进制直接转换成为字符串(无需Unicode解码)
     */
    public static String hexStr2Str(String hexStr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;
        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }

}
