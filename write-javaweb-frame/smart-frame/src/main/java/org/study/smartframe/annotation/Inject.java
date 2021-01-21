package org.study.smartframe.annotation;

import java.lang.annotation.*;

/**
 * @author chenyao
 * @date 2021/1/18 16:47
 * @description
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Inject {
}
