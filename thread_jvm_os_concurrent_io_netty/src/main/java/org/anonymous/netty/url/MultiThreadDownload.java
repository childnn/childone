package org.anonymous.netty.url;

import java.io.IOException;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/1/26 13:33
 */
public class MultiThreadDownload {

    public static void main(String[] args) throws IOException {

        // 如果使用 spring-security 相关的项目作为服务器, 由于需要先登录, 无法执行下载.
        // com.example.dateformat.DateFormatApplication
        final DownloadUtil download = new DownloadUtil(
                "http://localhost:8080/img.jpg",
                "download.jpg", 4);

        download.download();

        new Thread(() -> {
            while (download.getCompleteRate() < 1) {
                System.out.println("已完成: " + download.getCompleteRate());

                try {
                    Thread.sleep(1L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
