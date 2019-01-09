package com.ryx.credit.service.impl.agent;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.*;
import com.ryx.credit.pojo.admin.agent.AgentMerge;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.pojo.admin.vo.AgentNotifyVo;
import com.ryx.credit.profit.pojo.PAgentMerge;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.*;
import com.ryx.credit.dao.CUserMapper;
import com.ryx.credit.dao.CuserAgentMapper;
import com.ryx.credit.pojo.admin.CuserAgent;
import com.ryx.credit.pojo.admin.CuserAgentExample;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/***
 * 代理商合并
 * @Author liudh
 * @Description //TODO 
 * @Date 2019/1/7 16:24
 * @Param
 * @return
 **/
@Service("agentMergeService")
public class AgentMergeServiceImpl implements AgentMergeService {

    private static Logger logger = LoggerFactory.getLogger(AgentMergeServiceImpl.class);
    @Autowired
    private AgentMergeBusInfoMapper agentMergeBusInfoMapper;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private CUserMapper cUserMapper;
    @Autowired
    private CuserAgentMapper cUserAgentMapper;
    @Autowired
    private AgentMergeMapper agentMergeMapper;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private BusActRelMapper busActRelMapper;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private IdService idService;
    @Autowired
    private AgentEnterService agentEnterService;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private TaskApprovalService taskApprovalService;
    @Autowired
    private PlatFormMapper platFormMapper;
    @Autowired
    private AgentBusinfoService agentBusinfoService;
    @Autowired
    private ApaycompService apaycompService;
    @Autowired
    private AgentColinfoService agentColinfoService;
    @Autowired
    private AgentNotifyService agentNotifyService;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private AgentService agentService;
    @Autowired
    private AgentPlatFormSynMapper agentPlatFormSynMapper;


    /**
     * 合并列表
     * @param agentMerge
     * @param page
     * @param dataRole
     * @param userId
     * @return
     */
    @Override
    public PageInfo selectAgentMergeList(AgentMerge agentMerge, Page page, String dataRole, Long userId) {
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("status", Status.STATUS_1.status);
        if (StringUtils.isNotBlank(agentMerge.getId())) {
            reqMap.put("id", agentMerge.getId());
        }
        if (StringUtils.isNotBlank(agentMerge.getMainAgentId())) {
            reqMap.put("mainAgentId", agentMerge.getMainAgentId());
        }
        if (StringUtils.isNotBlank(agentMerge.getMainAgentName())) {
            reqMap.put("mainAgentName", agentMerge.getMainAgentName());
        }
        if (null != agentMerge.getCloReviewStatus()) {
            reqMap.put("cloReviewStatus", agentMerge.getCloReviewStatus());
        }
        List<Map<String,Object>> agentMergeList = agentMergeMapper.selectAgentMergeList(reqMap, page);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(agentMergeList);
        pageInfo.setTotal(agentMergeMapper.selectAgentMergeCount(reqMap));
        return pageInfo;
    }

    /**
     * 保存数据
     * @param agentMerge
     * @param cUser
     * @param saveFlag
     * @return
     * @throws Exception
     */
    @Override
    public AgentResult saveAgentMerge(AgentMerge agentMerge, String[] busType, String cUser, String saveFlag) throws Exception {
        if (StringUtils.isBlank(cUser)) {
            logger.info("代理商合并提交，操作用户为空:{}", cUser);
            return AgentResult.fail("代理商合并提交，操作用户为空！");
        }
        try {
            if (saveFlag.equals(SaveFlag.TJSP.getValue())) {
                agentMerge.setCloReviewStatus(AgStatus.Approving.status);
            } else {
                agentMerge.setCloReviewStatus(AgStatus.Create.status);
            }
            String mergeId = idService.genId(TabId.A_AGENT_MERGE);
            agentMerge.setId(mergeId);
            agentMerge.setcTime(new Date());
            agentMerge.setuTime(new Date());
            agentMerge.setcUser(cUser);
            agentMerge.setuUser(cUser);
            agentMerge.setStatus(Status.STATUS_1.status);
            agentMerge.setVersion(Status.STATUS_1.status);
            if (1 != agentMergeMapper.insertSelective(agentMerge)) {
                logger.info("代理商合并提交审批，新增数据失败:{}", cUser);
                throw new MessageException("代理商合并提交审批，新增数据失败！");
            }
            if (saveFlag.equals(SaveFlag.TJSP.getValue())) {
                startAgentMergeActivity(mergeId, cUser,true);
            }
            return AgentResult.ok();
        } catch (MessageException e) {
            e.printStackTrace();
            throw new MessageException(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("新增数据失败！");
        }
    }

    /**
     * 提交数据并审批
     * @param id
     * @param cUser
     * @param isSave 是否已保存
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult startAgentMergeActivity(String id, String cUser, Boolean isSave) throws Exception {
        if (!isSave) {
            AgentMerge agentMerge = agentMergeMapper.selectByPrimaryKey(id);
            if (agentMerge == null) {
                throw new MessageException("提交审批信息有误！");
            }
            agentMerge.setuUser(cUser);
            agentMerge.setuTime(new Date());
            agentMerge.setCloReviewStatus(AgStatus.Approving.status);
            if (1 != agentMergeMapper.updateByPrimaryKeySelective(agentMerge)) {
                throw new MessageException("提交审批处理失败！");
            }
        }
        Map startPar = agentEnterService.startPar(String.valueOf(cUser));
        if (null == startPar) {
            throw new ProcessException("启动部门参数为空！");
        }
        //启动审批流
        String proceId = activityService.createDeloyFlow(null, "mergeCity1.0", null, null, startPar);
        if (proceId == null) {
            logger.info("代理商合并提交审批，审批流启动失败{}:{}", id, cUser);
            throw new MessageException("审批流启动失败！");
        }
        //代理商业务&工作流关系
        BusActRel record = new BusActRel();
        record.setBusId(id);
        record.setActivId(proceId);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(cUser);
        record.setStatus(Status.STATUS_1.status);
        record.setBusType(BusActRelBusType.MERGE.name());
        record.setActivStatus(AgStatus.Approving.name());
//        record.setAgentId(agentId);
//        Agent agent = agentMapper.selectByPrimaryKey(agentId);
//        if (null != agent) {
//            record.setAgentName(agent.getAgName());
//        }
        if (1 != busActRelMapper.insertSelective(record)) {
            logger.info("代理商合并提交审批，启动审批异常，添加审批关系失败{}:{}", id, proceId);
            throw new MessageException("审批流启动失败：添加审批关系失败！");
        }
        return AgentResult.ok();
    }

    /**
     * 处理任务
     * @param agentVo
     * @param userId
     * @param busId
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentResult approvalAgentMergeTask(AgentVo agentVo, String userId, String busId) throws Exception{
        try {
            if(agentVo.getApprovalResult().equals(ApprovalType.PASS.getValue())){

            }
            AgentResult result = agentEnterService.completeTaskEnterActivity(agentVo,userId);
            if(!result.isOK()){
                logger.error(result.getMsg());
                throw new MessageException("工作流处理任务异常");
            }
        }catch (MessageException e) {
            e.printStackTrace();
            throw new MessageException(e.getMsg());
        }catch (Exception e) {
            e.printStackTrace();
            throw new MessageException("catch工作流处理任务异常!" );
        }
        return AgentResult.ok();
    }

    /**
     * 审批结果监听
     * @param proIns
     * @param agStatus
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult compressAgentMergeActivity(String proIns, BigDecimal agStatus)throws Exception{

        BusActRelExample example = new BusActRelExample();
        example.or().andActivIdEqualTo(proIns).andStatusEqualTo(Status.STATUS_1.status).andActivStatusEqualTo(AgStatus.Approving.name());
        List<BusActRel> list = busActRelMapper.selectByExample(example);
        if (list.size() != 1) {
            logger.info("审批任务结束{}{}，未找到审批中的审批和数据关系", proIns, agStatus);
            throw new MessageException("审批和数据关系有误");
        }
        BusActRel busActRel = list.get(0);
        AgentMerge agentMerge = agentMergeMapper.selectByPrimaryKey(busActRel.getBusId());
        agentMerge.setCloReviewStatus(agStatus);
        agentMerge.setuTime(new Date());
        int i = agentMergeMapper.updateByPrimaryKeySelective(agentMerge);
        if(i!=1){
            logger.info("审批任务结束{}{}，代理商合并更新失败1", proIns, agStatus);
            throw new MessageException("代理商合并更新失败");
        }

        AgentMergeBusInfoExample agentMergeBusInfoExample = new AgentMergeBusInfoExample();
        AgentMergeBusInfoExample.Criteria criteria = agentMergeBusInfoExample.createCriteria();
        criteria.andAgentMargeIdEqualTo(busActRel.getBusId());
        criteria.andMergeStatusEqualTo(MergeStatus.WXS.getValue());
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        List<AgentMergeBusInfo> agentMergeBusInfos = agentMergeBusInfoMapper.selectByExample(agentMergeBusInfoExample);
        if(agentMergeBusInfos.size()==0){
            throw new MessageException("查询合并业务失败");
        }
        for (AgentMergeBusInfo agentMergeBusInfo : agentMergeBusInfos) {
            agentMergeBusInfo.setMergeStatus(MergeStatus.SX.getValue());
            //更新合并业务表合并状态为生效
            int j = agentMergeBusInfoMapper.updateByPrimaryKeySelective(agentMergeBusInfo);
            if(j!=1){
                logger.info("审批任务结束{}{}，代理商合并更新合并状态", proIns, agStatus);
                throw new MessageException("代理商合并更新失败");
            }
            //删除副代理商被合并的平台
            AgentBusInfo agentBusInfo = new AgentBusInfo();
            agentBusInfo.setId(agentMergeBusInfo.getBusId());
            agentBusInfo.setStatus(Status.STATUS_0.status);
            agentBusInfo.setBusStatus(BusinessStatus.pause.status);
            int k = agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfo);
            if(k!=1){
                logger.info("审批任务结束{}{}，代理商合并删除副代理商被合并的平台失败", proIns, agStatus);
                throw new MessageException("代理商合并更新失败");
            }
        }
        //代理商合并之后如果一个业务都没了,基本信息等保留,禁止登陆后台
        AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
        AgentBusInfoExample.Criteria  agentBusInfocriteria = agentBusInfoExample.createCriteria();
        agentBusInfocriteria.andAgentIdEqualTo(agentMerge.getSubAgentId());
        agentBusInfocriteria.andStatusEqualTo(Status.STATUS_1.status);
        List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(agentBusInfoExample);
        if(agentBusInfos.size()==0){
            CuserAgentExample cuserAgentExample = new CuserAgentExample();
            CuserAgentExample.Criteria userAgentCriteria = cuserAgentExample.createCriteria();
            userAgentCriteria.andAgentidEqualTo(agentMerge.getSubAgentId());
            List<CuserAgent> cuserAgents = cUserAgentMapper.selectByExample(cuserAgentExample);
            if(cuserAgents.size()!=1){
                throw new MessageException("代理商信息有误");
            }
            CuserAgent cuserAgent = cuserAgents.get(0);
            int l = cUserMapper.updateStatusByPrimaryKey(Long.valueOf(cuserAgent.getUserid()));
            if(l!=1){
                logger.info("审批任务结束{}{}，代理商合并禁止登陆后台失败", proIns, agStatus);
                throw new MessageException("代理商合并更新失败");
            }
        }
        //如果财务填写了，合并到那个被合并代理商的分期订单欠款,同步到分润其他扣款

        //通知业务系统
        updateAgentName(busActRel.getBusId());

        return AgentResult.ok();
    }


    /**
     * 手动更改手刷、POS代理商名称
     * @param busId
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public AgentResult updateAgentName(String busId) throws Exception{
        try {
            AgentMerge agentMerge = agentMergeMapper.selectByPrimaryKey(busId);
            if(agentMerge==null){
                throw new MessageException("未找到合并信息");
            }
            AgentMergeBusInfoExample agentMergeBusInfoExample = new AgentMergeBusInfoExample();
            AgentMergeBusInfoExample.Criteria criteria = agentMergeBusInfoExample.createCriteria();
            criteria.andAgentMargeIdEqualTo(busId);
            criteria.andMergeStatusEqualTo(MergeStatus.WXS.getValue());
            criteria.andStatusEqualTo(Status.STATUS_1.status);
            List<AgentMergeBusInfo> agentMergeBusInfos = agentMergeBusInfoMapper.selectByExample(agentMergeBusInfoExample);
            if(agentMergeBusInfos.size()==0){
                throw new MessageException("查询合并业务失败");
            }
            String agentName = agentMerge.getMainAgentName() + "(" + agentMerge.getSubAgentName() + ")";

            List<String> mPosOrgList = new ArrayList<>();
            for (AgentMergeBusInfo agentMergeBusInfo : agentMergeBusInfos) {
                String platType = platFormMapper.selectPlatType(agentMergeBusInfo.getBusPlatform());
                if(platType.equals(PlatformType.MPOS.getValue())){
                    mPosOrgList.add(agentMergeBusInfo.getBusNum());
                }
                //调用Pos
                if(platType.equals(PlatformType.MPOS.getValue()) || platType.equals(PlatformType.ZPOS.getValue())){
                    AgentPlatFormSyn record = new AgentPlatFormSyn();
                    AgentNotifyVo agentNotifyVo = new AgentNotifyVo();
                    agentNotifyVo.setOrgName(agentName);
                    AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(agentMergeBusInfo.getBusId());
                    Agent agent = agentMapper.selectByPrimaryKey(agentBusInfo.getAgentId());
                    agentNotifyVo.setUniqueId(agentBusInfo.getId());
                    agentNotifyVo.setAgHeadMobile(agent.getAgHeadMobile());
                    agentNotifyVo.setOrgName(agent.getAgName());
                    agentNotifyVo.setUseOrgan(agentBusInfo.getBusUseOrgan());
                    agentNotifyVo.setBusPlatform(agentBusInfo.getBusPlatform());
                    agentNotifyVo.setBaseMessage(agent);
                    agentNotifyVo.setBusMessage(agentBusInfo);
                    agentNotifyVo.setHasS0(agentBusInfo.getDredgeS0().equals(new BigDecimal(1))?"0":"1");
                    agentNotifyVo.setLoginName(agentBusInfo.getBusLoginNum());
                    agentNotifyVo.setBusiType(platType.equals(PlatformType.POS.getValue())?"01":"02");
                    Dict dictByValue = dictOptionsService.findDictByValue(DictGroup.AGENT.name(), DictGroup.BUS_TYPE.name(), agentBusInfo.getBusType());
                    agentNotifyVo.setOrgType(dictByValue.getdItemname().contains(OrgType.STR.getContent())?OrgType.STR.getValue():OrgType.ORG.getValue());
                    AgentBusInfo agentParent = null;
                    if(StringUtils.isNotBlank(agentBusInfo.getBusParent())){
                        //取出上级业务
                        agentParent = agentBusInfoMapper.selectByPrimaryKey(agentBusInfo.getBusParent());
                        if(null!=agentParent){
                            agentNotifyVo.setSupDorgId(agentParent.getBusNum());
                        }
                    }
                    String sendJson = JsonUtil.objectToJson(agentNotifyVo);
                    record.setId(idService.genId(TabId.a_agent_platformsyn));
                    record.setNotifyTime(new Date());
                    record.setBusId(busId);
                    record.setPlatformCode(agentMergeBusInfo.getBusPlatform());
                    record.setVersion(Status.STATUS_1.status);
                    record.setcTime(new Date());
                    record.setNotifyStatus(Status.STATUS_0.status);
                    record.setNotifyCount(Status.STATUS_1.status);
                    record.setcUser(agentMergeBusInfo.getcUser());
                    record.setSendJson(sendJson);
                    AgentResult agentResult = agentNotifyService.httpRequestForPos(agentNotifyVo);
                    record.setNotifyJson(String.valueOf(agentResult.getData()));
                    agentPlatFormSynMapper.insert(record);
                }
            }
            //调用手刷接口
            if(mPosOrgList.size()!=0){
                List<AgentBusInfo> agentBusInfoList = agentBusinfoService.agentBusInfoList(agentMerge.getMainAgentId());
                AgentBusInfo busInfo = agentBusInfoList.get(0);
                PayComp payComp = apaycompService.selectById(busInfo.getCloPayCompany());
                AgentColinfo agentColinfo = agentColinfoService.selectByAgentIdAndBusId(agentMerge.getMainAgentId(), busInfo.getId());
                agentColinfo.setAccountId(payComp.getId());
                agentColinfo.setAccountName(payComp.getComName());
                AgentPlatFormSyn record = new AgentPlatFormSyn();
                String id = idService.genId(TabId.a_agent_platformsyn);
                record.setSendJson("agentName:"+agentName+",mPosOrgList:"+mPosOrgList.toString()+",agentColinfo:"+JsonUtil.objectToJson(agentColinfo));
                AgentResult agentResult = mPos_updateAgName(agentName, mPosOrgList, agentColinfo);
                record.setId(id);
                record.setNotifyTime(new Date());
                record.setBusId(busId);
                record.setPlatformCode(busInfo.getBusPlatform());
                record.setVersion(Status.STATUS_1.status);
                record.setcTime(new Date());
                record.setNotifyStatus(Status.STATUS_0.status);
                record.setNotifyCount(Status.STATUS_1.status);
                record.setcUser(busInfo.getcUser());
                record.setNotifyJson(String.valueOf(agentResult.getData()));
                agentPlatFormSynMapper.insert(record);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("代理商合并申请-更改代理商名称异常，activId：{}" + busId);
        }
        return AgentResult.ok();
    }


    /**
     * 通知手刷
     * @param agentName
     * @param mPosOrgList
     * @param agentColinfo
     * @return
     */
    public AgentResult mPos_updateAgName(String agentName, List<String> mPosOrgList, AgentColinfo agentColinfo) {
        HashMap<String,String> map = new HashMap<String,String>();

        map.put("companyname", agentName);//代理商名称
        map.put("batchIds", mPosOrgList.toString());//AG码
        map.put("colinfoMessage", JsonUtil.objectToJson(agentColinfo));//收款账户
        String params = JsonUtil.objectToJson(map);
        logger.info("======mPos_updateAgName:{}",params);
        String res = HttpClientUtil.doPostJson
                (AppConfig.getProperty("busiPlat.upAgName"),params);
        logger.info("======mPos_updateAgName结果:{}",res);
        JSONObject resObj = JSONObject.parseObject(res);
        if(!resObj.get("respCode").equals("000000")){
            logger.error("请求失败！");
            AppConfig.sendEmails("代理商更名失败:"+res,"代理商更名失败");
            return AgentResult.fail(resObj.toString());
        }
        logger.info("代理商更名成功！{}",resObj.get("respMsg"));
        return AgentResult.ok(resObj.toString());
    }

}
