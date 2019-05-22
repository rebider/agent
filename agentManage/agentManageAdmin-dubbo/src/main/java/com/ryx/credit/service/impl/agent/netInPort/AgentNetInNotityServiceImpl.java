package com.ryx.credit.service.impl.agent.netInPort;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.*;
import com.ryx.credit.pojo.admin.agent.*;
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
            PlatForm platForm = platFormMapper.selectByPlatFormNum(agentBusInfo.getBusPlatform());
            if(platForm.equals(PlatformType.RDBPOS.getValue())){
                //瑞大宝修改单独接口

            }else{
                netIn(busId,notifyType);
            }
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
                   Thread.sleep(1000);
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
                    if(PlatformType.whetherPOS(platForm.getPlatformType())){
                        reqMap.put("platForm",platForm);
                        paramMap = agentHttpPosServiceImpl.packageParam(reqMap);
                    }else if(platForm.getPlatformType().equals(PlatformType.MPOS.getValue())){
                        paramMap = agentHttpMposServiceImpl.packageParam(reqMap);
                    }else if(platForm.getPlatformType().equals(PlatformType.RDBPOS.getValue())){
                        paramMap = agentHttpRDBMposServiceImpl.packageParam(reqMap);
                    }
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
                    record.setNotifyType(notifyType);
                    record.setSendJson(JSONObject.toJSONString(paramMap));

                    if(PlatformType.whetherPOS(platForm.getPlatformType())){
                        result = agentHttpPosServiceImpl.httpRequestNetIn(paramMap);
                    }else if(platForm.getPlatformType().equals(PlatformType.MPOS.getValue())){
                        result = agentHttpMposServiceImpl.httpRequestNetIn(paramMap);
                    }else if(platForm.getPlatformType().equals(PlatformType.RDBPOS.getValue())){
                        result = agentHttpRDBMposServiceImpl.httpRequestNetIn(paramMap);
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
        });
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


    public void applyERBEdit(){

    }


    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    public void upgrade(String busId) throws Exception {
        threadPoolTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
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
                log.info("升级开户接口{}平台编号不为空走升级接口,获取请求参数",agentBusInfo.getBusNum());
                FastMap fastMap = FastMap.fastSuccessMap()
                        .putKeyV("agentBusinfoId", agentBusInfo.getId())
                        .putKeyV("processingId", busId);
                Map req_data = new HashMap<>();
                if(PlatformType.whetherPOS(platForm.getPlatformType())){
                    req_data = agentHttpPosServiceImpl.agencyLevelUpdateChangeData(fastMap);
                }else if(platForm.getPlatformType().equals(PlatformType.MPOS.getValue())){
                    req_data = agentHttpMposServiceImpl.agencyLevelUpdateChangeData(fastMap);
                }else if(platForm.getPlatformType().equals(PlatformType.RDBPOS.getValue())){
                    req_data = agentHttpRDBMposServiceImpl.agencyLevelUpdateChangeData(fastMap);
                }
                log.info("升级开户接口{}平台编号不为空走升级接口,请求参数{}",agentBusInfo.getBusNum(),req_data);
                try {
                    //发送请求
                    if(PlatformType.whetherPOS(platForm.getPlatformType())){
                        res = agentHttpPosServiceImpl.agencyLevelUpdateChange(req_data);
                    }else if(platForm.getPlatformType().equals(PlatformType.MPOS.getValue())){
                        res = agentHttpMposServiceImpl.agencyLevelUpdateChange(req_data);
                    }else if(platForm.getPlatformType().equals(PlatformType.RDBPOS.getValue())){
                        res = agentHttpRDBMposServiceImpl.agencyLevelUpdateChange(req_data);
                    }
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
                    record.setSuccesTime(new Date());
                    record.setNotifyStatus(Status.STATUS_1.status);
                    if(agentPlatFormSynMapper.insert(record)==1){
                        log.info("升级开户接口{}添加记录成功,更新本地平台{}",agentBusInfo.getBusNum(),"入网成功");
                    }
                    //执行修改操作
                    asynNotifyPlatform(agentBusInfo.getId(),NotifyType.NetInEdit.getValue());
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
}
