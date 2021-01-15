package org.study.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author chenyao
 * @date 2020/12/24 15:05
 * @description
 */
@Data
public class MoneyModel {
    private String country;
    private String cardType;
    private Integer period;
    private BigDecimal rate;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    private String maxAmountCurrency;
    private BigDecimal interestFree;
}
