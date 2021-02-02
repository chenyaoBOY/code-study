package org.study.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author chenyao
 * @date 2021/2/1 16:14
 * @description
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogAnn {
    String logTag() default "";

    String bizID() default "";
    /**
     * 是否记录ACCESS日志
     */
    boolean accessLog() default false;
    /**
     * 耗时多少以上才记录ACCESS日志
     */
    long logThreshold() default 200;
}
