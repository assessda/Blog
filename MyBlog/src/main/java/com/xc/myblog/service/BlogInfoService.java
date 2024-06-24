package com.xc.myblog.service;

import com.xc.myblog.entity.Blog;

import java.util.List;

public interface BlogInfoService {
    //获取阅读量
    public Long getViews(Long BlogId);
    //增加阅读量
    public Long viewsIncrease(Long BlogId);
    //获取评论总数
    public Long getComments(Long blogId);
}
