package exception;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/15 15:52
 * 从输出中可以看到，VeryImportantException 不见了，它被 finally 子句里的 HoHumException 所取代。
 * 这是相当严重的缺陷，因为异常可能会以一种比前面例子所示更微妙和难以察党的方式完全丢失。
 */
public class LostMessage {

    public static void main(String[] args) {
        try {
            LostMessage lm = new LostMessage();
            try {
                lm.f();
            } finally {
                lm.dispose();
            }
        } catch (VeryImportantException | HoHumException e) {
            System.out.println(e);
        }
    }

    void f() throws VeryImportantException {
        throw new VeryImportantException();
    }

    void dispose() throws HoHumException {
        throw new HoHumException();
    }
}

class VeryImportantException extends Exception {
    @Override
    public String toString() {
        return "A very important exception!";
    }
}

class HoHumException extends Exception {
    @Override
    public String toString() {
        return "A trivial exception";
    }
}