package bytebuddy.t;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2022/03/26
 */
public class GreetingInterceptor {

    public Object greet(Object argument) {
        return "Hello from " + argument;
    }

    public Object greet1() {
        return "Hello from ";
    }

}
