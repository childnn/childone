package org.anonymous.netty.file;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see java.nio.file.WatchService
 * 在 java7 以前, 如果程序需要监控文件的变化, 则可以考虑启动一条后台线程, 这条后台线程每隔一段时间去 "遍历"
 * 一次指定目录的文件, 如果发现此次遍历结果与上次遍历结果不同, 则认为文件发生了变化.
 * @see java.nio.file.Path#register(java.nio.file.WatchService, java.nio.file.WatchEvent.Kind[])
 * 用 watcher 监听该 paht 代表的目录下的文件变化. events 表示要监听哪些类型的事件.
 * @see java.nio.file.Path#register(java.nio.file.WatchService, java.nio.file.WatchEvent.Kind[], java.nio.file.WatchEvent.Modifier...)
 * -- 监听类别: 默认的一个类型, 有需要可扩展
 * @see java.nio.file.StandardWatchEventKinds#OVERFLOW
 * @see java.nio.file.StandardWatchEventKinds#ENTRY_CREATE
 * @see java.nio.file.StandardWatchEventKinds#ENTRY_DELETE
 * @see java.nio.file.StandardWatchEventKinds#ENTRY_MODIFY
 * ---
 * @see java.nio.file.WatchService#poll() 获取下一个 WatchKey, 如果没有则立即返回 null
 * @see java.nio.file.WatchService#poll(long, java.util.concurrent.TimeUnit) 限期等待
 * @see java.nio.file.WatchService#take() 无限期等待
 * @since 2021/1/22 18:47
 */
public class WatchServiceTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        // 获取当前文件系统的 WatchService
        final WatchService ws = FileSystems.getDefault().newWatchService();

        // 监听指定目录
        final WatchKey wk = Paths.get(".").register(ws,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.ENTRY_DELETE);
        while (true) {
            // 获取下一个文件变化事件
            final WatchKey key = ws.take(); // 无限期等待
            for (WatchEvent<?> e : key.pollEvents()) {
                System.out.println(e.context() + " 文件发生了 " + e.kind() + " 事件!");
            }
            // 重设 WatchKey
            final boolean valid = key.reset();
            // 如果重设失败, 退出监听
            if (!valid) {
                break;
            }
        }

    }

}
