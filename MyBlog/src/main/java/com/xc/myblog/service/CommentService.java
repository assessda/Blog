package com.xc.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xc.myblog.entity.Comment;

public interface CommentService  extends IService<Comment> {
    //删除评论
    public boolean removeCommentByBlogId(Long blogId);
}
