package com.ryx.credit.cms.controller;

import com.ryx.credit.cms.controller.agent.TreeController;
import com.ryx.credit.cms.shiro.captcha.DreamCaptcha;
import com.ryx.credit.common.enumc.ColinfoPayStatus;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.util.EnvironmentUtil;
import com.ryx.credit.commons.csrf.CsrfToken;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.*;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.AgentColinfo;
import com.ryx.credit.pojo.admin.vo.UserVo;
import com.ryx.credit.service.ICuserAgentService;
import com.ryx.credit.service.ISendSMSService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.AgentColinfoService;
import com.ryx.credit.service.agent.AgentService;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @description：登录退出
 * @author：wangqi
 * @date：2017/11/5 14:51
 */
@Controller
public class LoginController extends BaseController {

    private static final Logger logger = Logger.getLogger(TreeController.class);
    @Autowired
    private DreamCaptcha dreamCaptcha;
    @Autowired
    private AgentColinfoService agentColinfoService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private AgentService agentService;
    @Autowired
    private ICuserAgentService iCuserAgentService;
    @Autowired
    private ISendSMSService sendSMSService;

    /**
     * 首页
     *
     * @return
     */
    @GetMapping("/")
    public String index() {
        return "redirect:/index";
    }

    /**
     * 首页
     *
     * @param model
     * @return
     */
    @GetMapping("/index")
    public String index(Model model) {
        String agentId = getAgentId();
        Boolean isFail = false;
        try {
            if (StringUtils.isNotBlank(agentId)) {
                AgentColinfo agentColinfo = agentColinfoService.selectByAgentId(agentId);
                if (null == agentColinfo) {
                    model.addAttribute("isFail", isFail);
                    return "index";
                }
                if (ColinfoPayStatus.D.getValue().equals(agentColinfo.getPayStatus())) {
                    isFail = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("isFail", isFail);
        return "index";
    }

    /**
     * GET 登录
     * @return {String}
     */
  /*  @GetMapping("/login")
    @CsrfToken(create = true)
    public String login() {
        logger.info("GET请求登录");
        if (SecurityUtils.getSubject().isAuthenticated()) {
            return "redirect:/index";
        }
        return "login";
    }*/

    /**
     * 新页面调试contro
     *
     * @return {String}
     */
    @GetMapping("/login")
    @CsrfToken(create = true)
    public String login(HttpServletRequest request) {
        logger.info("GET请求登录");
        if (SecurityUtils.getSubject().isAuthenticated()) {
            return "redirect:/index";
        }
        request.setAttribute("ss", request.getParameter("ss"));
        if(!EnvironmentUtil.isProduction()){
            request.setAttribute("code","AAAA");
        }
        return "login2";
    }

    /**
     * POST 登录 shiro 写法
     *
     * @param username 用户名
     * @param password 密码
     * @return {Object}
     */
    @PostMapping("/login")
    @CsrfToken(remove = true)
    @ResponseBody
    public Object loginPost(HttpServletRequest request, HttpServletResponse response,
                            String username, String password, String captcha, String code,
                            @RequestParam(value = "rememberMe", defaultValue = "0") Integer rememberMe) {
        logger.info("POST请求登录");
        // 改为全部抛出异常，避免ajax csrf token被刷新
        if (StringUtils.isBlank(username)) {
            throw new RuntimeException("用户名不能为空");
        }
        logger.info("loginPost logger:{" + password + "}");
        if (StringUtils.isBlank(password)) {
            throw new RuntimeException("密码不能为空");
        }
        if (StringUtils.isBlank(captcha)) {
            throw new RuntimeException("验证码不能为空");
        }
        if (!dreamCaptcha.validate(request, response, captcha)) {
            throw new RuntimeException("验证码错误");
        }
//        UserVo userVo = new UserVo();
//        userVo.setLoginName(username);
//        List<CUser> cUsers = iUserService.selectByLoginName(userVo);
//        if (cUsers.size() > 0) {
//            CUser cUser = cUsers.get(0);
//            String agent = redisService.hGet("config", "org");
//            if (agent.equals(String.valueOf(cUser.getOrganizationId()))) {
//                if (StringUtils.isBlank(code)) {
//                    throw new RuntimeException("手机验证码不能为空");
//                }
//                //todo 代理商短信认证
//                CuserAgent cuserAgent = new CuserAgent();
//                cuserAgent.setUserid(cUser.getId().toString());
//                List<CuserAgent> cuserAgents = (List<CuserAgent>) iCuserAgentService.configExample(pageProcessAll(1), cuserAgent).get("list");
//                if (cuserAgents.size() > 0) {
//                    Agent agentPhone = agentService.getAgentById(cuserAgents.get(0).getAgentid());
//                    String sendCode = redisService.getValue(agentPhone.getAgLegalMobile());
//                    if (!sendCode.equals(code)) {
//                        throw new RuntimeException("手机验证码错误");
//                    } else {
//                        try {
//                            redisService.delete(agentPhone.getAgLegalMobile());
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//
//        }


        Subject user = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        // 设置记住密码
        token.setRememberMe(1 == rememberMe);
        try {
            user.login(token);
            return renderSuccess();
        } catch (UnknownAccountException e) {
            throw new RuntimeException("账号不存在！", e);
        } catch (DisabledAccountException e) {
            throw new RuntimeException("账号未启用！", e);
        } catch (IncorrectCredentialsException e) {
            throw new RuntimeException("密码错误！", e);
        } catch (Throwable e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 未授权
     *
     * @return {String}
     */
    @GetMapping("/unauth")
    public String unauth() {
        if (SecurityUtils.getSubject().isAuthenticated() == false) {
            return "redirect:/login";
        }
        return "unauth";
    }

    /**
     * 退出
     *
     * @return {Result}
     */
    @PostMapping("/logout")
    @ResponseBody
    public Object logout() {
        logger.info("登出");
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return renderSuccess();
    }

    /**
     * sendSMS
     *
     * @return {Result}
     */
    @PostMapping("/sendSms")
    @ResponseBody
    public Object sendSms(HttpServletRequest request, HttpServletResponse response, String username) {
        try {
            if (StringUtils.isBlank(username)) {
                return new ResponseInfo("0", "账户不能为空");
            }
            UserVo userVo = new UserVo();
            userVo.setLoginName(username);
            List<CUser> cUsers = iUserService.selectByLoginName(userVo);
            if (cUsers.size() > 0) {
                CUser cUser = cUsers.get(0);
                String agent = redisService.hGet("config", "org");
                if (agent.equals(String.valueOf(cUser.getOrganizationId()))) {
                    //todo 代理商短信认证
                    CuserAgent cuserAgent = new CuserAgent();
                    cuserAgent.setUserid(cUser.getId().toString());
                    List<CuserAgent> cuserAgents = (List<CuserAgent>) iCuserAgentService.configExample(pageProcessAll(1), cuserAgent).get("list");
                    if (cuserAgents.size() > 0) {
                        Agent agentPhone = agentService.getAgentById(cuserAgents.get(0).getAgentid());
                        String code = redisService.getValue(agentPhone.getAgLegalMobile());
                        if (org.apache.commons.lang3.StringUtils.isNotBlank(code)) {
                            return new ResponseInfo("0", "发送太频繁，请稍后再试");
                        }
                        RequestInfo requestInfo = new RequestInfo();
                        requestInfo.setMobileNos(agentPhone.getAgLegalMobile());
                        code = createRandom(true, 6);
                        requestInfo.setTemplateId(code);
                        requestInfo.setParams(agentPhone.getAgName() + "," + code);
                        sendSMSService.send(requestInfo);  //发送短信
                        return new ResponseInfo("1", "success");
                    }
                } else {
                    return new ResponseInfo("0", "您不是代理商");
                }
            }

        } catch (Exception e) {
            logger.error("toRepay sendSMSService error", e);
        }
        return new ResponseInfo("0", "error");
    }


    /**
     * 创建指定数量的随机字符串
     *
     * @param numberFlag 是否是数字
     * @param length
     * @return
     */
    public static String createRandom(boolean numberFlag, int length) {
        String retStr = "";
        String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
        int len = strTable.length();
        boolean bDone = true;
        do {
            retStr = "";
            int count = 0;
            for (int i = 0; i < length; i++) {
                double dblR = Math.random() * len;
                int intR = (int) Math.floor(dblR);
                char c = strTable.charAt(intR);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                retStr += strTable.charAt(intR);
            }
            if (count >= 2) {
                bDone = false;
            }
        } while (bDone);

        return retStr;
    }

}
