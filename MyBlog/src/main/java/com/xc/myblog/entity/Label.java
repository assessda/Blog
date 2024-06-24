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
@TableName("m_labels")
public class Label {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @NotBlank(message = "标签名称不能为空")
    String name;
    @TableField(exist = false)
    int records;
}
