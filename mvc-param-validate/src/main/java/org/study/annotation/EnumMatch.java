package org.study.annotation;

import org.study.validator.EnumMatchValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author chenyao
 * @date 2021/1/4 13:53
 * @description
 */

@Documented
@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = {EnumMatchValidator.class})
public @interface EnumMatch {
    /**
     * 异常信息输出
     * @return
     */
    String message() default "枚举参数校验不通过";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
    /**
     * 枚举类的class类型
     * @return
     */
    Class<?> clazz();
    /**
     * 该枚举值是否非空
     *      若允许为空 则无需校验枚举值了
     *      否则 需要遍历枚举类class
     * @return
     */
    boolean notNull() default true;





}
