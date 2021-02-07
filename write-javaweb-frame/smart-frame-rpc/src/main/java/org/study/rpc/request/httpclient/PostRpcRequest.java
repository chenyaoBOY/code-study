package org.study.rpc.request.httpclient;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.study.rpc.ann.PostMapping;
import org.study.rpc.ann.RequestBody;
import org.study.rpc.request.entity.ParameterInfo;
import org.study.rpc.util.ReflectUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URI;

/**
 * @author chenyao
 * @date 2021/2/5 14:44
 * @description
 */
@Slf4j
public class PostRpcRequest extends AbstractHttpClientRpcRequest {
    public PostRpcRequest(Method method, PostMapping getAnn, Object[] args, Object executeObj) {
        super(method, getAnn, args, executeObj);
    }

    @Override
    protected String doRequestRemote() {
        HttpPost httpPost = new HttpPost(creatUri());
        httpPost.setEntity(getEntityBody());
        httpPost.setHeader("Content-Type","application/json");
        return doSpecifiedRequest(httpPost);
    }

    private StringEntity getEntityBody() {

        for (ParameterInfo param : ReflectUtil.getPramParameterInfos(method, args)) {
            if (param.getParamAnns().length == 0) continue;
            for (Annotation paramAnn : param.getParamAnns()) {
                if (paramAnn.annotationType().equals(RequestBody.class)) {
                    return new StringEntity(JSON.toJSONString(param.getParamVal()),"utf-8");
                }
            }
        }
        return null;
    }

    private URI creatUri() {
        String url = ((PostMapping) paramAnnotation).url();
        if (StringUtils.isEmpty(url)) {
            throw new RuntimeException("url is blank");
        }
        log.info("request url:{}", url);
        return URI.create(url);
    }
}
