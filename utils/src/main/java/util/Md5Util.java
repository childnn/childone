package util;


import java.security.MessageDigest;

public class Md5Util {

    /**
     * 通过盐值对字符串进行MD5加密
     *
     * @param param
     *         需要加密的字符串
     * @param salt
     *         盐值
     * @return
     */
    public static String MD5(String param, String salt) {
        return MD5(param + salt);
    }

    /**
     * 加密字符串
     *
     * @param s
     *         字符串
     * @return
     */
    public static String MD5(String s) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = s.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String zzzz00 = MD5("zzzz00");
        System.out.println("zzzz00 = " + zzzz00);
    }
}
