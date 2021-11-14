package rebuild.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.regex.Pattern;

import static java.awt.BorderLayout.NORTH;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/8/30 10:21
 */
public class IntervalWindow extends JFrame {

    public IntervalWindow(String title) throws HeadlessException {
        super(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 300);
        setLocationByPlatform(true);
        setVisible(true);

        SymFocus symFocus = new SymFocus();

        startField = new TextField("0", 10);
        startField.addFocusListener(symFocus);
        Panel pStart = new Panel();
        pStart.add("start", startField);

        endField = new TextField("0", 10);
        endField.addFocusListener(symFocus);
        Panel pEnd = new Panel();
        pEnd.add("end", endField);

        lengthField = new TextField("0", 10);
        lengthField.addFocusListener(symFocus);
        Panel pLength = new Panel();
        pLength.add("length", lengthField);


        getContentPane().add(pStart, NORTH);
        getContentPane().add(pEnd, BorderLayout.CENTER);
        getContentPane().add(pLength, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        IntervalWindow iw = new IntervalWindow("IntervalWindow");
    }


    TextField startField;
    TextField endField;
    TextField lengthField;

    class SymFocus extends FocusAdapter {

        @Override
        public void focusLost(FocusEvent e) {
            Object source = e.getSource();
            // System.out.println("source = " + source);
            // System.out.println("startField = " + startField);
            if (source == startField) {
                // System.out.println("sssss = " + startField);
                startFieldFocusLost(e);
            } else if (source == endField) {
                endFieldFocusLost(e);
            } else if (source == lengthField) {
                lengthFieldFocusLost(e);
            }
        }

        private void lengthFieldFocusLost(FocusEvent e) {
            if (isNotInteger(lengthField.getText())) {
                // System.out.println("length not int");
                lengthField.setText("0");
            }
            calculateEnd();
        }

        private void endFieldFocusLost(FocusEvent e) {
            if (isNotInteger(endField.getText())) {
                // System.out.println("end not int");
                endField.setText("0");
            }
            calculateLength();
        }

        void startFieldFocusLost(FocusEvent e) {
            if (isNotInteger(startField.getText())) {
                // System.out.println("start not int");
                startField.setText("0");
            }
            calculateLength();
        }

        private boolean isNotInteger(String text) {
            return !Pattern.compile("\\d+").matcher(text).matches();
            // return false;
        }

        void calculateLength() {
            int start = Integer.parseInt(startField.getText());
            int end = Integer.parseInt(endField.getText());
            int length = end - start;
            lengthField.setText(length + "");
        }

        void calculateEnd() {
            int start = Integer.parseInt(startField.getText());
            int length = Integer.parseInt(lengthField.getText());
            int end = start + length;
            endField.setText(end + "");
        }

    }



}
