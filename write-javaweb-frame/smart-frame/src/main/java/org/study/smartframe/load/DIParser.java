package org.study.smartframe.load;

import lombok.extern.slf4j.Slf4j;
import org.study.smartframe.annotation.Inject;
import org.study.smartframe.util.ReflectUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author chenyao
 * @date 2021/1/19 10:07
 * @description
 */
@Slf4j
public final class DIParser {


    /**
     * 处理依赖注入
     */
    static {
        doDI();
    }

    public static void doDI() {
        Map<Class<?>, Object> beanMap = BeanParser.getBeanMap();
        if (beanMap == null || beanMap.size() == 0) {
            log.info("no beans was loaded");
            return;
        }
        for (Map.Entry<Class<?>, Object> entry : beanMap.entrySet()) {
            Object bean = entry.getValue();
            if (isCglibProxy(bean)) {
                setSuperField(bean);
            } else {
                setField(bean, bean.getClass());
            }
        }
    }

    private static void setSuperField(Object bean) {
        Map<Class<?>, Object> beanMap = BeanParser.getBeanMap();
        Class<?> superclass = bean.getClass().getSuperclass();
        Field[] superFields = superclass.getDeclaredFields();
        try {
            for (Field superField : superFields) {
                if (!superField.isAnnotationPresent(Inject.class)) continue;
                Object injectBean = beanMap.get(superField.getType());
                if (injectBean != null) {
                    Method method = bean.getClass().getMethod(upperFirst(superField.getName()), superField.getType());
                    method.invoke(bean, injectBean);
                    log.info("{} inject {} success", superclass.getSimpleName(), superField.getName());
                }
            }
        } catch (Exception e) {
            log.error("CGlib代理 注入异常", e);
        }
    }

    private static String upperFirst(String s){
        return "set"+s.substring(0,1).toUpperCase()+s.substring(1);
    }

    private static void setField(Object bean, Class<?> clazz) {
        Map<Class<?>, Object> beanMap = BeanParser.getBeanMap();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(Inject.class)) continue;
            Object injectBean = beanMap.get(field.getType());
            if (injectBean != null) {
                ReflectUtil.setField(field, bean, injectBean);
            }
        }
    }

    private static boolean isCglibProxy(Object bean) {
        try {
            bean.getClass().getDeclaredField("CGLIB$BOUND");
        } catch (NoSuchFieldException e) {
            return false;
        }
        return true;
    }

}
