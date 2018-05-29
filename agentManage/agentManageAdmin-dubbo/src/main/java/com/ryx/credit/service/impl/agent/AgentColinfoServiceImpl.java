package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.AttachmentRelType;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AgentColinfoMapper;
import com.ryx.credit.dao.agent.AgentColinfoRelMapper;
import com.ryx.credit.dao.agent.AttachmentRelMapper;
import com.ryx.credit.pojo.admin.agent.AgentColinfo;
import com.ryx.credit.pojo.admin.agent.AgentColinfoRel;
import com.ryx.credit.pojo.admin.agent.AttachmentRel;
import com.ryx.credit.service.dict.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by cx on 2018/5/28.
 */
@Service("agentColinfoService")
public class AgentColinfoServiceImpl implements com.ryx.credit.service.agent.AgentColinfoService {


    private static Logger logger = LoggerFactory.getLogger(AgentColinfoServiceImpl.class);
    @Autowired
    private IdService idService;
    @Autowired
    private AgentColinfoMapper agentColinfoMapper;
    @Autowired
    private AgentColinfoRelMapper agentColinfoRelMapper;
    @Autowired
    private AttachmentRelMapper attachmentRelMapper;


    /**
     * 代理商入网添加代理商交款项
     * @param ac
     * @param att
     * @return
     * @throws ProcessException
     */
    @Override
    public AgentColinfo agentColinfoInsert(AgentColinfo ac, List<String> att)throws ProcessException{
        if(StringUtils.isEmpty(ac.getcUser())){
            throw new ProcessException("操作人不能为空");
        }
        if(StringUtils.isEmpty(ac.getAgentId())){
            throw new ProcessException("代理商ID不能为空");
        }
        if(StringUtils.isEmpty(ac.getCloBank())){
            throw new ProcessException("收款开户行不能为空");
        }
        if(StringUtils.isEmpty(ac.getCloRealname())){
            throw new ProcessException("收款账户名不能为空");
        }
        if(StringUtils.isEmpty(ac.getCloBankAccount())){
            throw new ProcessException("收款账号不能为空");
        }
        if(StringUtils.isEmpty(ac.getCloType())){
            throw new ProcessException("收款账户类型不能为空");
        }
        Date d = Calendar.getInstance().getTime();
        ac.setcTime(d);
        ac.setcUtime(d);
        ac.setStatus(Status.STATUS_1.status);
        ac.setVarsion(Status.STATUS_1.status);
        ac.setId(idService.genId(TabId.a_agent_colinfo));
        if(att!=null) {
            for (String s : att) {
                if (org.apache.commons.lang.StringUtils.isEmpty(s)) continue;
                AttachmentRel record = new AttachmentRel();
                record.setAttId(s);
                record.setSrcId(ac.getId());
                record.setcUser(ac.getcUser());
                record.setcTime(ac.getcTime());
                record.setStatus(Status.STATUS_1.status);
                record.setBusType(AttachmentRelType.Agent.name());
                record.setId(idService.genId(TabId.a_attachment_rel));
                if (1 != attachmentRelMapper.insertSelective(record)) {
                    logger.info("收款账号添加:{}", "收款账号添加附件关系失败");
                    throw new ProcessException("收款账号添加附件关系失败");
                }

            }
        }
        if(1!=agentColinfoMapper.insertSelective(ac)){
            logger.info("收款账号添加:{}", "收款账号添加失败");
            throw new ProcessException("收款账号添加失败");
        }
        return ac;
    }


}