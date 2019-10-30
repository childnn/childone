package test.swing.simple.text;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/12 11:34
 * 文本编辑器
 */
public class TextFileOpener extends JFrame {

    private JTextArea showText; // 显示文件内容的文本域
    private JTextArea showProperty; // 显示文件属性的文本域

    public TextFileOpener(String title) throws HeadlessException {
        super(title);
        setBounds(400, 200, 700, 700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // 菜单栏
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar); // 把菜单栏放到窗体上

        // 文件菜单
        JMenu fileMenu = new JMenu("文件"); // 创建文件菜单
        //fileMenu.setText();
        menuBar.add(fileMenu); // 把文件菜单添加到菜单上

        JMenuItem miOpen = new JMenuItem("打开"); // 创建 打开 菜单项
        fileMenu.add(miOpen); // 把 菜单项 添加到 菜单
        fileMenu.addSeparator(); // 菜单分隔符
        // 监听 "打开" 菜单项, 操作文件
        miOpen.addActionListener(e -> openTextFile());

        JMenuItem miExit = new JMenuItem("退出"); // 创建 退出 菜单项
        fileMenu.add(miExit); // 将 退出项 添加到 菜单.
        miExit.addActionListener(e -> System.exit(0)); // 退出系统

        // 编辑菜单
        JMenu editMenu = new JMenu("编辑"); // 编辑菜单
        menuBar.add(editMenu); // 添加菜单到 菜单栏

        JMenuItem miPaste = new JMenuItem("粘贴");
        editMenu.add(miPaste);

        // 工具栏
        JToolBar toolBar = new JToolBar();
        Container contentPane = getContentPane();
        contentPane.add(toolBar, BorderLayout.NORTH);

        JButton btOpen = new JButton("打开"); // 打开按钮
        toolBar.add(btOpen); // 添加按钮到工具栏
        // 监听 按钮, 打开文件
        btOpen.addActionListener(e -> openTextFile());

        JButton btExit = new JButton("退出"); // 退出按钮
        toolBar.add(btExit); // 添加按钮到工具栏
        // 监听按钮, 退出
        btExit.addActionListener(e -> System.exit(0));

        // 选项卡
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
        contentPane.add(tabbedPane, BorderLayout.CENTER);

        // 1. 属性滚动面板
        JScrollPane propScrollPane = new JScrollPane();
        // 添加 滚动面板 到 选项卡的第一个选项页
        tabbedPane.addTab("文件属性", null, propScrollPane, "这是文件属性");

        showProperty = new JTextArea(); // 文本域
        showProperty.setEditable(false);
        showProperty.setFont(new Font("楷体", Font.BOLD, 33));
        // 文本域添加到滚动面板的视图中
        propScrollPane.setViewportView(showProperty);

        // 2. 文本滚动面板
        JScrollPane textScrollPane = new JScrollPane();
        // 添加文本滚动面板到 选项卡的第二个选项页
        tabbedPane.addTab("文件内容", textScrollPane);

        showText = new JTextArea();
        showText.setFont(new Font("宋体", Font.BOLD, 23));
        showText.setForeground(Color.RED);
        textScrollPane.setViewportView(showText);


        setVisible(true);
    }

    public static void main(String[] args) {
        new TextFileOpener("文本编辑器");
    }

    // 文件操作
    private void openTextFile() {
        JFileChooser fileChooser = new JFileChooser("E:\\dev-code\\WorkSpace\\child"); // 文件选择器
        fileChooser.setFileFilter(new FileNameExtensionFilter("文本文件", "txt", "java"));
        int val = fileChooser.showOpenDialog(getContentPane()); // 打开文件选择对话框
        if (val == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            showProperty.append(String.format("文件路径: %s\n", selectedFile.getAbsolutePath()));
            showProperty.append(String.format("是否隐藏文件: %s\n", selectedFile.isHidden()));

            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                reader.lines().forEach(line -> showText.append(line + "\n"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
