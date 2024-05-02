package com.example.myrpc.rpc7.client;

import com.example.myrpc.rpc7.commom.RPCRequest;
import com.example.myrpc.rpc7.commom.RPCResponse;

// 共性抽取出来
public interface RPCClient {
    RPCResponse sendRequest(RPCRequest response);
}