package org.anonymous.netty.url;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/1/26 10:18
 */
public class DownloadUtil {

    public final static String ACCEPTS_STR;
    private static final List<String> ACCEPTS = Arrays.asList("image/gif", "image/jpeg", "image/pjpeg",
            "application/x-shockwave-flash", "application/xaml+xml", "application/vnd.ms-xpsdocument",
            "application/x-ms-xbap", "application/x-ms-application", "application/vnd.ms-excel",
            "application/vnd.ms-powerpoint", "application/msword", "*/*");

    static {
        final Iterator<String> iter = ACCEPTS.iterator();
        StringBuilder sb = new StringBuilder();
        while (iter.hasNext()) {
            sb.append(iter.next());
            if (iter.hasNext()) {
                sb.append(",");
            }
        }
        ACCEPTS_STR = sb.toString();
    }

    // 定义下载资源的路径
    private final String path;
    // 指定所下载的文件的保存位置
    private final String targetFile;
    // 定义需要使用多少个线程下载资源
    private final int threadNum;
    // 定义下载的线程对象
    private final DownloadThread[] threads;
    // 定义下载的文件的总大小
    private int fileSize;

    public DownloadUtil(String path, String targetFile, int threadNum) {
        this.path = path;
        this.targetFile = targetFile;
        this.threadNum = threadNum;
        threads = new DownloadThread[threadNum];
    }

    public static void main(String[] args) {
        System.out.println(ACCEPTS_STR);
    }

    public void download() throws IOException {
        final URL url = new URL(path);
        final URLConnection urlConnection = url.openConnection();
        if (urlConnection instanceof HttpURLConnection) {
            final HttpURLConnection http = (HttpURLConnection) urlConnection;
            http.setRequestMethod("GET");
            http.setRequestProperty("Accept", ACCEPTS_STR);
            http.setRequestProperty("Accept-Language", "zh-CN");
            http.setRequestProperty("Charset", "UTF-8");
            http.setRequestProperty("Connection", "Keep-Alive");

            // 得到文件大小
            fileSize = http.getContentLength();
            System.out.println("fileSize = " + fileSize);

            http.disconnect();

            final int currentPartSize = fileSize / threadNum + 1;
            final RandomAccessFile file = new RandomAccessFile(targetFile, "rw");

            // 设置本地文件大小
            file.setLength(fileSize);
            file.close();

            for (int i = 0; i < threadNum; i++) {
                // 计算每个线程下载的开始位置
                final int startPos = i * currentPartSize;
                // 每个线程使用一个 RandomAccessFile 进行下载
                final RandomAccessFile currentPart = new RandomAccessFile(targetFile, "rw");
                // 定位该线程的下载位置
                currentPart.seek(startPos);
                // 创建下载线程
                threads[i] = new DownloadThread(startPos, currentPartSize, currentPart);

                // 启动下载线程
                threads[i].start();
            }

        } else {
            throw new IllegalArgumentException("不支持的连接类型: " + urlConnection.getClass().getName());
        }
    }

    // 获取下载的完成百分比
    public double getCompleteRate() {
        // 统计多个线程已经下载的总大小
        int sumSize = 0;
        for (int i = 0; i < threadNum; i++) {
            sumSize += threads[i].length;
        }

        // 返回已经完成的百分比
        return sumSize * 1.0 / fileSize;
    }

    class DownloadThread extends Thread {

        // 当前线程的下载位置
        private final int startPos;
        // 当前线程负责下载的文件大小
        private final int currentPartSize;
        // 当前线程需要下载的文件块
        private final RandomAccessFile currentPart;
        // 该线程已经下载的字节数
        private int length;

        public DownloadThread(int startPos, int currentPartSize, RandomAccessFile currentPart) {
            this.startPos = startPos;
            this.currentPartSize = currentPartSize;
            this.currentPart = currentPart;
        }

        @Override
        public void run() {
            try {
                final URL url = new URL(path);
                final URLConnection conn = url.openConnection();
                if (conn instanceof HttpURLConnection) {
                    final HttpURLConnection http = (HttpURLConnection) conn;
                    http.setConnectTimeout(5 * 1000);
                    http.setRequestMethod("GET");
                    http.setRequestProperty("Accept", ACCEPTS_STR);
                    http.setRequestProperty("Accept-Language", "zh-CN");
                    http.setRequestProperty("Charset", "UTF-8");

                    final InputStream is = http.getInputStream();

                    // 跳过 startPos 个字节, 表明该线程指下载自己负责的那部分文件
                    is.skip(startPos);

                    final byte[] buffer = new byte[1024];
                    int hasRead;

                    // 读取网络数据, 并写入本地文件
                    while (length < currentPartSize && (hasRead = is.read(buffer)) != -1) {
                        currentPart.write(buffer, 0, hasRead);

                        // 累计该线程下载的总大小
                        length += hasRead;
                    }
                    currentPart.close();
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}


