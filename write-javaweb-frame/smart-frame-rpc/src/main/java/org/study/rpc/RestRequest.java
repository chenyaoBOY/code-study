package org.study.rpc;

import lombok.extern.slf4j.Slf4j;
import org.study.smartframe.FrameWorkParser;
import org.study.smartframe.proxy.ProxyParserInterface;

import java.util.ServiceLoader;

/**
 * @author chenyao
 * @date 2021/2/4 14:00
 * @description
 */
@Slf4j
public class RestRequest {

    public static Object request(String url){
        log.info("request url:{}",url);
        return null;
    }

}
