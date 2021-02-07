package org.study.rpc.request;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.study.rpc.ann.GetMapping;
import org.study.rpc.ann.PostMapping;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URI;

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
