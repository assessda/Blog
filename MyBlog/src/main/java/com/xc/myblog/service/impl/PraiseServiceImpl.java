package com.xc.myblog.service.impl;

import com.xc.myblog.entity.Blog;
import com.xc.myblog.entity.User;
import com.xc.myblog.service.BlogService;
import com.xc.myblog.service.PraiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class PraiseServiceImpl implements PraiseService {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    BlogService blogService;
    @Override
    public Long getPraiseNumByBlog(Blog blog) {
        Long id = blog.getId();
        String str = "blog:praise:" + blog.getId();
        Long total = redisTemplate.opsForList().size(str);
        return total;
    }

    @Override
    public boolean getPraiseStatusByBlogAndUser(Blog blog, User user) {
        //博客点赞的用户列表
        List list = redisTemplate.opsForList().range("blog:praise:" + blog.getId(), 0, -1);
        //从列表中判断用户是否点过赞
        return list.contains("userId:" + user.getId());
    }

    @Override
    public boolean increasePraise(Blog blog, User user) {
        //如果没有点过赞
        if (!getPraiseStatusByBlogAndUser(blog, user)) {
            redisTemplate.opsForList().leftPush("blog:praise:" + blog.getId(), "userId:" + user.getId());
            return true;
        }
        return false;
    }

    @Override
    public boolean cancelPraise(Blog blog, User user) {
        //如果点过赞就删除
        if (getPraiseStatusByBlogAndUser(blog, user)) {
            //删除该用户点赞
            redisTemplate.opsForList().remove("blog:praise:" + blog.getId(), 0, "userId:" + user.getId());
            return true;
        }
        return false;
    }

    @Override
    public Integer getPraisesByUser(User user) {
        int count = 0;
        //获取所有博客id
        List<Blog> blogs = blogService.list();
        for (Blog blog : blogs) {
            //从博客的点赞池中查询是否点过赞
            if (blog.getUserId() == user.getId()) {
                count+=this.getPraiseNumByBlog(blog);
            }
        }
        return count;
    }
}
