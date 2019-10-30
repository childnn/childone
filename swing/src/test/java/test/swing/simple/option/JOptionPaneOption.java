package test.swing.simple.option;

import org.junit.Test;

import javax.swing.JOptionPane;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/11 13:40
 * 选项对话框
 */
public class JOptionPaneOption {

    @Test
    public void test() {
        JOptionPane.showOptionDialog(null, "what's your name?", "title",
                // optionType: 选择按钮类别; messageType: 图标类型
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, new String[]{"jack", "rose", "tom"}, "tom");
    }

}
