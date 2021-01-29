package interview;

/**
 * @author child
 * 2019/7/3 8:42
 */
public abstract class AbstractTest {
    public static AbstractTest newInstance() {
        return new AbstractTest() {
        };
    }

    public static void main(String[] args) {
        AbstractTest.newInstance().f();
    }

    public void f() {
        System.out.println("true = " + true);
    }
}
