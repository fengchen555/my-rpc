package com.example.myrpc.rpc5.server;

import com.example.myrpc.rpc5.server.service.BlogService;
import com.example.myrpc.rpc5.server.service.UserService;
import com.example.myrpc.rpc5.server.service.impl.BlogServiceImpl;
import com.example.myrpc.rpc5.server.service.impl.UserServiceImpl;

public class TestServer {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        BlogService blogService = new BlogServiceImpl();
//        Map<String, Object> serviceProvide = new HashMap<>();
//        // 暴露两个服务接口， 即在RPCServer中加一个HashMap
//        serviceProvide.put(UserService.class.getName(),userService);
//        serviceProvide.put(BlogService.class.getName(),blogService);
// 这里重用了服务暴露类，顺便在注册中心注册，实际上应分开，每个类做各自独立的事
        ServiceProvider serviceProvider = new ServiceProvider("127.0.0.1", 8899);
        serviceProvider.provideServiceInterface(userService);
        serviceProvider.provideServiceInterface(blogService);

        RPCServer RPCServer = new NettyRPCServer(serviceProvider);
        RPCServer.start(8899);
    }
}
// 这里先不去讨论实现其中的细节，因为这里还应该进行优化，我们先去把服务端代码松耦合，再回过来讨论