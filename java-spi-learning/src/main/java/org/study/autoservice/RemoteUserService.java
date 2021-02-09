package org.study.autoservice;

import com.google.auto.service.AutoService;

/**
 * @author dizhang
 * @date 2021-02-09
 */
@AutoService(UserService.class)
public class RemoteUserService implements UserService{
    @Override
    public String userName() {
        return "remote user";
    }
}
