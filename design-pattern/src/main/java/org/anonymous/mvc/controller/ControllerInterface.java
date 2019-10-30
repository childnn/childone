package org.anonymous.mvc.controller;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/14 23:13
 */
public interface ControllerInterface {
    void setBPM(int bpm);

    void start();

    void stop();

    void increaseBPM();

    void decreaseBPM();
}
