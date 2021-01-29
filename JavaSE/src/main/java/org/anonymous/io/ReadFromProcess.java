package org.anonymous.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/1/20 9:52
 */
public class ReadFromProcess {

    public static void main(String[] args) throws IOException {
        // 运行 javac 命令, 返回运行该命令的子进程
        Process p = Runtime.getRuntime().exec("javac");
        // 以 p 进程的错误流创建 BufferedReader 对象
        // 这个错误流对本程序是输入流, 对 p 进程则是输出流
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream(), Charset.forName("GBK")));

        String buf;
        // 运行输出 javac 命令的错误输出
        while (null != (buf = br.readLine())) {
            System.out.println(buf);
        }
    }

}
