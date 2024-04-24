package com.example.myrpc.rpc2.commom;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 定义了一个通用的Request的对象（消息格式）
 * 用于客户端向服务端发送请求
 */
@Data
@Builder
public class RPCRequest implements Serializable {
    // 请求的服务类名
    private String interfaceName;
    // 请求的方法名
    private String methodName;
    // 请求的参数
    private Object[] params;
    // 请求的参数类型
    private Class<?>[] paramsTypes;

}
