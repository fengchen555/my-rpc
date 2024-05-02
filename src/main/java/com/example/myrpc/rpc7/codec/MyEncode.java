package com.example.myrpc.rpc7.codec;

import com.example.myrpc.rpc7.commom.RPCRequest;
import com.example.myrpc.rpc7.commom.RPCResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;

/**
 * 依次按照自定义的消息格式写入，传入的数据为request或者response
 * 需要持有一个serialize器，负责将传入的对象序列化成字节数组
 */
@AllArgsConstructor
public class MyEncode extends MessageToByteEncoder {
    private Serializer serializer;
    /**
     * @param channelHandlerContext
     * @param msg
     * @param out
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext,
                          Object msg, ByteBuf out) throws Exception {
        //System.out.println(msg.getClass());
        //写入消息类型
        if(msg instanceof RPCRequest){
            out.writeShort(MessageType.REQUEST.getCode());
        } else if (msg instanceof RPCResponse){
            out.writeShort(MessageType.RESPONSE.getCode());
        } else {
            throw new RuntimeException("不支持的数据类型");
        }
        //写入序列化方式
        out.writeShort(serializer.getType());
        //得到序列化后的字节数组
        byte[] bytes = serializer.serialize(msg);
        //写入长度
        out.writeInt(bytes.length);
        //写入序列化后的字节数组
        out.writeBytes(bytes);

    }
}
