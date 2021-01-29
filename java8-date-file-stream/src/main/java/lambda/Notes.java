package lambda;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 2019年2月28日15:56:35
 *
 * @FunctionalInterface 注解 用来约束/判断函数式接口
 * 标注在接口名之上
 * java.util.function 包
 * 函数式接口: Single Abstract Method （SAM 类型接口：单抽象方法接口）
 * Supplier<T>: 生产型接口
 * T get()
 * <p>
 * Consumer<T>:  消费型接口
 * void accept(T t)
 * default Consumer<T> andThen​(Consumer<? super T> after) 返回一个组成的 Consumer ，依次执行此操作，然后执行 after操作。
 * Predicate<T>:  判断型接口
 * boolean test(T t)
 * default Predicate<T> and​(Predicate<? super T> other) 返回一个组合的谓词，表示该谓词与另一个谓词的短路逻辑AND。
 * default Predicate<T> or​(Predicate<? super T> other) 返回一个组合的谓词，表示该谓词与另一个谓词的短路逻辑或。
 * default Predicate<T> negate​() 返回表示此谓词的逻辑否定的谓词。
 * static <T> Predicate<T> isEqual​(Object targetRef) 返回一个谓词，根据 Objects.equals(Object, Object)测试两个参数是否相等。
 * Function<T,R>: 转换型接口
 * R apply​(T t) 将此函数应用于给定的参数。
 * default <V> Function<T,V> andThen​(Function<? super R,? extends V> after) 返回一个组合函数，首先将该函数应用于其输入，然后将 after函数应用于结果。
 */
public class Notes {
    public static void main(String[] args) throws IOException {
        //        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //        byte[] bys = baos.toByteArray();
        //        ByteArrayInputStream bais = new ByteArrayInputStream(bys);
        String str = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); //读取键盘录入
        String s = br.readLine(); //把键盘录入的一行数据命名为 s(赋值给s)
        System.out.println(s);
    }
}
