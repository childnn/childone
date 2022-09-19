package org.anonymous.swing;

import org.anonymous.util.AwtUtil;
import org.junit.Test;

import java.awt.*;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/21 22:20
 * @see java.awt.Component 普通组件顶层接口: 位置, 大小, 可见性, 添加其他组件, 获取指定点的组件...
 *      - {@link java.awt.Container} 可以容纳其他 Component
 *         -- {@link java.awt.Window} 可独立存在的顶级窗口
 *            --- {@link java.awt.Frame} 窗口: 有标题, 允许拖拽改变位置,大小. 默认不可见, 可设置可见性. 默认使用 {@link java.awt.BorderLayout} 布局
 *            --- {@link java.awt.Dialog} 对话框
 *         -- {@link java.awt.Panel} 可作为容器容纳其他组件, 但不可独立存在, 必须被添加到其他容器中. 默认使用 {@link java.awt.FlowLayout} 布局管理器
 *         -- {@link java.awt.ScrollPane} 带滚动条的容器. 不可独立存在. 默认使用 {@link java.awt.BorderLayout} 布局管理器.
 *             此容器通常用于盛装其他容器, 通常不允许改变 ScrollPane 布局管理器.
 *      - {@link java.awt.TextField}
 *      - {@link java.awt.List}
 *      - {@link java.awt.Button}
 *          @see java.awt.Window
 *              @see java.awt.Frame
 *                  @see javax.swing.JFrame
 *              @see javax.swing.JWindow
 *              @see java.awt.Dialog
 *                  @see java.awt.FileDialog
 *                  @see javax.swing.JDialog
 *          @see java.awt.Panel
 *              @see java.applet.Applet
 *              @see javax.swing.JApplet
 *
 * @see java.awt.MenuComponent 菜单组件顶层接口
 *      - {@link java.awt.MenuBar} 菜单条
 *      - {@link java.awt.MenuItem} 菜单项
 *      - {@link java.awt.Menu}
 * @see java.awt.LayoutManager 布局管理器
 *
 *
 * Swing 中的控件需要放到容器类中显示. Swing 中提供了 3 个有用的顶层容器类,
 * {@link javax.swing.JFrame}, {@link javax.swing.JDialog} {@link javax.swing.JApplet}
 * 注意事项:
 * 1. Swing 中的控件必须放置在顶层容器类中才能显示;
 * 2. 每个控件只能在容器中添加一次, 如果想将容器中的控件增加到另一个容器中, 则将移除第一个容器中的控件.
 * 3. 每个顶层容器都有一个 内容窗格, 用来保存添加的控件;
 * 4. 可以在顶层容器中增加 菜单栏. 菜单栏通常位于顶层容器中, 但是不在内容窗格中.
 * 5. 菜单栏通常仅用于 JFrame, JAppplet.
 *
 * 文本控件:
 * @see java.awt.Container
 *      @see javax.swing.JComponent
 *      @see javax.swing.text.JTextComponent
 *          @see javax.swing.JTextField                         -- 显示单行无格式文本.
 *              @see javax.swing.JPasswordField                 -- 显示单行密码.
 *              @see javax.swing.JFormattedTextField            -- 获得指定格式的文本.
 *          @see javax.swing.JTextArea                          -- 显示多行无格式的文本.
 *          @see javax.swing.JEditorPane                        -- 显示多行有格式文本.
 *              @see javax.swing.JTextPane
 *
 * 键盘操作
 * @see java.awt.event.KeyEvent
 * @see javax.swing.InputMap
 * @see javax.swing.JComponent#getInputMap()
 * @see javax.swing.KeyStroke
 * @see javax.swing.text.DefaultEditorKit
 *
 * 限制文本域中输入字符长度.
 * @see javax.swing.text.DocumentFilter
 * @see javax.swing.event.DocumentListener
 *
 * 按钮控件:
 * @see javax.swing.AbstractButton
 * @see javax.swing.JToggleButton
 * @see javax.swing.JCheckBoxMenuItem
 * @see javax.swing.JRadioButtonMenuItem
 * @see javax.swing.JButton
 * @see javax.swing.JMenuItem
 * @see javax.swing.JMenu
 * @see javax.swing.JCheckBoxMenuItem
 * @see javax.swing.JRadioButtonMenuItem
 */
public class AWTFramework {

    /**
     * 使用 Panel 作为容器来盛装一个文本框和一个按钮
     * 并将 Panel 对象添加到 Frame 对象中
     */
    public static void main(String[] args) {
        Frame f = new Frame("Panel");

        Panel p = new Panel();
        p.add(new TextField("文本框", 20));
        Button bt = new Button("别点我");
        // bt.setSize(30, 200);
        p.add(bt);

        f.add(p);
        f.setBounds(230, 230, 400, 400);

        f.setVisible(true);

        AwtUtil.closeWindow(f);
    }


    @Test
    public void panel() {

    }

}
