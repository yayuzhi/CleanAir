package com.ca.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@TableName("qk_item_desc")
@Data   //重写的toString方法一般只会添加自己的属性信息,父级的属性不会添加
@Accessors(chain = true)
public class ItemDesc extends BasePojo{
    private static final long serialVersionUID = 8747081360422828128L;     //代表商品的详情信息
    @TableId
    private Long itemId;        //要求与商品表数据一致.
    private String itemDesc;    //商品详情信息


}
