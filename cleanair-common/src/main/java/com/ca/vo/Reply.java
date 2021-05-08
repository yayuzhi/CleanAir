package com.ca.vo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown=true) //表示JSON转化时忽略未知属性
@Data
@Accessors(chain=true)
public class Reply {

    private Integer id;  //表单主键

    private String processInstanceId;//流程实例id

    private String username;//申请人name


    private String duration; //请假时长


    private String leaveType; //请假类型






}
