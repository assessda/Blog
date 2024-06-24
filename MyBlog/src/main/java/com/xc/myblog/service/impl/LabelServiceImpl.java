package com.xc.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xc.myblog.entity.Blog;
import com.xc.myblog.entity.BlogLabel;
import com.xc.myblog.entity.Comment;
import com.xc.myblog.entity.Label;
import com.xc.myblog.mapper.CommentMapper;
import com.xc.myblog.mapper.LabelMapper;
import com.xc.myblog.service.BlogLabelService;
import com.xc.myblog.service.CommentService;
import com.xc.myblog.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabelServiceImpl extends ServiceImpl<LabelMapper, Label> implements LabelService {
    @Autowired
    LabelMapper labelMapper;
    @Autowired
    BlogLabelService blogLabelService;
    @Override
    public List<Label> getAllLabel() {
        return labelMapper.selectList(new QueryWrapper<Label>());
    }

    @Override
    public List<Label> getAllLabelAndNum() {
        List<Label> labels = getAllLabel();
        //查询记录数
        for (Label label : labels) {
            QueryWrapper<BlogLabel> wrapper = new QueryWrapper<>();
            wrapper.eq("content", label.getName());
            int count = blogLabelService.count(wrapper);
            label.setRecords(count);
        }
        return labels;
    }
}
