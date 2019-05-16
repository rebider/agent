package com.ryx.credit.service.impl.agent;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AgentMapper;
import com.ryx.credit.dao.agent.CapitalFlowMapper;
import com.ryx.credit.dao.agent.CapitalMapper;
import com.ryx.credit.dao.agent.PayCompMapper;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.CapitalService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @Author Lihl
 * @Date 2018/10/11
 * 保证金
 */
@Service("capitalService")
public class CapitalServiceImpl implements CapitalService {

    private Logger logger = LoggerFactory.getLogger(CapitalServiceImpl.class);

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
    @Autowired
    private AgentMapper agentMapper;


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
            criteria.andCloReviewStatusEqualTo(AgStatus.Approved.getValue());
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
    public PageInfo getCapitalSummaryList(Map<String, Object> param, PageInfo pageInfo,String dataRole,Long userId) {

        if(StringUtils.isBlank(dataRole)){
            List<Map<String, Object>> orgCodeRes = iUserService.orgCode(userId);
            if(orgCodeRes == null && orgCodeRes.size() != 1){
                return null;
            }
            Map<String, Object> stringObjectMap = orgCodeRes.get(0);
            param.put("orgId", String.valueOf(stringObjectMap.get("ORGID")));
        }
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


    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public void profitIncom(String srcId,String capId,BigDecimal amount,String cUser)throws ProcessException{
        Capital capital = capitalMapper.selectByPrimaryKey(capId);
        Agent agent = agentMapper.selectByPrimaryKey(capital.getcAgentId());
        BigDecimal fqInAmount = capital.getcFqInAmount()==null?new BigDecimal(0):capital.getcFqInAmount();
        capital.setcFqInAmount(fqInAmount.add(amount));
        if (1!=capitalMapper.updateByPrimaryKeySelective(capital)){
            logger.info("资金记录抵扣金额更新失败");
            throw new ProcessException("资金记录抵扣金额更新失败");
        }
        Calendar c = Calendar.getInstance();
        //添加冻结资金流水
        CapitalFlow capitalFlow = new CapitalFlow();
        capitalFlow.setId(idService.genId(TabId.A_CAPITAL_FLOW));
        capitalFlow.setcType(capital.getcType());
        capitalFlow.setCapitalId(capital.getId());
        capitalFlow.setSrcType(SrcType.FRHRU.code);
        //资源类型id为 paymentdetail id
        capitalFlow.setSrcId(srcId);
        capitalFlow.setBeforeAmount(fqInAmount);
        capitalFlow.setcAmount(amount);
        capitalFlow.setOperationType(OperateTypes.RZ.getValue());
        capitalFlow.setAgentId(capital.getcAgentId());
        capitalFlow.setAgentName(agent.getAgName());
        capitalFlow.setRemark("分润抵扣");
        capitalFlow.setcTime(c.getTime());
        capitalFlow.setuTime(c.getTime());
        capitalFlow.setcUser(cUser);
        capitalFlow.setuUser(cUser);
        capitalFlow.setStatus(Status.STATUS_1.status);
        capitalFlow.setVersion(BigDecimal.ZERO);
        capitalFlow.setFlowStatus(Status.STATUS_1.status);//未生效
        if(1!=capitalFlowMapper.insertSelective(capitalFlow)){
            throw new ProcessException("明细生成失败");
        }

    }
    /**
     * 冻结扣除资金记录表
     * @param capitalType
     * @param amt
     * @param srcId
     * @param cUser
     * @param agentId
     * @param agentName
     * @param remark
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public void disposeCapital(String capitalType, BigDecimal amt, String srcId,String cUser,
                               String agentId,String agentName,String remark,SrcType srcType,PayType payType )throws Exception{

        //传递进来的扣除金额不能小于0
        if(amt==null || amt.compareTo(BigDecimal.ZERO)<=0){
            throw new MessageException("冻结缴款金额错误");
        }

        CapitalExample capitalExample = new CapitalExample();
        CapitalExample.Criteria criteria = capitalExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andCloReviewStatusEqualTo(AgStatus.Approved.getValue());
        criteria.andCAgentIdEqualTo(agentId);
        if(null!=capitalType)
        criteria.andCTypeEqualTo(capitalType);
        if(null!=payType)
        criteria.andCPayTypeEqualTo(payType.code);
        capitalExample.setOrderByClause(" c_fq_in_amount asc");
        List<Capital> capitals = capitalMapper.selectByExample(capitalExample);
        BigDecimal residueAmt = amt;  //算出未扣足的金额
        for (Capital capital : capitals) {
            BigDecimal fqInAmount = capital.getcFqInAmount();//可用余额
            BigDecimal operationAmt = BigDecimal.ZERO; //当前资金要扣除的金额
            //当前可用金额大于要冻结的金额
            if(fqInAmount.compareTo(residueAmt)>=1){
                //当前资金需要冻结的金额
                operationAmt = residueAmt;
                //下一笔需要扣除 为0  因为本次已经全部扣除
                residueAmt = BigDecimal.ZERO;
            //当前可用金额小于要冻结的金额
            } else {
                //全部扣除可用余额
                operationAmt = fqInAmount; //当前资金需要冻结的金额
                //下笔要扣除的金额为未扣除 - 全部扣除的可用余额
                residueAmt =residueAmt.subtract(fqInAmount);//下一笔需要扣除
            }
            //冻结可用余额
            capital.setcFqInAmount(capital.getcFqInAmount().subtract(operationAmt));
            //冻结金额加上要冻结的金额
            capital.setFreezeAmt(capital.getFreezeAmt().add(operationAmt));
            capital.setcUtime(new Date());
            int i = capitalMapper.updateByPrimaryKey(capital);
            if (i != 1) {
                throw new MessageException("更新资金记录失败！");
            }
            //添加冻结资金流水
            CapitalFlow capitalFlow = new CapitalFlow();
            capitalFlow.setId(idService.genId(TabId.A_CAPITAL_FLOW));
            capitalFlow.setcType(capital.getcType());
            capitalFlow.setCapitalId(capital.getId());
            capitalFlow.setSrcType(srcType.code);
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
            if (residueAmt.compareTo(BigDecimal.ZERO) < 1) {
                break;
            }
        }

        if (residueAmt.compareTo(BigDecimal.ZERO) == 1) {
            throw new MessageException("缴款金额不足，无法进行全额冻结");
        }
    }


    /**
     * 将冻结金额恢复
     * @param capitalType
     * @param amt
     * @param srcId
     * @param cUser
     * @param agentId
     * @param agentName
     * @param remark
     * @param srcType
     * @param payType
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public void unDisposeCapital(String capitalType, BigDecimal amt, String srcId,String cUser,
                               String agentId,String agentName,String remark,SrcType srcType,PayType payType )throws Exception{
        CapitalFlowExample capitalFlowExample = new CapitalFlowExample();
        CapitalFlowExample.Criteria criteria1 = capitalFlowExample.createCriteria();
        criteria1.andStatusEqualTo(Status.STATUS_1.status);
        criteria1.andFlowStatusEqualTo(Status.STATUS_0.status);
        criteria1.andSrcIdEqualTo(srcId);
        criteria1.andSrcTypeEqualTo(srcType.code);
        List<CapitalFlow> capitalFlows = capitalFlowMapper.selectByExample(capitalFlowExample);
        for (CapitalFlow capitalFlow : capitalFlows) {
            //解冻
            Capital capital = capitalMapper.selectByPrimaryKey(capitalFlow.getCapitalId());
            capital.setFreezeAmt(capital.getFreezeAmt().subtract(capitalFlow.getcAmount()));
            capital.setcFqInAmount(capital.getcFqInAmount().add(capitalFlow.getcAmount()));
            int i = capitalMapper.updateByPrimaryKeySelective(capital);
            if(i!=1){
                throw new MessageException("通过更新冻结金额失败！");
            }
            //删除资金流水
            logger.info("删除资金流水:{}", JSONObject.toJSONString(capitalFlow));
            CapitalFlowExample example = new CapitalFlowExample();
            example.or().andIdEqualTo(capitalFlow.getId());
            int j = capitalFlowMapper.deleteByExample(example);
            if(j!=1){
                throw new MessageException("通过更新资金流水记录失败！");
            }

        }
    }

    /**
     * 通过扣除
     * @param srcId
     * @param srcType
     * @param uUser
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public List<CapitalFlow> approvedDeduct(String srcId,SrcType srcType,String uUser)throws Exception{
        CapitalFlowExample capitalFlowExample = new CapitalFlowExample();
        CapitalFlowExample.Criteria criteria1 = capitalFlowExample.createCriteria();
        criteria1.andStatusEqualTo(Status.STATUS_1.status);
        criteria1.andFlowStatusEqualTo(Status.STATUS_0.status);
        criteria1.andSrcIdEqualTo(srcId);
        criteria1.andSrcTypeEqualTo(srcType.code);
        List<CapitalFlow> capitalFlows = capitalFlowMapper.selectByExample(capitalFlowExample);
        for (CapitalFlow capitalFlow : capitalFlows) {
            capitalFlow.setuUser(uUser);
            capitalFlow.setuTime(new Date());
            capitalFlow.setFlowStatus(Status.STATUS_1.status);
            int j = capitalFlowMapper.updateByPrimaryKey(capitalFlow);
            if(j!=1){
                throw new MessageException("通过更新资金流水记录失败！");
            }
            Capital capital = capitalMapper.selectByPrimaryKey(capitalFlow.getCapitalId());
            capital.setFreezeAmt(capital.getFreezeAmt().subtract(capitalFlow.getcAmount()));
            int i = capitalMapper.updateByPrimaryKeySelective(capital);
            if(i!=1){
                throw new MessageException("通过更新冻结金额失败！");
            }
        }
        return capitalFlows;
    }

    /**
     * 拒绝解冻
     * @param srcId
     * @param srcType
     * @param uUser
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public List<CapitalFlow> refuseUnfreeze(String srcId,SrcType srcType,String uUser)throws Exception{
        CapitalFlowExample capitalFlowExample = new CapitalFlowExample();
        CapitalFlowExample.Criteria criteria1 = capitalFlowExample.createCriteria();
        criteria1.andStatusEqualTo(Status.STATUS_1.status);
        criteria1.andFlowStatusEqualTo(Status.STATUS_0.status);
        criteria1.andSrcIdEqualTo(srcId);
        criteria1.andSrcTypeEqualTo(srcType.code);
        List<CapitalFlow> capitalFlows = capitalFlowMapper.selectByExample(capitalFlowExample);
        for (CapitalFlow capitalFlow : capitalFlows) {
            Capital capital = capitalMapper.selectByPrimaryKey(capitalFlow.getCapitalId());
            capital.setFreezeAmt(capital.getFreezeAmt().subtract(capitalFlow.getcAmount()));
            capital.setcFqInAmount(capital.getcFqInAmount().add(capitalFlow.getcAmount()));
            int i = capitalMapper.updateByPrimaryKeySelective(capital);
            if(i!=1){
                throw new MessageException("通过更新冻结金额失败！");
            }
        }
        return capitalFlows;
    }

}
