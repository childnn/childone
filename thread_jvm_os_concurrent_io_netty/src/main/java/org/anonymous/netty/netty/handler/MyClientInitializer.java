package org.anonymous.netty.netty.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MyClientInitializer extends ChannelInitializer<SocketChannel> {

    // 添加各种 handlers.
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        System.out.println(getClass());
        ChannelPipeline pipeline = ch.pipeline();
        //加入一个出站的handler 对数据进行一个编码
        pipeline.addLast(new MyLongToByteEncoder()); // outbound
        //一个入站的解码器(入站handler )
        //pipeline.addLast(new MyByteToLongDecoder());
        pipeline.addLast(new MyByteToLongDecoder2()); // inbound
        //加入一个自定义的handler, 处理业务
        pipeline.addLast(new MyClientHandler()); // inbound
    }

}
