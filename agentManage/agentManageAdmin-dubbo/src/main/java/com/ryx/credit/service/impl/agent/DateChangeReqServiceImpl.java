package com.ryx.credit.service.impl.agent;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.enumc.DictGroup;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.DateChangeRequestMapper;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.DateChangeRequest;
import com.ryx.credit.pojo.admin.agent.Dict;
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
    @Override
    public ResultVO dateChangeReqIn(String json,String oldJson,String srcId,String type,String userId) {
        try {
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
                                    resultMap.put("cloBank", agentColinfoVo.getCloBank());
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
                                    resultMap.put("cloBankPre", agentColinfoVo.getCloBank());
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
