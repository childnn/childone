import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/23 22:22
 */
public class SimpleChatClientA {
    private JTextField outgoing;
    private PrintWriter writer;
    private Socket sock;

    public void go() {
        JFrame frame = new JFrame("Ludicrously Simple Chat Client");
        JPanel mainPanel = new JPanel();
        outgoing = new JTextField(20);
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new SendButtonListener());
        mainPanel.add(outgoing);
        mainPanel.add(sendButton);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        setUpNetworking();
        frame.setSize(400, 500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setUpNetworking() {
        try (Socket localhost = sock = new Socket("localhost", 5000)) {
            writer = new PrintWriter(sock.getOutputStream());
            System.out.println("networking established");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class SendButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 可以开始写数据, 所以 println() 会把信息送到服务器.
            writer.print(outgoing.getText());
            writer.flush();
            outgoing.setText("");
            outgoing.requestFocus();
        }
    }

    // 先开启服务端: ServerSocket -- 可以使用 {code.SocketTest.server}
    public static void main(String[] args) {
        new SimpleChatClientA().go();
    }
}
