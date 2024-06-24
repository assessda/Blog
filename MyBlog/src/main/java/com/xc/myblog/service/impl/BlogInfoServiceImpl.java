package com.xc.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xc.myblog.entity.Comment;
import com.xc.myblog.service.BlogInfoService;
import com.xc.myblog.service.BlogService;
import com.xc.myblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class BlogInfoServiceImpl implements BlogInfoService {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    CommentService commentService;

    @Override
    public Long getViews(Long blogId) {
        String views = (String) redisTemplate.opsForValue().get("views:" + blogId);
        if (views == null) {
            return 0l;
        }
        return Long.valueOf(views);
    }

    @Override
    public Long viewsIncrease(Long blogId) {
        Long views = redisTemplate.opsForValue().increment("views:" + blogId);
        return views;
    }

    @Override
    public Long getComments(Long blogId) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("blog_id", blogId);
        int count = commentService.count(wrapper);
        return Long.valueOf(count);
    }
}
