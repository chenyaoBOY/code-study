package org.study.smartframe.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author chenyao
 * @date 2021/1/19 10:26
 * @description
 */
@Data
@AllArgsConstructor
public class Request {

    /**
     * 请求方式 get post 等
     */
    private String requestMethod;
    /**
     * 请求的路径
     */
    private String requestPath;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Request request = (Request) o;

        return new EqualsBuilder()
                .append(requestMethod, request.requestMethod)
                .append(requestPath, request.requestPath)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(requestMethod)
                .append(requestPath)
                .toHashCode();
    }
}
