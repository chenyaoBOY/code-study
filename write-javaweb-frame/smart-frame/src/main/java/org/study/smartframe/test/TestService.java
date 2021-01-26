package org.study.smartframe.test;

import lombok.extern.slf4j.Slf4j;
import org.study.smartframe.annotation.Inject;
import org.study.smartframe.annotation.Service;
import org.study.smartframe.proxy.ann.Aspect;
import org.study.smartframe.proxy.ann.Transaction;
import org.study.smartframe.proxy.defalut.ControllerAspect;

/**
 * @author chenyao
 * @date 2021/1/19 11:17
 * @description
 */
@Service
@Slf4j
public class TestService {

    @Inject
    private ControllerAspect controllerAspect;

    public void setControllerAspect(ControllerAspect controllerAspect) {
        this.controllerAspect = controllerAspect;
    }

    @Transaction
    public void sayBye(){
        log.info("bye ! bye !");
    }
}
