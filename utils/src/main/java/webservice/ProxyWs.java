package webservice;

import com.senyint.pacs.serv1.PacsChannel;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/10/18 19:14
 */
public class ProxyWs {

    public static void main(String[] args) {
        proxy(WebServiceTest.json());

    }

    // wsimport -s . http://127.0.0.1:11111/weather?wsdl -extension
    public static void proxy(String param) {
        String url = "http://192.168.10.80:8183/pacsChannel?wsdl";
        String portTypeName = "pacsChannel";//"receiveCheckApply";
        try {
            QName qname = new QName("http://serv.pacs.senyint.com/", portTypeName);
            Service service = Service.create(new URL(url), qname);
            // service.addPort(qname, SOAPBinding.SOAP11HTTP_BINDING, url);
            // service.getPort()
            PacsChannel testService = service.getPort(/*new QName(tsn, port name), */PacsChannel.class);
            System.out.println(testService.invoke("receiveCheckApply", param));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
