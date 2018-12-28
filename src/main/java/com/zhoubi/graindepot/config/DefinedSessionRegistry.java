package com.zhoubi.graindepot.config;

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
        UserBean currentUser = userSecurity.getUserByUsername(user.getUsername());
        request.getSession().setAttribute("currentUser", currentUser);
    }
}
