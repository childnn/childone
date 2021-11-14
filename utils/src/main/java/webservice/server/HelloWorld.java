package webservice.server;

import javax.jws.WebService;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/10/18 16:54
 */
@WebService
public interface HelloWorld {

    // @WebMethod
    String hello(/*@WebParam(name = "receiveCheckApply") */String name);

}
