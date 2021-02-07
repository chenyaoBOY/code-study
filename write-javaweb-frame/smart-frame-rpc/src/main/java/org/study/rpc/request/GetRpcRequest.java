package org.study.rpc.request;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.study.rpc.ann.GetMapping;
import org.study.rpc.ann.RequestParam;
import org.study.rpc.util.ReflectUtil;

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
public class GetRpcRequest extends AbstractHttpClientRpcRequest {

    public GetRpcRequest(Method method, GetMapping getAnn, Object[] args, Object executeObj) {
        super(method, getAnn, args, executeObj);
    }

    @Override
    protected String doRequestRemote() {
        HttpGet httpGet = new HttpGet(creatUri());
        return doSpecifiedRequest(httpGet);
    }

    private URI creatUri() {
        String url = ((GetMapping) paramAnnotation).url();
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
                    break;
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
