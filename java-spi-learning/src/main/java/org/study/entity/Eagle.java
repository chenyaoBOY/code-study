package org.study.entity;

/**
 * @author dizhang
 * @date 2021-02-08
 */
public class Eagle implements Flyable {

    @Override
    public void fly() {
        System.out.println("eagle fly");
    }
}