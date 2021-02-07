package org.study.rpc.request;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.study.rpc.request.entity.RequestInfo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author chenyao
 * @date 2021/2/7 11:03
 * @description
 */
@Slf4j
public abstract class AbstractRpcRequest implements RpcRequestInterface {
    protected final Method method;
    protected final Annotation paramAnnotation;
    protected final Object[] args;
    protected Object executeObj;

    public AbstractRpcRequest(Method method, Annotation paramAnnotation, Object[] args, Object executeObj) {
        this.method = method;
        this.paramAnnotation = paramAnnotation;
        this.args = args;
        this.executeObj = executeObj;
    }

    @Override
    public Object doRequest(RequestInfo requestInfo) {
        String result = doRequestRemote();
        Class<?> returnType = requestInfo.getMethod().getReturnType();
        if (returnType.equals(String.class)) {
            return result;
        }
        return JSON.parseObject(result, returnType);
    }

    protected abstract String doRequestRemote();

}
