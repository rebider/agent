package com.ryx.credit.cms.shiro.filter;

import com.ryx.credit.cms.shiro.permission.UrlPermission;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 作者：cx
 * 时间：2019/4/26
 * 描述：
 */
public class UrlAuthorityFilter extends AuthorizationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginRequest(request, response)) {
            return true;
        } else {
            Subject subject = getSubject(request, response);
            // If principal is not null, then the user is known and should be allowed access.
            //如果未登录就禁止访问
            if(subject.getPrincipal() == null){
                return false;
            }
            //如果登录了检查用户权限
            DefaultWebSecurityManager sem = (DefaultWebSecurityManager)SecurityUtils.getSecurityManager();
            if(sem.isPermitted(subject.getPrincipals(),new UrlPermission(((HttpServletRequest)request).getRequestURI()))){
                return true;
            }
            return false;
        }
    }




}
