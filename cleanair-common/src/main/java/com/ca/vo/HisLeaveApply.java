package com.ca.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
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
@Data
@Accessors(chain = true)
public class HisLeaveApply {
    LeaveApply leaveApply;

    private boolean result;//请假结果 true通过 false未通过
}
