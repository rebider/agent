package com.ryx.credit.service.impl.agent.netInPort;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.*;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.vo.AgentBusInfoVo;
import com.ryx.credit.pojo.admin.vo.AgentFreezePort;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.FreezeDetail;
import com.ryx.credit.service.agent.AgentBusinfoFreezeService;
import com.ryx.credit.service.agent.AgentFreezeService;
import com.ryx.credit.service.agent.AgentService;
import com.ryx.credit.service.agent.netInPort.AgentNetInHttpService;
import com.ryx.credit.service.agent.netInPort.AgentNetInNotityService;
import com.ryx.credit.service.dict.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/***
 * @Author liudh
 * @Description //TODO 
 * @Date 2019/5/21 16:16
 * @Param
 * @return
 **/
@Service("agentNetInNotityService")
public class AgentNetInNotityServiceImpl implements AgentNetInNotityService {

    private static Logger log = LoggerFactory.getLogger(AgentNetInNotityServiceImpl.class);
    @Resource(name="agentHttpPosServiceImpl")
    private  AgentNetInHttpService agentHttpPosServiceImpl;
    @Resource(name="agentHttpMposServiceImpl")
    private  AgentNetInHttpService  agentHttpMposServiceImpl;
    @Resource(name="agentHttpRDBMposServiceImpl")
    private  AgentNetInHttpService  agentHttpRDBMposServiceImpl;
    @Resource(name="agentHttpRJPosServiceImpl")
    private AgentNetInHttpService agentHttpRJPosServiceImpl;
    @Resource(name="agentHttpRHBPosServiceImpl")
    private  AgentNetInHttpService  agentHttpRHBPosServiceImpl;
    @Resource(name="agentHttpSsPosServiceImpl")
    private  AgentNetInHttpService  agentHttpSsPosServiceImpl;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private AgentService agentService;
    @Autowired
    private RegionMapper regionMapper;
    @Autowired
    private PlatFormMapper platFormMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private AgentPlatFormSynMapper agentPlatFormSynMapper;
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Autowired
    private BusActRelMapper busActRelMapper;
    @Autowired
    private AgentFreezeService agentFreezeService;
    @Autowired
    private AgentBusinfoFreezeService agentBusinfoFreezeService;


    /**
     * 通知列表
     * @param page
     * @param agentPlatFormSyn
     * @return
     */
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

    /**
     * 通知业务平台 统一入口
     * @param busId
     * @param notifyType
     */
    @Override
    public void asynNotifyPlatform(String busId,String notifyType){
        if(notifyType.equals(NotifyType.NetInAdd.getValue())){
            AgentBusInfoExample AgBusExample = new AgentBusInfoExample();
            AgentBusInfoExample.Criteria AgBusCriteria = AgBusExample.createCriteria();
            AgBusCriteria.andAgentIdEqualTo(busId);
            AgBusCriteria.andStatusEqualTo(Status.STATUS_1.status);
            List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(AgBusExample);
            for (AgentBusInfo agentBusInfo : agentBusInfos) {
                //升级
                if(StringUtils.isNotBlank(agentBusInfo.getBusNum())){
                    try {
                        upgrade(agentBusInfo.getId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    netIn(agentBusInfo.getId(),notifyType);
                }
            }
        }else if(notifyType.equals(NotifyType.NetInEdit.getValue())) {
            AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(busId);
            netInApplyEdit(agentBusInfo.getId(),notifyType);

        }else if(notifyType.equals(NotifyType.NetInAddBus.getValue())) {
            AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(busId);
            if(StringUtils.isNotBlank(agentBusInfo.getBusNum())){
                try {
                    upgrade(agentBusInfo.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                netIn(busId,notifyType);
            }
        }
    }


    /**
     * 手动通知入口
     * @param busId
     * @param notifyType  NotifyType.NetInAdd.getValue()
     */
    @Override
    public void netIn(String busId,String notifyType){
        threadPoolTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                   Thread.sleep(1500);
                } catch (InterruptedException e) {

                }
                log.info("入网开户修改操作: 业务id：{}",busId);
                if(StringUtils.isBlank(busId)){
                    log.info("入网开户修改操作: notifyPlatform业务ID为空");
                    return;
                }
                AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(busId);
                if(agentBusInfo==null){
                    log.info("入网开户修改操作: notifyPlatform记录不存在:{}",busId);
                    return;
                }
                //直签不直发不通知
                if(agentBusInfo.getBusType().equals("8")){
                    log.info("直签不直发不通知：agentId:{},busId:{}",agentBusInfo.getAgentId(),agentBusInfo.getId());
                    return;
                }
                Agent agent = agentService.getAgentById(agentBusInfo.getAgentId());
                PlatForm platForm = platFormMapper.selectByPlatFormNum(agentBusInfo.getBusPlatform());
                if(platForm==null){
                    log.info("入网开户修改操作: 通知pos手刷业务平台未知");
                    return;
                }
                String id = idService.genId(TabId.a_agent_platformsyn);
                AgentPlatFormSyn record = new AgentPlatFormSyn();
                AgentResult result = null;
                try {
                    //组装参数
                    Map<String, Object> paramMap = new HashMap<>();
                    Map<String, Object> reqMap = new HashMap<>();
                    reqMap.put("agentBusInfo",agentBusInfo);
                    reqMap.put("agent",agent);
                    reqMap.put("platForm",platForm);
                    record = agentPlatFormSynParam(record, agentBusInfo, notifyType);
                    if(PlatformType.whetherPOS(platForm.getPlatformType())){
                        paramMap = agentHttpPosServiceImpl.packageParam(reqMap);
                    }else if(platForm.getPlatformType().equals(PlatformType.MPOS.getValue())){
                        paramMap = agentHttpMposServiceImpl.packageParam(reqMap);
                    }else if(platForm.getPlatformType().equals(PlatformType.RDBPOS.getValue())){
                        paramMap = agentHttpRDBMposServiceImpl.packageParam(reqMap);
                    }else if(platForm.getPlatformType().equals(PlatformType.RJPOS.getValue())){
                        paramMap = agentHttpRJPosServiceImpl.packageParam(reqMap);
                    }else if(platForm.getPlatformType().equals(PlatformType.RHPOS.getValue())){
                        paramMap = agentHttpRHBPosServiceImpl.packageParam(reqMap);
                    }else if(platForm.getPlatformType().equals(PlatformType.SSPOS.getValue())){
                        paramMap = agentHttpSsPosServiceImpl.packageParam(reqMap);
                    }

                    record.setSendJson(JSONObject.toJSONString(paramMap));
                    if(PlatformType.whetherPOS(platForm.getPlatformType())){
                        result = agentHttpPosServiceImpl.httpRequestNetIn(paramMap);
                    }else if(platForm.getPlatformType().equals(PlatformType.MPOS.getValue())){
                        result = agentHttpMposServiceImpl.httpRequestNetIn(paramMap);
                    }else if(platForm.getPlatformType().equals(PlatformType.RDBPOS.getValue())){
                        result = agentHttpRDBMposServiceImpl.httpRequestNetIn(paramMap);
                    }else if(platForm.getPlatformType().equals(PlatformType.RJPOS.getValue())){
                        result = agentHttpRJPosServiceImpl.httpRequestNetIn(paramMap);
                    }else if(platForm.getPlatformType().equals(PlatformType.RHPOS.getValue())){
                        result = agentHttpRHBPosServiceImpl.httpRequestNetIn(paramMap);
                    }else if(platForm.getPlatformType().equals(PlatformType.SSPOS.getValue())){
                        result = agentHttpSsPosServiceImpl.httpRequestNetIn(paramMap);
                    }

                    log.info("入网开户修改操作: ,业务id：{},返回结果:{}",busId,JSONObject.toJSONString(result));
                    record.setNotifyJson(String.valueOf(result.getData()));
                } catch (Exception e) {
                    log.info("入网开户修改操作: 通知pos手刷http请求异常:{}",e.getMessage());
                    e.printStackTrace();
                    record.setNotifyCount(new BigDecimal(1));
                    record.setNotifyJson(e.getLocalizedMessage());
                    result = AgentResult.fail(e.getLocalizedMessage());
                }finally {
                    if(StringUtils.isBlank(record.getId())){
                        record.setId(idService.genId(TabId.a_agent_platformsyn));
                    }
                    agentPlatFormSynInsert(record,result,busId,platForm,agentBusInfo,agent);
                }
            }
        });
    }


    /**
     * 通知记录参数组装
     * @param record
     * @param agentBusInfo
     * @param notifyType
     * @return
     */
    private AgentPlatFormSyn agentPlatFormSynParam(AgentPlatFormSyn record,AgentBusInfo agentBusInfo,String notifyType){

        record.setId(idService.genId(TabId.a_agent_platformsyn));
        record.setNotifyTime(new Date());
        record.setAgentId(agentBusInfo.getAgentId());
        record.setBusId(agentBusInfo.getId());
        record.setPlatformCode(agentBusInfo.getBusPlatform());
        record.setVersion(Status.STATUS_1.status);
        record.setcTime(new Date());
        record.setNotifyStatus(Status.STATUS_0.status);
        record.setNotifyCount(Status.STATUS_1.status);
        record.setcUser(agentBusInfo.getcUser());
        record.setNotifyType(notifyType);
        return record;
    }

    /**
     * 入网/修改后处理
     * @param record
     * @param result
     * @param busId
     * @param platForm
     * @param agentBusInfo
     * @param agent
     */
    private void agentPlatFormSynInsert(AgentPlatFormSyn record,AgentResult result,String busId,PlatForm platForm,AgentBusInfo agentBusInfo,Agent agent){

        if(null!=result && !"".equals(result) && result.isOK()){
            record.setSuccesTime(new Date());
            record.setNotifyStatus(Status.STATUS_1.status);
        }
        if(agentPlatFormSynMapper.insert(record)==1 && null!=result && result.isOK()){
            log.info("入网开户修改操作: 接收入网更新入网状态开始,业务id：{},返回结果:{}",busId);
            //更新业务编号
            AgentBusInfo updateBusInfo = agentBusInfoMapper.selectByPrimaryKey(agentBusInfo.getId());
            JSONObject jsonObject = JSONObject.parseObject(String.valueOf(result.getData()));
            if(PlatformType.whetherPOS(platForm.getPlatformType())){
                JSONObject dataObj = JSONObject.parseObject(jsonObject.get("data").toString());
                updateBusInfo.setBusNum(dataObj.getString("orgId"));
                updateBusInfo.setBusLoginNum(dataObj.getString("loginName"));
            }else if(platForm.getPlatformType().equals(PlatformType.MPOS.getValue())){
                updateBusInfo.setBusNum(jsonObject.getString("orgId"));
                updateBusInfo.setBusLoginNum(jsonObject.getString("orgId"));
            }else if(platForm.getPlatformType().equals(PlatformType.RDBPOS.getValue())) {
                JSONObject jsonResult = JSONObject.parseObject(jsonObject.getString("result"));
                if (jsonResult != null)
                updateBusInfo.setBusNum(jsonResult.getString("agencyId"));
                updateBusInfo.setBusLoginNum(agentBusInfo.getBusLoginNum());//传入的手机号
            }else if(platForm.getPlatformType().equals(PlatformType.RJPOS.getValue())){
                JSONObject dataObj = JSONObject.parseObject(jsonObject.getString("data"));
                updateBusInfo.setBusNum(dataObj.getString("orgId"));
                updateBusInfo.setBusLoginNum(dataObj.getString("loginName"));
            }else if(platForm.getPlatformType().equals(PlatformType.RHPOS.getValue())){
                updateBusInfo.setBusNum(jsonObject.getString("agencyId"));
                updateBusInfo.setBrandNum(jsonObject.getString("brandId"));
            }else if(platForm.getPlatformType().equals(PlatformType.SSPOS.getValue())){
                if(jsonObject.getString("orgId")!=null)
                updateBusInfo.setBusNum(jsonObject.getString("orgId"));
                if(jsonObject.getString("loginName")!=null)
                updateBusInfo.setBusLoginNum(jsonObject.getString("loginName"));
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
            updateAgent.setcUtime(nowDate);
            int upResult1 = agentMapper.updateByPrimaryKeySelective(updateAgent);
            log.info("入网开户修改操作: 接收入网更新入网状态,业务id：{},upResult1:{}",upResult1);
            if (record.getNotifyType().equals(NotifyType.NetInAddBus.code)){
                //新增冻结
                try {
                    AgentFreezePort agentFreezePort = new AgentFreezePort();
                    agentFreezePort.setAgentId(agentBusInfo.getAgentId());
                    agentFreezePort.setBusPlatform(Arrays.asList(String.valueOf(agentBusInfo.getId())));
                    agentFreezePort.setFreezeNum(agentBusInfo.getId());
                    agentFreezePort.setOperationPerson(agentBusInfo.getcUser());
                    agentFreezePort.setRemark("新增业务冻结(自动发起)");
                    AgentResult agentResult = agentFreezeService.freezeNewBus(agentFreezePort);
                    if (!agentResult.isOK()){
                        AppConfig.sendEmails("代理商入网冻结失败："+ JsonUtil.objectToJson(agentFreezePort)+agentResult.getMsg(), "冻结失败报警");
                        log.info("入网新增业务调用冻结接口操作: 代理商id：{},返回结果:{},更新数据库失败",agentBusInfo.getAgentId(),agentResult.getMsg());
                    }
                }catch (Exception e){
                    log.info("入网新增业务调用冻结接口操作异常{}",e);
                }
            }
            if (record.getNotifyType().equals(NotifyType.NetInAdd.code)){
                //入网合同冻结
                try {
                    AgentFreezePort agentFreezePort = new AgentFreezePort();
                    agentFreezePort.setAgentId(agent.getId());
                    agentFreezePort.setFreezeCause(FreeCause.HTDJ.getValue());
                    agentFreezePort.setOperationPerson(agent.getcUser());
                    agentFreezePort.setFreezeNum(agent.getId());
                    agentFreezePort.setFreeType(Arrays.asList(FreeType.AGNET.code));
                    agentFreezePort.setBusPlatform(Arrays.asList(String.valueOf(agentBusInfo.getId())));
                    agentFreezePort.setNewBusFreeze(String.valueOf(BigDecimal.ZERO));
                    FreezeDetail freezeDetail = new FreezeDetail();
                    freezeDetail.setProfitFreeze(BigDecimal.ONE);//分润冻结
                    freezeDetail.setReflowFreeze(BigDecimal.ONE);//返现冻结
                    freezeDetail.setMonthlyFreeze(BigDecimal.ONE);//月结
                    freezeDetail.setDailyFreeze(BigDecimal.ONE);//日结
                    freezeDetail.setCashFreeze(BigDecimal.ONE);//体现结算冻结
                    agentFreezePort.setCurLevel(freezeDetail);
                    AgentResult agentResult = agentFreezeService.agentFreeze(agentFreezePort);
                    if(!agentResult.isOK()){
                        AppConfig.sendEmails("代理商入网合同冻结失败："+ JsonUtil.objectToJson(agentFreezePort)+agentResult.getMsg(), "冻结失败报警");
                        log.info("入网新增业务调用合同冻结接口操作: 代理商id：{},返回结果:{}",agentBusInfo.getAgentId(),agentResult.getMsg());
                    }
                } catch (MessageException e) {
                    e.printStackTrace();
                    log.error("入网新增业务调用冻结接口操作异常{}",e);
                }
            }

        }else{
            log.info("入网开户修改操作: 接收入网更新入网状态开始,业务id：{},返回结果:{}",busId,"开通业务失败");
            //更新业务编号
            AgentBusInfo updateBusInfo = agentBusInfoMapper.selectByPrimaryKey(agentBusInfo.getId());
            updateBusInfo.setBusStatus(BusinessStatus.pause.status);
            if(agentBusInfoMapper.updateByPrimaryKeySelective(updateBusInfo)!=1){
                log.info("入网开户修改操作: 接收入网更新入网状态开始,业务id：{},返回结果:{},更新数据库失败",busId,"开通业务失败");
            }
        }
    }

    /**
     * 递归获取层级
     * @param busRegion
     * @return
     */
    @Override
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
    public void netInApplyEdit(String busId,String notifyType){

        threadPoolTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {

                }
                log.info("入网开户修改操作: 业务id：{}",busId);
                if(StringUtils.isBlank(busId)){
                    log.info("入网开户修改操作: notifyPlatform业务ID为空");
                    return;
                }
                AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(busId);
                if(agentBusInfo==null){
                    log.info("入网开户修改操作: notifyPlatform记录不存在:{}",busId);
                    return;
                }
                //直签不直发不通知
                if(agentBusInfo.getBusType().equals("8")){
                    log.info("直签不直发不通知：agentId:{},busId:{}",agentBusInfo.getAgentId(),agentBusInfo.getId());
                    return;
                }
                Agent agent = agentService.getAgentById(agentBusInfo.getAgentId());
                PlatForm platForm = platFormMapper.selectByPlatFormNum(agentBusInfo.getBusPlatform());
                if(platForm==null){
                    log.info("入网开户修改操作: 通知pos手刷业务平台未知");
                    return;
                }

                AgentPlatFormSyn record = new AgentPlatFormSyn();
                AgentResult result = null;
                try {
                    //组装参数
                    Map<String, Object> paramMap = new HashMap<>();
                    Map<String, Object> reqMap = new HashMap<>();
                    reqMap.put("agentBusInfo",agentBusInfo);
                    reqMap.put("agent",agent);
                    reqMap.put("platForm",platForm);
                    record = agentPlatFormSynParam(record, agentBusInfo, notifyType);
                    if(PlatformType.whetherPOS(platForm.getPlatformType())){
                        paramMap = agentHttpPosServiceImpl.packageParamUpdate(reqMap);
                    }else if(platForm.getPlatformType().equals(PlatformType.MPOS.getValue())){
                        paramMap = agentHttpMposServiceImpl.packageParamUpdate(reqMap);
                    }else if(platForm.getPlatformType().equals(PlatformType.RDBPOS.getValue())){
                        paramMap = agentHttpRDBMposServiceImpl.packageParamUpdate(reqMap);
                    }else if(platForm.getPlatformType().equals(PlatformType.RJPOS.getValue())){
                        paramMap = agentHttpRJPosServiceImpl.packageParamUpdate(reqMap);
                    }else if(platForm.getPlatformType().equals(PlatformType.RHPOS.getValue())){
                        paramMap = agentHttpRHBPosServiceImpl.packageParamUpdate(reqMap);
                    }else if(platForm.getPlatformType().equals(PlatformType.SSPOS.getValue())){
                        paramMap = agentHttpSsPosServiceImpl.packageParamUpdate(reqMap);
                    }
                    record.setSendJson(JSONObject.toJSONString(paramMap));
                    if(PlatformType.whetherPOS(platForm.getPlatformType())){
                        result = agentHttpPosServiceImpl.httpRequestNetInUpdate(paramMap);
                    }else if(platForm.getPlatformType().equals(PlatformType.MPOS.getValue())){
                        result = agentHttpMposServiceImpl.httpRequestNetInUpdate(paramMap);
                    }else if(platForm.getPlatformType().equals(PlatformType.RDBPOS.getValue())){
                        result = agentHttpRDBMposServiceImpl.httpRequestNetInUpdate(paramMap);
                    }else if(platForm.getPlatformType().equals(PlatformType.RJPOS.getValue())){
                        result = agentHttpRJPosServiceImpl.httpRequestNetInUpdate(paramMap);
                    }else if(platForm.getPlatformType().equals(PlatformType.RHPOS.getValue())){
                        result = agentHttpRHBPosServiceImpl.httpRequestNetInUpdate(paramMap);
                    }else if(platForm.getPlatformType().equals(PlatformType.SSPOS.getValue())){
                        result = agentHttpSsPosServiceImpl.httpRequestNetInUpdate(paramMap);
                    }
                    log.info("入网开户修改操作: ,业务id：{},返回结果:{}",busId,result);
                    record.setNotifyJson(String.valueOf(result.getData()));
                } catch (Exception e) {
                    log.info("入网开户修改操作: 通知pos手刷http请求异常:{}",e.getMessage());
                    e.printStackTrace();
                    record.setNotifyCount(new BigDecimal(1));
                    record.setNotifyJson(e.getLocalizedMessage());
                    result = AgentResult.fail(e.getLocalizedMessage());
                }finally {
                    if(StringUtils.isBlank(record.getId())){
                        record.setId(idService.genId(TabId.a_agent_platformsyn));
                    }
                    agentPlatFormSynInsert(record,result,busId,platForm,agentBusInfo,agent);
                }
            }
        });
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public void upgrade(String busId) throws Exception {
        threadPoolTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {

                }
                //业务平台
                AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(busId);
                //直签不直发不通知
                if(agentBusInfo.getBusType().equals("8")){
                    log.info("直签不直发不通知：agentId:{},busId:{}",agentBusInfo.getAgentId(),agentBusInfo.getId());
                    return;
                }
                PlatForm platForm = platFormMapper.selectByPlatFormNum(agentBusInfo.getBusPlatform());
                AgentPlatFormSyn record = new AgentPlatFormSyn();
                AgentResult res = null;
                Map req_data = new HashMap<>();
                try {
                    log.info("升级开户接口{}平台编号不为空走升级接口,获取请求参数",agentBusInfo.getBusNum());
                    FastMap fastMap = FastMap.fastSuccessMap()
                            .putKeyV("agentBusinfoId", agentBusInfo.getId())
                            .putKeyV("processingId", busId)
                            .putKeyV("platForm", platForm);
                    if(PlatformType.whetherPOS(platForm.getPlatformType())){
                        req_data = agentHttpPosServiceImpl.agencyLevelUpdateChangeData(fastMap);
                    }else if(platForm.getPlatformType().equals(PlatformType.MPOS.getValue())){
                        req_data = agentHttpMposServiceImpl.agencyLevelUpdateChangeData(fastMap);
                    }else if(platForm.getPlatformType().equals(PlatformType.RDBPOS.getValue())){
                        req_data = agentHttpRDBMposServiceImpl.agencyLevelUpdateChangeData(fastMap);
                    }else if(platForm.getPlatformType().equals(PlatformType.RJPOS.getValue())){
                        req_data = agentHttpRJPosServiceImpl.agencyLevelUpdateChangeData(fastMap);
                    }else if(platForm.getPlatformType().equals(PlatformType.RHPOS.getValue())){
                        req_data = agentHttpRHBPosServiceImpl.agencyLevelUpdateChangeData(fastMap);
                    }else if(platForm.getPlatformType().equals(PlatformType.SSPOS.getValue())){
                        req_data = agentHttpSsPosServiceImpl.agencyLevelUpdateChangeData(fastMap);
                    }

                    log.info("升级开户接口{}平台编号不为空走升级接口,请求参数{}",agentBusInfo.getBusNum(),req_data);
                    //发送请求
                    if(PlatformType.whetherPOS(platForm.getPlatformType())){
                        res = agentHttpPosServiceImpl.agencyLevelUpdateChange(req_data);
                    }else if(platForm.getPlatformType().equals(PlatformType.MPOS.getValue())){
                        res = agentHttpMposServiceImpl.agencyLevelUpdateChange(req_data);
                    }else if(platForm.getPlatformType().equals(PlatformType.RDBPOS.getValue())){
                        res = agentHttpRDBMposServiceImpl.agencyLevelUpdateChange(req_data);
                    }else if(platForm.getPlatformType().equals(PlatformType.RJPOS.getValue())){
                        res = agentHttpRJPosServiceImpl.agencyLevelUpdateChange(req_data);
                    }else if(platForm.getPlatformType().equals(PlatformType.RHPOS.getValue())){
                        res = agentHttpRHBPosServiceImpl.agencyLevelUpdateChange(req_data);
                    }else if(platForm.getPlatformType().equals(PlatformType.SSPOS.getValue())){
                        res = agentHttpSsPosServiceImpl.agencyLevelUpdateChange(req_data);
                    }
                }catch (MessageException e) {
                    e.printStackTrace();
                    log.error(e.getMsg(),e);
                    res=AgentResult.fail(e.getMsg());
                    res.setData(e.getLocalizedMessage());
                }catch (Exception e) {
                    e.printStackTrace();
                    log.error(e.getMessage(),e);
                    res=AgentResult.fail("接口调用异常");
                    res.setData(e.getLocalizedMessage());
                }
                log.info("升级开户接口{}平台编号不为空走升级接口,请求结果{}",agentBusInfo.getBusNum(),res.getMsg());
                record.setId(idService.genId(TabId.a_agent_platformsyn));
                String sendJson = JsonUtil.objectToJson(req_data);
                record.setSendJson(sendJson);
                record.setNotifyJson(String.valueOf(res.getData()));
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
                        updateAgent.setcUtime(nowDate);
                        if(1!=agentMapper.updateByPrimaryKeySelective(updateAgent)){
                            log.info("升级开户接口{}平台编号不为空走升级接口,更新的代理商{}",agentBusInfo.getBusNum(),"入网状态更新失败");
                        }else{
                            log.info("升级开户接口{}平台编号不为空走升级接口,更新的代理商{}",agentBusInfo.getBusNum(),"入网状态更新成功");
                        }
                    }
                    agentBusInfo.setBusStatus(BusinessStatus.Enabled.status);
                    if(platForm.getPlatformType().equals(PlatformType.RDBPOS.getValue())){
                        //瑞大宝升级用户填手机号，成功后返回A码更新编码
                        JSONObject jsonObject = (JSONObject)res.getData();
                        agentBusInfo.setBusNum(jsonObject.getString("result"));
                    }if(platForm.getPlatformType().equals(PlatformType.RHPOS.getValue())){
                        //瑞花宝升级用户填机构号，成功后返回品牌码更新编码
                        JSONObject jsonObject = (JSONObject)res.getData();
                        agentBusInfo.setBrandNum(jsonObject.getString("brandId"));
                    }
                    if(1!=agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfo)){
                        log.info("升级开户接口{}平台编号不为空走升级接口,更新业务{}",agentBusInfo.getBusNum(),"入网成功状态更新失败");
                    }else{
                        log.info("升级开户接口{}平台编号不为空走升级接口,更新业务{}",agentBusInfo.getBusNum(),"入网成功状态更新成功");
                    }
                    record.setSuccesTime(new Date());
                    record.setNotifyStatus(Status.STATUS_1.status);
                    if(agentPlatFormSynMapper.insert(record)==1){
                        log.info("升级开户接口{}添加记录成功,更新本地平台{}",agentBusInfo.getBusNum(),"入网成功");
                    }
                    //瑞大宝升级直接修改不需要在走修改接口
                    if(!platForm.getPlatformType().equals(PlatformType.RDBPOS.getValue()) && !platForm.getPlatformType().equals(PlatformType.RHPOS.getValue())){
                        //执行修改操作
                        asynNotifyPlatform(agentBusInfo.getId(),NotifyType.NetInEdit.getValue());
                    }
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
                }
            }
        });
    }

    /**
     * 升级校验
     * @param reqMap
     * @return
     */
    @Override
    public AgentResult agencyLevelCheck(Map<String, Object> reqMap){

        AgentResult result = AgentResult.fail();
        AgentVo agentVo = (AgentVo) reqMap.get("agentVo");
        try {
            AgentBusInfoVo agentBusInfoVo = (AgentBusInfoVo)reqMap.get("busInfo");
            String busPlatform = agentBusInfoVo.getBusPlatform();
            if(StringUtils.isBlank(busPlatform) || busPlatform.equals("null")){
                throw new ProcessException("业务平台码为空");
            }
            PlatForm platForm = platFormMapper.selectByPlatFormNum(busPlatform);
            if(platForm==null){
                throw new ProcessException("业务平台不存在");
            }
            if(PlatformType.whetherPOS(platForm.getPlatformType())){
                reqMap.put("agentVo",agentVo);
                result = agentHttpPosServiceImpl.agencyLevelCheck(reqMap);
            }else if(platForm.getPlatformType().equals(PlatformType.MPOS.getValue())){
                result = agentHttpMposServiceImpl.agencyLevelCheck(reqMap);
            }else if(platForm.getPlatformType().equals(PlatformType.RDBPOS.getValue())){
                reqMap.put("agentVo",agentVo);
                result = agentHttpRDBMposServiceImpl.agencyLevelCheck(reqMap);
            }else if(platForm.getPlatformType().equals(PlatformType.RJPOS.getValue())){
                result = agentHttpRJPosServiceImpl.agencyLevelCheck(reqMap);
            }else if(platForm.getPlatformType().equals(PlatformType.RHPOS.getValue())){
                reqMap.put("brandCode",platForm.getBusplatform());
                reqMap.put("agentId",agentBusInfoVo.getBusNum());
                reqMap.put("directAgentId",agentBusInfoVo.getBusParent());
                result = agentHttpRHBPosServiceImpl.agencyLevelCheck(reqMap);
            }else if(platForm.getPlatformType().equals(PlatformType.SSPOS.getValue())){
                result = agentHttpSsPosServiceImpl.agencyLevelCheck(reqMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AgentResult.fail(e.getMessage());
        }
        return result;
    }
}
