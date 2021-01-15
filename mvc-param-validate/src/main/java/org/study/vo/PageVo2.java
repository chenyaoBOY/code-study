package org.study.vo;

import org.study.annotation.PageValue;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.*;
import java.util.Set;

/**
 * @author chenyao
 * @date 2020/12/15 10:18
 * @description
 */

public class PageVo2 {
    @PageValue
    private Integer pageSize;
    private Integer pageNum;
    private String name;
    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        PageVo2 pageVo = new PageVo2();
        pageVo.setName("cy");
        pageVo.setPageSize(1);
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

        Validator validator = validatorFactory.getValidator();

        Set<ConstraintViolation<PageVo2>> validate = validator.validate(pageVo);
        for (ConstraintViolation<PageVo2> constraintViolation : validate) {
            System.out.println(constraintViolation.getMessage());
        }
    }
}
