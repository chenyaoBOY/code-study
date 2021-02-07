package org.study.rpc.util;

import org.study.rpc.ann.RequestBody;
import org.study.rpc.ann.RequestParam;
import org.study.rpc.request.entity.ParameterInfo;
import org.study.smartframe.entity.Param;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenyao
 * @date 2021/2/5 15:06
 * @description
 */
public class ReflectUtil {


    public static List<ParameterInfo> getPramParameterInfos(Method method,Object[] args) {
        String[] parameterNames = getParameterNames(method);
        Class<?>[] parameterTypes = method.getParameterTypes();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();

        List<ParameterInfo> res = new ArrayList<>(parameterTypes.length);
        for (int i = 0; i < parameterTypes.length; i++) {
            res.add(ParameterInfo.builder()
                    .index(i)
                    .paramName(parameterNames[i])
                    .paramClass(parameterTypes[i])
                    .paramAnns(parameterAnnotations[i])
                    .paramVal(args[i])
                    .build());
        }
        return res;
    }

    /**
     * jdk1.8 之前的版本 无法通过 Parameter#getName 获取到参数名称 只能获取到  arg0 arg1...
     * 如果想要获取 可以通过三种方式：
     * 1 idea设置 java compiler 中 Java doc 加上 -parameters参数
     * 2 maven设置plugin 也是当前选择的方式
     * 3 使用spring的工具类  LocalVariableTableParameterNameDiscoverer 获取
     * 通过debug查看源码方式 spring是通过再次加载class文件 获取输入流的方式 并缓存该class的method
     *
     * @param method
     * @return
     */
    private static String[] getParameterNames(Method method) {
        String[] res = new String[method.getParameters().length];
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            res[i] = parameters[i].getName();
        }
        return res;
    }

    public ParameterInfo getInfo(@RequestBody Param param, @RequestParam String name, Integer age) {
        return null;
    }
    public ParameterInfo getInfo2(@RequestBody Param param, @RequestParam String name, Integer age) {
        return null;
    }

    public static void main(String[] args) throws NoSuchMethodException {
        ReflectUtil reflectUtil = new ReflectUtil();
        Method method = reflectUtil.getClass().getMethod("getInfo2", Param.class, String.class, Integer.class);
        reflectUtil.getPramParameterInfos(method,null);
    }
}
