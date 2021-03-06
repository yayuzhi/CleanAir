package com.ca.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

@JsonIgnoreProperties(ignoreUnknown=true) //表示JSON转化时忽略未知属性
@TableName("qk_role")
@Data
@Accessors(chain=true)
public class Role extends BasePojo{
    private static final long serialVersionUID = 5342579071652219025L;
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String note;
}
