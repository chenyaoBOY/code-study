package org.study.frame.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author chenyao
 * @date 2021/1/19 16:18
 * @description
 */
public class HelloDynamicProxy implements InvocationHandler {

    private final Hello hello;

    public HelloDynamicProxy(Hello hello) {
        this.hello = hello;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object invoke = method.invoke(hello, args);
        after();
        return invoke;
    }

    public  <T> T getProxy(){
        return (T) Proxy.newProxyInstance(hello.getClass().getClassLoader(),hello.getClass().getInterfaces(), this);
    }

    private void after() {
        System.out.println("after hello");
    }

    private void before() {
        System.out.println("before hello");
    }


    public static void main(String[] args) {
        HelloDynamicProxy helloDynamicProxy = new HelloDynamicProxy(new HelloImpl());
        Hello proxy = helloDynamicProxy.getProxy();
        proxy.sayHello();
    }


}
