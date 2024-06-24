package com.xc.myblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("m_skills")
public class Skills {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @NotBlank(message = "用户id不能为空")
    private Long userId ;
    @NotBlank(message = "标签不能为空")
    private String skill;


    public Skills() {
        this.userId = userId;
        this.skill = skill;
    }

    public Skills(Long userId, String skill) {
        this.userId = userId;
        this.skill = skill;
    }
}
