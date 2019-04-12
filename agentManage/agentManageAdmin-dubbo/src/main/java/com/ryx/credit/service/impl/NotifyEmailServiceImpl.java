package com.ryx.credit.service.impl;

import com.ryx.credit.common.enumc.BusActRelBusType;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.ThreadPool;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.COrganizationMapper;
import com.ryx.credit.dao.CUserMapper;
import com.ryx.credit.dao.CuserAgentMapper;
import com.ryx.credit.dao.agent.AgentMapper;
import com.ryx.credit.pojo.admin.COrganization;
import com.ryx.credit.pojo.admin.CUser;
import com.ryx.credit.pojo.admin.CuserAgent;
import com.ryx.credit.pojo.admin.CuserAgentExample;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.pojo.admin.vo.UserVo;
import com.ryx.credit.service.CRoleService;
import com.ryx.credit.service.NotifyEmailService;
import com.ryx.credit.service.agent.BusActRelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/***
 * @Author liudh
 * @Description //TODO 
 * @Date 2019/4/10 16:36
 * @Param
 * @return
 **/
@Service("notifyEmailService")
public class NotifyEmailServiceImpl implements NotifyEmailService {


    private static final String netInUrlsPid = AppConfig.getProperty("netInUrls_pid");
    private static final Logger log = LoggerFactory.getLogger(NotifyEmailServiceImpl.class);

    @Autowired
    private BusActRelService busActRelService;
    @Autowired
    private CRoleService roleService;
    @Autowired
    private COrganizationMapper organizationMapper;
    @Autowired
    private CUserMapper cUserMapper;
    @Autowired
    private CuserAgentMapper userAgentMapper;
    @Autowired
    private AgentMapper agentMapper;


    public void notifyEmail(String groupId,String executionId,String eventName){
        ThreadPool.putThreadPool(() -> {
            if ("create".endsWith(eventName)) {
                try {
                    Thread.sleep(10000L);
                } catch (InterruptedException e) {
                    log.error("待办任务通知:Thread error");
                }
                Set<String> notityEmail = new HashSet<>();
                BusActRel busActRel = busActRelService.findById(executionId);
                if(busActRel==null){
                    log.info("待办任务通知:未找到关联关系表,executionId:{}",executionId);
                    return;
                }
                if(groupId.equals("agent")){
                    log.info("待办任务通知:代理商暂不发送邮件,groupId:{}",groupId);
                    return;
                }
                COrganization cOrganization = new COrganization();
                if(groupId.equals("city")){
                    CUser cUser = cUserMapper.selectById(Long.valueOf(busActRel.getcUser()));
                    //判断是代理商发起还是省区发起的审批
                    COrganization organization = organizationMapper.selectByPrimaryKey(String.valueOf(cUser.getOrganizationId()));
                    if(organization.getCode().equals("agent")){
                        CuserAgentExample cuserAgentExample = new CuserAgentExample();
                        CuserAgentExample.Criteria criteria = cuserAgentExample.createCriteria();
                        criteria.andStatusEqualTo(Status.STATUS_1.status);
                        criteria.andUseridEqualTo(busActRel.getcUser());
                        List<CuserAgent> cuserAgents = userAgentMapper.selectByExample(cuserAgentExample);
                        if(cuserAgents.size()!=1){
                            log.info("待办任务通知:没有该代理商,userId:{}",busActRel.getcUser());
                            return;
                        }
                        Agent agent = agentMapper.selectByPrimaryKey(cuserAgents.get(0).getAgentid());
                        if(null==agent){
                            log.info("待办任务通知:没有该代理商,userId:{}",busActRel.getcUser());
                            return;
                        }
                        cOrganization.setId(Long.parseLong(agent.getAgDocPro()));
                    }else{
                        cOrganization.setId(cUser.getOrganizationId().longValue());
                    }
                }else {
                    cOrganization = organizationMapper.selectByCode(groupId);
                    if(cOrganization==null){
                        log.info("待办任务通知:没有找到部门,groupId:{}",groupId);
                        return;
                    }
                }
                List<UserVo> userVos = cUserMapper.selectUserByOrgId(cOrganization.getId());
                if(userVos.size()==0){
                    log.info("待办任务通知:userVos没有需要通知的人,退出,Org:{}",cOrganization.getId());
                    return;
                }
                for (UserVo userVo : userVos) {
                    if(busActRel.getBusType().equals(BusActRelBusType.Agent.name()) || busActRel.getBusType().equals(BusActRelBusType.Business.name())){
                        Set<String> strings = roleService.selectShiroUrl(userVo.getId(), netInUrlsPid, busActRel.getNetInBusType());
                        //说明有权限收邮件
                        if(strings.size()!=0 && StringUtils.isNotBlank(userVo.getUserEmail())){
                            notityEmail.add(userVo.getUserEmail());
                        }
                    }else{
                        if(StringUtils.isNotBlank(userVo.getUserEmail())){
                            notityEmail.add(userVo.getUserEmail());
                        }
                    }
                }
                if(notityEmail.size()==0){
                    log.info("待办任务通知:notityEmail没有需要通知的人,退出,Org:{}",cOrganization.getId());
                    return;
                }
                StringBuffer sb = new StringBuffer();
                sb.append("<b>"+"您有一条待办任务需处理,如已处理请忽略！：</b>\r\n");
                sb.append("<table border='1' cellspacing='0' cellpadding='0'>");
                sb.append("<tr><th>任务编号</th><th>任务类型</th><th>代理商编号</th><th>代理商名称</th><th>业务编号</th><th>申请时间</th><th>申请人</th></tr>");
                sb.append("<br><br><br>\r\n \r\n");

                String busType = BusActRelBusType.getItemString(busActRel.getBusType());
                sb.append("<tr>");
                sb.append("<td>");
                sb.append(busActRel.getActivId());
                sb.append("</td>");
                sb.append("<td>");
                sb.append(busType);
                sb.append("</td>");
                sb.append("<td>");
                sb.append(busActRel.getAgentId());
                sb.append("</td>");
                sb.append("<td>");
                sb.append(busActRel.getAgentName());
                sb.append("</td>");
                sb.append("<td>");
                sb.append(busActRel.getBusId());
                sb.append("</td>");
                sb.append("<td>");
                sb.append(DateUtil.format(busActRel.getcTime()));
                sb.append("</td>");
                sb.append("<td>");
                CUser cUser = cUserMapper.selectById(Long.valueOf(busActRel.getcUser()));
                sb.append(cUser!=null?cUser.getName():busActRel.getcUser());
                sb.append("</td>");
                sb.append("</tr>");
                sb.append("</table>");

                String title = "[代理商系统]工作流审批任务:"+busType;
                AppConfig.sendEmail(notityEmail.toArray(new String[]{}),sb.toString() ,title);
                log.info("待办任务通知:邮件发送成功,收件人:{},标题:{}",notityEmail.toString(),title);
            } else if ("assignment".endsWith(eventName)) {
                
            } else if ("complete".endsWith(eventName)) {

            }
        });
    }

}
