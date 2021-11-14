// package webservice;
//
// import com.alibaba.fastjson.JSONObject;
// import org.apache.axis.client.Call;
// import org.apache.axis.encoding.XMLType;
// import org.apache.axis.soap.SOAPConstants;
//
// import javax.xml.namespace.QName;
// import javax.xml.rpc.ParameterMode;
// import javax.xml.rpc.ServiceException;
// import javax.xml.ws.Service;
// import java.nio.charset.StandardCharsets;
// import java.rmi.RemoteException;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Map;
//
// /**
//  * ~~ Talk is cheap. Show me the code. ~~ :-)
//  *
//  * @author MiaoOne
//  * @since 2021/10/18 19:51
//  */
// public class Axis1 {
//
//     static String endpoint = "http://192.168.10.80:8183/pacsChannel?wsdl";
//     static String namespace = "http://serv.pacs.senyint.com/";
//     static String method = "invoke"; // receiveCheckApply /invoke
//
//     public static void main(String[] args) throws ServiceException, RemoteException {
//         send2();
//     }
//
//     static void send2() throws ServiceException, RemoteException {
//
//         Service service = new Service();
//
//         Call call = (Call) service.createCall();
//
//         // Pacs pacs = JSON.parseObject(WebServiceTest.json(), Pacs.class);
//
//         Object[] object = {WebServiceTest.json()};
//
//         call = (Call) service.createCall();
//
//         call.setTargetEndpointAddress(endpoint);// 远程调用路径
//         // QName qn = new QName(namespace, method); //接口的命名空间,请根据您的实际接口填写，和方法名称：
//         call.setOperationName(method);
//
//
//         // call.setOperationName(qn);
//         //
//         // call.registerTypeMapping(Pacs.class, qn,
//         //
//         //         new BeanSerializerFactory(Pacs.class, qn),
//         //
//         //         new BeanDeserializerFactory(Pacs.class, qn));
//
//         // call.addParameter("input", qn, Pacs.class, ParameterMode.IN);  //"arg0"不用改哟，我这里只有一个参数，并且参数是一个实体对象  http://cnblogs.com/qgc
//
//         // call.setReturnType(XMLType.XSD_STRING);// 返回值类型：String
//
//         call.addParameter("aaa", XMLType.XSD_STRING, ParameterMode.IN);
//         call.setReturnClass(Map.class);
//
//         Object result = call.invoke(object); // 远程调用
//
//         System.out.println(result);
//
//
//         // return result;
//     }
//
//     static void send() {
//         // String endpoint = "http://192.168.10.80:8183/pacsChannel?wsdl";
//         // String namespace = "http://serv.pacs.senyint.com/";
//         // String xmlBody = WebServiceTest.json();
//         JSONObject jsonObject = WebServiceTest.jsonObject();
//         String returnData;
//         try {
//             Service service = new Service();
//             Call call = (Call) service.createCall();
//             call.setEncodingStyle(StandardCharsets.UTF_8.name());
//             call.setTargetEndpointAddress(endpoint);// 远程调用路径
//             call.setUseSOAPAction(true);
//             call.setSOAPActionURI(namespace + "receiveCheckApply");
//             call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
//             List<Object> params = new ArrayList<>();
//             jsonObject.forEach((k, v) -> {
//                 call.addParameter(k, XMLType.XSD_STRING, ParameterMode.IN);
//                 params.add(v);
//
//             });
//
//             // call.addParameter(new QName(namespace, "receiveCheckApply"), XMLType.XSD_STRING, ParameterMode.IN);
//             call.setOperationName(new QName("http://serv.pacs.senyint.com/", "receiveCheckApply"));
//             // 设置参数名: 参数名,参数类型,参数模式
//             // call.addParameter("service", XMLType.XSD_STRING, ParameterMode.IN);
//             // call.addParameter("xmlBody", XMLType.XSD_STRING, ParameterMode.IN);
//             // call.setReturnType(XMLType.XSD_STRING);// 设置被调用方法的返回值类型
//             returnData = (String) call.invoke(params.toArray());// 远程调用
//             System.out.println("result is " + returnData);
//         } catch (Exception e) {
//             System.err.println(e.toString());
//         }
//
//     }
//
//
// }
