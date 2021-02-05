package org.study.rpc;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.study.rpc.ann.GetMapping;
import org.study.rpc.ann.PostMapping;
import org.study.rpc.request.RequestInfo;
import org.study.rpc.request.RpcRequestInterface;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URI;

/**
 * @author chenyao
 * @date 2021/2/4 14:00
 * @description
 */
@Slf4j
public class RestRequest {

    public static Object invokeUrl(RequestInfo requestInfo) {
        RpcRequestInterface instance = RpcRequestInterface.getInstance(requestInfo);
        if (instance == null) {
            throw new RuntimeException("获取rpc实例异常！" + requestInfo.toString());
        }
        return instance.doRequest(requestInfo);
    }

}
