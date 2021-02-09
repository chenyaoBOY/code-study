package org.study.controller.anno;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author dizhang
 * @date 2021-02-09
 */
public class PasswordValidator implements ConstraintValidator<Password,String> {
    private int length;
    private int nonAlpha;

    @Override
    public void initialize(Password password) {
        this.length = password.length();
        this.nonAlpha = password.nonAlpha();

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value.length() < length) {
            return false;
        }
        int nonAlphaNr = 0;
        for (int i = 0; i < value.length(); i++) {
            if (!Character.isLetterOrDigit(value.charAt(i))) {
                nonAlphaNr++;
            }
        }
        return nonAlphaNr >= nonAlpha;
    }
}
