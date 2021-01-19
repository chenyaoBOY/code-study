package org.study.smartframe.load;

import lombok.extern.slf4j.Slf4j;
import org.study.smartframe.annotation.Inject;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author chenyao
 * @date 2021/1/19 10:07
 * @description
 */
@Slf4j
public final class IocParser {


    static {
        Map<Class<?>, Object> beanMap = BeanParser.getBeanMap();
        if (beanMap == null || beanMap.size() == 0) throw new RuntimeException("no beans was loaded");
        for (Map.Entry<Class<?>, Object> entry : beanMap.entrySet()) {
            Object bean = entry.getValue();
            Field[] fields = bean.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Inject.class)) {
                    Class<?> type = field.getType();
                    Object injectBean = beanMap.get(type);
                    if (injectBean != null) {
                        field.setAccessible(true);
                        try {
                            field.set(bean, injectBean);
                        } catch (IllegalAccessException e) {
                            log.error("inject exception:", e);
                            throw new RuntimeException(e);
                        }
                    }
                }
            }

        }

    }

}
