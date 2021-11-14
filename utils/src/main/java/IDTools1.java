import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import util.HttpUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/7/26 9:17
 */
public class IDTools1 {
    public static void main(String[] args) {

        String appcode = "dc2dfe88a22240a3af13363f5c28d7d3"; // 【3】开通服务后 买家中心-查看AppCode
        String idCard = "ssss";// 【4】请求参数，详见文档描述
        String name = "sss";// 【4】请求参数，详见文档描述


        String host = "https://idcard.market.alicloudapi.com";
        String path = "/lianzhuo/idcard";
        String method = "GET";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("cardno", idCard);
        querys.put("name", name);


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doGet(host, path, headers, querys);
            System.out.println(response.toString());
            //获取response的body
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
