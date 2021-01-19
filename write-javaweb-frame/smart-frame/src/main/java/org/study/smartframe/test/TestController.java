package org.study.smartframe.test;

import org.study.smartframe.annotation.Action;
import org.study.smartframe.annotation.Controller;
import org.study.smartframe.annotation.Inject;

/**
 * @author chenyao
 * @date 2021/1/19 11:17
 * @description
 */
@Controller
public class TestController {

    @Inject
    private TestService testService;

    @Action("get:/get")
    public void get(){

    }
}
