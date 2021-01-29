package org.anonymous;

import java.awt.Button;
import java.awt.Frame;

/**
 * 2019年3月9日13:27:46
 * GUI: graph user interface 图形化用户界面
 * Component: 组件
 * 图形化用户界面的基本组成元素,凡是能够以图形化方式显示在屏幕上并能够与用户进行交互的对象均为组件
 * 菜单: 用户界面中点击后有显示选择条目的按钮
 * 按钮: button 界面中点击后有直接反应的按钮
 * 标签: label 界面中直接显示某些信息,但是无法点击操作的部分 //如 行号
 * 文本框: text field 可以有用户输入以完成某些功能的部分 // 如 用户注册,查找
 * 文本域: text area, 多行文本框
 * 对话框: dialog box
 * 文件对话框: file dialog
 * 滚动条: scroll bar 略
 * 下拉框
 * 复选框: CheckBox
 * 单选框
 * 选择列表
 * awt: abstract window toolkit -- 抽象窗口工具集
 * java.awt.Component 抽象类
 * component 是一个具有图形表示能力的对象，可在屏幕上显示，并可与用户进行交互。典型图形用户界面中的按钮、复选框和滚动条都是组件示例。
 * Component 类是与菜单不相关的 Abstract Window Toolkit 组件的抽象超类。
 * 是java 中除了菜单相关组件之外所有java awt 主键类的根类,该类规定了GUI 组件的基本特征, 如 尺寸,位置,颜色效果等,并实现了作为一个GUI 部件所应具备的基本功能
 * java.awt.MenuComponent
 */
public class Test01 {
    public static void main(String[] args) {
        Frame f = new Frame("shadow");
        Button bn = new Button("冷");
        bn.setBounds(50, 300, 100, 100);
        f.add(bn);
        f.setBounds(50, 300, 250, 500);
        f.setVisible(true);
    }
}
