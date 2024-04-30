package com.example.myrpc.rpc6.server;

import com.example.myrpc.rpc6.server.service.BlogService;
import com.example.myrpc.rpc6.server.service.UserService;
import com.example.myrpc.rpc6.server.service.impl.BlogServiceImpl;
import com.example.myrpc.rpc6.server.service.impl.UserServiceImpl;

public class TestServer {
    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 8899;
        UserService userService = new UserServiceImpl();
        BlogService blogService = new BlogServiceImpl();

        ServiceProvider serviceProvider = new ServiceProvider(host, port);
        serviceProvider.provideServiceInterface(userService);
        serviceProvider.provideServiceInterface(blogService);

        RPCServer RPCServer = new NettyRPCServer(serviceProvider);
        RPCServer.start(port);
    }
}

