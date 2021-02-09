package org.study.entity;

/**
 * @author dizhang
 * @date 2021-02-08
 */
public class Dove implements Flyable {

    @Override
    public void fly() {
        System.out.println("dove fly");
    }
}
