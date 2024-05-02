package com.example.myrpc.rpc7.server.service;

import com.example.myrpc.rpc7.commom.entity.Blog;

// 新的服务接口
public interface BlogService {
    Blog getBlogById(Integer id);
}