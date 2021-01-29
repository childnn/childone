package org.anonymous.swing.layout;

import java.awt.Button;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see java.awt.GridBagLayout
 * @see java.awt.GridBagConstraints#gridx 该对象控制的 GUI 组件左上角所在网格的横向索引
 * @see java.awt.GridBagConstraints#gridy .. 纵向索引
 * GridBagLayout 左上角网格的索引为 (0,0), 上述两个值默认为 {@link java.awt.GridBagConstraints#RELATIVE}
 * 它表明当前组件紧跟上一组件之后.
 * @see java.awt.GridBagConstraints#gridwidth 横向跨越
 * @see java.awt.GridBagConstraints#gridheight 纵向跨越
 * 以上两值默认为 1. 如果设置这俩属性值为 {@link java.awt.GridBagConstraints#REMAINDER}, 表明该对象控制的 GUI 组件
 * 是横向, 纵向最后一个组件; 如果这俩值为 {@link java.awt.GridBagConstraints#RELATIVE}, 表明该对象控制的 GUI 组件是
 * 横向, 纵向倒数第二个组件.
 * @see java.awt.GridBagConstraints#fill 设置受该对象控制的 GUI 组件如何占据空白区域.
 * 该属性取值如下:
 * @see java.awt.GridBagConstraints#NONE GUI 组件不扩大
 * @see java.awt.GridBagConstraints#HORIZONTAL GUI 组件水平扩大以占据空白区域
 * @see java.awt.GridBagConstraints#VERTICAL  GUI 组件垂直扩大以占据空白区域
 * @see java.awt.GridBagConstraints#BOTH GUI 组件水平, 垂直同时扩大以占据空白区域
 * --
 * @see java.awt.GridBagConstraints#ipadx
 * @see java.awt.GridBagConstraints#ipady
 * 设置受该对象控制的 GUI 组件横向, 纵向内部填充的大小, 即在该组件最小尺寸基础上还需要增大多少.
 * 如果设置了这俩属性, 则组件横向大小为最小宽度再加 ipadx*2px, 纵向大小为最小高度再加 ipady*2px
 * @see java.awt.GridBagConstraints#insets 设置受该对象控制的 GUI 组件的外部填充的大小, 即该组件边界和显示区域边界之间的距离.
 * @see java.awt.GridBagConstraints#anchor 设置受该对象控制的 GUI 组件在其显示区域中的定位方式.
 * {@link java.awt.GridBagConstraints#CENTER} 中间
 * {@link java.awt.GridBagConstraints#NORTH} 上中
 * {@link java.awt.GridBagConstraints#NORTHEAST} 左上
 * {@link java.awt.GridBagConstraints#NORTHEAST} 右上
 * {@link java.awt.GridBagConstraints#SOUTH} 下中
 * {@link java.awt.GridBagConstraints#SOUTHWEST} 左下
 * {@link java.awt.GridBagConstraints#SOUTHEAST} 右下
 * {@link java.awt.GridBagConstraints#EAST} 右中
 * {@link java.awt.GridBagConstraints#WEST} 左中
 * @see java.awt.GridBagConstraints#weightx
 * @see java.awt.GridBagConstraints#weighty
 * 设置受该对象控制的 GUI 组件占据多余空间的水平/垂直增加比例(权重), 俩值默认都为 0, 即该组件不占据多余空间.
 * 假设某个容器的水平线上包括三个 GUI 组件, 它们的水平增加比例分别是 1, 2, 3, 但容器宽度增加 60px 时, 则第一个组件宽度
 * 增加 10px, 第二个组件宽度增加 20px, 第三个组件宽度增加 30px. 如果其增加比例为 0, 则表示不会增加.
 * 如果希望某个组件的大小随容器的增大而增大, 则必须同时设置控制该组件的 GridBagConstraints 对象的 fill 属性和 weightx/weighty 属性.
 * @since 2021/1/14 15:46
 * 为了处理 GridBagLayout 中的 GUI 组件的大小,跨越性, Java 提供了 {@link java.awt.GridBagConstraints}
 * 该类与特定的 GUI 组件关联, 用于控制该 GUI 组件的大小, 跨越性.
 */
public class GridBagLayoutTest {

    private final Frame f = new Frame("GridBag");
    private final GridBagLayout gb = new GridBagLayout();
    private final GridBagConstraints gbc = new GridBagConstraints();
    private final Button[] bs = new Button[10];

    public static void main(String[] args) {
        new GridBagLayoutTest().init();
    }

    /**
     * 虽然设置了按钮 4, 按钮 5 横向上不会扩大, 但因为按钮 4/5 的宽度会受上一行 4 个按钮的影响,
     * 所以它们实际上依然会变大; 同理, 虽然设置了按钮 8/9 纵向上不会扩大, 但因为受按钮 7 的影响,
     * 所以按钮 9 纵向上依然会变大, 但按钮 8 不会变高.
     */
    void init() {
        f.setLayout(gb);

        for (int i = 0; i < bs.length; i++) {
            bs[i] = new Button("Btn" + i);
        }
        // 所有组件都可以在横向, 纵向上扩大
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        addBtn(bs[0]);
        addBtn(bs[1]);
        addBtn(bs[2]);

        // 该 GridBagConstraints 控制的 GUI 组件将会称为横向最后一个组件
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        addBtn(bs[3]);

        // 该 GridBagConstraints 控制的 GUI 组件将在横向上不会扩大
        gbc.weightx = 0;
        addBtn(bs[4]);

        // 该 GridBagConstraints 控制的 GUI 组件将横跨两个网格
        gbc.gridwidth = 2;
        addBtn(bs[5]);

        // 该 GridBagConstraints 控制的 GUI 组件将横跨一个网格
        gbc.gridwidth = 1;
        // 该 GridBagConstraints 控制的 GUI 组件将在纵向上跨两个网络
        gbc.gridheight = 2;
        // 该 GridBagConstraints 控制的 GUI 组件将会称为横向最后一个组件
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        addBtn(bs[6]);

        // 该 GridBagConstraints 控制的 GUI 组件将横向跨越一个网络, 纵向跨越两个网络
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        // 该 GridBagConstraints 控制的 GUI 组件纵向扩大的权重是 1
        gbc.weighty = 1;
        addBtn(bs[7]);

        // 设置下面的按钮在纵向上不会扩大
        gbc.weighty = 0;
        // 该 GridBagConstraints 控制的 GUI 组件将会称为横向最后一个组件
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        // 该 GridBagConstraints 控制的 GUI 组件将在纵向上横跨一个网络
        gbc.gridheight = 1;
        addBtn(bs[8]);
        addBtn(bs[9]);

        f.pack();
        f.setVisible(true);
    }

    private void addBtn(Button b) {
        gb.setConstraints(b, gbc);
        f.add(b);
    }

}
