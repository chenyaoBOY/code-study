package org.study.smartframe;

import org.study.smartframe.entity.Param;
import org.study.smartframe.load.BeanParser;
import org.study.smartframe.load.ClassParser;
import org.study.smartframe.load.ControllerParser;
import org.study.smartframe.load.DIParser;
import org.study.smartframe.proxy.ProxyParser;
import org.study.smartframe.proxy.core.ProxyManage;
import org.study.smartframe.proxy.defalut.ControllerAspect;
import org.study.smartframe.test.TestController;
import org.study.smartframe.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenyao
 * @date 2021/1/19 11:08
 * @description
 */
public class FramWorkParser {

    public static void initSmartFrameWork(){
        /**
         * 顺序一定不能错
         * 严格的加载顺序
         */
        Class<?>[] classes = {
                ClassParser.class,
                BeanParser.class,
                ProxyParser.class,
                DIParser.class,
                ControllerParser.class
        };
        for (Class<?> c : classes) {
            ClassUtil.loadClass(c.getName(),true);
        }
    }

    public static void main(String[] args) {
        initSmartFrameWork();
        Map<Class<?>, Object> beanMap = BeanParser.getBeanMap();
        TestController o = (TestController) beanMap.get(TestController.class);
        Field[] fields = o.getClass().getDeclaredFields();
        for (Field field : fields) {
                field.setAccessible(true);
                Annotation[] annotations = field.getAnnotations();
        }
        o.get(new Param(new HashMap<>()));


        TestController o1 = ProxyManage.creatProxy(TestController.class, Arrays.asList(new ControllerAspect()));
    }
}
