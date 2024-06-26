package com.example.myrpc.rpc1.client;



import com.example.myrpc.rpc1.commom.RPCRequest;
import com.example.myrpc.rpc1.commom.RPCResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class IOClient {
    // 这里负责底层与服务端的通信，发送的Request，接受的是Response对象
    // 客户端发起一次请求调用，Socket建立连接，发起请求Request，得到响应Response
    // 这里的request是封装好的（上层进行封装），不同的service需要进行不同的封装，
    // 客户端只知道Service接口，需要一层动态代理根据反射封装不同的Service
    public static RPCResponse sendRequest(String host, int port, RPCRequest request){
        try {
            // 建立Socket连接
            Socket socket = new Socket(host, port);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            // 传给服务器request
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();
            // 服务器查询数据，返回对应的对象
            RPCResponse response  = (RPCResponse) objectInputStream.readObject();
            return response;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("客户端启动失败");
            return null;
        }
    }

}