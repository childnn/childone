package xml;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author child
 * 2019/5/12 9:37
 * 1. final, finally, finalize 区别
 * final：修饰变量, 方法, 类
 * 1) final 修饰的变量必须初始化,且一旦初始化就不可变
 * 2) final 修饰的方法,不可以被重写
 * 3) final 修饰的类,不可以被继承
 * finally: 与 try..catch..finally, 或者 try..finally 连用
 * finalize: Object 类的方法,用来手动释放资源,在 jdk9 过时
 * <p>
 * 2. 获得一个类的 Class 对象的方式
 * 源码阶段: Class.forName("全限定名")
 * 编译之后: 类名.class
 * 运行阶段: 对象名.getClass()
 * <p>
 * 3. 事务的 ACID 指什么， 事务的隔离级别
 * A: atomicity, 原子性, 组成事务的各部分操作是一个整体, 要么同时成功, 要么同时失败, 不可能处在中间状态
 * C: consistency, 一致性, 事务操作的前后, 数据总量不变
 * I: isolation, 隔离性, 多个事务之间相互独立, 互不影响
 * D: durability, 持久性, 事务一旦成功提交, 数据将永久改变
 * 事务隔离级别:
 * read uncommitted: 读未提交
 * read committed: 读已提交
 * repeatable read: 可重复读
 * serializable: 序列化(串行化)
 * <p>
 * 4. String/Object 常用方法
 * String: equalsIgnoreCase(), split(), toUpperCase(), trim(), replace()
 * Object: equals(), hashcode(), clone(), wait(), notify(), getClass()
 * <p>
 * 5. cookie/session 比较
 * cookie 是客户端的会话技术; session 是服务器端的会话技术
 * cookie 的存储数据有大小限制(4kb), 只能存字符串; session 是三大域对象之一, 存储数据没有大小限制, 可以存储任意数据类型
 * cookie 相对不安全, session 相对安全
 * 在同一会话内(浏览器不关闭), 只有一个 cookie 对象, 默认情况下, 浏览器关闭, cookie 销毁
 * 可以设置 cookie 持久化
 * 在同一次会话中, session 在 第一次调用 request.getSession() 方法时创建, 此后获取的都是同一个 session (依赖于 cookie 的 JSESSIONID)
 * session 的销毁: 默认 30 分钟自动销毁; 调用 session.invalidate() 方法手动销毁; 服务器非正常关闭
 * <p>
 * 6. throw/throws 比较
 * throw: 创建异常对象, 在异常发生时, 创建异常线程
 * throws: 在方法声明上抛出异常, 给调用者, 或者 jvm
 * <p>
 * 7. HashMap/Hashtable 区别
 * HashMap: 非同步, 线程不安全, 效率高, 可以存储 null 键值对
 * Hashtable: 同步, 线程安全, 效率低, 不能存储 null 键值对, 类名不遵循 驼峰命名法
 * <p>
 * 8. 写一个单例模式，说明单例模式的特点
 * 代码见下方
 * 特点: 一个类永远只有一个对象, 节约资源
 * <p>
 * 9. 倒序数组： int[] arr = {1, 3, 6, 9, 5, 21, 45, 10, 23};
 */
public class SingletonFactory {

    private static final Map<String, Object> mapper = new HashMap<>();

    static {
        try {
            mapper.put("singleton", Class.forName("org.xml.Singleton").newInstance());
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private final int[] arr = {1, 3, 6, 9, 5, 21, 45, 10, 23};

    @Test
    public void test() {
        Object singleton1 = mapper.get("singleton");
        Object singleton2 = mapper.get("singleton");
        System.out.println(singleton1 == singleton2);
    }

    @Test //ok
    public void test1() {
        Arrays.sort(arr);
        for (int i = arr.length - 1; i >= 0; i--) {
            System.out.println(arr[i]);
        }
    }

    @Test //
    public void test2() {
        int length = arr.length;
        int[] integers = new int[length];
        System.arraycopy(arr, 0, integers, 0, length);
        //        Arrays.sort(integers, (a, b) -> b - a);
        System.out.println(Arrays.toString(integers));
    }

    @Test
    public void test3() {
        Integer[] a = {1, 2};
        //        Object[] b = arr; //error
        //        a = arr; //error
        Object[] b = a;
        System.out.println(Arrays.toString(b));
    }

}

class Singleton {

}