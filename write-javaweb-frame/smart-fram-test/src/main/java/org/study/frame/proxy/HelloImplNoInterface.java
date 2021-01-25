package org.study.frame.proxy;

/**
 * @author chenyao
 * @date 2021/1/19 16:19
 * @description
 */
public class HelloImplNoInterface  {
    @MyAnn
    private Hello helloField;
    public void sayHello() {
        System.out.println("hello");
    }
}
