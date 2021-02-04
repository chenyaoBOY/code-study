package org.study.rpc.core;

import lombok.extern.slf4j.Slf4j;
import org.study.smartframe.load.ClassParser;
import org.study.smartframe.load.service.ClassParserInterface;
import org.study.smartframe.util.ClassUtil;

import java.util.Set;

/**
 * @author chenyao
 * @date 2021/2/4 11:15
 * @description
 */
@Slf4j
public class RpcClassParser implements ClassParserInterface {
    @Override
    public void init() {
        Set<Class<?>> rpcClasses = ClassUtil.getClassFromPackage("org.study.rpc");
        ClassParser.getClassSet().addAll(rpcClasses);
        log.info("load {} rpc class success",rpcClasses.size());
    }
}
