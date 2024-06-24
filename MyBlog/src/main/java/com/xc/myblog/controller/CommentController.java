package com.xc.myblog.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xc.myblog.common.lang.Result;
import com.xc.myblog.dto.CommentDto;
import com.xc.myblog.entity.Blog;
import com.xc.myblog.entity.Comment;
import com.xc.myblog.entity.User;
import com.xc.myblog.service.BlogService;
import com.xc.myblog.service.CommentService;
import com.xc.myblog.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.management.Query;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;
    @Autowired
    BlogService blogService;

    //获取当前博客的所有评论
    @GetMapping("/get/{blogId}")
    public Result getCurrentComment(@PathVariable("blogId") Long blogId) {
        //封装Map查询对象
        Map<String, Object> map = new HashMap<>();
        map.put("blog_id", blogId);
        //根据博客id查询并返回创建时间的倒序Comment
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("created");
        wrapper.eq("blog_id", blogId);
        Collection<Comment> comments = commentService.list(wrapper);
        //结果集List，其中对象为前端需要的数据
        List<CommentDto> commentDtos = new ArrayList<>();
        //将Comment封装成前端需要的数据返回
        for (Comment comment : comments) {
            User user = userService.getById(comment.getUserId());
            CommentDto dto = new CommentDto();
            //复制comment的属性
            BeanUtil.copyProperties(comment, dto);
            dto.setAvatar(user.getAvatar());
            dto.setUsername(user.getUsername());
            commentDtos.add(dto);
        }
        return Result.success(commentDtos);
    }

    @PostMapping("/add")
    @RequiresAuthentication
    public Result addComment(@RequestBody Comment comment) {
        comment.setCreated(LocalDateTime.now());
        if (comment.getBlogId() != null && comment.getBlogId() != null) {
            //评论内容不能为空
            if (comment.getContent() != null && comment.getContent().length() != 0) {
                //数据库添加
                commentService.save(comment);
                return Result.success("评论成功");
            } else {
                return Result.fail("评论内容不能为空");
            }
        }
        return Result.fail("用户不存在或者博客不存在");
    }

    @DeleteMapping("/delete/{commentId}")
    @RequiresAuthentication
    public Result deleteComment(@PathVariable("commentId") Long commentId, @RequestBody() User user) {
        Comment comment = commentService.getById(commentId);
        Blog blog = blogService.getById(comment.getBlogId());
        //判断用户是否对应,或者为当前博客的主人
        if (user.getId() == comment.getUserId() || comment.getUserId() == blog.getUserId()) {
            commentService.removeById(comment.getId());
            return Result.success("删除成功");
        } else {
            return Result.fail("权限不足");
        }
    }
}
