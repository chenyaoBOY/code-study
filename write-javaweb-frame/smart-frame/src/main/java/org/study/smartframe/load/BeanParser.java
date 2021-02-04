package org.study.smartframe.load;

import lombok.extern.slf4j.Slf4j;
import org.study.smartframe.load.service.FrameInit;
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
public class BeanParser implements FrameInit {
    /**
     * 被@Controller和@Service注解的bean 都被存储在BeanMap
     */
    private static final Map<Class<?>, Object> BEAN_MAP = new ConcurrentHashMap<>();

    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    public static void setBean(Class<?> key, Object value) {
        BEAN_MAP.put(key, value);
    }

    public static <T> T getBean(Class<T> t) {
        if (!BEAN_MAP.containsKey(t)) throw new RuntimeException(t.getName() + " doesnt exist");
        return (T) BEAN_MAP.get(t);
    }
    @Override
    public void init() {
        /**
         * bean的实例化
         */
        Set<Class<?>> beanClasses = ClassParser.getBeanClasses();
        for (Class<?> beanClass : beanClasses) {
            BEAN_MAP.put(beanClass, ReflectUtil.newInstance(beanClass));
            log.debug("{} new instance success", beanClass.getSimpleName());
        }
        log.info("smart framework had load {} beans", BEAN_MAP.size());
    }
}
