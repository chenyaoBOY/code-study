package org.study.smartframe.test;

import lombok.extern.slf4j.Slf4j;
import org.study.smartframe.annotation.Service;

/**
 * @author chenyao
 * @date 2021/1/19 11:17
 * @description
 */
@Service
@Slf4j
public class TestService {

    public void sayBye(){
        log.info("bye ! bye !");
    }
}
