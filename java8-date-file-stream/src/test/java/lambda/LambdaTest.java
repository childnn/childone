package lambda;

interface Strategy {
    String apply(String msg);
}

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/14 12:03
 */
public class LambdaTest {

    Strategy s;
    String msg;

    public LambdaTest(String msg) {
        s = new Soft();
        this.msg = msg;
    }

    public static void main(String[] args) {
        Strategy[] ss = {
                new Strategy() {
                    @Override
                    public String apply(String msg) {
                        return msg.toUpperCase() + "!";
                    }
                },
                msg -> msg.substring(0, 5),
                Unrelated::twice,
                /*new Strategy() {
                    @Override
                    public String apply(String msg) {
                        return Unrelated.twice(msg);
                    }
                }*/
        };

        LambdaTest lt = new LambdaTest("HELLO THERE");
        lt.print();

        for (Strategy s : ss) {
            lt.setS(s);
            lt.print();
        }
    }

    void setS(Strategy s) {
        this.s = s;
    }

    void print() {
        System.out.println(s.apply(msg));
    }
}

class Soft implements Strategy {

    @Override
    public String apply(String msg) {
        return msg.toLowerCase() + "?";
    }

}

class Unrelated {
    static String twice(String msg) {
        return msg + " " + msg;
    }
}