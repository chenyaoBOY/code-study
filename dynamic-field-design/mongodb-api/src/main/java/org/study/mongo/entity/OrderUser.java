package org.study.mongo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenyao
 * @date 2021/2/7 16:31
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderUser {
    private Long userId;
    private String useName;
}
