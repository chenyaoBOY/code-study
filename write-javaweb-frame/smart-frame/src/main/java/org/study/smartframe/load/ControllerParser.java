package org.study.smartframe.load;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.study.smartframe.annotation.Action;
import org.study.smartframe.entity.Request;
import org.study.smartframe.entity.RequestHandler;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenyao
 * @date 2021/1/19 10:25
 * @description
 */
@Slf4j
public class ControllerParser {
    /**
     * 将带有@Action注解的 请求与 类和方法 映射
     */
    private static final Map<Request, RequestHandler> REQUEST_HANDLER_MAP = new ConcurrentHashMap<>();

    static {
        Set<Class<?>> controllerClasses = ClassParser.getControllerClasses();
        for (Class<?> controllerClass : controllerClasses) {
            Method[] methods = controllerClass.getMethods();
            for (Method method : methods) {
                if (!method.isAnnotationPresent(Action.class)) continue;
                String mapping = method.getAnnotation(Action.class).value();
                if (StringUtils.isEmpty(mapping) || !mapping.matches("\\w+:/\\w*")) continue;
                String[] split = mapping.split(":");
                if (split.length != 2) continue;
                Request request = new Request(split[0], split[1]);
                if (REQUEST_HANDLER_MAP.containsKey(request))
                    throw new RuntimeException("@Action for requestMethod and requestPath only permit one exists");
                REQUEST_HANDLER_MAP.put(request, new RequestHandler(controllerClass, method));
                log.debug("method:{} url:{} mapping success",split[0], split[1]);
            }
        }
    }

    public static RequestHandler getHandler(String requestMethod, String requestPath) {
        if(StringUtils.isNotEmpty(requestMethod)){
            requestMethod = requestMethod.toLowerCase();
        }
        return REQUEST_HANDLER_MAP.get(new Request(requestMethod, requestPath));
    }
}
