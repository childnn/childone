package bridge;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/7/3 10:56
 * javap -c SubClass.class -- 字节码
 *
 * 编译：
 * 	在 src 目录下： javac -d ../classes lee/*.java
 * 运行：
 * 	进入编译包所在顶层路径, 此处为 lee 所在目录
 * 	java lee.Hello
 * 添加 main 方法的启动入参，直接在启动类后添加即可，多个参数空格分割，如果参数本身包含空格用双引号包裹
 * 	java lee.Hello abc 123
 *
 */
public class SubClass implements SuperClass<String> {
    // 因为泛型是在1.5引入的，为了向前兼容，所以会在编译时去掉泛型（泛型擦除）。
    // 那么SuperClass接口中的method方法的参数在虚拟机中只能是 Object
    // 而实际的实现是 void print(String s), 根本没有 void print(Object t);
    // 虚拟机会自动实现一个方法, 转而调用实际实现的 print(String s)
    // E:\dev-code\WorkSpace\child/utils\target\classes\bridge>javap -p SubClass.class
    // Compiled from "SubClass.java"
    // public class bridge.SubClass implements bridge.SuperClass<java.lang.String> {
    //   public bridge.SubClass();
    //   public void print(java.lang.String);
    //   public void print(java.lang.Object); // bridge method
    // }
    @Override
    public void print(String s) {
        System.out.println("s = " + s);
    }

    // 虚拟机自动实现的 bridge method
    // public void print(Object s) {
    // 调用 print(String s);
    //     print((String) s);
    // }

    // E:\dev-code\WorkSpace\child/utils\target\classes\bridge>javap -c SubClass.class
    // Compiled from "SubClass.java"
    // public class bridge.SubClass implements bridge.SuperClass<java.lang.String> {
    //   public bridge.SubClass();
    //     Code:
    //        0: aload_0
    //        1: invokespecial #1                  // Method java/lang/Object."<init>":()V
    //        4: return
    //
    //   public void print(java.lang.String);
    //     Code:
    //        0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
    //        3: new           #3                  // class java/lang/StringBuilder
    //        6: dup
    //        7: invokespecial #4                  // Method java/lang/StringBuilder."<init>":()V
    //       10: ldc           #5                  // String s =
    //       12: invokevirtual #6                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //       15: aload_1
    //       16: invokevirtual #6                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //       19: invokevirtual #7                  // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
    //       22: invokevirtual #8                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
    //       25: return
    //
    //   public void print(java.lang.Object);
    //     Code:
    //        0: aload_0
    //        1: aload_1
    //        2: checkcast     #9                  // class java/lang/String
    //        5: invokevirtual #10                 // Method print:(Ljava/lang/String;)V
    //        8: return
    // }

}
