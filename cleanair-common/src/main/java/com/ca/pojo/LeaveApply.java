package com.ca.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.awt.event.PaintEvent;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true) //表示JSON转化时忽略未知属性
@TableName("qk_leavemessage")
@Data
@Accessors(chain = true)
public class LeaveApply extends BasePojo {
    @TableId(type = IdType.AUTO)
    private Integer id;  //表单主键

    private String processInstanceId;//流程实例id

    private Integer userId;//申请人id

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date start;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date end;

    private String leaveType;//请假类型

    private String reason;//请假原因

    private Boolean result; //结果信息

    @TableField(exist = false)
    private String applicant; //申请人
}
