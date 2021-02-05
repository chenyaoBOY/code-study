package org.study.rpc.request;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.study.rpc.ann.GetMapping;
import org.study.rpc.ann.RequestParam;
import org.study.rpc.util.ReflectUtil;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @author chenyao
 * @date 2021/2/5 14:44
 * @description
 */
@Slf4j
public class GetRpcRequest implements RpcRequestInterface {
    private final Method method;
    private final GetMapping getAnn;
    private final Object[] args;
    private Object executeObj;

    public GetRpcRequest(Method method, GetMapping getAnn, Object[] args, Object executeObj) {
        this.method = method;
        this.getAnn = getAnn;
        this.args = args;
        this.executeObj = executeObj;
    }

    @Override
    public Object doRequest(RequestInfo requestInfo) {
        String result = doGet();
        Class<?> returnType = requestInfo.getMethod().getReturnType();
        if(returnType.equals(String.class)){
            return result;
        }
        return JSON.parseObject(result, returnType);
    }

    private String doGet() {

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(creatUri());

        try {
            HttpResponse httpResponse = client.execute(httpGet);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                InputStream content = httpResponse.getEntity().getContent();
                return IOUtils.toString(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private URI creatUri() {
        String url = getAnn.url();
        if (StringUtils.isEmpty(url)) {
            throw new RuntimeException("url is blank");
        }
        log.info("request url:{}", url);

        List<ParameterInfo> parameterInfos = ReflectUtil.getPramParameterInfos(method, args);
        URIBuilder uriBuilder = new URIBuilder(URI.create(url));
        for (ParameterInfo parameterInfo : parameterInfos) {
            if (parameterInfo.getParamAnns().length == 0) continue;
            for (Annotation paramAnn : parameterInfo.getParamAnns()) {
                if (paramAnn.annotationType().equals(RequestParam.class)) {
                    uriBuilder.addParameter(parameterInfo.getParamName(), JSON.toJSONString(parameterInfo.getParamVal()));
                }
            }
        }
        try {
            return uriBuilder.build();
        } catch (URISyntaxException e) {
            throw new RuntimeException("create URI exception", e);
        }
    }
}
