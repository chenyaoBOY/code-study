package org.study.frame.proxy;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import javax.annotation.Resource;
import java.lang.reflect.Field;

/**
 * @author chenyao
 * @date 2021/1/19 16:19
 * @description
 */
@Slf4j
public class HelloImplNoInterface  {
    @Resource
    private Hello helloField;
    @MyAnn("myannValue")
    public void sayHello() {
        System.out.println("hello");
    }
    public void sayHello2() {
        System.out.println("hello");
    }

    public Hello getHelloField() {
        return helloField;
    }

    public void setHelloField(Hello helloField) {
        this.helloField = helloField;
    }

}
