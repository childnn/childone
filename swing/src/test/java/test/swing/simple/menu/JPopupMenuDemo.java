package test.swing.simple.menu;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/9 18:48
 * 弹出式菜单
 * 右键
 */
public class JPopupMenuDemo extends JFrame {

    private static final JPopupMenu popupMenu = new JPopupMenu();
    private JMenu fileMenu;
    private JMenuItem openFile, closeFile, exit;
    private JRadioButtonMenuItem copyFile, pasteFile;
    private ButtonGroup btGroup;

    public JPopupMenuDemo(String title) {
        super(title);

        btGroup = new ButtonGroup();

        fileMenu = new JMenu("文件");
        openFile = new JMenuItem("打开");
        closeFile = new JMenuItem("关闭");
        fileMenu.add(openFile);
        fileMenu.add(closeFile);

        popupMenu.add(fileMenu); // 添加文件菜单
        popupMenu.addSeparator(); // 分隔符

        // 复制粘贴同一个组: 单选
        copyFile = new JRadioButtonMenuItem("复制");
        pasteFile = new JRadioButtonMenuItem("粘贴");
        btGroup.add(copyFile);
        btGroup.add(pasteFile);

        // 添加到弹出菜单
        popupMenu.add(copyFile);
        popupMenu.add(pasteFile);
        popupMenu.addSeparator();

        exit = new JMenuItem("退出");
        popupMenu.add(exit);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(700, 400, 300, 200);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showPopupMenu(e);
            }

            private void showPopupMenu(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    System.out.println(String.format("x: %s, y: %s", e.getX(), e.getY()));
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                showPopupMenu(e);
            }
        });

        setVisible(true);

    }

    public static void main(String[] args) {
        new JPopupMenuDemo("POPUP");
    }
}
