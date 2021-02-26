package org.study.entity;

import java.util.ServiceLoader;

/**
 * @author dizhang
 * @date 2021-02-08
 */
public class Client {
    public static void main(String[] args) {
        ServiceLoader<Flyable> serviceLoader = ServiceLoader.load(Flyable.class);
        for (Flyable flyable : serviceLoader) {
            flyable.fly();
        }
    }
}