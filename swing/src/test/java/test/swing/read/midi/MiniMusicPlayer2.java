package test.swing.read.midi;

import javax.sound.midi.*;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/16 13:55
 */
public class MiniMusicPlayer2 implements ControllerEventListener { // 监听 ControllerEvent, 实现接口.

    public static void main(String[] args) {
        MiniMusicPlayer2 mini = new MiniMusicPlayer2();
        mini.go();
    }

    private void go() {
        try {
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();

            // 向 sequencer 注册事件. 注册的方法取用监听者与代表想要监听的事件的 int 数组, 我们只需要 127 事件.
            int[] eventsIWant = {127};
            sequencer.addControllerEventListener(this, eventsIWant);

            Sequence seq = new Sequence(Sequence.PPQ, 4);
            Track track = seq.createTrack();

            for (int i = 5; i < 60; i++) {
                track.add(makeEvent(144, 1, i, 100, i));
                // 插入事件编号为 127 的自定义 ControllerEvent (176), 它不会做任何事情,
                // 只是让我们知道有音符被播放, 因为他的 tick 跟 NOTE ON 是同时进行的.
                track.add(makeEvent(176, 1, 127, 0, i));

                track.add(makeEvent(128, 1, i, 100, i + 2));
            }

            sequencer.setSequence(seq);
            sequencer.setTempoInBPM(220);
            sequencer.start();
        } catch (MidiUnavailableException | InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }

    private MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
        MidiEvent event = null;
        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(comd, chan, one, two);
            event = new MidiEvent(a, tick);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        return event;
    }

    // 获知事件时在命令行打印字符串的事件处理程序.
    @Override
    public void controlChange(ShortMessage event) {
        System.out.println("la");
    }
}
