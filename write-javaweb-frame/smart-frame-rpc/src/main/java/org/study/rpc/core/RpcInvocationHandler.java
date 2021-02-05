package org.study.rpc.core;


import org.apache.commons.lang3.StringUtils;
import org.study.rpc.RestRequest;
import org.study.rpc.ann.Rest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author chenyao
 * @date 2021/2/3 16:42
 * @description
 */
public class RpcInvocationHandler implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (!method.isAnnotationPresent(Rest.class)) {
            throw new RuntimeException("rpc代理类的方法需要添加 @Rest注解");
        }
        Rest rest = method.getAnnotation(Rest.class);
        String url = rest.url();
        if(StringUtils.isEmpty(url)){
            throw new RuntimeException("@Rest: url is blank");
        }
        return RestRequest.invokeUrl(url,method,rest);
    }
}
