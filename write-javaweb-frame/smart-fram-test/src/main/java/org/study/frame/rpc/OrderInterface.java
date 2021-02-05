package org.study.frame.rpc;

import org.study.rpc.ann.Rest;
import org.study.rpc.ann.RestRpc;

/**
 * @author chenyao
 * @date 2021/2/3 16:54
 * @description
 */
@RestRpc
public interface OrderInterface {

    @Rest(url = "http://localhost:8082/getCustomerList")
    String sayHello();

}
