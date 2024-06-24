package com.xc.myblog.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xc.myblog.entity.Blog;
import com.xc.myblog.entity.BlogLabel;
import com.xc.myblog.entity.User;
import com.xc.myblog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BlogInfoUtils {

    @Autowired
    BlogLabelService blogLabelService;
    @Autowired
    PraiseService praiseService;
    @Autowired
    BlogInfoService blogInfoService;
    @Autowired
    UserService userService;


    public IPage<Blog> addToInfo(IPage<Blog> pageData) {
        //获取点赞数量
        for (Object object : pageData.getRecords()) {
            Blog blog = (Blog) object;
            //获取标签信息
            List<String> labels = blogLabelService.getLabelsByBlogId(blog.getId());
            //加入点赞、评论、阅读量、标签信息
            blog.setPraises(praiseService.getPraiseNumByBlog(blog));
            blog.setViews(blogInfoService.getViews(blog.getId()));
            blog.setComments(blogInfoService.getComments(blog.getId()));
            blog.setLabels(labels);
            //查询用户信息并加入用户信息
            User user = userService.getById(blog.getUserId());
            blog.setUsername(user.getUsername());
            blog.setAvatar(user.getAvatar());
        }
        return pageData;
    }


}
