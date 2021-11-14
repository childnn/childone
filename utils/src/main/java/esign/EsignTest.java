package esign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import util.HttpUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/8/5 19:11
 */
public class EsignTest {

    // GET https://smlopenapi.esign.cn/v1/oauth2/access_token?appId=4438870119&secret=42fa7a18b451a4dabe7ed0937034371e&grantType=client_credentials
    @Test
    public void token() throws Exception {
        String host = "https://smlopenapi.esign.cn";
        String path = "/v1/oauth2/access_token";
        Map<String, String> params = new HashMap<String, String>() {{
            put("appId", "4438870119");
            put("secret", "42fa7a18b451a4dabe7ed0937034371e");
            put("grantType", "client_credentials");
        }};
        HttpResponse resp = HttpUtils.doGet(host, path, null, params);
        System.out.println(EntityUtils.toString(resp.getEntity()));
    }

    // 应用ID
    static String appId = "4438870119";
    // 应用密钥
    static String appKey = "42fa7a18b451a4dabe7ed0937034371e";
    // 接口调用域名
    static String host = "https://smlopenapi.esign.cn";
    // 个人账户ID
    static String accountId = "4c66c987eXXXc2eb474b5abc54";

    /**
     * 应用ID
     * 4438870119
     *
     * 应用secret
     * 42fa7a18b451a4dabe7ed0937034371e
     *
     * corpid：
     * dingb21110b22051f51a35c2f4657eb6378f
     *
     * 然后调用接口顺序是
     * 1、获取扫描登录授权URL地址：将返回的url生成二维码
     * 2、查询扫码登录结果：获取到用户工号
     * 3、查询扫码授权结果：获取到授权码
     * 4、数据签署：传入授权码，返回签署结果
     * 5、查询个人印章：获得医生的签名图片
     */
    public static void main(String[] args) {


        // 请求签名鉴权-POST请求
        testPost(appId, appKey, host);

        // 请求签名鉴权-GET请求
        // testGet(appId, appKey, host, accountId);

    }

    @Test
    public void caSig() throws Exception {
        String url = "https://smlopenapi.esign.cn/v1/accounts/createByThirdPartyUserId";

        JSONObject reqBodyObj = new JSONObject();
        // reqBodyObj.put("thirdPartyUserId", "20210431");
        reqBodyObj.put("name", "缪万");
        // reqBodyObj.put("idType", "CRED_PSN_CH_IDCARD");
        // reqBodyObj.put("idNumber", "9527");
        // reqBodyObj.put("mobile", "13163249276");
        // reqBodyObj.put("email", "13163249276@163.com");

        String contentMD5 = doContentMD5(reqBodyObj.toString());
        System.out.println("contentMD5 = " + contentMD5);
        // 构建待签名字符串
        String method = "POST";
        String accept = "*/*";
        String contentType = "application/json; charset=UTF-8";
        String date = "" + timeStamp();
        String headers = generateHeaders() ;
        StringBuilder sb = new StringBuilder();
        sb.append(method).append("\n")
                .append(accept).append("\n")
                .append(contentMD5).append("\n")
                .append(contentType).append("\n")
                .append(date).append("\n");
        if ("".equals(headers)) {
            sb.append(headers).append(url);
        } else {
            sb.append(headers).append("\n").append(url);
        }
        System.out.println(doSignatureBase64(sb.toString(), appKey));
    }

    /***
     * 请求签名鉴权-POST请求
     *
     */
    public static void testPost(String appId, String appKey, String host) {
        // 个人创建账号接口地址
        String accountsApi = "/v1/accounts/createByThirdPartyUserId";
        // 个人创建账号接口请求地址
        String accountsApiUrl = host + accountsApi;

        try {
            // 构建请求Body体
            JSONObject reqBodyObj = new JSONObject();
            reqBodyObj.put("thirdPartyUserId", "20210431");
            reqBodyObj.put("name", "缪万");
            reqBodyObj.put("idType", "CRED_PSN_CH_IDCARD");
            reqBodyObj.put("idNumber", "9527");
            reqBodyObj.put("mobile", "13163249276");
            reqBodyObj.put("email", "13163249276@163.com");

            // 请求Body体数据
            String reqBodyData = reqBodyObj.toString();
            // 对请求Body体内的数据计算ContentMD5
            String contentMD5 = doContentMD5(reqBodyData);

            // 构建待签名字符串
            String method = "POST";
            String accept = "*/*";
            String contentType = "application/json; charset=UTF-8";
            String date = "";
            String headers = generateHeaders() ;

            /*
            其中HTTPMethod、Accept、Content-MD5、Content-Type、Date 如果为空也需要添加换行符”\n”，
            Headers如果为空不需要添加”\n”换行符。
            注：当请求body为空时，Content-MD5不参与待签名字符串进行签名，待签名字符串无需拼接 Content-MD5。
            String stringToSign=
                    HTTPMethod + "\n" +
                    Accept + "\n" +
                    Content-MD5 + "\n"
                    Content-Type + "\n" +
                    Date + "\n" +
                    Headers +
                    Url
             */
            StringBuilder sb = new StringBuilder();
            sb.append(method).append("\n")
                    .append(accept).append("\n")
                    .append(contentMD5).append("\n")
                    .append(contentType).append("\n")
                    .append(date).append("\n");
            if ("".equals(headers)) {
                sb.append(headers).append(accountsApiUrl);
            } else {
                sb.append(headers).append("\n").append(accountsApiUrl);
            }

            // 构建参与请求签名计算的明文
            String plaintext = sb.toString();
            // 计算请求签名值
            String reqSignature = doSignatureBase64(plaintext, appKey);

            // 获取时间戳(精确到毫秒)
            long timeStamp = timeStamp();

            // 构建请求头
            LinkedHashMap<String, String> header = new LinkedHashMap<>();
            header.put("X-Tsign-Open-App-Id", appId);
            header.put("X-Tsign-Open-Auth-Mode", "Signature");
            header.put("X-Tsign-Open-Ca-Timestamp", String.valueOf(timeStamp));
            header.put("Accept", accept);
            header.put("Content-Type", contentType);
            header.put("X-Tsign-Open-Ca-Signature", reqSignature);
            header.put("Content-MD5", contentMD5);

            // 发送POST请求
            String result = HTTPHelper.sendPOST(accountsApiUrl, reqBodyData, header, "UTF-8");
            JSONObject resultObj = JSON.parseObject(result);
            System.out.println("请求返回信息： " + resultObj.toString());
        } catch (Exception e) {
            e.printStackTrace();
            String msg = MessageFormat.format("请求签名鉴权方式调用接口出现异常: {0}", e.getMessage());
            System.out.println(msg);
        }
    }

    /***
     * 请求签名鉴权-GET请求
     *
     */
    public static void testGet(String appId, String appKey, String host, String accountId) {
        // 查询个人印章接口
        String getPersonSealsApi = "/v1/accounts/" + accountId + "/seals?offset=1&size=10";
        // 查询个人印章接口请求地址
        String getPersonSealsApi_Url = host + getPersonSealsApi;

        try {
            // GET请求时ContentMD5为""
            String contentMD5 = "";

            // 构建待签名字符串
            String method = "GET";
            String accept = "*/*";
            String contentType = "application/json; charset=UTF-8";
            String date = "";
            String headers = generateHeaders();

            StringBuilder sb = new StringBuilder();
            sb.append(method).append("\n")
                    .append(accept).append("\n")
                    .append(contentMD5).append("\n")
                    .append(contentType).append("\n")
                    .append(date).append("\n");
            if ("".equals(headers)) {
                sb.append(headers).append(getPersonSealsApi);
            } else {
                sb.append(headers).append("\n").append(getPersonSealsApi);
            }

            // 构建参与请求签名计算的明文
            String plaintext = sb.toString();
            // 计算请求签名值
            String reqSignature = doSignatureBase64(plaintext, appKey);

            // 获取时间戳(精确到毫秒)
            long timeStamp = timeStamp();

            // 构建请求头
            LinkedHashMap<String, String> header = new LinkedHashMap<>();
            header.put("X-Tsign-Open-App-Id", appId);
            header.put("X-Tsign-Open-Auth-Mode", "Signature");
            header.put("X-Tsign-Open-Ca-Timestamp", String.valueOf(timeStamp));
            header.put("Accept", accept);
            header.put("Content-Type", contentType);
            header.put("X-Tsign-Open-Ca-Signature", reqSignature);
            header.put("Content-MD5", contentMD5);

            // 发送GET请求
            String result = HTTPHelper.sendGet(getPersonSealsApi_Url, header);
            JSONObject resultObj = JSONObject.parseObject(result);
            System.out.println("请求返回信息： " + resultObj.toString());
        } catch (Exception e) {
            e.printStackTrace();
            String msg = MessageFormat.format("请求签名鉴权方式调用接口出现异常: {0}", e.getMessage());
            System.out.println(msg);
        }
    }

    // 无需对headers进行签名，建议传空值
    private static String generateHeaders() {
        return "";
    }

    /***
     * 加密请求 data
     * @param str 待计算的消息
     * @return MD5计算后摘要值的Base64编码(ContentMD5)
     * @throws Exception 加密过程中的异常信息
     */
    public static String doContentMD5(String str) throws Exception {
        String contentMD5;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md5.update(str.getBytes(StandardCharsets.UTF_8));
            // 获取文件MD5的二进制数组（128位）
            byte[] md5Bytes = md5.digest();
            // 把MD5摘要
            // 后的二进制数组md5Bytes使用Base64进行编码（而不是对32位的16进制字符串进行编码）
            contentMD5 = new String(Base64.encodeBase64(md5Bytes), StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException e) {
            String msg = MessageFormat.format("不支持此算法: {0}", e.getMessage());
            throw new Exception(msg, e);
        }
        return contentMD5;
    }

    /***
     * 计算请求签名值
     *
     * @param message 待计算的消息
     * @param secret 密钥
     * @return HmacSHA256计算后摘要值的Base64编码
     * @throws Exception 加密过程中的异常信息
     */
    public static String doSignatureBase64(String message, String secret) throws Exception {
        String algorithm = "HmacSHA256";
        Mac hmacSha256;
        String digestBase64;
        try {
            hmacSha256 = Mac.getInstance(algorithm);
            byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
            byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
            hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, algorithm));
            // 使用HmacSHA256对二进制数据消息Bytes计算摘要
            byte[] digestBytes = hmacSha256.doFinal(messageBytes);
            // 把摘要后的结果digestBytes转换成十六进制的字符串
            // String digestBase64 = Hex.encodeHexString(digestBytes);
            // 把摘要后的结果digestBytes使用Base64进行编码
            digestBase64 = new String(Base64.encodeBase64(digestBytes), StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException e) {
            String msg = MessageFormat.format("不支持此算法: {0}", e.getMessage());
            throw new Exception(msg, e);
        } catch (InvalidKeyException e) {
            String msg = MessageFormat.format("无效的密钥规范: {0}", e.getMessage());
            throw new Exception(msg, e);
        }
        return digestBase64;
    }

    /***
     * 获取时间戳(毫秒级)
     *
     * @return 毫秒级时间戳,如 1578446909000
     */
    public static long timeStamp() {
        return System.currentTimeMillis();
    }

    @Test // 1628211884564
         // 1628212506420
    public void ts() {
        System.out.println(timeStamp());
    }

}