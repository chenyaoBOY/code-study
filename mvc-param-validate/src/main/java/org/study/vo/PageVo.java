package org.study.vo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

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
@Data
@Slf4j
public class PageVo {
    @NotNull(message = "分页大小不能为空")
    @Min(value = 10,message = "分页最小为10")
    @Max(value = 100,message ="分页最大不超过100" )
    private Integer pageSize;
    @NotNull(message = "当前页不能为空")
    private Integer pageNum;
    @NotBlank(message = "name 不能为空")
    @Size(min = 10,max=100,message = "名字长度在10-100")
    private String name;

    public static void main(String[] args) {
        PageVo pageVo = new PageVo();
        pageVo.setName("cy");
        pageVo.setPageSize(1);
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

        Validator validator = validatorFactory.getValidator();

        Set<ConstraintViolation<PageVo>> validate = validator.validate(pageVo);
        for (ConstraintViolation<PageVo> constraintViolation : validate) {
            log.info(constraintViolation.getMessage());
        }
    }
}
