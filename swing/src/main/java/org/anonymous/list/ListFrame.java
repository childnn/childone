package org.anonymous.list;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.BorderLayout;

/**
 * @author child
 * 2019/6/21 10:33
 * this frame contains a word list and a label that shows a sentence made up from the
 * chosen words. Note that you can select multiple words with Ctrl+click and Shift-click.
 * @see JList#JList(Object[])
 * -- 构建一个显示这些选项 (item) 的列表
 * @see JList#getVisibleRowCount()
 * @see JList#setVisibleRowCount(int)
 * -- 获取或设置列表在没有滚动条时显示的默认行数
 * @see JList#getLayoutOrientation()
 * @see JList#setLayoutOrientation(int)  -- 参数: VERTICAL, VERTICAL_WRAP, HORIZONTAL_WRAP 其中之一
 * -- 获取或设置方向布局
 * @see JList#getSelectionMode()
 * @see JList#setSelectionMode(int) -- 参数: SINGLE_SELECTION, SINGLE_INTERVAL_SELECTION, MULTIPLE_INTERVAL_SELECTION 其中之一
 * -- 获取或设置选择方式是单选或多选
 * @see JList#addListSelectionListener(ListSelectionListener)
 * -- 向列表添加一个在每次选择结果发生变化时会被告知的监听器
 * @see JList#getSelectedValuesList()
 * -- 返回所有的选定值, 如果选择结果为空, 则返回一个空表
 * @see JList#getSelectedValue()
 * -- 返回第一个选定值, 如果选择结果为空, 则返回 null
 * @see ListSelectionListener#valueChanged(ListSelectionEvent)
 * -- 在任何时刻, 只要选择结果发生变化, 改方法就会被调用
 */
public class ListFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 300;
    private JPanel listPanel;
    private JList<String> wordList;
    private JLabel label;
    private JPanel buttonPanel;
    private ButtonGroup group;
    private String prefix = "The ";
    private String suffix = "fox jumps over the lazy dog.";

    public ListFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        String[] words = {"quick", "brown", "hungry", "wild", "silent", "huge", "private", "abstract", "static", "final"};

        wordList = new JList<>(words);
        wordList.setVisibleRowCount(4);
        JScrollPane scrollPane = new JScrollPane(wordList);

        listPanel = new JPanel();
        listPanel.add(scrollPane);
        wordList.addListSelectionListener(e -> {
            StringBuilder text = new StringBuilder(prefix);
            for (String value : wordList.getSelectedValuesList()) {
                text.append(value).append(" ");
            }
            text.append(suffix);

            label.setText(text.toString());
        });

        buttonPanel = new JPanel();
        group = new ButtonGroup();
        makeButton("Vertical", JList.VERTICAL);
        makeButton("Vertical Wrap", JList.VERTICAL_WRAP);
        makeButton("Horizontal Wrap", JList.HORIZONTAL_WRAP);

        add(listPanel, BorderLayout.NORTH);
        label = new JLabel(prefix + suffix);
        add(label, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Makes a radio button to set the layout orientation.
     *
     * @param label       the button label
     * @param orientation the orientation for the list
     */
    private void makeButton(String label, int orientation) {
        JRadioButton button = new JRadioButton(label);
        buttonPanel.add(button);
        if (group.getButtonCount() == 0) {
            button.setSelected(true);
        }
        group.add(button);
        button.addActionListener(e -> {
            wordList.setLayoutOrientation(orientation);
            listPanel.revalidate();
        });
    }
}
