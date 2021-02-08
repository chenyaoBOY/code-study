package org.study.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dizhang
 * @date 2021-02-07
 */
@RestController
@RequestMapping
public class HelloController {

    @RequestMapping("/hello")
    public String home() {
        return "Hello World from a Spring boot Java application";
    }
}