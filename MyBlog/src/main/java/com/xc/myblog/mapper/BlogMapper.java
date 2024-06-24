package com.xc.myblog.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xc.myblog.entity.Blog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 */
@Repository
public interface BlogMapper extends BaseMapper<Blog> {
    //根据创建时间来返回正序或者逆序的数据
    IPage<Blog> selectPageByCreated(Page page, @Param("sortWay") String sortWay, @Param("labels") List<String> lables);
    //根据指定id列表来排序数据
    IPage<Blog> selectPageOfList(Page page, @Param("sortWay") String sortWay, @Param("ids") List<Long> ids,  @Param("labels") List<String> lables);
    //根据用户名称查询博客
    IPage<Blog> selectPageOfUsername(Page page, @Param("username") String username);
    //根据搜索内用来查询
    IPage<Blog> selectPageBySearch(Page page, @Param("param") String searchParam, @Param("sortWay") String sortName, @Param("ids") List<Long> ids);
}
