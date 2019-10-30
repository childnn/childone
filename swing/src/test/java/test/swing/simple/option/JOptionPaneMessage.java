package test.swing.simple.option;

import org.junit.Test;

import javax.swing.JOptionPane;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/11 13:18
 * 消息对话框
 */
public class JOptionPaneMessage {

    @Test
    public void test() {
        JOptionPane.showMessageDialog(null, "用户名或密码错误！", "错误", JOptionPane.ERROR_MESSAGE);
    }

    @Test
    public void test1() {
        JOptionPane.showMessageDialog(null, "请注册或登录...", "提示", JOptionPane.INFORMATION_MESSAGE);
    }

    @Test
    public void test2() {
        JOptionPane.showMessageDialog(null, "普通会员无权执行删除操作！", "警告", JOptionPane.WARNING_MESSAGE);
    }

    @Test
    public void test3() {
        JOptionPane.showMessageDialog(null, "你是哪一位？请输入用户名", "问题", JOptionPane.QUESTION_MESSAGE);
    }

    @Test
    public void test4() {
        JOptionPane.showMessageDialog(null, "扫描完毕，没有发现病毒！", "提示", JOptionPane.PLAIN_MESSAGE);
    }

}
