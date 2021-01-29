package org.anonymous.se.calendar;

import java.util.Calendar;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see java.util.Calendar#set 方法延迟修改
 * set(field, value) 方法将日历字段 field 改为 value, 此外还设置了一个内部成员变量 fields
 * 以指示日历字段 field 已被更改. 尽管日历字段 field 是立即生效的, 但该 Calendar 所代表的时间却不会立即修改.
 * 直到下次调用 get(), getTime(), getTimeInMillis(), add() 或 roll() 方法时才会重新计算日历的时间.
 * 这被称为 set() 方法的延迟修改, 采用延迟修改的优势是多次调用 set() 不会触发不必要的计算: 需要计算 protected long time;
 * @since 2021/1/8 19:21
 */
public class LazyTest {

    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        cal.set(2003, Calendar.AUGUST, 31);

        // 月份设置为 9 月, 9 月没有 31 天, 自动调整为 10月1日
        // 但是这里只是将 MONTH 字段修改为 8, 并未执行计算, 并未更新 long-time 的值. -- 直到调用 getTime 等方法.
        cal.set(Calendar.MONTH, 8);

        // System.out.println(cal.getTime().toLocaleString()); // 查看注释此行代码前后运行结果.

        // 日期设置为 5日
        cal.set(Calendar.DATE, 5);

        // 查看两次不同的结果.
        System.out.println(cal.getTime().toLocaleString());
    }
}
