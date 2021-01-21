package org.study.smartframe.proxy.ann;

import java.lang.annotation.*;

/**
 * @author chenyao
 * @date 2021/1/20 13:40
 * @description
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Aspect {

    Class<? extends Annotation> value();
}
