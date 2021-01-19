package org.study.smartframe.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.study.smartframe.BeanContainer;
import org.study.smartframe.annotation.Controller;
import org.study.smartframe.annotation.Inject;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author chenyao
 * @date 2021/1/19 10:07
 * @description
 */
@Slf4j
public final class IocUtil {


    static {
        Map<Class<?>, Object> beanMap = BeanContainer.getBeanMap();
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
