package com.xc.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xc.myblog.entity.Blog;
import com.xc.myblog.entity.BlogLabel;
import com.xc.myblog.mapper.BlogLabelMapper;
import com.xc.myblog.mapper.BlogMapper;
import com.xc.myblog.mapper.UserMapper;
import com.xc.myblog.service.BlogLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
@Service
public class BlogLabelServiceImpl extends ServiceImpl<BlogLabelMapper, BlogLabel> implements BlogLabelService {

    @Autowired
    private BlogLabelMapper blogLabelMapper;

    @Override
    public BlogLabelMapper getBaseMapper() {
        return super.getBaseMapper();
    }


    @Override
    public List<String> getLabelsByBlogId(Long blogId) {
        List<String> results = new ArrayList<>();
        //查询LabelList
        QueryWrapper<BlogLabel> wrapper = new QueryWrapper<>();
        wrapper.eq("blog_id", blogId);
        List<BlogLabel> labelList = blogLabelMapper.selectList(wrapper);
        if (labelList == null) {
            return results;
        }
        //封装为String：List
        for (BlogLabel label : labelList) {
            results.add(label.getContent());
        }
        return results;
    }

    @Override
    public boolean removeAllLabelByBlogId(Long blogId) {
        //删除文章标签
        QueryWrapper<BlogLabel> wrapper = new QueryWrapper<>();
        wrapper.eq("blog_id", blogId);
        blogLabelMapper.delete(wrapper);
        return false;
    }

    @Override
    public List<String> addLabelByBlogId(Long blogId, List<String> labels) {
        //添加标签
        List<String> labelList= getLabelsByBlogId(blogId);
        //判断是否超过限制
        try {
            if (labelList != null && labelList.size() >= 5) {
                throw new IllegalArgumentException("个数超过限制");
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        //添加标签
        for (String label : labels) {
            //如果有相同的标签就不添加
            if (!haveSameLabel(blogId, label)) {
                BlogLabel bloglabel = new BlogLabel();
                bloglabel.setBlogId(blogId);
                bloglabel.setContent(label);
                blogLabelMapper.insert(bloglabel);
            }
        }
        return this.getLabelsByBlogId(blogId);
    }

    @Override
    public boolean haveSameLabel(Long blogId, String label) {
        //查询LabelList
        QueryWrapper<BlogLabel> wrapper = new QueryWrapper<>();
        wrapper.eq("blog_id", blogId);
        List<BlogLabel> labelList = blogLabelMapper.selectList(wrapper);
        if (labelList != null) {
            for (BlogLabel blogLabel : labelList) {
                if (blogLabel.equals(label)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<String> saveBlogLabel(Long blogId, List<String> labels) {
        //先删除原来的标签
        this.removeAllLabelByBlogId(blogId);
        //将新标签加入
        this.addLabelByBlogId(blogId,labels);
        return this.getLabelsByBlogId(blogId);
    }
}
