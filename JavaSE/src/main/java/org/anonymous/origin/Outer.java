package org.anonymous.origin;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Objects;

/**
 * shadow
 * 2019/3/10 20:10
 * 服务的架构:
 * B/S : java --  browser --> server --> 数据库
 * 特点: 只需要一个浏览器   eg: 淘宝, 12306, 百度等
 * C/S : C++ --  client --> server --> 数据库
 * 特点: 需要在电脑上安装客户端.   eg: qq, 游戏客户端, 迅雷
 * 对比:
 * B/S架构
 * 优点: 服务器升级, 浏览器不需要升级
 * 缺点: 所有的压力都在服务器端, 效果不炫(ajax 可以解决这个缺点)
 * C/S架构
 * 优点: 效果炫酷
 * 缺点: 服务器升级, 客户端也需要升级
 * <p>
 * 数据资源:
 * 静态资源
 * 在页面写死的数据,不会随着时间变化的信息
 * 动态资源
 * 不是在页面写死的数据,页面上的数据随着时间变化, 这些数据一般来自数据库
 * <p>
 * WORA : write once run anywhere -- java 设计理念: 一次编写,到处运行
 * parameter : 变量
 * argument : 参数
 * garbage collection : 垃圾回收
 * stackOverflow overloading: 方法重载 -- 多态的形式之一
 * override: 重写(覆盖) 不同的类-- 多态的另一种形式--一个接口,多个方法 -- 父引用指向子对象,调用子类成员--动态的运行时多态
 * 重写遵循 “两同两小一大”: 同名、同参列表, 返回值/异常更小或相等, 访问权限更大或相等.
 * overloading: 同一个类多个方法，同名不同参（类型/顺序）。 在重载方法中的某一个方法被调用后，编译时期，编译器必须挑选出具体执行哪个方法，
 * 它通过用各种给出的参数类型与特定方法调用所使用的值类型进行匹配来挑选相应的方法。如果编译器找不到匹配的参数，就会产生编译时错误，
 * 因为根本不存在匹配。 （这个过程称为 重载解析 overloading resolution）
 * 想要完整描述一个方法，需要指出方法名及参数类型，这叫做方法的签名（signature）
 * recursion : 递归. 调用递归的方法称为是递归的(recursion)
 * factorial : 阶乘
 * access control : 访问控制 -- 在继承中至关重要
 * nested classes : 嵌套类
 * anonymous inner classes : 匿名内部类
 * regular expression: 正则表达式
 * access modifier: 访问修饰符
 * CamelCase: 驼峰命名法
 * primitive type: 基本类型. 4 种整型, 2 种浮点类型, 1 种表示 Unicode 编码的字符单元的字符类型 char, 1 种表示真假值的 boolean 类型
 * wrapper: 包装类
 * <p>
 * <p>
 * encapsulation: 封装（数据隐藏）
 * 实现封装的关键在于绝不能让类中的方法(stackOverflow)直接访问其他类的实例域(instance field,成员变量),程序仅通过对象的方法对对象数据进行交互
 * 封装给对象赋予了"黑盒"特征,这是提高 重用性 和 可靠性 的关键,这意味着一个类可以全面地改变存储数据的方式,只要仍旧使用同样的方法操作数据
 * 其他对象就不会知道或介意所发生的变化
 * 注: 并不是所有的类都具有面向对象特征. 比如 工具类, Math,Arrays,Date,Objects 等, 只需要知道 方法名和参数, 不必了解它的具体实现过程, 它不需要也不必隐藏数据(成员变量)
 * 由于没有数据(成员变量),因此也不必担心生成对象以及初始化实例域(成员变量)
 * inheritance: 继承 -- extends(扩展)
 * polymorphism: 多态 -- 一个对象变量可以指向多种实例类型(本类/子类)
 * -- 在运行时 jvm 能够自动的识别 该变量具体指向哪个(本类还是子类)对象,让后调用对应对象的方法 -- 动态绑定(dynamic binding)
 * -- 如果是 private/static/final 方法/构造方法, 编译器将可以准确知道应该调用哪个方法 -- 静态绑定(static binding)
 * jvm 会预先为每个类创建一个方法表(stackOverflow table),其中列出了所有方法的签名(方法名+参数类型)和实际调用的方法,在真正调用方法的时候,虚拟机仅查找表就可以了
 * inheritance hierarchy: 继承层次. 由一个公共超类派生出来的所有类的集合
 * inheritance chain: 继承链. 在继承层次中,从某个特定的类到其祖先(顶层超类)的路径
 * is-a 规则: 用来判断是否应该设计为继承关系的简单规则,它表明子类的每个对象也是超类的对象(多态)
 * -- 置换法则: 程序中出现超类对象的任何地方都可以用子类对象置换 (一个超类的变量可以接收其任意子类对象)
 * 对象的三个主要特征:
 * 对象的行为 behavior -- 可以对对象实施哪些操作,或可以对对象施加哪些方法 -- 可调用的方法
 * 对象的状态 state -- 当施加那些方法时,对象如何响应 -- 描述当前特征的信息
 * 对象的状态可能会随着时间而发生变化,但这种改变不会是自发的. 对象状态的该百年必须通过调用方法实现
 * 如果不经过方法调用就可以改变对象状态,只能说明封装性遭到破坏
 * 对象标识 identity -- 如何辨别具有相同行为与状态的不同对象 -- (不同内存地址/一般不同变量名)
 * 类和类的关系:
 * 依赖: uses-a  dependence. 一个类的方法操纵另一个类的对象
 * 注意: 应该尽可能的将相互依赖的类减少至最少.如果类 A 不知道 类 B 的存在, 它就不会关心 B 的任何改变
 * (这意味着 B 的改变不会导致 A 产生任何 BUG) -- 这就是让类之间的耦合度最小
 * 聚合: has-a aggregation. 类 A 的对象包含 类 B 的对象
 * 继承(inheritance): is-a 用来表示 特殊 与 一般的关系
 * constructor: 构造器(构造方法) -- 一种特殊的方法,用来构造并初始化对象
 * initialization block： 初始化块
 * <p>
 * equals 方法:
 * 全等校验: if (this == otherObject) return false;
 * 非空校验: if (otherObject == null) return false;
 * 同类校验: if (getClass() != otherObject.getClass()) return false; // if(!(otherObject instanceof ClassName)) return false;
 * <p>
 * 如果将一个类声明为 final,那么其中的方法自动的 final,不包括成员变量
 * inlining(内联): 如果一个方法没有被 override 且很短(如 JavaBean 的 get/set 方法),编译器就能对其进行优化处理,这个过程称为内联
 * 在内联调用中, e.getName() 将被替换为访问 e.name -- 提高效率
 * <p>
 * 如果子类的构造方法没有显示的调用超类的构造方法, 则在子类的构造方法中(第一个语句)默认调用超类的无参构造
 * 如果超类没有无参构造,并且在子类的构造方法中没有显示的调用超类的其他构造方法,则 Java 编译器将报告错误.
 * <p>
 * callAndPass by value : 按值调用
 * callAndPass by reference : 按引用调用
 * pass by value: java 中只有 按值传递 -- 基本数据类型 传值, 引用数据类型传地址
 * pass by reference:
 * <p>
 * UML: unified modeling language 统一建模语言 -- 用来绘制类图,描述类之间的关系
 * <p>
 * 0X 或 0x : 十六进制
 * 0B 或 0b : 二进制
 * "\u2122" : ™
 * "\u03C0" : π
 * "\u005B" : [
 * "\u005D" : ]
 * <p>
 * 继承的本质: 一旦创建 一个定义了对象一般属性的超类,该超类可以被继承以生成特殊用途的类.每个子类只增添它自己独特的属性
 * 多态: 父引用指向子对象的多态 是 运行时多态
 * equals相等,hashcode一定相等//hashcode相等,equals不一定相等(eg,"重地".hashcode() == "通话".hashcode())
 * synchronization : 同步
 * TCP/IP 为特定协议保留了低端的1024 个端口: 端口21是为FTP的，23是Telnet，25是为e-mail，79是为finger的，80是HTTP，119是为网络新闻
 * <p>
 * JFC:java foundation classes, java 基础类
 * <p>
 * object-orient: OO - 面向对象
 * object-oriented programming: OOP - 面向对象程序设计
 * java 特性: 简单,面向对象,分布式,健壮,安全性,体系结构中立,可移植性,解释型,高性能,多线程,动态
 * <p>
 * java SE: 标准版, standard edition (桌面或简单服务器)
 * java EE: 企业编, enterprise edition (服务端)
 * java ME: 微型版, micro edition (嵌入式)
 * <p>
 * jdk: java development kit, 1.2-1.4 版本 被称为 java SDK(software development kit, 软件开发包)
 * jre: java runtime environment, 包含 vm 但不包含 编译器(javac)
 * jvm: java virtual machine
 * <p>
 * IDE: integrated development environment
 * ANSI: American National Standards Institute 美国国家标准学会
 * ISO: International Standards Organization 国际标准化组织
 * GCC: GNU Compiler Collection GNU 编译器套件
 * 包括 C, C++, Objective-C, Fortran, Java, Ada, GO
 * FSF: free software foundation 自由软件基金会: 致力于推广自由软件,促进计算机用户自由的美国民间非营利性组织
 * 于 1985 年 10 月由 理查德·斯托曼(Richard Matthew Stallman) 建立. 其主要工作时执行 GUN计划, 开发更多的自由软件,完善自由软件理念
 * GNU 计划: "革奴计划", 由 理查德·脱丝曼 于 1983 年 9 月 27 日公开发起的 自由软件 集体协作计划. 目标是创建一套完全自由的操作系统 GUN.
 * GNU -- GNU's Not UNIX (递归缩写)
 * GNU GPL: GNU General Public License. GNU 通用公共许可证 ("公共版权")
 * Linus' Law: 林纳斯定律.  Given enough eyeballs, all bugs are shallow.
 * λ-calculus: lambda 演算
 * <p>
 * 所谓面向对象,即关注"想做什么"(结果,目的), 忽略"怎么做"(过程)
 * 面向过程 首先关心 如何操作数据，再决定如何组织数据（以便于数据操作） -- 先算法，再数据结构
 * OOP 相反，将数据放在第一位，然后再考虑操作数据的算法
 * generic type: 泛型类型 (模板)
 * <p>
 * auto-boxing: list.add(3) --> 自动转换为 list.add(Integer.valueOf(3))
 * auto-unboxing: int x = list.get(i) --> 自动转换为 int x = list.get(i).intValue()
 * <p>
 * toString 和 静态方法 valueOf() 互为 逆方法
 * <p>
 * functional interface: 函数式接口。 有且只有一个抽象方法的接口（可以有其他非抽象方法）
 * stackOverflow reference: 方法引用
 * object::instance: System.out.println(x) --> System.ou::println
 * Class::staticMethod: (x,y) -> Math.pow(x,y) --> Math::pow
 * Class::instanceMethod: (x,y) -> x.compareToIgnoreCase(y) --> String::compareToIgnoreCase //this/super::instanceMethod
 * x -> new int[x]  --> int[]::new
 * lambda 表达式中捕获的变量必须实际上是最终变量： effectively final -- 这个变量初始化之后不会再赋新值
 * <p>
 * exception handling：异常处理
 * 如果由于出现错误而使得某些操作没有完成，程序应该：
 * 1.返回到一种安全状态（比如数据库的"事务"机制），并能够让用户执行一些其他命令，
 * 2.或 允许用户保存所有操作的结果，并以妥善的方式终止程序
 * 要做到这些并不是一件更容易的事情,因为检测/引发错误条件的代码通常离那些能够让数据恢复到安全状态,或者能够保存用户的
 * 操作结果,并正常的退出程序的代码很远.
 * 异常处理的任务,就是将控制权从错误产生的地方转移给能够处理这种情况的错误处理器.
 * 为了能够在程序中处理异常情况,必须研究程序中可能会出现的错误和问题,一级哪些问题需要关注.
 * 1.用户输入错误
 * 除了那些不可避免的键盘输入错误,有些用户喜欢各行其是,不遵守程序的要求. 例如,假设有一个用户请求连接一个 url,而语法不正确
 * 在程序代码中应该对此进行检查,如果没有检查,网络层就会给出警告.
 * 2.设备错误
 * 硬件并不总是让它做什么,它就做什么.打印机可能随时被关闭;网页可能临时性的不能浏览,在一个任务的处理过程中,硬件经常出现问题.
 * 例如,打印机在打印过程中没纸了
 * 3.物理限制
 * 硬盘满了,可用存储空间已被用完
 * 4.代码错误
 * 程序方法有可能无法正确执行. 例如,方法可能返回一个错误的答案,或者错误的调用了其他方法.
 * 数组索引不合法,试图在散列表中查找一个不存在的记录,或者试图让一个空栈执行弹出操作,这些都属错误代码
 * 对于方法中的一个错误.传统做法是返回一个特殊的错误码.有调用方法分析. 如,对于一个从文件中读取信息的方法来说,返回值通常不是
 * 标准字符,而是一个 -1 或 null 引用,表示文件读取结束.
 * 但是,并不是任何情况下都可以返回错误码,有可能无法明确的将有效数据与无效数据加以区分
 * 一个返回整型的方法,就不能简单的通过返回 -1 表示错误,因为 -1 很可能是一个合法的结果而非错误
 * 如果一个方法不能采用正常的途径完成它的任务,就可以通过另外一个路径退出方法,在这种情况下这个方法不反悔任何值,而是抛出(throw)
 * 一个封装了错误信息的对象.需要注意的是,这个方法将会立即退出,并不返回任何值.此外,调用这个方法的代码也将无法继续执行,取而代之的是,
 * 异常处理机制开始搜索能够处理这种异常状况的异常处理器(exception handler)
 * <p>
 * 异常的层次结构:
 * Throwable: 异常(exception)和错误(error)的超类
 * error类: 描述了 Java 运行时系统的内部错误和资源耗尽错误,应用程序不应该抛出这种类型的对象,如果出现了这样的内部错误,除了通告用户,
 * 并尽力使程序安全的终止外,再也无能为力了. 但这种情况很少出现
 * exception: 这是需要主要关注的层次结构. 这个层次结构分解为两个分支: 一个分支派生出于 RuntimeException; 另一个分支包含其他异常.
 * 划分两个分支的规则是: 有程序错误导致的异常属于 RuntimeException, 而程序本身没有问题,但由于像 io 错误这类错误导致的异常属于其他异常.
 * 派生于 RuntimeException 的异常一般包含：（代码逻辑错误）
 * 错误的类型转化: classCastException
 * 数组访问越界： indexOutOfBoundsException
 * 访问 null 指针： NullPointerException
 * 非派生于 RuntimeException 的异常
 * 试图在文件尾部后面读取数据
 * 试图打开一个不存在的文件 FileNotFoundException -- 有可能文件在调用之前被删除了（这取决于环境，而不只是代码）
 * 试图根据给定的字符串查找 class 对象，而这个字符串表示的类并不存在 classNotFoundException
 * "如果出现了 RuntimeException, 那么一定是你的问题"
 * Java 语言规范将派生于 Error 类或 RuntimeException 类的所有异常称为 非受检查（unchecked）的异常,
 * 所有其他的异常称为 受查(checked)异常（编译时异常）
 * 根据 exception specification 异常规范，那些可能被他人使用的 Java 方法，应该在方法的 首部声明这个方法可能抛出的异常
 * 对于无法预知的 错误（比如，任何代码都有可能出现 iobe，则无需 throws ArrayIndexOutOfBoundsException）,无需抛出，交由虚拟机处理
 * 总之，一个方法必须声明所有可能抛出的受查异常，而 非受查异常 要么不可控制（error），要么就应该避免发生（RuntimeException）,
 * 如果方法没有声明所有可能发生的受查异常，编译器就会发出一个错误报告（即 编译时异常）
 * 如何判断一个异常应该抛出还是捕获：
 * 通常，应该捕获那些知道如何处理的异常，而将那些不知道怎样处理的异常继续进行传递（在方法中 throws 声明）
 */
public class Outer {
    private int a = 100;

    public void test() {
        for (int i = 0; i < 10; i++) {
            class Inner { //inner class
                void f() {
                    System.out.println(a--);
                }
            }
            //创建 inner class 对象
            Inner inner = new Inner();
            inner.f();
            Object[] o = {1, 2, 3, "1", '1', new ArrayList<>()};
        }
    }

    @Test
    public void f() {
        Outer outer = new Outer();
        outer.test();
    }

    @Test
    public void test1() {
        System.out.println("重地".hashCode() == "通话".hashCode()); //true
        System.out.println(Objects.equals("通话", "重地")); //false
    }
}

