package org.anonymous.mvc.controller;

import org.anonymous.mvc.model.HeartAdapter;
import org.anonymous.mvc.model.HeartModelInterface;
import org.anonymous.mvc.view.DJView;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/15 15:37
 */
public class HeartController implements ControllerInterface {
    private HeartModelInterface model;
    private DJView view;

    public HeartController(HeartModelInterface model) {
        this.model = model;
        view = new DJView(this, new HeartAdapter(model));
        view.createView();
        view.createControls();
        view.disableStopMenuItem();
        view.disableStartMenuItem();
    }

    @Override
    public void setBPM(int bpm) {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void increaseBPM() {

    }

    @Override
    public void decreaseBPM() {

    }
}
