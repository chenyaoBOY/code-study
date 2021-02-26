package org.study.controller.anno;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

/**
 * @author dizhang
 * @date 2021-02-09
 */


@Target({TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FieldExistValidator.class)
public @interface FieldExist {

    String message() default "fields 数量不满足 number要求";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] fields() default {};

    int number() default 0;
}
