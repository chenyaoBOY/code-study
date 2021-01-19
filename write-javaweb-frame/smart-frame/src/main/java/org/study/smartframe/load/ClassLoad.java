package org.study.smartframe.load;

import org.study.smartframe.util.ClassUtil;

/**
 * @author chenyao
 * @date 2021/1/19 11:08
 * @description
 */
public class ClassLoad {

    public static void initSmartFrameWork(){
        /**
         * 顺序一定不能错
         * 严格的加载顺序
         */
        Class<?>[] classes = {
                ClassParser.class,
                BeanParser.class,
                IocParser.class,
                ControllerParser.class
        };
        for (Class<?> c : classes) {
            ClassUtil.loadClass(c.getName(),true);
        }
    }

    public static void main(String[] args) {
        initSmartFrameWork();
    }
}
