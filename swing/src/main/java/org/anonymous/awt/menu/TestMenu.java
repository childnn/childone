package org.anonymous.awt.menu;

import org.anonymous.util.AwtUtil;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see java.awt.MenuBar 菜单栏
 * @see java.awt.Menu 菜单
 * @see java.awt.MenuItem 菜单项
 * @see test.swing.simple.menu.JPopupMenuDemo
 * @since 2021/12/15
 */
public class TestMenu {

    private Frame f;

    public static void main(String[] args) {
        TestMenu tm = new TestMenu();
        tm.init();
    }

    private void init() {
        f = new Frame("Menu-mac菜单请往上看");

        // 菜单栏
        MenuBar mb = new MenuBar();

        // 菜单
        Menu file = new Menu("File");
        Menu tool = new Menu("Tool");
        Menu help = new Menu("Help");

        // 将菜单添加到菜单栏
        mb.add(file);
        mb.add(tool);
        mb.setHelpMenu(help);

        f.setMenuBar(mb);

        MenuItem mi1 = new MenuItem("New");
        MenuItem mi2 = new MenuItem("Save");
        MenuItem mi3 = new MenuItem("Load");
        MenuItem mi4 = new MenuItem("Quit");
        MenuItem mi5 = new MenuItem("Change BackGround");

        ActionListener lis = e -> {
            String cmd = e.getActionCommand();
            if ("Quit".equals(cmd)) {
                System.exit(0);
            } else if ("Change BackGround".equals(cmd)) {
                Random ran = new Random();
                int r = ran.nextInt(255);
                int g = ran.nextInt(255);
                int b = ran.nextInt(255);

                System.out.printf("R: %s, G: %s, B: %s%n", r, g, b);

                f.setBackground(new Color(r, g, b));
            }
        };

        mi1.addActionListener(lis);
        mi2.addActionListener(lis);
        mi3.addActionListener(lis);
        mi4.addActionListener(lis);
        mi5.addActionListener(lis);

        file.add(mi1);
        file.add(mi2);
        file.add(mi3);
        file.add(mi4);
        tool.add(mi5);

        f.setVisible(true);
        f.setBounds(300, 300, 500, 300);
        AwtUtil.closeWindow(f);
    }


}
