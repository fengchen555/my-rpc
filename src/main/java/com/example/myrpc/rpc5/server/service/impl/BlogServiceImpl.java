package com.example.myrpc.rpc5.server.service.impl;

import com.example.myrpc.rpc5.commom.entity.Blog;
import com.example.myrpc.rpc5.server.service.BlogService;

// 服务端新的服务接口实现类
public class BlogServiceImpl implements BlogService {
    @Override
    public Blog getBlogById(Integer id) {
        Blog blog = Blog.builder().id(id).title("我的博客").useId(22).build();
        System.out.println("客户端查询了"+id+"博客");
        return blog;
    }
}