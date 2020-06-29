package com.ryx.credit.service.impl.agent;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AgentBusInfoMapper;
import com.ryx.credit.dao.agent.AgentMapper;
import com.ryx.credit.dao.agent.DateChangeRequestMapper;
import com.ryx.credit.dao.agent.PlatFormMapper;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.vo.AgentColinfoVo;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.service.agent.DateChangeReqService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.management.resources.agent;

import java.math.BigDecimal;
import java.util.*;

/**
 * @ClassName DateChangeReqServiceImpl
 * @Description lrr
 * @Author RYX
 * @Date 2018/6/1
 **/
@Service("dateChangeReqService")
public class DateChangeReqServiceImpl implements DateChangeReqService{
    private static Logger logger = LoggerFactory.getLogger(DateChangeReqServiceImpl.class);
    @Autowired
    private DateChangeRequestMapper dateChangeRequestMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private PlatFormMapper platFormMapper;
    @Autowired
    private AgentMapper agentMapper;
    @Override
    public ResultVO dateChangeReqIn(String json,String oldJson,String srcId,String type,String userId) {
        try {

            if(DataChangeApyType.DC_Colinfo.name().equals(type) || DataChangeApyType.DC_AG_Colinfo.name().equals(type)){
                Agent agent = agentMapper.selectByPrimaryKey(srcId);
                //检查业务是否有审批通过未启用业务
                AgentBusInfoExample example = new AgentBusInfoExample();
                example.or().andStatusEqualTo(Status.STATUS_1.status)
                        .andCloReviewStatusEqualTo(AgStatus.Approved.status)
                        .andAgentIdEqualTo(srcId)
                        .andBusStatusNotIn(Arrays.asList(BusStatus.QY.status,BusStatus.SD.status,BusStatus.WJH.status,BusStatus.WQY.status));
                List<AgentBusInfo> agentBusInfoList = agentBusInfoMapper.selectByExample(example);
                for (AgentBusInfo agentBusInfo : agentBusInfoList) {
                    if(agentBusInfo.getBusStatus()!=null){
                        if(StringUtils.isBlank(agentBusInfo.getBusNum())){
                            PlatForm p =  platFormMapper.selectByPlatFormNum(agentBusInfo.getBusPlatform());
                            return ResultVO.fail("请检查["+agent.getAgName()+"]["+p.getPlatformName()+"]业务，出现业务编号为空的无效业务，请处理完成后进行提交");
                        }
                        if(agentBusInfo.getBusStatus().compareTo(BusStatus.SD.status)==0){
                            PlatForm p =  platFormMapper.selectByPlatFormNum(agentBusInfo.getBusPlatform());
                            return ResultVO.fail("请检查["+agent.getAgName()+"]["+p.getPlatformName()+"]["+agentBusInfo.getBusNum()+"]业务，该业务被锁定，请处理完成后进行提交");
                        }
                        if(agentBusInfo.getBusStatus().compareTo(BusStatus.WJH.status)==0){
                            PlatForm p =  platFormMapper.selectByPlatFormNum(agentBusInfo.getBusPlatform());
                            return ResultVO.fail("请检查["+agent.getAgName()+"]["+p.getPlatformName()+"]["+agentBusInfo.getBusNum()+"]业务，该业务未激活，请处理完成后进行提交");
                        }
                        if(agentBusInfo.getBusStatus().compareTo(BusStatus.WQY.status)==0){
                            PlatForm p =  platFormMapper.selectByPlatFormNum(agentBusInfo.getBusPlatform());
                            return ResultVO.fail("请检查["+agent.getAgName()+"]["+p.getPlatformName()+"]["+agentBusInfo.getBusNum()+"]业务，该业务未启用，请处理完成后进行提交");
                        }
                    }
                }
            }
            logger.info("开始数据变更");
            DateChangeRequest changeRequest = new DateChangeRequest();
            Date date = Calendar.getInstance().getTime();
            changeRequest.setId(idService.genId(TabId.data_change_request));
            changeRequest.setDataId(srcId);
            changeRequest.setDataType(type);
            changeRequest.setDataContent(json);
            changeRequest.setDataPreContent(oldJson);
            changeRequest.setcUser(userId);
            changeRequest.setcTime(date);
            changeRequest.setcUpdate(date);
            changeRequest.setAppyStatus(AgStatus.Create.status);
            changeRequest.setStatus(Status.STATUS_1.status);
            changeRequest.setVersion(Status.STATUS_1.status);
             if(1==dateChangeRequestMapper.insertSelective(changeRequest)){
                 return ResultVO.success(changeRequest);
             }else{
                 throw new ProcessException("数据变更申请失败");
             }
        }catch (Exception e){
            logger.info("数据变更失败");
            e.printStackTrace();
            throw new ProcessException("数据变更失败");
        }
    }





    @Override
    public DateChangeRequest getById(String id){
        return dateChangeRequestMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo queryData(Page page,Map map) {
        List<Map<String, Object>> dateChangeReqList = dateChangeRequestMapper.queryData(map,page);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(dateChangeReqList);
        pageInfo.setTotal(dateChangeRequestMapper.queryDataCount(map));
        return pageInfo;
    }

    @Override
    public List<Map<String, Object>> exportDcColinfo(Map map) throws Exception {
        List<Dict> colinfoType = dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.COLINFO_TYPE.name());
        List<Dict> agStatusNum = dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.AG_STATUS_I.name());
        List<Map<String, Object>> resultList = new ArrayList<>();
        List<Map<String, Object>> dateChangeReqList = dateChangeRequestMapper.exportDcColinfo(map);
        if(null!=dateChangeReqList && dateChangeReqList.size()>0){
            for (Map<String, Object> dateChangeReqMap : dateChangeReqList) {
                Map<String, Object> resultMap = new HashMap<>();
                if(null!=dateChangeReqMap.get("APPY_STATUS")){
                    BigDecimal appyStatus=(BigDecimal) dateChangeReqMap.get("APPY_STATUS");
                    for (Dict dict : agStatusNum) {
                        if (null!=dict && appyStatus.toString().equals(dict.getdItemvalue())){
                            resultMap.put("appyStatus", dict.getdItemname());
                        }
                    }
                }
                resultMap.put("cUpdate", dateChangeReqMap.get("C_UPDATE"));
                String id=(String) dateChangeReqMap.get("ID");
                if(StringUtils.isNotBlank(id)){
                    DateChangeRequest dateChangeRequest = dateChangeRequestMapper.selectByPrimaryKey(id);
                    if (null!=dateChangeRequest){
                        AgentVo vo = JSONObject.parseObject(dateChangeRequest.getDataContent(), AgentVo.class);
                        if (null!=vo){
                            if(vo.getAgent()==null){
                                continue;
                            }
                                Agent agent = vo.getAgent();
                                resultMap.put("after","");
                                resultMap.put("id",agent.getId());
                                resultMap.put("agUniqNum",agent.getAgUniqNum());
                                resultMap.put("agName",agent.getAgName());

                            if (null!=vo.getColinfoVoList() && vo.getColinfoVoList().size()>0){
                                List<AgentColinfoVo> colinfoVoList = vo.getColinfoVoList();
                                for (AgentColinfoVo agentColinfoVo : colinfoVoList) {
                                   if(null!=agentColinfoVo.getCloType()){
                                       for (Dict dict : colinfoType) {
                                           if (null!=dict && agentColinfoVo.getCloType().toString().equals(dict.getdItemvalue())){
                                               resultMap.put("cloType", dict.getdItemname());
                                           }
                                       }
                                   }
                                    resultMap.put("cloRealname", agentColinfoVo.getCloRealname());
                                    resultMap.put("cloBankAccount", agentColinfoVo.getCloBankAccount());
                                    resultMap.put("cloBankBranch", agentColinfoVo.getCloBankBranch());
                                    resultMap.put("branchLineNum", agentColinfoVo.getBranchLineNum());
                                    if(null!=agentColinfoVo.getCloInvoice()){
                                        if (agentColinfoVo.getCloInvoice().compareTo(new BigDecimal(0))==0){
                                            resultMap.put("cloInvoice", "否");
                                        }else{
                                            resultMap.put("cloInvoice","是");
                                        }
                                    }


                                }
                            }
                        }

                        AgentVo prevo = JSONObject.parseObject(dateChangeRequest.getDataPreContent(), AgentVo.class);
                        if (null!=prevo){
                            if (null!=prevo.getAgent()){
                                Agent agent = prevo.getAgent();
                                resultMap.put("before","");
                                resultMap.put("agNamePre",agent.getAgName());
                            }
                            if (null!=prevo.getColinfoVoList() && prevo.getColinfoVoList().size()>0){
                                List<AgentColinfoVo> colinfoVoList = prevo.getColinfoVoList();
                                for (AgentColinfoVo agentColinfoVo : colinfoVoList) {
                                    if(null!=agentColinfoVo.getCloType()){
                                        for (Dict dict : colinfoType) {
                                            if (null!=dict && agentColinfoVo.getCloType().toString().equals(dict.getdItemvalue())){
                                                resultMap.put("cloTypePre", dict.getdItemname());
                                            }

                                        }
                                    }
                                    resultMap.put("cloRealnamePre", agentColinfoVo.getCloRealname());
                                    resultMap.put("cloBankAccountPre", agentColinfoVo.getCloBankAccount());
                                    resultMap.put("cloBankBranchPre", agentColinfoVo.getCloBankBranch());
                                    resultMap.put("branchLineNumPre", agentColinfoVo.getBranchLineNum());
                                    if(null!=agentColinfoVo.getCloInvoice()){
                                        if (agentColinfoVo.getCloInvoice().compareTo(new BigDecimal(0))==0){
                                            resultMap.put("cloInvoicePre", "否");
                                        }else{
                                            resultMap.put("cloInvoicePre","是");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                resultList.add(resultMap);
            }
        }
        return resultList;
    }
}
