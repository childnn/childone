package org.anonymous.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author child
 * 2019/4/21 15:35
 * 文件上传： 这里 maven 导入 upload 包出现问题， 直接放在 tomcat 的 lib 目录下，解决问题
 */
@Controller("upload")
@RequestMapping("/upload")
public class UploadController {
    /**
     * MultipartFile: springMVC 提供的文件解析对象
     * 封装了上传文件的信息内容
     * 要求:
     * 1. 这个对象的名称要和 <input type="file" name="name属性值"/>
     * 的 name 属性值一致
     * 2. 在 springMVC.xml 配置文件中进行配置： CommonsMultipartResolver
     *
     * @param
     * @param request
     * @return
     */
    @RequestMapping("/upload")
    public String upload(MultipartFile myFile, HttpServletRequest request) throws IOException {
        //        System.out.println("myFile = " + myFile); //org.springframework.web.multipart.commons.CommonsMultipartFile@50ef040b
        //获取文件名
        String filename = myFile.getOriginalFilename();
        System.out.println(filename); //2.jpg
        filename = UUID.randomUUID().toString() + filename; //保证上传的文件名唯一
        //创建文件
        //获取当前项目在服务器的根路径: 绝对路径(从盘符开始的路径)
        ServletContext servletContext = request.getServletContext();
        String realPath = servletContext.getRealPath("/upload");
        System.out.println(realPath); //D:\Develop\IDEA_Project\maven-project\springMVC_day02_a\target/springMVC_day02_a/upload
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new Date());

        //创建文件存放的目录： 以日期作为 文件的父目录
        realPath = realPath + "/" + date;
        //        System.out.println("realPath = " + realPath);
        File file = new File(realPath);
        if (!file.exists()) { //判断文件加是否存在
            //创建文件夹
            file.mkdirs();
        }

        //将文件的写到路径下
        File file1 = new File(file, filename);
        myFile.transferTo(file1); //封装 io
        return "upload";
    }
}
