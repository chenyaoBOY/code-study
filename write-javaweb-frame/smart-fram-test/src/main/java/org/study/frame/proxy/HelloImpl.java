package org.study.frame.proxy;

/**
 * @author chenyao
 * @date 2021/1/19 16:19
 * @description
 */
public class HelloImpl implements Hello {
    @Override
    public void sayHello() {
        System.out.println("hello");
    }
}
