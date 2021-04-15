package com.ca.aspect;

import com.ca.annotation.RequiredLog;
import com.ca.pojo.Log;
import com.ca.service.LogService;
import com.ca.util.IPUtils;
import com.ca.util.ShiroUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author yayuzhi
 */

/**
 * 日志切面 AOP
 */
@Aspect
@Component
public class LogAspect {

    private Logger log = LoggerFactory.getLogger(LogAspect.class);


    @Autowired
    private LogService logService;

    @Pointcut("@annotation(com.ca.annotation.RequiredLog)")
    public void  logPointCut(){}


    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint //连接点
                                 jointPoint) throws Throwable{
        long startTime=System.currentTimeMillis();
        //执行目标方法(result为目标方法的执行结果)
        Object result=jointPoint.proceed();
        long endTime=System.currentTimeMillis();
        long totalTime=endTime-startTime;
        log.info("方法执行的总时长为:"+totalTime);
        saveLog(jointPoint,totalTime);
        return result;

    }

    private void saveLog(ProceedingJoinPoint point, long totleTime) throws NoSuchMethodException, SecurityException, JsonProcessingException {
        //1.获取日志信息
        MethodSignature ms= (MethodSignature)point.getSignature();
        Class<?> targetClass=point.getTarget().getClass();
        String className=targetClass.getName();
        //获取接口声明的方法
        String methodName=ms.getMethod().getName();
        Class<?>[] parameterTypes=ms.getMethod().getParameterTypes();
        //获取目标对象方法(AOP版本不同,可能获取方法对象方式也不同)
        Method targetMethod=targetClass.getDeclaredMethod(methodName,parameterTypes);
        //获取用户名
        String username= ShiroUtils.getAdminname();
        //获取方法参数
        Object[] paramsObj=point.getArgs();
        System.out.println("paramsObj="+paramsObj);
        //将参数转换为字符串
        String params=new ObjectMapper().writeValueAsString(paramsObj);
        //2.封装日志信息
        Log log=new Log();
        //登陆的用户
        log.setUsername(username);
        //假如目标方法对象上有注解,我们获取注解定义的操作值
        RequiredLog requestLog= targetMethod.getDeclaredAnnotation(RequiredLog.class);
        if(requestLog!=null){
            log.setOperation(requestLog.value());
        }
        //className.methodName()
        log.setMethod(className+"."+methodName);
        //method params
        log.setParams(params);
        //ip 地址
        log.setIp(IPUtils.getIpAddr());
        //获取方法执行花费的总时间
        log.setTime(totleTime);
        log.setCreatedTime(new Date());
        //3.保存日志信息
        logService.savelog(log);
    }

}
