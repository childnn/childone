package org.anonymous.netty.udp;

import java.net.InetSocketAddress;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/1 14:27
 */
public final class LogEvent {
    public static final byte SEPARATOR = ':';

    private final InetSocketAddress source;
    private final String logFile;
    private final String msg;
    private final long received;

    // 1. 构造器用于出站消息
    public LogEvent(String logFile, String msg) {
        this(null, -1, logFile, msg);
    }

    // 2. 构造器用于入站消息
    public LogEvent(InetSocketAddress source, long received, String logFile, String msg) {
        this.source = source;
        this.logFile = logFile;
        this.msg = msg;
        this.received = received;
    }

    // 3. 返回发送的 LogEvent 的 InetSocketAddress 的资源
    public InetSocketAddress getSource() {
        return source;
    }

    // 4. 返回用于发送 LogEvent 的日志文件的名称
    public String getLogFile() {
        return logFile;
    }

    // 5. 返回消息的内容
    public String getMsg() {
        return msg;
    }

    // 6. 返回 LogEvent 接收到的事件
    public long getReceivedTimestamp() {
        return received;
    }
}
