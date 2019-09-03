package com.ryx.credit.cms.intercept;

import com.ryx.credit.cms.shiro.PasswordHash;
import com.ryx.credit.commons.shiro.ShiroUser;
import com.ryx.credit.pojo.admin.CUser;
import com.ryx.credit.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 作者：cx
 * 时间：2018/12/28
 * 描述：
 */
public class MainInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private IUserService userService;
    @Autowired
    private PasswordHash passwordHash;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(SecurityUtils.getSubject()!=null && SecurityUtils.getSubject().getPrincipal()!=null) {
            Long userId = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getId();
            request.setAttribute("MainLoginUserId",userId);

            //拦截默认密码强制修改
            String requestURI = request.getRequestURI();
            if(!requestURI.equals("/user/toEditPwd") && !requestURI.equals("/user/editUserPwd") && !requestURI.equals("/logout") ){
                CUser cUser = userService.selectById(userId);
                String hexPwd = passwordHash.toHex("111111", cUser.getSalt());
                if (cUser.getPassword().equals(hexPwd)) {
                    response.sendRedirect("/user/toEditPwd");
                }
            }
        }

        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
