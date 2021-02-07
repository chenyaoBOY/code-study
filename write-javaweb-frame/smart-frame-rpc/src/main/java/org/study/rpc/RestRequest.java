package org.study.rpc;

import lombok.extern.slf4j.Slf4j;
import org.study.rpc.request.entity.RequestInfo;
import org.study.rpc.request.RpcRequestInterface;

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
