package com.example.myrpc.server.service;

import com.example.myrpc.pojo.entity.User;

public interface UserService {
    // 客户端通过这个接口调用服务端的实现类
    User getUserByUserId(Integer id);
}