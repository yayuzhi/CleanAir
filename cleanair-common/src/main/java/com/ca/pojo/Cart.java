package com.ca.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

@JsonIgnoreProperties(ignoreUnknown=true) //表示JSON转化时忽略未知属性
@TableName("qk_cart")
@Data
@Accessors(chain = true)
public class Cart extends BasePojo{
    private static final long serialVersionUID = -4440774019861086917L;
    @TableId(type = IdType.AUTO)     //主键自增
    private Long id; //购物车Id号
    private Long userId; //用户Id号
    private Long itemId; //商品id号
    private String itemTitle;    //商品标题
    private String itemImage;    //商品图片信息
    private Long itemPrice;
    private Integer num;
}