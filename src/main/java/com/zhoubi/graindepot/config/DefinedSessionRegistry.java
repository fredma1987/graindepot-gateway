package com.zhoubi.graindepot.config;

import com.zhoubi.graindepot.bean.BaseUser;
import com.zhoubi.graindepot.bean.UserAddress;
import com.zhoubi.graindepot.bean.UserBean;
import com.zhoubi.graindepot.biz.UserSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by zhanghao on 2018/8/1.
 */
@Component
public class DefinedSessionRegistry extends SessionRegistryImpl {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserSecurity userSecurity;

    @Override
    public void registerNewSession(String sessionId, Object principal) {
        super.registerNewSession(sessionId, principal);
        User user = (User) principal;
        BaseUser currentUser = userSecurity.getUserByUsername(user.getUsername());
        //用户的地址信息,用于和出入库相关等必须针对某个粮库进行操作时使用
        request.getSession().setAttribute("currentUser", currentUser);
        UserAddress ua = new UserAddress();
        ua.setProvinceid(currentUser.getProvinceid());
        ua.setCityid(currentUser.getCityid());
        ua.setCountyid(currentUser.getCountyid());
        ua.setGroupid(currentUser.getGroupid());
        ua.setCompanyid(currentUser.getCompanyid());
        ua.setGraindepotid(currentUser.getGraindepotid());
        request.getSession().setAttribute("userAddress", ua);
    }
}
