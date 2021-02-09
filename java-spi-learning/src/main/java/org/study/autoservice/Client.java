package org.study.autoservice;

import java.util.ServiceLoader;

/**
 * @author dizhang
 * @date 2021-02-09
 */
public class Client {
    public static void main(String[] args) {
        ServiceLoader<UserService> serviceLoader = ServiceLoader.load(UserService.class);
        for (UserService userService : serviceLoader) {
            System.out.println(userService.userName());
        }
    }
}