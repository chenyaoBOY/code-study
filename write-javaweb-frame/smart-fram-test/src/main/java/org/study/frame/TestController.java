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
        Map<String, Object> map = new HashMap<>();
        map.put("name", "chenyao");
        map.put("age", 18);
        return new ModelData(map);
    }

    @Action("get:/view")
    public View getView(Param param) {
        List<Customer> list = DatabaseUtil.getList(Customer.class, "SELECT * FROM customer");
        return new View("customer-show.jsp").addModel("list", list);
    }
    @Action("get:/redirect")
    public View redirect(Param param) {
        return new View("/view");
    }
}
