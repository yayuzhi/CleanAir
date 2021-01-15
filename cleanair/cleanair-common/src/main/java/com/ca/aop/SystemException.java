package com.ca.aop;

import com.ca.vo.JsonResult;
import com.fasterxml.jackson.databind.util.JSONPObject;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice  //定义全局异常处理
public class SystemException {

    //遇到运行时异常时方法执行.
    //JSONP报错 返回值 callback(JSON) 如果请求参数中包含callback参数,则标识为跨域请求
    @ExceptionHandler({RuntimeException.class})
    public Object fail(Exception e, HttpServletRequest request){
        e.printStackTrace();    //输出异常信息.
        String callback = request.getParameter("callback");
        if(StringUtils.isEmpty(callback)){
            //如果参数为空表示 不是跨域请求.
            return JsonResult.fail();
        }else{
            //有callback参数,表示是跨域请求.
            JsonResult jsonResult = JsonResult.fail();
            return new JSONPObject(callback,jsonResult);
        }
    }
}
