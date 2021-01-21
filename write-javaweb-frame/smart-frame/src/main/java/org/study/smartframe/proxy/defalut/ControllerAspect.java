package org.study.smartframe.proxy.defalut;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.study.smartframe.annotation.Controller;
import org.study.smartframe.proxy.core.AbstractAspectProxy;
import org.study.smartframe.proxy.ann.Aspect;
import org.study.smartframe.proxy.core.ProxyChain;

/**
 * @author chenyao
 * @date 2021/1/20 14:35
 * @description
 */
@Slf4j
@Aspect(Controller.class)
public class ControllerAspect extends AbstractAspectProxy {

    private static ThreadLocal<Long> T_TIME = new ThreadLocal<>();

    @Override
    protected void before(ProxyChain proxyChain) {
        long begin = System.currentTimeMillis();
        T_TIME.set(begin);
        log.debug("invoke:{}#{} param:{} time:{}", proxyChain.getTargetClass().getSimpleName()
                , proxyChain.getTargetMethod().getName()
                , JSON.toJSONString(proxyChain.getMethodParams())
                , begin);
    }

    @Override
    protected void after(ProxyChain proxyChain, Object result) {
        try {
            Long begin = T_TIME.get();
            long after = System.currentTimeMillis();
            log.debug("invoke:{}#{} result:{} time:{} consumed:{}", proxyChain.getTargetClass().getSimpleName()
                    , proxyChain.getTargetMethod().getName()
                    , JSON.toJSONString(result)
                    , after
                    , after - begin);
        } finally {
            T_TIME.remove();
        }


    }
}
