package webservice;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/10/18 19:06
 */
public class RestTemp {


    public static void main(String[] args) {
        restTemplate(WebServiceTest.getParams());
    }
    public static void restTemplate(String soap) {

        String url = "http://192.168.10.80:8183/pacsChannel?wsdl";
        String opName = "invoke";

        String targetNamespace = "http://serv.pacs.senyint.com/";

        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("SOAPAction", targetNamespace + "invoke");
        headers.add("Content-Type", "text/xml;charset=UTF-8");
        org.springframework.http.HttpEntity<String> entity = new org.springframework.http.HttpEntity<>(soap, headers);
        ResponseEntity<String> response = template.postForEntity(url, entity, String.class);
        if (response.getStatusCode() == org.springframework.http.HttpStatus.OK) {
            String back = template.postForEntity(url, entity, String.class).getBody();
            System.out.println("restTemplate返回soap：" + back);
            // System.out.println("restTemplate返回结果：" + parseResult(back));
        } else {
            System.out.println("restTemplate返回状态码：" + response.getStatusCode());
        }
    }

    private static String parseResult(String s) {
        String result = "";
        try {
            Reader file = new StringReader(s);
            SAXReader reader = new SAXReader();

            Map<String, String> map = new HashMap<String, String>();
            map.put("ns", "http://webxml.com.cn/");
            reader.getDocumentFactory().setXPathNamespaceURIs(map);
            Document dc = reader.read(file);
            result = dc.selectSingleNode("//ns:toTraditionalChineseResult").getText().trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}
