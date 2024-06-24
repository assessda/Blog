package com.xc.myblog.service;

import com.xc.myblog.entity.Blog;
import com.xc.myblog.entity.User;

public interface PraiseService {
    //根据博客获取点赞个数
    public Long getPraiseNumByBlog(Blog blog);
    //根据用户和博客获取点赞状态
    public boolean getPraiseStatusByBlogAndUser(Blog blog, User user);
    //根据用户点赞，给redis中增加点赞功能
    public boolean increasePraise(Blog blog, User user);
    //取消点赞
    public boolean cancelPraise(Blog blog, User user);
    //根据用户获取所有点赞数量
    public Integer getPraisesByUser(User user);
}
