package com.xc.myblog.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("m_blog_recommend")
public class BlogRecommend {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @NotBlank(message = "博客id不能为空")
    private Long blogId ;
}