package org.study.smartframe.load;

import lombok.extern.slf4j.Slf4j;
import org.study.smartframe.annotation.Controller;
import org.study.smartframe.annotation.Service;
import org.study.smartframe.load.service.ClassParserInterface;
import org.study.smartframe.load.service.FrameInit;
import org.study.smartframe.proxy.ann.Aspect;
import org.study.smartframe.util.ClassUtil;
import org.study.smartframe.util.ConfigUtil;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author chenyao
 * @date 2021/1/19 10:48
 * @description
 */
@Slf4j
public class ClassParser implements ClassParserInterface {

    private static  Set<Class<?>> CLASS_SET;


    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    public static Set<Class<?>> getServiceClasses() {
        return CLASS_SET.stream()
                .filter(c -> c.isAnnotationPresent(Service.class))
                .collect(Collectors.toSet());
    }

    public static Set<Class<?>> getAnnotationClasses(Class<? extends Annotation> clazz) {
        return CLASS_SET.stream()
                .filter(c -> c.isAnnotationPresent(clazz))
                .collect(Collectors.toSet());
    }

    public static Set<Class<?>> getBeanClasses() {
        return CLASS_SET.stream()
                .filter(c -> c.isAnnotationPresent(Controller.class) || c.isAnnotationPresent(Service.class) || isAnnotationAspect(c))
                .collect(Collectors.toSet());
    }

    private static boolean isAnnotationAspect(Class<?> c) {
        return ConfigUtil.getAopSwitch().equals("true") && c.isAnnotationPresent(Aspect.class);
    }

    public static Set<Class<?>> getClassBySuper(Class<?> superClass){
        Set<Class<?>> res = new HashSet<>();

        for (Class<?> c : CLASS_SET) {
            if(superClass.isAssignableFrom(c) && !superClass.equals(c)){
                res.add(c);
            }
        }
        return res;
    }

    @Override
    public void init() {
        /**
         * 加载指定目录下的class文件
         */
        String appBasePackage = ConfigUtil.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassFromPackage(appBasePackage);
        log.info("smart framework had load {} classes successful", CLASS_SET.size());
    }
}
