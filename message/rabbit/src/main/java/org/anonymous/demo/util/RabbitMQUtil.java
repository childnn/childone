package org.anonymous.demo.util;

import cn.hutool.core.net.NetUtil;

import javax.swing.*;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/11/14 17:56
 */
public class RabbitMQUtil {
    public static void main(String[] args) {
        checkServer();
    }

    public static void checkServer() {
        if (NetUtil.isUsableLocalPort(15672)) {
            JOptionPane.showMessageDialog(null, "Rabbit 未启动...");
            System.exit(1);
        }
    }
}
