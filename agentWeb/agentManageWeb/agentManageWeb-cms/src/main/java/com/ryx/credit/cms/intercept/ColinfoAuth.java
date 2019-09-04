//package com.ryx.credit.cms.intercept;
//
//import com.ryx.credit.cms.controller.BaseController;
//import com.ryx.credit.cms.shiro.PasswordHash;
//import com.ryx.credit.pojo.admin.CUser;
//import com.ryx.credit.service.IUserService;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.log4j.LogManager;
//import org.apache.log4j.Logger;
//import org.apache.shiro.SecurityUtils;
//import org.springframework.web.context.support.WebApplicationContextUtils;
//import org.springframework.web.context.support.XmlWebApplicationContext;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Date;
//
///**
// * Created by RYX on 2018/9/19.
// */
//public class ColinfoAuth extends BaseController implements Filter {
//
//    private static final Logger log = LogManager.getLogger(ColinfoAuth.class);
//
//    private String excludedPages;
//
//    private String[] excludedPageArray;
//
//    private IUserService userService;
//    private PasswordHash passwordHash;
//
//    public void init(FilterConfig fConfig) throws ServletException {
//        ServletContext sc = fConfig.getServletContext();
//        XmlWebApplicationContext cxt = (XmlWebApplicationContext) WebApplicationContextUtils.getWebApplicationContext(sc);
//        userService = (IUserService)cxt.getBean("userService");
//        passwordHash = (PasswordHash)cxt.getBean("passwordHash");
//        excludedPages = fConfig.getInitParameter("excludedPages");
//        if (StringUtils.isNotEmpty(excludedPages)) {
//            excludedPageArray = excludedPages.split(",");
//        }
//        return;
//    }
//
//    public void destroy() {
//        return;
//    }
//
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//
//        try {
//            boolean isExcludedPage = false;
//            request.setAttribute("system_now_date",new Date());
//            String queryString = ((HttpServletRequest) request).getQueryString();
//            String path = ((HttpServletRequest) request).getRequestURI()+(queryString==null?"":"?"+queryString) ;
//            for (String page : excludedPageArray) {//判断是否在过滤url之外
//                if(path.contains(page)){
//                    isExcludedPage = true;
//                    break;
//                }
//            }
//            if (isExcludedPage) {
//                chain.doFilter(request, response);
//            } else {
//                if(SecurityUtils.getSubject().getPrincipal()!=null) {
//                    CUser cUser = userService.selectById(getUserId());
//                    String hexPwd = passwordHash.toHex("111111", cUser.getSalt());
//                    if (cUser.getPassword().equals(hexPwd)) {
//                        log.info("=================="+path+"被拦截并且跳转");
//                        ((HttpServletResponse) response).sendRedirect("/user/toEditPwd");
//                    } else {
//                        chain.doFilter(request, response);
//                    }
//                }else{
//                    chain.doFilter(request, response);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ServletException e) {
//            e.printStackTrace();
//        }
//    }
//
//}
