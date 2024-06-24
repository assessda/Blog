package com.xc.myblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xc.myblog.entity.BlogRecommend;
import com.xc.myblog.mapper.BlogRecommendMapper;
import com.xc.myblog.service.BlogRecommendService;
import org.springframework.stereotype.Service;

@Service
public class BlogRecommendServiceImpl extends ServiceImpl<BlogRecommendMapper, BlogRecommend> implements BlogRecommendService {
}
