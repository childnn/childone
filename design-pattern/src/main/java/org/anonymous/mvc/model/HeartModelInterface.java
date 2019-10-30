package org.anonymous.mvc.model;

import org.anonymous.mvc.view.BPMObserver;
import org.anonymous.mvc.view.BeatObserver;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/15 15:25
 */
public interface HeartModelInterface {
    int getHeartRate();

    void registerObserver(BeatObserver o);

    void removeObserver(BeatObserver o);

    void registerObserver(BPMObserver o);

    void removeObserver(BPMObserver o);
}
