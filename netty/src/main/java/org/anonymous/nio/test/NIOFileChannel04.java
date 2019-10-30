package org.anonymous.nio.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

/**
 * copy
 *
 * @see FileChannel#transferTo(long, long, java.nio.channels.WritableByteChannel)
 * @see FileChannel#transferFrom(ReadableByteChannel, long, long)
 */
public class NIOFileChannel04 {

    public static void main(String[] args) {
        //Channels.newChannel()
        try (//创建相关流
             FileInputStream fileInputStream = new FileInputStream("netty\\src\\main\\resources\\file01.txt");
             FileOutputStream fileOutputStream = new FileOutputStream("netty\\src\\main\\resources\\04.txt");

             //获取各个流对应的file channel
             FileChannel sourceCh = fileInputStream.getChannel();
             FileChannel destCh = fileOutputStream.getChannel()
        ) {
            //使用transferForm完成拷贝
            destCh.transferFrom(sourceCh, 0, sourceCh.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
/*
        //创建相关流
        FileInputStream fileInputStream = new FileInputStream("netty\\src\\main\\resources\\file01.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("netty\\src\\main\\resources\\04.txt");

        //获取各个流对应的filechannel
        FileChannel sourceCh = fileInputStream.getChannel();
        FileChannel destCh = fileOutputStream.getChannel();

        //使用transferForm完成拷贝
        destCh.transferFrom(sourceCh, 0, sourceCh.size());
        //关闭相关通道和流
        sourceCh.close();
        destCh.close();
        fileInputStream.close();
        fileOutputStream.close();*/
    }
}
