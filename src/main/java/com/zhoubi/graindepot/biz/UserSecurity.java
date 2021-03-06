package com.zhoubi.graindepot.biz;

import com.zhoubi.graindepot.bean.BaseUser;
import com.zhoubi.graindepot.bean.UserBean;
import com.zhoubi.graindepot.rpc.IUserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * ${DESCRIPTION}
 *
 * @author administrator
 * @create 2017-06-21 8:39
 */
@Service
public class UserSecurity {
    @Lazy
    @Autowired
    private IUserService userService;

    @HystrixCommand(fallbackMethod = "fallbackMethod")
    //获取当前登录用户，登录专用方法
    public BaseUser getUserByUsername(String username) {
        return userService.getUserByUsername(username);
    }
    public BaseUser fallbackMethod(String username) {
        return new BaseUser();
    }
}
