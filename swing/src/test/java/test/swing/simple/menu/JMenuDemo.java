package test.swing.simple.menu;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/9 16:50
 * 菜单
 * 菜单如果依附到 JMenuBar 对象上，则此菜单就是菜单栏中的菜单。
 * 菜单如果依附在 JPopupMenu 对象上，此菜单就是弹出式菜单。
 * 实现菜单栏和弹出式菜单的原理是一样的，但在具体的实现方式上有一些区别。
 * ------------
 * JMenu 对象的 setMnemonic() 方法设置当前菜单的快速访问符。
 * 该符号必须对应键盘上的一个键，并且应该使用 java.awt.event.KeyEvent
 * 中定义的 VK—XXX 键代码之一指定。
 * 提示：快速访问符是一种快捷键，通常在按下 Alt 键和某个字母时激活。
 * 例如，常用的 Alt+F 是“文件” 菜单的快速访问符。
 * JMenuItem 类实现的是菜单中的菜单项。菜单项本质上是位于列表中的按钮。
 * 当用户单击“按钮”时，则执行与菜单项关联的操作。JMenuItem 的常用构造方法有以下三个。
 */
public class JMenuDemo extends JMenuBar {

    @SuppressWarnings("serial")
    private static final Map<String, Integer> FILE_ITEMS = new HashMap<String, Integer>() {{
        put("新建(N)", KeyEvent.VK_N);
        put("打开(O)", KeyEvent.VK_O);
        put("保存(S)", KeyEvent.VK_S);
        put("退出(E)", KeyEvent.VK_E);
    }};
    private static final Map<String, Integer> EDIT_ITEMS = new HashMap<>();

    static {
        EDIT_ITEMS.put("撤销(U)", KeyEvent.VK_U);
        EDIT_ITEMS.put("剪切(X)", KeyEvent.VK_X);
        EDIT_ITEMS.put("复制(C)", KeyEvent.VK_C);
    }

    public JMenuDemo() {
        add(createFileMenu()); // "文件" 菜单
        add(createEditMenu()); // "编辑" 菜单
    }

    public static void main(String[] args) {
        JFrame jf = new JFrame("Menu");
        jf.setBounds(700, 400, 500, 500);
        jf.setJMenuBar(new JMenuDemo());
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jf.setVisible(true);
    }

    private JMenu createEditMenu() {
        JMenu editMenu = new JMenu("编辑(E)");
        editMenu.setMnemonic(KeyEvent.VK_E);

        EDIT_ITEMS.forEach((text, kv) -> {
            JMenuItem item = new JMenuItem(text, kv);
            editMenu.add(item);
            editMenu.addSeparator();
        });

        JCheckBoxMenuItem item = new JCheckBoxMenuItem("自动换行");
        editMenu.add(item);

        return editMenu;
    }

    // VK: virtual key
    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("文件(F)");
        fileMenu.setMnemonic(KeyEvent.VK_F); // 快捷键

        FILE_ITEMS.forEach((text, vk) -> {
            JMenuItem item = new JMenuItem(text, vk);
            item.setAccelerator(KeyStroke.getKeyStroke(vk, InputEvent.CTRL_DOWN_MASK));
            fileMenu.add(item);
        });

        return fileMenu;
    }


}
