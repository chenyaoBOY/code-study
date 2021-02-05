package org.study.rpc.core;


import org.apache.commons.lang3.StringUtils;
import org.study.rpc.RestRequest;
import org.study.rpc.ann.GetMapping;
import org.study.rpc.ann.PostMapping;
import org.study.rpc.request.RequestInfo;

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
        if (!method.isAnnotationPresent(GetMapping.class) && !method.isAnnotationPresent(PostMapping.class)) {
            throw new RuntimeException("rpc代理类的方法需要添加 GetMapping或者PostMapping注解");
        }
        return RestRequest.invokeUrl(new RequestInfo(method,args,proxy));
    }
}
