package org.study.vo;

/**
 * @Description:enum公共接口
 * 继承接口可以使用工具类查询按code和desc查询enum
 * @Author:
 * @CreateDate: 2019/1/11 14:34
 */

public interface BaseEnum {
    /**
     * 获取编码
     * @return
     */
    Integer getCode();

    /**
     * 获取描述
     * @return
     */
    String getDesc();


}
