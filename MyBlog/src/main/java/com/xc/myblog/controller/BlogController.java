package com.xc.myblog.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.xpath.internal.objects.XNull;
import com.xc.myblog.common.lang.Result;
import com.xc.myblog.entity.Blog;
import com.xc.myblog.entity.Comment;
import com.xc.myblog.entity.User;
import com.xc.myblog.service.*;
import com.xc.myblog.util.BlogInfoUtils;
import com.xc.myblog.util.ShiroUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 * @since 2022-02-22
 */
@Controller
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    BlogService blogService;
    @Autowired
    PraiseService praiseService;
    @Autowired
    CommentService commentService;
    @Autowired
    BlogInfoService blogInfoService;
    @Autowired
    UserService userService;
    @Autowired
    AddressService addressService;
    @Autowired
    BlogLabelService blogLabelService;
    @Autowired
    FileService fileService;
    @Autowired
    BlogInfoUtils blogInfoUtils;

    @GetMapping("/test")
    @ResponseBody
    public Object test() {
            List<Blog> list = blogService.list();
            return Result.success(list);
    }

    /*博客分页展示*/
    /*没有输入第几页自动跳转*/
    @GetMapping("/page")
    public String getPage() {
        return "forward:/blog/page/1";
    }

    @GetMapping("/page/{page}")
    @ResponseBody
    public Result getPage(@PathVariable("page") Integer current, @RequestParam("sortName") String sortName,
                          @RequestParam("pageSize") Integer pageSize,
                          @RequestParam(value = "label", required = false) String label,
                          @RequestParam(value = "user", required = false) Integer userId) {
        //创建标签筛选列表
        List<String> labelList = new ArrayList<>();
        if (label != null && label.length() != 0) {
            labelList.add(label);
        }
        //判断页数
        if (current == null || current < 1) {
            current =1;
        }
        Page page = new Page(current, pageSize);
        IPage pageData = blogService.getPageOfInfo(page, sortName, labelList);
        //添加其他各种信息
        pageData = blogInfoUtils.addToInfo(pageData);
        return Result.success(pageData);
    }




    /*没有输入第几页自动跳转*/
    @GetMapping("/page/user")
    public String getPageByUserId() {
        return "forward:/blog/page/user/1";
    }

    /*博客分页展示*/
    @GetMapping("/page/user/{page}")
    @ResponseBody
    public Result getPageByUserId(@PathVariable("page") Integer current,
                          @RequestParam("pageSize") Integer pageSize,
                          @RequestParam("username") String username) {
        //判断页数
        if (current == null || current < 1) {
            current =1;
        }
        Page page = new Page(current, pageSize);
        IPage pageData = blogService.getPageOfUsername(page, username);
        //添加其他各种信息
        pageData = blogInfoUtils.addToInfo(pageData);
        return Result.success(pageData);
    }




    /*编辑功能*/
    @PostMapping("/edit")
    @RequiresAuthentication
    @ResponseBody
    public Result edit(@RequestBody Blog blog) {
        Blog temp = new Blog();
        //如果权限不够
        if (blog.getId() != null) {
            temp = blogService.getById(blog.getId());
            //比较ID如果不一样，就不是自己的，不能修改
            Assert.isTrue(temp.getUserId().equals(ShiroUtil.getProfile().getId()), "修改权限不足");;
        } else {
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }
        BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "status");
        //更新博客
        blogService.saveOrUpdate(temp);
        //更新标签
        blogLabelService.saveBlogLabel(temp.getId(), temp.getLabels());
        return Result.success(temp);

    }
    /*删除博客功能*/
    @DeleteMapping("/delete")
    @RequiresAuthentication
    @ResponseBody
    public Result deleteBlog(@RequestParam("user") String userStr,
                             @RequestParam("blog") String blogStr) {
        User user = JSONUtil.parseObj(userStr).toBean(User.class);
        Blog blog = JSONUtil.parseObj(blogStr).toBean(Blog.class);
        //如果没有权限，不能删除别人的博客
        if (user.getId() != blog.getUserId()) {
            return Result.fail(403, "没有权限", null);
        } else {
            //删除博客
            blogService.removeById(blog.getId());
            //删除标签表
            blogLabelService.removeAllLabelByBlogId(blog.getId());
            //删除评论
            commentService.removeCommentByBlogId(blog.getId());
            return Result.success("删除成功");
        }
    }
    /*上传博客图片*/
    @PostMapping("/imgUpload")
    @RequiresAuthentication
    @ResponseBody
    public Result upLoadImg(@RequestPart("image") MultipartFile image) throws IOException {
        //获取上传文件(返回的为文件存储的路径)
        String url = fileService.fileUpload(image);
        return Result.success("上传成功",url);
    }
    /*取消上传图片或者删除*//*
    @DeleteMapping("/deleteBlogImg")
    @RequiresAuthentication
    @ResponseBody
    public Result deleteBlogImage(@RequestParam("file") String fileName, @RequestParam("user") String userStr) {
        User user = JSONUtil.parse(userStr).toBean(User.class);
        //从文件名称获取所属用户id
        int index = fileName.indexOf('-');
        int fileId = Integer.parseInt(fileName.substring(0, index));
        //如果当前文件id 不等于 当前用户id，那么可以删除
        if (user.getId() != fileId) {
            return Result.fail("删除失败，权限不足");
        }
        //图片路径
        String path = System.getProperty("user.dir") + "/src/main/resources/upload/blog/Img/" + user.getId() + "/" + fileName;
        //如果不存在，那么删除失败
        File file = new File(path);
        if (!file.exists()) {
            return Result.fail("图片不存在！");
        } else {
            file.delete();
            return Result.success("删除成功");
        }
    }*/

    /*博客详情*/
    @GetMapping("/detail/{blogId}")
    @ResponseBody
    public Result getDetails(@PathVariable Long blogId) {
        Blog blog = blogService.getById(blogId);
        Assert.notNull(blog, "该博客不存在");
        //加入点赞信息
        Long praises = praiseService.getPraiseNumByBlog(blog);
        blog.setPraises(praises);
        //增加阅读量
        blog.setViews(blogInfoService.viewsIncrease(blogId));
        //加入用户信息
        User user = userService.getUserInfoById(blog.getUserId());
        //设置用户头像和用户名
        blog.setUsername(user.getUsername());
        blog.setAvatar(user.getAvatar());
        //查询标签
        List<String> labels = blogLabelService.getLabelsByBlogId(blogId);
        blog.setLabels(labels);
        return Result.success(blog);
    }

    //返回搜索内容
    @GetMapping("/search/{content}/{current}")
    @ResponseBody
    public Result getBlogBySearch(@PathVariable("content") String searchContent,
                                  @PathVariable("current") Integer current,
                                  @RequestParam("pageSize") Integer pageSize,
                                  @RequestParam("sortName") String sortName) {

        IPage pageData = blogService.getPageOfSearch(new Page(current, pageSize), searchContent, sortName);
        //添加其他各种信息
        pageData = blogInfoUtils.addToInfo(pageData);
        return Result.success("操作成功", pageData);
    }


    @GetMapping("/recommend")
    @ResponseBody
    public Result getRecommendBlog() {
        return Result.success("操作成功",blogService.getRecommendBlog());
    }
}
