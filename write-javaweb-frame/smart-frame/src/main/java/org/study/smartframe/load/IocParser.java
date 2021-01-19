package org.study.smartframe.load;

import lombok.extern.slf4j.Slf4j;
import org.study.smartframe.annotation.Inject;
import org.study.smartframe.util.ReflectUtil;

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
        if (beanMap == null || beanMap.size() == 0) {
            log.info("no beans was loaded");
        }else{
            int total=0;
            for (Map.Entry<Class<?>, Object> entry : beanMap.entrySet()) {
                Object bean = entry.getValue();
                Field[] fields = bean.getClass().getDeclaredFields();
                for (Field field : fields) {
                    if (!field.isAnnotationPresent(Inject.class)) continue;
                    Object injectBean = beanMap.get(field.getType());
                    if (injectBean != null) {
                        ReflectUtil.setField(field, bean, injectBean);
                        total++;
                    }
                }
            }
            log.info("smart framework inject {} count",total);
        }
    }

}
