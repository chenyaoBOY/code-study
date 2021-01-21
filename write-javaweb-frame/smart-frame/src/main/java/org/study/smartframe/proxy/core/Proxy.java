package org.study.smartframe.proxy.core;

/**
 * @author chenyao
 * @date 2021/1/20 13:43
 * @description
 *  代理增强接口
 */
public interface Proxy {

    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
