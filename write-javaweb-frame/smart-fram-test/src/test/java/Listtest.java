import org.junit.Test;
import org.study.frame.proxy.HelloImpl;
import org.study.rpc.OrderInterface;
import org.study.rpc.core.RpcInvocationHandler;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chenyao
 * @date 2021/1/20 15:31
 * @description
 */
public class Listtest implements OrderInterface{

    @Test
    public  void test() {
        List<HelloImpl> list= new ArrayList<>();
        list.add(new HelloImpl("chenyao"));
        list.add(new HelloImpl("zhangdi"));
        list.add(new HelloImpl("jiafu"));

        List<HelloImpl> collect = list.stream().filter(val -> val.getName().equals("chenyao")).collect(Collectors.toList());
        HelloImpl hello = collect.get(0);
        hello.setName("asdlkghadsklg");
        System.out.println(1);
    }

    @Test
    public void test2(){
        Class<?>[] interfaces = OrderInterface.class.getInterfaces();
        ClassLoader classLoader = OrderInterface.class.getClassLoader();
        OrderInterface o = (OrderInterface) Proxy.newProxyInstance(classLoader,new Class[]{OrderInterface.class} , new RpcInvocationHandler());
        o.sayHello();
    }

    @Override
    public void sayHello() {
        System.out.println(123);
    }
}
