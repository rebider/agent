package com.ryx.credit.task;

import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.commons.utils.DigestUtils;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.CUserMapper;
import com.ryx.credit.dao.CUserRoleMapper;
import com.ryx.credit.dao.agent.AgentMapper;
import com.ryx.credit.pojo.admin.CUser;
import com.ryx.credit.pojo.admin.CUserRole;
import com.ryx.credit.pojo.admin.CuserAgent;
import com.ryx.credit.pojo.admin.CuserAgentExample;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.AgentExample;
import com.ryx.credit.pojo.admin.vo.UserVo;
import com.ryx.credit.service.ICuserAgentService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.impl.agent.AgentServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 作者：cx
 * 时间：2019/2/1
 * 描述：
 */
@Component
public class AgentLoginAccountCreateTask {
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private CUserMapper userMapper;
    @Autowired
    private CUserRoleMapper cUserRoleMapper;
    @Autowired
    private ICuserAgentService iCuserAgentService;
    private static Logger logger = LoggerFactory.getLogger(AgentServiceImpl.class);

    /**
     * 每个60秒一次
     */
    @Scheduled(fixedDelay = 60 * 1000 )
    public void createAgentAccount(){
        try {
            String is_create_account_begindate = redisService.hGet("config", "is_create_account_begindate");
            String is_create_account = redisService.hGet("config", "is_create_account");
            if(StringUtils.isBlank(is_create_account_begindate) || StringUtils.isBlank(is_create_account) || !is_create_account.equals("1")){
                return;
            }
            logger.info("启动代理商开户任务");
            AgentExample example = new AgentExample();
            example.or().andAgStatusEqualTo(AgStatus.Approved.name())
                    .andStatusEqualTo(Status.STATUS_1.status)
                    .andCTimeGreaterThan(DateUtil.format(is_create_account_begindate,"yyyy-MM-dd"));
            List<Agent> agents = agentMapper.selectByExample(example);

            for (Agent agent : agents) {
                UserVo userVo_q = new UserVo();
                userVo_q.setLoginName(agent.getAgUniqNum());
                List<CUser>  userVoSelect = iUserService.selectByLoginName(userVo_q);
                if (userVoSelect != null && userVoSelect.size()>0) {
                    CUser cUser = userVoSelect.get(0);
                    logger.info("代理商已经有账户不进行开户{}",agent.getAgUniqNum());
                    CuserAgentExample cuserAgentExample = new CuserAgentExample();
                    cuserAgentExample.or().andAgentidEqualTo(agent.getId()).andStatusEqualTo(BigDecimal.ONE);
                    List<CuserAgent> cuserAgentlist = iCuserAgentService.selectByExample(cuserAgentExample);
                    if(cuserAgentlist.size()==0){
                        logger.info("代理商进行没有关系户，进行开户{}",agent.getAgUniqNum());
                        CuserAgent cuserAgent = new CuserAgent();
                        cuserAgent.setAgentid(agent.getId());
                        cuserAgent.setUserid(cUser.getId().toString());
                        cuserAgent.setcTime(new Date());
                        cuserAgent.setStatus(BigDecimal.ONE);
                        cuserAgent.setUserType(BigDecimal.ONE.toString());
                        iCuserAgentService.insert(cuserAgent);
                        redisService.hSet("agent", String.valueOf(cUser.getId()), agent.getId());
                    }
                    List<CUserRole> roles = cUserRoleMapper.selectByUserId(cUser.getId());
                    if(roles.size()==0){
                        String ro = redisService.hGet("config", "role");
                        if(StringUtils.isNotBlank(ro)) {
                            String[] roles_array = ro.split(",");
                            CUserRole userRole = new CUserRole();
                            for (String s : roles_array) {
                                userRole.setUserId(cUser.getId());
                                userRole.setRoleId(Long.valueOf(s));
                                cUserRoleMapper.insert(userRole);
                                logger.info("代理商进行任务开户添加角色{}：{}",agent.getAgUniqNum(),s);
                            }
                        }
                    }
                   continue;
                }
                logger.info("代理商进行任务开户{}",agent.getAgUniqNum());
                UserVo userVo = new UserVo();
                String salt = com.ryx.credit.commons.utils.StringUtils.getUUId();
                String pwd = DigestUtils.hashByShiro("md5", redisService.hGet("config", "pass"), salt, 1);
                userVo.setSalt(salt);
                userVo.setPassword(pwd);
                userVo.setName(agent.getAgName());
                userVo.setLoginName(agent.getAgUniqNum());
                userVo.setOrganizationId(Integer.valueOf(redisService.hGet("config", "org")));
                userVo.setRoleIds(redisService.hGet("config", "role"));
                userVo.setUserType(1);
                userVo.setPhone(agent.getId());
                iUserService.insertByVo(userVo);
                List<CUser>  list_db = userMapper.selectListByLogin(agent.getAgUniqNum());
                CUser cUser = new CUser();
                if(list_db.size()>0){
                    cUser = list_db.get(0);
                }
                CuserAgent cuserAgent = new CuserAgent();
                cuserAgent.setAgentid(agent.getId());
                cuserAgent.setUserid(cUser.getId().toString());
                cuserAgent.setcTime(new Date());
                cuserAgent.setStatus(BigDecimal.ONE);
                cuserAgent.setUserType(BigDecimal.ONE.toString());
                iCuserAgentService.insert(cuserAgent);
                redisService.hSet("agent", String.valueOf(cUser.getId()), agent.getId());
                logger.info("代理商进行任务开户成功{}",agent.getAgUniqNum());
            }

        } catch (Exception e) {
            logger.error("创建账户失败",e);
        }
    }
}
