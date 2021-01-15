package com.ca.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)//定义我们的注解何时有效
@Target(ElementType.METHOD) //定义我们的注解可以修饰谁
public @interface RequiredLog {//自定义注解
      String value() default "";
}
