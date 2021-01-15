package org.study.vo;

/**
 * 退款路径枚举 退款路径 1API退款 2渠道退款 3手工打款 4API打款
 *
 * @author huangzejun
 * @since 2019-08-08
 */
public enum RefundPathEnum implements BaseEnum {
    /**
     * 未定义
     */
    UNDEFINED(0, "未定义", "undefined"),

    /**
     * API退款
     */
    API_REFUND(1, "API退款", "Api refund"),

    /**
     * 渠道退款
     */
    CHANNEL_REFUND(2, "渠道退款", "Channel refund"),

    /**
     * 手工打款
     */
    MANUAL_TRANSFER(3, "手工打款", "Manual offline refund"),

    /**
     * API打款
     */
    API_TRANSFER(4, "API打款", "Api offline refund"),

    /**
     * 平台退款（用于超期退）
     */
    PLATFORM_REFUND (5, "平台退款", "Platform refund (exceed the deadline)");

    /**
     * code
     */
    private final Integer code;

    /**
     * 文字描述
     */
    private final String desc;

    /**
     * 英文文字描述
     */
    private final String enDesc;

    RefundPathEnum(Integer code, String desc, String enDesc) {
        this.code = code;
        this.desc = desc;
        this.enDesc =enDesc;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    public String getEnDesc(){
        return enDesc;
    }


}
