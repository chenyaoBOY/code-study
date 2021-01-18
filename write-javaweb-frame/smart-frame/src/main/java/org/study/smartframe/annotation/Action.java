package org.study.smartframe.annotation;

import java.lang.annotation.*;

/**
 * @author chenyao
 * @date 2021/1/18 16:47
 * @description
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Action {
    /**
     * 请求的路径
     * @return
     */
    String value();
}
