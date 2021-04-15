package com.ca.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true) //表示JSON转化时忽略未知属性
@TableName("qk_order_item")
@Data
@Accessors(chain=true)
public class OrderItem implements Serializable {
    private static final long serialVersionUID = -8001333438236807828L;
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long ItemId;
    private Long OrderId;
    private String title;
    private Long price;
    private Integer num;
    private String image;

}
