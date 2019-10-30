package test.swing.simple.choose;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Color;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/10 18:57
 * 颜色选择器
 * JColorChooser 类提供一个用于允许用户操作和选择颜色的控制器窗格。该类提供三个级别的 API：
 * 显示有模式颜色选取器对话框并返回用户所选颜色的静态便捷方法。
 * 创建颜色选取器对话框的静态方法，可以指定当用户单击其中一个对话框按钮时要调用的 ActionListener。
 * 能直接创建 JColorChooser 窗格的实例（在任何容器中），可以添加 PropertyChange 作为监听器以检测当前“颜色”属性的更改。
 * -----
 * 一般使用 JColorChooser 类的静态方法 showDialog(Component component,String title,Color initialColor)
 * 创建一个颜色对话框，在隐藏对话框之前一直堵塞进程。
 * 其中 component 参数指定对话框所依赖的组件，title 参数指定对话框的标题，
 * initialColor 参数指定对话框返回的初始颜色，即对话框消失后返回的默认值。
 */
public class JColorChooserDemo {

    public static void main(String[] args) {
        JFrame jf = new JFrame("颜色选择器");
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JColorChooser.showDialog(jf, "color", Color.GREEN);


        jf.pack();
        jf.setVisible(true);
    }

}
