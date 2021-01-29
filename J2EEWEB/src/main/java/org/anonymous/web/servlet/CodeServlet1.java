package org.anonymous.web.servlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author child
 * 2019/3/29 11:48
 */
//生成验证码
@WebServlet("/CodeServlet1")
public class CodeServlet1 extends HttpServlet {
    private static final long serialVersionUID = -6514931275683432041L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int width = 100, height = 50;
        //验证码图片大小
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //画笔
        Graphics graphics = image.getGraphics();
        //填充颜色
        graphics.setColor(Color.PINK);
        graphics.fillRect(0, 0, width, height);
        //边框
        graphics.setColor(Color.BLACK);
        graphics.drawRect(0, 0, width - 1, height - 1);
        //验证码数据
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        //定义 StringBuilder, 拼接生成的验证码,存入 session 待验证
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < 5; i++) {
            int index = (int) (Math.random() * str.length());
            char c = str.charAt(index);
            sb.append(c);
            //往图片上写验证码
            graphics.drawString(c + "", width / 5 * i, height / 2);
        }
        //将系统生成的验证码存入域对象 session
        request.getSession().setAttribute("code", sb.toString());
        //把生成的验证码图片写在页面
        ImageIO.write(image, "jpg", response.getOutputStream());
    }
}
