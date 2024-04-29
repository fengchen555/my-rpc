package com.example.myrpc.rpc6.server.service;

import com.example.myrpc.rpc6.commom.entity.Blog;

// 新的服务接口
public interface BlogService {
    Blog getBlogById(Integer id);
}