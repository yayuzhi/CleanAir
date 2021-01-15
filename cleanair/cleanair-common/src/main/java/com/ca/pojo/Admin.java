package com.ca.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown=true) //表示JSON转化时忽略未知属性
@TableName("qk_admin")
@Data
@Accessors(chain=true)
public class Admin extends BasePojo{
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String password;
    private String salt;
    private Integer valid;
    private Integer roleId;
    private String email;
    private String phone;



}
