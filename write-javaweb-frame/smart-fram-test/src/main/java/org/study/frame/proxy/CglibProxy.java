package org.study.frame.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author chenyao
 * @date 2021/1/19 16:41
 * @description
 */
public class CglibProxy implements MethodInterceptor {

    private static final CglibProxy CGLIB_PROXY = new CglibProxy();

    private CglibProxy() {
    }

    public static CglibProxy getCglibProxy(){
        return CGLIB_PROXY;
    }

    @Override
    public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        before();
        Object result = methodProxy.invokeSuper(object, args);
        after();
        return result;
    }

    private void after() {
        System.out.println("after hello");
    }

    private void before() {
        System.out.println("before hello");
    }

    public <T> T getProxy(Class<T> tClass) {
        return (T) Enhancer.create(tClass, this);
    }

    public static void main(String[] args) {
        HelloImplNoInterface hello = CGLIB_PROXY.getProxy(HelloImplNoInterface.class);
        hello.sayHello();

        /**
         * 直接使用接口的话 会报错 提示没有该方法
         * java.lang.NoSuchMethodError: java.lang.Object.sayHello()V
         */
        Hello proxy1 = CGLIB_PROXY.getProxy(Hello.class);
        proxy1.sayHello();
    }

}
