package org.study.smartframe.test;

import org.study.smartframe.FramWorkParser;
import org.study.smartframe.entity.Param;
import org.study.smartframe.load.BeanParser;

import java.util.HashMap;

/**
 * @author chenyao
 * @date 2021/1/25 15:52
 * @description
 */
public class TestFrame {

    public static void main(String[] args) {
        FramWorkParser.initSmartFrameWork();
        TestController bean = BeanParser.getBean(TestController.class);
        bean.sayBye(new Param(new HashMap<>()));
    }
}
