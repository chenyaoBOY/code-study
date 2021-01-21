package org.study.frame.proxy;

import lombok.Data;

/**
 * @author chenyao
 * @date 2021/1/19 16:19
 * @description
 */
@Data
public class HelloImpl implements Hello {
    public HelloImpl() {
    }

    public HelloImpl(String name) {
        this.name = name;
    }

    private String name;
    @Override
    public void sayHello() {
        System.out.println("hello");
    }
}
