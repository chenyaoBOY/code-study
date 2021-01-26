package org.study.smartframe.proxy.ann;

import java.lang.annotation.*;

/**
 * @author chenyao
 * @date 2021/1/26 15:08
 * @description
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Transaction {
}
