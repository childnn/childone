package test.basic.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 二、使用SimpleDateFormat类,把2018-03-04转换为2018年03月04日。
 */
public class test002 {

    public static void main(String[] args) throws ParseException {
        // parse 方法，字符串解析为date格式
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        Date p1 = sdf1.parse("2018-03-04");
        System.out.println(p1);
        // format 方法格式化为字符串
        System.out.println(new SimpleDateFormat("yyyy年MM月dd日").format(p1));
    }
}
