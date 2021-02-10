package org.study.controller.anno;

import org.study.controller.exception.ValidatorException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

/**
 * @author dizhang
 * @date 2021-02-09
 */
public class FieldExistValidator implements ConstraintValidator<FieldExist, Object> {
    private String[] fields;

    private int number;

    @Override
    public void initialize(FieldExist fieldExist) {
        this.fields = fieldExist.fields();
        this.number = fieldExist.number();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        int existNumber = 0;
        for (String fieldName : fields) {
            try {
                Field field = object.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                Object value = field.get(object);
                existNumber += value == null ? 0 : 1;

            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new ValidatorException("FieldExistValidator exception", e);
            }
        }
        return existNumber >= number;
    }
}
