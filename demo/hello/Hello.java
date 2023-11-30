import java.util.Arrays;
import com.H;

public class Hello {
    public static void main(String[] args){
        System.out.println("hello: " + Arrays.asList(args));
        System.out.println("-DfilePath: " + System.getProperty("filePath"));
        System.out.println("--filePath: " + System.getenv("filePath"));

        H.hello();
    }

}