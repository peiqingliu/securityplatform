package com.security.platform.common.annotation;

import com.security.platform.common.enums.LogType;

import java.lang.annotation.*;

/**
 * @author LiuPeiQing
 * @version 1.0
 * @date 2020/1/3 13:29
 * @File SystemLog
 * @Software IntelliJ IDEA
 * @description 系统日志自定义注解
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})//作用于参数或方法上
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLog {
    /**
     * 日志名称
     * @return
     */
    String description() default "";

    /**
     * 日志类型
     * @return
     */
    LogType type() default LogType.OPERATION;

}
