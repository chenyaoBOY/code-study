package org.study.frame.rpc;

import org.study.frame.Customer;
import org.study.rpc.util.ReflectUtil;
import org.study.smartframe.entity.Param;

import java.lang.reflect.Method;

/**
 * @author chenyao
 * @date 2021/2/5 17:21
 * @description
 */
public class Demo {
    public static void main(String[] args) throws NoSuchMethodException {
        Method method = OrderInterface.class.getMethod("requestSpringboot", String.class, Integer.class);
        ReflectUtil.getPramParameterInfos(method,null);


        Customer customer = new Customer();
        Method method1 = customer.getClass().getMethod("setName", String.class);
        ReflectUtil.getPramParameterInfos(method1,null);
    }
}
