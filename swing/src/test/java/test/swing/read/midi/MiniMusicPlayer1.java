package test.swing.read.midi;

import javax.sound.midi.*;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/16 13:47
 */
public class MiniMusicPlayer1 {
    public static void main(String[] args) {
        try {
            // 创建并打开队列.
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();

            // 创建队列并 track.
            Sequence seq = new Sequence(Sequence.PPQ, 4);
            Track track = seq.createTrack();

            // 创建一堆序列的音符事件.
            for (int i = 5; i < 61; i += 4) {
                // 调用 makeEvent() 来产生信息和事件然后把它们加到 track 上.
                track.add(makeEvent(144, 1, i, 100, i));
                track.add(makeEvent(128, 1, i, 100, i + 2));
            }

            // 开始播放.
            sequencer.setSequence(seq);
            sequencer.setTempoInBPM(220);
            sequencer.start();
        } catch (MidiUnavailableException | InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }

    private static MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
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
}
