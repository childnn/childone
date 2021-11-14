package webservice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingOperationInfo;

import javax.xml.namespace.QName;
import java.util.Arrays;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/10/18 15:20
 */
public class WebServiceTest {

    // 这种方式必须在 server 的实现上加注解属性: targetNamespace = "http://server.webservice/ 接口包名反写
    private static void webService(String url, String operationName, Object params) throws Exception {
        JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
        Client client = factory.createClient(url);
        Endpoint endpoint = client.getEndpoint();
        // QName qName = new QName(url/*endpoint.getService().getName().getNamespaceURI()*/, operationName/*"invoke"*/);
        // BindingInfo binding = endpoint.getEndpointInfo().getBinding();
        // if (binding.getOperation(qName) == null) {
        //     for (BindingOperationInfo operation : binding.getOperations()) {
        //         if (operationName.equals(operation.getName().getLocalPart())) {
        //             qName = operation.getName();
        //             break;
        //         }
        //     }
        // }
        // client.invoke(qName, params);
        System.out.println(Arrays.toString(client.invoke(operationName, params)));

        // HTTPConduit conduit = ((HTTPConduit) client.getConduit());
        // HTTPClientPolicy clientPolicy = new HTTPClientPolicy();
        //
        // conduit.setClient(clientPolicy);

        // client.invoke(operationName, params);
    }

    // 这种方式不需加 webService 中的注解属性
    public static void webService2(String url, String operationName, Object... params) throws Exception {
        JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
        Client client = factory.createClient(url);
        Endpoint endpoint = client.getEndpoint();
        QName qName = new QName(url/*endpoint.getService().getName().getNamespaceURI()*/, operationName/*"invoke"*/);
        BindingInfo binding = endpoint.getEndpointInfo().getBinding();
        if (binding.getOperation(qName) == null) {
            for (BindingOperationInfo operation : binding.getOperations()) {
                QName name = operation.getName();
                if (operationName.equals(name.getLocalPart())) {
                    qName = name;
                    break;
                }
            }
        }
        System.out.println(Arrays.toString(client.invoke(qName, params)));
        // System.out.println(Arrays.toString(client.invoke(operationName, params)));

        // HTTPConduit conduit = ((HTTPConduit) client.getConduit());
        // HTTPClientPolicy clientPolicy = new HTTPClientPolicy();
        //
        // conduit.setClient(clientPolicy);

        // client.invoke(operationName, params);
    }



    // 后缀必须为 ?wsdl
    public static final String url = "http://localhost:4321/ws?wsdl";//"http://192.168.10.80:8183/pacsChannel?wsdl";
    public static final String operationName = "hello"; //"receiveCheckApply";  // "invoke";//


    public static void main(String[] args) throws Exception {
        // http://192.168.10.80:8183/pacsChannel?wsdl/receiveCheckApply


        // webService(url, operationName, getParams()) ;
        webService2(url, operationName, getParams()) ;
    }

    public static String json1() {
        // return "{\"bingRenId\":\"247733\",\"bingRenXm\":\"许盼盼\",\"chuShengRq\":\"1991-06-17 00:00:00\",\"jiZhenBz\":0,\"jianChaDLFlId\":\"JCFL004\",\"jianChaDLFlMc\":\"超声\",\"jianChaFlId\":\"JCFL016\",\"jianChaFlMc\":\"超声-妇科\",\"jianChaKs\":\"5998\",\"jianChaKsMc\":\"超声室（南）\",\"jianChaSqdId\":\"2296879\",\"jianChaXmId\":\"272156\",\"jianChaXmMc\":\"腔内彩超\",\"jianYaoBs\":\"体查\",\"jiaoPianFbZ\":1,\"jinE\":121,\"jiuZhenId\":\"37457\",\"jiuZhenKh\":\"2007417\",\"kaiDanKs\":\"6132\",\"kaiDanKsMc\":\"妇科内分泌专家门诊\",\"kaiDanRen\":\"105880\",\"kaiDanRenXm\":\"韩献琴\",\"kaiDanRq\":\"2021-10-12 16:36:47\",\"linChuangZd\":\"人流术后\",\"menZhenZyBz\":1,\"nianLing\":\"30岁\",\"shenFenZh\":\"411423199106174549\",\"shenQingDanZt\":\"530\",\"shouFeiBz\":1,\"xianZhuZhiDh\":\"15516170088\",\"xingBie\":2,\"yuanQuId\":\"1\",\"zhiYeMc\":\"其他\",\"zhuYuanCs\":0}";
        return "{\"bingQuId\":\"6236\",\"bingQuMc\":\"妇科二病区(南)\",\"bingRenId\":\"229348\",\"bingRenXm\":\"王小丽\",\"bingRenZyId\":\"37456\",\"chuShengRq\":\"1986-04-18 00:00:00\",\"chuangWeiHao\":\"7\",\"hunYinMc\":\"未婚\",\"jiZhenBz\":0,\"jianChaDLFlId\":\"JCFL004\",\"jianChaDLFlMc\":\"超声\",\"jianChaFlId\":\"JCFL019\",\"jianChaFlMc\":\"超声-心电图\",\"jianChaKs\":\"5998\",\"jianChaKsMc\":\"超声室(南)\",\"jianChaSqdId\":\"2296854\",\"jianChaXmId\":\"272134\",\"jianChaXmMc\":\"心电图\",\"jiaoPianFbZ\":1,\"jinE\":26.4,\"kaiDanKs\":\"6235\",\"kaiDanKsMc\":\"妇科病房一(南)\",\"kaiDanRen\":\"106112\",\"kaiDanRenXm\":\"桂俊\",\"kaiDanRq\":\"2021-10-12 16:26:32\",\"keShiId\":\"6237\",\"keShiMc\":\"妇科病房二(南)\",\"linChuangZd\":\"子宫平滑肌瘤\",\"menZhenZyBz\":2,\"minZuMc\":\"汉族\",\"nianLing\":\"35岁\",\"shenFenZh\":\"622627198604182420\",\"shenQingDanZt\":\"510\",\"shouFeiBz\":0,\"xianZhuZhiDh\":\"15756278936\",\"xingBie\":2,\"yuanQuId\":\"1\",\"zhiYeMc\":\"其他\",\"zhuYuanCs\":2,\"zhuYuanHao\":\"00000375\"}";
    }



    public static String json() {
        return "{\n" +
                "  \"jianChaSqdId\": \"2240791\",\n" +
                "  \"yuanQuId\": \"1\",\n" +
                "  \"bingRenId\": \"74378\",\n" +
                "  \"zhuYuanHao\": \"\",\n" +
                "  \"jiuZhenKh\": \"0000999\",\n" +
                "  \"menZhenZyBz\": 1,\n" +
                "  \"jianChaXmId\": \"63117\",\n" +
                "  \"jianChaXmMc\": \"头颅正侧位\",\n" +
                "  \"jianChaBzFlMc\": \"\",\n" +
                "  \"jianChaFlId\": \"\",\n" +
                "  \"jianChaFlMc\": \"超声检查\",\n" +
                "  \"jianChaDLFlId\": \"\",\n" +
                "  \"jianChaDLFlMc\": \"\",\n" +
                "  \"jiuZhenId\": \"17635\",\n" +
                "  \"bingRenZyId\": \"\",\n" +
                "  \"zhuYuanCs\": 0,\n" +
                "  \"bingRenXm\": \"测试7\",\n" +
                "  \"nianLing\": \"31\",\n" +
                "  \"chuShengRq\":\"2013-09-29T18:46:19\",\n" +
                "  \"lingChuangZd\":\"测试临床诊断\",\n" +
                "  \"xingBie\": 1,\n" +
                "  \"shenFenZh\": \"\",\n" +
                "  \"xianZhuZhiXxDz\": \"\",\n" +
                "  \"xianZhuZhiDh\": \"\",\n" +
                "  \"hunYinMc\": \"\",\n" +
                "  \"zhiYeMc\": \"\",\n" +
                "  \"minZuMc\": \"\",\n" +
                "  \"keShiId\": \"\",\n" +
                "  \"keShiMc\": \"\",\n" +
                "  \"bingQuId\": \"\",\n" +
                "  \"bingQuMc\": \"\",\n" +
                "  \"chuangWeiHao\": \"\",\n" +
                "  \"linChuangZd\": \"\",\n" +
                "  \"jianYaoBs\": \"\",\n" +
                "  \"xiangGuanJc\": \"\",\n" +
                "  \"ZHUYISX\": \"\",\n" +
                "  \"jianChaBwMc\": \"\",\n" +
                "  \"jianChaSm\": \"\",\n" +
                "  \"kaiDanKs\": \"6401\",\n" +
                "  \"kaiDanKsMc\": \"测试科室\",\n" +
                "  \"kaiDanRen\": \"DBA\",\n" +
                "  \"kaiDanRenXm\": \"系统管理员\",\n" +
                "  \"kaiDanRq\": \"2013-09-29T18:46:19\",\n" +
                "  \"jianChaKs\": \"6001\",\n" +
                "  \"jianChaKsMc\": \"X光室（南、体检）\",\n" +
                "  \"shenQingDanZt\": \"510\",\n" +
                "  \"shouFeiBz\": 0,\n" +
                "  \"jiZhenBz\": 0,\n" +
                "  \"JinE\": 12,\n" +
                "  \"JIAOPIANFBZ\": 0,\n" +
                "  \"yuanQuBzBm\": \"\"\n" +
                "}";
        // return "{\"jianChaSqdId\":\"2240791\",\"chuangWeiHao\":\"\",\"bingQuMc\":\"\",\"bingQuId\":\"\",\"keShiMc\":\"\",\"keShiId\":\"\",\"hunYinMc\":\"\",\"zhiYeMc\":\"\",\"minZuMc\":\"\", \"xianZhuZhiDh\":\"\",\"xianZhuZhiXxDz\":\"\",\"shenFenZh\":\"\",\"jianChaDLFlId\":\"\",\"zhuYuanCs\":\"\",\"jianChaDLFlMc\":\"\", \"jianChaBzFlMc\": \"\",\"jianChaFlMc\":\"\",\"yuanQuId\":\"1\",\"bingRenId\":\"74378\",\"zhuYuanHao\":\"\",\"jiuZhenKh\":\"0000999\",\"menZhenZyBz\":1,\"jianChaXmId\":\"63117\",\"jianChaXmMc\":\"头颅正侧位\",\"jiuZhenId\":\"17635\",\"bingRenZyId\":\"\",\"jianChaFlId\":\"\",\"bingRenXm\":\"测试7\",\"nianLing\":\"31岁\",\"xingBie\":1,\"chuShengRq\":\"3122131\",\"linChuangZd\":\"\",\"jianYaoBs\":\"\",\"xiangGuanJc\":\"\",\"ZHUYISX\":\"\",\"jianChaBwMc\":\"\",\"jianChaSm\":\"\",\"kaiDanKs\":\"6401\",\"kaiDanKsMc\":\"测试科室\",\"kaiDanRen\":\"DBA\",\"kaiDanRenXm\":\"系统管理员\",\"kaiDanRq\":\"3122131\",\"jianChaKs\":\"6001\",\"jianChaKsMc\":\"X光室（南、体检）\",\"shenQingDanZt\":\"510\",\"shouFeiBz\":0,\"jiZhenBz\":0,\"JinE\":12,\"JIAOPIANFBZ\":0,\"yuanQuBzBm\":\"\"}\n";
        // return "{\"bingRenId\":\"74378\",\"yuanQuBzBm\":\"\",\"bingRenXm\":\"测试7\",\"bingRenZyId\":\"\",\"chuShengRq\":636739200000,\"jiZhenBz\":0,\"jianChaBwMc\":\"\",\"jianChaKs\":\"6001\",\"jianChaKsMc\":\"X光室（南、体检）\",\"jianChaSm\":\"\",\"jianChaSqdId\":\"2240791\",\"jianChaXmId\":\"63117\",\"jianChaXmMc\":\"头颅正侧位\",\"jianYaoBs\":\"\",\"jiaoPianFbZ\":1,\"jinE\":0.0000,\"jiuZhenId\":\"17635\",\"jiuZhenKh\":\"0000999\",\"kaiDanKs\":\"6401\",\"kaiDanKsMc\":\"测试科室\",\"kaiDanRen\":\"DBA\",\"kaiDanRenXm\":\"系统管理员\",\"kaiDanRq\":1631692805000,\"linChuangZd\":\"\",\"menZhenZyBz\":1,\"nianLing\":\"31岁\",\"shenQingDanZt\":\"510\",\"shouFeiBz\":0,\"xiangGuanJc\":\"\",\"xingBie\":\"1\",\"yuanQuId\":\"1\",\"zhuYiSx\":\"\",\"zhuYuanHao\":\"\",\"zuHao\":\"101185093\"}";
    }

    public static JSONObject jsonObject() {
        return JSON.parseObject(json());
    }

    public static String xml() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<obj>\n" +
                "    <bingRenId>74378</bingRenId>\n" +
                "    <bingRenXm>测试7</bingRenXm>\n" +
                "    <bingRenZyId></bingRenZyId>\n" +
                "    <chuShengRq>636739200000</chuShengRq>\n" +
                "    <jiZhenBz>0</jiZhenBz>\n" +
                "    <jianChaBwMc></jianChaBwMc>\n" +
                "    <jianChaKs>6001</jianChaKs>\n" +
                "    <jianChaKsMc>X光室（南、体检）</jianChaKsMc>\n" +
                "    <jianChaSm></jianChaSm>\n" +
                "    <jianChaSqdId>2240791</jianChaSqdId>\n" +
                "    <jianChaXmId>63117</jianChaXmId>\n" +
                "    <jianChaXmMc>头颅正侧位</jianChaXmMc>\n" +
                "    <jianYaoBs></jianYaoBs>\n" +
                "    <jiaoPianFbZ>1</jiaoPianFbZ>\n" +
                "    <jinE>0</jinE>\n" +
                "    <jiuZhenId>17635</jiuZhenId>\n" +
                "    <jiuZhenKh>0000999</jiuZhenKh>\n" +
                "    <kaiDanKs>6401</kaiDanKs>\n" +
                "    <kaiDanKsMc>测试科室</kaiDanKsMc>\n" +
                "    <kaiDanRen>DBA</kaiDanRen>\n" +
                "    <kaiDanRenXm>系统管理员</kaiDanRenXm>\n" +
                "    <kaiDanRq>1631692805000</kaiDanRq>\n" +
                "    <linChuangZd></linChuangZd>\n" +
                "    <menZhenZyBz>1</menZhenZyBz>\n" +
                "    <nianLing>31岁</nianLing>\n" +
                "    <shenQingDanZt>510</shenQingDanZt>\n" +
                "    <shouFeiBz>0</shouFeiBz>\n" +
                "    <xiangGuanJc></xiangGuanJc>\n" +
                "    <xingBie>1</xingBie>\n" +
                "    <yuanQuId>1</yuanQuId>\n" +
                "    <zhuYiSx></zhuYiSx>\n" +
                "    <zhuYuanHao></zhuYuanHao>\n" +
                "    <zuHao>101185093</zuHao>\n" +
                "</obj>";
    }

    public static String getParams() {
        return "{\n" +
                "  \"bingRenId\": \"74378\",\n" +
                "  \"bingRenXm\": \"测试7\",\n" +
                "  \"bingRenZyId\": \"\",\n" +
                "  \"chuShengRq\": 636739200000,\n" +
                "  \"jiZhenBz\": 0,\n" +
                "  \"jianChaBwMc\": \"\",\n" +
                "  \"jianChaKs\": \"6001\",\n" +
                "  \"jianChaKsMc\": \"X光室（南、体检）\",\n" +
                "  \"jianChaSm\": \"\",\n" +
                "  \"jianChaSqdId\": \"2240791\",\n" +
                "  \"jianChaXmId\": \"63117\",\n" +
                "  \"jianChaXmMc\": \"头颅正侧位\",\n" +
                "  \"jianYaoBs\": \"\",\n" +
                "  \"jiaoPianFbZ\": 1,\n" +
                "  \"jinE\": 0.0000,\n" +
                "  \"jiuZhenId\": \"17635\",\n" +
                "  \"jiuZhenKh\": \"0000999\",\n" +
                "  \"kaiDanKs\": \"6401\",\n" +
                "  \"kaiDanKsMc\": \"测试科室\",\n" +
                "  \"kaiDanRen\": \"DBA\",\n" +
                "  \"kaiDanRenXm\": \"系统管理员\",\n" +
                "  \"kaiDanRq\": 1631692805000,\n" +
                "  \"linChuangZd\": \"\",\n" +
                "  \"menZhenZyBz\": 1,\n" +
                "  \"nianLing\": \"31岁\",\n" +
                "  \"shenQingDanZt\": \"510\",\n" +
                "  \"shouFeiBz\": 0,\n" +
                "  \"xiangGuanJc\": \"\",\n" +
                "  \"xingBie\": \"1\",\n" +
                "  \"yuanQuId\": \"1\",\n" +
                "  \"zhuYiSx\": \"\",\n" +
                "  \"zhuYuanHao\": \"\",\n" +
                "  \"zuHao\": \"101185093\"\n" +
                "}";
    }

}
