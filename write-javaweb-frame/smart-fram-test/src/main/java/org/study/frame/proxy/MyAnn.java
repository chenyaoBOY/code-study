package org.study.frame.proxy;

import java.lang.annotation.*;

/**
 * @author chenyao
 * @date 2021/1/25 11:29
 * @description
 */
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MyAnn {

    String value();
}
