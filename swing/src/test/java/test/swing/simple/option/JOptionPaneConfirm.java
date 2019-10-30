package test.swing.simple.option;

import org.junit.Test;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/11 13:03
 * 对话框:
 * 确认对话框, 消息对话框, 输入对话框, 选项对话框
 */
public class JOptionPaneConfirm {

    @Test
    public void test() {
        JFrame jf = new JFrame();
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JOptionPane.showConfirmDialog(jf, "确定要删除吗?", "删除提示", JOptionPane.YES_NO_OPTION);

        jf.pack();
        jf.setVisible(true);
    }

    @Test
    public void test1() {
        JFrame jf = new JFrame();
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JOptionPane.showConfirmDialog(jf, "确定要删除吗?", "删除提示", JOptionPane.YES_NO_CANCEL_OPTION);

        jf.pack();
        jf.setVisible(true);
    }

    @Test
    public void test2() {
        JFrame jf = new JFrame();
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JOptionPane.showConfirmDialog(jf, "确定要删除吗?", "删除提示", JOptionPane.OK_CANCEL_OPTION);

        jf.pack();
        jf.setVisible(true);
    }

}
