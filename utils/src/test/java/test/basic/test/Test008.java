package test.basic.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// 五、请用代码实现:获取当前的日期,并把这个日期转换为指定格式的字符串,如2088-08-08 08:08:08。
// 六、使用SimpleDateFormat类,把2018-03-04转换为2018年03月04日。
public class Test008 {
    public static void main(String[] args) throws ParseException {
        // 获取当前系统时间
        Date date = new Date();
        // 定义格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //格式化Date对象，format
        System.out.println(sdf.format(date)); // 2019-01-31 14:50:05
        System.out.println("==============");

        // 定义原始格式
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
//        Date date1 = sdf1.parse(String.valueOf(2018-03-04));
        // 将原有格式解析为Date
        Date date1 = sdf1.parse("2018-03-04");
        // 定义替换格式
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日");
        // 格式化Date并输出
        System.out.println(sdf2.format(date1));

    }
}
