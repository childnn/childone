package org.anonymous.awt.scroll;

import org.anonymous.util.AwtUtil;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/12/10
 * 滚动条
 */
public class TestScrollPane {

    public static void main(String[] args) {
        TestScrollPane tsp = new TestScrollPane();
        tsp.init();
    }

    private void init() {
        Frame f = new Frame("frame");
        ScrollPane sp = new ScrollPane();
        Panel p = new Panel(new GridLayout(3, 3, 5, 5));
        List<String> names = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I");

        for (String name : names) {
            p.add(new Button(name));
        }

        sp.add(p);
        f.add(sp);

        f.setBounds(500, 300, 200, 200);

        f.setVisible(true);
        AwtUtil.closeWindow(f);

    }


}
