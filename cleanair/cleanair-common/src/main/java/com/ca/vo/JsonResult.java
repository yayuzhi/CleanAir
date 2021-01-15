package com.ca.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**封装服务端响应到客户端的数据,通过此对象定义一种规范会的格式*/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class JsonResult implements Serializable {
    private static final long serialVersionUID = -4971076199594828397L;
    private Integer status; //200成功  201失败
    private String  msg;    //服务器返回的提示信息
    private Object  data;   //服务器数据

    //1.编辑失败方法
    public static JsonResult fail(){

        return new JsonResult(201,"服务器调用失败",null);
    }

    //2.重载成功方法
    public static JsonResult success(){

        return new JsonResult(200,"服务器执行成功",null);
    }

    public static JsonResult success(Object data){

        return new JsonResult(200,"服务器执行成功",data);
    }

    public static JsonResult success(String msg,Object data ){

        return new JsonResult(200,msg,data);
    }

}
