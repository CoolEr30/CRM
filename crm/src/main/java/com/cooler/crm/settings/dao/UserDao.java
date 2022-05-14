package com.cooler.crm.settings.dao;

import com.cooler.crm.settings.domain.User;

import java.util.List;
import java.util.Map;

/**
 * @author CoolEr
 * @create 2022/3/2
 */
public interface UserDao {


    User login(Map<String, String> map);
    List<User> getUserList();
}
