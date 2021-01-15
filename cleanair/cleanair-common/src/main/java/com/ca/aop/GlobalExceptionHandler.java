package com.ca.aop;


import com.ca.vo.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ShiroException.class)
    @ResponseBody
    public JsonResult doHandleShiroException(ShiroException e) {
        JsonResult r = new JsonResult();
        r.setStatus(201);
        if (e instanceof UnknownAccountException) {
            r.setData("账户不存在");
        } else if (e instanceof LockedAccountException) {
            r.setData("账户已被禁用");
        } else if (e instanceof IncorrectCredentialsException) {
            r.setData("密码不正确");
        } else if (e instanceof AuthorizationException) {
            r.setData("没有此操作权限");
        } else {
            r.setData("系统维护中");
        }
        e.printStackTrace();
        return r;
    }


    @ExceptionHandler(RuntimeException.class)
    public JsonResult doHandleRuntimeException(RuntimeException e) {
        e.printStackTrace();//控制台输出
        log.error("exception msg {}" + e.getMessage());
        return  JsonResult.success(e);
    }
}
