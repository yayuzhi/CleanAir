package com.ca.vo;


import com.ca.pojo.BasePojo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown=true) //表示JSON转化时忽略未知属性
@Data
@Accessors(chain=true)
public class ItemPlus extends BasePojo {

    private Integer id;
    private String title;
    private String sell_point;
    private Long price;
    private Integer num;
    private String image;
    private String itemDesc;

}
