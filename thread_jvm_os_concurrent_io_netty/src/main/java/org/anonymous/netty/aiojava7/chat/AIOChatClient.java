package org.anonymous.netty.aiojava7.chat;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.anonymous.netty.aiojava7.chat.AIOChatServer.PORT;
import static org.anonymous.netty.aiojava7.chat.AIOChatServer.UTF8;
import static org.anonymous.netty.bio.BIOServer.HOST;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/1/29 18:27
 */
public class AIOChatClient {

    JFrame mainWin = new JFrame("多人聊天");
    JTextArea jta = new JTextArea(16, 48); // 聊天框
    JTextField jtf = new JTextField(40); // 输入框
    JButton sendBtn = new JButton("发送");
    // 与服务器端通信的 异步 Channel
    AsynchronousSocketChannel client;

    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
        final AIOChatClient cl = new AIOChatClient();
        cl.init();
        cl.connect();
    }

    void init() {
        mainWin.setLayout(new BorderLayout());
        jta.setEnabled(false);
        mainWin.add(new JScrollPane(jta), BorderLayout.CENTER);
        final JPanel jp = new JPanel();
        jp.add(jtf);
        jp.add(sendBtn);

        jtf.setText("Ctrl+Enter 发送");

        Action sendAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取用户输入的内容
                final String content = jtf.getText().trim();
                if (!content.isEmpty()) {
                    // 将 content 写入 Channel 中
                    try {
                        client.write(ByteBuffer.wrap(content.getBytes(UTF8))).get();
                    } catch (InterruptedException | ExecutionException ex) {
                        ex.printStackTrace();
                        System.out.println("写入信息异常: " + ex);
                        jtf.setText("异常:" + ex);
                    }
                }
                // 清空输入框: 设置默认内容
                jtf.setText("Ctrl+Enter 发送");
                // jtf.setText("");
            }
        };
        // 发送消息的 Action
        sendBtn.addActionListener(sendAction);

        // 将 Ctrl+Enter 与 send 关联
        jtf.getInputMap().put(KeyStroke.getKeyStroke('\n', InputEvent.CTRL_MASK), "send");

        // 将 send 和 sendAction 关联
        jtf.getActionMap().put("send", sendAction);

        mainWin.add(jp, BorderLayout.SOUTH);
        mainWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWin.pack();
        mainWin.setVisible(true);
    }

    void connect() throws ExecutionException, InterruptedException, IOException {
        // 1. bind
        final ExecutorService pool = Executors.newFixedThreadPool(40);
        final AsynchronousChannelGroup channelGroup = AsynchronousChannelGroup.withThreadPool(pool);
        client = AsynchronousSocketChannel.open(channelGroup);

        // 2. connect to specified host:port
        client.connect(new InetSocketAddress(HOST, PORT)).get();

        // todo: 暂未实现现实发送人的信息.
        final int currentClientId = getCurrentClientId();

        jta.append("currentClientId[" + currentClientId + "]---连接服务器成功---\n");


        // 3. read
        final ByteBuffer buf = ByteBuffer.allocate(1024);
        buf.clear();

        client.read(buf, currentClientId, new CompletionHandler<Integer, Object>() {
            @Override
            public void completed(Integer result, Object attachment) {
                buf.flip(); // 读写切换
                // 将 buf 中的内容转换成字符串
                final String content = UTF8.decode(buf).toString();

                // 回显信息
                jta.append("currentClientId[" + /*currentClientId*/attachment + "]: " + content + "\n");
                buf.clear();

                // 继续读下一个
                client.read(buf, attachment, this);
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                jta.setText("读取数据失败: " + exc);
                exc.printStackTrace();
            }
        });
    }

    private int getCurrentClientId() {
        // 当前客户端的 id:
        final Random r = new Random();
        return r.nextInt(99999);
    }

}
