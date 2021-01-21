package org.study.smartframe.proxy.core;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.study.smartframe.proxy.defalut.ControllerAspect;
import org.study.smartframe.test.Hello;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @author chenyao
 * @date 2021/1/20 14:07
 * @description
 */
public class ProxyManage {

    public static <T> T creatProxy(final Class<?> targetClass, List<Proxy> proxyList) {
        return (T) Enhancer.create(targetClass, new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

                return new ProxyChain(targetClass, o, method, methodProxy, objects, proxyList).doProxyChain();
            }
        });
    }

    public static void main(String[] args) {
        Proxy p1 = proxyChain -> {
            System.out.println("before1");
            Object o = proxyChain.doProxyChain();
            System.out.println("after1");
            return o;
        };
        Proxy p2 = proxyChain -> {
            System.out.println("before2");
            Object o = proxyChain.doProxyChain();
            System.out.println("after2");
            return o;
        };
        Hello hello = creatProxy(Hello.class, Arrays.asList(p1,p2));
        Hello hello2 = creatProxy(Hello.class, Arrays.asList(new ControllerAspect()));
        hello2.sayHello();
    }
}
