package com.ryx.credit.service.impl.agent;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.common.util.agentUtil.AESUtil;
import com.ryx.credit.common.util.agentUtil.RSAUtil;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.*;
import com.ryx.credit.dao.bank.DPosRegionMapper;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.bank.DPosRegion;
import com.ryx.credit.pojo.admin.vo.AgentNotifyVo;
import com.ryx.credit.service.agent.AgentNotifyService;
import com.ryx.credit.service.agent.AgentService;
import com.ryx.credit.service.agent.PlatformSynService;
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
public class AgentNotifyServiceImpl implements AgentNotifyService {

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

    @PostConstruct
    public void init(){
        platformSynServiceList = new ArrayList<PlatformSynService>();
        platformSynServiceList.add(platformSynServicePos);
        platformSynServiceList.add(platformSynServiceMpos);
    }


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
                    agentBusInfo.setBusStatus(Status.STATUS_1.status);
                    if(agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfo)==1){
                        log.info("开平台升级接口请求发起用户{}开平台{}平台编号不为空走升级接口,更新本地业务平台{},更新成功",userid,agentBusInfo.getId(),"入网成功");
                    }else{
                        log.info("开平台升级接口请求发起用户{}开平台{}平台编号不为空走升级接口,更新本地业务平台{},更新失败",userid,agentBusInfo.getId(),"入网成功");
                    }

                    //执行账户修改
                    notifyPlatformUpadteByBusId(agentBusInfo.getId(),userid);

                }else{

                    agentBusInfo.setBusStatus(Status.STATUS_0.status);
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
        Agent agent = agentService.getAgentById(agentBusInfo.getAgentId());
        AgentBusInfo agentParent = null;
        if(StringUtils.isNotBlank(agentBusInfo.getBusParent())){
            //取出上级业务
            agentParent = agentBusInfoMapper.selectByPrimaryKey(agentBusInfo.getBusParent());
        }
        //业务区域
        String[] split = new String[1];
        if(StringUtils.isNotBlank(agentBusInfo.getBusRegion())){
            if(agentBusInfo.getBusRegion().equals("0")){
                Set<String> dPosRegions = posRegionMapper.queryNationwide();
                split = dPosRegions.toArray(new String[]{});
            }else{
                Set<String> dPosRegions = posRegionService.queryCityByCode(agentBusInfo.getBusRegion());
                split = dPosRegions.toArray(new String[]{});
            }
        }
        //通知对象
        AgentNotifyVo agentNotifyVo = new AgentNotifyVo();
        agentNotifyVo.setBusiAreas(split);

        agentNotifyVo.setAgHeadMobile(agent.getAgHeadMobile());
        agentNotifyVo.setOrgId(agentBusInfo.getBusNum());
        agentNotifyVo.setOrgName(agent.getAgName());
        agentNotifyVo.setUseOrgan(agentBusInfo.getBusUseOrgan());
        agentNotifyVo.setBusPlatform(agentBusInfo.getBusPlatform());
        agentNotifyVo.setBaseMessage(agent);
        agentNotifyVo.setBusMessage(agentBusInfo);
        agentNotifyVo.setHasS0(agentBusInfo.getDredgeS0().equals(new BigDecimal(1))?"0":"1");
        PlatForm platForm = platFormMapper.selectByPlatFormNum(agentBusInfo.getBusPlatform());
        if(platForm.getPlatformType().equals(PlatformType.POS.getValue()) || platForm.getPlatformType().equals(PlatformType.ZPOS.getValue())){
            agentNotifyVo.setBusiType(platForm.getPlatformType().equals(PlatformType.POS.getValue())?"01":"02");
        }
        //如果是直签 就传02：直签机构  否则就传递 01：普通机构
        Dict dictByValue = dictOptionsService.findDictByValue(DictGroup.AGENT.name(), DictGroup.BUS_TYPE.name(), agentBusInfo.getBusType());
        agentNotifyVo.setOrgType(dictByValue.getdItemname().equals(OrgType.STR.getContent())?OrgType.STR.getValue():OrgType.ORG.getValue());
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

            if(platForm==null){
                log.info("已有编号进行入网修改：通知pos手刷业务平台未知");
                throw new MessageException("通知pos手刷业务平台未知");
            }

            //调用POST接口
            if(platForm.getPlatformType().equals(PlatformType.POS.getValue()) || platForm.getPlatformType().equals(PlatformType.ZPOS.getValue())){
                //POS传递的唯一ID是业务平台记录ID
                agentNotifyVo.setUniqueId(agentBusInfo.getId());
                log.info("已有编号进行入网修改：接收入网请求开始POS: busId：{},userId:{},data:{}",busId,userId,JSONObject.toJSONString(agentNotifyVo));
                String sendJson = JsonUtil.objectToJson(agentNotifyVo);
                record.setSendJson(sendJson);
                result = httpRequestForPos(agentNotifyVo);
                log.info("已有编号进行入网修改：接收入网请求结束POS: busId：{},userId:{},data:{}",busId,userId,JSONObject.toJSONString(agentNotifyVo));
            }

            //调用首刷接口
            if(platForm.getPlatformType().equals(PlatformType.MPOS.getValue())){
                //MPOS传递的唯一id为代理商唯一ID
                agentNotifyVo.setUniqueId(agentBusInfo.getAgentId());
                log.info("已有编号进行入网修改：接收入网请求开始MPOS: busId：{},userId:{},data:{}",busId,userId,JSONObject.toJSONString(agentNotifyVo));
                String sendJson = JsonUtil.objectToJson(agentNotifyVo);
                record.setSendJson(sendJson);
                result = httpRequestForMPOS(agentNotifyVo);
                log.info("已有编号进行入网修改：接收入网请求结束MPOS: busId：{},userId:{},data:{}",busId,userId,JSONObject.toJSONString(agentNotifyVo));
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
        if(null!=result && !"".equals(result) && result.isOK()){
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
                AgentBusInfo updateBusInfo = new AgentBusInfo();
                updateBusInfo.setVersion(agentBusInfo.getVersion());
                updateBusInfo.setId(agentBusInfo.getId());
                if(agentBusInfo.getBusNum()!=null){
                    updateBusInfo.setBusNum(jsonObject.getString("orgId"));
                    updateBusInfo.setBusStatus(Status.STATUS_1.status);
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

                        agentBusInfo.setBusStatus(Status.STATUS_1.status);
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

                        agentBusInfo.setBusStatus(Status.STATUS_0.status);
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
        Agent agent = agentService.getAgentById(agentBusInfo.getAgentId());
        AgentBusInfo agentParent = null;
        if(StringUtils.isNotBlank(agentBusInfo.getBusParent())){
            //取出上级业务
            agentParent = agentBusInfoMapper.selectByPrimaryKey(agentBusInfo.getBusParent());
        }
        AgentNotifyVo agentNotifyVo = new AgentNotifyVo();
        if(StringUtils.isNotBlank( agentBusInfo.getBusRegion())) {
            String[] split = null;
            if(agentBusInfo.getBusRegion().equals("0")){
                Set<String> dPosRegions = posRegionMapper.queryNationwide();
                split = dPosRegions.toArray(new String[]{});
            }else{
                Set<String> dPosRegions = posRegionService.queryCityByCode(agentBusInfo.getBusRegion());
                split = dPosRegions.toArray(new String[]{});
            }
            agentNotifyVo.setBusiAreas(split);
        }

        agentNotifyVo.setAgHeadMobile(agent.getAgHeadMobile());
        agentNotifyVo.setOrgName(agent.getAgName());
        agentNotifyVo.setUseOrgan(agentBusInfo.getBusUseOrgan());
        agentNotifyVo.setBusPlatform(agentBusInfo.getBusPlatform());
        agentNotifyVo.setBaseMessage(agent);
        agentNotifyVo.setBusMessage(agentBusInfo);
        agentNotifyVo.setHasS0(agentBusInfo.getDredgeS0().equals(new BigDecimal(1))?"0":"1");
        PlatForm platForm = platFormMapper.selectByPlatFormNum(agentBusInfo.getBusPlatform());
        if(platForm.getPlatformType().equals(PlatformType.POS.getValue()) || platForm.getPlatformType().equals(PlatformType.ZPOS.getValue())){
            agentNotifyVo.setBusiType(platForm.getPlatformType().equals(PlatformType.POS.getValue())?"01":"02");
        }
        Dict dictByValue = dictOptionsService.findDictByValue(DictGroup.AGENT.name(), DictGroup.BUS_TYPE.name(), agentBusInfo.getBusType());
        agentNotifyVo.setOrgType(dictByValue.getdItemname().equals(OrgType.STR.getContent())?OrgType.STR.getValue():OrgType.ORG.getValue());
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
            record.setNotifyCount(Status.STATUS_1.status);
            record.setcUser(agentBusInfo.getcUser());
            if(platForm==null){
                log.info("入网开户修改操作: 通知pos手刷业务平台未知");
            }
            if(platForm.getPlatformType().equals(PlatformType.POS.getValue()) || platForm.getPlatformType().equals(PlatformType.ZPOS.getValue())){

                //POS传递业务ID
                agentNotifyVo.setUniqueId(agentBusInfo.getId());
                String sendJson = JsonUtil.objectToJson(agentNotifyVo);
                record.setSendJson(sendJson);
                result = httpRequestForPos(agentNotifyVo);
            }
            if(platForm.getPlatformType().equals(PlatformType.MPOS.getValue())){

                //首刷传递代理商ID
                agentNotifyVo.setUniqueId(agentBusInfo.getAgentId());
                String sendJson = JsonUtil.objectToJson(agentNotifyVo);
                record.setSendJson(sendJson);
                result = httpRequestForMPOS(agentNotifyVo);
            }
            log.info("入网开户修改操作: ,业务id：{},返回结果:{}",busId,result);
            record.setNotifyJson(String.valueOf(result.getData()));
        } catch (Exception e) {
            log.info("入网开户修改操作: 通知pos手刷http请求异常:{}",e.getMessage());
            record.setNotifyCount(new BigDecimal(1));
            result = AgentResult.fail(e.getLocalizedMessage());
        }
        if(null!=result && !"".equals(result) && result.isOK()){
            record.setSuccesTime(new Date());
            record.setNotifyStatus(Status.STATUS_1.status);
        }

        int czResult =  agentPlatFormSynMapper.insert(record);

        if(czResult==1 && null!=result && !"".equals(result) && result.isOK()){
            log.info("入网开户修改操作: 接收入网更新入网状态开始,业务id：{},返回结果:{}",busId);
            //更新入网状态
            Agent updateAgent = new Agent();
            updateAgent.setId(agent.getId());
            updateAgent.setVersion(agent.getVersion());
            updateAgent.setcIncomStatus(AgentInStatus.IN.status);
            Date nowDate = new Date();
            updateAgent.setcIncomTime(nowDate);
            updateAgent.setcUtime(nowDate);
            int upResult1 = agentMapper.updateByPrimaryKeySelective(updateAgent);
            log.info("入网开户修改操作: 接收入网更新入网状态,业务id：{},upResult1:{}",upResult1);
            //更新业务编号
            AgentBusInfo updateBusInfo = new AgentBusInfo();
            JSONObject jsonObject = JSONObject.parseObject(String.valueOf(result.getData()));
            updateBusInfo.setVersion(agentBusInfo.getVersion());
            updateBusInfo.setId(agentBusInfo.getId());
            updateBusInfo.setBusNum(jsonObject.getString("orgId"));
            updateBusInfo.setBusStatus(Status.STATUS_1.status);
            int upResult2 = agentBusInfoMapper.updateByPrimaryKeySelective(updateBusInfo);
            log.info("入网开户修改操作: 接收入网更新入网状态,业务id：{},upResult2:{}",upResult2);
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
    private List<String> getParent(String busRegion){
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

    private AgentResult httpRequestForPos(AgentNotifyVo agentNotifyVo)throws Exception{
        try {

            String cooperator = com.ryx.credit.util.Constants.cooperator;
            String charset = "UTF-8"; // 字符集
            String tranCode = "ORG001"; // 交易码
            String reqMsgId = UUID.randomUUID().toString().replace("-", ""); // 请求流水
            String reqDate = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"); // 请求时间

            JSONObject jsonParams = new JSONObject();
            JSONObject data = new JSONObject();
            jsonParams.put("version", "1.0.0");
            jsonParams.put("msgType", "01");
            jsonParams.put("reqDate", reqDate);
            data.put("uniqueId",agentNotifyVo.getUniqueId());
            data.put("useOrgan",agentNotifyVo.getUseOrgan()); //使用范围
            data.put("orgName",agentNotifyVo.getOrgName());
            data.put("busiAreas",agentNotifyVo.getBusiAreas());
            data.put("hasS0",agentNotifyVo.getHasS0());
            if(StringUtils.isNotBlank(agentNotifyVo.getOrgId())){
                data.put("orgId",agentNotifyVo.getOrgId());
            }
//            if(StringUtils.isNotBlank(agentNotifyVo.getProvince()))
//                data.put("province",agentNotifyVo.getProvince());
//            if(StringUtils.isNotBlank(agentNotifyVo.getCity()))
//                data.put("city",agentNotifyVo.getCity());
//            if(StringUtils.isNotBlank(agentNotifyVo.getCity()))
//                data.put("cityArea",agentNotifyVo.getCity());
            data.put("orgType",agentNotifyVo.getOrgType());
            if(agentNotifyVo.getOrgType().equals(OrgType.STR.getValue()))
                data.put("supDorgId",agentNotifyVo.getSupDorgId());

            jsonParams.put("data", data);
            String plainXML = jsonParams.toString();
            // 请求报文加密开始
            String keyStr = AESUtil.getAESKey();
            byte[] plainBytes = plainXML.getBytes(charset);
            byte[] keyBytes = keyStr.getBytes(charset);
            String encryptData = new String(Base64.encodeBase64((AESUtil.encrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null))), charset);
            String signData = new String(Base64.encodeBase64(RSAUtil.digitalSign(plainBytes, Constants.privateKey, "SHA1WithRSA")), charset);
            String encrtptKey = new String(org.apache.commons.codec.binary.Base64.encodeBase64(RSAUtil.encrypt(keyBytes, Constants.publicKey, 2048, 11, "RSA/ECB/PKCS1Padding")), charset);
            // 请求报文加密结束

            Map<String, String> map = new HashMap<>();
            map.put("encryptData", encryptData);
            map.put("encryptKey", encrtptKey);
            map.put("cooperator", cooperator);
            map.put("signData", signData);
            map.put("tranCode", tranCode);
            map.put("reqMsgId", reqMsgId);

            log.info("通知pos请求参数:{}",map);
            String httpResult = HttpClientUtil.doPost(AppConfig.getProperty("agent_pos_notify_url"), map);
            JSONObject jsonObject = JSONObject.parseObject(httpResult);
            if (!jsonObject.containsKey("encryptData") || !jsonObject.containsKey("encryptKey")) {
                System.out.println("请求异常======" + httpResult);
                throw new Exception("http请求异常");
            } else {
                String resEncryptData = jsonObject.getString("encryptData");
                String resEncryptKey = jsonObject.getString("encryptKey");
                byte[] decodeBase64KeyBytes = Base64.decodeBase64(resEncryptKey.getBytes(charset));
                byte[] merchantAESKeyBytes = RSAUtil.decrypt(decodeBase64KeyBytes, Constants.privateKey, 2048, 11, "RSA/ECB/PKCS1Padding");
                byte[] decodeBase64DataBytes = Base64.decodeBase64(resEncryptData.getBytes(charset));
                byte[] merchantXmlDataBytes = AESUtil.decrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
                String respXML = new String(merchantXmlDataBytes, charset);
                log.info("通知pos返回参数：{}",respXML);

                // 报文验签
                String resSignData = jsonObject.getString("signData");
                byte[] signBytes = Base64.decodeBase64(resSignData);
                if (!RSAUtil.verifyDigitalSign(respXML.getBytes(charset), signBytes, Constants.publicKey, "SHA1WithRSA")) {
                    System.out.println("签名验证失败");
                } else {
                    System.out.println("签名验证成功");
                    if (respXML.contains("data") && respXML.contains("orgId")){
                        JSONObject respXMLObj = JSONObject.parseObject(respXML);
                        JSONObject dataObj = JSONObject.parseObject(respXMLObj.get("data").toString());
                        System.out.println(dataObj);
                        return AgentResult.ok(dataObj);
                    }else{
                        log.info("http请求超时返回错误:{}",httpResult);
                        throw new Exception("http返回有误");
                    }
                }
                return new AgentResult(500,"http请求异常","");
            }
        } catch (Exception e) {
            log.info("http请求超时:{}",e.getMessage());
            throw e;
        }
    }

    private AgentResult httpRequestForMPOS(AgentNotifyVo agentNotifyVo)throws Exception{

        try {
            Map<String,Object> jsonParams = new HashMap<>();
            jsonParams.put("uniqueId",agentNotifyVo.getUniqueId());
            if(StringUtils.isNotBlank(agentNotifyVo.getOrgId()))
                jsonParams.put("orgId",agentNotifyVo.getOrgId());
            jsonParams.put("useOrgan",agentNotifyVo.getUseOrgan()); //使用范围
            jsonParams.put("orgName",agentNotifyVo.getOrgName());
            jsonParams.put("busPlatform",agentNotifyVo.getBusPlatform());
            jsonParams.put("agHeadMobile",agentNotifyVo.getAgHeadMobile());
            jsonParams.put("baseMessage",agentNotifyVo.getBaseMessage());
            jsonParams.put("busMessage",agentNotifyVo.getBusMessage());
            if(StringUtils.isNotBlank(agentNotifyVo.getProvince()))
                jsonParams.put("province",agentNotifyVo.getProvince());
            if(StringUtils.isNotBlank(agentNotifyVo.getCity()))
                jsonParams.put("city",agentNotifyVo.getCity());
            if(StringUtils.isNotBlank(agentNotifyVo.getCity()))
                jsonParams.put("cityArea",agentNotifyVo.getCity());
            jsonParams.put("orgType",agentNotifyVo.getOrgType());
            if(agentNotifyVo.getOrgType().equals(OrgType.STR.getValue()))
                jsonParams.put("supDorgId",agentNotifyVo.getSupDorgId());

            String json = JsonUtil.objectToJson(jsonParams);
            log.info("通知手刷请求参数：{}",json);

            //发送请求
            String httpResult = HttpClientUtil.doPostJson(AppConfig.getProperty("agent_mpos_notify_url"), json);

            log.info("通知手刷返回参数：{}",httpResult);
            if (httpResult.contains("data") && httpResult.contains("orgId")){
                JSONObject respXMLObj = JSONObject.parseObject(httpResult);
                JSONObject dataObj = JSONObject.parseObject(respXMLObj.get("data").toString());
                System.out.println(dataObj);
                return AgentResult.ok(dataObj);
            }else{
                log.info("http请求超时返回错误:{}",httpResult);
                throw new Exception("http返回有误");
            }
        } catch (Exception e) {
            log.info("http请求超时:{}",e.getMessage());
            throw new Exception("http请求超时");
        }
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
        List<AgentPlatFormSyn> list = agentPlatFormSynMapper.queryList(map, page);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(list);
        pageInfo.setTotal(agentPlatFormSynMapper.queryCount(map));
        return pageInfo;
    }

}
