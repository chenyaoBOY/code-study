package org.study.smartframe.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenyao
 * @date 2021/1/19 13:35
 * @description
 */
@Data
public class View {

    private String path;

    private Map<String,Object> model;

    public View(String path) {
        this.path = path;
        model = new HashMap<>();
    }

    public View addModel(String key,Object value){
        model.put(key,value);
        return this;
    }
}
