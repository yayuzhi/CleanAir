package com.ca.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true) //表示JSON转化时忽略未知属性
@TableName("qk_menus")
@Data
@Accessors(chain=true)
public class MenuVO implements Serializable {
    private static final long serialVersionUID = -3915152447018388096L;
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**菜单名称*/
    private String name;
    /**上级菜单id*/
    private Integer pid;

//    private Boolean parent;
}
