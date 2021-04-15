package com.ca.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ImageVO {
    // {"error":0,"url":"图片的保存路径","width":图片的宽度,"height":图片的高度}
    private Integer error;  //错误信息   0 正常  1 失败
    private String  url;    //图片网址
    private Integer width;  //宽度
    private Integer height; //高度

    //准备API 简化用户操作
    public static com.ca.vo.ImageVO fail(){

        return new com.ca.vo.ImageVO(1, null, null, null);
    }

    public static com.ca.vo.ImageVO success(String url, Integer width, Integer height){

        return new com.ca.vo.ImageVO(0,url, width, height);
    }

}
