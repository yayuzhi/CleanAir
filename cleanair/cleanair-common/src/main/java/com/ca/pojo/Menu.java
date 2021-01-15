package com.ca.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true) //表示JSON转化时忽略未知属性
@TableName("qk_menus")
@Data
@Accessors(chain=true)
public class Menu implements Serializable {
    private static final long serialVersionUID = -3915152447018388096L;
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**菜单名称*/
    private String name;
    /**菜单url: log/doFindPageObjects*/
    private String url;
    /**菜单类型(两种:按钮,普通菜单)*/
    private Integer type=1;
    /**排序(序号)*/
    private Integer sort;
    /**备注*/
    private String note;
    /**上级菜单id*/
    @TableField("parentId")
    private Integer parentId;
    /**菜单对应的权限标识(sys:log:delete)*/
    private String permission;
}
