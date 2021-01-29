package org.anonymous.test;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/9/27 19:38
 */
@Component // 单例
public class SpringTaskXML {

    // 执行 3s, 等待 1s: 两次任务时间间隔 4s
    public void execute() {
        System.out.println(this + ":------------------------------ " + LocalDateTime.now());
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
