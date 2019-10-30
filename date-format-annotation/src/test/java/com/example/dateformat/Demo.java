package com.example.dateformat;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/8/14 11:23
 */
public class Demo {

    @Test
    public void test() {
        UUID uuid = UUID.randomUUID();
        System.out.println("uuid = " + uuid); // 0070e6c8-062e-4e8b-9de3-c9a91d8ddbb7
        int length = uuid.toString().length();
        System.out.println(length);
    }

    @Test
    public void test1() {
        String format = new SimpleDateFormat("yyyy-MM").format(new Date());
        System.out.println("format = " + format);
    }

    @Test
    public void test2() {
        String month = new SimpleDateFormat("MM").format(new Date());
        System.out.println("month = " + month);
        String history = String.valueOf(Integer.parseInt(month) - 3);

    }

}
