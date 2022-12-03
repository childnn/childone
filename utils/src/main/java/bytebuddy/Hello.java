package bytebuddy;

import bytebuddy.t.Foo;
import bytebuddy.t.GreetingInterceptor;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.NamingStrategy;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import static net.bytebuddy.ClassFileVersion.JAVA_V8;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2022/03/26
 */
public class Hello {

    public static final String s = "fsdfsad";

    /*
        net.bytebuddy.ByteBuddy 实例不可变
        After seeing Byte Buddy's domain specific language in action, we need to have a short look at the way this language is implemented.
        The one detail you need to know about the implementation is that the language is built around immutable objects.
        As a matter of fact, almost every class that lives in the Byte Buddy namespace was made immutable
        and in the few cases we could not make a type immutable, we explicitly mention it in this class's javadoc.
        If you implement custom features for Byte Buddy, we recommend you to stick with this principle.
     */
    @Test
    public void immutable() {
        ByteBuddy byteBuddy = new ByteBuddy();
        // 注意: ByteBuddy 每个方法返回的都是新的实例, 而不是对原始 ByteBuddy 对象做配置
        ByteBuddy suffix = byteBuddy.with(new NamingStrategy.SuffixingRandom("suffix"));
        DynamicType.Unloaded<?> dynamicType = byteBuddy.subclass(Object.class).make();
        System.out.println(dynamicType.load(currentClassLoader()).getLoaded());
        System.out.println(suffix.subclass(Object.class).make().load(currentClassLoader()).getLoaded());
    }

    @Test
    public void redefine$rebase() throws InstantiationException, IllegalAccessException, IOException {
        Class<? extends Foo> name = new ByteBuddy()
                // .with(new NamingStrategy.PrefixingRandom("org.anonymous.bytebuddy"))
                .subclass(Foo.class)
                // .name("org.anonymous.bytebuddy.HelloTest")
                .method(ElementMatchers.named("name"))
                .intercept(FixedValue.value("Hello World!"))
                .make()
                .load(currentClassLoader()) // java.lang.IllegalStateException: Class already loaded: class bytebuddy.t.Foo
                .getLoaded(); //
        Foo foo1 = name.newInstance();
        System.out.println(foo1.name());
        // Foo foo = new Foo();
        // System.out.println(foo.name());

        // As the name suggests, these types are not loaded into the Java virtual machine.
        // Instead, classes created by Byte Buddy are represented in their binary form, in the Java class file format.
        DynamicType.Unloaded<Foo> unloaded = new ByteBuddy()
                .redefine(Foo.class)
                .make();

        // 将 class 输出到指定路径
        Map<TypeDescription, File> typeDescriptionFileMap = unloaded.saveIn(new File("src/main/java/bytebuddy"));
        //  it allows you to inject(File) classes into an existing jar file.
        // unloaded.inject()

        // redefine.make().
        // new ByteBuddy().rebase(Foo.class)
    }

    // public class Foo {
    //     String name() {
    //         return "name";
    //     }
    // }

    /**
     * @see net.bytebuddy.NamingStrategy
     * @see net.bytebuddy.NamingStrategy#BYTE_BUDDY_RENAME_PACKAGE
     */
    @Test
    public void test() throws InstantiationException, IllegalAccessException,
            NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        Class<?> dynamicType = new ByteBuddy(JAVA_V8)
                .subclass(Hello.class)
                // 指定全限定名: 不指定则生成默认 -- convention over configuration
                .name("org.anonymous.bytebuddy.HelloTest") // full qualified name
                .method(ElementMatchers.named("toString"))
                .intercept(FixedValue.value("Hello World!"))
                .make()
                .load(Hello.class.getClassLoader())
                .getLoaded();
        // Method test = dynamicType.getMethod("test");
        // test.invoke(dynamicType.newInstance());
        Field s = dynamicType.getField("s");
        s.setAccessible(true);
        System.out.println(s.get(dynamicType.newInstance()));
        // class bytebuddy.Hello$ByteBuddy$EAqIQf8j  -- 自动生成
        // class org.anonymous.bytebuddy.HelloTest   -- 指定全限定名
        System.out.println("dynamicType = " + dynamicType);

        System.out.println("dynamicType = " + dynamicType.newInstance()); // to string

        Class<? extends java.util.function.Function> dt = new ByteBuddy()
                .subclass(java.util.function.Function.class)
                .method(ElementMatchers.named("apply"))
                .intercept(MethodDelegation.to(new GreetingInterceptor()))
                .make()
                .load(Hello.class.getClassLoader())
                .getLoaded();

        System.out.println("dt.newInstance().apply(\"Byte Buddy\") = " + dt.newInstance().apply("Byte Buddy"));
    }

    @Test
    public void test2() {
        DynamicType.Loaded<Object> load = new ByteBuddy(JAVA_V8)
                // 指定生成的类名
                .with(new NamingStrategy.AbstractBase() {

                    /**
                     * Determines a new name when creating a new type that subclasses the provided type.
                     *
                     * @param superClass The super type of the created type.
                     * @return The name of the dynamic type.
                     */
                    @Override
                    protected String name(TypeDescription superClass) {
                        System.out.println("superClass = " + superClass);
                        return "i.love.ByteBuddy." + superClass.getSimpleName();
                    }
                })
                .subclass(Object.class)
                .make()
                .load(currentClassLoader());

        System.out.println("load = " + load);
        Class<?> loaded = load.getLoaded();
        System.out.println("loaded = " + loaded);


        // 指定前缀
        Class<?> loaded1 = new ByteBuddy(JAVA_V8)
                .with(new NamingStrategy.PrefixingRandom("org.anonymous.bytebuddy"))
                .subclass(Object.class)
                .make()
                .load(currentClassLoader())
                .getLoaded();
        System.out.println("loaded1 = " + loaded1);

    }

    public static ClassLoader currentClassLoader() {
        return Hello.class.getClassLoader();
    }

}

