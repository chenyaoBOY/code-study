import org.junit.Test;
import org.study.smartframe.proxy.ProxyParserInterface;

import java.util.ServiceLoader;

/**
 * @author chenyao
 * @date 2021/2/4 14:34
 * @description
 */
public class SPITest {
    @Test
    public void test(){
        ServiceLoader<ProxyParserInterface> result = ServiceLoader.load(ProxyParserInterface.class);
        for (ProxyParserInterface proxy : result) {
            System.out.println(proxy.getClass().getSimpleName());
        }
    }
}
