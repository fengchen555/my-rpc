package com.example.myrpc.rpc5.client;

import com.example.myrpc.rpc5.codec.MyDecode;
import com.example.myrpc.rpc5.codec.MyEncode;
import com.example.myrpc.rpc5.codec.ObjectSerializer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * 初始化，主要负责序列化的编码解码， 需要解决netty的粘包问题
 */
/**
 * 同样的与服务端解码和编码格式
 */
public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 消息格式 [长度][消息体]
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
        // 计算当前待大宋消息的长度，写入到前4个字节中
        pipeline.addLast(new LengthFieldPrepender(4));
//        pipeline.addLast(new ObjectEncoder());
//
//        pipeline.addLast(new ObjectDecoder(new ClassResolver() {
//            @Override
//            public Class<?> resolve(String className) throws ClassNotFoundException {
//                return Class.forName(className);
//            }
//        }));
        pipeline.addLast(new MyEncode(new ObjectSerializer()));
        pipeline.addLast(new MyDecode());

        pipeline.addLast(new NettyClientHandler());
    }
}