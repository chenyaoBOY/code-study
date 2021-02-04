package org.study.smartframe;

import org.study.smartframe.entity.Param;
import org.study.smartframe.load.*;
import org.study.smartframe.load.service.ClassParserInterface;
import org.study.smartframe.load.service.FrameInit;
import org.study.smartframe.proxy.DefaultProxyParser;
import org.study.smartframe.proxy.ProxyParserInterface;
import org.study.smartframe.proxy.core.ProxyManage;
import org.study.smartframe.proxy.defalut.ControllerAspect;
import org.study.smartframe.test.TestController;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author chenyao
 * @date 2021/1/19 11:08
 * @description
 */
public class FrameWorkParser {

    private final static List<ProxyParserInterface> proxyParserInits = new ArrayList<>();
    private final static List<ClassParserInterface> classParser = new ArrayList<>();

    public static void initSmartFrameWork(){
        classParser.add(new ClassParser());
        loadClassParsers();
        proxyParserInits.add(new DefaultProxyParser());
        loadSpiProxyParser();
        /**
         * 顺序一定不能错
         * 严格的加载顺序
         */
        List<FrameInit> list = new ArrayList<>();
        list.addAll(classParser);
        list.add(new BeanParser());
        list.addAll(proxyParserInits);
        list.add(new DIParser());
        list.add(new ControllerParser());
        for (FrameInit init : list) {
            init.init();
        }
    }

    public static void loadClassParsers(){
        ServiceLoader<ClassParserInterface> result = ServiceLoader.load(ClassParserInterface.class);
        for (ClassParserInterface bean : result) {
            classParser.add(bean);
        }
    }

    public static void loadSpiProxyParser() {
        ServiceLoader<ProxyParserInterface> result = ServiceLoader.load(ProxyParserInterface.class);
        for (ProxyParserInterface parserInit : result) {
            proxyParserInits.add(parserInit);
        }
    }
    /**
     * 启动前 注释掉 DispatcherServlet.java
     * @param args
     */
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
