package com.ryx.internet.service.impl;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.CUser;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.*;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.internet.dao.InternetLogoutDetailMapper;
import com.ryx.internet.dao.InternetLogoutMapper;
import com.ryx.internet.dao.OInternetCardMapper;
import com.ryx.internet.pojo.*;
import com.ryx.internet.service.InternetCardLogoutService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/***
 * 流量卡注销
 * @Author liudh
 * @Description //TODO 
 * @Date 2019/11/21 10:14
 * @Param
 * @return
 **/
@Service("internetCardLogoutService")
public class InternetCardLogoutServiceImpl implements InternetCardLogoutService {

    private static Logger log = LoggerFactory.getLogger(InternetCardLogoutServiceImpl.class);
    @Autowired
    private AgentEnterService agentEnterService;
    @Autowired
    private BusActRelService busActRelService;
    @Autowired
    private InternetLogoutMapper internetLogoutMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private OInternetCardMapper internetCardMapper;
    @Autowired
    private InternetLogoutDetailMapper internetLogoutDetailMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private AgentBusinfoService agentBusinfoService;
    @Autowired
    private TaskApprovalService taskApprovalService;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private IUserService iUserService;


    @Override
    public PageInfo internetCardLogoutList(InternetLogout internetLogout, Page page, String agentId, Long userId){
        Map<String, Object> reqMap = new HashMap<>();
        if(StringUtils.isNotBlank(internetLogout.getId())){
            reqMap.put("id",internetLogout.getId());
        }
        if(StringUtils.isNotBlank(agentId)){
            reqMap.put("agentId",agentId);
        }else if(StringUtils.isNotBlank(internetLogout.getAgentId())){
            reqMap.put("agentId",internetLogout.getAgentId());
        }
        if(StringUtils.isNotBlank(internetLogout.getAgentName())){
            reqMap.put("agentName",internetLogout.getAgentName());
        }
        if(StringUtils.isNotBlank(internetLogout.getBusNum())){
            reqMap.put("busNum",internetLogout.getBusNum());
        }
        if(null!=internetLogout.getReviewStatus()){
            reqMap.put("reviewStatus",internetLogout.getReviewStatus());
        }
        List<Map<String, Object>> orgCodeRes = iUserService.orgCode(userId);
        if(orgCodeRes==null && orgCodeRes.size()!=1){
            return null;
        }
        Map<String, Object> stringObjectMap = orgCodeRes.get(0);
        String organizationCode = String.valueOf(stringObjectMap.get("ORGANIZATIONCODE"));
        //省区大区查看自己的代理商 部门权限
        if(StringUtils.isNotBlank(organizationCode) && (organizationCode.contains("region") || organizationCode.contains("beijing"))) {
            reqMap.put("orgCode", organizationCode);
        }
        //内部人员根据名称查询指定流量卡
        List<String> agentNameList = dictOptionsService.getAgentNameList(userId);
        if(agentNameList.size()!=0) {
            reqMap.put("agentNameList", agentNameList);
        }
        reqMap.put("page",page);
        List<Map<String, Object>> internetLogoutList = internetLogoutMapper.internetCardLogoutList(reqMap);
        for (Map<String, Object> map : internetLogoutList) {
            if(StringUtils.isNotBlank(String.valueOf(map.get("C_USER")))){
                CUser cUser = iUserService.selectById(Long.valueOf(String.valueOf(map.get("C_USER"))));
                if(null!=cUser)
                map.put("C_USER",cUser.getName());
            }
        }
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(internetLogoutList);
        pageInfo.setTotal(internetLogoutMapper.internetCardLogoutCount(reqMap));
        return pageInfo;
    }


    @Override
    public PageInfo internetCardLogoutDetailList(InternetLogoutDetail internetLogoutDetail, Page page, String agentId, Long userId){

        Map<String, Object> reqMap = internetLogoutDetailCommon(internetLogoutDetail, agentId, userId);
        reqMap.put("page",page);
        List<Map<String, Object>> internetLogoutList = internetLogoutDetailMapper.internetCardLogoutDetailList(reqMap);
        for (Map<String, Object> map : internetLogoutList) {
            map.put("ISSUER",Issuerstatus.getContentByValue(String.valueOf(map.get("ISSUER"))));
            map.put("LOGOUT_STATUS",InternetLogoutStatus.getContentByValue(String.valueOf(map.get("LOGOUT_STATUS"))));
            map.put("REVIEW_STATUS",AgStatus.getMsg(new BigDecimal(map.get("REVIEW_STATUS").toString())));
            if(StringUtils.isNotBlank(String.valueOf(map.get("C_USER")))){
                CUser cUser = iUserService.selectById(Long.valueOf(String.valueOf(map.get("C_USER"))));
                if(null!=cUser)
                    map.put("C_USER",cUser.getName());
            }
        }
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(internetLogoutList);
        pageInfo.setTotal(internetLogoutDetailMapper.internetCardLogoutDetailCount(reqMap));
        return pageInfo;
    }

    /**
     * 查询导出公共部分
     * @param internetLogoutDetail
     * @param agentId
     * @return
     */
    private Map<String, Object> internetLogoutDetailCommon(InternetLogoutDetail internetLogoutDetail,String agentId,Long userId){
        Map<String, Object> reqMap = new HashMap<>();
        if(StringUtils.isNotBlank(internetLogoutDetail.getId())){
            reqMap.put("id",internetLogoutDetail.getId());
        }
        if(StringUtils.isNotBlank(agentId)){
            reqMap.put("agentId",agentId);
        }else if(StringUtils.isNotBlank(internetLogoutDetail.getAgentId())){
            reqMap.put("agentId",internetLogoutDetail.getAgentId());
        }
        if(StringUtils.isNotBlank(internetLogoutDetail.getAgentName())){
            reqMap.put("agentName",internetLogoutDetail.getAgentName());
        }
        if(StringUtils.isNotBlank(internetLogoutDetail.getBusNum())){
            reqMap.put("busNum",internetLogoutDetail.getBusNum());
        }
        if(StringUtils.isNotBlank(internetLogoutDetail.getIccidNum())){
            reqMap.put("iccidNum",internetLogoutDetail.getIccidNum());
        }
        if(StringUtils.isNotBlank(internetLogoutDetail.getRenewId())){
            reqMap.put("renewId",internetLogoutDetail.getRenewId());
        }
        if(StringUtils.isNotBlank(internetLogoutDetail.getSnNum())){
            reqMap.put("snNum",internetLogoutDetail.getSnNum());
        }
        if(StringUtils.isNotBlank(internetLogoutDetail.getMerId())){
            reqMap.put("merId",internetLogoutDetail.getMerId());
        }
        if(StringUtils.isNotBlank(internetLogoutDetail.getMerName())){
            reqMap.put("merName",internetLogoutDetail.getMerName());
        }
        if(StringUtils.isNotBlank(internetLogoutDetail.getLogoutStatus())){
            reqMap.put("logoutStatus",internetLogoutDetail.getLogoutStatus());
        }
        if(StringUtils.isNotBlank(internetLogoutDetail.getInternetCardNum())){
            reqMap.put("internetCardNum",internetLogoutDetail.getInternetCardNum());
        }
        if(StringUtils.isNotBlank(internetLogoutDetail.getIssuer())){
            reqMap.put("issuer",internetLogoutDetail.getIssuer());
        }
        if(StringUtils.isNotBlank(internetLogoutDetail.getReviewStatus())){
            reqMap.put("reviewStatus",internetLogoutDetail.getReviewStatus());
        }
        if(StringUtils.isNotBlank(internetLogoutDetail.getBeginCTime())){
            reqMap.put("beginCTime",internetLogoutDetail.getBeginCTime());
        }
        if(StringUtils.isNotBlank(internetLogoutDetail.getEndCTime())){
            reqMap.put("endCTime",internetLogoutDetail.getEndCTime());
        }
        if(StringUtils.isNotBlank(internetLogoutDetail.getBeginSnNum())){
            reqMap.put("beginSnNum",internetLogoutDetail.getBeginSnNum());
        }
        if(StringUtils.isNotBlank(internetLogoutDetail.getEndSnNum())){
            reqMap.put("endSnNum",internetLogoutDetail.getEndSnNum());
        }

        List<Map<String, Object>> orgCodeRes = iUserService.orgCode(userId);
        if(orgCodeRes==null && orgCodeRes.size()!=1){
            return null;
        }
        Map<String, Object> stringObjectMap = orgCodeRes.get(0);
        String organizationCode = String.valueOf(stringObjectMap.get("ORGANIZATIONCODE"));
        //省区大区查看自己的代理商 部门权限
        if(StringUtils.isNotBlank(organizationCode) && (organizationCode.contains("region") || organizationCode.contains("beijing"))) {
            reqMap.put("orgCode", organizationCode);
        }
        //内部人员根据名称查询指定流量卡
        List<String> agentNameList = dictOptionsService.getAgentNameList(userId);
        if(agentNameList.size()!=0) {
            reqMap.put("agentNameList", agentNameList);
        }
        return reqMap;
    }


    @Override
    public Integer queryInternetLogoutDetailCount(InternetLogoutDetail internetLogoutDetail,String agentId,Long userId){
        Map<String, Object> reqMap = internetLogoutDetailCommon(internetLogoutDetail, agentId, userId);
        Integer count = internetLogoutDetailMapper.internetCardLogoutDetailCount(reqMap);
        return count;
    }


    /**
     * 注销服务接口  物联网卡信息-->批量 注销
     * @param internetLogout
     * @param iccids
     * @param cUser
     * @return
     * @throws MessageException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public AgentResult saveAndApprove(InternetLogout internetLogout, List<String> iccids, String cUser)throws MessageException {

        log.info("注销保存提交审批请求参数,internetLogout:{},iccids:{},cUser:{}",internetLogout.toString(),iccids.toString(),cUser);
        String retIdentifier = "";
        try {
            retIdentifier = redisService.lockWithTimeout(RedisCachKey.RENEW_CARD.code + cUser, RedisService.ACQUIRE_TIME_OUT, RedisService.TIME_OUT);
            if (StringUtils.isBlank(retIdentifier)) {
                log.info("续费中请勿重复提交,cUser:{}", cUser);
                throw new MessageException("注销中请勿重复提交");
            }
            // 流程中的部门参数
            Map startPar = agentEnterService.startPar(cUser);
            String party = "";// TODO 关注下返回的是什么
            if (null == startPar) {
                List<Map<String, Object>> orgCodeRes = iUserService.orgCode(Long.valueOf(cUser));
                if(orgCodeRes==null && orgCodeRes.size()!=1){
                    throw new MessageException("登陆账户有误,请联系管理员!");
                }
                Map<String, Object> stringObjectMap = orgCodeRes.get(0);
                String organizationCode = String.valueOf(stringObjectMap.get("ORGANIZATIONCODE"));
                Map<String,Object> reqMap = new HashMap<>();
                // 省区大区查看自己的代理商 部门权限
                if(StringUtils.isNotBlank(organizationCode) && (organizationCode.contains("region") || organizationCode.contains("beijing"))) {
                    reqMap.put("orgCode", organizationCode);
                }
                // 内部人员根据名称查询指定流量卡
                List<String> agentNameList = dictOptionsService.getAgentNameList(Long.valueOf(cUser));
                if(agentNameList.size()!=0) {
                    party = "interior";
                }else{
                    log.info("========用户{}启动部门参数为空", cUser);
                    throw new MessageException("启动部门参数为空!");
                }
            }else{
                party = String.valueOf(startPar.get("party"));
            }

            String internetLogoutId = idService.genId(TabId.O_INTERNET_LOGOUT);
            AgentBusInfo queryAgentBusInfo = null;
            BigDecimal cardCount = BigDecimal.ZERO;
            Set<String> agentIdSet = new HashSet<>();
            Set<String> agentNameSet = new HashSet<>();
            Set<String> busNumSet = new HashSet<>();
            Set<String> busPlatformSet = new HashSet<>();
            String agentId = "";
            String agName = "";
            for (String iccid : iccids) {
                OInternetCard oInternetCard = internetCardMapper.selectByPrimaryKey(iccid);
                if(oInternetCard==null){
                    throw new MessageException("iccid有误");
                }
                if(StringUtils.isBlank(oInternetCard.getInternetCardNum())){
                    throw new MessageException("物联卡号为空,不可申请注销,iccid:"+iccid);
                }
                if(StringUtils.isBlank(oInternetCard.getIssuer())){
                    throw new MessageException("发卡方为空,不可申请注销,iccid:"+iccid);
                }
                if(party.equals("agent")){
                    if(StringUtils.isBlank(oInternetCard.getBusNum()) || StringUtils.isBlank(oInternetCard.getBusPlatform())){
                        throw new MessageException("业务平台数据不全,不可申请注销,iccid:"+iccid);
                    }
                }
                InternetLogoutDetailExample internetLogoutDetailExample = new InternetLogoutDetailExample();
                InternetLogoutDetailExample.Criteria criteria = internetLogoutDetailExample.createCriteria();
                criteria.andStatusEqualTo(Status.STATUS_1.status);
                criteria.andIccidNumEqualTo(iccid);
                List<String> logoutStatusList = new ArrayList<>();
                logoutStatusList.add(InternetLogoutStatus.SX.getValue()); // 失效
                logoutStatusList.add(InternetLogoutStatus.TJSB.getValue());// 停机失败
                criteria.andLogoutStatusNotIn(logoutStatusList);// 除了失效 和 停机失败  其他均是注销中的
                List<InternetLogoutDetail> internetLogoutDetails = internetLogoutDetailMapper.selectByExample(internetLogoutDetailExample);
                if (internetLogoutDetails.size() != 0) {
                    throw new MessageException("iccid:" + iccid + "注销中,请勿重复发起");
                }
                InternetLogoutDetail internetLogoutDetail = new InternetLogoutDetail();
                String internetLogoutDetailId = idService.genId(TabId.O_INTERNET_LOGOUT_DETAIL);
                internetLogoutDetail.setId(internetLogoutDetailId);
                internetLogoutDetail.setRenewId(internetLogoutId);
                internetLogoutDetail.setIccidNum(oInternetCard.getIccidNum());
                internetLogoutDetail.setInternetCardStatus(oInternetCard.getInternetCardStatus());
                internetLogoutDetail.setOrderId(oInternetCard.getOrderId());
                internetLogoutDetail.setSnNum(oInternetCard.getSnNum());
                internetLogoutDetail.setInternetCardNum(oInternetCard.getInternetCardNum());
                internetLogoutDetail.setOpenAccountTime(oInternetCard.getOpenAccountTime());
                internetLogoutDetail.setExpireTime(oInternetCard.getExpireTime());
                internetLogoutDetail.setMerId(oInternetCard.getMerId());
                internetLogoutDetail.setMerName(oInternetCard.getMerName());
                internetLogoutDetail.setAgentId(oInternetCard.getAgentId());
                internetLogoutDetail.setAgentName(oInternetCard.getAgentName());
                internetLogoutDetail.setLogoutStatus(InternetLogoutStatus.ZXZ.getValue());// TODO  提交申请时审批前  修改为  --> 待注销
                internetLogoutDetail.setStatus(Status.STATUS_1.status);
                internetLogoutDetail.setBusNum(oInternetCard.getBusNum());
                internetLogoutDetail.setBusPlatform(oInternetCard.getBusPlatform());
                internetLogoutDetail.setcTime(new Date());
                internetLogoutDetail.setuTime(new Date());
                internetLogoutDetail.setcUser(cUser);
                internetLogoutDetail.setuUser(cUser);
                internetLogoutDetail.setVersion(BigDecimal.ONE);
                internetLogoutDetail.setIssuer(oInternetCard.getIssuer());
                if(StringUtils.isNotBlank(oInternetCard.getBusNum()) && StringUtils.isNotBlank(oInternetCard.getBusPlatform())){
                    // 查询最新对接省区大区对接人
                    AgentBusInfo agentBusInfo = new AgentBusInfo();
                    agentBusInfo.setBusNum(oInternetCard.getBusNum());
                    agentBusInfo.setBusPlatform(oInternetCard.getBusPlatform());
                    List<BigDecimal> busStatusList = new ArrayList<>();
                    busStatusList.add(BusStatus.QY.getValue());// 启用
                    busStatusList.add(BusStatus.WJH.getValue());//  未激活
                    busStatusList.add(BusStatus.WQY.getValue());// 未启用
                    agentBusInfo.setBusStatusList(busStatusList);
                    agentBusInfo.setCloReviewStatus(AgStatus.Approved.getValue());
                    List<AgentBusInfo> agentBusInfos = agentBusinfoService.selectByAgentBusInfo(agentBusInfo);
                    if(agentBusInfos.size()!=1){
                        throw new MessageException("平台码或平台错误,请联系管理员");
                    }
                    queryAgentBusInfo = agentBusInfos.get(0);
                    internetLogoutDetail.setAgDocPro(queryAgentBusInfo.getAgDocPro());
                    internetLogoutDetail.setAgDocDistrict(queryAgentBusInfo.getAgDocDistrict());
                    internetLogoutDetail.setBusContactPerson(queryAgentBusInfo.getBusContactPerson());
                }
                log.info("注销明细保存参数,internetLogoutDetail:{}",internetLogoutDetail.toString());
                internetLogoutDetailMapper.insertSelective(internetLogoutDetail);
                oInternetCard.setRenewStatus(InternetRenewStatus.ZXZ.getValue());// 提交申请后 就是注销中
                int j = internetCardMapper.updateByPrimaryKeySelective(oInternetCard);
                if(j!=1){
                    throw new MessageException("更新物联网卡信息失败");
                }

                busNumSet.add(oInternetCard.getBusNum());
                busPlatformSet.add(oInternetCard.getBusPlatform());
                agentIdSet.add(oInternetCard.getAgentId());
                agentNameSet.add(oInternetCard.getAgentName());
                agentId = oInternetCard.getAgentId();
                agName = oInternetCard.getAgentName();
                cardCount = cardCount.add(BigDecimal.ONE);
            }
            if(agentIdSet.size()!=1){
                throw new MessageException("不同代理商编码请分开申请");
            }
            if(agentNameSet.size()!=1){
                throw new MessageException("不同代理商名称请分开申请");
            }
            if(busNumSet.size()!=1){
                throw new MessageException("不同平台码请分开申请");
            }
            if(busPlatformSet.size()!=1){
                throw new MessageException("不同平台请分开申请");
            }
            if(party.equals("agent") && queryAgentBusInfo==null){
                throw new MessageException("缺少平台码或平台");
            }

            internetLogout.setId(internetLogoutId);// 注销申请表 记录
            internetLogout.setAgentId(agentId);
            internetLogout.setAgentName(agName);
            if(queryAgentBusInfo!=null){
                internetLogout.setBusNum(queryAgentBusInfo.getBusNum());
                internetLogout.setBusPlatform(queryAgentBusInfo.getBusPlatform());
                internetLogout.setAgDocPro(queryAgentBusInfo.getAgDocPro());
                internetLogout.setAgDocDistrict(queryAgentBusInfo.getAgDocDistrict());
                internetLogout.setBusContactPerson(queryAgentBusInfo.getBusContactPerson());
            }
            internetLogout.setLogoutCardCount(cardCount);
            internetLogout.setReviewStatus(AgStatus.Approving.getValue());
            internetLogout.setStatus(Status.STATUS_1.status);
            internetLogout.setcTime(new Date());
            internetLogout.setuTime(new Date());
            internetLogout.setcUser(cUser);
            internetLogout.setuUser(cUser);
            internetLogout.setVersion(BigDecimal.ONE);
            log.info("注销申请保存参数,internetLogout:{}",internetLogout.toString());
            internetLogoutMapper.insertSelective(internetLogout);
            log.info("注销申请请求类型,party:{}",party);
            if(party.equals("agent")){
                //启动审批
                String proce = activityService.createDeloyFlow(null,dictOptionsService.getApproveVersion("cardLogout"), null, null,null);
                if (proce == null) {
                    throw new MessageException("审批流启动失败!");
                }
                //代理商业务视频关系
                BusActRel record = new BusActRel();
                record.setBusId(internetLogoutId);
                record.setActivId(proce);
                record.setcTime(Calendar.getInstance().getTime());
                record.setcUser(cUser);
                record.setStatus(Status.STATUS_1.status);
                record.setBusType(BusActRelBusType.cardLogout.name());
                record.setActivStatus(AgStatus.Approving.name());
                record.setDataShiro(BusActRelBusType.cardLogout.key);
                record.setAgentId(StringUtils.isBlank(agentId) || agentId.equals("null")?"":agentId);
                record.setAgentName(StringUtils.isBlank(agName) || agName.equals("null")?"":agName);
                record.setAgDocPro(queryAgentBusInfo.getAgDocPro());
                record.setAgDocDistrict(queryAgentBusInfo.getAgDocDistrict());
                record.setExplain(queryAgentBusInfo.getBusNum());
                try {
                    taskApprovalService.addABusActRel(record);
                    log.info("物联网卡注销审批流启动成功");
                } catch (Exception e) {
                    AppConfig.sendEmails(MailUtil.printStackTrace(e), "申请注销保存出现异常,saveAndApprove方法");
                    e.getStackTrace();
                    log.error("物联网卡注销审批流启动失败{}");
                    throw new MessageException("物联网卡注销审批流启动失败!:{}",e.getMessage());
                }
            }else{
                saveOrCompress(internetLogout,AgStatus.Approved.getValue());
            }
        }finally {
            if(StringUtils.isNotBlank(retIdentifier)){
                redisService.releaseLock(RedisCachKey.RENEW_CARD.code+cUser, retIdentifier);
            }
        }

        return AgentResult.ok();
    }

    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentResult approvalTask(AgentVo agentVo, String userId) throws Exception {
        log.info("注销处理任务开始:agentVo:{},userId:{}",agentVo.toString(),userId);
        try {
            AgentResult result = agentEnterService.completeTaskEnterActivity(agentVo,userId);
            if(!result.isOK()){
                log.error("申请注销审批出现异常，msg：{}"+result.getMsg());
                throw new MessageException("工作流处理任务异常");
            }
        } catch (ProcessException e) {
            log.info("申请注销审批出现异常,approvalTask方法1");
            AppConfig.sendEmails(MailUtil.printStackTrace(e), "申请注销审批出现异常,approvalTask方法1");
            e.printStackTrace();
            throw new MessageException(e.getMsg());
        } catch (MessageException e) {
            log.info("申请注销审批出现异常,approvalTask方法2");
            AppConfig.sendEmails(MailUtil.printStackTrace(e), "申请注销审批出现异常,approvalTask方法2");
            e.printStackTrace();
            throw new MessageException(e.getMsg());
        } catch (Exception e) {
            log.info("申请注销审批出现异常,approvalTask方法3");
            AppConfig.sendEmails(MailUtil.printStackTrace(e), "申请注销审批出现异常,approvalTask方法3");
            e.printStackTrace();
            throw new MessageException(e.getLocalizedMessage());
        }
        log.info("注销处理任务完成");
        return AgentResult.ok();
    }

    /**
     * 完成监听
     * @param proIns
     * @param agStatus
     * @return
     * @throws MessageException
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult compressCompensateActivity(String proIns, BigDecimal agStatus)throws MessageException{

        log.info("注销完成监听:请求参数:proIns:{},agStatus:{}",proIns,agStatus);
        BusActRel busActRel = busActRelService.findByProIns(proIns);
        if (busActRel==null){
            log.info("审批任务结束{}{}，未找到审批中的审批和数据关系", proIns, agStatus);
            throw new MessageException("查询关系表失败");
        }
        InternetLogout internetLogout = internetLogoutMapper.selectByPrimaryKey(busActRel.getBusId());
        if(internetLogout==null){
            throw new MessageException("查询注销记录失败");
        }
        saveOrCompress(internetLogout,agStatus);
        busActRel.setActivStatus(AgStatus.getAgStatusString(agStatus));
        int z = busActRelService.updateByPrimaryKey(busActRel);
        if(z!=1) {
            throw new MessageException("物联网卡注销更新关系表失败");
        }
        log.info("注销完成监听:完成");
        return AgentResult.ok();
    }

    /**
     * 直接保存  / 审批通过后调用
     * @param internetLogout
     * @param agStatus
     * @throws MessageException
     */
    private void saveOrCompress(InternetLogout internetLogout,BigDecimal agStatus)throws MessageException{

        log.info("注销直接保存/审批通过后调用:请求参数:internetLogout:{},agStatus:{}",internetLogout.toString(),agStatus);
        internetLogout.setReviewStatus(agStatus);
        if(agStatus.compareTo(AgStatus.Approved.getValue())==0){
            internetLogout.setReviewPassTime(new Date());
        }
        int i = internetLogoutMapper.updateByPrimaryKeySelective(internetLogout);
        if(i!=1){
            throw new MessageException("更新注销记录失败");
        }
        InternetLogoutDetailExample internetLogoutDetailExample = new InternetLogoutDetailExample();
        InternetLogoutDetailExample.Criteria criteria = internetLogoutDetailExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andLogoutStatusEqualTo(InternetLogoutStatus.ZXZ.getValue());// TODO 审核通过后，查询 注销状态为待注销的 记录  -- >  待注销
        criteria.andRenewIdEqualTo(internetLogout.getId());
        List<InternetLogoutDetail> internetLogoutDetails = internetLogoutDetailMapper.selectByExample(internetLogoutDetailExample);
        for (InternetLogoutDetail internetLogoutDetail : internetLogoutDetails) {
            OInternetCard oInternetCard = internetCardMapper.selectByPrimaryKey(internetLogoutDetail.getIccidNum());
            if(null==oInternetCard){
                throw new MessageException("iccid不存在");
            }
            if(agStatus.compareTo(AgStatus.Refuse.getValue())==0){
                internetLogoutDetail.setLogoutStatus(InternetLogoutStatus.SX.getValue());
                oInternetCard.setRenewStatus(InternetRenewStatus.WXF.getValue());
            }else if(agStatus.compareTo(AgStatus.Approved.getValue())==0){// 揭阳移动 和 烟台移动的处理方法不一样
                if(StringUtils.isNotBlank(internetLogoutDetail.getIssuer()) && internetLogoutDetail.getIssuer().equals(Issuerstatus.JY_MOBILE.getValue())){
                    internetLogoutDetail.setLogoutStatus(InternetLogoutStatus.TJCLZ.getValue());// TODO 审核通过 修改为  --> 注销中
                    // TODO 2 审核通过 卡状态更改为  --> 已注销
                }else{
                    internetLogoutDetail.setLogoutStatus(InternetLogoutStatus.DZX.getValue()); // TODO  烟台移动 修改为  --> 注销中
                    oInternetCard.setRenewStatus(InternetRenewStatus.YZX.getValue());
                }
            }
            int j = internetLogoutDetailMapper.updateByPrimaryKeySelective(internetLogoutDetail);
            if(j!=1){
                throw new MessageException("更新注销明细失败");
            }
            int k = internetCardMapper.updateByPrimaryKeySelective(oInternetCard);
            if(k!=1){
                throw new MessageException("更新物联网卡信息失败");
            }
        }
        log.info("注销直接保存/审批通过后调用结束");
    }

    @Override
    public InternetLogout selectByPrimaryKey(String id){
        InternetLogout internetLogout = internetLogoutMapper.selectByPrimaryKey(id);
        return internetLogout;
    }

    /**
     * 查询导出数目总和
     * @param internetLogout
     * @param page
     * @param agentId
     * @param userId
     * @return
     */
    @Override
    public Integer internetCardLogoutCount(InternetLogout internetLogout, Page page, String agentId, Long userId){
        Map<String, Object> reqMap = new HashMap<>();
        if(StringUtils.isNotBlank(internetLogout.getId())){
            reqMap.put("id",internetLogout.getId());
        }
        if(StringUtils.isNotBlank(agentId)){
            reqMap.put("agentId",agentId);
        }else if(StringUtils.isNotBlank(internetLogout.getAgentId())){
            reqMap.put("agentId",internetLogout.getAgentId());
        }
        if(StringUtils.isNotBlank(internetLogout.getAgentName())){
            reqMap.put("agentName",internetLogout.getAgentName());
        }
        if(StringUtils.isNotBlank(internetLogout.getBusNum())){
            reqMap.put("busNum",internetLogout.getBusNum());
        }
        if(null!=internetLogout.getReviewStatus()){
            reqMap.put("reviewStatus",internetLogout.getReviewStatus());
        }
        List<Map<String, Object>> orgCodeRes = iUserService.orgCode(userId);
        if(orgCodeRes==null && orgCodeRes.size()!=1){
            return 0;
        }
        Map<String, Object> stringObjectMap = orgCodeRes.get(0);
        String organizationCode = String.valueOf(stringObjectMap.get("ORGANIZATIONCODE"));
        //省区大区查看自己的代理商 部门权限
        if(StringUtils.isNotBlank(organizationCode) && (organizationCode.contains("region") || organizationCode.contains("beijing"))) {
            reqMap.put("orgCode", organizationCode);
        }
        //内部人员根据名称查询指定流量卡
        List<String> agentNameList = dictOptionsService.getAgentNameList(userId);
        if(agentNameList.size()!=0) {
            reqMap.put("agentNameList", agentNameList);
        }
        reqMap.put("page",page);
        int total  = internetLogoutMapper.internetCardLogoutCount(reqMap);
        return total;
    }
}
