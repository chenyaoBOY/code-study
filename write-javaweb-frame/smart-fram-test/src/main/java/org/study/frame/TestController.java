package org.study.frame;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.study.database.DatabaseUtil;
import org.study.frame.rpc.OrderInterface;
import org.study.smartframe.annotation.Action;
import org.study.smartframe.annotation.Controller;
import org.study.smartframe.annotation.Inject;
import org.study.smartframe.entity.ModelData;
import org.study.smartframe.entity.Param;
import org.study.smartframe.entity.View;

import java.util.List;

/**
 * @author chenyao
 * @date 2021/1/19 11:17
 * @description
 */
@Controller
@Slf4j
public class TestController {

    /**
     * CGLIB代理的类 需要注入属性时 需要提供set方法
     */
    @Inject
    private TestService testService;

    @Inject
    private OrderInterface orderInterface;

    public void setOrderInterface(OrderInterface orderInterface) {
        this.orderInterface = orderInterface;
    }

    public void setTestService(TestService testService) {
        this.testService = testService;
    }

    @Action("get:/get")
    public ModelData get(Param param) {
        return new ModelData(param);
    }
    @Action("get:/getCustomerList")
    public ModelData getCustomerList() {
        List<Customer> list = testService.getList();
        return new ModelData(list);
    }

    @Action("get:/view")
    public View getView() {
        String sayHello = orderInterface.sayHello();
        log.info("orderInterface#sayHello result:{}",sayHello);
        ModelData modelData = JSON.parseObject(sayHello, ModelData.class);
        String obj = modelData.getObj().toString();
        List<Customer> list = JSON.parseArray(obj, Customer.class);
        return new View("customer-show.jsp").addModel("list", list);
    }
    @Action("post:/view")
    public View postView() {
        List<Customer> list = DatabaseUtil.getList(Customer.class, "SELECT * FROM customer");
        return new View("customer-show.jsp").addModel("list", list);
    }

    /**
     * 可以重定向到  get /view
     * @return
     */
    @Action("get:/redirect")
    public View redirect() {
        return new View("/view");
    }
}
