import org.junit.jupiter.api.Test;

import java.text.ChoiceFormat;
import java.text.MessageFormat;
import java.text.ParsePosition;
import java.util.Arrays;
import java.util.Date;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see MessageFormat
 * @see MessageFormat#format(String, Object...) 一次性
 * @see MessageFormat#MessageFormat(String)
 * @since 2020/10/24 16:01
 */
public class MessageFormatTest {

    /**
     * @see MessageFormat#TYPE_KEYWORDS
     * @see MessageFormat#NUMBER_MODIFIER_KEYWORDS
     * @see MessageFormat#DATE_TIME_MODIFIERS
     */
    @Test
    public void test() {
        // 注意在格式化字符串中 英文 单/双引号的表示方法
        String msg = "At ''{1, time}'' on \"{1,date}\", there was {2} on planet {0,number,integer}.";
        int planet = 7;
        String event = "a disturbance in the Force";

        String result = MessageFormat.format(msg, planet, new Date(), event);
        System.out.println("result = " + result);
    }

    // number
    @Test
    public void test1() {
        // 数字格式化与 #
        String msg = "{0, number, $'#'.##}";
        float number = 31.4567f;
        String result = MessageFormat.format(msg, number);
        System.out.println("result = " + result);
    }

    /**
     * @see MessageFormat#MessageFormat(String) 多次使用
     */
    @Test
    public void test2() {
        int fileCount = 1273;
        String diskName = "MyDisk";

        String msg = "The disk ''{1}'' contains {0} file(s).";
        MessageFormat format = new MessageFormat(msg);
        String result = format.format(new Object[]{fileCount, diskName});
        System.out.println("result = " + result);

        String res = format.format(new Object[]{0, diskName});
        System.out.println("res = " + res);

    }

    @Test
    public void test3() {
        MessageFormat form = new MessageFormat("The disk \"{1}\" contains {0}.");
        double[] filelimits = {0, 1, 2};
        String[] filepart = {"no files", "one file", "{0,number} files"};
        ChoiceFormat fileform = new ChoiceFormat(filelimits, filepart);
        form.setFormatByArgumentIndex(0, fileform);

        int fileCount = 0;
        String diskName = "MyDisk";
        Object[] testArgs = {fileCount, diskName};

        System.out.println(form.format(testArgs));
    }

    @Test
    public void test4() {
        MessageFormat form = new MessageFormat("The disk \"{1}\" contains {0}.");
        // sub-formats, cause recursion
        form.applyPattern("There {0,choice,0#are no files|1#is one file|1<are {0,number,integer} files}.");

        int fileCount = 1;
        String diskName = "MyDisk";
        Object[] testArgs = {fileCount, diskName};

        System.out.println(form.format(testArgs));
    }

    @Test
    public void test5() {
        MessageFormat mf = new MessageFormat("{0,number,#.##}, {0,number,#.#}");
        Object[] objs = {3.1415};
        String result = mf.format(objs);
        // result now equals "3.14, 3.1"
        System.out.println("result = " + result);

        objs = mf.parse(result, new ParsePosition(0));
        // objs now equals {new Double(3.1)}
        System.out.println("objs = " + Arrays.toString(objs));
    }

    @Test
    public void test6() {
        MessageFormat mf = new MessageFormat("{0}, {0}, {0}");
        String forParsing = "x, y, z";
        Object[] objs = mf.parse(forParsing, new ParsePosition(0));
        // result now equals {new String("z")}
        System.out.println("objs = " + Arrays.toString(objs));
    }

}
