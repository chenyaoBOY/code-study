package org.study.smartframe.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author chenyao
 * @date 2021/1/18 17:33
 * @description
 */
public class ReflectUtil {

    private static final Logger log = LoggerFactory.getLogger(ReflectUtil.class);

    public static Object newInstance(Class<?> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            log.error("class {} new instance failure", clazz.toString());
            throw new RuntimeException(e);
        }
    }

    public static Object invokeMethod(Object obj, Method method, Object... params) {
        try {
            return method.invoke(obj, params);
        } catch (Exception e) {
            log.error("invoke method {} is failure", method.getName());
            throw new RuntimeException(e);
        }
    }

    public static void setField(Field field, Object obj, Object value) {
        try {
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception e) {
            log.error("setField is failure", e);
            throw new RuntimeException(e);
        }
    }
}
