package org.study.smartframe.entity;

import lombok.Data;

/**
 * @author chenyao
 * @date 2021/1/19 13:38
 * @description
 */
@Data
public class ModelData {
    private Object obj;

    public ModelData(Object obj) {
        this.obj = obj;
    }
}
