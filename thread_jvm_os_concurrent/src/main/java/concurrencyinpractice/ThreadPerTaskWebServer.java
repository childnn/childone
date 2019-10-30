package concurrencyinpractice;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/21 14:12
 */
public class ThreadPerTaskWebServer {
    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(80);
        while (true) {
            final Socket connection = socket.accept();
            Runnable task = () -> Task.handleRequest(connection);
            new Thread(task).start();
        }
    }

}

class Task {
    static void handleRequest(Socket connection) {
        //..
    }
}

class TaskExecutionWebServer {
    private static final int NTHRADS = 100;
    private static final Executor exec = Executors.newFixedThreadPool(NTHRADS);

    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(80);
        while (true) {
            final Socket connection = socket.accept();
            exec.execute(() -> Task.handleRequest(connection));
        }
    }
}
