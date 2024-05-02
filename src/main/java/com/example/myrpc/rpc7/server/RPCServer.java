package com.example.myrpc.rpc7.server;

// 把RPCServer抽象成接口，以后的服务端实现这个接口即可
public interface RPCServer {
    void start(int port);
    void stop();
}