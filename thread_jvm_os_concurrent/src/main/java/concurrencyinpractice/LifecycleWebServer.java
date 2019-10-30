package concurrencyinpractice;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

import static concurrencyinpractice.Task.handleRequest;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/21 15:34
 */
public class LifecycleWebServer {
    private final ExecutorService exec = Executors.newFixedThreadPool(100);

    public void start() throws IOException {
        ServerSocket socket = new ServerSocket(80);
        while (!exec.isShutdown()) {
            try {
                final Socket conn = socket.accept();
                exec.execute(() -> handleRequest(conn));
            } catch (RejectedExecutionException e) {
                if (!exec.isShutdown()) {
                    System.out.println("task submission rejected");
                }
            }
        }
    }
}
