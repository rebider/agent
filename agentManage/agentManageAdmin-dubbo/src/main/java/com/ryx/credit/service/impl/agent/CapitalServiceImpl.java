package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.CapitalFlowMapper;
import com.ryx.credit.dao.agent.CapitalMapper;
import com.ryx.credit.dao.agent.PayCompMapper;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.CapitalService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Lihl
 * @Date 2018/10/11
 * 保证金
 */
@Service("capitalService")
public class CapitalServiceImpl implements CapitalService {

    @Autowired
    private CapitalMapper capitalMapper;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private CapitalFlowMapper capitalFlowMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private PayCompMapper payCompMapper;


    @Override
    public List<Capital> queryCapital(String agentId) {
        if(StringUtils.isNotBlank(agentId)){
            CapitalExample capitalExample = new CapitalExample();
            CapitalExample.Criteria criteria = capitalExample.createCriteria();
            criteria.andCAgentIdEqualTo(agentId);
            criteria.andStatusEqualTo(Status.STATUS_1.status);
            List<Capital> capitals = capitalMapper.selectByExample(capitalExample);
            for (Capital capital : capitals) {
                Dict dictByValue = dictOptionsService.findDictByValue(DictGroup.AGENT.name(), DictGroup.CAPITAL_TYPE.name(), capital.getcType());
                if(null!=dictByValue)
                capital.setcType(dictByValue.getdItemname());
            }
            return capitals;
        }
        return null;
    }

    @Override
    public List<Capital> queryCapital(String agentId,String cPayType) {
        if(StringUtils.isNotBlank(agentId)){
            CapitalExample capitalExample = new CapitalExample();
            CapitalExample.Criteria criteria = capitalExample.createCriteria();
            criteria.andCAgentIdEqualTo(agentId);
            criteria.andCPayTypeEqualTo(cPayType);
            criteria.andStatusEqualTo(Status.STATUS_1.status);
            List<Capital> capitals = capitalMapper.selectByExample(capitalExample);
            return capitals;
        }
        return null;
    }

    @Override
    public List<Capital> queryByAgentId(String agentId) {
        if(StringUtils.isNotBlank(agentId)){
            CapitalExample capitalExample = new CapitalExample();
            CapitalExample.Criteria criteria = capitalExample.createCriteria();
            criteria.andCAgentIdEqualTo(agentId);
            criteria.andStatusEqualTo(Status.STATUS_1.status);
            List<Capital> capitals = capitalMapper.selectByExample(capitalExample);
            return capitals;
        }
        return null;
    }

    /**
     * 汇总列表
     * @param param
     * @param pageInfo
     * @return
     */
    @Override
    public PageInfo getCapitalSummaryList(Map<String, Object> param, PageInfo pageInfo) {
        Long count = capitalMapper.getCapitalSummaryCount(param);
        List<Map<String, Object>> list = capitalMapper.getCapitalSummaryList(param);
        pageInfo.setTotal(count.intValue());
        pageInfo.setRows(list);
        return pageInfo;
    }

    /**
     * 缴纳款记录
     * @param capital
     * @param page
     * @param userId
     * @return
     */
    @Override
    public PageInfo queryCapitalList(Capital capital, Page page, String dataRole, Long userId) {
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("status", Status.STATUS_1.status);
        if (StringUtils.isNotBlank(capital.getId())) {
            reqMap.put("id", capital.getId());
        }
        if (StringUtils.isNotBlank(capital.getcAgentId())) {
            reqMap.put("agentId", capital.getcAgentId());
        }
        if(StringUtils.isBlank(dataRole)){
            List<Map<String, Object>> orgCodeRes = iUserService.orgCode(userId);
            if(orgCodeRes == null && orgCodeRes.size() != 1){
                return null;
            }
            Map<String, Object> stringObjectMap = orgCodeRes.get(0);
            reqMap.put("orgId", String.valueOf(stringObjectMap.get("ORGID")));
        }
        reqMap.put("cloReviewStatus", AgStatus.Approved.getValue());
        List<Map<String, Object>> capitalChangeList = capitalMapper.queryCapitalList(reqMap, page);
        for (Map<String, Object> stringMap : capitalChangeList) {
            String cInCom = String.valueOf(stringMap.get("C_IN_COM"));
            PayComp payComp = payCompMapper.selectByPrimaryKey(cInCom);
            if(null!=payComp){
                stringMap.put("C_IN_COM",payComp.getComName());
            }
        }
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(capitalChangeList);
        pageInfo.setTotal(capitalMapper.queryCapitalCount(reqMap));
        return pageInfo;
    }

    /**
     * 扣除资金记录表
     * @param capitals
     * @param amt 扣除金额
     * @throws Exception
     */
    @Override
    public void disposeCapital(List<Capital> capitals, BigDecimal amt,String srcId,String cUser,
                               String agentId,String agentName,String remark)throws Exception{
        BigDecimal residueAmt = amt;
        for (Capital capital : capitals) {
            BigDecimal fqInAmount = capital.getcFqInAmount();
            BigDecimal freezeAmt = capital.getFreezeAmt();
            BigDecimal lockAmt = capital.getcFqInAmount().subtract(residueAmt);
            BigDecimal operationAmt = BigDecimal.ZERO;
            //如果等于已扣足
            if (lockAmt.compareTo(BigDecimal.ZERO) == 0) {
                operationAmt = capital.getcFqInAmount();
                capital.setFreezeAmt(capital.getFreezeAmt().add(capital.getcFqInAmount()));
            } else if (lockAmt.compareTo(BigDecimal.ZERO) == 1) {
                operationAmt = residueAmt;
                capital.setFreezeAmt(capital.getFreezeAmt().add(residueAmt));
            } else {
                operationAmt = capital.getcFqInAmount();
                capital.setFreezeAmt(capital.getFreezeAmt().add(capital.getcFqInAmount()));
                String lockAmtStr = String.valueOf(lockAmt);
                String substring = lockAmtStr.substring(1, lockAmtStr.length());
                residueAmt = new BigDecimal(substring);
            }
            capital.setcFqInAmount(capital.getcFqInAmount().subtract(capital.getFreezeAmt()).add(freezeAmt));
            capital.setcUtime(new Date());
            int i = capitalMapper.updateByPrimaryKey(capital);
            if (i != 1) {
                throw new MessageException("更新资金记录失败！");
            }
            if(fqInAmount.compareTo(BigDecimal.ZERO)==1 && operationAmt.compareTo(BigDecimal.ZERO)==1){
                CapitalFlow capitalFlow = new CapitalFlow();
                capitalFlow.setId(idService.genId(TabId.A_CAPITAL_FLOW));
                capitalFlow.setcType(capital.getcType());
                capitalFlow.setCapitalId(capital.getId());
                capitalFlow.setSrcType(SrcType.BZJ.getValue());
                capitalFlow.setSrcId(srcId);
                capitalFlow.setBeforeAmount(fqInAmount);
                capitalFlow.setcAmount(operationAmt);
                capitalFlow.setOperationType(OperateTypes.CZ.getValue());
                capitalFlow.setAgentId(agentId);
                capitalFlow.setAgentName(agentName);
                capitalFlow.setRemark(remark);
                capitalFlow.setcTime(new Date());
                capitalFlow.setuTime(new Date());
                capitalFlow.setcUser(cUser);
                capitalFlow.setuUser(cUser);
                capitalFlow.setStatus(Status.STATUS_1.status);
                capitalFlow.setVersion(BigDecimal.ZERO);
                capitalFlow.setFlowStatus(Status.STATUS_0.status);//未生效
                capitalFlowMapper.insertSelective(capitalFlow);
            }
            if (lockAmt.compareTo(BigDecimal.ZERO) >= 0) {
                break;
            }
        }
    }

}
