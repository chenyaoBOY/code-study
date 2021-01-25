package org.study.smartframe.proxy;

import org.apache.commons.collections4.CollectionUtils;
import org.study.smartframe.load.BeanParser;
import org.study.smartframe.load.ClassParser;
import org.study.smartframe.proxy.ann.Aspect;
import org.study.smartframe.proxy.core.AbstractAspectProxy;
import org.study.smartframe.proxy.core.Proxy;
import org.study.smartframe.proxy.core.ProxyChain;
import org.study.smartframe.proxy.core.ProxyManage;
import org.study.smartframe.util.ConfigUtil;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @author chenyao
 * @date 2021/1/20 14:51
 * @description
 */
public class ProxyParser {
    /**
     * Aspect注解上的value值 可能会针对同一个注解（例如：Controller） 写多个代理
     * 1 将所有的bean中 带有Aspect注解的类找到 并判断该类是不是Proxy的实现类
     * 2 然后将Aspect中的value值 也就是要增强哪种注解的class值 放入ANNOTATION_CLASS_PROXY_MAP
     * 3 遍历所有bean 判断哪些bean是被ANNOTATION_CLASS_PROXY_MAP的key修饰
     * 4 生成代理 并替换掉原来的bean实例
     */
    private static final Map<Class<? extends Annotation>, List<Proxy>> ANNOTATION_CLASS_PROXY_MAP = new HashMap<>();

    static {
//        proxyByMyself();
        init();
    }

    private static void proxyByMyself() {
        if (ConfigUtil.getAopSwitch().equals("true")) {
            Map<Class<?>, Object> beanMap = BeanParser.getBeanMap();
            for (Map.Entry<Class<?>, Object> entry : beanMap.entrySet()) {
                Class<?> beanClass = entry.getKey();
                if (!beanClass.isAnnotationPresent(Aspect.class)) continue;
                Aspect aspect = beanClass.getAnnotation(Aspect.class);
                Class<? extends Annotation> annClass = aspect.value();
                if (entry.getValue() instanceof AbstractAspectProxy && !annClass.equals(Aspect.class)) {
                    if (ANNOTATION_CLASS_PROXY_MAP.containsKey(annClass)) {
                        ANNOTATION_CLASS_PROXY_MAP.get(annClass).add((Proxy) entry.getValue());
                    } else {
                        List<Proxy> list = new ArrayList<>();
                        list.add((Proxy) entry.getValue());
                        ANNOTATION_CLASS_PROXY_MAP.put(annClass, list);
                    }
                }
            }

            for (Map.Entry<Class<? extends Annotation>, List<Proxy>> entry : ANNOTATION_CLASS_PROXY_MAP.entrySet()) {
                Class<? extends Annotation> annClass = entry.getKey();
                Iterator<Map.Entry<Class<?>, Object>> iterator = beanMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<Class<?>, Object> beanEntry = iterator.next();
                    Class<?> beanClass = beanEntry.getKey();
                    if (beanClass.isAnnotationPresent(annClass)) {
                        beanEntry.setValue(ProxyManage.creatProxy(beanClass, entry.getValue()));
                    }
                }
            }
        }
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
        // 所有的proxy实现类
        Set<Class<?>> proxyClassSet = ClassParser.getClassBySuper(Proxy.class);
        for (Class<?> proxyClass : proxyClassSet) {
            if (!proxyClass.isAnnotationPresent(Aspect.class)) continue;
            Aspect aspect = proxyClass.getAnnotation(Aspect.class);
            map.put(proxyClass, createTargetClassesSet(aspect));
        }
        return map;
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
