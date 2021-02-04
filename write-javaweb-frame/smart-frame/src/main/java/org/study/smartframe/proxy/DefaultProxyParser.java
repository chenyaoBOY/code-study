package org.study.smartframe.proxy;

import org.study.smartframe.annotation.Service;
import org.study.smartframe.load.BeanParser;
import org.study.smartframe.load.ClassParser;
import org.study.smartframe.proxy.ann.Aspect;
import org.study.smartframe.proxy.core.*;
import org.study.smartframe.proxy.defalut.TransactionProxy;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @author chenyao
 * @date 2021/1/20 14:51
 * @description
 */
public class DefaultProxyParser implements ProxyParserInterface {

    /**
     * Aspect注解 获取需要被代理的class
     *
     * @param aspect
     * @return
     */
    private Set<Class<?>> createTargetClassesSet(Aspect aspect) {
        Set<Class<?>> res = new HashSet<>();
        Class<? extends Annotation> targetAnn = aspect.value();
        if (targetAnn != null && !targetAnn.equals(Aspect.class)) {
            res.addAll(ClassParser.getAnnotationClasses(targetAnn));
        }
        return res;
    }

    /**
     * 获取proxyCLass  和  targetClass之间的映射关系
     *
     * @return
     */
    public Map<Class<?>/*proxyClass*/, Set<Class<?>>/*targetClass*/> createProxyMap() {
        Map<Class<?>, Set<Class<?>>> map = new HashMap<>();
        addAspectProxy(map);
        addTransactionProxy(map);
        return map;
    }
    private void addTransactionProxy(Map<Class<?>, Set<Class<?>>> map) {
        Set<Class<?>> classSet = ClassParser.getAnnotationClasses(Service.class);
        map.put(TransactionProxy.class, classSet);
    }

    private void addAspectProxy(Map<Class<?>, Set<Class<?>>> map) {
        // 所有的proxy实现类
        Set<Class<?>> proxyClassSet = ClassParser.getClassBySuper(Proxy.class);
        for (Class<?> proxyClass : proxyClassSet) {
            if (!proxyClass.isAnnotationPresent(Aspect.class)) continue;
            Aspect aspect = proxyClass.getAnnotation(Aspect.class);
            map.put(proxyClass, createTargetClassesSet(aspect));
        }
    }

    @Override
    public Object createProxy(Class<?> targetClass, List<Object> proxyList) {
        List<Proxy> list = new ArrayList<>();
        for (Object o : proxyList) {
            list.add((Proxy) o);
        }
        return ProxyManage.creatProxy(targetClass, list);
    }
}
