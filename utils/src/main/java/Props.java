/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/9/3 8:16
 */
public class Props {

    public static void main(String[] args) {
        System.getenv().forEach((k, v) -> System.out.println(k + ": " + v));

        System.out.println("==============================================");
        System.getProperties().forEach((k, v) -> System.out.println(k + ": " + v));
    }
}
