package org.study.mongo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenyao
 * @date 2021/2/7 16:30
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProduct {

    private Long orderProductId;
    private String orderProductName;
}
