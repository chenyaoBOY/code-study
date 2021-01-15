package org.study.validator;

import org.study.annotation.PageValue;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author chenyao
 * @date 2020/12/15 10:59
 * @description
 */
public class PageValidator implements ConstraintValidator<PageValue, Integer> {
    private PageValue ann;

    @Override
    public void initialize(PageValue ann) {
        this.ann = ann;
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return true;
    }
}
