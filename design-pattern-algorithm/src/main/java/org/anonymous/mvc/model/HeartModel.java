package org.anonymous.mvc.model;

import org.anonymous.mvc.view.BPMObserver;
import org.anonymous.mvc.view.BeatObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/15 15:27
 */
public class HeartModel implements HeartModelInterface, Runnable {
    private List<BeatObserver> beatObservers = new ArrayList<>();
    private List<BPMObserver> bpmObservers = new ArrayList<>();
    private int time = 1000;
    private int bpm = 90;
    private Random random = new Random(System.currentTimeMillis());
    private Thread thread;

    public HeartModel() {
        this.thread = new Thread(this);
        thread.start();
    }

    @Override
    public int getHeartRate() {
        return 60000 / time;
    }

    @Override
    public void registerObserver(BeatObserver o) {
        beatObservers.add(o);
    }

    @Override
    public void removeObserver(BeatObserver o) {
        beatObservers.remove(o);
    }

    @Override
    public void registerObserver(BPMObserver o) {
        bpmObservers.add(o);
    }

    @Override
    public void removeObserver(BPMObserver o) {
        bpmObservers.remove(o);
    }

    @Override
    public void run() {
        int lastRate = -1;
        for (; ; ) {
            int change = random.nextInt(10);
            if (random.nextInt(2) == 0) {
                change = 0 - change;
            }
            int rate = 60000 / (time + change);
            if (rate < 120 && rate > 50) {
                time += change;
                notifyBeatObservers();
                if (rate != lastRate) {
                    lastRate = rate;
                    notifyBPMObservers();
                }
            }
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void notifyBPMObservers() {
        for (BPMObserver observer : bpmObservers) {
            observer.updateBPM();
        }
    }

    private void notifyBeatObservers() {
        for (BeatObserver observer : beatObservers) {
            observer.updateBeat();
        }
    }
}
