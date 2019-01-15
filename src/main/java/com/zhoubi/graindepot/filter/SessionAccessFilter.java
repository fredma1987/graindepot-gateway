package com.zhoubi.graindepot.filter;

import com.alibaba.fastjson.JSON;
import com.zhoubi.graindepot.rpc.IUserService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * ${DESCRIPTION}
 *
 * @author administrator
 * @create 2017-06-23 8:25
 */
@Component
@Slf4j
public class SessionAccessFilter extends ZuulFilter {
    @Autowired
    private SessionRepository<?> repository;
    @Autowired
    private IUserService userService;

    @Value("${gate.ignore.startWith}")
    private String startWith;
    @Value("${gate.ignore.contain}")
    private String contain;
    @Value("${gate.oauth.prefix}")
    private String oauthPrefix;

    public SessionAccessFilter() {
        super();
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        final String requestUri = request.getRequestURI();
        if (isStartWith(requestUri) || isContains(requestUri) || isOAuth(requestUri)) {
            return null;
        }
        return null;
    }

    /**
     * 判定是否oauth资源
     *
     * @param requestUri
     * @return
     */
    private boolean isOAuth(String requestUri) {
        return requestUri.startsWith(oauthPrefix);
    }




    /**
     * 是否包含某种特征
     *
     * @param requestUri
     * @return
     */
    private boolean isContains(String requestUri) {
        boolean flag = false;
        for (String s : contain.split(",")) {
            if (requestUri.contains(s))
                return true;
        }
        return flag;
    }


    /**
     * URI是否以什么打头
     *
     * @param requestUri
     * @return
     */
    private boolean isStartWith(String requestUri) {
        boolean flag = false;
        for (String s : startWith.split(",")) {
            if (requestUri.startsWith(s))
                return true;
        }
        return flag;
    }

    /**
     * Reports an error message given a response body and code.
     *
     * @param body
     * @param code
     */
    private void setFailedRequest(HttpServletRequest request,String body, int code) {
        //log.debug("Reporting error ({}): {}", code, body);

        RequestContext ctx = RequestContext.getCurrentContext();
        String requestType = request.getHeader("X-Requested-With");
        if("XMLHttpRequest".equals(requestType)){
            ctx.setResponseStatusCode(403);
        }else{
            ctx.setResponseStatusCode(200);
        }
        ctx.getResponse().addHeader("noPermissions","true");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/noPermissions");

        if (dispatcher!=null) {
            try {
                dispatcher.forward(request,ctx.getResponse());
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
