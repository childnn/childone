package rebuild.gui.r1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Observable;
import java.util.Observer;
import java.util.regex.Pattern;

import static java.awt.BorderLayout.NORTH;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/8/30 20:53
 * Interval 的 Obsever
 */
public class IntervalWindow extends JFrame implements Observer {

    private final Interval subject;

    private final TextField startField;
    private final TextField endField;
    private final TextField lengthField;

    // “I'll start with the end field.”
    public String getEnd() {
        return subject.getEnd();
        // return endField.getText();
    }

    public void setEnd(String arg) {
        subject.setEnd(arg);
        // endField.setText(arg);
    }

    public String getStart() {
        return subject.getStart();
        // return startField.getText();
    }

    public void setStart(String arg) {
        subject.setStart(arg);
        // startField.setText(arg);
    }

    public String getLength() {
        return subject.getLength();
        // return lengthField.getText();
    }

    public void setLength(String arg) {
        subject.setLength(arg);
        // lengthField.setText(arg);
    }

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

        // add observer
        subject = new Interval();
        subject.addObserver(this);
    }

    public static void main(String[] args) {
        IntervalWindow iw = new IntervalWindow("IntervalWindow");
    }

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
            setLength(lengthField.getText());
            if (isNotInteger(lengthField.getText())) {
                // System.out.println("length not int");
                lengthField.setText("0");
            }
            subject.calculateEnd();
        }

        private void endFieldFocusLost(FocusEvent e) {
            // 这个动作把 End 文本框设定为其当前值
            // 此调用动作, 并没有使用前面的 getEnd() 取得 End 文本框当前内容,
            // 而是直接访问文本框, 之所以这样, 是因为随后的重构将使 getEnd() 从领域对象
            // 而非文本框身上取值. 那时如果这里用的是 getEnd() 函数, 每当用户修改文本框内容, 这里就会将
            // 文本框又改回原值. 所以必须使用直接访问文本框的方式获取当前值
            setEnd(endField.getText()); // 可以把这一句注释, 看效果
            if (isNotInteger(getEnd()/*endField.getText()*/)) {
                // System.out.println("end not int");
                // endField.setText("0");
                setEnd("0");
            }
            subject.calculateLength();
        }

        void startFieldFocusLost(FocusEvent e) {
            setStart(startField.getText());
            if (isNotInteger(startField.getText())) {
                // System.out.println("start not int");
                startField.setText("0");
            }
            subject.calculateLength();
        }

        private boolean isNotInteger(String text) {
            return !Pattern.compile("\\d+").matcher(text).matches();
            // return false;
        }

    }

    @Override
    public void update(Observable o, Object arg) {
        // 注: 这里是另一个直接访问文本框的地点, 如果调用的是 设值函数
        // 程序将陷入无限递归调用
        endField.setText(subject.getEnd());
        startField.setText(subject.getStart());
        lengthField.setText(subject.getLength());
    }

}
