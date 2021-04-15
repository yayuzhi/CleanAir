package com.ca.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true) //表示JSON转化时忽略未知属性
@TableName("qk_admin_role")
@Data
@Accessors(chain=true)
public class AdminRole implements Serializable {
    private static final long serialVersionUID = -4658539344415213638L;
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer adminId;
    private Integer roleId;
}
