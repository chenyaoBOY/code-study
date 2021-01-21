package org.study.smartframe.proxy.core;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chenyao
 * @date 2021/1/20 14:26
 * @description
 */
@Slf4j
public abstract class AbstractAspectProxy implements Proxy {

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        begin();
        Object result;
        try {
            if (match(proxyChain)) {
                before(proxyChain);
                result = proxyChain.doProxyChain();
                after(proxyChain,result);
            } else {
                result = proxyChain.doProxyChain();
            }
        } catch (Exception e) {
            log.error("doproxy failure",e);
            error(proxyChain,e);
            throw e;
        } finally {
            end(proxyChain);
        }
        return result;
    }

    protected void begin() {


    }

    protected void end(ProxyChain proxyChain) {

    }

    protected void error(ProxyChain proxyChain, Exception e) {

    }

    protected void after(ProxyChain proxyChain, Object result) {

    }

    protected void before(ProxyChain proxyChain) {

    }

    protected boolean match(ProxyChain proxyChain) {
        return true;
    }
}
