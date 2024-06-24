package com.xc.myblog.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import com.xc.myblog.common.lang.Result;
import com.xc.myblog.entity.Blog;
import com.xc.myblog.entity.User;
import com.xc.myblog.service.BlogService;
import com.xc.myblog.service.PraiseService;
import com.xc.myblog.service.UserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/praise")
public class PraiseController {
    @Autowired
    PraiseService praiseService;
    @Autowired
    BlogService blogService;
    @Autowired
    UserService userService;

    @GetMapping("/add/{blogId}")
    public Result addPraise(@PathVariable("blogId") Integer blogId, @RequestParam("id") Integer id) {
        User user = userService.getById(id);
        Blog blog = blogService.getById(blogId);
        if(praiseService.increasePraise(blog, user)) {
            return Result.success("点赞成功");
        } else {
            return Result.fail("重复点赞");
        }
    }

    @GetMapping("/cancel/{blogId}")
    public Result cancelPraise(@PathVariable("blogId") Integer blogId, @RequestParam("id") Integer id) {
        User user = userService.getById(id);
        Blog blog = blogService.getById(blogId);
        if(praiseService.cancelPraise(blog, user)) {
            return Result.success("取消成功");
        } else {
            return Result.fail("还没有点赞，不能取消");
        }
    }

    @GetMapping("/getPraiseStatus")
    public Result getPraiseStatus(@RequestParam("id") Integer id, @RequestParam("blogId") String blogIdStr) {
        User user = userService.getById(id);
        Blog blog = blogService.getById(Integer.parseInt(blogIdStr));
        return Result.success(MapUtil.builder().put("praiseStatus", praiseService.getPraiseStatusByBlogAndUser(blog, user)).map());
    }

}
