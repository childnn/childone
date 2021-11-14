package rebuild.gui.r1;

import java.util.Observable;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/8/30 20:53
 */
public class Interval extends Observable {

    private String end = "0";
    private String start = "0";
    private String length = "0";

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
        setChanged();
        // 调用 java.util.Observer#update
        notifyObservers();
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
        setChanged();
        // 调用 java.util.Observer#update
        notifyObservers();
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
        setChanged();
        // 调用 java.util.Observer#update
        notifyObservers();
    }

    public void calculateLength() {
        int start = Integer.parseInt(getStart());
        int end = Integer.parseInt(getEnd()/*endField.getText()*/);
        int length = end - start;
        setLength(length + "");
    }

    public void calculateEnd() {
        int start = Integer.parseInt(getStart());
        int length = Integer.parseInt(getLength());
        int end = start + length;
        setEnd(end + "");
        // endField.setText(end + "");
    }
}
