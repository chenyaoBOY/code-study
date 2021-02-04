package org.study.smartframe.test;

import org.study.smartframe.FrameWorkParser;
import org.study.smartframe.entity.Param;
import org.study.smartframe.load.BeanParser;

import java.util.HashMap;

/**
 * @author chenyao
 * @date 2021/1/25 15:52
 * @description
 */
public class TestFrame {

    static {
        FrameWorkParser.initSmartFrameWork();
    }

    /**
     * 测试前  DispatcherServlet 注释掉
     * @param args
     */
    public static void main(String[] args) {
//       testDiWork();
        testTransaction();
    }

    private static void testTransaction() {
        TestService bean = BeanParser.getBean(TestService.class);
        bean.sayBye();

    }

    public static void testDiWork() {
        TestController bean = BeanParser.getBean(TestController.class);
        bean.sayBye(new Param(new HashMap<>()));
    }
}
