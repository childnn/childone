package org.anonymous.chapter03.connector.http;

import org.anonymous.chapter03.support.Processor;
import org.anonymous.chapter03.support.ServletProcessor;
import org.anonymous.chapter03.support.StaticResourceProcessor;
import org.anonymous.chapter03.util.RequestUtil;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * this class used to be called HttpServer
 * 创建 Request/Response 对象
 *
 * @see HttpConnector
 * HttpConnector 的支撑类
 */
public class HttpProcessor {

    /**
     * The HttpConnector with which this processor is associated.
     */
    private final HttpConnector connector;
    protected String method = null;
    protected String queryString = null;
    /**
     * The string manager for this package.
     */
    protected StringManager sm =
            StringManager.getManager(getClass().getPackage().getName()); // "org.anonymous.chapter03.connector.http"
    private HttpRequest request;
    private final HttpRequestLine requestLine = new HttpRequestLine();
    private HttpResponse response;

    public HttpProcessor(HttpConnector connector) {
        this.connector = connector;
    }

    /**
     * @param socket 来自 HTTP 请求的套接字
     *               <p>
     *               对每个传入的 HTTP 请求, 它要完成 4 个操作:
     *               1. 创建一个 HttpRequest 对象
     *               2. 创建一个 HttpResponse 对象
     *               3. 解析 HTTP 请求的第一行内容和请求头信息, 填充 HttpRequest 对象
     *               4. 将 HttpRequest 对象和 HttpResponse 对象传递给 {@link Processor#process(HttpRequest, HttpResponse)} 方法
     */
    public void process(Socket socket) {
        SocketInputStream input;
        OutputStream output;
        try {
            input = new SocketInputStream(socket.getInputStream(), 2048);
            output = socket.getOutputStream();

            // create HttpRequest object and parse
            request = new HttpRequest(input);

            // create HttpResponse object
            response = new HttpResponse(output);
            response.setRequest(request);

            response.setHeader("Server", "Pyrmont Servlet Container");

            // 解析请求行
            parseRequest(input, output);
            // 解析请求头
            parseHeaders(input);

            //check if this is a request for a servlet or a static resource
            //a request for a servlet begins with "/servlet/"
            getProcessor().process(request, response);

            // Close the socket
            socket.close();
            // no shutdown for this application
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Processor getProcessor() {
        Processor processor;
        if (request.getRequestURI().startsWith("/servlet/")) {
            processor = new ServletProcessor();
        } else {
            processor = new StaticResourceProcessor();
        }
        return processor;
    }

    /**
     * This method is the simplified version of the similar method in
     * org.apache.catalina.connector.http.HttpProcessor.
     * However, this method only parses some "easy" headers, such as
     * "cookie", "content-length", and "content-type", and ignore other headers.
     *
     * @param input The input stream connected to our socket
     * @throws IOException      if an input/output error occurs
     * @throws ServletException if a parsing error occurs
     */
    private void parseHeaders(SocketInputStream input) throws IOException, ServletException {
        // 不断从 input 中读取请求头信息, 直到全部读完
        while (true) {
            HttpHeader header = new HttpHeader();

            // 填充 HttpHeader 对象
            // Read the next header
            input.readHeader(header);
            if (header.getNameEnd() == 0) {
                if (header.getValueEnd() == 0) {
                    return;
                } else {
                    throw new ServletException(sm.getString("httpProcessor.parseHeaders.colon"));
                }
            }

            String name = new String(header.getName(), 0, header.getNameEnd());
            String value = new String(header.getValue(), 0, header.getValueEnd());
            request.addHeader(name, value);

            // 处理特殊的请求头
            // do something for some headers, ignore others.
            switch (name) {
                case "cookie":
                    Cookie[] cookies = RequestUtil.parseCookieHeader(value);
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().equals("jsessionid")) {
                            // Override anything requested in the URL
                            if (!request.isRequestedSessionIdFromCookie()) {
                                // Accept only the first session id cookie
                                request.setRequestedSessionId(cookie.getValue());
                                request.setRequestedSessionCookie(true);
                                request.setRequestedSessionURL(false);
                            }
                        }
                        request.addCookie(cookie);
                    }
                    break;
                case "content-length":
                    int n;
                    try {
                        n = Integer.parseInt(value);
                    } catch (Exception e) {
                        throw new ServletException(sm.getString("httpProcessor.parseHeaders.contentLength"));
                    }
                    request.setContentLength(n);
                    break;
                case "content-type":
                    request.setContentType(value);
                    break;
            }
        } //end while
    }

    // 解析请求行
    private void parseRequest(SocketInputStream input, OutputStream output) throws IOException, ServletException {
        // Parse the incoming request line
        input.readRequestLine(requestLine);

        // 获取请求方法, URI, 请求协议的版本信息
        String method = new String(requestLine.getMethod(), 0, requestLine.getMethodEnd());
        String protocol = new String(requestLine.getProtocol(), 0, requestLine.getProtocolEnd());
        String uri;

        // Validate the incoming request line
        if (method.length() < 1) {
            throw new ServletException("Missing HTTP request method");
        } else if (requestLine.getUriEnd() < 1) {
            throw new ServletException("Missing HTTP request URI");
        }
        // URI 后面 ? 拼接的查询字符串
        // Parse any query parameters out of the request URI
        int question = requestLine.indexOf("?");
        char[] fullURI = requestLine.getUri();
        if (question >= 0) {
            request.setQueryString(new String(fullURI, question + 1, requestLine.getUriEnd() - question - 1));
            uri = new String(fullURI, 0, question);
        } else {
            request.setQueryString(null);
            uri = new String(fullURI, 0, requestLine.getUriEnd());
        }

        // Checking for an absolute URI (with the HTTP protocol)
        if (!uri.startsWith("/")) {
            // not starting with /, this is an absolute URI
            int pos = uri.indexOf("://");
            // Parsing out protocol and host name
            if (pos != -1) {
                pos = uri.indexOf('/', pos + 3);
                if (pos == -1) {
                    uri = "";
                } else {
                    uri = uri.substring(pos);
                }
            }
        }

        // 如果存在参数 jsessionid, 则表明会话标识符在查询字符串中, 而不在 Cookie 中.
        // Parse any requested session ID out of the request URI
        String match = ";jsessionid=";
        int semicolon = uri.indexOf(match);
        if (semicolon >= 0) {
            String rest = uri.substring(semicolon + match.length());
            int semicolon2 = rest.indexOf(';');
            if (semicolon2 >= 0) {
                request.setRequestedSessionId(rest.substring(0, semicolon2));
                rest = rest.substring(semicolon2);
            } else {
                request.setRequestedSessionId(rest);
                rest = "";
            }
            request.setRequestedSessionURL(true);
            uri = uri.substring(0, semicolon) + rest;
        } else {
            request.setRequestedSessionId(null);
            request.setRequestedSessionURL(false);
        }

        // Normalize URI (using String operations at the moment)
        String normalizedUri = normalize(uri);

        // Set the corresponding request properties
        request.setMethod(method);
        request.setProtocol(protocol);
        if (normalizedUri != null) {
            request.setRequestURI(normalizedUri);
        } else {
            request.setRequestURI(uri);
        }

        if (normalizedUri == null) {
            throw new ServletException("Invalid URI: " + uri + "'");
        }
    }

    /**
     * Return a context-relative path, beginning with a "/", that represents
     * the canonical version of the specified path after ".." and "." elements
     * are resolved out.  If the specified path attempts to go outside the
     * boundaries of the current context (i.e. too many ".." path elements
     * are present), return <code>null</code> instead.
     *
     * @param path Path to be normalized
     */
    protected String normalize(String path) {
        if (path == null)
            return null;
        // Create a place for the normalized path
        String normalized = path;

        // Normalize "/%7E" and "/%7e" at the beginning to "/~"
        if (normalized.startsWith("/%7E") || normalized.startsWith("/%7e"))
            normalized = "/~" + normalized.substring(4);

        // Prevent encoding '%', '/', '.' and '\', which are special reserved
        // characters
        if ((normalized.contains("%25"))
                || (normalized.contains("%2F"))
                || (normalized.contains("%2E"))
                || (normalized.contains("%5C"))
                || (normalized.contains("%2f"))
                || (normalized.contains("%2e"))
                || (normalized.contains("%5c"))) {
            return null;
        }

        if (normalized.equals("/."))
            return "/";

        // Normalize the slashes and add leading slash if necessary
        if (normalized.indexOf('\\') >= 0)
            normalized = normalized.replace('\\', '/');
        if (!normalized.startsWith("/"))
            normalized = "/" + normalized;

        // Resolve occurrences of "//" in the normalized path
        while (true) {
            int index = normalized.indexOf("//");
            if (index < 0)
                break;
            normalized = normalized.substring(0, index) + normalized.substring(index + 1);
        }

        // Resolve occurrences of "/./" in the normalized path
        while (true) {
            int index = normalized.indexOf("/./");
            if (index < 0)
                break;
            normalized = normalized.substring(0, index) + normalized.substring(index + 2);
        }

        // Resolve occurrences of "/../" in the normalized path
        while (true) {
            int index = normalized.indexOf("/../");
            if (index < 0)
                break;
            if (index == 0)
                return (null);  // Trying to go outside our context
            int index2 = normalized.lastIndexOf('/', index - 1);
            normalized = normalized.substring(0, index2) + normalized.substring(index + 3);
        }

        // Declare occurrences of "/..." (three or more dots) to be invalid
        // (on some Windows platforms this walks the directory tree!!!)
        if (normalized.contains("/..."))
            return null;

        // Return the normalized path that we have completed
        return normalized;
    }

}
