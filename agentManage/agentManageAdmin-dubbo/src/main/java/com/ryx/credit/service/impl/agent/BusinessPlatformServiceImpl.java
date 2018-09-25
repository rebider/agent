package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AgentBusInfoMapper;
import com.ryx.credit.dao.agent.AgentMapper;
import com.ryx.credit.dao.agent.PayCompMapper;
import com.ryx.credit.dao.agent.PlatFormMapper;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.bank.DPosRegion;
import com.ryx.credit.pojo.admin.vo.*;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.*;
import com.ryx.credit.service.bank.PosRegionService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.RegionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;

/**
 * 业务平台管理
 *
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/5/22 9:26
 */
@Service("businessPlatformService")
public class BusinessPlatformServiceImpl implements BusinessPlatformService {
    private Logger logger = LoggerFactory.getLogger(BusinessPlatformServiceImpl.class);
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private PlatFormMapper platFormMapper;
    @Autowired
    private AgentContractService agentContractService;
    @Autowired
    private AccountPaidItemService accountPaidItemService;
    @Autowired
    private AgentBusinfoService agentBusinfoService;
    @Autowired
    private AgentColinfoService agentColinfoService;
    @Autowired
    private AgentDataHistoryService agentDataHistoryService;
    @Autowired
    private PayCompMapper payCompMapper;
    @Autowired
    private AimportService aimportService;
    @Autowired
    private AgentNotifyService agentNotifyService;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private PosRegionService posRegionService;
    @Autowired
    private AgentService agentService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private AgentQueryService agentQueryService;
    @Override
    public PageInfo queryBusinessPlatformList(AgentBusInfo agentBusInfo, Agent agent, Page page,String flag,String isZpos) {
        Map<String, Object> reqMap = new HashMap<>();

        reqMap.put("agStatus", AgStatus.Approved.name());
        if (!StringUtils.isBlank(agent.getId())) {
            reqMap.put("id", agent.getId());
        }
        if (!StringUtils.isBlank(agent.getAgName())) {
            reqMap.put("agName", agent.getAgName());
        }
        if (!StringUtils.isBlank(agent.getAgUniqNum())) {
            reqMap.put("agUniqNum", agent.getAgUniqNum());
        }
        if (!StringUtils.isBlank(agentBusInfo.getBusNum())) {
            reqMap.put("busNum", agentBusInfo.getBusNum());
        }
        if (!StringUtils.isBlank(agentBusInfo.getBusPlatform())) {
            reqMap.put("busPlatform", agentBusInfo.getBusPlatform());
        }
        if (agentBusInfo.getCloReviewStatus() != null) {
            reqMap.put("cloReviewStatus", agentBusInfo.getCloReviewStatus());
        }
        if (StringUtils.isNotBlank(flag) && flag.equals("1")){
            List<Map<String, Object>> orgCodeRes = iUserService.orgCode(Long.valueOf(agentBusInfo.getcUser()));
            if(orgCodeRes==null && orgCodeRes.size()!=1){
                return null;
            }
            Map<String, Object> stringObjectMap = orgCodeRes.get(0);
            String orgId = String.valueOf(stringObjectMap.get("ORGID"));
            reqMap.put("orgId",orgId);
            reqMap.put("userId",Long.valueOf(agentBusInfo.getcUser()));
        }
        reqMap.put("status", Status.STATUS_1.status);
        reqMap.put("isZpos",isZpos);
        reqMap.put("platForm", Platform.ZPOS.getValue());
        List<Map<String, Object>> agentBusInfoList = agentBusInfoMapper.queryBusinessPlatformList(reqMap, page);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(agentBusInfoList);
        pageInfo.setTotal(agentBusInfoMapper.queryBusinessPlatformCount(reqMap));
        return pageInfo;
    }

    /**
     * 根据代理商唯一编号检索
     * @param agUniqNum
     * @return
     */
    @Override
    public AgentResult verifyAgent(String agUniqNum) {
        AgentResult result = new AgentResult(500,"参数错误","");
        if (StringUtils.isBlank(agUniqNum)) {
            return result;
        }
        AgentExample example = new AgentExample();
        AgentExample.Criteria criteria = example.createCriteria();
        criteria.andAgUniqNumEqualTo(agUniqNum);
        criteria.andAgStatusEqualTo(AgStatus.Approved.name());
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        List<Agent> agents = agentMapper.selectByExample(example);
        if(agents.size()==1){
            return AgentResult.ok(agents.get(0));
        }
        if (agents.size()>1) {
            result.setMsg("代理商不唯一");
            return result;
        }
        AgentExample exampleName = new AgentExample();
        AgentExample.Criteria criteriaName = exampleName.createCriteria();
        criteriaName.andAgNameEqualTo(agUniqNum);
        criteriaName.andAgStatusEqualTo(AgStatus.Approved.name());
        criteriaName.andStatusEqualTo(Status.STATUS_1.status);
        List<Agent> agentsName = agentMapper.selectByExample(exampleName);
        if(agentsName.size()==1){
            return AgentResult.ok(agentsName.get(0));
        }
        if (agentsName.size()>1) {
            result.setMsg("代理商不唯一");
            return result;
        }
        AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
        AgentBusInfoExample.Criteria agentBusInfoCriteria = agentBusInfoExample.createCriteria();
        agentBusInfoCriteria.andBusNumEqualTo(agUniqNum);
        agentBusInfoCriteria.andBusStatusEqualTo(Status.STATUS_1.status);
        agentBusInfoCriteria.andStatusEqualTo(Status.STATUS_1.status);
        List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(agentBusInfoExample);
        if(agentBusInfos.size()==1){
            AgentBusInfo agentBusInfo = agentBusInfos.get(0);
            Agent agent = agentMapper.selectByPrimaryKey(agentBusInfo.getAgentId());
            return AgentResult.ok(agent);
        }
        if (agentsName.size()>1) {
            result.setMsg("代理商不唯一");
            return result;
        }
        result.setMsg("代理商不存在或未通过审批");
        return result;
    }

    @Override
    public AgentBusInfo findById(String id) {
        AgentBusInfo agentBusInfo = null;
        if (StringUtils.isBlank(id)) {
            return agentBusInfo;
        } else {
            agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(id);
        }
        return agentBusInfo;
    }

    @Override
    public int updateByPrimaryKeySelective(AgentBusInfo agentBusInfo) {
        if (StringUtils.isBlank(agentBusInfo.getId())) {
            return 0;
        }
        AgentBusInfo agbus = agentBusInfoMapper.selectByPrimaryKey(agentBusInfo.getId());
        agentBusInfo.setVersion(agbus.getVersion());
        int i = agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfo);
        return i;
    }

    @Override
    public List<PlatForm> queryAblePlatForm() {
        PlatFormExample example = new PlatFormExample();
        example.or().andStatusEqualTo(Status.STATUS_1.status).andPlatformStatusEqualTo(Status.STATUS_1.status);
        example.setOrderByClause(" c_time asc ");
        return platFormMapper.selectByExample(example);
    }

    /**
     * 平台业务保存
     *
     * @param agentVo
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public AgentResult saveBusinessPlatform(AgentVo agentVo) throws Exception {
        try {

            Agent agent = agentVo.getAgent();
            agent.setId(agentVo.getAgentId());
            //先查询业务是否已添加 有个添加过 全部返回
            for (AgentBusInfoVo item : agentVo.getBusInfoVoList()) {
                item.setAgentId(agent.getId());
                Boolean busPlatExist = findBusPlatExist(item);
                if (busPlatExist) {
                    return new AgentResult(500, "业务已添加,请勿重复添加", "");
                }
            }
            for (AgentContractVo item : agentVo.getContractVoList()) {
                if (StringUtils.isNotBlank(agent.getcUser()) && StringUtils.isNotBlank(agent.getId())) {
                    item.setcUser(agent.getcUser());
                    item.setAgentId(agent.getId());
                    agentContractService.insertAgentContract(item, item.getContractTableFile(),agent.getcUser());
                }
            }
            for (CapitalVo item : agentVo.getCapitalVoList()) {
                if (StringUtils.isNotBlank(agent.getcUser()) && StringUtils.isNotBlank(agent.getId())) {
                    item.setcAgentId(agent.getId());
                    item.setcUser(agent.getcUser());
                    AgentResult result = accountPaidItemService.insertAccountPaid(item, item.getCapitalTableFile(), agentVo.getAgent().getcUser());
                    if (!result.isOK()) {
                        throw new ProcessException("缴纳款项信息录入失败");
                    }
                }
            }
            if (null != agentVo.getColinfoVoList()) {
                for (AgentColinfoVo item : agentVo.getColinfoVoList()) {
                    item.setAgentId(agent.getId());
                    item.setcUser(agent.getcUser());
                    agentColinfoService.agentColinfoInsert(item, item.getColinfoTableFile());
                }
            }
            List<AgentBusInfo> agentBusInfoList = new ArrayList<>();
            for (AgentBusInfoVo item : agentVo.getBusInfoVoList()) {
                item.setcUser(agent.getcUser());
                item.setAgentId(agent.getId());
                AgentBusInfo agentBusInfo = agentBusinfoService.agentBusInfoInsert(item);
                agentBusInfoList.add(agentBusInfo);
            }
            return AgentResult.ok(agentBusInfoList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ProcessException("业务平台信息录入失败");
        }
    }

    /**
     * 查询代理上次业务是否添加过
     *
     * @param agentBusInfo
     * @return
     */
    private Boolean findBusPlatExist(AgentBusInfo agentBusInfo) {
        AgentBusInfoExample example = new AgentBusInfoExample();
        AgentBusInfoExample.Criteria criteria = example.createCriteria();
        criteria.andAgentIdEqualTo(agentBusInfo.getAgentId());
        criteria.andBusPlatformEqualTo(agentBusInfo.getBusPlatform());
        List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(example);
        if (null == agentBusInfos) {
            return true;
        }
        if (agentBusInfos.size() == 0) {
            return false;
        }
        return true;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public List<String> addList(List<List<Object>> list, String userid) throws Exception {
        List<String> busList = new ArrayList<>();
        if (null == list && list.size() == 0) {
            logger.info("导入的数据为空");
            throw new MessageException("导入的数据为空");
        }
        for (List<Object> objectList : list) {
            if (StringUtils.isBlank(String.valueOf(objectList.get(0)))) {
                logger.info("代理商ID为空:{}", String.valueOf(objectList.get(0)));
                throw new MessageException("请填写编号");
            }
            if (StringUtils.isBlank(String.valueOf(objectList.get(1)))) {
                logger.info("业务平台编号为空:{}", String.valueOf(objectList.get(1)));
                throw new MessageException("请填写业务平台编号");
            }
            if (StringUtils.isBlank(String.valueOf(objectList.get(2)))) {
                logger.info("打款公司为空:{}", String.valueOf(objectList.get(2)));
                throw new MessageException("请填写打款公司");
            }
            AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
            AgentBusInfoExample.Criteria criteria = agentBusInfoExample.createCriteria();
            criteria.andAgentIdEqualTo(String.valueOf(objectList.get(0)));
            criteria.andBusNumEqualTo(String.valueOf(objectList.get(1)));
            List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(agentBusInfoExample);
            if (1 != agentBusInfos.size()) {
                logger.info("不匹配,请检查数据是否正确");
                throw new MessageException("不匹配,请检查数据是否正确");
            }
            //到这说明已经存在这条数据
            PayCompExample payCompExample = new PayCompExample();
            PayCompExample.Criteria criteria1 = payCompExample.or().andComNameEqualTo(String.valueOf(objectList.get(2)));
            List<PayComp> payComps = payCompMapper.selectByExample(payCompExample);
            if (null == payComps && payComps.size() == 0) {
                logger.info("没有此公司");
                throw new MessageException("没有此公司");
            }
            AgentBusInfo agentBus = agentBusInfos.get(0);
            agentBus.setCloPayCompany(payComps.get(0).getId());
            if (1 != agentBusInfoMapper.updateByPrimaryKeySelective(agentBus)) {
                logger.info("更新失败");
                throw new MessageException("更新失败");
            }

            try {
                ImportAgent importAgent = new ImportAgent();
                importAgent.setDataid(agentBus.getId());
                importAgent.setDatatype(AgImportType.DATACHANGEAPP.name());
                importAgent.setBatchcode(Calendar.getInstance().getTime().toString());
                importAgent.setcUser(userid);
                if (1 != aimportService.insertAgentImportData(importAgent)) {
                    logger.info("代理商审批通过-添加开户任务失败");
                } else {
                    logger.info("代理商审批通过-添加开户任务成功!{},{}", AgImportType.BUSAPP.getValue(), agentBus.getId());
                }

                agentDataHistoryService.saveDataHistory(agentBus, DataHistoryType.BUSINESS.getValue());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                agentNotifyService.asynNotifyPlatform();
            }
            busList.add(agentBus.getId());
        }

        return busList;
    }


    @Override
    public int updateBusPlatDkgsBySelective(AgentBusInfo agentBusInfo,String userId) {
        if (StringUtils.isBlank(agentBusInfo.getId())) {
            return 0;
        }
        AgentBusInfo agbus = agentBusInfoMapper.selectByPrimaryKey(agentBusInfo.getId());
        agentBusInfo.setVersion(agbus.getVersion());
        int i = agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfo);
        try {
            ImportAgent importAgent = new ImportAgent();
            importAgent.setDataid(agbus.getId());
            importAgent.setDatatype(AgImportType.DATACHANGEAPP.name());
            importAgent.setBatchcode(Calendar.getInstance().getTime().toString());
            importAgent.setcUser(userId);
            if (1 != aimportService.insertAgentImportData(importAgent)) {
                logger.info("代理商审批通过-添加开户任务失败");
            } else {
                logger.info("代理商审批通过-添加开户任务成功!{},{}", AgImportType.BUSAPP.getValue(), agbus.getId());
            }

            agentDataHistoryService.saveDataHistory(agbus, DataHistoryType.BUSINESS.getValue());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            agentNotifyService.asynNotifyPlatform();
        }
        return i;
    }

    @Override
    public List<BusinessOutVo> exportAgent(Map map, Long userId) throws ParseException {
        if (String.valueOf(map.get("flag")).equals("1")){
            List<Map<String, Object>> orgCodeRes = iUserService.orgCode(userId);
            if(orgCodeRes==null && orgCodeRes.size()!=1){
                return null;
            }
            Map<String, Object> stringObjectMap = orgCodeRes.get(0);
            String orgId = String.valueOf(stringObjectMap.get("ORGID"));
            map.put("orgId",orgId);
            map.put("userId",userId);
        }

        map.put("agStatus", AgStatus.Approved.name());
        map.put("status", Status.STATUS_1.status);
        map.put("isZpos",String.valueOf(map.get("isZpos")));
        map.put("platForm", Platform.ZPOS.getValue());
        List<BusinessOutVo> agentoutVos = agentBusInfoMapper.excelAgent(map);
        List<Dict> BUS_TYPE = dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.BUS_TYPE.name());
        List<Dict> BUS_SCOPE = dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.BUS_SCOPE.name());
        if (null != agentoutVos && agentoutVos.size() > 0)
            for (BusinessOutVo agentoutVo : agentoutVos) {//类型
                if (StringUtils.isNotBlank(agentoutVo.getBusType()) && !agentoutVo.getBusType().equals("null")) {
                    for (Dict dict : BUS_TYPE) {
                        if (null!=dict  &&  agentoutVo.getBusType().equals(dict.getdItemvalue())){
                            agentoutVo.setBusType(dict.getdItemname());
                            break;
                        }
                    }
                }
                if (StringUtils.isNotBlank(agentoutVo.getBusIndeAss()) && !agentoutVo.getBusIndeAss().equals("null")) {
                    if (agentoutVo.getBusIndeAss().equals("1")) {
                        agentoutVo.setBusIndeAss("是");
                    } else
                        agentoutVo.setBusIndeAss("否");
                }
                //业务区域
               if (StringUtils.isNotBlank(agentoutVo.getBusRegion()) && !agentoutVo.getBusRegion().equals("null")){
                    String busRegion = agentoutVo.getBusRegion();
                    if (StringUtils.isNotBlank(busRegion) &&!busRegion.equals("null")){
                        String[] arr = busRegion.split(",");
                        String name = agentQueryService.dPosRegionNameFromDposIds(arr);
                        if (StringUtils.isNotBlank(name) && !name.equals("null"))
                            agentoutVo.setBusRegion(name);
                    }
                }

                if (StringUtils.isNotBlank(agentoutVo.getBusScope()) && !agentoutVo.getBusScope().equals("null")) {
                    for (Dict dict : BUS_SCOPE) {//业务范围
                        if (null!=dict  &&  agentoutVo.getBusScope().equals(dict.getdItemvalue())){
                            agentoutVo.setBusScope(dict.getdItemname());
                            break;
                        }
                    }
                }
            }
        return agentoutVos;
    }


    @Override
    public List<Map<String, Object>> queryByBusNum(String busNum){
        if(StringUtils.isBlank(busNum)){
            return null;
        }
        return agentBusInfoMapper.queryByBusNum(busNum);
    }


    /**
     * 查询代理商是否有标准一代的
     * @param agBusLic
     * @param busInfoVoList
     * @return
     */
    @Override
    public Map<String,Object> queryIsBZYD(String agBusLic,List<AgentBusInfoVo> busInfoVoList){
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("code","200");
        resultMap.put("msg","成功");
        AgentExample agentExample = new AgentExample();
        AgentExample.Criteria criteria = agentExample.createCriteria();
        criteria.andAgBusLicEqualTo(agBusLic);
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        List<Agent> agents = agentMapper.selectByExample(agentExample);
        for (Agent agent : agents) {
            AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
            AgentBusInfoExample.Criteria busCriteria = agentBusInfoExample.createCriteria();
            busCriteria.andAgentIdEqualTo(agent.getId());
            busCriteria.andStatusEqualTo(Status.STATUS_1.status);
            List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(agentBusInfoExample);
            for (AgentBusInfo agentBusInfo : agentBusInfos) {
                for (AgentBusInfoVo agentBusInfoVo : busInfoVoList) {
                    if(agentBusInfo.getBusPlatform().equals(agentBusInfoVo.getBusPlatform())){
                        if(agentBusInfo.getBusType().equals(BusType.BZYD.key)){
                            resultMap.put("code","500");
                            resultMap.put("msg","唯一编号："+agentBusInfo.getAgentId()+",已有标准一代");
                            return resultMap;
                        }
                        if(StringUtils.isBlank(agentBusInfo.getBusParent()) && StringUtils.isBlank(agentBusInfo.getBusParent())){
                            continue;
                        }
                        if((StringUtils.isBlank(agentBusInfo.getBusParent()) && StringUtils.isNotBlank(agentBusInfo.getBusParent())) ||
                                (StringUtils.isNotBlank(agentBusInfo.getBusParent()) && StringUtils.isBlank(agentBusInfo.getBusParent())) ){
                            resultMap.put("code","500");
                            resultMap.put("msg","平台："+Platform.getContentByValue(agentBusInfo.getBusPlatform())+",上级不一致");
                            return resultMap;
                        }
                        if(!agentBusInfo.getBusParent().equals(agentBusInfoVo.getBusParent())){
                            resultMap.put("code","500");
                            AgentBusInfo agentBus = agentBusInfoMapper.selectByPrimaryKey(agentBusInfo.getBusParent());
                            Agent agent1 = agentMapper.selectByPrimaryKey(agentBus.getAgentId());
                            resultMap.put("msg","平台："+Platform.getContentByValue(agentBusInfo.getBusPlatform())+",上级不一致,上级名称:"+agent1.getAgName());
                            return resultMap;
                        }
                    }
                }
            }
        }
        return resultMap;
    }

    @Override
    public List<Map<String,Object>> queryIsBZYDList(String agBusLic,List<AgentBusInfoVo> busInfoVoList){
        List<Map<String,Object>> resultList = new ArrayList<>();
        AgentExample agentExample = new AgentExample();
        AgentExample.Criteria criteria = agentExample.createCriteria();
        criteria.andAgBusLicEqualTo(agBusLic);
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        List<Agent> agents = agentMapper.selectByExample(agentExample);
        for (Agent agent : agents) {
            AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
            AgentBusInfoExample.Criteria busCriteria = agentBusInfoExample.createCriteria();
            busCriteria.andAgentIdEqualTo(agent.getId());
            busCriteria.andStatusEqualTo(Status.STATUS_1.status);
            List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(agentBusInfoExample);
            for (AgentBusInfo agentBusInfo : agentBusInfos) {
                for (AgentBusInfoVo agentBusInfoVo : busInfoVoList) {
                    if(agentBusInfo.getBusPlatform().equals(agentBusInfoVo.getBusPlatform())){
                        if(agentBusInfo.getBusType().equals(BusType.BZYD.key)){
                            Map<String,Object> resultMap = new HashMap<>();
                            resultMap.put("msg","存在标准一代");
                            resultMap.put("agentId",agentBusInfo.getAgentId());
                            resultMap.put("agentName",agent.getAgName());
                            resultList.add(resultMap);
                        }
                        if(StringUtils.isBlank(agentBusInfo.getBusParent()) && StringUtils.isBlank(agentBusInfo.getBusParent())){
                             continue;
                        }
                        if((StringUtils.isBlank(agentBusInfo.getBusParent()) && StringUtils.isNotBlank(agentBusInfo.getBusParent())) ||
                            (StringUtils.isNotBlank(agentBusInfo.getBusParent()) && StringUtils.isBlank(agentBusInfo.getBusParent())) ){
                            Map<String,Object> resultMap = new HashMap<>();
                            resultMap.put("msg","上级不一致");
                            resultMap.put("agentId",agentBusInfo.getAgentId());
                            resultMap.put("agentName",agent.getAgName());
                            resultMap.put("busPlatform",Platform.getContentByValue(agentBusInfo.getBusPlatform()));
                        }else if(!agentBusInfo.getBusParent().equals(agentBusInfoVo.getBusParent())){
                            Map<String,Object> resultMap = new HashMap<>();
                            AgentBusInfo agentBus = agentBusInfoMapper.selectByPrimaryKey(agentBusInfo.getBusParent());
                            Agent agent1 = agentMapper.selectByPrimaryKey(agentBus.getAgentId());
                            resultMap.put("msg","上级不一致");
                            resultMap.put("agentId",agentBusInfo.getAgentId());
                            resultMap.put("agentName",agent.getAgName());
                            resultMap.put("busPlatform",Platform.getContentByValue(agentBusInfo.getBusPlatform()));
                            resultMap.put("busParent",agent1.getAgName());
                            resultList.add(resultMap);
                        }
                    }
                }
            }
        }
        return resultList;
    }

}