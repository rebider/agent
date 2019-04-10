package com.ryx.credit.service.impl;

import com.ryx.credit.common.enumc.BusActRelBusType;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.ThreadPool;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.COrganizationMapper;
import com.ryx.credit.dao.CUserMapper;
import com.ryx.credit.dao.agent.BusActRelMapper;
import com.ryx.credit.pojo.admin.COrganization;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.pojo.admin.vo.UserVo;
import com.ryx.credit.service.CRoleService;
import com.ryx.credit.service.NotifyEmailService;
import com.ryx.credit.service.agent.BusActRelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private BusActRelMapper busActRelMapper;
    @Autowired
    private BusActRelService busActRelService;
    @Autowired
    private CRoleService roleService;
    @Autowired
    private COrganizationMapper organizationMapper;
    @Autowired
    private CUserMapper cUserMapper;

    public void notifyEmail(String groupId,String executionId,String eventName){
        ThreadPool.putThreadPool(() -> {
            if ("create".endsWith(eventName)) {
                List<String> notityEmail = new ArrayList<>();
                BusActRel busActRel = busActRelService.findById(executionId);
                COrganization cOrganization = organizationMapper.selectByCode(groupId);
                List<UserVo> userVos = cUserMapper.selectUserByOrgId(cOrganization.getId());
                if(userVos.size()==0){
                    log.info("没有需要通知的人,退出1");
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
                    log.info("没有需要通知的人,退出2");
                    return;
                }
                String agentId = "代理商编号:"+busActRel.getAgentId()+" ";
                String agentName = "代理商名称:"+busActRel.getAgentName()+" ";
                String busId = "业务编号:"+busActRel.getBusId()+" ";
                String msg = "待审批任务信息："+agentId+agentName+busId;
                String title = "代理商系统_工作流审批任务:"+BusActRelBusType.getItemString(busActRel.getBusType());
                AppConfig.sendEmail(notityEmail.toArray(new String[]{}),msg ,title);
            } else if ("assignment".endsWith(eventName)) {


            } else if ("complete".endsWith(eventName)) {

            }

        });
    }
}
