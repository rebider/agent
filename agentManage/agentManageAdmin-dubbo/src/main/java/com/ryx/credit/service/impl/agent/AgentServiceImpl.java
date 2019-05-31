package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.DigestUtils;
import com.ryx.credit.dao.COrganizationMapper;
import com.ryx.credit.dao.CUserMapper;
import com.ryx.credit.dao.CUserRoleMapper;
import com.ryx.credit.dao.CuserAgentMapper;
import com.ryx.credit.dao.agent.*;
import com.ryx.credit.pojo.admin.*;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.UserVo;
import com.ryx.credit.service.ICuserAgentService;
import com.ryx.credit.service.IResourceService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.AgentDataHistoryService;
import com.ryx.credit.service.agent.AgentService;
import com.ryx.credit.service.dict.DepartmentService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import org.apache.commons.collections.FastHashMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 代理商基础信息管理服务类
 * Created by cx on 2018/5/22.
 */
@Service("agentService")
public class AgentServiceImpl implements AgentService {

    private static Logger logger = LoggerFactory.getLogger(AgentServiceImpl.class);

    private final String JURIS_DICTION = AppConfig.getProperty("region_jurisdiction");

    private static final String permission_key="AGENT_LIST_PLATFORM_";

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
    @Autowired
    private CUserMapper cUserMapper;
    @Autowired
    private AttachmentMapper attachmentMapper;
    @Autowired
    private AgentDataHistoryService agentDataHistoryService;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private AgentService agentService;
    @Autowired
    private CUserMapper userMapper;
    @Autowired
    private CUserRoleMapper cUserRoleMapper;
    @Autowired
    private IResourceService iResourceService;


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
//        c.andCUserEqualTo(agent.getcUser());
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

    @Autowired
    private COrganizationMapper organizationMapper;

    /**
     * 省区大区查询
     * @param page
     * @param map
     * @param userId
     * @return
     */
    @Override
    public PageInfo queryAgentAll(Page page, Map map ,Long userId) {
        List<Map<String, Object>> orgCodeRes = iUserService.orgCode(userId);
        if (orgCodeRes == null && orgCodeRes.size() != 1) {
            return null;
        }
        Map<String, Object> stringObjectMap = orgCodeRes.get(0);
        String orgId = String.valueOf(stringObjectMap.get("ORGID"));
        String organizationCode = String.valueOf(stringObjectMap.get("ORGANIZATIONCODE"));
        map.put("orgId", orgId);
        map.put("userId", userId);
        map.put("organizationCode", organizationCode);
        if (null != map) {
            String time = String.valueOf(map.get("time"));
            if (StringUtils.isNotBlank(time)&&!time.equals("null")) {
                String reltime = time.substring(0, 10);
                map.put("time", reltime);
            }
        }
        List<Map> platfromPerm = iResourceService.userHasPlatfromPerm(userId);
        map.put("platfromPerm",platfromPerm);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(agentMapper.queryAgentListView(map,page));
        pageInfo.setTotal(agentMapper.queryAgentListViewCount(map));
        return pageInfo;
    }


    /**
     * 管理人员管理查询，查询自己创建的，查询平台权限分配的
     * @param page
     * @param map
     * @param userId
     * @return
     */
    @Override
    public PageInfo agentManageList(Page page, Map map, Long userId) {
        if (null != map) {
            String time = String.valueOf(map.get("time"));
            if (StringUtils.isNotBlank(time)&&!time.equals("null")) {
                String reltime = time.substring(0, 10);
                map.put("time", reltime);
            }
        }
        List<Map> platfromPerm = iResourceService.userHasPlatfromPerm(userId);
        map.put("platfromPerm",platfromPerm);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(agentMapper.queryAgentListView(map,page));
        pageInfo.setTotal(agentMapper.queryAgentListViewCount(map));

        return pageInfo;
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
    public Agent insertAgent(Agent agent, List<String> attrId,String userId) throws ProcessException {
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

        boolean isHaveYYZZ = false;
        boolean isHaveFRSFZ = false;

        if (1 == agentMapper.insertSelective(agent)) {
            if (attrId != null) {
                for (String s : attrId) {
                    if (StringUtils.isEmpty(s)) continue;

                    Attachment attachment = attachmentMapper.selectByPrimaryKey(s);
                    if(attachment!=null){
                        if(AttDataTypeStatic.YYZZ.code.equals(attachment.getAttDataType()+"")){
                            isHaveYYZZ = true;
                        }
                        if(AttDataTypeStatic.SFZZM.code.equals(attachment.getAttDataType()+"")){
                            isHaveFRSFZ = true;
                        }
                    }
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
            if(!agent.isImport()) {
                if (!isHaveYYZZ) {
                    throw new ProcessException("请添加营业执照附件");
                }
                if (!isHaveFRSFZ) {
                    throw new ProcessException("请添加法人身份证附件");
                }
            }
            //保存数据历史
            if(!agentDataHistoryService.saveDataHistory(agent,agent.getId(), DataHistoryType.BASICS.code,userId,agent.getVersion()).isOK()){
                throw new ProcessException("添加是失败！请重试");
            }

            logger.info("代理商添加:成功");

            try {
                redisService.hSet(RedisCachKey.AGENTINFO.code, FastMap.fastMap(agent.getId(),agent.getAgName()));
                logger.info("代理商添加:成功");
            }catch (Exception e){
                e.printStackTrace();
            }

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
    public List<CuserAgent> queryByUserId(String userId) {
        CuserAgentExample example = new CuserAgentExample();
        example.or().andUseridEqualTo(userId).andStatusEqualTo(Status.STATUS_1.status);
        return cuserAgentMapper.selectByExample(example);
    }

    @Override
    public Agent queryAgentByUserId(String userId) {
        List<CuserAgent> cas = queryByUserId(userId);
        for (CuserAgent ca : cas) {
            if (StringUtils.isNotEmpty(ca.getAgentid())) {
                Agent agent = agentMapper.selectByPrimaryKey(ca.getAgentid());
                if (agent != null) return agent;
            }
        }
        return null;
    }

    @Override
    public AgentResult isAgent(String userId) {
        Agent agent = queryAgentByUserId(userId);
        return agent == null ? AgentResult.fail() : AgentResult.ok(agent);
    }

    @Override
    public int updateAgent(Agent agent) {
        return agentMapper.updateByPrimaryKeySelective(agent);
    }


    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public Agent updateAgentVo(Agent agent, List<String> attrs,String userId) throws Exception {
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
        db_agent.setAgRegArea(agent.getAgRegArea());
        if (1 != agentMapper.updateByPrimaryKeySelective(db_agent)) {
            throw new ProcessException("代理商信息更新失败");
        }else{
            //保存数据历史
            if(!agentDataHistoryService.saveDataHistory(db_agent,db_agent.getId(), DataHistoryType.BASICS.code,userId,agent.getVersion()).isOK()){
                throw new ProcessException("代理商信息更新失败！请重试");
            }
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
        //营业执照和法人身份证附件必须上传
        boolean isHaveYYZZ = false;
        boolean isHaveFRSFZ = false;
        //添加新的附件
        if (attrs != null) {
            for (String fileId : attrs) {

                Attachment attachment = attachmentMapper.selectByPrimaryKey(fileId);
                if(attachment!=null){
                    if(AttDataTypeStatic.YYZZ.code.equals(attachment.getAttDataType()+"")){
                        isHaveYYZZ = true;
                    }
                    if(AttDataTypeStatic.SFZZM.code.equals(attachment.getAttDataType()+"")){
                        isHaveFRSFZ = true;
                    }
                }

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
        if(!isHaveYYZZ){
            throw new ProcessException("请添加营业执照附件");
        }
        if(!isHaveFRSFZ){
            throw new ProcessException("请添加法人身份证附件");
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
                agentService.createUser(agentId);
            } catch (Exception e) {
                logger.error("createBackUserbyAgent error {}", agentId, e);
            }

        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void createUser(String agentId)throws Exception{
        Agent agent = getAgentById(agentId);
        List<UserVo>  userVoSelect = cUserMapper.selectListByLogin(agent.getAgUniqNum());
        if (userVoSelect.size()>0) {
            return;
        }
        UserVo userVo = new UserVo();
        String salt = com.ryx.credit.commons.utils.StringUtils.getUUId();
        String pwd = DigestUtils.hashByShiro("md5", redisService.hGet("config", "pass"), salt, 1);
        userVo.setSalt(salt);
        userVo.setPassword(pwd);
        userVo.setName(agent.getAgName());
        userVo.setLoginName(agent.getId());
        userVo.setOrganizationId(Integer.valueOf(redisService.hGet("config", "org")));
        userVo.setRoleIds(redisService.hGet("config", "role"));
        userVo.setUserType(1);
        userVo.setPhone(agent.getId());
        iUserService.insertByVo(userVo);


        List<UserVo>  list_db = cUserMapper.selectListByLogin(agent.getId());
        UserVo cUser = new UserVo();
        if(list_db.size()>0){
            cUser = list_db.get(0);
        }
        CuserAgent cuserAgent = new CuserAgent();
        cuserAgent.setAgentid(agent.getId());
        cuserAgent.setUserid(cUser.getId().toString());
        cuserAgent.setcTime(new Date());
        cuserAgent.setStatus(BigDecimal.ONE);
        cuserAgent.setUserType(BigDecimal.ONE.toString());
        cuserAgent.setVersion(BigDecimal.ONE);
        iCuserAgentService.insert(cuserAgent);
        redisService.hSet("agent", String.valueOf(cUser.getId()), agent.getId());
    }


    /**
     * 生成后台用户
     */
    @Override
    public void createBackUserbyAgentByredis() {
        List<CuserAgent> cuserAgents = iCuserAgentService.selectByExample(new CuserAgentExample());
        cuserAgents.forEach((cuserAgent) -> {
            try {
                String agent = redisService.hGet("agent", String.valueOf(cuserAgent.getUserid()));
                if (StringUtils.isBlank(agent)) {
                    redisService.hSet("agent", String.valueOf(cuserAgent.getUserid()), cuserAgent.getAgentid());
                }
            } catch (Exception e) {
                logger.error("createBackUserbyAgentByredis error {}", cuserAgent.getAgentid(), e);
            }
        });

    }

    @Override
    public int updateByPrimaryKeySelective(Agent record) {
        return agentMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public AgentResult checkAgentIsIn(String agentId){
        AgentBusInfoExample example = new AgentBusInfoExample();
        example.or().andAgentIdEqualTo(agentId).andStatusEqualTo(Status.STATUS_1.status);
        List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(example);
        for (AgentBusInfo agentBusInfo : agentBusInfos) {
            //如果是pos 启用状态 有效状态就可激活
            if(agentBusInfo.getBusStatus().compareTo(Status.STATUS_1.status)==0){
                 return AgentResult.ok();
            }

        }
        return AgentResult.fail();
    }

    /**
     * 查看自己下的代理商
     * @param page
     * @param agent
     * @return
     */
    @Override
    public PageInfo queryAgentTierList(Page page, Agent agent, Long userId,String dataType) {

        List<Map<String, Object>> orgCodeRes = iUserService.orgCode(userId);
        if(orgCodeRes==null && orgCodeRes.size()!=1){
            return null;
        }


        Map<String, Object> stringObjectMap = orgCodeRes.get(0);
        String orgId = String.valueOf(stringObjectMap.get("ORGID"));
        String organizationCode = String.valueOf(stringObjectMap.get("ORGANIZATIONCODE"));
        Map<String,Object> reqMap = new HashMap<>();
        //省区大区查看自己的代理商 部门权限
        if(StringUtils.isNotEmpty(organizationCode) &&
                (organizationCode.contains("north") || organizationCode.contains("south") || organizationCode.contains("beijing")  || organizationCode.contains("city"))) {
            reqMap.put("organizationCode", organizationCode);
        }

        //平台权限
        List<Map> platfromPerm = iResourceService.userHasPlatfromPerm(userId);
        reqMap.put("platfromPerm",platfromPerm);

        reqMap.put("status",Status.STATUS_1.status);
        if(StringUtils.isNotBlank(agent.getAgStatus())){
            reqMap.put("agStatus",agent.getAgStatus());
        }
        if(StringUtils.isNotBlank(agent.getAgUniqNum())){
            reqMap.put("agUniqNum",agent.getAgUniqNum());
        }
        if(StringUtils.isNotBlank(agent.getAgName())){
            reqMap.put("agName",agent.getAgName());
        }
        List<String> agStatusList = new ArrayList<>();
        if(dataType.equals("pass")){
            agStatusList.add(AgStatus.Approved.name());
        }else{
            agStatusList.add(AgStatus.Approving.name());
            agStatusList.add(AgStatus.Approved.name());
        }
        reqMap.put("agStatusList",agStatusList);
        List<Agent> list = agentMapper.queryAgentTierList(reqMap,page);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(list);
        pageInfo.setTotal(agentMapper.queryAgentTierCount(reqMap));
        return pageInfo;
    }

    /**
     * 代理商解冻
     * @param agentId
     * @param cUser
     * @return
     * @throws Exception
     */
    @Override
    public AgentResult agentUnfreeze(String agentId, String cUser) throws Exception {
        if (StringUtils.isBlank(agentId)) {
            throw new MessageException("数据ID为空！");
        }
        Agent agent = agentMapper.selectByPrimaryKey(agentId);
        agent.setFreestatus(FreeStatus.JD.getValue());
        agent.setcUtime(new Date());
        agent.setcUser(cUser);
        if (1 != agentMapper.updateByPrimaryKeySelective(agent)) {
            throw new MessageException("合并数据处理失败！");
        }
        return AgentResult.ok();
    }

    @Override
    public List<Agent> getListByORGAndId(Map<String,String> map){
        AgentExample example = new AgentExample();
        AgentExample.Criteria c = example.createCriteria();
        if(StringUtils.isNotBlank(map.get("id"))){
            c.andIdEqualTo(map.get("id"));
        }
        if(StringUtils.isNotBlank(map.get("orgId"))){   //所屬省區
            c.andAgDocProEqualTo(map.get("orgId"));
        }
        if(StringUtils.isNotBlank(map.get("docDistrict"))){ //所屬大區
            c.andAgDocDistrictEqualTo(map.get("docDistrict"));
        }
        c.andStatusEqualTo(Status.STATUS_1.status);
        List<Agent> list = agentMapper.selectByExample(example);
        return list;
    }




    @Override
    public AgentResult createAgentAccount() {
        try {
            String is_create_account_begindate = redisService.hGet("config", "is_create_account_begindate");
            String is_create_account = redisService.hGet("config", "is_create_account");
            if(com.ryx.credit.commons.utils.StringUtils.isBlank(is_create_account_begindate) || com.ryx.credit.commons.utils.StringUtils.isBlank(is_create_account) || !is_create_account.equals("1")){
                return AgentResult.fail("开关未开启");
            }
            logger.info("启动代理商开户任务");
            AgentExample example = new AgentExample();
            example.or().andAgStatusEqualTo(AgStatus.Approved.name())
                    .andStatusEqualTo(Status.STATUS_1.status)
                    .andCTimeGreaterThan(DateUtil.format(is_create_account_begindate,"yyyy-MM-dd"));
            List<Agent> agents = agentMapper.selectByExample(example);

            for (Agent agent : agents) {
                UserVo userVo_q = new UserVo();
                userVo_q.setLoginName(agent.getId());
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
                        if(com.ryx.credit.commons.utils.StringUtils.isNotBlank(ro)) {
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
                userVo.setLoginName(agent.getId());
                userVo.setOrganizationId(Integer.valueOf(redisService.hGet("config", "org")));
                userVo.setRoleIds(redisService.hGet("config", "role"));
                userVo.setUserType(1);
                userVo.setPhone(agent.getId());
                iUserService.insertByVo(userVo);
                List<UserVo>  list_db = userMapper.selectListByLogin(agent.getId());
                UserVo cUser = new UserVo();
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
            return AgentResult.ok();
        } catch (Exception e) {
            logger.error("创建账户失败",e);
            return AgentResult.fail(e.getLocalizedMessage());
        }
    }

    @Override
    public Agent getAgentByName(String name) {
        AgentExample agentExample = new AgentExample();
        AgentExample.Criteria criteria1 = agentExample.createCriteria();
        criteria1.andStatusEqualTo(Status.STATUS_1.status);
        criteria1.andAgNameEqualTo(name);
        List<Agent> agents = agentMapper.selectByExample(agentExample);
        if(agents.size()!=0) {
            Agent agent = agents.get(0);
            return agent;
        }
        return null;
    }
}
