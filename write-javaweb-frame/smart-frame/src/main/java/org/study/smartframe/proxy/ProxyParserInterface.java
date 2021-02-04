package org.study.smartframe.proxy;

import org.study.smartframe.load.BeanParser;
import org.study.smartframe.load.service.FrameInit;
import org.study.smartframe.proxy.core.Proxy;

import java.util.*;

/**
 * @author chenyao
 * @date 2021/2/4 10:47
 * @description
 */
public interface ProxyParserInterface extends FrameInit {

    Map<Class<?>/*proxyClass*/, Set<Class<?>>/*targetClass*/> createProxyMap();

    Object createProxy(final Class<?> targetClass, List<Object> proxyList);

    default Map<Class<?>, List<Object>> createTargetMap(Map<Class<?>/*proxyClass*/, Set<Class<?>>/*targetClass*/> proxyMap) throws IllegalAccessException, InstantiationException {
        Map<Class<?>/*targetClass*/, List<Object>/*proxyClass*/> res = new HashMap<>();
        for (Map.Entry<Class<?>, Set<Class<?>>> entry : proxyMap.entrySet()) {

            for (Class<?> targetClass : entry.getValue()) {
                Class<?> proxyClass = entry.getKey();
                /**
                 * 代理类是接口 需要通过JDK动态代理 代理
                 */
                if(proxyClass.isInterface()){
                    res.put(proxyClass,null);
                    continue;
                }
                /**
                 * 代理类是Proxy接口的实现类  先通过构造函数实例化
                 * 再通过Cglib代理目标类
                 */
                Object proxy = entry.getKey().newInstance();
                if (res.containsKey(targetClass)) {
                    res.get(targetClass).add(proxy);
                } else {
                    List<Object> list = new ArrayList<>();
                    list.add(proxy);
                    res.put(targetClass, list);
                }
            }
        }
        return res;
    }
    /**
     * 1 先找到 代理类（Proxy的实现类） 的class 与 目标class（被增强的类） 的关系  Map<Class<?>/proxyClass/, Set<Class<?>>/targetClass/>
     * 1.1 比如Aspect注解增强，先获取所有的Proxy实现类 即代理类 过滤带有Aspect注解的 Set<Class>集合
     * 1.2 再根据Aspect注解上的value值 找到目标class
     * 2. 由于目标类可能会被多种 代理增强  所以要反转一下1中的map映射 找出 targetClass 和ProxyCLass之间的映射关系
     * 3. 根据第二部的结果 就可以直接通过CGlib工具创建 targetClass的代理类
     */
    @Override
    default void init() {
        try {
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?>, List<Object>> targetMap = createTargetMap(proxyMap);
            for (Map.Entry<Class<?>, List<Object>> entry : targetMap.entrySet()) {
                BeanParser.setBean(entry.getKey(), createProxy(entry.getKey(), entry.getValue()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
