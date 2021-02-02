package org.study.core;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.study.util.SpelParser;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author chenyao
 * @date 2021/2/1 16:13
 * @description
 */
@Component
@Aspect
public class LogAop {
//    解析参数名称
    private static final LocalVariableTableParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

    @Pointcut("@annotation(logAnn)")
    public void cutPoint(LogAnn logAnn) {
    }

    @Around("cutPoint(logAnn)")
    public Object around(ProceedingJoinPoint point, LogAnn logAnn) throws Throwable {
        Map<String, String> logMapInfo = new HashMap<>(8);
        if (StringUtils.isNotEmpty(logAnn.logTag())) {
            logMapInfo.put(LogConstant.LOG_TAG, logAnn.logTag());
        }
        if (StringUtils.isNotEmpty(logAnn.bizID())) {
            String spel = parseSpel(point, logAnn.bizID());
            logMapInfo.put(LogConstant.BIZ_ID, spel);
        }
        for (Map.Entry<String, String> en : logMapInfo.entrySet()) {
            MDC.put(en.getKey(), en.getValue());
        }
        try {
            return point.proceed();
        } finally {
            // 清理上一次设置的MDC数据
            logMapInfo.keySet().forEach(MDC::remove);
        }
    }
    private String parseSpel(ProceedingJoinPoint point, String spel) {
        //获取方法参数的名称 比如parseSpel 结果为：[point,spel]
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        String[] params = parameterNameDiscoverer.getParameterNames(method);
        return SpelParser.getSpelValue(params, point.getArgs(),spel);
    }
}
