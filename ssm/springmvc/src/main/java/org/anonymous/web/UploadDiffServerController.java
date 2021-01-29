package org.anonymous.web;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @author child
 * 2019/4/21 20:30
 * 跨服务器上传
 */
@Controller("upload")
public class UploadDiffServerController {

    @RequestMapping("/upload")
    public String upload(MultipartFile myFile) throws IOException {
        //获取上传的文件名
        String originalFilename = myFile.getOriginalFilename();
        originalFilename = UUID.randomUUID().toString() + originalFilename;
        System.out.println(originalFilename);

        //确认要上传的 服务器地址: 另一个 tomcat 服务器地址 - server.xml 设置 端口 8081
        String path = "http://localhost:8081/springMVC_img/uploads/";
        //D:\Develop\tomcat\apache-tomcat-9.0.7-windows-x64\apache-tomcat-9.0.7\webapps\ROOT\springMVC_img\ uploads
        //创建一个客户端
        Client client = Client.create();
        //创建资源连接地址
        WebResource resource = client.resource(path + originalFilename);
        //读写即可: 要的是文件的字节流数组
        //细节: 底层用的是 put 提交方式: tomcat 默认只接受 get/post
        /*
         * 在 tomcat 目录下 web.xml 中配置: 让另一个 服务器 接受 put 提交
         * <init-param>
         * 			<param-name>readonly</param-name>
         * 			<param-value>false</param-value>
         * </init-param>
         */
        resource.put(myFile.getBytes());
        return "success";

    }
}
