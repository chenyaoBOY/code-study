package org.study.smartframe;

import lombok.extern.slf4j.Slf4j;
import org.study.smartframe.util.ClassUtil;
import org.study.smartframe.util.ReflectUtil;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenyao
 * @date 2021/1/18 17:40
 * @description
 */
@Slf4j
public class BeanContainer {
    /**
     *  被@Controller和@Service注解的bean 都被存储在BeanMap
     */
    private static final Map<Class<?>, Object> BEAN_MAP = new ConcurrentHashMap<>();

    static {
        Set<Class<?>> beanClasses = ClassUtil.getBeanClasses();
        for (Class<?> beanClass : beanClasses) {
            BEAN_MAP.put(beanClass, ReflectUtil.newInstance(beanClass));
        }
        log.info("smart framework had load {} beans", BEAN_MAP.size());
    }

    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    public static <T> T getBean(Class<T> t) {
        if (!BEAN_MAP.containsKey(t)) throw new RuntimeException(t.getName() + " does");
        return (T) BEAN_MAP.get(t);
    }
}
