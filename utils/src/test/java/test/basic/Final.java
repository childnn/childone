package test.basic;

/**
 * final :
 *   1. 类：被 final 修饰的类没有子类，无法被继承
 *   2. 方法：被 final 的方法，不能被重写
 *   3. 变量：被 final 的变量，不能被重新赋值。
 *          即 自定义常量：public static final 数据类型 常量名 = 初始化值;
 *          static：类名调用
 *          final：无法改变
 *          常量名：全部大写，单词之间用下划线_分隔
 *
 *  权限修饰符：      public            protected             (default)          private
 *  同一个类                                                                      同类
 *  同一个包                                                    同包
 *  不同包子类                          子类（继承）
 *  不同包非子类
 *  public：只针对当前模块 module
 *  private：同类
 *  protected：继承关系，子父类
 */
public class Final {

//    final int a;
//    a = 20;  // error， 不能分开赋值，必须一次完成

    public static void main(String[] args) {
        final int num; // 与成员变量不同，局部变量可以分开赋值
        num = 20;
//        num = 30; // error, 无法重新赋值
        // 对于基本数据类型，不可变的是数值
        // 对于引用数据类型，不可变的是地址值（属性可变）
    }
}
