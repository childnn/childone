package org.anonymous.util;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/11/29
 */
public class AwtUtil {

    public static void closeWindow(Frame f) {
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0); // 安全退出
                // 释放由此 window, 其子组件及其拥有的所有子组件所使用
                // 的所有本机屏幕资源. 即这些组件的资源将被破坏, 它们使用的所有内存
                // 都将返回操作系统, 并将它们标记为不可显示.
                f.dispose();
            }
        });
    }

    public static void closeWindow(Dialog f) {
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0); // 安全退出
                // 释放由此 window, 其子组件及其拥有的所有子组件所使用
                // 的所有本机屏幕资源. 即这些组件的资源将被破坏, 它们使用的所有内存
                // 都将返回操作系统, 并将它们标记为不可显示.
                f.dispose();
            }
        });
    }

}
