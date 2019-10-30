package test.swing.simple.tick.slider;

import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.WindowConstants;
import java.awt.Container;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/9 10:45
 * 滑块
 */
public class JSliderDemo {

    public static void main(String[] args) {
        JFrame jf = new JFrame("Swing-slider");
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setBounds(500, 500, 400, 300);

        Container contentPane = jf.getContentPane();

        JSlider js = new JSlider(0, 100, 23);
        js.setMajorTickSpacing(11); // 设置主刻度标记的间隔
        js.setMinorTickSpacing(3); // 设置次刻度标记的间隔
        js.setPaintLabels(true); // 是否在滑块上绘制标签 -- 文字刻度
        js.setPaintTicks(true); // 是否在滑块上绘制刻度标记 -- 标记
        //js.setPaintTrack(false); // 是否在滑块上绘制滑道: 默认值就是 true
        //js.setSnapToTicks(true); // 指定为 true，则滑块（及其所表示的值）解析为最靠近用户放置滑块处的刻度标记的值
        contentPane.add(js);

        jf.setVisible(true);
    }

}
