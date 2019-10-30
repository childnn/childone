package test.swing.read.gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/15 23:06
 */
public class TwoButtons {
    private JFrame frame;
    private JLabel label;

    public static void main(String[] args) {
        TwoButtons gui = new TwoButtons();
        gui.go();
    }

    private void go() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton labelButton = new JButton("Change Label");
        labelButton.addActionListener(new LabelListener());

        JButton colorButton = new JButton("Change Circle");
        colorButton.addActionListener(new ColorListener());

        label = new JLabel("I'am a label");
        MyDrawPanel drawPanel = new MyDrawPanel();
        frame.getContentPane().add(BorderLayout.SOUTH, colorButton);
        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);
        frame.getContentPane().add(BorderLayout.EAST, labelButton);
        frame.getContentPane().add(BorderLayout.WEST, label);

        frame.setSize(300, 300);
        frame.setVisible(true);
    }

    private class LabelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            label.setText("Ouch!");
        }
    }

    private class ColorListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.repaint();
        }
    }
}
