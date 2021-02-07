package org.study.rpc.request;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.study.rpc.ann.GetMapping;

import java.io.IOException;
import java.io.InputStream;
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


    protected String doSpecifiedRequest(HttpUriRequest request) {
        HttpResponse httpResponse = doExecute(request);
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            return getIsString(httpResponse, request);
        }
        log.error("Response code:{}", statusCode);
        throw new RuntimeException("doSpecifiedRequest failed: status code is not 200");
    }

    private HttpResponse doExecute(HttpUriRequest request){
        HttpClient client = HttpClientBuilder.create().build();
        try {
            return client.execute(request);
        } catch (IOException e) {
            log.error("request remote url:{} execute failed", request.getURI().toString());
            throw new RuntimeException("doSpecifiedRequest failed:IOException", e);
        }
    }

    private String getIsString(HttpResponse httpResponse, HttpUriRequest request) {
        try {
            InputStream content = httpResponse.getEntity().getContent();
            return IOUtils.toString(content,"utf-8");
        } catch (IOException e) {
            log.error("request remote url:{} getEntity failed", request.getURI().toString());
            throw new RuntimeException("doSpecifiedRequest failed:IOException", e);
        }
    }
}
