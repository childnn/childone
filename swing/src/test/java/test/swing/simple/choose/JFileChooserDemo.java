package test.swing.simple.choose;

import javax.swing.*;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/10 17:57
 * 文件选择器
 * showOpenDialog() 方法将返回一个整数，可能取值情况有 3 种：
 * JFileChooser.CANCEL—OPTION、JFileChooser.APPROVE_OPTION 和 JFileChooser.ERROR_OPTION，
 * 分别用于表示单击“取消”按钮退出对话框，无文件选取、正常选取文件和发生错误
 * 或者对话框已被解除而退出对话框。因此在文本选择器交互结束后，
 * 应进行判断是否从对话框中选择了文件，然后根据返回值情况进行处理。
 */
public class JFileChooserDemo {

    private JLabel label = new JLabel("所选文件路径: ");
    private JTextField textField = new JTextField(25);
    private JButton bt = new JButton("浏览");

    public JFileChooserDemo() {
        JFrame jf = new JFrame("文件选择器");
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setLocation(700, 400);

        JPanel jp = new JPanel();
        jp.add(label);
        jp.add(textField);
        jp.add(bt);

        jf.add(jp);

        jf.pack();
        jf.setVisible(true);

        // 监听按钮, 打开文件
        bt.addActionListener(e -> {
            JFileChooser jfc = new JFileChooser("c:/");
            int val = jfc.showOpenDialog(null);
            String msg;
            // 正常选择文件
            if (val == JFileChooser.APPROVE_OPTION) {
                msg = jfc.getSelectedFile() + "";
            } else {
                msg = "文件未选择";
            }
            textField.setText(msg);
        });
    }

    public static void main(String[] args) {
        new JFileChooserDemo();
    }
}
