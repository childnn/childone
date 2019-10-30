package org.anonymous.mvc.model;

import org.anonymous.mvc.view.BPMObserver;
import org.anonymous.mvc.view.BeatObserver;

import javax.sound.midi.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/14 22:56
 */
public class BeatModel implements BeatModelInterface, MetaEventListener {

    // 定序器 对象知道如何产生真是的节拍(你听到的拍子).
    private Sequencer sequencer;
    private List<BeatObserver> beatObservers = new ArrayList<>();
    private List<BPMObserver> bpmObservers = new ArrayList<>();
    private int bpm = 90; // BPM 实例变量持有节拍的频率, 默认值是 90 BPM.

    private Sequence sequence;
    private Track track;

    @Override
    public void meta(MetaMessage meta) {
        if (meta.getType() == 47) {
            beatEvent();
            sequencer.start();
            setBPM(getBPM());
        }
    }

    // 此方法为我们设置定序器和节拍音轨.
    @Override
    public void initialize() {
        setUpMidi();
        buildTrackAndStart();
    }

    private void buildTrackAndStart() {
        int[] trackList = {35, 0, 46, 0};

        sequence.deleteTrack(null);
        track = sequence.createTrack();

        makeTracks(trackList);
        track.add(makeEvent(192, 9, 1, 0, 4));
        try {
            sequencer.setSequence(sequence);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }

    }

    private MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
        MidiEvent event = null;
        ShortMessage a = new ShortMessage();
        try {
            a.setMessage(comd, chan, one, two);
            event = new MidiEvent(a, tick);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        return event;
    }

    private void makeTracks(int[] trackList) {
        for (int i = 0; i < trackList.length; i++) {
            int key = trackList[i];
            if (key != 0) {
                track.add(makeEvent(144, 9, key, 100, i));
                track.add(makeEvent(128, 9, key, 100, i + 1));
            }
        }
    }

    private void setUpMidi() {
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.addMetaEventListener(this);
            sequence = new Sequence(Sequence.PPQ, 4);
            track = sequence.createTrack();
            sequencer.setTempoInBPM(getBPM());
        } catch (MidiUnavailableException | InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }

    // 此方法开始了定序器, 并将 BPM 设定为默认值: 90.
    @Override
    public void on() {
        sequencer.start();
        setBPM(90);
    }

    // 此方法通过将 BPM 设置为 0, 停止定序器.
    @Override
    public void off() {
        setBPM(0);
        sequencer.stop();
    }

    /**
     * 控制器有用此方法操纵节拍, 它做了三件事:
     * 1. 设置 BPM 实例变量.
     * 2. 要求定序器改变 BPM.
     * 3. 通知所有 BPM 观察者, BPM 已经改变了.
     */
    @Override
    public void setBPM(int bpm) {
        this.bpm = bpm;
        sequencer.setTempoInBPM(getBPM());
        notifyBPMObservers();
    }

    private void notifyBPMObservers() {
        for (int i = 0; i < bpmObservers.size(); i++) {
            BPMObserver observer = bpmObservers.get(i);
            observer.updateBPM();
        }
    }
    void beatEvent() {
        notifyBeatObservers();
    }

    private void notifyBeatObservers() {
        for (int i = 0; i < beatObservers.size(); i++) {
            BeatObserver observer = beatObservers.get(i);
            observer.updateBeat();
        }
    }

    @Override
    public int getBPM() {
        return bpm;
    }

    @Override
    public void registerObserver(BeatObserver o) {
        beatObservers.add(o);
    }

    @Override
    public void removeObserver(BeatObserver o) {
        beatObservers.remove(o);
        // int i = beatObservers.indexOf(o);
        // if (i >= 0) {
        //     beatObservers.remove(i);
        // }
    }

    @Override
    public void registerObserver(BPMObserver o) {
        bpmObservers.add(o);
    }

    @Override
    public void removeObserver(BPMObserver o) {
        bpmObservers.remove(o);
        // int i = bpmObservers.indexOf(o);
        // if (i >= 0) {
        //     bpmObservers.remove(i);
        // }
    }
}
