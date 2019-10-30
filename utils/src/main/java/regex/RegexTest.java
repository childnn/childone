package regex;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/1/4 18:55
 * idea 匹配空行: ^\s
 */
public class RegexTest {
    void p(Object o) {
        System.out.println("o = " + o);
    }

    /**
     * .	Any character (may or may not match line terminators), 任意字符
     * X?	X, once or not at all       零个或一个
     * X*	X, zero or more times       零个或多个
     * X+	X, one or more times        一个或多个
     * X{n}	X, exactly n times          x出现n次
     * X{n,}	X, at least n times     x出现至少n次
     * X{n,m}	X, at least n but not more than m times 出现n~m次
     * -----------------------------------------------------
     * 范围:
     * [n-m] n-m的数字
     * [^abc] 除 abc 之外的字符
     * [a-zA-z] 字母.
     * [a-z|A-z] 同上.
     * [a-z[A-Z]] 同上.
     * [A-Z&&[REQ]] A~Z 且属于 REQ 之一的字符.
     * ----------------------------------------------------
     * \\\\ 匹配一个 \
     * \d  a digit: [0-9] 数字
     * \D  a non-digit: [^0-9] 非数字
     * \s  a whitespace character: [\t\n\x0B\f\r] 空格
     * \S  a non-whitespace character: [^\s] 非空格
     * \w  a word character: [a-zA-Z_0-9] 数字,字母,下划线
     * \W  a non-word character: [^\w] 非数字字母下划线
     * --------------------------------------------------------
     * ^ 在括号中表示取反, 不在则表示正则的开头.
     * $ 正则结尾.
     * [^..] 取反
     * ^[abc]$  开头与结尾
     * \b 单词的边界, 可以是空格, 换行符等.
     */
    @Test
    public void test() {
        // . 表示任意字符
        p("a".matches("."));
        // \\s{4} 表示 4 个空白符
        p(" \n\r\t".matches("^\\s{4}$"));
        // \\w{3} 表示数字字母下划线, 3 个字符
        p("a_8".matches("^\\w{3}$"));
        // 匹配 \
        p("\\".matches("^\\\\$"));

        String str = "hello sir";
        p(str.matches("^h.*$"));
        p(str.matches("^h[a-z]{1,3}o\\b.*$"));
        p("hellosir".matches("^h[a-z]{1,3}o\\b.*$"));

        // 判断空白行: 空白行可能包括空格,制表符等.
        // [\s&&[^\n]]* 是空格但不是换行符, \n 表示最后以换行符结束.
        p("\n".matches("^[\\s&&[^\n]]*\\n$"));
        // 邮箱: [\w.-]+ 一个或多个数字字母下划线 . - 组成, @ 连接, \. 匹配点., 最后是一个或多个
        p("abc.xyz@abc.com".matches("^[\\w.-]+@[\\w.-]+\\.[\\w]+$"));
    }

    @Test
    public void test1() {
        Pattern pattern = Pattern.compile("\\d{3,5}"); // 不能
        String s = "123-34345-234-00";
        Matcher m = pattern.matcher(s);

        //先演示matches(), 与整个字符串匹配.
        p(m.matches());
        //结果为false, 显然要匹配3~5个数字会在-处匹配失败

        //然后演示find(), 先使用reset()方法把当前位置设置为字符串的开头
        m.reset();
        p(m.find());//true 匹配123成功
        p(m.find());//true 匹配34345成功
        p(m.find());//true 匹配234成功
        p(m.find());//false 匹配00失败

        //下面我们演示不在matches()使用reset(), 看看当前位置的变化
        m.reset();//先重置
        p(m.matches());//false 匹配整个字符串失败, 当前位置来到-
        p(m.find());// true 匹配34345成功
        p(m.find());// true 匹配234成功
        p(m.find());// false 匹配00始边
        p(m.find());// false 没有东西匹配, 失败

        //演示lookingAt(), 从头开始找
        p(m.lookingAt());//true 找到123, 成功

    }

    @Test
    public void test2() {
        Pattern pattern = Pattern.compile("\\d{3,5}");
        String s = "123-34345-234-00";
        Matcher m = pattern.matcher(s);

        p(m.find());//true 匹配123成功
        p("start: " + m.start() + " - end:" + m.end());
        p(m.find());//true 匹配34345成功
        p("start: " + m.start() + " - end:" + m.end());
        p(m.find());//true 匹配234成功
        p("start: " + m.start() + " - end:" + m.end());
        p(m.find());//false 匹配00失败
        try {
            p("start: " + m.start() + " - end:" + m.end());
        } catch (Exception e) {
            System.out.println("报错了...");
        }
        p(m.lookingAt());
        p("start: " + m.start() + " - end:" + m.end());
    }

    /**
     * @see Matcher#replaceAll(java.lang.String)
     * @see Matcher#replaceFirst(java.lang.String)
     */
    @Test
    public void test3() {
        Pattern p = Pattern.compile("java");
        Matcher m = p.matcher("java Java JAVA JAva I love Java and you");
        p(m.replaceAll("JAVA")); // replaceAll()方法会替换所有匹配到的字符串
    }

    // 替换.
    @Test
    public void test4() {
        Pattern p = Pattern.compile("java", Pattern.CASE_INSENSITIVE); // 指定为大小写不敏感的
        Matcher m = p.matcher("java Java JAVA JAva I love Java and you");
        p(m.replaceAll("JAVA"));
    }

    //
    @Test
    public void test5() {
        Pattern p = Pattern.compile("java", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher("java Java JAVA JAva I love Java and you ?");
        StringBuffer sb = new StringBuffer();
        int index = 1;
        while (m.find()) {
            m.appendReplacement(sb, (index++ & 1) == 0 ? "java" : "JAVA"); // 较为简洁的写法
           /* if ((index & 1) == 0) { // 偶数
                m.appendReplacement(sb, "java");
            } else {
                m.appendReplacement(sb, "JAVA");
            }
            index++;*/
        }
        m.appendTail(sb); // 把剩余的字符串加入
        p(sb);
    }

    // 分组. 正则表达式"\\d{3,5}[a-z]{2}"表示3~5个数字跟上两个字母
    @Test
    public void test6() {
        Pattern p = Pattern.compile("\\d{3,5}[a-z]{2}");
        String s = "123aa-5423zx-642oi-00";
        Matcher m = p.matcher(s);
        while (m.find()) {
            p(m.group());
        }
    }

    /**
     * 如果想要打印每个匹配串中的数字, 如何操作呢.
     * <p>
     * 首先你可能想到把匹配到的字符串再进行匹配, 但是这样太麻烦了, 分组机制可以帮助我们在正则表达式中进行分组.
     * 规定使用()进行分组, 这里我们把字母和数字各分为一组"(\\d{3,5})([a-z]{2})"
     * 然后在调用m.group(int group)方法时传入组号即可
     * 注意, 组号从0开始, 0组代表整个正则表达式,
     * 从0之后, 就是在正则表达式中从左到右每一个左括号对应一个组. 在这个表达式中第1组是数字, 第2组是字母.
     */
    @Test
    public void test7() {
        Pattern p = Pattern.compile("(\\d{3,5})([a-z]{2})"); // 正则表达式为3~5个数字跟上两个字母
        String s = "123aa-5423zx-642oi-00";
        Matcher m = p.matcher(s);
        while (m.find()) {
//            p(m.group(0)); // 等价于 m.group()
//            p(m.group(1));
            p(m.group(2));
        }
    }
}
