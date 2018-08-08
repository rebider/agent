package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.enumc.AttachmentRelType;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.common.util.ThreadPool;
import com.ryx.credit.commons.utils.DigestUtils;
import com.ryx.credit.dao.CuserAgentMapper;
import com.ryx.credit.dao.agent.AgentMapper;
import com.ryx.credit.dao.agent.AttachmentRelMapper;
import com.ryx.credit.dao.agent.BusActRelMapper;
import com.ryx.credit.pojo.admin.COrganization;
import com.ryx.credit.pojo.admin.CuserAgent;
import com.ryx.credit.pojo.admin.CuserAgentExample;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.UserVo;
import com.ryx.credit.service.ICuserAgentService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.AgentService;
import com.ryx.credit.service.dict.DepartmentService;
import com.ryx.credit.service.dict.IdService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 代理商基础信息管理服务类
 * Created by cx on 2018/5/22.
 */
@Service("agentService")
public class AgentServiceImpl implements AgentService {

    private static Logger logger = LoggerFactory.getLogger(AgentServiceImpl.class);

    @Autowired
    private AttachmentRelMapper attachmentRelMapper;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private BusActRelMapper busActRelMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private ICuserAgentService iCuserAgentService;
    @Autowired
    private CuserAgentMapper cuserAgentMapper;

    /**
     * 查询代理商信息
     *
     * @param page
     * @param agent
     * @return
     */
    @Override
    public PageInfo queryAgentList(PageInfo page, Agent agent) {

        AgentExample example = new AgentExample();
        AgentExample.Criteria c = example.or();
        if (agent != null && StringUtils.isNotEmpty(agent.getAgUniqNum())) {
            c.andAgUniqNumEqualTo(agent.getAgUniqNum());
        }
        if (agent != null && StringUtils.isNotEmpty(agent.getAgName())) {
            c.andAgNameLike("%" + agent.getAgName() + "%");
        }
        if (agent != null && StringUtils.isNotEmpty(agent.getAgDocPro())) {
            c.andAgDocProEqualTo(agent.getAgDocPro());
        }
        if (agent != null && StringUtils.isNotEmpty(agent.getAgDocDistrict())) {
            c.andAgDocDistrictEqualTo(agent.getAgDocDistrict());
        }
        if (agent != null && StringUtils.isNotEmpty(agent.getAgStatus())) {
            c.andAgStatusEqualTo(agent.getAgStatus());
        }
        if (agent != null && StringUtils.isNotEmpty(agent.getAgZbh())) {
            c.andAgZbhLike("%" + agent.getAgZbh() + "%");
        }
        c.andStatusEqualTo(Status.STATUS_1.status);
        int count = agentMapper.countByExample(example);
        example.setOrderByClause(" c_utime desc ");
        example.setPage(new Page(page.getFrom(), page.getPagesize()));
        List<Agent> list = agentMapper.selectByExample(example);
//        for (Agent agent1 : list) {
//            if(StringUtils.isNotEmpty(agent1.getAgDocPro())) {
//                COrganization organization = departmentService.getById(agent1.getAgDocPro());
//                if(null!=organization) {
//                    agent1.setAgDocProTemp(organization.getName());
//                }
//            }
//            if(StringUtils.isNotEmpty(agent1.getAgDocDistrict())) {
//                COrganization organization = departmentService.getById(agent1.getAgDocDistrict());
//                if(null!=organization) {
//                    agent1.setAgDocDistrictTemp(organization.getName());
//                }
//            }
//        }
        page.setRows(list);
        page.setTotal(count);
        return page;
    }


    /**
     * 代理商新曾
     *
     * @param agent
     * @param attrId
     * @return
     * @throws ProcessException
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public Agent insertAgent(Agent agent, List<String> attrId) throws ProcessException {
        if (agent == null) {
            logger.info("代理商添加:{}", "代理商信息为空");
            throw new ProcessException("代理商信息为空");
        }
        if (StringUtils.isEmpty(agent.getcUser())) {
            logger.info("代理商添加:{}", "操作用户不能为空");
            throw new ProcessException("操作用户不能为空");
        }
        if (StringUtils.isEmpty(agent.getAgName())) {
            logger.info("代理商添加:{}", "代理商名称不能为空");
            throw new ProcessException("代理商名称不能为空");
        }
//        if(StringUtils.isEmpty(agent.getAgBusLic())){
//            logger.info("代理商添加:{}","营业执照不能为空");
//            throw new ProcessException("营业执照不能为空");
//        }
        Date date = Calendar.getInstance().getTime();
        agent.setStatus(Status.STATUS_1.status);
        agent.setVersion(Status.STATUS_1.status);
        agent.setcIncomStatus(Status.STATUS_0.status);
        agent.setAgStatus(AgStatus.Create.name());
        agent.setcTime(date);
        agent.setcUtime(date);
        agent.setId(idService.genId(TabId.a_agent));
        if (1 == agentMapper.insertSelective(agent)) {
            if (attrId != null) {
                for (String s : attrId) {
                    if (StringUtils.isEmpty(s)) continue;
                    AttachmentRel record = new AttachmentRel();
                    record.setAttId(s);
                    record.setSrcId(agent.getId());
                    record.setcUser(agent.getcUser());
                    record.setcTime(agent.getcTime());
                    record.setStatus(Status.STATUS_1.status);
                    record.setBusType(AttachmentRelType.Agent.name());
                    record.setId(idService.genId(TabId.a_attachment_rel));
                    if (1 != attachmentRelMapper.insertSelective(record)) {
                        logger.info("代理商添加:{}", "添加代理商附件关系失败");
                        throw new ProcessException("添加代理商附件关系失败");
                    }
                }
            }
            logger.info("代理商添加:成功");
            return agent;
        }
        logger.info("代理商添加:{}", "添加代理商失败");
        throw new ProcessException("添加代理商失败");
    }

    @Override
    public Agent getAgentById(String id) {
        return agentMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<CuserAgent>  queryByUserId(String userId) {
        CuserAgentExample example  = new CuserAgentExample();
        example.or().andUseridEqualTo(userId).andStatusEqualTo(Status.STATUS_1.status);
        return   cuserAgentMapper.selectByExample(example);
    }

    @Override
    public Agent queryAgentByUserId(String userId) {
        List<CuserAgent>   cas =  queryByUserId(userId);
        for (CuserAgent ca : cas) {
            if(StringUtils.isNotEmpty(ca.getAgentid())) {
                Agent agent = agentMapper.selectByPrimaryKey(ca.getAgentid());
                if(agent!=null)return agent;
            }
        }
        return null;
    }

    @Override
    public AgentResult isAgent(String userId) {
        Agent agent  = queryAgentByUserId(userId);
        return agent==null?AgentResult.fail():AgentResult.ok(agent);
    }

    @Override
    public int updateAgent(Agent agent) {
        return agentMapper.updateByPrimaryKeySelective(agent);
    }


    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public Agent updateAgentVo(Agent agent, List<String> attrs) throws Exception {

        if (null == agent || StringUtils.isEmpty(agent.getId())) {
            throw new ProcessException("代理商信息错误");
        }
        Agent db_agent = getAgentById(agent.getId());
        db_agent.setAgName(agent.getAgName());
        db_agent.setAgNature(agent.getAgNature());
        db_agent.setAgCapital(agent.getAgCapital());
        db_agent.setAgBusLic(agent.getAgBusLic());
        db_agent.setAgBusLicb(agent.getAgBusLicb());
        db_agent.setAgBusLice(agent.getAgBusLice());
        db_agent.setAgLegal(agent.getAgLegal());
        db_agent.setAgLegalCertype(agent.getAgLegalCertype());
        db_agent.setAgLegalCernum(agent.getAgLegalCernum());
        db_agent.setAgLegalMobile(agent.getAgLegalMobile());
        db_agent.setAgHead(agent.getAgHead());
        db_agent.setAgHeadMobile(agent.getAgHeadMobile());
        db_agent.setAgRegAdd(agent.getAgRegAdd());
        db_agent.setAgBusScope(agent.getAgBusScope());
        db_agent.setCloTaxPoint(agent.getCloTaxPoint());
        db_agent.setAgDocPro(agent.getAgDocPro());
        db_agent.setAgDocDistrict(agent.getAgDocDistrict());
        db_agent.setAgRemark(agent.getAgRemark());
        db_agent.setStatus(agent.getStatus());
        if (1 != agentMapper.updateByPrimaryKeySelective(db_agent)) {
            throw new ProcessException("代理商信息更新失败");
        }


        //删除老的附件
        AttachmentRelExample example = new AttachmentRelExample();
        example.or().andBusTypeEqualTo(AttachmentRelType.Agent.name()).andSrcIdEqualTo(db_agent.getId()).andStatusEqualTo(Status.STATUS_1.status);
        List<AttachmentRel> list = attachmentRelMapper.selectByExample(example);
        for (AttachmentRel attachmentRel : list) {
            attachmentRel.setStatus(Status.STATUS_0.status);
            int i = attachmentRelMapper.updateByPrimaryKeySelective(attachmentRel);
            if (1 != i) {
                logger.info("修改代理商附件关系失败");
                throw new ProcessException("更新修改代理商失败");
            }
        }

        //添加新的附件
        if (attrs != null) {
            for (String fileId : attrs) {
                AttachmentRel record = new AttachmentRel();
                record.setAttId(fileId);
                record.setSrcId(db_agent.getId());
                record.setcUser(agent.getcUser());
                record.setcTime(Calendar.getInstance().getTime());
                record.setStatus(Status.STATUS_1.status);
                record.setBusType(AttachmentRelType.Agent.name());
                record.setId(idService.genId(TabId.a_attachment_rel));
                int i = attachmentRelMapper.insertSelective(record);
                if (1 != i) {
                    logger.info("修改代理商附件关系失败");
                    throw new ProcessException("更新修改代理商失败");
                }
            }
        }

        return db_agent;
    }

    /**
     * 根据实例id查询代理商信息
     *
     * @param activId
     * @return
     */
    @Override
    public Agent findAgentByActivId(String activId) {
        if (StringUtils.isBlank(activId)) {
            return null;
        }
        BusActRel busActRel = busActRelMapper.findById(activId);
        if (busActRel == null) {
            return null;
        }
        Agent agent = agentMapper.selectByPrimaryKey(busActRel.getBusId());
        if (agent != null) {
            agent.setAgDocPro(departmentService.getById(agent.getAgDocPro()).getName());
            agent.setAgDocDistrict(departmentService.getById(agent.getAgDocDistrict()).getName());
        }
        return agent;
    }

    /**
     * 生成后台用户
     *
     * @param agentId
     */
    @Override
    public void createBackUserbyAgent(String agentId) {
        ThreadPool.putThreadPool(() -> {
            try {
                Agent agent = getAgentById(agentId);
                UserVo userVoSelect = iUserService.selectByName(agent.getAgName());
                if (userVoSelect != null) {
                    return;
                }
                UserVo userVo = new UserVo();
                String salt = com.ryx.credit.commons.utils.StringUtils.getUUId();
                String pwd = DigestUtils.hashByShiro("md5", redisService.hGet("config", "pass"), salt, 1);
                userVo.setSalt(salt);
                userVo.setPassword(pwd);
                userVo.setLoginName(agent.getAgLegalMobile());
                userVo.setName(agent.getAgName());
                userVo.setOrganizationId(Integer.valueOf(redisService.hGet("config", "org")));
                userVo.setRoleIds(redisService.hGet("config", "role"));
                userVo.setUserType(1);
                userVo.setPhone(agent.getId());
                iUserService.insertByVo(userVo);
                userVo = iUserService.selectByName(userVo.getName());
                CuserAgent cuserAgent = new CuserAgent();
                cuserAgent.setAgentid(agent.getId());
                cuserAgent.setUserid(userVo.getId().toString());
                cuserAgent.setcTime(new Date());
                cuserAgent.setStatus(BigDecimal.ONE);
                cuserAgent.setUserType(BigDecimal.ONE.toString());
                iCuserAgentService.insert(cuserAgent);
                redisService.hSet("agent", String.valueOf(userVo.getId()), agent.getId());
            } catch (Exception e) {
                logger.error("createBackUserbyAgent error {}", agentId, e);
            }

        });
    }

    /**
     * 生成后台用户
     */
    @Override
    public void createBackUserbyAgentByredis() {
        List<CuserAgent> cuserAgents = iCuserAgentService.selectByExample(new CuserAgentExample());
        cuserAgents.forEach((cuserAgent) -> {
            try {
                String agent=  redisService.hGet("agent", String.valueOf(cuserAgent.getUserid()));
                if(StringUtils.isBlank(agent)){
                    redisService.hSet("agent", String.valueOf(cuserAgent.getUserid()), cuserAgent.getAgentid());
                }
            } catch (Exception e) {
                logger.error("createBackUserbyAgentByredis error {}", cuserAgent.getAgentid(), e);
            }
        });

    }
}
