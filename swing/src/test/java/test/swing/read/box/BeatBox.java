package test.swing.read.box;

import javax.sound.midi.*;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/18 11:20
 */
public class BeatBox {
    private JPanel mainPanel;
    // 把 checkbox 存储在 List 中.
    private List<JCheckBox> checkBoxList;
    private Sequencer sequencer;
    private Sequence sequence;
    private Track track;
    private JFrame theFrame;
    // 乐器名称.
    private String[] instrumentNames = {
            "Bass Drum", "Closed Hi-Hat", "Open Hi-Hat",
            "Acoustic Snare", "Crash Cymbal", "Hand Clap",
            "High Tom", "Hi Bongo", "Maracas", "Whistle", "Low Conga",
            "Cowbell", "Vibraslap", "Low-mid Tom", "High Agogo",
            "Open Hi Conga",
    };
    // 实际的乐器关键字.-- 与名称一一对应.
    private int[] instruments = {
            35, 42, 46, 38, 49, 39,
            50, 60, 70, 72, 64, 56,
            58, 47, 67, 63,
    };

    public static void main(String[] args) {
        new BeatBox().buildGUI();
    }

    private void buildGUI() {
        theFrame = new JFrame("Cyber BeatBox");
        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderLayout layout = new BorderLayout();
        JPanel background = new JPanel(layout);
        // 设定页面上摆设组件时的空白边缘.
        background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        checkBoxList = new ArrayList<>();
        Box buttonBox = new Box(BoxLayout.Y_AXIS);

        JButton start = new JButton("Start");
        start.addActionListener(new MyStartListener());
        buttonBox.add(start);

        JButton stop = new JButton("Stop");
        stop.addActionListener(new MyStopListener());
        buttonBox.add(stop);

        JButton upTempo = new JButton("Tempo Up");
        upTempo.addActionListener(new MyUpTempoListener());
        buttonBox.add(upTempo);

        JButton downTempo = new JButton("Tempo Down");
        downTempo.addActionListener(new MyDownTempoListener());
        buttonBox.add(downTempo);

        Box nameBox = new Box(BoxLayout.Y_AXIS);
        for (int i = 0; i < 16; i++) {
            nameBox.add(new Label(instrumentNames[i]));
        }
        // 把按钮添加在布局右侧. 乐器名称放在布局左侧.
        background.add(BorderLayout.EAST, buttonBox);
        background.add(BorderLayout.WEST, nameBox);

        theFrame.getContentPane().add(background);

        GridLayout grid = new GridLayout(16, 16);
        grid.setVgap(1);
        grid.setHgap(2);
        mainPanel = new JPanel(grid);
        background.add(BorderLayout.CENTER, mainPanel);

        // 创建 checkbox 组, 设定成未勾选的为 false 并加到 ArrayList 和面板上.
        for (int i = 0; i < 256; i++) {
            JCheckBox c = new JCheckBox();
            c.setSelected(false);
            checkBoxList.add(c);
            mainPanel.add(c);
        }

        setUpMidi();

        theFrame.setBounds(50, 50, 300, 300);
        theFrame.pack();
        theFrame.setVisible(true);
    }

    private void setUpMidi() {
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequence = new Sequence(Sequence.PPQ, 4);
            track = sequence.createTrack();
            sequencer.setTempoInBPM(120);
        } catch (MidiUnavailableException | InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }

    /**
     * 重点: 此处会将复选框状态转换为 MIDI 事件并加到 track 上.
     */
    private void buildTrackAndStart() {
        // 创建出 16 个元素的数组来存储一项乐器的值.
        // 如果该节应该要演奏, 其值会是关键字值, 否则值为零.
        int[] trackList;
        // 清除掉旧的 track 做一个新的.
        sequence.deleteTrack(track);
        track = sequence.createTrack();

        // 对每个乐器都执行一次.
        for (int i = 0; i < 16; i++) {
            trackList = new int[16];
            // 设定代表乐器的关键字.
            int key = instruments[i];

            // 对每一拍执行一次
            for (int j = 0; j < 16; j++) {
                JCheckBox jc = checkBoxList.get(j + (16 * i));
                // 如果有勾选, 将关键字值放到数组的该位置上,
                if (jc.isSelected()) {
                    trackList[j] = key;
                } else { // 不然的话就补零.
                    trackList[j] = 0;
                }
            }

            // 创建此乐器的事件并加到 track 上.
            makeTracks(trackList);
            track.add(makeEvent(176, 1, 127, 0, 16));
        }

        track.add(makeEvent(192, 9, 1, 0, 15));
        try {
            sequencer.setSequence(sequence);
            // 指定无穷的重复次数.
            sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
            sequencer.start();
            sequencer.setTempoInBPM(120);
        } catch (InvalidMidiDataException e) {
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

    // 创建某项乐器的所有事件.
    private void makeTracks(int[] list) {
        for (int i = 0; i < 16; i++) {
            int key = list[i];

            if (0 != key) {
                // 创建 NOTE ON 和 NOTE OFF 事件并加入到 track 上.
                track.add(makeEvent(144, 9, key, 100, i));
                track.add(makeEvent(128, 9, key, 100, i + 1));
            }
        }
    }

    // 第一个内部类, 按钮的监听者.
    private class MyStartListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            buildTrackAndStart();
        }
    }

    private class MyStopListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            sequencer.stop();
        }
    }

    private class MyUpTempoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            float tempoFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float) (tempoFactor * 1.03));
        }
    }

    // 节奏因子, 预设为 1.0, 每次调整 3%. -- 加减节奏速度.

    private class MyDownTempoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            float tempoFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float) (tempoFactor * .97));
        }
    }
}
