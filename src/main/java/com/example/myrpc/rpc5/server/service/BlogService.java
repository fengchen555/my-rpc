package com.example.myrpc.rpc5.server.service;

import com.example.myrpc.rpc5.commom.entity.Blog;

// 新的服务接口
public interface BlogService {
    Blog getBlogById(Integer id);
}