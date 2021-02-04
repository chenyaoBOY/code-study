package org.study.rpc.core;

import lombok.extern.slf4j.Slf4j;
import org.study.rpc.ann.RestRpc;
import org.study.smartframe.load.ClassParser;
import org.study.smartframe.proxy.ProxyParserInterface;

import java.lang.reflect.Proxy;
import java.util.*;

/**
 * @author chenyao
 * @date 2021/2/4 10:56
 * @description
 */
@Slf4j
public class RpcProxyParser implements ProxyParserInterface {

    @Override
    public Map<Class<?>/*proxyClass*/, Set<Class<?>>/*targetClass*/> createProxyMap() {
        Map<Class<?>, Set<Class<?>>> map = new HashMap<>();
        Set<Class<?>> restClasses = ClassParser.getAnnotationClasses(RestRpc.class);
        for (Class<?> clazz : restClasses) {
            Set<Class<?>> set = new HashSet<>();
            set.add(clazz);
            map.put(clazz,set);
        }
        return map;
    }

    @Override
    public Object createProxy(Class<?> targetClass, List<Object> proxyList) {
        Object o = null;
        try {
            /**
             * 此处RpcInvocationHandler 可以共用一个  也可以为每个接口创建一个新的
             */
            o = Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{targetClass}, new RpcInvocationHandler());
        } catch (Exception e) {
            log.error("动态代理出现异常！",e);
            throw new RuntimeException(e);
        }
        return o;
    }
}
