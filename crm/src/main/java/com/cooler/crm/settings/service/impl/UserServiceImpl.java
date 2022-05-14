package com.cooler.crm.settings.service.impl;

import com.cooler.crm.exception.LoginException;
import com.cooler.crm.settings.dao.UserDao;
import com.cooler.crm.settings.domain.User;
import com.cooler.crm.settings.service.UserService;
import com.cooler.crm.utils.DateTimeUtil;
import com.cooler.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author CoolEr
 * @create 2022/3/2
 */
public class UserServiceImpl implements UserService {

    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    public User login(String loginAct, String loginPwd, String ip) throws LoginException {

        Map<String,String> map = new HashMap<String,String>();
        map.put("loginAct", loginAct);
        map.put("loginPwd", loginPwd);

        User user = userDao.login(map);

        if(user==null){

            throw new LoginException("账号密码错误");

        }

        //验证失效时间
        String expireTime = user.getExpireTime();
        String currentTime = DateTimeUtil.getSysTime();
        if(expireTime.compareTo(currentTime)<0){

            throw new LoginException("账号已失效");

        }

        //判断锁定状态
        String lockState = user.getLockState();
        if("0".equals(lockState)){

            throw new LoginException("账号已锁定");

        }

        //判断ip地址
        String allowIps = user.getAllowIps();

        if(!allowIps.contains(ip)){

            throw new LoginException("ip地址受限");

        }


        return user;
    }

    public List<User> getUserList() {

        List<User> uList = userDao.getUserList();

        return uList;
    }
}
