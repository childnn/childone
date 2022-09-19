package org.anonymous.awt.file;

import org.anonymous.util.AwtUtil;

import java.awt.*;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see FileDialog awt 的文件选择器
 * @see javax.swing.JFileChooser swing 的文件选择器
 * @see javax.swing.JColorChooser 颜色选择器
 * @since 2021/12/10
 */
public class TestFileDialog {

    Frame f = new Frame("open file");
    FileDialog fdl = new FileDialog(f, "My FileDialog", FileDialog.LOAD);
    FileDialog fds = new FileDialog(f, "My FileDialog", FileDialog.SAVE);

    public static void main(String[] args) {
        TestFileDialog tfd = new TestFileDialog();
        tfd.init();
    }

    private void init() {
        Button load = new Button("LOAD");
        load.addActionListener(e -> fdl.setVisible(true));

        Button save = new Button("SAVE");
        save.addActionListener(e -> fds.setVisible(true));

        f.setLayout(new FlowLayout());
        f.add(load);
        f.add(save);

        f.setBounds(500, 300, 200, 120);
        f.setVisible(true);

        AwtUtil.closeWindow(f);

    }

}
