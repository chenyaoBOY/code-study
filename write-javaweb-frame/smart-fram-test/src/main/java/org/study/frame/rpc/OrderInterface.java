package org.study.frame.rpc;

import org.study.rpc.ann.*;
import org.study.smartframe.entity.Param;

/**
 * @author chenyao
 * @date 2021/2/3 16:54
 * @description
 */
@RestRpc
public interface OrderInterface {

    @GetMapping(url = "http://localhost:8082/getCustomerList")
    String sayHello();

    @GetMapping(url = "http://localhost:8082/get")
    String sayHello(@RequestParam Param param);

    @PostMapping(url = "http://localhost:8082/postCustomerList")
    String sayHello2(@RequestBody Param param);

    @GetMapping(url = "http://localhost:8080/debugParam")
    String requestSpringboot(@RequestParam String name, @RequestParam Integer age);

    /**
     * 用一个@RequestBody就可以了 多的不会解析
     * @param user
     * @param age
     * @return
     */
    @PostMapping(url = "http://localhost:8080/smart/post")
    UserJapan smartPost(@RequestBody UserJapan user, @RequestBody Integer age);

}
