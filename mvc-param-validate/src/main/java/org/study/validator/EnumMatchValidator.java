package org.study.validator;

import org.study.annotation.EnumMatch;
import org.study.vo.BaseEnum;
import org.study.vo.RefundPathEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author chenyao
 * @date 2021/1/4 13:53
 * @description
 */
public class EnumMatchValidator implements ConstraintValidator<EnumMatch,Object> {

    private String msg;
    private Class<?> clazz;
    private boolean notNull;
    @Override
    public void initialize(EnumMatch ann) {
        this.msg = ann.message();
        this.clazz=ann.clazz();
        this.notNull = ann.notNull();
    }

    @Override
    public boolean isValid(Object param, ConstraintValidatorContext context) {
        if(!notNull) return true;
        if(clazz == null) return true;
        if(!clazz.isEnum()) return true;

        try {
            BaseEnum[] enums = (BaseEnum[]) clazz.getEnumConstants();
            if(param instanceof Integer){
                for (BaseEnum en : enums) {
                    if(en.getCode().equals(param)) return true;
                }
            }
            context.buildConstraintViolationWithTemplate(msg);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    public static void main(String[] args) {
        Object[] enumConstants = RefundPathEnum.class.getEnumConstants();
        for (Object enumConstant : enumConstants) {
            System.out.println(enumConstant.toString());
        }
    }
}
