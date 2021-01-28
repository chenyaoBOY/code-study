package org.study.demo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author chenyao
 * @date 2021/1/28 16:34
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {

    private Integer id;

    private String firstName;
    private String lastName;
    private Integer age;

    private String about;
    private List<Hobby> hobbies;


}
