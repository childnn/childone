package test.swing.simple.select.combobox;

import javax.swing.*;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/8 18:19
 * 下拉框
 * ComboBox 能够响应 ItemEvent 事件和 ActionEvent 事件，
 * 其中 ItemEvent 触发的时机是当下拉列表框中的所选项更改时，
 * ActionEvent 触发的时机是当用户在 JComboBox 上直接输入选择项并回车时。
 * 要处理这两个事件，需要创建相应的事件类并实现 ItemListener 接口和 ActionListener 接口。
 */
public class JComboBoxDemo {

    public static void main(String[] args) {
        JFrame jf = new JFrame("Swing-Combobox"); // 窗口
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setBounds(300, 100, 500, 500);

        JPanel jp = new JPanel(); // 面板

        JLabel jl = new JLabel("Please select");
        jp.add(jl);

        JComboBox<String> jcb = new JComboBox<>();
        jcb.addItem("身份证");
        jcb.addItem("驾驶证");
        jcb.addItem("军官证");
        jp.add(jl);
        jp.add(jcb);

        jf.add(jp);
        jf.setVisible(true);
    }

}
