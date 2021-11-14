package webservice;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/10/18 16:32
 */
public class WS1 {


    /**
     * CXF 动态代理模式，不用生成本地WS代理类， * 通过反射调用 WS 的对应的方法，传入相应的参数 * 访问cxf-server-web项目下的webservice; * 测试jaxws-rt发布WebService web方式。 * 此测试实例，用于测试SEI和SIB的targetNamespace指定的webService接口： * http://localhost:8080/cxf_server_web/jws_services?wsdl； * @author donald * 2017年7月8日 * 下午7:24:12
     */
        // private static final String JWS_RT_WSDL_URI = "http://localhost:8080/cxf_server_web/jws_services?wsdl";

        public static void main(String[] args) throws Exception {
            JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
            Client client = dcf.createClient("http://192.168.10.80:8183/pacsChannel?wsdl");
            HTTPConduit conduit = (HTTPConduit) client.getConduit();
            HTTPClientPolicy policy = new HTTPClientPolicy();
            policy.setConnectionTimeout(10000);
            policy.setAllowChunking(false);
            policy.setReceiveTimeout(10000);
            conduit.setClient(policy);
            Object[] invokeResult = client.invoke("receiveCheckApply", WebServiceTest.getParams());
        }

}
