import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.study.customer.Customer;
import org.study.customer.CustomerService;

import java.util.List;

/**
 * @author chenyao
 * @date 2021/1/15 14:28
 * @description
 */
public class CustomerTest {

    @Test
    public void test(){
        CustomerService customerService = new CustomerService();
        List<Customer> customerList = customerService.getAllCustomerList();
        System.out.println(JSON.toJSONString(customerList));
    }
}
