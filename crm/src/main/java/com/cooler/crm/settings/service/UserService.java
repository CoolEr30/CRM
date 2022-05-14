package com.cooler.crm.settings.service;

import com.cooler.crm.exception.LoginException;
import com.cooler.crm.settings.domain.User;

import java.util.List;

/**
 * @author CoolEr
 * @create 2022/3/2
 */
public interface UserService {
    User login(String loginAct, String loginPwd, String ip) throws LoginException;

    List<User> getUserList();
}
