package com.example.myrpc.rpc7.server;

import com.example.myrpc.rpc7.config.ServerConfig;

public class TestServer4 {
    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 8900;

        // 创建服务提供者，并传入主机地址、端口号以及启动类的 Class 对象
        ServiceProvider serviceProvider = new ServiceProvider(host, port, ServerConfig.class);
//        System.out.println(serviceProvider.toString());
//        System.out.println(serviceProvider.getClass().getName());
        // 启动你的服务器
        startServer(serviceProvider, port);
    }

    private static void startServer(ServiceProvider serviceProvider, int port) {
        RPCServer RPCServer = new NettyRPCServer(serviceProvider);
        RPCServer.start(port);
        // 在这里启动你的服务器逻辑
        // 可以使用Netty或其他服务器框架来实现
    }
}
