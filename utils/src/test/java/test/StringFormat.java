package test;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/11 20:38
 *
 * 格式化参数最多会有 5 个部分(不包括 % 符号), 下面 [] 符号里的都是选择性的项目,
 * 只有 % 和 type 是必须的. 格式化说明的顺序是有规定的, 必须要以这个顺序来指定.
 * %[argument number][flags][width][.precision]type
 * argument number: 如果要格式化的参数超过一个以上, 可以以该值指定是哪一个.
 * flags: 特定类型的特定选项, 例如数字要加逗号或正负号.
 * width: 最小的字符数, 注意这不是总数: 输出可以超过此宽度, 不足自动补零.
 * .precision: 精确度, 注意前面有个圆点符号.
 * type: 一定要指定的类型标识.
 *
 * 关于类型: (type)
 *  %d: decimal  -- 整型. -- 必须兼容 int.
 *  %f: floating point -- 浮点型.
 *  %x: hexadecimal -- 必须是 byte, short, int, long, BigInteger.
 *  %c: character -- 同上, 不包括 BigInteger.
 *
 *  %tc: 完整的日期与时间.
 *  %tr: 只有时间.
 *  %tA %tB %td: 周, 月, 日
 */
public class StringFormat {
    @Test
    public void test() {
        String s = String.format("%, d", 100000000);
        System.out.println("s = " + s);
        ArrayList<Object> list = new ArrayList<>();
        list.add(1);
        list.add(1);
        list.add(1);
        String format = String.format("%s", list);
        System.out.println("format = " + format);
    }

    @Test
    public void test1() {
        // 四舍五入, 保留两位.
        String s = String.format("%.2f", 1212.125912);
        System.out.println("format = " + s);
        // 每三位按逗号分隔
        String s1 = String.format("%, .4f", 1212.125912);
        System.out.println("s1 = " + s1);
    }

    @Test
    public void test2() {
        String s = String.format("%c", 42);
        System.out.println("s = " + s); // * 字符星号
    }

    @Test
    public void test3() {
        int one = 204567788;
        double two = 100356576.54765765D;
        // The rank is 204,567,788 out of 100,356,576.55
        String s = String.format("The rank is %,d out of %,.2f", one, two);
        // String s1 = String.format("The rank is %1,d out of %0,.2f", two, one);

        System.out.println("s = " + s);
    }

    @Test
    public void test4() {
        Date now = new Date();
        String date = String.format("%tc", now);
        System.out.println("date = " + date);
        String time = String.format("%tr", now);
        System.out.println("time = " + time);
        String format = String.format("%tA, %tB, %td", now, now, now);
        System.out.println("format = " + format);


        // "<" 表示重复使用之前的参数.
        String format1 = String.format("%tA, %<tB, %<td", now);
        System.out.println("format1 = " + format1);
    }

}
