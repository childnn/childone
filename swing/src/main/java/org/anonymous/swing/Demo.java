package org.anonymous.swing;

import javax.swing.*;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/21 22:20
 * @see java.awt.Component
 *      @see java.awt.Container
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
 * @see JComponent#getInputMap()
 * @see KeyStroke
 * @see javax.swing.text.DefaultEditorKit
 *
 * 限制文本域中输入字符长度.
 * @see javax.swing.text.DocumentFilter
 * @see javax.swing.event.DocumentListener
 *
 * 按钮控件:
 * @see AbstractButton
 * @see JToggleButton
 * @see JCheckBoxMenuItem
 * @see JRadioButtonMenuItem
 * @see JButton
 * @see JMenuItem
 * @see JMenu
 * @see JCheckBoxMenuItem
 * @see JRadioButtonMenuItem
 */
public class Demo {

}
