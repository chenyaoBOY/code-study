package org.study.smartframe.test;

import org.study.smartframe.annotation.Action;
import org.study.smartframe.annotation.Controller;
import org.study.smartframe.annotation.Inject;
import org.study.smartframe.entity.ModelData;
import org.study.smartframe.entity.Param;
import org.study.smartframe.entity.View;

import java.lang.annotation.Inherited;
import java.util.List;

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
    public ModelData get(Param param) {
        return new ModelData(param);
    }

}
