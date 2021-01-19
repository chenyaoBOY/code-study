package org.study.smartframe.entity;

import lombok.Data;

import java.util.Map;

/**
 * @author chenyao
 * @date 2021/1/19 13:34
 * @description
 */

@Data
public class Param {

    private Map<String,Object> map;

    public Param(Map<String, Object> map) {
        this.map = map;
    }
}
