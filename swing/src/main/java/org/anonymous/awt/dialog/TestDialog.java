package org.anonymous.awt.dialog;

import org.anonymous.util.AwtUtil;

import java.awt.*;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see java.awt.Dialog
 * @since 2021/12/15
 */
public class TestDialog {

    private final Frame f = new Frame("frame");
    // 参数一: 该对话框宿主
    // 参数二: 对话框标题
    // 参数三: 该对话框选取的模式, true 则 dialog 将阻断输入到其他应用程序窗口显示的内容
    // 参数四: 目标屏幕设备的 GraphicsConfiguration,
    //   如果为 null, 则使用所拥有的 Dialog 相同的 GraphicsConfiguration
    // 这里设置 true, Dialog 会强制置顶
    Dialog d = new Dialog(f, "Dialog", false);

    public static void main(String[] args) {
        TestDialog td = new TestDialog();
        td.init();
    }

    private void init() {
        // 定义一个按钮, 并添加单击事件弹出对话框
        Button b = new Button("Click Me");
        b.addActionListener(e -> {
            d.setVisible(true);
            AwtUtil.closeWindow(d);
        });

        // 新建一个标签
        Label l = new Label("My First Dialog", Label.CENTER);
        // 将标签放置于对话框 Dialog 中
        d.add(l);
        // 设置对话框起始位置和大小
        d.setBounds(550, 350, 200, 120);
        // 设置 Frame 为流布局
        f.setLayout(new FlowLayout());
        f.add(b);

        // 设置初始位置和大小
        f.setBounds(500, 300, 200, 120);

        f.setVisible(true);

        AwtUtil.closeWindow(f);
    }

}
