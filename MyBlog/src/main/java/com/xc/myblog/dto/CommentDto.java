package com.xc.myblog.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CommentDto implements Serializable {

    @NotBlank(message = "评论id不能为空")
    private Long id;

    @NotBlank(message = "用户id不能为空")
    private Long userId;

    @NotBlank(message = "博客id不能为空")
    private Long blogId;

    @NotBlank(message = "用户名不能为空")
    public String username;

    @NotBlank(message = "头像不能为空")
    private String avatar;

    @NotBlank(message = "评论不能为空")
    public String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;
}
