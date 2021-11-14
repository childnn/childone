package webservice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.Pacs;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import javax.xml.namespace.QName;
import java.util.Arrays;
import java.util.Date;

import static com.alibaba.fastjson.serializer.SerializerFeature.WriteNullNumberAsZero;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/10/18 16:35
 */
public class WS2 {

    /**
     * CXF 动态代理模式，不用生成本地WS代理类， * 通过反射调用 WS 的对应的方法，传入相应的参数 * 访问cxf-server-web项目下的webservice; * 测试jaxws-rt发布WebService web方式。 * 此测试实例，用于测试SEI和SIB的targetNamespace未指定的webService接口： * http://localhost:8080/cxf_server_web/jws_services?wsdl * @author donald * 2017年7月8日 * 下午7:24:12
     */
    // private static final String JWS_RT_WSDL_URI = "http://localhost:8080/cxf_server_web/jws_services?wsdl";

    public static void main(String[] args) throws Exception {
        String url = WebServiceTest.url;
        String opName = WebServiceTest.operationName;


        // send(url, opName);

        url = "http://192.168.10.80:8183/pacsChannel?wsdl";
        opName = "invoke";
        send(url, opName, "CheckStatusInfoUpdate");


        // WebServiceTest.webService2("http://192.168.10.80:8183/pacsChannel?wsdl", "receiveCheckApply"/*, WebServiceTest.getParams()*/);
    }

    private static void send(String url, String opName, String methodName) throws Exception {
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(url);
        HTTPConduit conduit = (HTTPConduit) client.getConduit();
        HTTPClientPolicy policy = new HTTPClientPolicy();
        policy.setConnectionTimeout(10000);
        policy.setAllowChunking(true);
        policy.setReceiveTimeout(10000);
        conduit.setClient(policy);            //获取操作对应的Qname
        QName operateQName = getOperateQName(client, opName);
        //如果Qname已知，可以通过如下方式，直接创建QName//
        // operateQName = new QName("http://serv.pacs.senyint.com/", WebServiceTest.operationName);
        String s1 = fastJson();
        String s2 = gson();
        System.out.println("s1 = " + s1);
        System.out.println("s2 = " + s2);

        Object[] params = {methodName/*"receiveCheckApply"*/, s1/*WebServiceTest.json()*/};
        Object[] invokeResult = client.invoke(operateQName, params);
        System.out.println("Arrays.toString(invokeResult) = " + Arrays.toString(invokeResult));
    }

    private static String fastJson() {
        Pacs pacs = JSON.parseObject(WebServiceTest.json(), Pacs.class);
        Pacs pacs1 = new Pacs();
        pacs1.setJianChaFlMc("PACS");
        pacs1.setJianChaDLFlMc("ppacs");
        pacs1.setJianChaSqdId("1231232");
        return JSON.toJSONString(pacs1, SerializerFeature.WriteNullStringAsEmpty, WriteNullNumberAsZero/*UseISO8601DateFormat*/);
    }

    public static String compact() {
        return "{\"jianChaSqdId\":\"2240791\",\"yuanQuId\":\"1\",\"bingRenId\":\"74378\",\"zhuYuanHao\":\"\",\"jiuZhenKh\":\"0000999\",\"menZhenZyBz\":1,\"jianChaXmId\":\"63117\",\"jianChaXmMc\":\"头颅正侧位\",\"jianChaBzFlMc\":\"\",\"jianChaFlId\":\"\",\"jianChaFlMc\":\"超声检查\",\"jianChaDLFlId\":\"\",\"jianChaDLFlMc\":\"\",\"jiuZhenId\":\"17635\",\"bingRenZyId\":\"\",\"zhuYuanCs\":0,\"bingRenXm\":\"测试7\",\"nianLing\":\"31\",\"chuShengRq\":\"2013-09-29T18:46:19\",\"lingChuangZd\":\"测试临床诊断\",\"xingBie\":1,\"shenFenZh\":\"\",\"xianZhuZhiXxDz\":\"\",\"xianZhuZhiDh\":\"\",\"hunYinMc\":\"\",\"zhiYeMc\":\"\",\"minZuMc\":\"\",\"keShiId\":\"\",\"keShiMc\":\"\",\"bingQuId\":\"\",\"bingQuMc\":\"\",\"chuangWeiHao\":\"\",\"linChuangZd\":\"\",\"jianYaoBs\":\"\",\"xiangGuanJc\":\"\",\"ZHUYISX\":\"\",\"jianChaBwMc\":\"\",\"jianChaSm\":\"\",\"kaiDanKs\":\"6401\",\"kaiDanKsMc\":\"测试科室\",\"kaiDanRen\":\"DBA\",\"kaiDanRenXm\":\"系统管理员\",\"kaiDanRq\":\"2013-09-29T18:46:19\",\"jianChaKs\":\"6001\",\"jianChaKsMc\":\"X光室（南、体检）\",\"shenQingDanZt\":\"510\",\"shouFeiBz\":0,\"jiZhenBz\":0,\"JinE\":12,\"JIAOPIANFBZ\":0,\"yuanQuBzBm\":\"\"}\n";
    }


    private static String gson() {
        GsonBuilder gb = new GsonBuilder();
        gb.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        gb.registerTypeAdapter(String.class, new StringConverter());
        gb.registerTypeAdapter(Date.class, new DateConverter());
        gb.registerTypeAdapter(Integer.class, new IntConverter());
        Gson gson = gb.create();
        Pacs pacs = gson.fromJson(WebServiceTest.json(), Pacs.class);
        return gson.toJson(pacs);
    }

    /**
     * 针对SEI和SIB不在统一个包内的情况，先查找操作对应的Qname，	 * client通过Qname调用对应操作	 * @param client	 * @param operation	 * @return
     */
    private static QName getOperateQName(Client client, String operation) {
        Endpoint endpoint = client.getEndpoint();
        QName opName = new QName(endpoint.getService().getName().getNamespaceURI(), operation);
        // System.out.println("opName = " + opName);
        BindingInfo bindingInfo = endpoint.getEndpointInfo().getBinding();
        if (bindingInfo.getOperation(opName) == null) {
            return bindingInfo.getOperations()
                    .stream()
                    .filter(op -> operation.equals(op.getOperationInfo().getName().getLocalPart()))
                    .findFirst()
                    .map(BindingOperationInfo::getName)
                    .orElse(opName);
            // for (BindingOperationInfo operationInfo : bindingInfo.getOperations()) {
            //     OperationInfo info = operationInfo.getOperationInfo();
            //     System.out.println("info = " + info);
            //     System.out.println("info.getName() = " + info.getName());
            //
            //     if (operation.equals(operationInfo.getName().getLocalPart())) {
            //         opName = operationInfo.getName();
            //         break;
            //     }
            // }
        }
        return opName;
    }
}
