package org.study.rpc.request;

import org.study.rpc.ann.GetMapping;
import org.study.rpc.ann.PostMapping;
import org.study.rpc.request.entity.RequestInfo;
import org.study.rpc.request.httpclient.GetRpcRequest;
import org.study.rpc.request.httpclient.PostRpcRequest;

import java.lang.reflect.Method;

/**
 * @author chenyao
 * @date 2021/2/5 14:42
 * @description
 */
public interface RpcRequestInterface {

    Object doRequest(RequestInfo requestInfo);

    static RpcRequestInterface getInstance(RequestInfo requestInfo) {
        Method method = requestInfo.getMethod();
        Object[] args = requestInfo.getArgs();
        Object executeObj = requestInfo.getExecuteObj();
        if (method.isAnnotationPresent(GetMapping.class)) {
            return new GetRpcRequest(method,method.getAnnotation(GetMapping.class),args,executeObj);
        } else if (method.isAnnotationPresent(PostMapping.class)) {
            return new PostRpcRequest(method,method.getAnnotation(PostMapping.class),args,executeObj);
        }
        return null;
    }


}
