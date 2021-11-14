package esign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.LinkedHashMap;

public class HTTPHelper {
    // slf4j日志记录器
    private static final Logger LOG = LoggerFactory.getLogger(HTTPHelper.class);

    public static void main(String[] args) throws UnsupportedEncodingException {
        String s = new String("ç¼ºå°\u0091å\u008F\u0082æ\u0095°".getBytes("GBK"), StandardCharsets.UTF_8);
        System.out.println("s = " + s);
    }

    /***
     * 向指定URL发送GET方法的请求
     *
     */
    public static String sendGet(String apiUrl, LinkedHashMap<String, String> headers) throws Exception {
        // 获得响应内容
        String http_RespContent;
        HttpURLConnection httpURLConnection = null;
        int http_StatusCode;
        String http_RespMessage;
        try {
            LOG.info(">>>> 实际请求Url: " + apiUrl);

            // 建立连接
            URL url = new URL(apiUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            // 需要输出
            httpURLConnection.setDoOutput(true);
            // 需要输入
            httpURLConnection.setDoInput(true);
            // 不允许缓存
            httpURLConnection.setUseCaches(false);
            // HTTP请求方式
            httpURLConnection.setRequestMethod("GET");
            // 设置Headers
            if (null != headers) {
                for (String key : headers.keySet()) {
                    httpURLConnection.setRequestProperty(key, headers.get(key));
                }
            }
            // 连接会话
            httpURLConnection.connect();
            // 获得响应状态(HTTP状态码)
            http_StatusCode = httpURLConnection.getResponseCode();
            // 获得响应消息(HTTP状态码描述)
            http_RespMessage = httpURLConnection.getResponseMessage();
            // 获得响应内容
            if (HttpURLConnection.HTTP_OK == http_StatusCode) {
                // 返回响应结果
                http_RespContent = getResponseContent(httpURLConnection);
            } else {
                // 返回非200状态时响应结果
                http_RespContent = getErrorResponseContent(httpURLConnection);
                String msg =
                        MessageFormat.format("请求失败: Http状态码 = {0} , {1}", http_StatusCode, http_RespMessage);
                LOG.info(msg);
            }
        } catch (UnknownHostException e) {
            String message = MessageFormat.format("网络请求时发生异常: {0}", e.getMessage());
            Exception ex = new Exception(message);
            ex.initCause(e);
            throw ex;
        } catch (MalformedURLException e) {
            String message = MessageFormat.format("格式错误的URL: {0}", e.getMessage());
            Exception ex = new Exception(message);
            ex.initCause(e);
            throw ex;
        } catch (IOException e) {
            String message = MessageFormat.format("网络请求时发生异常: {0}", e.getMessage());
            Exception ex = new Exception(message);
            ex.initCause(e);
            throw ex;
        } catch (Exception e) {
            String message = MessageFormat.format("网络请求时发生异常: {0}", e.getMessage());
            Exception ex = new Exception(message);
            ex.initCause(e);
            throw ex;
        } finally {
            if (null != httpURLConnection) {
                httpURLConnection.disconnect();
            }
        }
        return http_RespContent;
    }


    /***
     * 向指定URL发送POST方法的请求
     *
     */
    public static String sendPOST(String apiUrl, String data, LinkedHashMap<String, String> headers,
                                  String encoding) throws Exception {
        // 获得响应内容
        String http_RespContent;
        HttpURLConnection httpURLConnection = null;
        int http_StatusCode;
        String http_RespMessage;
        try {
            // 建立连接
            URL url = new URL(apiUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            // 需要输出
            httpURLConnection.setDoOutput(true);
            // 需要输入
            httpURLConnection.setDoInput(true);
            // 不允许缓存
            httpURLConnection.setUseCaches(false);
            // HTTP请求方式
            httpURLConnection.setRequestMethod("POST");
            // 设置Headers
            if (null != headers) {
                for (String key : headers.keySet()) {
                    httpURLConnection.setRequestProperty(key, headers.get(key));
                }
            }
            // 连接会话
            httpURLConnection.connect();
            // 建立输入流，向指向的URL传入参数
            DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());
            // 设置请求参数
            dos.write(data.getBytes(encoding));
            dos.flush();
            dos.close();
            // 获得响应状态(HTTP状态码)
            http_StatusCode = httpURLConnection.getResponseCode();
            // 获得响应消息(HTTP状态码描述)
            http_RespMessage = httpURLConnection.getResponseMessage();
            // 获得响应内容
            if (HttpURLConnection.HTTP_OK == http_StatusCode) {
                // 返回响应结果
                http_RespContent = getResponseContent(httpURLConnection);
            } else {
                // 返回非200状态时响应结果
                http_RespContent = getErrorResponseContent(httpURLConnection);
                String msg =
                        MessageFormat.format("请求失败: Http状态码 = {0} , {1}", http_StatusCode, http_RespMessage);
                LOG.info(msg);
            }
        } catch (MalformedURLException e) {
            String message = MessageFormat.format("格式错误的URL: {0}", e.getMessage());
            throw new Exception(message, e);
        } catch (Exception e) {
            String message = MessageFormat.format("网络请求时发生异常: {0}", e.getMessage());
            throw new Exception(message, e);
        } finally {
            if (null != httpURLConnection) {
                httpURLConnection.disconnect();
            }
        }
        return http_RespContent;
    }

    /***
     * 读取HttpResponse响应内容
     *
     */
    private static String getResponseContent(HttpURLConnection httpURLConnection) throws IOException {
        StringBuilder contentBuffer;
        BufferedReader responseReader = null;
        try {
            contentBuffer = new StringBuilder();
            String readLine;
            responseReader =
                    new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8));
            while ((readLine = responseReader.readLine()) != null) {
                contentBuffer.append(readLine);
            }
        } finally {
            if (null != responseReader) {
                responseReader.close();
            }
        }
        return contentBuffer.toString();
    }

    /***
     * 读取HttpResponse响应内容
     *
     */
    private static String getErrorResponseContent(HttpURLConnection httpURLConnection)
            throws IOException {
        StringBuilder contentBuffer;
        BufferedReader responseReader = null;
        try {
            contentBuffer = new StringBuilder();
            String readLine;
            responseReader =
                    new BufferedReader(new InputStreamReader(httpURLConnection.getErrorStream(), StandardCharsets.UTF_8));
            while ((readLine = responseReader.readLine()) != null) {
                contentBuffer.append(readLine);
            }
        } finally {
            if (null != responseReader) {
                responseReader.close();
            }
        }
        return contentBuffer.toString();
    }

}
