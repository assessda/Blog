package com.xc.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xc.myblog.entity.BlogLabel;

import java.util.List;

public interface BlogLabelService extends IService<BlogLabel> {
    //根据博客id获取所有标签
    public List<String> getLabelsByBlogId(Long blogId);
    //删除文章标签
    public boolean removeAllLabelByBlogId(Long blogId);
    //添加标签
    public List<String> addLabelByBlogId(Long blogId, List<String> labels);
    //判断当前标签是否重复
    public boolean haveSameLabel(Long blogId, String label);
    //修改文章标签
    public List<String> saveBlogLabel(Long blogId, List<String> labels);
}
