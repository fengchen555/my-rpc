package com.example.myrpc.rpc7.client.test;

import com.example.myrpc.rpc7.commom.entity.Blog;
import com.example.myrpc.rpc7.commom.entity.User;
import com.example.myrpc.rpc7.server.service.BlogService;
import com.example.myrpc.rpc7.server.service.UserService;

import java.util.concurrent.Callable;
public class BlogTask implements Callable<Blog> {
    private BlogService blogService;
    private int blogId;

    public BlogTask(BlogService blogService, int blogId) {
        this.blogService = blogService;
        this.blogId = blogId;
    }

    @Override
    public Blog call() throws Exception {
        return blogService.getBlogById(blogId);
    }
}