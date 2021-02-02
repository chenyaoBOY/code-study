package org.study.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.study.core.LogAnn;

/**
 * @author chenyao
 * @date 2021/2/1 15:44
 * @description
 */
@RestController
@Slf4j
public class LogController {

    @GetMapping("/log")
    @LogAnn(logTag = "oms",bizID = "#param.getOrderSn()")
    public String log(@RequestBody Param param){
        log.info("oms business execute");
        return "ok";
    }

}
