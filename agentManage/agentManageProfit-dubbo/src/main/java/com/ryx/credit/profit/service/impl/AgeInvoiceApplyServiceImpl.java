package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.enumc.BusActRelBusType;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.DateUtils;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.profit.dao.InvoiceApplyMapper;
import com.ryx.credit.profit.dao.InvoiceDetailMapper;
import com.ryx.credit.profit.pojo.InvoiceApply;
import com.ryx.credit.profit.pojo.InvoiceApplyExample;
import com.ryx.credit.profit.pojo.InvoiceDetail;
import com.ryx.credit.profit.pojo.InvoiceDetailExample;
import com.ryx.credit.profit.service.IAgeInvoiceApplyService;
import com.ryx.credit.profit.service.IOwnInvoiceService;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.agent.AgentEnterService;
import com.ryx.credit.service.agent.TaskApprovalService;
import com.ryx.credit.service.dict.IdService;
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

/**
 * 代理商发票线上维护实现类
 * @Author chenqiutian
 * @Date 2019/2/18
 */
@Service("ageInvoiceApplyService")
public class AgeInvoiceApplyServiceImpl implements IAgeInvoiceApplyService {

    Logger logger = LoggerFactory.getLogger(AgeInvoiceApplyServiceImpl.class);

    @Autowired
    private InvoiceApplyMapper invoiceApplyMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private AgentEnterService agentEnterService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private TaskApprovalService taskApprovalService;
    @Autowired
    InvoiceDetailMapper invoiceDetailMapper;
    @Autowired
    IOwnInvoiceService ownInvoiceService;

    /**
     * 代理商获取发票申请明细
     */
    @Override
    public PageInfo agentGetInvoiceApplyList(InvoiceApply invoiceApply, Page page) {
        InvoiceApplyExample example = new InvoiceApplyExample();
        example.setPage(page);
        InvoiceApplyExample.Criteria criteria = example.createCriteria();

        if(StringUtils.isNotBlank(invoiceApply.getInvoiceCompany())){
            criteria.andInvoiceCompanyEqualTo(invoiceApply.getInvoiceCompany());
        }
        if(StringUtils.isNotBlank(invoiceApply.getInvoiceNumber())){
            criteria.andInvoiceNumberEqualTo(invoiceApply.getInvoiceNumber());
        }
        if(StringUtils.isNotBlank(invoiceApply.getExpressNumber())){
            criteria.andExpressNumberEqualTo(invoiceApply.getExpressNumber());
        }
        if(StringUtils.isNotBlank(invoiceApply.getExpressDate())){
            criteria.andExpressDateEqualTo(invoiceApply.getExpressDate());
        }
        if(StringUtils.isNotBlank(invoiceApply.getAgentId())){
            criteria.andAgentIdEqualTo(invoiceApply.getAgentId());
        }
        if(StringUtils.isNotBlank(invoiceApply.getAgentName())){
            criteria.andAgentNameEqualTo(invoiceApply.getAgentName());
        }
        if(StringUtils.isNotBlank(invoiceApply.getShResult())){
            criteria.andShResultNotEqualTo(invoiceApply.getShResult());
        }
        example.setOrderByClause("CREATE_DATE  desc");
        PageInfo pageInfo = new PageInfo();
        List<InvoiceApply> list = invoiceApplyMapper.selectByExample(example);
        Long count = invoiceApplyMapper.countByExample(example);
        pageInfo.setTotal(Integer.parseInt(count.toString()));
        pageInfo.setRows(list);
        return pageInfo;
    }

    /**
     * 启动审批流
     * @param invoiceApply
     * @param userId
     * @param workId
     * @param cUser
     */
    @Override
    public void applyInvoice(InvoiceApply invoiceApply, String userId, String workId, String cUser){
        if(StringUtils.isNotBlank(invoiceApply.getId())){
           //表示修改信息
            invoiceApply = invoiceApplyMapper.selectByPrimaryKey(invoiceApply.getId());
        }else{
            //表示申请信息
            invoiceApply.setId(idService.genId(TabId.P_INVOICE_APPLY));
            invoiceApply.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:ss:mm").format(new Date()));
            invoiceApplyMapper.insert(invoiceApply);
        }

        logger.info("序列ID......"+idService.genId(TabId.P_INVOICE_APPLY));

        Map startPar = agentEnterService.startPar(cUser);
        if (null == startPar) {
            logger.info("========用户{}{}启动部门参数为空",  cUser);
            throw new ProcessException("启动部门参数为空!");
        }

        //启动审批流
        String proceId = activityService.createDeloyFlow(null, workId, null, null, null);
        if (proceId == null) {
            //启动失败，要删除对应数据
            InvoiceApplyExample example = new InvoiceApplyExample();
            example.createCriteria().andIdEqualTo(invoiceApply.getId());
            invoiceApplyMapper.deleteByExample(example);
            logger.error("发票线上审批流启动失败，代理商ID：{}", invoiceApply.getId());
            throw new ProcessException("发票线上审批流启动失败!");
        }

        BusActRel record = new BusActRel();
        record.setBusId(invoiceApply.getId());
        record.setActivId(proceId);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(userId);
        record.setAgentId(invoiceApply.getAgentId());
        record.setAgentName(invoiceApply.getAgentName());
        record.setBusType(BusActRelBusType.INVOICEAPPLY.name());
        try{
            taskApprovalService.addABusActRel(record);
            logger.info("发票线上审批流启动成功");
        }catch (Exception e){
            e.printStackTrace();
            logger.info("发票线上审批流启动失败");
            throw new ProcessException("发票线上审批流启动失败!:{}",e.getMessage());
        }

        invoiceApply.setShResult("0"); // 表示审核中
        invoiceApplyMapper.updateByPrimaryKeySelective(invoiceApply);
    }

    /**
     * 处理任务
     */
    @Override
    public AgentResult approvalTask(AgentVo agentVo, String userId,InvoiceApply invoiceApply) throws ProcessException {
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
        if("reject".equals(agentVo.getApprovalResult())
                && StringUtils.isBlank(agentVo.getOrderAprDept())){
            reqMap.put("dept", "finish");
        }
        if("back".equals(agentVo.getApprovalResult())){
            invoiceApply.setShResult("3");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
            invoiceApply.setShDate(sdf.format(new Date()));
            invoiceApplyMapper.updateByPrimaryKeySelective(invoiceApply);
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

    /**
     * 审批流回调
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public void completeTaskEnterActivity(String insid, String status) {
        BusActRel busActRel = new BusActRel();
        busActRel.setActivId(insid);
        try {
            BusActRel rel =  taskApprovalService.queryBusActRel(busActRel);
            if (rel != null) {
                InvoiceApply invoiceApply = invoiceApplyMapper.selectByPrimaryKey(rel.getBusId());
                invoiceApply.setShResult(status);
                invoiceApply.setShDate(new SimpleDateFormat("yyyy-MM-dd HH:ss:mm").format(new Date()));
                invoiceApplyMapper.updateByPrimaryKeySelective(invoiceApply);

                if("1".equals(status)){
                    // 表示通过，计入欠票表中
                    ownInvoiceService.invoiceApplyComputer(invoiceApply);
                }
                logger.info("2更新审批流与业务对象");
                rel.setActivStatus(AgStatus.Approved.name());
                taskApprovalService.updateABusActRel(rel);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("发票线上审批审批流回调异常，activId：{}"+insid);
        }
    }


    /**
     * 修改信息
     */
    @Override
    public void editCheckRegect(InvoiceApply invoiceApply) throws Exception {
        logger.info("审批拒绝，修改信息");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        invoiceApply.setCreateDate(sdf.format(new Date()));
        invoiceApplyMapper.updateByPrimaryKeySelective(invoiceApply);
    }

    @Override
    public InvoiceApply getInvoiceApplyById(String id) {
        return invoiceApplyMapper.selectByPrimaryKey(id);
    }

    @Override
    public void volumeImportData(Map<String, String> map, List<List<Object>> list) {
        if(list.size()<=0){
            throw new ProcessException("导入数据为空");
        }
        try{
            for (List<Object> lists:list) {
                insertInvoiceApply(map,lists);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new ProcessException("导入数据格式不正确");
        }
    }

    private void insertInvoiceApply(Map<String, String> map, List<Object> list){
        InvoiceApply invoiceApply = new InvoiceApply();
        invoiceApply.setInvoiceCompany(list.get(0).toString());
        invoiceApply.setInvoiceDate(list.get(1).toString());
        invoiceApply.setInvoiceNumber(list.get(2).toString().substring(0,list.get(2).toString().indexOf(".")));
        invoiceApply.setInvoiceCode(list.get(3).toString().substring(0,list.get(3).toString().indexOf(".")));
        invoiceApply.setInvoiceItem(list.get(4).toString());
        invoiceApply.setUnitPrice(new BigDecimal(list.get(5).toString().trim()));
        String str = list.get(6).toString().trim();
        invoiceApply.setNumberSl(Long.valueOf(str.substring(0,str.indexOf("."))));
        invoiceApply.setSumAmt(new BigDecimal(list.get(7).toString().trim()));
        invoiceApply.setTax(new BigDecimal(list.get(8).toString().trim()));
        invoiceApply.setExpressCompany(list.get(9).toString());
        invoiceApply.setExpressNumber(list.get(10).toString().trim());
        invoiceApply.setShResult("1");
        invoiceApply.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:SS:mm").format(new Date()));
        invoiceApply.setCreateName(map.get("userId"));
        invoiceApply.setAgentName(map.get("agentName"));
        invoiceApply.setAgentId(map.get("agentId"));
        //启动审批流
        applyInvoice(invoiceApply, map.get("userId"), map.get("workId"), map.get("userId"));
    }


    @Override
    public InvoiceApply getInvoiceApplyByInvoiceNumber(String invoiceNumber) {
        InvoiceApplyExample example = new InvoiceApplyExample();
        InvoiceApplyExample.Criteria criteria = example.createCriteria();
        criteria.andInvoiceNumberEqualTo(invoiceNumber);
        List<InvoiceApply> list = invoiceApplyMapper.selectByExample(example);
        if(list.size() > 0){
            return  list.get(0);
        }
        return null;
    }

    @Override
    public void insertInvoiceApply(InvoiceApply invoiceApply) {
        invoiceApplyMapper.updateByPrimaryKeySelective(invoiceApply);
    }
}
