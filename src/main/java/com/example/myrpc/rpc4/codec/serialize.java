package com.example.myrpc.rpc4.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;


/**
 * 按照自定义的消息格式解码数据
 */
@AllArgsConstructor
public class serialize extends MessageToByteEncoder {
    /**
     * @param channelHandlerContext
     * @param msg
     * @param in
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext,
                          Object msg, ByteBuf in) throws Exception {
        // 1. 读取消息类型
        short messageType = in.readShort();




    }
}
