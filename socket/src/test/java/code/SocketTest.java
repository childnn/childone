package code;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/23 21:44
 */
public class SocketTest {

    @Test
    public void client1() throws IOException {
        Socket chatSocket = new Socket("localhost", 5000);
        // 底层和高层串流间的桥梁.
        InputStreamReader stream = new InputStreamReader(chatSocket.getInputStream());
        // 将 BufferedReader 链接到 InputStreamReader.
        BufferedReader reader = new BufferedReader(stream);
        String message = reader.readLine();

    }

    @Test
    public void client2() throws IOException {
        Socket chatSocket = new Socket("localhost", 5000);
        // 字符数据和字节间的转换桥梁, 可以衔接 String 和 Socket 两端.
        PrintWriter writer = new PrintWriter(chatSocket.getOutputStream());
        writer.println("message to send");
        writer.print("another message");
    }

    /**
     * @see ServerSocket#accept()
     * accept 方法会在等待用户的 Socket 连接时闲置着.
     * 当用户连上来时, 此方法会返回一个 Socket (在不同的端口上),
     * 以便与客户端通信. Socket 与 ServerSocket 的端口不同, 因此 ServerSocket
     * 可以空出来等待其他的用户.
     */
    @Test
    public void server() throws IOException {
        ServerSocket serverSock = new ServerSocket(5000);
        while (true) {
            // 这个方法会停下来等待要求到达之后才会继续.
            Socket sock = serverSock.accept();

            PrintWriter writer = new PrintWriter(sock.getOutputStream());
            String advice = getAdvice();
            writer.print(advice); // 使用 Socket 链接送出信息.
            writer.close(); // 关闭链接.
            System.out.println(advice);
        }

    }

    String[] adviceList = {"Take smaller bites", "Go for the tight jeans. No they do NOT boss what *really* think", "You might want to rethink that haircut."};

    private String getAdvice() {
        int random = (int) (Math.random() * adviceList.length);
        return adviceList[random];
    }
}
