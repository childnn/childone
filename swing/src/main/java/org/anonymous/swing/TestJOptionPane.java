package org.anonymous.swing;

import javax.swing.*;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see javax.swing.JOptionPane
 * 弹出对话框, 要求用户提供值或者发出通知
 * @see javax.swing.JOptionPane#showConfirmDialog 询问一个确认问题, 例如 yes/no/concel
 * @see javax.swing.JOptionPane#showInputDialog 提示要求输入某些信息
 * @see javax.swing.JOptionPane#showMessageDialog 通知信息
 * @see javax.swing.JOptionPane#showOptionDialog 上述三者统一
 * 关于参数:
 * parentComp: 定义此对话框的父窗口组件. 该参数有两种使用方式, 一种是将包含对话框的 Frame 作为
 * 参数值, 对话框的位置使用其屏幕坐标, 一般情况下, 将对话框紧靠组件置于其之下; 另一种是使用 null,
 * 在这种情况下, 将使用默认的 Frame 作为父窗口, 并且对话框将剧中位于屏幕上.
 * message:
 * 对话框中的描述信息, 通常 String 类型
 * msgType:
 * message 样式. 外观管理器布置的对话框可能因此而异, 并且通常提供默认图标.
 * {@link JOptionPane#ERROR_MESSAGE}
 * {@link JOptionPane#INFORMATION_MESSAGE}
 * {@link JOptionPane#WARNING_MESSAGE}
 * {@link JOptionPane#QUESTION_MESSAGE}
 * {@link JOptionPane#PLAIN_MESSAGE}
 * optionType:
 * 定义在对话框底部显示选项按钮的集合. 该参数值可以为
 * {@link JOptionPane#DEFAULT_OPTION}
 * {@link JOptionPane#YES_NO_OPTION}
 * {@link JOptionPane#YES_NO_CANCEL_OPTION}
 * {@link JOptionPane#OK_CANCEL_OPTION}
 * title: 标题
 * icon: 图标
 * @see test.swing.simple.option.JOptionPaneConfirm
 * @since 2021/12/15
 */
public class TestJOptionPane {


}
