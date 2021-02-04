package org.study.frame;

import lombok.extern.slf4j.Slf4j;
import org.study.smartframe.annotation.Controller;
import org.study.smartframe.proxy.ann.Aspect;
import org.study.smartframe.proxy.core.AbstractAspectProxy;
import org.study.smartframe.proxy.core.ProxyChain;
import org.study.smartframe.proxy.defalut.ControllerAspect;

/**
 * @author chenyao
 * @date 2021/1/20 16:01
 * @description
 */
@Aspect(Controller.class)
@Slf4j
public class ControllerProxy extends ControllerAspect {

}
