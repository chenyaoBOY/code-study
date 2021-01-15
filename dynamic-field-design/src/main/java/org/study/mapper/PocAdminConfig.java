package org.study.mapper;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * poc_admin_config
 * @author 
 */
@Data
public class PocAdminConfig implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 配置类型
     */
    private Short configType;

    /**
     * 支付渠道
     */
    private String payChannel;

    /**
     * 支付方式
     */
    private String payMethod;

    /**
     * 品牌
     */
    private String siteId;

    /**
     * 站点
     */
    private String siteUid;

    /**
     * 操作人
     */
    private String operateName;

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