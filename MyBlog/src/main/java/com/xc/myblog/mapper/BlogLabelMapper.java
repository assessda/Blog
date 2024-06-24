package com.xc.myblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xc.myblog.entity.BlogLabel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogLabelMapper extends BaseMapper<BlogLabel> {
}
