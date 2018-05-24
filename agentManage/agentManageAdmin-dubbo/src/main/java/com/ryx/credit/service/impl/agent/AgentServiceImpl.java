package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.enumc.AttachmentRelType;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.dao.agent.AgentMapper;
import com.ryx.credit.dao.agent.AttachmentRelMapper;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.AgentExample;
import com.ryx.credit.pojo.admin.agent.AttachmentRel;
import com.ryx.credit.service.agent.AgentService;
import com.ryx.credit.service.dict.IdService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 代理商基础信息管理服务类
 * Created by cx on 2018/5/22.
 */
@Service("agentService")
public class AgentServiceImpl implements  AgentService {

    private static Logger logger = LoggerFactory.getLogger(AgentServiceImpl.class);

    @Autowired
    private AttachmentRelMapper attachmentRelMapper;

    @Autowired
    private AgentMapper agentMapper;

    @Autowired
    private IdService idService;


    /**
     * 查询代理商信息
     * @param page
     * @param agent
     * @return
     */
    @Override
    public PageInfo queryAgentList(PageInfo page, Agent agent){
        AgentExample example  = new AgentExample();
        AgentExample.Criteria c = example.or();
        if(agent!=null && StringUtils.isNotEmpty(agent.getAgUniqNum())) {
            c.andAgUniqNumEqualTo(agent.getAgUniqNum());
        }
        if(agent!=null && StringUtils.isNotEmpty(agent.getAgName())) {
            c.andAgNameLike("%"+agent.getAgName()+"%");
        }
        if(agent!=null && StringUtils.isNotEmpty(agent.getAgDocPro())) {
            c.andAgDocProEqualTo(agent.getAgDocPro());
        }
        if(agent!=null && StringUtils.isNotEmpty(agent.getAgDocDistrict())) {
            c.andAgDocDistrictEqualTo(agent.getAgDocDistrict());
        }
        if(agent!=null && StringUtils.isNotEmpty(agent.getAgStatus())) {
            c.andAgStatusEqualTo(agent.getAgStatus());
        }
        if(agent!=null && StringUtils.isNotEmpty(agent.getAgZbh())) {
            c.andAgZbhLike("%"+agent.getAgZbh()+"%");
        }
        c.andStatusEqualTo(Status.STATUS_1.status);
        int count = agentMapper.countByExample(example);
        example.setOrderByClause(" c_utime desc ");
        example.setPage(new Page(page.getFrom(),page.getPagesize()));
        List<Agent> list = agentMapper.selectByExample(example);
        page.setRows(list);
        page.setTotal(count);
        return page;
    }


    /**
     * 代理商新曾
     * @param agent
     * @param attrId
     * @return
     * @throws ProcessException
     */
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public Agent insertAgent(Agent agent, List<String> attrId) throws ProcessException {
        if(agent==null){
            logger.info("代理商添加:{}","代理商信息为空");
            throw new ProcessException("代理商信息为空");
        }
        if(StringUtils.isEmpty(agent.getcUser())){
            logger.info("代理商添加:{}","操作用户不能为空");
            throw new ProcessException("操作用户不能为空");
        }
        if(StringUtils.isEmpty(agent.getAgName())){
            logger.info("代理商添加:{}","代理商名称不能为空");
            throw new ProcessException("代理商名称不能为空");
        }
        if(StringUtils.isEmpty(agent.getAgBusLic())){
            logger.info("代理商添加:{}","营业执照不能为空");
            throw new ProcessException("营业执照不能为空");
        }
        Date date = Calendar.getInstance().getTime();
        agent.setStatus(Status.STATUS_1.status);
        agent.setVersion(Status.STATUS_1.status);
        agent.setAgStatus(AgStatus.Create.name());
        agent.setcTime(date);
        agent.setcUtime(date);
        agent.setId(idService.genId(TabId.a_agent));
        if(1==agentMapper.insertSelective(agent)){
            for (String s : attrId) {
                if(StringUtils.isEmpty(s))continue;
                AttachmentRel record  = new AttachmentRel();
                record.setAttId(s);
                record.setSrcId(agent.getId());
                record.setcUser(agent.getcUser());
                record.setcTime(agent.getcTime());
                record.setStatus(Status.STATUS_1.status);
                record.setBusType(AttachmentRelType.Agent.name());
                record.setId(idService.genId(TabId.a_attachment_rel));
                if(1!=attachmentRelMapper.insertSelective(record)){
                    logger.info("代理商添加:{}","添加代理商附件关系失败");
                    throw new ProcessException("添加代理商附件关系失败");
                }
            }
            logger.info("代理商添加:成功");
            return agent;
        }
        logger.info("代理商添加:{}","添加代理商失败");
        throw new ProcessException("添加代理商失败");
    }

    @Override
    public Agent getAgentById(String id) {
        return agentMapper.selectByPrimaryKey(id);
    }
}
