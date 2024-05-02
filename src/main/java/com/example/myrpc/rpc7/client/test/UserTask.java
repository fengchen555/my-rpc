package com.example.myrpc.rpc7.client.test;

import com.example.myrpc.rpc7.commom.entity.Blog;
import com.example.myrpc.rpc7.commom.entity.User;
import com.example.myrpc.rpc7.server.service.BlogService;
import com.example.myrpc.rpc7.server.service.UserService;

import java.util.concurrent.Callable;

public class UserTask implements Callable<User> {
    private UserService userService;
    private int userId;

    public UserTask(UserService userService, int userId) {
        this.userService = userService;
        this.userId = userId;
    }

    @Override
    public User call() throws Exception {
        return userService.getUserByUserId(userId);
    }




}


