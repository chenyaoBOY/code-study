package org.study.rpc.request.entity;

import lombok.Data;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author chenyao
 * @date 2021/2/5 14:42
 * @description
 */
@Data
public class RequestInfo {

    private Method method;
    private Object[] args;
    private Object executeObj;

    public RequestInfo(Method method, Object[] args, Object executeObj) {
        this.method = method;
        this.args = args;
        this.executeObj = executeObj;
    }

    @Override
    public String toString() {
        return "RequestInfo:" + method.getName();
    }
}
