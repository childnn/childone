package lambda.methodreference;

import javax.swing.JFrame;

@FunctionalInterface
interface AFrame {

    JFrame generate(String title);

}

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/1/7 15:40
 */
public class ConstructorReference {

    public static void main(String[] args) {
        AFrame f = title -> new JFrame(title); // lambda
        f = JFrame::new; // method reference, 注意与 new JFrame() 差别
    }

}