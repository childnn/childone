package test.swing.simple.tick.timer;

import javax.swing.Timer;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see Timer
 * @see java.util.Timer
 * @since 2020/3/9 11:57
 * 计时器
 */
public class TimerDemo {

    public static void main(String[] args) {
        new Timer(2000, e -> System.err.println(LocalDateTime.now())).start();
        System.out.println(100000000);

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
