package org.study.frame;

import org.study.database.DatabaseUtil;
import org.study.smartframe.annotation.Action;
import org.study.smartframe.annotation.Controller;
import org.study.smartframe.annotation.Inject;
import org.study.smartframe.entity.ModelData;
import org.study.smartframe.entity.Param;
import org.study.smartframe.entity.View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenyao
 * @date 2021/1/19 11:17
 * @description
 */
@Controller
public class TestController {

    @Inject
    private TestService testService;

    @Action("get:/get")
    public ModelData get(Param param) {
        return new ModelData(param);
    }

    @Action("get:/view")
    public View getView(Param param) {
        List<Customer> list = DatabaseUtil.getList(Customer.class, "SELECT * FROM customer");
        return new View("customer-show.jsp").addModel("list", list);
    }
    @Action("post:/view")
    public View postView(Param param) {
        List<Customer> list = DatabaseUtil.getList(Customer.class, "SELECT * FROM customer");
        return new View("customer-show.jsp").addModel("list", list);
    }

    /**
     * 可以重定向到  get /view
     * @param param
     * @return
     */
    @Action("get:/redirect")
    public View redirect(Param param) {
        return new View("/view");
    }
}
