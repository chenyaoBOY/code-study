package org.study.rpc.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.annotation.Annotation;

/**
 * @author chenyao
 * @date 2021/2/5 14:28
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParameterInfo {

    private Integer index;
    private String paramName;
    private Class<?> paramClass;
    private Annotation[] paramAnns;
    private Object paramVal;

}
