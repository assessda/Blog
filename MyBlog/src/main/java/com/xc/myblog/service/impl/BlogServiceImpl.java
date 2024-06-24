package com.xc.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xc.myblog.common.lang.ValueEntry;
import com.xc.myblog.entity.Blog;
import com.xc.myblog.entity.BlogRecommend;
import com.xc.myblog.entity.User;
import com.xc.myblog.mapper.BlogMapper;
import com.xc.myblog.mapper.UserMapper;
import com.xc.myblog.service.BlogInfoService;
import com.xc.myblog.service.BlogRecommendService;
import com.xc.myblog.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xc.myblog.service.PraiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    BlogMapper blogMapper;
    @Autowired
    PraiseService praiseService;
    @Autowired
    BlogInfoService blogInfoService;
    @Autowired
    BlogRecommendService blogRecommendService;


    @Override
    public IPage getPageOfInfo(Page page, String sortName, List<String> labels) {
        //如果是根据created返回正逆序
        IPage<Blog> blogs = null;
        if (sortName.equals("createdAsc")  || sortName.equals("createdDesc")) {
           blogs = blogMapper.selectPageByCreated(page, sortName, labels);
        } else if (sortName.equals("praiseDesc")) {
            //如果是根据点赞数量排序
            //获取所有用户id
            List<Blog> allBlog = blogMapper.selectList(new QueryWrapper<>());
            List<Map.Entry<Long, Long>> praiseMapList = new ArrayList<Map.Entry<Long, Long>>();
            //查询所有用户的点赞数量
            for (Blog blog : allBlog) {
                Long praises = praiseService.getPraiseNumByBlog(blog);
                praiseMapList.add(new ValueEntry(blog.getId(), praises));
            }
            //根据点赞或者评论排序
            Collections.sort(praiseMapList, new Comparator<Map.Entry<Long, Long>>() {
                @Override
                public int compare(Map.Entry<Long, Long> o1, Map.Entry<Long, Long> o2) {
                    Long o1Val = o1.getValue();
                    Long o1Va2 = o2.getValue();
                    if (o1Val > o1Va2) {
                        return -1;
                    } else{
                        return 1;
                    }
                }
            });
            //根据所得id顺序去查询mysql数据库
            List<Long> ids = new ArrayList<>();
            for (Map.Entry<Long, Long> entry : praiseMapList) {
                ids.add(entry.getKey());
            }
            blogs = blogMapper.selectPageOfList(page, "praiseDesc", ids, labels);
        } else if (sortName.equals("viewsDesc")) {
            //如果是根据阅读数量排序
            //获取所有用户id
            List<Blog> allBlog = blogMapper.selectList(new QueryWrapper<>());
            List<Map.Entry<Long,Long>> viewsMapList = new ArrayList<Map.Entry<Long,Long>>();
            //查询所有用户的点赞数量
            for (Blog blog : allBlog) {
                Long views = blogInfoService.getViews(blog.getId());
                viewsMapList.add(new ValueEntry(blog.getId(), views));
            }
            //根据点赞或者评论排序
            Collections.sort(viewsMapList, new Comparator<Map.Entry<Long,Long>>() {
                @Override
                public int compare(Map.Entry<Long,Long> o1, Map.Entry<Long,Long> o2) {
                    Long o1Val = o1.getValue();
                    Long o1Va2 = o2.getValue();
                    if (o1Val > o1Va2) {
                        return -1;
                    } else{
                        return 1;
                    }
                }
            });
            //根据所得id顺序去查询mysql数据库
            List<Long> ids = new ArrayList<>();
            for (Map.Entry<Long,Long> entry : viewsMapList) {
                ids.add(entry.getKey());
            }
            blogs = blogMapper.selectPageOfList(page, "viewsDesc", ids, labels);
        } else {
            throw new IllegalArgumentException("参数错误");
        }
        return blogs;
    }

    @Override
    public IPage getPageOfInfo(Page page, String oderName) {
        return this.getPageOfInfo(page, oderName, null);
    }

    @Override
    public IPage getPageOfUsername(Page page, String userName) {
        return this.blogMapper.selectPageOfUsername(page, userName);
    }

    @Override
    public List<Blog> getRecommendBlog() {
        List<BlogRecommend> list = blogRecommendService.list();
        List<Blog> ans = new ArrayList<>();
        for (BlogRecommend recommend : list) {
            ans.add(blogMapper.selectById(recommend.getBlogId()));
        }
        return ans;
    }

    /*根据搜索结果查询*/
    @Override
    public IPage getPageOfSearch(Page page, String search, String sortName) {

        //如果是根据created返回正逆序
        IPage<Blog> blogs = null;
        if (sortName.equals("createdAsc")  || sortName.equals("createdDesc")) {
            blogs = blogMapper.selectPageBySearch(page, search, sortName, null);
        } else if (sortName.equals("praiseDesc")) {
            //如果是根据点赞数量排序
            //获取所有用户id
            List<Blog> allBlog = blogMapper.selectList(new QueryWrapper<>());
            List<Map.Entry<Long, Long>> praiseMapList = new ArrayList<Map.Entry<Long, Long>>();
            //查询所有用户的点赞数量
            for (Blog blog : allBlog) {
                Long praises = praiseService.getPraiseNumByBlog(blog);
                praiseMapList.add(new ValueEntry(blog.getId(), praises));
            }
            //根据点赞或者评论排序
            Collections.sort(praiseMapList, new Comparator<Map.Entry<Long, Long>>() {
                @Override
                public int compare(Map.Entry<Long, Long> o1, Map.Entry<Long, Long> o2) {
                    Long o1Val = o1.getValue();
                    Long o1Va2 = o2.getValue();
                    if (o1Val > o1Va2) {
                        return -1;
                    } else{
                        return 1;
                    }
                }
            });
            //根据所得id顺序去查询mysql数据库
            List<Long> ids = new ArrayList<>();
            for (Map.Entry<Long, Long> entry : praiseMapList) {
                ids.add(entry.getKey());
            }
            blogs = blogMapper.selectPageBySearch(page, search, "praiseDesc",  ids);
        } else if (sortName.equals("viewsDesc")) {
            //如果是根据阅读数量排序
            //获取所有用户id
            List<Blog> allBlog = blogMapper.selectList(new QueryWrapper<>());
            List<Map.Entry<Long,Long>> viewsMapList = new ArrayList<Map.Entry<Long,Long>>();
            //查询所有用户的点赞数量
            for (Blog blog : allBlog) {
                Long views = blogInfoService.getViews(blog.getId());
                viewsMapList.add(new ValueEntry(blog.getId(), views));
            }
            //根据点赞或者评论排序
            Collections.sort(viewsMapList, new Comparator<Map.Entry<Long,Long>>() {
                @Override
                public int compare(Map.Entry<Long,Long> o1, Map.Entry<Long,Long> o2) {
                    Long o1Val = o1.getValue();
                    Long o1Va2 = o2.getValue();
                    if (o1Val > o1Va2) {
                        return -1;
                    } else{
                        return 1;
                    }
                }
            });
            //根据所得id顺序去查询mysql数据库
            List<Long> ids = new ArrayList<>();
            for (Map.Entry<Long,Long> entry : viewsMapList) {
                ids.add(entry.getKey());
            }
            blogs = blogMapper.selectPageBySearch(page, search,"viewsDesc", ids);
        } else {
            throw new IllegalArgumentException("参数错误");
        }
        return blogs;
    }

}
