package org.anonymous.mvc.model;

import org.anonymous.mvc.view.BPMObserver;
import org.anonymous.mvc.view.BeatObserver;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/14 22:41
 */
public interface BeatModelInterface {
    // 这些方法是让控制器调用的.
    // 控制器分局用户的操作而对模型做出适当的处理.
    void initialize(); // BeatModel 被初始化之后, 就会调用此方法.

    void on(); // 用来将节拍产生器打开或关闭.

    void off();

    void setBPM(int bpm); // 这个方法设定 BPM. 调用此方法后, 节拍频率马上改变.

    //-------------------------------------------------

    // 这些方法允许视图和控制器取得状态, 并变成观察者.
    int getBPM();

    // 分成两种观察者, 一种观察者希望每个节拍都被通知;
    // 另一种观察者只希望 BPM 改变时被通知.
    void registerObserver(BeatObserver o);

    void removeObserver(BeatObserver o);

    void registerObserver(BPMObserver o);

    void removeObserver(BPMObserver o);
}
