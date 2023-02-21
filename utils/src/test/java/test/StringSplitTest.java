package test;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/11 21:02
 */
public class StringSplitTest {
    @Test
    public void test() {
        String[] split = "1|2|3".split("\\|");
        System.out.println(Arrays.toString(split));
    }

    @Test
    public void cmd() {
        String os = System.getProperty("os.name");
        System.out.println(os);
    }

    @Test
    public void cmdExec() throws IOException {
        Runtime runtime = Runtime.getRuntime();
        // Process exec = runtime.exec("cmd java -version");
        // Process exec = runtime.exec("ping www.baidu.com");
        Process exec = runtime.exec("java -version");
        // InputStream is = exec.getInputStream();
        InputStream is = exec.getErrorStream();
        // String s = IOUtils.toString(is, StandardCharsets.UTF_8);
        // String s = IOUtils.toString(new BufferedReader(new InputStreamReader(is, "gbk")));
        // System.out.println("s = " + s);
        // OutputStream os = exec.getOutputStream();
        // IOUtils.
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "gbk"));
        String s;
        while (null != (s = br.readLine())) {
            System.out.println("s = " + s);
        }
    }


    @Test
    public void cmdExecFfmpeg() throws IOException {
        Runtime runtime = Runtime.getRuntime();
        // Process exec = runtime.exec("java -version");
        // Process exec = runtime.exec("ping www.baidu.com");
        Process exec = runtime.exec("ffmpeg.exe -rtsp_transport tcp -buffer_size 4096000 -i \"rtsp://admin:Wdd123123!@10.41.59.24:554/Streaming/Channels/101?transportmode=unicast\" -vcodec copy -acodec copy -f flv \"rtmp://127.0.0.1:1935/hls/live\"\n");
        // InputStream is = exec.getInputStream();
        InputStream is = exec.getErrorStream();
        // String s = IOUtils.toString(is, StandardCharsets.UTF_8);
        // String s = IOUtils.toString(new BufferedReader(new InputStreamReader(is, "gbk")));
        // System.out.println("s = " + s);
        // OutputStream os = exec.getOutputStream();
        // IOUtils.
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "gbk"));
        String s;
        while (null != (s = br.readLine())) {
            System.out.println("s = " + s);
        }
    }

}
