package org.study.vo;

import lombok.Data;
import org.study.annotation.EnumMatch;

import javax.validation.constraints.NotBlank;

/**
 * @author chenyao
 * @date 2021/1/4 14:31
 * @description
 */
@Data
public class RefundParam {

    @EnumMatch(clazz = RefundPathEnum.class, message = "退款路径不合法")
    private Integer refundPath;
}
