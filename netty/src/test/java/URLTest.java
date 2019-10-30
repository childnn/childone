import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/5/4 18:42
 */
public class URLTest {
    public static void main(String[] args) {
        try {
            URL url = new URL("http://www.baidu.com");
            URLConnection connection = url.openConnection();
            System.out.println(connection.getContentType());
            System.out.println(connection.getContentLength());
            System.out.println(connection.getContentEncoding());
            System.out.println(connection.getDate());
            System.out.println(connection.getExpiration());
            System.out.println(connection.getLastModified());

            try (InputStream in = connection.getInputStream()) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while (-1 != (len = in.read(buffer))) {
                    out.write(buffer, 0, len);
                    System.out.println(new String(buffer, StandardCharsets.UTF_8));
                }
                //System.out.println(new String(out.toByteArray()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
