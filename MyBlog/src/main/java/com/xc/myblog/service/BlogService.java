package com.xc.myblog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xc.myblog.entity.Blog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 */
public interface BlogService extends IService<Blog> {
    //根据日期升序排列
    IPage getPageOfInfo(Page page, String oderName, List<String> labels);
    IPage getPageOfInfo(Page page, String oderName);
    //根据用户id查询
    IPage getPageOfUsername(Page page, String userName);
    //查询被推荐的博客
    List<Blog> getRecommendBlog();
    //根据搜索项查询
    IPage getPageOfSearch(Page page, String search, String sortName);
}
