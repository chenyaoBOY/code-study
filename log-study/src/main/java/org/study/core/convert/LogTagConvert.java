package org.study.core.convert;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.ThrowableProxy;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.study.core.LogConstant;

import java.util.Map;

/**
 * @author chenyao
 * @date 2021/2/1 16:01
 * @description
 */
public class LogTagConvert extends ClassicConverter {

    @Override
    public String convert(ILoggingEvent loggingEvent) {
        Map<String, String> mdcPropertyMap = loggingEvent.getMDCPropertyMap();
        if(mdcPropertyMap.containsKey(LogConstant.LOG_TAG)) {
            return loggingEvent.getMDCPropertyMap().get(LogConstant.LOG_TAG);
        }
        return StringUtils.EMPTY;
    }
}