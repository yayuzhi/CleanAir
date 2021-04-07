package com.ca.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown=true) //表示JSON转化时忽略未知属性
@TableName("qk_order")
@Data
@Accessors(chain=true)
public class Order extends BasePojo{

    private static final long serialVersionUID = 8187380989812640400L;
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long orderId; //订单编号
    private Long payment; //订单总金额 1
    private Integer status; //订单状态
    private Date start;  //租用开始时间
    private Date end;    //租用结束
    private Long userId; //用户Id
    private String userName; //收货人姓名
    private String address; //收货地址 1
    private String phone; //联系电话   1

    //   这是用户/工作人员的操作 a 是用户 b 是 工作人员   //前面为用户看到的意思 // 后面是工作人员看到的状态
   //1、 a确认订单    运输中/派件中 2、 a确认收货    租用中/租用中  3、   a结束租用  订单结束//取货中   4、  b取货成功   订单结束//商品入库 订单完成






}
