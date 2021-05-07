package com.ca.vo;

import com.ca.pojo.BasePojo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

@JsonIgnoreProperties(ignoreUnknown=true) //表示JSON转化时忽略未知属性
@Data
@Accessors(chain=true)
public class RunApplyTable extends BasePojo {
    String executionid;
    String processInstanceid;
    String businesskey;
    String activityid;
}
