package org.anonymous.swing.layout;

import java.awt.Button;
import java.awt.Frame;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/1/18 15:08
 * 绝对定位: 拖动控件.
 * 1. 将 Container 的布局管理器设置为 null;
 * 2. 向容器中添加组件时, 先调用 setBounds()/setSize() 设置组件大小, 位置;
 * 或直接创建 GUI 组件时通过构造参数指定该组件的大小, 位置, 然后将该组件添加到容器中.
 * 采用绝对定位不是最好的方法, 它可能导致该 GUI 界面失去跨平台特性.
 */
public class AbsolutePosition {

    /**
     * 可以使来给你个按钮重叠, 但此方式是以丧失跨平台特性为代价的.
     */
    private final Frame f = new Frame("绝对定位");
    Button bt1 = new Button("Bt1");
    Button bt2 = new Button("Bt2");

    public static void main(String[] args) {
        new AbsolutePosition().init();
    }

    void init() {
        // 使用 null 布局管理器
        f.setLayout(null);
        // 强制设置每个按钮的大小, 位置
        bt1.setBounds(20, 30, 90, 28);
        f.add(bt1);
        bt2.setBounds(50, 45, 120, 35);
        f.add(bt2);

        f.setBounds(50, 50, 200, 100);
        f.setVisible(true);
    }

}
