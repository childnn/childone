package org.example.aop;

import com.alibaba.fastjson2.JSON;
import org.example.aop.bean.Stu;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2023/7/14
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Hello world!");

        Stu jack = new Stu("jack", 12);

        String jsonString = JSON.toJSONString(jack);
        System.out.println("jsonString = " + jsonString);
    }

}