package org.study.mongo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenyao
 * @date 2021/2/7 16:29
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfo {

    private String orderId;
    private String orderMoney;

    private OrderUser orderUser;
    private OrderProduct orderProduct;
}
