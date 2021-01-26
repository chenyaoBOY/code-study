package org.study.smartframe.proxy;

import org.study.smartframe.annotation.Service;
import org.study.smartframe.load.BeanParser;
import org.study.smartframe.load.ClassParser;
import org.study.smartframe.proxy.ann.Aspect;
import org.study.smartframe.proxy.core.*;
import org.study.smartframe.proxy.defalut.TransactionProxy;
import org.study.smartframe.util.ConfigUtil;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @author chenyao
 * @date 2021/1/20 14:51
 * @description
 */
public class ProxyParser {

    static {
        /**
         * 1 先找到 代理类（Proxy的实现类） 的class 与 目标class（被增强的类） 的关系  Map<Class<?>/proxyClass/, Set<Class<?>>/targetClass/>
         *      1.1 比如Aspect注解增强，先获取所有的Proxy实现类 即代理类 过滤带有Aspect注解的 Set<Class>集合
         *      1.2 再根据Aspect注解上的value值 找到目标class
         * 2. 由于目标类可能会被多种 代理增强  所以要反转一下1中的map映射 找出 targetClass 和ProxyCLass之间的映射关系
         * 3. 根据第二部的结果 就可以直接通过CGlib工具创建 targetClass的代理类
         */
        init();
    }
    /**
     * Aspect注解 获取需要被代理的class
     *
     * @param aspect
     * @return
     */
    private static Set<Class<?>> createTargetClassesSet(Aspect aspect) {
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
    private static Map<Class<?>/*proxyClass*/, Set<Class<?>>/*targetClass*/> createProxyMap() {
        Map<Class<?>, Set<Class<?>>> map = new HashMap<>();
        addAspectProxy(map);
        addTransactionProxy(map);
        return map;
    }

    private static void addTransactionProxy(Map<Class<?>, Set<Class<?>>> map) {
        Set<Class<?>> classSet = ClassParser.getAnnotationClasses(Service.class);
        map.put(TransactionProxy.class,classSet);
    }

    private static void addAspectProxy(Map<Class<?>, Set<Class<?>>> map) {
        // 所有的proxy实现类
        Set<Class<?>> proxyClassSet = ClassParser.getClassBySuper(Proxy.class);
        for (Class<?> proxyClass : proxyClassSet) {
            if (!proxyClass.isAnnotationPresent(Aspect.class)) continue;
            Aspect aspect = proxyClass.getAnnotation(Aspect.class);
            map.put(proxyClass, createTargetClassesSet(aspect));
        }
    }

    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>/*proxyClass*/, Set<Class<?>>/*targetClass*/> proxyMap) throws IllegalAccessException, InstantiationException {
        Map<Class<?>/*targetClass*/, List<Proxy>/*proxyClass*/> res = new HashMap<>();
        for (Map.Entry<Class<?>, Set<Class<?>>> entry : proxyMap.entrySet()) {

            Set<Class<?>> targetClasses = entry.getValue();
            for (Class<?> targetClass : targetClasses) {
                Proxy proxy = (Proxy) entry.getKey().newInstance();
                if (res.containsKey(targetClass)) {
                    res.get(targetClass).add(proxy);
                } else {
                    List<Proxy> list = new ArrayList<>();
                    list.add(proxy);
                    res.put(targetClass, list);
                }
            }
        }
        return res;
    }

    private static void init()  {
        try {
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
            for (Map.Entry<Class<?>, List<Proxy>> entry : targetMap.entrySet()) {
                Object proxy = ProxyManage.creatProxy(entry.getKey(), entry.getValue());
                BeanParser.setBean(entry.getKey(),proxy);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
