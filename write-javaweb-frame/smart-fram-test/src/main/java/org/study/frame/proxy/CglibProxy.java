package org.study.frame.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.springframework.core.annotation.AnnotationUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
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

    /**
     * 被CGlib代理的类 无法直接获取字段/方法上的注解
     * 需要通过父类获取
     * spring提供的AnnotationUtils工具类可以获取到
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        HelloImplNoInterface hello = CGLIB_PROXY.getProxy(HelloImplNoInterface.class);
        hello.sayHello();
        Field[] fields = hello.getClass().getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getName());
        }
        Method[] methods = hello.getClass().getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
        }
        boolean helloField = hello.getClass().getSuperclass().getDeclaredField("helloField").isAnnotationPresent(Resource.class);
        Method sayHello = hello.getClass().getMethod("sayHello");
        MyAnn ann = AnnotationUtils.findAnnotation(sayHello, MyAnn.class);
        Method sayHello2 = hello.getClass().getMethod("sayHello2");
        MyAnn ann2 = AnnotationUtils.findAnnotation(sayHello2, MyAnn.class);
        /**
         * 直接使用接口的话 会报错 提示没有该方法
         * java.lang.NoSuchMethodError: java.lang.Object.sayHello()V
         */
        Hello proxy1 = CGLIB_PROXY.getProxy(Hello.class);
        proxy1.sayHello();
    }

}
