package com.ryx.credit.service.impl.agent;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.common.util.agentUtil.AESUtil;
import com.ryx.credit.common.util.agentUtil.RSAUtil;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.*;
import com.ryx.credit.dao.bank.DPosRegionMapper;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.vo.AgentNotifyVo;
import com.ryx.credit.service.agent.*;
import com.ryx.credit.service.bank.PosRegionService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.dict.RegionService;
import com.ryx.credit.util.Constants;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 异步通知 pos/手刷开户
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/6/11 11:33
 */
@Service("agentNotifyService")
public class AgentNotifyServiceImpl implements AgentNotifyService{

    private static Logger log = LoggerFactory.getLogger(AgentNotifyServiceImpl.class);

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private RegionMapper regionMapper;
    @Autowired
    private AgentService agentService;
    @Autowired
    private AgentPlatFormSynMapper agentPlatFormSynMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private AgentNotifyService agentNotifyService;
    @Autowired
    private ImportAgentMapper importAgentMapper;
    @Autowired
    private PlatFormMapper platFormMapper;
    @Autowired
    private RegionService regionService;
    @Autowired
    private PosRegionService posRegionService;
    @Resource(name="platformSynServicePos")
    private  PlatformSynService  platformSynServicePos;
    @Resource(name="platformSynServiceMpos")
    private  PlatformSynService  platformSynServiceMpos;
    private List<PlatformSynService> platformSynServiceList ;
    @Autowired
    private DPosRegionMapper posRegionMapper;
    @Autowired
    private ApaycompService apaycompService;
    @Autowired
    private AgentColinfoService agentColinfoService;



    @PostConstruct
    public void init(){
        platformSynServiceList = new ArrayList<PlatformSynService>();
        platformSynServiceList.add(platformSynServicePos);
        platformSynServiceList.add(platformSynServiceMpos);
    }


    /**
     * 流程调用入网流程，修改流程
     */
    @Override
    public void asynNotifyPlatform(){
        threadPoolTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                }
                ImportAgentExample example = new ImportAgentExample();
                ImportAgentExample.Criteria criteria = example.createCriteria();
                List<String> dataType = new ArrayList<>();
                dataType.add(AgImportType.NETINAPP.getValue());
                dataType.add(AgImportType.BUSAPP.getValue());
                dataType.add(AgImportType.DATACHANGEAPP.getValue());
                criteria.andDatatypeIn(dataType);
                criteria.andDealstatusEqualTo(Status.STATUS_0.status);
                List<ImportAgent> importAgents = importAgentMapper.selectByExample(example);
                log.info("接收入网查询列表开始:size：{}",importAgents.size());
                for (ImportAgent importAgent : importAgents) {

                    if(importAgent.getDatatype().equals(AgImportType.NETINAPP.getValue())){
                        AgentBusInfoExample AgBusExample = new AgentBusInfoExample();
                        AgentBusInfoExample.Criteria AgBusCriteria = AgBusExample.createCriteria();
                        AgBusCriteria.andAgentIdEqualTo(importAgent.getDataid());
                        List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(AgBusExample);
                        for (AgentBusInfo agentBusInfo : agentBusInfos) {
                            try {
                                agentNotifyService.notifyPlatformNewLevelAndUpdate(agentBusInfo.getId(),importAgent.getId());
                            } catch (Exception e) {
                                log.info("异步通知pos手刷接口异常:{},busId:{},--{}",e.getMessage(),agentBusInfo.getId(),AgImportType.NETINAPP.getValue());
                                e.printStackTrace();
                            }
                        }
                    }
                    if(importAgent.getDatatype().equals(AgImportType.BUSAPP.getValue())){
                        try {
                            agentNotifyService.notifyPlatformNewLevelAndUpdate(importAgent.getDataid(),importAgent.getId());
                        } catch (Exception e) {
                            log.info("异步通知pos手刷接口异常:{},busId:{},--{}",e.getMessage(),importAgent.getDataid(),AgImportType.BUSAPP.getValue());
                            e.printStackTrace();
                        }
                    }
                    if(importAgent.getDatatype().equals(AgImportType.DATACHANGEAPP.getValue())){
                        try {
                            agentNotifyService.notifyPlatform(importAgent.getDataid(),importAgent.getId());
                        } catch (Exception e) {
                            log.info("异步通知pos手刷接口异常:{},busId:{},--{}",e.getMessage(),importAgent.getDataid(),AgImportType.DATACHANGEAPP.getValue());
                            e.printStackTrace();
                        }
                    }

                    //只处理一次
                    try {
                        ImportAgent importAgentAfter  = importAgentMapper.selectByPrimaryKey(importAgent.getId());
                        if(importAgentAfter.getDealstatus().equals(Status.STATUS_0.status)){
                            importAgentAfter.setDealstatus(Status.STATUS_3.status);
                             if(1!=importAgentMapper.updateByPrimaryKeySelective(importAgentAfter)){
                                 log.info("异步通知更新ImportAgent失败:busId:{},--{}",importAgent.getDataid(),importAgent.getDatatype());
                             }else{
                                 log.info("异步通知更新ImportAgent成功:busId:{},--{}",importAgent.getDataid(),importAgent.getDatatype());
                             }
                        }
                    } catch (Exception e) {
                        log.info("异步通知更新ImportAgent异常:busId:{},--{}",importAgent.getDataid(),importAgent.getDatatype());
                        e.printStackTrace();
                        log.error(e.getMessage(),e);
                    }

                }
            }
        });
    }

    @Override
    public void asynNotifyPlatform(String busId){
        threadPoolTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    agentNotifyService.notifyPlatform(busId,null);
                } catch (Exception e) {
                    log.info("异步通知pos手刷接口异常:{},busId:{}",e.getMessage(),busId);
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 手动调用通知接口
     * @param id
     * @param userid
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public void userNotifyPlatformLevelAndUpdateAsynById(String id, String userid)throws Exception {
       log.info("开平台升级接口请求发起用户{}操作ID{}", userid, id);
       AgentPlatFormSyn agentPlatFormSyn = agentPlatFormSynMapper.selectByPrimaryKey(id);
       AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(agentPlatFormSyn.getBusId());
        for (PlatformSynService platformSynService : platformSynServiceList) {
            if(platformSynService.isMyPlatformByPlatformCode(agentPlatFormSyn.getPlatformCode())) {
                AgentResult res = null;

                AgentPlatFormSyn record = new AgentPlatFormSyn();
                try {
                    //请求参数构建
                    Map req_data =  platformSynService.agencyLevelUpdateChangeData(
                            FastMap.fastSuccessMap()
                                    .putKeyV("agentBusinfoId",agentBusInfo.getId())
                                    .putKeyV("processingId",userid));

                    log.info("开平台升级接口请求发起用户{}开平台{}平台编号不为空走升级接口,请求参数{}",userid,
                            agentPlatFormSyn.getPlatformCode(),
                            req_data);

                    record.setId(idService.genId(TabId.a_agent_platformsyn));
                    record.setSendJson(JSONObject.toJSONString(req_data));
                    record.setNotifyTime(new Date());
                    record.setAgentId(agentBusInfo.getAgentId());
                    record.setBusId(agentBusInfo.getId());
                    record.setPlatformCode(agentBusInfo.getBusPlatform());
                    record.setVersion(Status.STATUS_1.status);
                    record.setcTime(new Date());
                    record.setNotifyStatus(Status.STATUS_0.status);
                    record.setNotifyCount(new BigDecimal(1));
                    record.setcUser(userid);

                    //发送请求
                    res = platformSynService.agencyLevelUpdateChange(req_data);

                } catch (MessageException e) {
                    e.printStackTrace();
                    res = AgentResult.fail(e.getMsg());
                } catch (Exception e) {
                    e.printStackTrace();
                    res = AgentResult.fail(e.getLocalizedMessage());
                }
                if(res.isOK()){
                    record.setSuccesTime(new Date());
                    record.setNotifyStatus(Status.STATUS_1.status);
                    record.setNotifyJson((res.getData()!=null?res.getData().toString():res.getMsg()));
                    if(1!=agentPlatFormSynMapper.insertSelective(record)){
                        log.info("开平台升级接口请求发起用户{}开平台{}平台编号不为空走升级接口,更新记录{},更新成功",userid,agentBusInfo.getId(),agentPlatFormSyn.getId());
                    }
                    //更新入网状态
                    Agent agent = agentService.getAgentById(agentBusInfo.getAgentId());
                    //更新入网状态
                    Agent updateAgent = new Agent();
                    updateAgent.setId(agent.getId());
                    updateAgent.setVersion(agent.getVersion());
                    updateAgent.setcIncomStatus(AgentInStatus.IN.status);
                    Date nowDate = new Date();
                    updateAgent.setcIncomTime(nowDate);
                    updateAgent.setcUtime(nowDate);
                    if(agentMapper.updateByPrimaryKeySelective(updateAgent)==1){
                        log.info("开平台升级接口请求发起用户{}开平台{}平台编号不为空走升级接口,更新本地代理商{},更新成功",userid,updateAgent.getId(),"入网成功");
                    }else{
                        log.info("开平台升级接口请求发起用户{}开平台{}平台编号不为空走升级接口,更新本地代理商{},更新失败",userid,updateAgent.getId(),"入网成功");
                    }
                    log.info("开平台升级接口请求发起用户{}开平台{}平台编号不为空走升级接口,更新本地平台{}",userid,agentBusInfo.getBusNum(),"入网成功");
                    agentBusInfo.setBusStatus(BusinessStatus.Enabled.status);
                    agentBusInfo.setBusLoginNum(agentBusInfo.getBusNum());
                    if(agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfo)==1){
                        log.info("开平台升级接口请求发起用户{}开平台{}平台编号不为空走升级接口,更新本地业务平台{},更新成功",userid,agentBusInfo.getId(),"入网成功");
                    }else{
                        log.info("开平台升级接口请求发起用户{}开平台{}平台编号不为空走升级接口,更新本地业务平台{},更新失败",userid,agentBusInfo.getId(),"入网成功");
                    }

                    //执行账户修改
                    notifyPlatformUpadteByBusId(agentBusInfo.getId(),userid);

                }else{

                    agentBusInfo.setBusStatus(BusinessStatus.pause.status);
                    if(1==agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfo)){
                        log.info("开平台升级接口请求发起用户{}开平台{}平台编号不为空走升级接口,更新本地业务平台{},更新成功",userid,agentBusInfo.getId(),"入网失败");
                    }else{
                        log.info("开平台升级接口请求发起用户{}开平台{}平台编号不为空走升级接口,更新本地业务平台{},更新失败",userid,agentBusInfo.getId(),"入网失败");
                    }

                    record.setSuccesTime(new Date());
                    record.setNotifyStatus(Status.STATUS_0.status);
                    record.setNotifyJson((res.getData()!=null?JSONObject.toJSONString(res.getData()):res.getMsg().toString()));
                    agentPlatFormSynMapper.updateByPrimaryKeySelective(record);
                }
            }
        }
    }


    /**
     * 已有编号进行入网修改
     * @param busId
     * @param userId
     * @throws Exception
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public void notifyPlatformUpadteByBusId(String busId, String userId) throws Exception {
        log.info("已有编号进行入网修改: busId：{},userId:{}",busId,userId);
        if(StringUtils.isBlank(busId)){
            log.info("已有编号进行入网修改：notifyPlatform业务ID为空");
            return;
        }
        //业务信息
        AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(busId);
        if(StringUtils.isBlank(agentBusInfo.getBusNum())){
            log.info("已有编号进行入网修改：BusNum为空");
            return;
        }
        //直签不直发不通知
        if(agentBusInfo.getBusType().equals("8")){
            log.info("直签不直发不通知：agentId:{},busId:{}",agentBusInfo.getAgentId(),agentBusInfo.getId());
            return;
        }
        Agent agent = agentService.getAgentById(agentBusInfo.getAgentId());
        AgentBusInfo agentParent = null;
        if(StringUtils.isNotBlank(agentBusInfo.getBusParent())){
            //取出上级业务
            agentParent = agentBusInfoMapper.selectByPrimaryKey(agentBusInfo.getBusParent());
        }
        //业务区域
        String[] split = new String[1];
        if(StringUtils.isNotBlank(agentBusInfo.getBusRegion())){
            String[] busRegion = agentBusInfo.getBusRegion().split(",");
            Boolean flag = false;
            for(int i=0;i<busRegion.length;i++){
                if(busRegion[i].equals("0")){
                    flag = true;
                    break;
                }
            }
            if(flag){
                Set<String> dPosRegions = posRegionMapper.queryNationwide();
                split = dPosRegions.toArray(new String[]{});
            }else{
                Set<String> dPosRegions = posRegionService.queryCityByCode(agentBusInfo.getBusRegion());
                split = dPosRegions.toArray(new String[]{});
            }
        }
        //通知对象
        AgentNotifyVo agentNotifyVo = new AgentNotifyVo();
        //注册区域
        if(agent.getAgRegArea()!=null){
            List<String> regionList = getParent(agent.getAgRegArea());
            if(regionList!=null){
                if(regionList.size()==3){
                    agentNotifyVo.setProvince(regionList.get(0));
                    agentNotifyVo.setCity(regionList.get(1));
                    agentNotifyVo.setCityArea(regionList.get(2));
                }else if(regionList.size()==2){
                    agentNotifyVo.setProvince(regionList.get(0));
                    agentNotifyVo.setCity(regionList.get(1));
                }else if(regionList.size()==1){
                    agentNotifyVo.setProvince(regionList.get(0));
                }
            }
        }
        agentNotifyVo.setBusiAreas(split);

        agentNotifyVo.setAgHeadMobile(agent.getAgHeadMobile());
        agentNotifyVo.setOrgId(agentBusInfo.getBusNum());
        agentNotifyVo.setOrgName(agent.getAgName());
        agentNotifyVo.setUseOrgan(agentBusInfo.getBusUseOrgan());
        agentNotifyVo.setBusPlatform(agentBusInfo.getBusPlatform());
        agentNotifyVo.setBaseMessage(agent);
        agentNotifyVo.setBusMessage(agentBusInfo);
        agentNotifyVo.setHasS0(agentBusInfo.getDredgeS0().equals(new BigDecimal(1))?"0":"1");
        agentNotifyVo.setDebitTop(agentBusInfo.getDebitCapping());//借记封顶额（元）
        agentNotifyVo.setCkDebitRate(agentBusInfo.getDebitAppearRate());//借记出款费率（%）
        agentNotifyVo.setLowDebitRate(agentBusInfo.getDebitRateLower());//借记费率下限（%）
        if(StringUtils.isNotBlank(agentBusInfo.getBusLoginNum())){
            agentNotifyVo.setLoginName(agentBusInfo.getBusLoginNum());
        }
        PlatForm platForm = platFormMapper.selectByPlatFormNum(agentBusInfo.getBusPlatform());
        //cxinfo posbusIType 从数据库中获取
        if(PlatformType.whetherPOS(platForm.getPlatformType())){
            agentNotifyVo.setBusiType(platForm.getPosbusitype());
        }
        //如果是直签 就传02：直签机构  否则就传递 01：普通机构
        agentNotifyVo.setOrgType(OrgType.zQ(agentBusInfo.getBusType())?OrgType.STR.getValue():OrgType.ORG.getValue());
        if(null!=agentParent){
            agentNotifyVo.setSupDorgId(agentParent.getBusNum());
        }

        String id = idService.genId(TabId.a_agent_platformsyn);
        AgentPlatFormSyn record = new AgentPlatFormSyn();
        AgentResult result = null;
        try {
            record.setId(id);
            record.setNotifyTime(new Date());
            record.setAgentId(agentBusInfo.getAgentId());
            record.setBusId(agentBusInfo.getId());
            record.setPlatformCode(agentBusInfo.getBusPlatform());
            record.setVersion(Status.STATUS_1.status);
            record.setcTime(new Date());
            record.setNotifyStatus(Status.STATUS_0.status);
            record.setNotifyCount(new BigDecimal(1));
            record.setcUser(userId);
            record.setNotifyType(NotifyType.NetInEdit.getValue());
            if(platForm==null){
                log.info("已有编号进行入网修改：通知pos手刷业务平台未知");
                throw new MessageException("通知pos手刷业务平台未知");
            }

            //调用POST接口
            if(PlatformType.whetherPOS(platForm.getPlatformType())){
                //智能POS代理商名加N区分 CXINFO pos平台的名称前缀从数据库中获取
                if(org.apache.commons.lang.StringUtils.isNotEmpty(platForm.getPosanameprefix())){
                    agentNotifyVo.setOrgName(agentNotifyVo.getOrgName());
                    agentNotifyVo.setActivityType(platForm.getPosanameprefix());
                }else{
                    agentNotifyVo.setOrgName(agentNotifyVo.getOrgName());
                }
                //POS传递的唯一ID是业务平台记录ID
                agentNotifyVo.setUniqueId(agentBusInfo.getId());
                log.info("已有编号进行入网修改：接收入网请求开始POS: busId：{},userId:{},data:{}",busId,userId,JSONObject.toJSONString(agentNotifyVo));
                String sendJson = JsonUtil.objectToJson(agentNotifyVo);
                record.setSendJson(sendJson);
//                result = httpRequestForPos(agentNotifyVo);
                log.info("已有编号进行入网修改：接收入网请求结束POS: busId：{},userId:{},data:{}",busId,userId,JSONObject.toJSONString(agentNotifyVo));
            }

            //调用首刷接口
            if(platForm.getPlatformType().equals(PlatformType.MPOS.getValue())){
                PayComp payComp = apaycompService.selectById(agentBusInfo.getCloPayCompany());
                AgentColinfo agentColinfo = agentColinfoService.selectByAgentIdAndBusId(agent.getId(), agentBusInfo.getId());
                agentColinfo.setAccountId(agentBusInfo.getCloPayCompany());
                agentColinfo.setAccountName(payComp.getComName());
                agentNotifyVo.setColinfoMessage(agentColinfo);
                //MPOS传递的唯一id为代理商唯一ID
                agentNotifyVo.setUniqueId(agentBusInfo.getAgentId());
                log.info("已有编号进行入网修改：接收入网请求开始MPOS: busId：{},userId:{},data:{}",busId,userId,JSONObject.toJSONString(agentNotifyVo));
                String sendJson = JsonUtil.objectToJson(agentNotifyVo);
                record.setSendJson(sendJson);
//                result = httpRequestForMPOS(agentNotifyVo);
                log.info("已有编号进行入网修改：接收入网请求结束MPOS: busId：{},userId:{},data:{}",busId,userId,JSONObject.toJSONString(agentNotifyVo));
            }
            for (PlatformSynService platformSynService : platformSynServiceList) {
                log.info("已有编号进行入网修改,平台编号:{} 服务类:{}", agentBusInfo.getBusNum(), platformSynService.getClass().getSimpleName());
                if (platformSynService.isMyPlatform(agentBusInfo.getId())) {
                    result = platformSynService.httpRequestNetIn(agentNotifyVo);
                }
            }

            log.info("已有编号进行入网修改：接收入网,业务id：{},返回结果:{}",busId,result);
            record.setNotifyJson(String.valueOf(result.getData()));
        } catch (Exception e) {
            log.info("已有编号进行入网修改：通知pos手刷http请求异常:{}",e.getMessage());
            record.setNotifyJson(e.getLocalizedMessage());
            record.setNotifyCount(BigDecimal.ONE);
            result = AgentResult.fail(e.getLocalizedMessage());
        }
        //接口请求成功
        if(null!=result && result.isOK()){
            //添加请求记录
            record.setSuccesTime(new Date());
            record.setNotifyStatus(Status.STATUS_1.status);
            if(1==agentPlatFormSynMapper.insertSelective(record)){
                log.info("已有编号进行入网修改：添加请求记录成功,id:{},业务ID:{},返回结果:{}",record.getId(),busId,result.getMsg());
            }
            log.info("已有编号进行入网修改：更新代理商信息,id:{},业务ID:{},返回结果:{}",record.getId(),busId,result.getMsg());
            Agent updateAgent = agentMapper.selectByPrimaryKey(agent.getId());
            if(updateAgent!=null && updateAgent.getcIncomStatus()!=null && updateAgent.getcIncomStatus().equals(AgentInStatus.IN.status)){
                log.info("已有编号进行入网修改：代理商已入网,id:{},业务ID:{},返回结果:{}",record.getId(),busId,result.getMsg());
            }else {
                updateAgent.setcIncomStatus(AgentInStatus.IN.status);
                Date nowDate = new Date();
                updateAgent.setcIncomTime(nowDate);
                updateAgent.setcUtime(nowDate);
                if(1==agentMapper.updateByPrimaryKeySelective(updateAgent)) {
                    log.info("已有编号进行入网修改：更新入网状态成功,id:{},业务ID:{},返回结果:{}", record.getId(), busId, result.getMsg());
                }else{
                    log.info("已有编号进行入网修改：更新入网状态失败,id:{},业务ID:{},返回结果:{}", record.getId(), busId, result.getMsg());
                }
            }
            JSONObject jsonObject = JSONObject.parseObject(String.valueOf(result.getData()));
            //更新业务编号
            if(StringUtils.isNotBlank(jsonObject.getString("orgId"))) {
                log.info("已有编号进行入网修改：返回orgid不为空,id:{},业务ID:{},返回结果:{}", record.getId(), busId, result.getMsg());
                AgentBusInfo updateBusInfo = agentBusInfoMapper.selectByPrimaryKey(agentBusInfo.getId());
                if(agentBusInfo.getBusNum()!=null){
                    updateBusInfo.setBusNum(jsonObject.getString("orgId"));
                    updateBusInfo.setBusStatus(BusinessStatus.Enabled.status);
                    log.info("已有编号进行入网修改：更新orgId到库,id:{},业务ID:{},返回结果:{}", record.getId(), busId, jsonObject.toJSONString());
                }else{
                    log.info("已有编号进行入网修改：更新orgId到库,已存在不更新到库,id:{},业务ID:{},返回结果:{}", record.getId(), busId, jsonObject.toJSONString());
                }
                if(1!=agentBusInfoMapper.updateByPrimaryKeySelective(updateBusInfo)){
                    log.info("已有编号进行入网修改：更新业务编号失败,id:{},业务ID:{},返回结果:{}", record.getId(), busId, jsonObject.toJSONString());
                }
            }else{
                log.info("已有编号进行入网修改：不存在orgid,id:{},业务ID:{},返回结果:{}", record.getId(), busId, jsonObject.toJSONString());
            }
        }else{
//            AgentBusInfo updateBusInfo = agentBusInfoMapper.selectByPrimaryKey(agentBusInfo.getId());
//            updateBusInfo.setBusStatus(BusinessStatus.pause.status);
//            if(agentBusInfoMapper.updateByPrimaryKeySelective(updateBusInfo)!=1){
//                log.info("已有编号进行入网修改：接口调用失败,插入记录成功,id:{},业务ID:{},返回结果:{}", record.getId(), busId, result.getMsg());
//            }
            record.setSuccesTime(new Date());
            record.setNotifyStatus(Status.STATUS_0.status);
            if(1==agentPlatFormSynMapper.insertSelective(record)){
                log.info("已有编号进行入网修改：接口调用失败,插入记录成功,id:{},业务ID:{},返回结果:{}", record.getId(), busId, result.getMsg());
            }else{
                log.info("已有编号进行入网修改：接口调用失败,插入记录失败,id:{},业务ID:{},返回结果:{}", record.getId(), busId, result.getMsg());
            }
        }
    }

    /**
     * 启动调用升级开户接口
     * @param busId
     * @param impId
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public void notifyPlatformNewLevelAndUpdate(String busId, String impId) throws Exception {
        //业务平台
        ImportAgent importAgent = importAgentMapper.selectByPrimaryKey(impId);
        AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(busId);
        //直签不直发不通知
        if(agentBusInfo.getBusType().equals("8")){
            log.info("直签不直发不通知：agentId:{},busId:{}",agentBusInfo.getAgentId(),agentBusInfo.getId());
            return;
        }
        //业务平台编号已存在
        if(StringUtils.isNotBlank(agentBusInfo.getBusNum())){
            log.info("升级开户接口{}平台编号不为空走升级接口",agentBusInfo.getBusNum());

            for (PlatformSynService platformSynService : platformSynServiceList) {
                log.info("升级开户接口{}平台编号不为空走升级接口 服务类{}",agentBusInfo.getBusNum(),platformSynService.getClass().getSimpleName());
                if(platformSynService.isMyPlatform(agentBusInfo.getId())){

                    log.info("升级开户接口{}平台编号不为空走升级接口 匹配服务类{}",agentBusInfo.getBusNum(),platformSynService.getClass().getSimpleName());
                    AgentPlatFormSyn record = new AgentPlatFormSyn();
                    AgentResult res = null;
                    log.info("升级开户接口{}平台编号不为空走升级接口,获取请求参数,审批流{}",agentBusInfo.getBusNum(),importAgent.getBatchcode());

                    //请求参数构建
                    Map req_data =  platformSynService.agencyLevelUpdateChangeData(
                            FastMap.fastSuccessMap()
                            .putKeyV("agentBusinfoId",agentBusInfo.getId())
                            .putKeyV("processingId",importAgent.getBatchcode()));//存储审批流ID

                    log.info("升级开户接口{}平台编号不为空走升级接口,请求参数{},审批流{}",agentBusInfo.getBusNum(),req_data,importAgent.getBatchcode());
                    try {
                        //发送请求
                        res = platformSynService.agencyLevelUpdateChange(req_data);
                    }catch (MessageException e) {
                        e.printStackTrace();
                        log.error(e.getMsg(),e);
                        res=AgentResult.fail(e.getMsg());
                    }catch (Exception e) {
                        e.printStackTrace();
                        log.error(e.getMessage(),e);
                        res=AgentResult.fail("接口调用异常");
                    }
                    log.info("升级开户接口{}平台编号不为空走升级接口,请求结果{}",agentBusInfo.getBusNum(),res.getMsg());
                    record.setId(idService.genId(TabId.a_agent_platformsyn));
                    String sendJson = JsonUtil.objectToJson(req_data);
                    record.setSendJson(sendJson);
                    record.setNotifyJson(res.getData().toString());
                    record.setNotifyTime(new Date());
                    record.setAgentId(agentBusInfo.getAgentId());
                    record.setBusId(agentBusInfo.getId());
                    record.setPlatformCode(agentBusInfo.getBusPlatform());
                    record.setVersion(Status.STATUS_1.status);
                    record.setcTime(new Date());
                    record.setNotifyStatus(Status.STATUS_0.status);
                    record.setNotifyCount(new BigDecimal(1));
                    record.setcUser(agentBusInfo.getcUser());
                    record.setNotifyType(NotifyType.NetInUpgrade.getValue());
                    log.info("升级开户接口{}平台编号不为空走升级接口,请求结果{},记录数据：{}",agentBusInfo.getBusNum(),res.getMsg(),JSONObject.toJSONString(record));
                    if(null!=res &&  res.isOK()){
                        //更新入网状态
                        Agent agent = agentService.getAgentById(agentBusInfo.getAgentId());
                        //更新入网状态
                        if(agent.getcIncomStatus()!=null && !agent.getcIncomStatus().equals(AgentInStatus.IN.status)) {
                            Agent updateAgent = new Agent();
                            updateAgent.setId(agent.getId());
                            updateAgent.setVersion(agent.getVersion());
                            updateAgent.setcIncomStatus(AgentInStatus.IN.status);
                            Date nowDate = new Date();
                            updateAgent.setcIncomTime(nowDate);
                            updateAgent.setcUtime(nowDate);
                            if(1!=agentMapper.updateByPrimaryKeySelective(updateAgent)){
                                log.info("升级开户接口{}平台编号不为空走升级接口,更新的代理商{}",agentBusInfo.getBusNum(),"入网状态更新失败");
                            }else{
                                log.info("升级开户接口{}平台编号不为空走升级接口,更新的代理商{}",agentBusInfo.getBusNum(),"入网状态更新成功");
                            }
                        }
                        agentBusInfo.setBusStatus(BusinessStatus.Enabled.status);
                        if(1!=agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfo)){
                            log.info("升级开户接口{}平台编号不为空走升级接口,更新业务{}",agentBusInfo.getBusNum(),"入网成功状态更新失败");
                        }else{
                            log.info("升级开户接口{}平台编号不为空走升级接口,更新业务{}",agentBusInfo.getBusNum(),"入网成功状态更新成功");
                        }
                        if(impId!=null){
                          updateImportAgent(impId, Status.STATUS_2.status, "处理成功");
                        }
                        record.setSuccesTime(new Date());
                        record.setNotifyStatus(Status.STATUS_1.status);
                        if(agentPlatFormSynMapper.insert(record)==1){
                            log.info("升级开户接口{}添加记录成功,更新本地平台{}",agentBusInfo.getBusNum(),"入网成功");
                        }
                        //执行修改操作
                        notifyPlatformUpadteByBusId(agentBusInfo.getId(),agentBusInfo.getcUser());
                    }else{
                        agentBusInfo.setBusStatus(BusinessStatus.pause.status);
                        if(1!=agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfo)){
                            log.info("升级开户接口{}平台编号不为空走升级接口,更新业务{}",agentBusInfo.getBusNum(),"入网失败状态更新失败");
                        }else{
                            log.info("升级开户接口{}平台编号不为空走升级接口,更新业务{}",agentBusInfo.getBusNum(),"入网失败状态更新成功");
                        }
                        log.info("升级开户接口{}平台编号不为空走升级接口,请求结果{}",agentBusInfo.getBusNum(),res.getMsg());
                        record.setSuccesTime(new Date());
                        record.setNotifyStatus(Status.STATUS_0.status);
                        if(agentPlatFormSynMapper.insert(record)==1){
                            log.info("开平台{}平台编号不为空走升级接口,更新本地平台{}",agentBusInfo.getBusNum(),"入网成功");
                        }
                        if(impId!=null) {
                            log.info("开平台{}ImportAgent,{}",agentBusInfo.getBusNum(),"更新异常");
                            updateImportAgent(impId, Status.STATUS_3.status, "更新异常");
                        }
                    }

                }
            }
        }else {
            //业务平台不存在调用开户接口
            log.info("升级开户接口{}平台编号为空走开户接口",agentBusInfo.getBusNum());
            notifyPlatform(busId, impId);
        }
    }

    /**
     * 平台开户,入网新增开户
     * @param busId
     * @param impId
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public void notifyPlatform(String busId,String impId)throws Exception{
        log.info("入网开户修改操作: 业务id：{},类型:{}",busId,impId);
        if(StringUtils.isBlank(busId)){
            log.info("入网开户修改操作: notifyPlatform业务ID为空");
            return;
        }
        AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(busId);
        if(agentBusInfo==null){
            log.info("入网开户修改操作: notifyPlatform记录不存在:{}",busId);
            if(impId!=null){
                updateImportAgent(impId,Status.STATUS_1.status,"记录不存在");
            }
            return;
        }
        ImportAgent importAgent = importAgentMapper.selectByPrimaryKey(impId);
        //直签不直发不通知
        if(agentBusInfo.getBusType().equals("8")){
            log.info("直签不直发不通知：agentId:{},busId:{}",agentBusInfo.getAgentId(),agentBusInfo.getId());
            return;
        }
        Agent agent = agentService.getAgentById(agentBusInfo.getAgentId());
        AgentBusInfo agentParent = null;
        if(StringUtils.isNotBlank(agentBusInfo.getBusParent())){
            //取出上级业务
            agentParent = agentBusInfoMapper.selectByPrimaryKey(agentBusInfo.getBusParent());
        }
        AgentNotifyVo agentNotifyVo = new AgentNotifyVo();
        if(StringUtils.isNotBlank( agentBusInfo.getBusRegion())) {
            String[] split = null;
            String[] busRegion = agentBusInfo.getBusRegion().split(",");
            Boolean flag = false;
            for(int i=0;i<busRegion.length;i++){
                if(busRegion[i].equals("0")){
                    flag = true;
                    break;
                }
            }
            if(flag){
                Set<String> dPosRegions = posRegionMapper.queryNationwide();
                split = dPosRegions.toArray(new String[]{});
            }else{
                Set<String> dPosRegions = posRegionService.queryCityByCode(agentBusInfo.getBusRegion());
                split = dPosRegions.toArray(new String[]{});
            }
            agentNotifyVo.setBusiAreas(split);
        }
        if(agent.getAgRegArea()!=null){
            List<String> regionList = getParent(agent.getAgRegArea());
            if(regionList!=null){
                if(regionList.size()==3){
                    agentNotifyVo.setProvince(regionList.get(0));
                    agentNotifyVo.setCity(regionList.get(1));
                    agentNotifyVo.setCityArea(regionList.get(2));
                }else if(regionList.size()==2){
                    agentNotifyVo.setProvince(regionList.get(0));
                    agentNotifyVo.setCity(regionList.get(1));
                }else if(regionList.size()==1){
                    agentNotifyVo.setProvince(regionList.get(0));
                }
            }
        }
        agentNotifyVo.setAgHeadMobile(agent.getAgHeadMobile());
        agentNotifyVo.setOrgName(agent.getAgName());
        agentNotifyVo.setUseOrgan(agentBusInfo.getBusUseOrgan());
        agentNotifyVo.setBusPlatform(agentBusInfo.getBusPlatform());
        agentNotifyVo.setBaseMessage(agent);
        agentNotifyVo.setBusMessage(agentBusInfo);
        agentNotifyVo.setHasS0(agentBusInfo.getDredgeS0().equals(new BigDecimal(1))?"0":"1");
        agentNotifyVo.setDebitTop(agentBusInfo.getDebitCapping());//借记封顶额（元）
        agentNotifyVo.setCkDebitRate(agentBusInfo.getDebitAppearRate());//借记出款费率（%）
        agentNotifyVo.setLowDebitRate(agentBusInfo.getDebitRateLower());//借记费率下限（%）
        if(StringUtils.isNotBlank(agentBusInfo.getBusLoginNum())){
            agentNotifyVo.setLoginName(agentBusInfo.getBusLoginNum());
        }

        PlatForm platForm = platFormMapper.selectByPlatFormNum(agentBusInfo.getBusPlatform());
        //cxinfo 业务类型使用数据库字段
        if(PlatformType.whetherPOS(platForm.getPlatformType())){
            agentNotifyVo.setBusiType(platForm.getPosbusitype());
        }
        agentNotifyVo.setOrgType(OrgType.zQ(agentBusInfo.getBusType())?OrgType.STR.getValue():OrgType.ORG.getValue());
        if(null!=agentParent){
            agentNotifyVo.setSupDorgId(agentParent.getBusNum());
        }

        String id = idService.genId(TabId.a_agent_platformsyn);
        AgentPlatFormSyn record = new AgentPlatFormSyn();
        AgentResult result = AgentResult.fail();
        try {
            record.setId(id);
            record.setNotifyTime(new Date());
            record.setAgentId(agentBusInfo.getAgentId());
            record.setBusId(agentBusInfo.getId());
            record.setPlatformCode(agentBusInfo.getBusPlatform());
            record.setVersion(Status.STATUS_1.status);
            record.setcTime(new Date());
            record.setNotifyStatus(Status.STATUS_0.status);
            record.setNotifyCount(Status.STATUS_1.status);
            record.setcUser(agentBusInfo.getcUser());
            //根据数据类型设置接口类型
            record.setNotifyType(importAgent==null?NotifyType.NetInAdd.getValue():AgImportType.getAgImportTypeByValue(importAgent.getDatatype()).notifyType);
            if(platForm==null){
                log.info("入网开户修改操作: 通知pos手刷业务平台未知");
            }
            if(PlatformType.whetherPOS(platForm.getPlatformType())){
                //智能POS代理商名加N区分 cxinfo pos商户名称使用数据库配置字段
                if(org.apache.commons.lang.StringUtils.isNotEmpty(platForm.getPosanameprefix())){
                    agentNotifyVo.setOrgName(agentNotifyVo.getOrgName());
                    agentNotifyVo.setActivityType(platForm.getPosanameprefix());
                }
                //POS传递业务ID
                agentNotifyVo.setUniqueId(agentBusInfo.getId());
                String sendJson = JsonUtil.objectToJson(agentNotifyVo);
                record.setSendJson(sendJson);
            }
            if(platForm.getPlatformType().equals(PlatformType.MPOS.getValue())){
                PayComp payComp = apaycompService.selectById(agentBusInfo.getCloPayCompany());
                AgentColinfo agentColinfo = agentColinfoService.selectByAgentIdAndBusId(agent.getId(), agentBusInfo.getId());
                agentColinfo.setAccountId(agentBusInfo.getCloPayCompany());
                agentColinfo.setAccountName(payComp.getComName());
                agentNotifyVo.setColinfoMessage(agentColinfo);
                //首刷传递代理商ID
                agentNotifyVo.setUniqueId(agentBusInfo.getAgentId());
                String sendJson = JsonUtil.objectToJson(agentNotifyVo);
                record.setSendJson(sendJson);
            }
            for (PlatformSynService platformSynService : platformSynServiceList) {
                log.info("开户接口,平台编号:{} 服务类:{}", agentBusInfo.getBusNum(), platformSynService.getClass().getSimpleName());
                if (platformSynService.isMyPlatform(agentBusInfo.getId())) {
                    result = platformSynService.httpRequestNetIn(agentNotifyVo);
                }
            }

            log.info("入网开户修改操作: ,业务id：{},返回结果:{}",busId,result);
            record.setNotifyJson(String.valueOf(result.getData()));
        } catch (Exception e) {
            log.info("入网开户修改操作: 通知pos手刷http请求异常:{}",e.getMessage());
            record.setNotifyCount(new BigDecimal(1));
            record.setNotifyJson(e.getLocalizedMessage());
            result = AgentResult.fail(e.getLocalizedMessage());
        }
        if(null!=result && !"".equals(result) && result.isOK()){
            record.setSuccesTime(new Date());
            record.setNotifyStatus(Status.STATUS_1.status);
        }
        if(agentPlatFormSynMapper.insert(record)==1 && null!=result && result.isOK()){
            log.info("入网开户修改操作: 接收入网更新入网状态开始,业务id：{},返回结果:{}",busId);
            //更新业务编号
            AgentBusInfo updateBusInfo = agentBusInfoMapper.selectByPrimaryKey(agentBusInfo.getId());
            JSONObject jsonObject = JSONObject.parseObject(String.valueOf(result.getData()));
            updateBusInfo.setBusNum(jsonObject.getString("orgId"));
            if(PlatformType.whetherPOS(platForm.getPlatformType())){
                updateBusInfo.setBusLoginNum(jsonObject.getString("loginName"));
            }else if(platForm.getPlatformType().equals(PlatformType.MPOS.getValue())){
                updateBusInfo.setBusLoginNum(jsonObject.getString("orgId"));
            }
            //代理商修改也会走这里
            // cxinfo  如果有已有效的业务信息就为已入网，否则为已入网未激活 业务平台状态：1启用和0注销2开户未激活，注销是指代理商解除某项业务平台合作。
            if(updateBusInfo.getBusStatus()==null || !updateBusInfo.getBusStatus().equals(Status.STATUS_1.status)){
                PlatFormExample example  = new PlatFormExample();
                example.or().andPlatformNumEqualTo(agentBusInfo.getBusPlatform()).andStatusEqualTo(Status.STATUS_1.status);
                List<PlatForm>  platForms = platFormMapper.selectByExample(example);
                if(platForms.size()==1){
                    PlatForm p = platForms.get(0);
                    //首刷初始状态为开户未激活s
                    if(PlatformType.MPOS.code.equals(p.getPlatformType()+"")){
                        updateBusInfo.setBusStatus(BusinessStatus.inactive.status);
                    }else{
                        updateBusInfo.setBusStatus(BusinessStatus.Enabled.status);
                    }
                }else{
                    updateBusInfo.setBusStatus(BusinessStatus.Enabled.status);
                }
            }else{
                updateBusInfo.setBusStatus(BusinessStatus.Enabled.status);
            }
            int upResult2 = agentBusInfoMapper.updateByPrimaryKeySelective(updateBusInfo);
            log.info("入网开户修改操作: 接收入网更新入网状态,业务id：{},upResult2:{}",upResult2);
            //更新入网状态
            // cxinfo   如果有已有效的业务信息就为已入网，否则为已入网未激活 0未入网，1已入网，2已入网未激活
            Agent updateAgent = new Agent();
            updateAgent.setId(agent.getId());
            updateAgent.setVersion(agent.getVersion());
            //检查是否可以更新为入网状态
            if(agentService.checkAgentIsIn(agent.getId()).isOK()){
                updateAgent.setcIncomStatus(AgentInStatus.IN.status);//已入网
            }else{
                updateAgent.setcIncomStatus(AgentInStatus.NO_ACT.status);//入网未激活
            }
            Date nowDate = new Date();
            updateAgent.setcIncomTime(nowDate);
            updateAgent.setcUtime(nowDate);
            int upResult1 = agentMapper.updateByPrimaryKeySelective(updateAgent);
            log.info("入网开户修改操作: 接收入网更新入网状态,业务id：{},upResult1:{}",upResult1);
            //更新任务记录
            if(upResult1!=1 || upResult2!=1){
                if(impId!=null) {
                    updateImportAgent(impId, Status.STATUS_3.status, "更新异常");
                }
                throw new Exception("入网开户修改操作: 更新入网状态/业务编号异常");
            }
            if(impId!=null){
                int clRes = updateImportAgent(impId, Status.STATUS_2.status, "处理成功");
                log.info("入网开户修改操作: 接收入网更新入网状态,业务id：{},clRes:{}",clRes);
            }
        }else{
            log.info("入网开户修改操作: 接收入网更新入网状态开始,业务id：{},返回结果:{}",busId,"开通业务失败");
            //更新业务编号
            AgentBusInfo updateBusInfo = agentBusInfoMapper.selectByPrimaryKey(agentBusInfo.getId());
            updateBusInfo.setBusStatus(BusinessStatus.pause.status);
            if(agentBusInfoMapper.updateByPrimaryKeySelective(updateBusInfo)!=1){
                log.info("入网开户修改操作: 接收入网更新入网状态开始,业务id：{},返回结果:{},更新数据库失败",busId,"开通业务失败");
            }
            updateImportAgent(impId, Status.STATUS_3.status, result.getMsg());
        }
    }

    /**
     * 更新
     * @param impId
     * @return
     */
    private int updateImportAgent(String impId,BigDecimal dealstatus,String dealMsg){
        int i = 0;
        if(impId!=null){
            ImportAgent importAgent = importAgentMapper.selectByPrimaryKey(impId);
            ImportAgent impRecord = new ImportAgent();
            impRecord.setId(impId);
            impRecord.setVersion(importAgent.getVersion());
            impRecord.setDealstatus(dealstatus);
            impRecord.setDealmsg(dealMsg);
            impRecord.setDealTime(new Date());
            i = importAgentMapper.updateByPrimaryKeySelective(impRecord);
        }
        return i;
    }

    /**
     * 递归获取层级
     * @param busRegion
     * @return
     */
    public List<String> getParent(String busRegion){
        List<String> resultList = new ArrayList<>();
        Region region = queryParent(busRegion);
        resultList.add(0,region.getrCode());
        while (true){
            if(!region.getpCode().equals("0")){
                region = queryParent(region.getpCode());
                resultList.add(0,region.getrCode());
            }else{
                break;
            }
        }
        return resultList;
    }

    private Region queryParent(String busRegion){
        RegionExample example = new RegionExample();
        RegionExample.Criteria criteria = example.createCriteria();
        criteria.andRCodeEqualTo(busRegion);
        List<Region> regions = regionMapper.selectByExample(example);
        return regions.get(0);
    }


    @Override
    public AgentPlatFormSyn findByBusId(String busId){
        AgentPlatFormSynExample example = new AgentPlatFormSynExample();
        AgentPlatFormSynExample.Criteria criteria = example.createCriteria();
        criteria.andBusIdEqualTo(busId);
        List<AgentPlatFormSyn> agentPlatFormSyns = agentPlatFormSynMapper.selectByExample(example);
        if(null==agentPlatFormSyns || agentPlatFormSyns.size()!=1){
            return null;
        }
        return agentPlatFormSyns.get(0);
    }


    @Override
    public PageInfo queryList(Page page, AgentPlatFormSyn agentPlatFormSyn) {
        Map<String, Object> map = new HashMap<>();
        if(null!=agentPlatFormSyn.getAgentId()){
            map.put("agentId",agentPlatFormSyn.getAgentId());
        }
        if(StringUtils.isNotBlank(agentPlatFormSyn.getBusId())){
            map.put("busId",agentPlatFormSyn.getBusId());
        }
        if(StringUtils.isNotBlank(agentPlatFormSyn.getNotifyType())){
            map.put("notifyType",agentPlatFormSyn.getNotifyType());
        }
        List<AgentPlatFormSyn> list = agentPlatFormSynMapper.queryList(map, page);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(list);
        pageInfo.setTotal(agentPlatFormSynMapper.queryCount(map));
        return pageInfo;
    }


}
