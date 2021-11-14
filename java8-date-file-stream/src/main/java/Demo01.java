/*
  Object类：类 Object 是类层次结构的根类。每个类都使用 Object 作为超类。所有对象（包括数组）都实现这个类的方法。
           String toString()：返回该对象的字符串表示。 // 引用数据类型默认比较地址值，没有意义，必须重写
           boolean equals(Object obj)：指示其他某个对象是否与此对象“相等”。// 默认比较地址值
  Objects类：jdk 1.7+ : 容忍空指针，防止空指针异常
           static boolean equals​(Object a, Object b) 返回 true如果参数相等，彼此 false否则。
  Date类
           Date()： 分配 Date 对象并初始化此对象，以表示分配它的时间（精确到毫秒）。
           Date(long date)： 传递毫秒值，转换为date日期
           long getTime()： 返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 【Date 对象】表示的毫秒数。
                       中国东八区，加八小时
 */

import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Demo01 {

    @Test
    public void test() {
        String x = "1*2|2*3";

        Map<String, String> collect = Arrays.stream(x.split("\\|"))
                .collect(Collectors.toMap(s -> s.split("\\*")[0], s -> s.split("\\*")[1]/*, (s1, s2) -> s1*/));
        System.out.println("collect = " + collect);

        List<List<String>> list = Arrays.stream(x.split("\\|")).map(s -> Arrays.asList(s.split("\\*"))).collect(Collectors.toList());

    }

    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(date); // 系统当前时间：Wed Jan 30 17:27:06 CST 2019
        date.setTime(100000L);
        System.out.println(date);
        //上下等价
        Date date1 = new Date(100000L);
        System.out.println(date1); //Thu Jan 01 08:01:40 CST 1970
        System.out.println(date1.getTime()); //100000
        long time = date.getTime();
        System.out.println(time); // 自 1970 年 1 月 1 日 00:00:00 GMT 以来到date时间的毫秒数
    }
}
