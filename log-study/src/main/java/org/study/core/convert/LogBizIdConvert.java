package org.study.core.convert;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.apache.commons.lang3.StringUtils;
import org.study.core.LogConstant;

import java.util.Map;

/**
 * @author chenyao
 * @date 2021/2/1 16:01
 * @description
 */
public class LogBizIdConvert extends ClassicConverter {

    @Override
    public String convert(ILoggingEvent loggingEvent) {
        Map<String, String> mdcPropertyMap = loggingEvent.getMDCPropertyMap();
        if(mdcPropertyMap.containsKey(LogConstant.BIZ_ID)) {
            return loggingEvent.getMDCPropertyMap().get(LogConstant.BIZ_ID);
        }
        return StringUtils.EMPTY;
    }
}