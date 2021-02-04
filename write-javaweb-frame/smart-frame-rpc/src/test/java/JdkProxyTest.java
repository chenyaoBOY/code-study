import org.junit.Test;
import org.study.rpc.OrderInterface;
import org.study.rpc.core.RpcInvocationHandler;

import java.lang.reflect.Proxy;

/**
 * @author chenyao
 * @date 2021/2/4 14:34
 * @description
 */
public class JdkProxyTest {

    @Test
    public void test(){
        OrderInterface o = (OrderInterface) Proxy.newProxyInstance(this.getClass().getClassLoader(), OrderInterface.class.getInterfaces(), new RpcInvocationHandler());
        o.sayHello();
    }
}
