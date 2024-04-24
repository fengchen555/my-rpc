package com.example.myrpc.rpc3.commom;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
/**
 * 定义了一个通用的Response的对象（消息格式）
 * 用于服务端向客户端返回响应
 */
@Data
@Builder
public class RPCResponse implements Serializable {
    // 状态信息
    private int code;
    // 信息
    private String message;
    // 具体数据
    private Object data;
    // 成功的响应
    public static RPCResponse success(Object data){
        return RPCResponse.builder().code(200).data(data).build();
    }
    // 失败的响应
    public static RPCResponse fail(){
        return RPCResponse.builder().code(500).message("服务器发生错误").build();
    }

}
