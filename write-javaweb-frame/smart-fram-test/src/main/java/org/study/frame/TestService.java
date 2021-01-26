package org.study.frame;

import org.study.database.DatabaseUtil;
import org.study.smartframe.annotation.Service;
import org.study.smartframe.proxy.ann.Transaction;

import java.util.List;

/**
 * @author chenyao
 * @date 2021/1/19 11:17
 * @description
 */
@Service
public class TestService {

    @Transaction
    public List<Customer> getList(){
        return DatabaseUtil.getList(Customer.class, "SELECT * FROM customer");
    }
}
