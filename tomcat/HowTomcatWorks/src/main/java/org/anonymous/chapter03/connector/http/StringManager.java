package org.anonymous.chapter03.connector.http;

import java.text.MessageFormat;
import java.util.Hashtable;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/10/19 20:11
 * 一个像 Tomcat 这样的大型应用需要仔细的处理错误信息。在 Tomcat 中，错误信息对于系统
 * 管理员和 servlet 程序员都是有用的。例 如，Tomcat 记录错误信息，让系统管理员可以定位发
 * 生 的 任 何 异 常 。 对 servlet 程 序 员 来 说 ， Tomcat 会 在 抛 出 的 任 何 一 个
 * javax.servlet.ServletException 中发送一个错误信息，这样程序员可以知道他/她的 servlet
 * 究竟发送什么错误了。
 * Tomcat 所采用的方法是在一个属性文件里边存储错误信息，这样，可以容易的修改这些信
 * 息。不过，Tomcat 中有数以百计的类。把所有类使用的错误信 息存储到一个大的属性文件里边
 * 将会容易产生维护的噩梦。为了避免这一情况，Tomcat 为每个包都分配一个属性文件。例如，
 * 在包 org.apache.catalina.connector 里边的属性文件包含了该包所有的类抛出的所有错误信
 * 息。每个属性文件都会被一个 org.apache.catalina.util.StringManager 类的实例所处理。当
 * Tomcat 运行时，将会有许多 StringManager 实例，每个实例会读取包对应的一个属性文件。此
 * 外，由于 Tomcat 的受欢迎程度，提供多种语言的错误信息也是有意义的。目前，有三种语言是
 * 被支持的。英语的错误信息属性文件名为 LocalStrings.properties。另外两个是西班牙语和日
 * 语，分别放在 LocalStrings_es.properties 和 LocalStrings_ja.properties 里边。
 * 当包里边的一个类需要查找放在该包属性文件的一个错误信息时，它首先会获得一个
 * StringManager 实例。不过，相同包里边的许多类可能也需要 StringManager，为每个对象创建
 * 一个 StringManager 实例是一种资源浪费。因此，StringManager 类被设计成一个 StringManager
 * 实例可以被包里边的所有类共享。假如你熟悉设计模式，你将会正确的猜到 StringManager 是一
 * 个单例 (singleton)类。仅有的一个构造方法是私有的，所有你不能在类的外部使用 new 关键字
 * 来实例化。你通过传递一个包名来调用它的公共静态方法 getManager 来获得一个实例。每个实
 * 例存储在一个以包名为键(key)的 Hashtable 中。
 *
 */
public class StringManager {

    private static final Hashtable<String, StringManager> managers = new Hashtable<>();
    private final ResourceBundle bundle;
    private StringManager(String packageName) {
        String bundleName = packageName + ".LocalStrings";
        this.bundle = ResourceBundle.getBundle(bundleName);
    }

    public static void main(String[] args) {
        ResourceBundle.getBundle("LocalStrings");
    }

    public static synchronized StringManager getManager(String packageName) {
        packageName = "i18n"; // 固定
        StringManager mgr = managers.get(packageName);
        if (mgr == null) {
            mgr = new StringManager(packageName);
            managers.put(packageName, mgr);
        }

        return mgr;
    }

    public String getString(String key) {
        String str;
        if (key == null) {
            str = "key is null";
            throw new NullPointerException(str);
        } else {
            str = null;

            try {
                str = this.bundle.getString(key);
            } catch (MissingResourceException var3) {
                str = "Cannot find message associated with key '" + key + "'";
            }

            return str;
        }
    }

    public String getString(String key, Object arg) {
        Object[] args = new Object[]{arg};
        return this.getString(key, args);
    }

    public String getString(String key, Object arg1, Object arg2) {
        Object[] args = new Object[]{arg1, arg2};
        return this.getString(key, args);
    }

    public String getString(String key, Object arg1, Object arg2, Object arg3) {
        Object[] args = new Object[]{arg1, arg2, arg3};
        return this.getString(key, args);
    }

    public String getString(String key, Object arg1, Object arg2, Object arg3, Object arg4) {
        Object[] args = new Object[]{arg1, arg2, arg3, arg4};
        return this.getString(key, args);
    }

    public String getString(String key, Object[] args) {
        String iString = null;
        String value = this.getString(key);

        int i;
        try {
            Object[] nonNullArgs = args;

            for (i = 0; i < args.length; ++i) {
                if (args[i] == null) {
                    if (nonNullArgs == args) {
                        nonNullArgs = args.clone();
                    }

                    nonNullArgs[i] = "null";
                }
            }

            iString = MessageFormat.format(value, nonNullArgs);
        } catch (IllegalArgumentException var7) {
            StringBuffer buf = new StringBuffer();
            buf.append(value);

            for (i = 0; i < args.length; ++i) {
                buf.append(" arg[" + i + "]=" + args[i]);
            }

            iString = buf.toString();
        }

        return iString;
    }
}
