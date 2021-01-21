package org.study.frame;

import lombok.extern.slf4j.Slf4j;
import org.study.smartframe.annotation.Controller;
import org.study.smartframe.proxy.ann.Aspect;
import org.study.smartframe.proxy.core.AbstractAspectProxy;
import org.study.smartframe.proxy.core.ProxyChain;

/**
 * @author chenyao
 * @date 2021/1/20 16:01
 * @description
 */
@Aspect(Controller.class)
@Slf4j
public class ControllerProxy extends AbstractAspectProxy {

    @Override
    protected void begin() {
        log.debug("aop切面开始执行");
    }

    @Override
    protected void end(ProxyChain proxyChain) {
        log.debug("aop切面执行结束");
    }
}
