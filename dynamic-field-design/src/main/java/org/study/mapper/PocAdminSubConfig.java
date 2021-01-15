package org.study.mapper;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * poc_admin_sub_config
 * @author 
 */
@Data
public class PocAdminSubConfig implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * poc_admin_config 表主键
     */
    private Long adminConfigId;

    /**
     * 配置类型
     */
    private Short configType;

    /**
     * 字段名
     */
    private String name;

    /**
     * 字段值
     */
    private String value;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后更新时间
     */
    private Date lastUpdateTime;

    /**
     * 是否删除 1是 0 否
     */
    private Boolean isDel;

    private static final long serialVersionUID = 1L;
}