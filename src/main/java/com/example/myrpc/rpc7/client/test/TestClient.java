package com.example.myrpc.rpc7.client.test;

import com.example.myrpc.rpc7.client.NettyRPCClient;
import com.example.myrpc.rpc7.client.RPCClient;
import com.example.myrpc.rpc7.client.RPCClientProxy;
import com.example.myrpc.rpc7.commom.entity.Blog;
import com.example.myrpc.rpc7.commom.entity.User;
import com.example.myrpc.rpc7.server.service.BlogService;
import com.example.myrpc.rpc7.server.service.UserService;

import java.util.concurrent.*;

public class TestClient {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(10); // 创建一个固定大小的线程池

        RPCClient rpcClient = new NettyRPCClient();
        System.out.println("Netty客户端启动了....");

        RPCClientProxy rpcClientProxy = new RPCClientProxy(rpcClient);

        UserService userService = rpcClientProxy.getProxy(UserService.class);
        BlogService blogService = rpcClientProxy.getProxy(BlogService.class);


        // 使用CompletionService来管理异步任务
        CompletionService<User> userCompletionService = new ExecutorCompletionService<>(executor);
        CompletionService<Blog> blogCompletionService = new ExecutorCompletionService<>(executor);
        CompletionService<Integer> insertUserCompletionService = new ExecutorCompletionService<>(executor);

        // 插入用户
        User user = User.builder().userName("张三").id(100).sex(true).build();
        for (int i = 0; i < 10; i++) {  // 示例：执行10次插入操作
            insertUserCompletionService.submit(new InsertUserTask(userService, user));
        }

        // 获取插入用户操作的结果
        for (int i = 0; i < 10; i++) {
            try {
                Future<Integer> futureInsertResult = insertUserCompletionService.take();
                Integer insertResult = futureInsertResult.get();
                System.out.println("向服务端插入数据：" + insertResult);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        // 提交多个任务并立即处理返回结果
        for (int i = 0; i < 10; i++) {
            userCompletionService.submit(new UserTask(userService, 10));
            blogCompletionService.submit(new BlogTask(blogService, 10000));
        }

        // 获取并显示用户查询结果
        for (int i = 0; i < 10; i++) {
            try {
                Future<User> futureUser = userCompletionService.take(); // 阻塞直到有任务完成
                User user1 = futureUser.get();
                System.out.println("从服务端得到的user为：" + user1);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        // 获取并显示博客查询结果
        for (int i = 0; i < 10; i++) {
            try {
                Future<Blog> futureBlog = blogCompletionService.take(); // 阻塞直到有任务完成
                Blog blog = futureBlog.get();
                System.out.println("从服务端得到的blog为：" + blog);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown(); // 关闭线程池
    }
}
