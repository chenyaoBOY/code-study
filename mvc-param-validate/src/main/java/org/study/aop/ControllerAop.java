package org.study.aop;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.validator.HibernateValidator;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author chenyao
 * @date 2020/12/15 16:26
 * @description
 */
@Component
@Aspect
@Slf4j
public class ControllerAop {

    private static final Validator VALIDATOR  = Validation
            .byProvider(HibernateValidator .class).configure().failFast(true).buildValidatorFactory().getValidator();

    @Around("execution(* org.study.controller.*.*(..))")
    public Object Around(JoinPoint point) throws InvocationTargetException, IllegalAccessException {

        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Object[] args = point.getArgs();
        if (args == null || args.length > 1 || args[0] == null) {
            return method.invoke(point.getTarget(),args);
        }
        Set<ConstraintViolation<Object>> set = VALIDATOR.validate(args[0]);
        if(CollectionUtils.isEmpty(set)) return method.invoke(point.getTarget(),args);
        for (ConstraintViolation<Object> content : set) {
            String message = content.getMessage();
            Map<String,Object> map = new HashMap<>();
            map.put("code","-1");
            map.put("message",message);
            return JSON.toJSONString(map);
        }
        return method.invoke(point.getTarget(),args);
    }
}
