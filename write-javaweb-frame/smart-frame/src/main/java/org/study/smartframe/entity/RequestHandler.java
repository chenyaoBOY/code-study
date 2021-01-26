package org.study.smartframe.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Method;

/**
 * @author chenyao
 * @date 2021/1/19 10:28
 * @description
 */
@Data
public class RequestHandler {

    private Class<?> controllerClass;

    private Method actionMethod;

    private Class<?>[] parameterTypes;

    public RequestHandler(Class<?> controllerClass, Method actionMethod, Class<?>[] parameterTypes) {
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
        this.parameterTypes = parameterTypes;
    }
}
