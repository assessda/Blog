package com.xc.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xc.myblog.entity.Blog;
import com.xc.myblog.entity.Comment;
import com.xc.myblog.mapper.BlogMapper;
import com.xc.myblog.mapper.CommentMapper;
import com.xc.myblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public boolean removeCommentByBlogId(Long blogId) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("blog_id", blogId);
        commentMapper.delete(wrapper);
        return true;
    }
}
