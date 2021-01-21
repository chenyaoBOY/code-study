package org.study.smartframe.proxy.core;

import lombok.Data;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author chenyao
 * @date 2021/1/20 13:44
 * @description
 */
@Data
public class ProxyChain {

    private Class<?> targetClass;
    private Object targetObject;
    private Method targetMethod;

    private MethodProxy methodProxy;
    private Object[] methodParams;

    private List<Proxy> proxyList;
    private int proxyIndex;

    public ProxyChain(Class<?> targetClass, Object targetObject, Method targetMethod,
                      MethodProxy methodProxy, Object[] methodParams, List<Proxy> proxyList) {
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.methodProxy = methodProxy;
        this.methodParams = methodParams;
        this.proxyList = proxyList;
    }

    /**
     * 通过调用该方法 执行所有的代理Proxy的实现 proxyList
     * 在Proxy#doProxy(ProxyChain)中
     * 最后还要调用一下 ProxyChain#doProxyChain方法 方能使所有的Proxy执行完 最后再执行本体
     * @return
     * @throws Throwable
     */
    public Object doProxyChain() throws Throwable {
        if (proxyIndex < proxyList.size()) {
            return proxyList.get(proxyIndex++).doProxy(this);
        } else {
            return methodProxy.invokeSuper(targetObject, methodParams);
        }
    }
}
