package lambda;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/1/7 15:55
 */
public interface DefaultMethod {

    void test(int a, int b);

    default void df() {
        System.out.println("default...");
    }


}

class DMTest {

    public static void main(String[] args) {
        DefaultMethod dm = (a, b) -> {
            System.out.println(a + b);
            // df(); // error
        };

        dm = new DefaultMethod() {
            @Override
            public void test(int a, int b) {
                df(); // ok
            }
        };
    }

}
