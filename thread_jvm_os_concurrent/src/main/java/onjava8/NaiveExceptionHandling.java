package onjava8;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/5 10:49
 */
public class NaiveExceptionHandling {

    public static void main(String[] args) {
        ExecutorService es = Executors.newCachedThreadPool();
        // 无法捕获异常
        try {
            es.execute(new ExceptionThread());
        } catch (RuntimeException ue) {
            // This statement will NOT execute!
            System.out.println("Exception was handled!");
        } finally {
            es.shutdown();
        }
    }
}
