package com.example.myrpc.rpc4.client;

import com.example.myrpc.rpc4.commom.RPCRequest;
import com.example.myrpc.rpc4.commom.RPCResponse;

// 共性抽取出来
public interface RPCClient {
    RPCResponse sendRequest(RPCRequest response);
}