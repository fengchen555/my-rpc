package com.example.myrpc.rpc7.client.test;

import com.example.myrpc.rpc7.commom.entity.User;
import com.example.myrpc.rpc7.server.service.UserService;

import java.util.concurrent.Callable;

public class InsertUserTask implements Callable<Integer> {
    private UserService userService;
    private User user;

    public InsertUserTask(UserService userService, User user) {
        this.userService = userService;
        this.user = user;
    }

    @Override
    public Integer call() throws Exception {
        return userService.insertUserId(user);
    }
}
