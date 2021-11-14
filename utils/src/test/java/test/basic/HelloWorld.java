package test.basic;

import java.math.BigDecimal;

public class HelloWorld
{

	public static void main(String[] args)
	{
        System.out.println(3 - 2.6); // 0.3999999999999999
        System.out.println(3 - 2.6 == 0.4); // false
        BigDecimal i = new BigDecimal("3");
        BigDecimal j = new BigDecimal("2.6");
        BigDecimal sub = i.subtract(j); // 计算 3 - 2.6
        System.out.println(sub); // 0.4
//        System.out.println(sub == 0.4); // error，不能直接这样写
        double x = sub.doubleValue();
        System.out.println(x); // 0.4
        System.out.println(x == 0.4); // true
        System.out.println("==============");

        System.out.println("HelloWorld");
		System.out.println("123");
		System.out.println("");
		System.out.println("	1");
		System.out.println(" " + '1');
		System.out.println('0');
		System.out.println('0' + 0);
		System.out.println(123);
		System.out.println("1" + "2" + "3");
		System.out.println('1' + '2' + '3'); // 字符以在 unicode 中对应的数字相加

	}

}