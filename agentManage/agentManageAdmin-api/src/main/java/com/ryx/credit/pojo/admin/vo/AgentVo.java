package com.ryx.credit.pojo.admin.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ryx.credit.common.util.DateJsonDeserializer;
import com.ryx.credit.common.util.DateJsonSerializer;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.AgentColinfoRel;
import com.ryx.credit.pojo.admin.agent.AgentMerge;
import com.ryx.credit.pojo.admin.agent.AgentQuit;
import com.ryx.credit.pojo.admin.order.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by cx on 2018/5/28.
 */
public class AgentVo implements Serializable {

    private Agent agent;

    public String getPretest() {
        return pretest;
    }

    public void setPretest(String pretest) {
        this.pretest = pretest;
    }

    private List<CapitalVo> capitalVoList;
    private List<AgentContractVo> contractVoList;
    private List<AgentColinfoVo> colinfoVoList;
    private List<AgentBusInfoVo> busInfoVoList;
    private List<String> agentTableFile;
    private String agentId;
    private String submitType;
    private String agDocProString;
    private String agDocDistrictString;
    private List<AgentColinfoRel> agentColinfoRelList;
    private String approvalOpinion;
    private String approvalResult;
    private String dept;
    private String taskId;
    private String flag;
    private String agentBusId;
    private BigDecimal realPayAmount;
    private String supplementId;
    private Date remitTime;
    private String pretest;
    @JSONField(format="yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @JsonDeserialize(using = DateJsonDeserializer.class)
    @JsonSerialize(using = DateJsonSerializer.class)
    private Date checkTime;
    //订单审批下个审批部门参数
    private String orderAprDept;
    private List<ORefundPriceDiffDetail> refundPriceDiffDetailList;
    private List<String> refundPriceDiffFile;
    private List<String> refundPriceDiffFinanceFile;
    private ORefundPriceDiff oRefundPriceDiff;
    private ORefundPriceDiffVo oRefundPriceDiffVo;
    private List<ODeductCapital> deductCapitalList;
    //付款单
    private Map<String, String> oPayment;

    //退货ID
    private String returnId;
    //退货排单计划
    private String plans;
    //工作流节点ID
    private String sid;
    //退货打款截图
    private String[] attachments;
    private List<Map<String,Object>> reqListMap;
    private ReceiptPlan receiptPlan;
    private List<ReceiptPlan> receiptPlanList;

    private String payMethod;
    //退货单
    private OReturnOrder oReturnOrder;
    private List<OCashReceivablesVo> oCashReceivablesVoList;
    private List<TerminalTransferDetail > terminalTransferDetailList;
    private TerminalTransfer terminalTransfer;
    private String debt;  //欠款
    private String oweTicket;  //欠票
    private BigDecimal mergeType;
    private String mainDocDistrict; //主代理商大区
    private String subDocDistrict;  //副代理商大区
    private AgentQuit agentQuit;
    private BigDecimal realitySuppDept;
    private List<String> quitRefundFile;
    private List<String> capitalChangeFinaFiles;
    private String operationType;
    private BigDecimal amt;
    private String remitTimeStr;
    private String remitPerson;
    private BigDecimal remitAmt;


    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public List<String> getQuitRefundFile() {
        return quitRefundFile;
    }

    public void setQuitRefundFile(List<String> quitRefundFile) {
        this.quitRefundFile = quitRefundFile;
    }

    public BigDecimal getRealitySuppDept() {
        return realitySuppDept;
    }

    public void setRealitySuppDept(BigDecimal realitySuppDept) {
        this.realitySuppDept = realitySuppDept;
    }

    //发票信息
   private String invoiceId;
   private  String invoiceReturnReason;
    private  String invoiceReturnExpressNumber;
    private  String invoiceReturnExpressCompany;
    private  String invoiceReturnDate;

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInvoiceReturnReason() {
        return invoiceReturnReason;
    }

    public void setInvoiceReturnReason(String invoiceReturnReason) {
        this.invoiceReturnReason = invoiceReturnReason;
    }

    public String getInvoiceReturnExpressNumber() {
        return invoiceReturnExpressNumber;
    }

    public void setInvoiceReturnExpressNumber(String invoiceReturnExpressNumber) {
        this.invoiceReturnExpressNumber = invoiceReturnExpressNumber;
    }

    public String getInvoiceReturnExpressCompany() {
        return invoiceReturnExpressCompany;
    }

    public void setInvoiceReturnExpressCompany(String invoiceReturnExpressCompany) {
        this.invoiceReturnExpressCompany = invoiceReturnExpressCompany;
    }

    public String getInvoiceReturnDate() {
        return invoiceReturnDate;
    }

    public void setInvoiceReturnDate(String invoiceReturnDate) {
        this.invoiceReturnDate = invoiceReturnDate;
    }

    public String getMainDocDistrict() {
        return mainDocDistrict;
    }

    public void setMainDocDistrict(String mainDocDistrict) {
        this.mainDocDistrict = mainDocDistrict;
    }

    public String getSubDocDistrict() {
        return subDocDistrict;
    }

    public void setSubDocDistrict(String subDocDistrict) {
        this.subDocDistrict = subDocDistrict;
    }

    public OReturnOrder getoReturnOrder() {
        return oReturnOrder;
    }

    public void setoReturnOrder(OReturnOrder oReturnOrder) {
        this.oReturnOrder = oReturnOrder;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public List<CapitalVo> getCapitalVoList() {
        return capitalVoList;
    }

    public void setCapitalVoList(List<CapitalVo> capitalVoList) {
        this.capitalVoList = capitalVoList;
    }

    public List<AgentContractVo> getContractVoList() {
        return contractVoList;
    }

    public void setContractVoList(List<AgentContractVo> contractVoList) {
        this.contractVoList = contractVoList;
    }

    public List<AgentColinfoVo> getColinfoVoList() {
        return colinfoVoList;
    }

    public void setColinfoVoList(List<AgentColinfoVo> colinfoVoList) {
        this.colinfoVoList = colinfoVoList;
    }

    public List<AgentBusInfoVo> getBusInfoVoList() {
        return busInfoVoList;
    }

    public void setBusInfoVoList(List<AgentBusInfoVo> busInfoVoList) {
        this.busInfoVoList = busInfoVoList;
    }

    public List<String> getAgentTableFile() {
        return agentTableFile;
    }

    public void setAgentTableFile(List<String> agentTableFile) {
        this.agentTableFile = agentTableFile;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getSubmitType() {
        return submitType;
    }

    public void setSubmitType(String submitType) {
        this.submitType = submitType;
    }


    public String getAgDocProString() {
        return agDocProString;
    }

    public void setAgDocProString(String agDocProString) {
        this.agDocProString = agDocProString;
    }

    public String getAgDocDistrictString() {
        return agDocDistrictString;
    }

    public void setAgDocDistrictString(String agDocDistrictString) {
        this.agDocDistrictString = agDocDistrictString;
    }

    public List<AgentColinfoRel> getAgentColinfoRelList() {
        return agentColinfoRelList;
    }

    public void setAgentColinfoRelList(List<AgentColinfoRel> agentColinfoRelList) {
        this.agentColinfoRelList = agentColinfoRelList;
    }

    public String getApprovalOpinion() {
        return approvalOpinion;
    }

    public void setApprovalOpinion(String approvalOpinion) {
        this.approvalOpinion = approvalOpinion;
    }

    public String getApprovalResult() {
        return approvalResult;
    }

    public void setApprovalResult(String approvalResult) {
        this.approvalResult = approvalResult;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getOrderAprDept() {
        return orderAprDept;
    }

    public void setOrderAprDept(String orderAprDept) {
        this.orderAprDept = orderAprDept;
    }

    public Map<String, String> getoPayment() {
        return oPayment;
    }

    public void setoPayment(Map<String, String> oPayment) {
        this.oPayment = oPayment;
    }

    public List<ORefundPriceDiffDetail> getRefundPriceDiffDetailList() {
        return refundPriceDiffDetailList;
    }

    public void setRefundPriceDiffDetailList(List<ORefundPriceDiffDetail> refundPriceDiffDetailList) {
        this.refundPriceDiffDetailList = refundPriceDiffDetailList;
    }

    public List<String> getRefundPriceDiffFile() {
        return refundPriceDiffFile;
    }

    public void setRefundPriceDiffFile(List<String> refundPriceDiffFile) {
        this.refundPriceDiffFile = refundPriceDiffFile;
    }

    public ORefundPriceDiff getoRefundPriceDiff() {
        return oRefundPriceDiff;
    }

    public void setoRefundPriceDiff(ORefundPriceDiff oRefundPriceDiff) {
        this.oRefundPriceDiff = oRefundPriceDiff;
    }

    public List<ODeductCapital> getDeductCapitalList() {
        return deductCapitalList;
    }

    public void setDeductCapitalList(List<ODeductCapital> deductCapitalList) {
        this.deductCapitalList = deductCapitalList;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public ORefundPriceDiffVo getoRefundPriceDiffVo() {
        return oRefundPriceDiffVo;
    }

    public void setoRefundPriceDiffVo(ORefundPriceDiffVo oRefundPriceDiffVo) {
        this.oRefundPriceDiffVo = oRefundPriceDiffVo;
    }

    public String getAgentBusId() {
        return agentBusId;
    }

    public void setAgentBusId(String agentBusId) {
        this.agentBusId = agentBusId;
    }

    public String getPlans() {
        return plans;
    }

    public void setPlans(String plans) {
        this.plans = plans;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String[] getAttachments() {
        return attachments;
    }

    public void setAttachments(String[] attachments) {
        this.attachments = attachments;
    }

    public String getReturnId() {
        return returnId;
    }

    public void setReturnId(String returnId) {
        this.returnId = returnId;
    }

    public List<String> getRefundPriceDiffFinanceFile() {
        return refundPriceDiffFinanceFile;
    }

    public void setRefundPriceDiffFinanceFile(List<String> refundPriceDiffFinanceFile) {
        this.refundPriceDiffFinanceFile = refundPriceDiffFinanceFile;
    }
    public String getSupplementId() {
        return supplementId;
    }

    public void setSupplementId(String supplementId) {
        this.supplementId = supplementId;
    }

    public BigDecimal getRealPayAmount() {
        return realPayAmount;
    }

    public void setRealPayAmount(BigDecimal realPayAmount) {
        this.realPayAmount = realPayAmount;
    }

    public List<Map<String, Object>> getReqListMap() {
        return reqListMap;
    }

    public void setReqListMap(List<Map<String, Object>> reqListMap) {
        this.reqListMap = reqListMap;
    }

    public ReceiptPlan getReceiptPlan() {
        return receiptPlan;
    }

    public void setReceiptPlan(ReceiptPlan receiptPlan) {
        this.receiptPlan = receiptPlan;
    }

    public List<ReceiptPlan> getReceiptPlanList() {
        return receiptPlanList;
    }

    public void setReceiptPlanList(List<ReceiptPlan> receiptPlanList) {
        this.receiptPlanList = receiptPlanList;
    }

    public Date getRemitTime() {
        return remitTime;
    }

    public void setRemitTime(Date remitTime) {
        this.remitTime = remitTime;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public List<OCashReceivablesVo> getoCashReceivablesVoList() {
        return oCashReceivablesVoList;
    }

    public void setoCashReceivablesVoList(List<OCashReceivablesVo> oCashReceivablesVoList) {
        this.oCashReceivablesVoList = oCashReceivablesVoList;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public List<TerminalTransferDetail> getTerminalTransferDetailList() {
        return terminalTransferDetailList;
    }

    public void setTerminalTransferDetailList(List<TerminalTransferDetail> terminalTransferDetailList) {
        this.terminalTransferDetailList = terminalTransferDetailList;
    }

    public TerminalTransfer getTerminalTransfer() {
        return terminalTransfer;
    }

    public void setTerminalTransfer(TerminalTransfer terminalTransfer) {
        this.terminalTransfer = terminalTransfer;
    }

    public String getDebt() {
        return debt;
    }

    public void setDebt(String debt) {
        this.debt = debt;
    }

    public String getOweTicket() {
        return oweTicket;
    }

    public void setOweTicket(String oweTicket) {
        this.oweTicket = oweTicket;
    }

    public BigDecimal getMergeType() {
        return mergeType;
    }

    public void setMergeType(BigDecimal mergeType) {
        this.mergeType = mergeType;
    }

    public AgentQuit getAgentQuit() {
        return agentQuit;
    }

    public void setAgentQuit(AgentQuit agentQuit) {
        this.agentQuit = agentQuit;
    }

    public List<String> getCapitalChangeFinaFiles() {
        return capitalChangeFinaFiles;
    }

    public void setCapitalChangeFinaFiles(List<String> capitalChangeFinaFiles) {
        this.capitalChangeFinaFiles = capitalChangeFinaFiles;
    }

    public String getRemitTimeStr() {
        return remitTimeStr;
    }

    public void setRemitTimeStr(String remitTimeStr) {
        this.remitTimeStr = remitTimeStr;
    }

    public String getRemitPerson() {
        return remitPerson;
    }

    public void setRemitPerson(String remitPerson) {
        this.remitPerson = remitPerson;
    }

    public BigDecimal getRemitAmt() {
        return remitAmt;
    }

    public void setRemitAmt(BigDecimal remitAmt) {
        this.remitAmt = remitAmt;
    }
}
