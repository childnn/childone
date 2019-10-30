package test.swing.simple.option;

import org.junit.Test;

import javax.swing.JOptionPane;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/11 13:29
 * 输入对话框
 */
public class JOptionPaneInput {

    @Test
    public void test() {
        JOptionPane.showInputDialog("请输入");
    }

    @Test
    public void test1() {
        JOptionPane.showInputDialog(null, "请选择用户名", "选择用户名",
                JOptionPane.PLAIN_MESSAGE, null, new String[]{"jack", "rose", "tom"}, "tom");
    }

    @Test
    public void test2() {
        JOptionPane.showInputDialog("input", "tom");
    }

}
