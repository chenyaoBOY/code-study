package org.study.frame;

import org.study.frame.rpc.OrderInterface;
import org.study.frame.rpc.UserJapan;
import org.study.smartframe.annotation.Action;
import org.study.smartframe.annotation.Controller;
import org.study.smartframe.annotation.Inject;
import org.study.smartframe.entity.ModelData;
import org.study.smartframe.entity.Param;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenyao
 * @date 2021/2/5 11:22
 * @description
 */
@Controller
public class OrderController {

    @Inject
    private OrderInterface orderInterface;

    public void setOrderInterface(OrderInterface orderInterface) {
        this.orderInterface = orderInterface;
    }

    @Action("get:/orderGet")
    public ModelData get() {
        Map<String,Object> map = new HashMap<>();
        map.put("name","chenyao");
        map.put("age",18);
        String s = orderInterface.sayHello(new Param(map));
        return new ModelData(s);
    }
    @Action("get:/orderRequestSpringboot")
    public ModelData requestSpringboot() {
        String s = orderInterface.requestSpringboot("chenyao",18);
        return new ModelData(s);
    }
    @Action("post:/requestSpringbootPost")
    public ModelData requestSpringbootPost() {
        UserJapan s = orderInterface.smartPost(new UserJapan("苍井空"),18);
        return new ModelData(s);
    }

}
