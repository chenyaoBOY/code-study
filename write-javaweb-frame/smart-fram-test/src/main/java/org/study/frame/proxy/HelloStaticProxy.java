package org.study.frame.proxy;

/**
 * @author chenyao
 * @date 2021/1/19 16:18
 * @description
 */
public class HelloStaticProxy implements Hello{

    private Hello hello;

    public HelloStaticProxy() {
        this.hello = new HelloImpl();
    }

    @Override
    public void sayHello() {
        before();
        hello.sayHello();
        after();
    }

    private void after() {
        System.out.println("after hello");
    }

    private void before() {
        System.out.println("before hello");
    }

    public static void main(String[] args) {
        HelloStaticProxy helloProxy = new HelloStaticProxy();
        helloProxy.sayHello();
    }
}
