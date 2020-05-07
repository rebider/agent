package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.enumc.BusActRelBusType;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.DateUtils;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.profit.dao.BalanceApprovalMapper;
import com.ryx.credit.profit.dao.PBalanceSerialMapper;
import com.ryx.credit.profit.dao.PmsProfitMapper;
import com.ryx.credit.profit.pojo.*;
import com.ryx.credit.profit.service.IAgentPayService;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.agent.AgentEnterService;
import com.ryx.credit.service.agent.TaskApprovalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/***
 * @author renshenghao
 * @version 1.0
 * @
 */
@Service("agentPayService")
public class AgentPayServiceImpl implements IAgentPayService {

    Logger logger = LoggerFactory.getLogger(AgentPayServiceImpl.class);

    @Autowired
    private AgentEnterService agentEnterService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private TaskApprovalService taskApprovalService;
    @Autowired
    private BalanceApprovalMapper balanceApprovalMapper;
    @Autowired
    private PmsProfitMapper pmsProfitMapper;
    @Autowired
    private PBalanceSerialMapper pBalanceSerialMapper;


    @Override
    public PageInfo getBalanceApprovalList(Page page, BalanceApproval balanceApproval){
        PageInfo pageInfo=new PageInfo();
        BalanceApprovalExample example = new BalanceApprovalExample();
        example.setPage(page);
        BalanceApprovalExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(balanceApproval.getAgentId()))
            criteria.andAgentIdEqualTo(balanceApproval.getAgentId());
        if (StringUtils.isNotBlank(balanceApproval.getAgentName()))
            criteria.andAgentNameEqualTo(balanceApproval.getAgentName());
        if (StringUtils.isNotBlank(balanceApproval.getApplyDate()))
            criteria.andApplyDateEqualTo(balanceApproval.getApplyDate());
        if (StringUtils.isNotBlank(balanceApproval.getApplyTime()))
            criteria.andApplyTimeEqualTo(balanceApproval.getApplyTime());
        if (StringUtils.isNotBlank(balanceApproval.getApplyUser()))
            criteria.andApplyUserEqualTo(balanceApproval.getApplyUser());
        if (StringUtils.isNotBlank(balanceApproval.getApprovalStatus()))
            criteria.andApprovalStatusEqualTo(balanceApproval.getApprovalStatus());
        if (StringUtils.isNotBlank(balanceApproval.getBalanceId()))
            criteria.andBalanceIdEqualTo(balanceApproval.getBalanceId());
        if (StringUtils.isNotBlank(balanceApproval.getBatchNo()))
            criteria.andBatchNoEqualTo(balanceApproval.getBatchNo());
        if (StringUtils.isNotBlank(balanceApproval.getBusCode()))
            criteria.andBusCodeEqualTo(balanceApproval.getBusCode());
        if (StringUtils.isNotBlank(balanceApproval.getRequestNo()))
            criteria.andRequestNoEqualTo(balanceApproval.getRequestNo());
        if (StringUtils.isNotBlank(balanceApproval.getSettleMonth()))
            criteria.andSettleMonthEqualTo(balanceApproval.getSettleMonth());
        List<BalanceApproval> balanceApprovals = balanceApprovalMapper.selectByExample(example);
        int countByExample = (int) balanceApprovalMapper.countByExample(example);
        pageInfo.setRows(balanceApprovals);
        pageInfo.setTotal(countByExample);
        return pageInfo;
    }

    @Override
    public List<Map<String, Object>> getBalanceApprovalList(BalanceApproval balanceApproval){
        List<Map<String, Object>> balanceApprovals = balanceApprovalMapper.getBalanceApprovalList(balanceApproval);
        return balanceApprovals;
    }

    @Override
    public boolean applyAgentPay(String balanceBatchNo, List<String> balanceIds, String userId, String workId) {

        if (StringUtils.isBlank(balanceBatchNo))
            throw new ProcessException("出款失败，出款批次号创建失败。");
        if (balanceIds.size()<1)
            throw new ProcessException("出款失败，出款对象为空!");
        if (StringUtils.isBlank(userId))
            throw new ProcessException("出款失败，出款人异常");
        if (StringUtils.isBlank(workId))
            throw new ProcessException("出款失败，workId为空。");


        String applyDate = DateUtil.format(new Date(), "yyyyMMdd");
        String applyTime = DateUtil.format(new Date(), "HH:mm:ss");
        for (String balanceId:balanceIds){
            BalanceApproval approval = new BalanceApproval();
            PmsProfit pmsProfit = pmsProfitMapper.selectByPrimaryKey(balanceId);
            approval.setBalanceId(pmsProfit.getBalanceId());    //出款流水
            approval.setBatchNo(balanceBatchNo);    //出款批次号
            approval.setSettleMonth(pmsProfit.getSettleMonth());//月份
            approval.setApplyDate(applyDate);       //申请日期
            approval.setApplyUser(userId);          //审批提出人
            approval.setApplyTime(applyTime);       //申请时间
            approval.setAgentId(pmsProfit.getUniqueFlag()); //AG码
            approval.setAgentName(pmsProfit.getAgentName()); //代理商名称
            approval.setBusCode(pmsProfit.getBusCode());     //品牌码
            approval.setRealityAgId(pmsProfit.getRealityAgId()); //实际打款代理商ID
            approval.setRealityAgName(pmsProfit.getRealityAgName()); //实际打款代理商名称
            approval.setRequestNo(pmsProfit.getProfitType());   //请款批次
            approval.setBalanceAmt(pmsProfit.getBalanceAmt());  //结算金额
            approval.setApprovalStatus("00");       //审批状态：申请中
            balanceApprovalMapper.insert(approval);
        }

        Map<String,Object> map=agentEnterService.startPar(userId);
        Map<String,Object> param=new HashMap<String,Object>();
        if(map!=null&&map.get("party")!=null){
            param.put("part",map.get("party"));
        }
        String proceId = activityService.createDeloyFlow(null, workId, null, null, param);
        if (proceId == null) {
            throw new ProcessException("代理商出款审批流启动失败!");
        }

        for (String balanceId:balanceIds){
            PmsProfit  pmsProfit= new PmsProfit();
            pmsProfit.setBalanceId(balanceId);
            pmsProfit.setBatchNo(balanceBatchNo);
            pmsProfit.setSunbmitCheckTime(applyDate+" "+applyTime);
            pmsProfit.setBillStatus("04");  //审批中
            pmsProfitMapper.updateByPrimaryKeySelective(pmsProfit);
        }

        BusActRel record = new BusActRel();
        record.setBusId(balanceBatchNo);
        record.setActivId(proceId);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(userId);
        record.setBusType(BusActRelBusType.BALANCE_APPLY.name());
        record.setDataShiro(BusActRelBusType.BALANCE_APPLY.key);
        try {
            taskApprovalService.addABusActRel(record);
            logger.info("代理商出款审批流启动成功");
        } catch (Exception e) {
            e.getStackTrace();
            logger.error("代理商出款审批流启动失败{}");
            throw new ProcessException("代理商出款审批流启动失败!:{}",e.getMessage());
        }
        return true;
    }

    @Override
    public List<Map<String,Object>> queryDetailByBatchNo(String batchNo) {
        return balanceApprovalMapper.queryDetailByBatchNo(batchNo);
    }

    @Override
    public int updateAgentPayByBalanceIdAndBatchNo(String balanceId, String batchNo,String approvalStatus) {
        return balanceApprovalMapper.updateAgentPayByBalanceIdAndBatchNo(balanceId, batchNo, approvalStatus);
    }

    @Override
    public long countByBatchNo(String batchNo) {
        BalanceApprovalExample example = new BalanceApprovalExample();
        BalanceApprovalExample.Criteria criteria = example.createCriteria();
        criteria.andBatchNoEqualTo(batchNo);
        return balanceApprovalMapper.countByExample(example);
    }

    @Override
    public BigDecimal countAmtByBatchNo(String batchNo) {
        BalanceApprovalExample example = new BalanceApprovalExample();
        BalanceApprovalExample.Criteria criteria = example.createCriteria();
        criteria.andBatchNoEqualTo(batchNo);
        return balanceApprovalMapper.countAmtByBatchNo(example);
    }

    @Override
    public int updateBalanceApprovalAcct(BalanceApproval approval) {
        return balanceApprovalMapper.updateBalanceApprovalAcct(approval);
    }

    /**
     * 更新审批状态
     * @param batchNo
     * @param approvalResult 审批结果  0:审批通过 1：审批拒绝
     * @return
     */
    public int updateBalanceApproval(String batchNo,String approvalResult){
        int result = 0;
        if ("0".equals(approvalResult)){    //审批通过
            //将审批中改为审批通过
            result = result + balanceApprovalMapper.updateBalanceApproval(batchNo, "00", "01");
            //将退回中改为审批退回
            result = result + balanceApprovalMapper.updateBalanceApproval(batchNo, "02", "03");
        }else if ("1".equals(approvalResult)){  //审批退回
            //将此次审批的所有记录改为审批退回
            result = result + balanceApprovalMapper.updateBalanceApproval(batchNo, null, "03");
        }
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentResult approvalAgentPayTask(AgentVo agentVo, String userId) throws Exception {
        logger.info("审批对象：{}", JSONObject.toJSON(agentVo));
        AgentResult result = new AgentResult(500, "系统异常", "");
        Map<String, Object> reqMap = new HashMap<>();
        if(StringUtils.isNotBlank(agentVo.getOrderAprDept())){
            reqMap.put("dept", agentVo.getOrderAprDept());
        }
        if(Objects.equals("pass",agentVo.getApprovalResult())
                && StringUtils.isBlank(agentVo.getOrderAprDept())){
            reqMap.put("dept", "finish");
        }
        reqMap.put("rs", agentVo.getApprovalResult());
        reqMap.put("approvalOpinion", agentVo.getApprovalOpinion());
        reqMap.put("approvalPerson", userId);
        reqMap.put("createTime", DateUtils.dateToStringss(new Date()));
        reqMap.put("taskId", agentVo.getTaskId());

        logger.info("创建下一审批流对象：{}", reqMap.toString());
        Map resultMap = activityService.completeTask(agentVo.getTaskId(), reqMap);
        Boolean rs = (Boolean) resultMap.get("rs");
        String msg = String.valueOf(resultMap.get("msg"));
        if (resultMap == null) {
            return result;
        }
        if (!rs) {
            result.setMsg(msg);
            return result;
        }
        return AgentResult.ok(resultMap);
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public void completeTaskEnterActivity(String insid, String status) {

        BusActRel busActRel = new BusActRel();
        busActRel.setActivId(insid);
        try {
            BusActRel rel =  taskApprovalService.queryBusActRel(busActRel);
            String batchNo = rel.getBusId();
            String endTime = DateUtil.format(new Date(),DateUtil.DATE_FORMAT_1);
            String settleDate = DateUtil.format(new Date(),DateUtil.DATE_FORMAT_3);//清结算日期
            if (rel != null) {
                BalanceApproval approval = new BalanceApproval();
                approval.setBatchNo(batchNo);
                if("0".equals(status)){ //审批通过
                    //业务相关
                    updateBalanceApproval(batchNo, "0");   //审批流表状态更新
                    List<Map<String, Object>> approvalList = getBalanceApprovalList(approval);  //此批次的所有出款审批
                    for (int i = 0; i < approvalList.size(); i++) {
                        Map<String, Object> approvalMap = approvalList.get(i);

                        String approvalStatus = String.valueOf(approvalMap.get("APPROVAL_STATUS"));
                        String balanceId = String.valueOf(approvalMap.get("BALANCE_ID"));
                        if ("03".equals(approvalStatus)) {//审批退回的
                            PmsProfit pmsProfit = new PmsProfit();
                            pmsProfit.setBalanceId(balanceId);
                            pmsProfit.setBillStatus("05");
                            pmsProfitMapper.updateByPrimaryKeySelective(pmsProfit);

                            BalanceApproval upApproval = new BalanceApproval();
                            upApproval.setBalanceId(balanceId);
                            upApproval.setEndTime(endTime);
                            updateBalanceApprovalAcct(upApproval);

                            continue;
                        }


                        String balanceAcctNo = String.valueOf(approvalMap.get("BALANCE_ACCT_NO"));
                        String balanceAcctName = String.valueOf(approvalMap.get("BALANCE_ACCT_NAME"));
                        String balanceBankNo = String.valueOf(approvalMap.get("BALANCE_BANK_NO"));
                        String balanceBankName = String.valueOf(approvalMap.get("BALANCE_BANK_NAME"));
                        String varsion = String.valueOf(approvalMap.get("VARSION"));
                        PmsProfit pmsProfit = new PmsProfit();
                        pmsProfit.setBalanceId(balanceId);
                        pmsProfit.setBalanceAcctNo(balanceAcctNo);
                        pmsProfit.setBalanceAcctName(balanceAcctName);
                        pmsProfit.setBalanceBankNo(balanceBankNo);
                        pmsProfit.setBalanceBankName(balanceBankName);
                        pmsProfit.setBillStatus("06");
                        pmsProfitMapper.updateByPrimaryKeySelective(pmsProfit);  //审批通过 更新pmsProfit表中状态以及结算卡信息

                        BalanceApproval upApproval = new BalanceApproval();
                        upApproval.setBalanceId(balanceId);
                        upApproval.setBalanceAcctNo(balanceAcctNo);
                        upApproval.setBalanceAcctName(balanceAcctName);
                        upApproval.setBalanceBankNo(balanceBankNo);
                        upApproval.setBalanceBankName(balanceBankName);
                        upApproval.setEndTime(endTime);
                        updateBalanceApprovalAcct(upApproval);

                        PBalanceSerial pBalanceSerial =new PBalanceSerial();
                        String agentId = String.valueOf(approvalMap.get("AGENT_ID"));
                        String agentName = String.valueOf(approvalMap.get("AGENT_NAME"));
                        BigDecimal balanceAmt = new BigDecimal(String.valueOf(approvalMap.get("BALANCE_AMT")));
                        String balanceBatchNo = String.valueOf(approvalMap.get("BATCH_NO"));
                        String settleMonth = String.valueOf(approvalMap.get("SETTLE_MONTH"));
                        String busCode = String.valueOf(approvalMap.get("BUS_CODE"));
                        String requestNoTemp = String.valueOf(approvalMap.get("REQUEST_NO_TEMP"));
                        String realAgId = String.valueOf(approvalMap.get("REALITY_AG_ID"));
                        String realAgName = String.valueOf(approvalMap.get("REALITY_AG_NAME"));
                        String balanceAcctType = String.valueOf(approvalMap.get("CLO_TYPE"));
                        String applyUser = String.valueOf(approvalMap.get("APPLY_USER"));
                        String applyDate = String.valueOf(approvalMap.get("APPLY_DATE"));
                        String applyTime = String.valueOf(approvalMap.get("APPLY_TIME"));

                        pBalanceSerial.setBalanceId(balanceId);
                        pBalanceSerial.setBalanceBatchNo(balanceBatchNo);
                        pBalanceSerial.setSettleMonth(settleMonth);
                        pBalanceSerial.setSettleDate(settleDate);
                        pBalanceSerial.setAgId(agentId);
                        pBalanceSerial.setAgName(agentName);
                        pBalanceSerial.setBrandNo(busCode);
                        pBalanceSerial.setImportBatch(requestNoTemp);
                        pBalanceSerial.setRealAgId(realAgId);
                        pBalanceSerial.setRealAgName(realAgName);
                        pBalanceSerial.setBalanceAmt(balanceAmt);
                        pBalanceSerial.setBalanceAcctNo(balanceAcctNo);
                        pBalanceSerial.setBalanceAcctName(balanceAcctName);
                        pBalanceSerial.setBalanceAcctType(balanceAcctType);
                        pBalanceSerial.setBalanceBankNo(balanceBankNo);
                        pBalanceSerial.setBalanceBankName(balanceBankName);
                        pBalanceSerial.setBalanceStatus("00");
                        pBalanceSerial.setSubmitTer(applyUser);
                        pBalanceSerial.setSubmitTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("yyyyMMddHH:mm:ss").parse(applyDate+applyTime)));
                        pBalanceSerial.setBalanceBankVersion(varsion);
                        pBalanceSerialMapper.insert(pBalanceSerial);


                    }

                    rel.setActivStatus(AgStatus.Approved.name());
                }else if ("1".equals(status)){  //审批拒绝
                    //业务相关
                    updateBalanceApproval(batchNo, "1");   //审批流表状态更新
                    List<Map<String, Object>> approvalList = getBalanceApprovalList(approval);  //此批次的所有出款审批
                    for (int i = 0; i < approvalList.size(); i++) {
                        Map<String, Object> approvalMap = approvalList.get(i);

                        String approvalStatus = String.valueOf(approvalMap.get("APPROVAL_STATUS"));
                        String balanceId = String.valueOf(approvalMap.get("BALANCE_ID"));
                        if ("03".equals(approvalStatus)) {//审批退回的
                            PmsProfit pmsProfit = new PmsProfit();
                            pmsProfit.setBalanceId(balanceId);
                            pmsProfit.setBillStatus("05");
                            pmsProfitMapper.updateByPrimaryKeySelective(pmsProfit);

                            BalanceApproval upApproval = new BalanceApproval();
                            upApproval.setBalanceId(balanceId);
                            upApproval.setEndTime(endTime);
                            updateBalanceApprovalAcct(upApproval);
                        }
                    }

                    rel.setActivStatus(AgStatus.Refuse.name());
                }else throw new RuntimeException("审批流异常");
                logger.info("更新审批流与业务对象");
                taskApprovalService.updateABusActRel(rel);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("代理商出款审批流回调异常，activId：{}" + insid);
        }
    }

}
