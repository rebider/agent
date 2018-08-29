package com.ryx.credit.profit.service.impl;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.profit.dao.*;
import com.ryx.credit.profit.enums.DeductionStatus;
import com.ryx.credit.profit.pojo.*;
import com.ryx.credit.profit.service.*;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.agent.TaskApprovalService;
import com.ryx.credit.service.dict.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author yangmx
 * @desc 月分润数据展示服务实现
 */
@Service("profitMonthService")
public class ProfitMonthServiceImpl implements ProfitMonthService {
    Logger LOG = LoggerFactory.getLogger(ProfitMonthServiceImpl.class);

    private static Runtime RUN = Runtime.getRuntime();

    @Autowired
    private ProfitMonthMapper profitMonthMapper;
    @Autowired
    private ProfitDetailMonthMapper profitDetailMonthMapper;
    @Autowired
    private ProfitUnfreezeMapper profitUnfreezeMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private TaskApprovalService taskApprovalService;

    @Autowired
    private ProfitDeductionService profitDeductionServiceImpl;

    @Autowired
    @Qualifier("profitToolsDeductServiceImpl")
    private DeductService profitToolsDeductService;

    @Autowired
    @Qualifier("posProfitComputeServiceImpl")
    private DeductService posProfitComputeServiceImpl;

    @Autowired
    private ProfitComputerService profitComputerService;
    @Autowired
    private ProfitDirectMapper directMapper;
    @Autowired
    private TransProfitDetailMapper transProfitDetailMapper;

    @Autowired
    private TransProfitDetailService transProfitDetailService;

//分润展示
    @Override
   /* public List<ProfitMonth> getProfitMonthList(Page page, ProfitMonth profitMonth) {
        ProfitMonthExample profitMonthExample= this.profitMonthEqualsTo(profitMonth);
        if(page != null){
            profitMonthExample.setPage(page);
        }
        return profitMonthMapper.selectByExample(profitMonthExample);
    }*/
    public List<ProfitDetailMonth> getProfitMonthList(Page page, ProfitDetailMonth profitDetailMonth) {
        ProfitDetailMonthExample profitDetailMonthExample= this.profitDetailMonthEqualsTo(profitDetailMonth);
        if(page != null){
            profitDetailMonthExample.setPage(page);
        }
        return profitDetailMonthMapper.selectByExample(profitDetailMonthExample);
    }


    private ProfitMonthExample profitMonthEqualsTo(ProfitMonth profitMonth) {
        ProfitMonthExample profitMonthExample = new ProfitMonthExample();
        if(profitMonth == null ){
            return profitMonthExample;
        }
        ProfitMonthExample.Criteria criteria = profitMonthExample.createCriteria();
        // 月份按开始到结束查询
        if (StringUtils.isNotBlank(profitMonth.getProfitDateStart()) && StringUtils.isNotBlank(profitMonth.getProfitDateEnd()))
        {
            criteria.andProfitDateBetween(profitMonth.getProfitDateStart(),profitMonth.getProfitDateEnd());
        }else if (StringUtils.isNotBlank(profitMonth.getProfitDateStart())){
            criteria.andProfitDateEqualTo(profitMonth.getProfitDateStart());
        }else if (StringUtils.isNotBlank(profitMonth.getProfitDateEnd())){
            criteria.andProfitDateEqualTo(profitMonth.getProfitDateEnd());
        }
        if(StringUtils.isNotBlank(profitMonth.getAgentName())){
            criteria.andAgentNameEqualTo(profitMonth.getAgentName());
        }
        if(StringUtils.isNotBlank(profitMonth.getAgentId())){
            criteria.andAgentIdEqualTo(profitMonth.getAgentId());
        }
        if(StringUtils.isNotBlank(profitMonth.getStatus())){
            criteria.andStatusEqualTo(profitMonth.getStatus());
        } else {
            criteria.andStatusNotEqualTo("0");
        }
        return profitMonthExample;
    }
//分润展示
    /*@Override
    public int getProfitMonthCount(ProfitMonth profitMonth) {
        ProfitMonthExample profitMonthExample= this.profitMonthEqualsTo(profitMonth);
        return profitMonthMapper.countByExample(profitMonthExample);
    }*/
    public int getProfitMonthCount(ProfitDetailMonth profitDetailMonth) {
        ProfitDetailMonthExample profitDetailMonthExample= this.profitDetailMonthEqualsTo(profitDetailMonth);
        return profitDetailMonthMapper.countByExample(profitDetailMonthExample);
    }
//月分润
    @Override
    public List<ProfitDetailMonth> getProfitDetailMonthList(Page page, ProfitDetailMonth profitDetailMonth) {
        ProfitDetailMonthExample profitDetailMonthExample = profitDetailMonthEqualsTo(profitDetailMonth);
        if(page != null){
            profitDetailMonthExample.setPage(page);
        }
        return profitDetailMonthMapper.selectByExample(profitDetailMonthExample);
    }

    private ProfitDetailMonthExample profitDetailMonthEqualsTo(ProfitDetailMonth profitDetailMonth) {
        ProfitDetailMonthExample profitDetailMonthExample = new ProfitDetailMonthExample();
        if(profitDetailMonth == null){
            return profitDetailMonthExample;
        }
        ProfitDetailMonthExample.Criteria criteria = profitDetailMonthExample.createCriteria();
        if(StringUtils.isNotBlank(profitDetailMonth.getAgentId())){
            criteria.andAgentIdEqualTo(profitDetailMonth.getAgentId());
        }
        if(StringUtils.isNotBlank(profitDetailMonth.getAgentName())){
            criteria.andAgentNameLike("%"+profitDetailMonth.getAgentName()+"%");
        }
        if(StringUtils.isNotBlank(profitDetailMonth.getAgentPid())){
            criteria.andAgentPidEqualTo(profitDetailMonth.getAgentPid());
        }
        if(StringUtils.isNotBlank(profitDetailMonth.getProfitId())){
            criteria.andProfitIdEqualTo(profitDetailMonth.getProfitId());
        }
        if(StringUtils.isNotBlank(profitDetailMonth.getProfitDate())){
            criteria.andProfitDateEqualTo(profitDetailMonth.getProfitDate());
        }
        if(StringUtils.isNotBlank(profitDetailMonth.getStatus())){
            criteria.andStatusEqualTo(profitDetailMonth.getStatus());
        }
        if (StringUtils.isNotBlank(profitDetailMonth.getProfitDateStart()) && StringUtils.isNotBlank(profitDetailMonth.getProfitDateEnd()))
        {
            criteria.andProfitDateBetween(profitDetailMonth.getProfitDateStart(),
                    profitDetailMonth.getProfitDateEnd());
        }else if (StringUtils.isNotBlank(profitDetailMonth.getProfitDateStart())){
            criteria.andProfitDateEqualTo(profitDetailMonth.getProfitDateStart());
        }else if (StringUtils.isNotBlank(profitDetailMonth.getProfitDateEnd())){
            criteria.andProfitDateEqualTo(profitDetailMonth.getProfitDateEnd());
        }
        return profitDetailMonthExample;
    }
//月分润
    @Override
    public int getProfitDetailMonthCount(ProfitDetailMonth profitDetailMonth) {
        ProfitDetailMonthExample profitDetailMonthExample = profitDetailMonthEqualsTo(profitDetailMonth);
        return profitDetailMonthMapper.countByExample(profitDetailMonthExample);
    }

    @Override
    public List<TransProfitDetail> getTransProfitDetail(Page page, TransProfitDetail transProfitDetail) {
        TransProfitDetailExample transProfitDetailExample = new TransProfitDetailExample();
        transProfitDetailExample.setPage(page);
        TransProfitDetailExample.Criteria criteria = transProfitDetailExample.createCriteria();
        criteria.andProfitDateEqualTo(transProfitDetail.getProfitDate());
        criteria.andAgentIdEqualTo(transProfitDetail.getAgentId());
        return transProfitDetailMapper.selectByExample(transProfitDetailExample);
    }

    @Override
    public long getTransProfitDetailCount(TransProfitDetail transProfitDetail) {
        TransProfitDetailExample transProfitDetailExample = new TransProfitDetailExample();
        TransProfitDetailExample.Criteria criteria = transProfitDetailExample.createCriteria();
        criteria.andAgentIdEqualTo(transProfitDetail.getAgentId());
        criteria.andProfitDateEqualTo(transProfitDetail.getProfitDate());
        return transProfitDetailMapper.countByExample(transProfitDetailExample);
    }

    @Override
    public ProfitDetailMonth getProfitDetailMonth(String id) {
        if(StringUtils.isNotBlank(id)){
            return profitDetailMonthMapper.selectByPrimaryKey(id);
        }
        return null;
    }

    @Override
    public void updateProfitMonth(ProfitDetailMonth profitMonth) {
        if(profitMonth != null){
            profitDetailMonthMapper.updateByPrimaryKeySelective(profitMonth);
        }
    }

    @Override
    public void insertProfitMonth(ProfitMonth profitMonth) {
        if(profitMonth != null){
            profitMonthMapper.insert(profitMonth);
        }
    }

    @Override
    public ProfitDetailMonth selectByPrimaryKey(String id) {
        return profitDetailMonthMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ProfitDetailMonth record) {
        return profitDetailMonthMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public ProfitUnfreeze insertProfitUnfreeze(ProfitUnfreeze profitUnfreeze) {
        profitUnfreeze.setId(idService.genId(TabId.p_profit_unfreeze));
        profitUnfreeze.setCreateTime(new Date());
        profitUnfreeze.setUpdateTime(new Date());
        profitUnfreezeMapper.insertSelective(profitUnfreeze);
        return profitUnfreeze;
    }

    @Override
    public void editProfitUnfreeze(ProfitUnfreeze profitUnfreeze) {
        profitUnfreeze.setUpdateTime(new Date());
        profitUnfreezeMapper.updateByPrimaryKeySelective(profitUnfreeze);
    }


    /**
     * 事务控制
     * 月分润解冻审批流
     * @param profitUnfreeze
     */
    @Override
    public void apptlyProfitUnfreeze(ProfitUnfreeze profitUnfreeze, String userId, String workId) throws ProcessException{
        //启动审批流
        String proceId = activityService.createDeloyFlow(null, workId, null, null, null);
        if (proceId == null) {
            LOG.error("月分润解冻审批流启动失败，代理商ID：{}", profitUnfreeze.getAgentId());
            profitUnfreeze.setUpdateTime(new Date());
            profitUnfreeze.setFreezeStatus("4");
            profitUnfreezeMapper.updateByPrimaryKeySelective(profitUnfreeze);
            throw new ProcessException("月分润解冻审批流启动失败!");
        }
        BusActRel record = new BusActRel();
        record.setBusId(profitUnfreeze.getId());
        record.setActivId(proceId);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(userId);
        record.setBusType(BusActRelBusType.THAW.name());
        try {
            taskApprovalService.addABusActRel(record);
            LOG.info("月分润解冻申请审批流启动成功");
        } catch (Exception e) {
            LOG.error("月分润解冻申请审批流启动失败{}");
            profitUnfreeze.setUpdateTime(new Date());
            profitUnfreeze.setFreezeStatus("4");
            profitUnfreezeMapper.updateByPrimaryKeySelective(profitUnfreeze);
            throw new ProcessException("月分润解冻申请审批流启动失败!");
        }

        ProfitDetailMonth profitDetailMonth = new ProfitDetailMonth();
        profitDetailMonth.setId(profitUnfreeze.getProfitId());
        profitDetailMonth.setStatus(ProfitStatus.STATUS_2.status.toString());
        profitDetailMonth.setRemark(profitUnfreeze.getRemark());
        profitDetailMonthMapper.updateByPrimaryKeySelective(profitDetailMonth);
    }

    @Override
    public ProfitUnfreeze getProfitUnfreezeById(String id) {
        if (StringUtils.isNotBlank(id)) {
            return profitUnfreezeMapper.selectByPrimaryKey(id);
        }
        return null;
    }

    @Override
    public ProfitUnfreeze getProfitUnfreezeByProfitId(String profitId) {
        if (StringUtils.isNotBlank(profitId)) {
            ProfitUnfreeze unfreeze = new ProfitUnfreeze();
            unfreeze.setProfitId(profitId);
            ProfitUnfreezeExample example = new ProfitUnfreezeExample();
            ProfitUnfreezeExample.Criteria criteria = example.createCriteria();
            criteria.andProfitIdEqualTo(profitId);
            List<ProfitUnfreeze> profitUnfreezes = profitUnfreezeMapper.selectByExample(example);

            if (profitUnfreezes != null && profitUnfreezes.size() > 0) {
                return profitUnfreezes.get(0);
            }
        }
        return null;
    }

    @Override
    public void completeTaskEnterActivity(String insid, String status) {
        BusActRel busActRel = new BusActRel();
        busActRel.setActivId(insid);
        try {

            BusActRel rel =  taskApprovalService.queryBusActRel(busActRel);
            if (rel != null) {
                ProfitUnfreeze profitUnfreeze = getProfitUnfreezeById(rel.getBusId());
                if (profitUnfreeze != null) {
                    String profitStatus = "4";
                    String thawStatus = "1";
                    rel.setStatus(Status.STATUS_2.status);
                    LOG.info("1.更新分润状态为未分润");
                    ProfitDetailMonth profitDetailMonth = new ProfitDetailMonth();
                    profitDetailMonth.setId(profitUnfreeze.getProfitId());
                    profitDetailMonth.setStatus(profitStatus);
                    profitDetailMonthMapper.updateByPrimaryKeySelective(profitDetailMonth);
                    LOG.info("2.更新解冻审批对象解冻成功");
                    profitUnfreeze.setUpdateTime(new Date());
                    profitUnfreeze.setFreezeStatus(thawStatus);
                    profitUnfreezeMapper.updateByPrimaryKeySelective(profitUnfreeze);
                    //解冻下级所有代理商
                    directMapper.updateFristAgentStatus(profitUnfreeze.getAgentPid());
                    LOG.info("3更新审批流与业务对象");
                    taskApprovalService.updateABusActRel(rel);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ProfitDetailMonth getAgentProfit(String agentId, String profitDate) {
        if(StringUtils.isNotBlank(agentId) && StringUtils.isNotBlank(profitDate)){
            ProfitDetailMonthExample profitDetailMonthExample = new ProfitDetailMonthExample();
            ProfitDetailMonthExample.Criteria criteria = profitDetailMonthExample.createCriteria();
            criteria.andAgentPidEqualTo(agentId);
            criteria.andProfitDateEqualTo(profitDate);
            List<ProfitDetailMonth> list = profitDetailMonthMapper.selectByExample(profitDetailMonthExample);
            if(list != null && !list.isEmpty()){
                ProfitDetailMonth profitMonth = list.get(0);
                return profitMonth;
            }
        }
        return null;
    }

    @Override
    public void computeProfitAmt() {
        // 获取所有代理商月度分润明细
        ProfitDetailMonth profitDetailMonth = new ProfitDetailMonth();
        profitDetailMonth.setProfitDate(LocalDate.now().plusMonths(-1).format(DateTimeFormatter.BASIC_ISO_DATE).substring(0,6));
//        profitDetailMonth.setAgentId("AG20180817000000000006101");// 验证使用
        List<ProfitDetailMonth> profitDetailMonthList = getProfitDetailMonthList(null, profitDetailMonth);
        Map<String, BigDecimal> parentPosReward = new HashMap<>(5);
        if (profitDetailMonthList != null && profitDetailMonthList.size() > 0) {
            profitDetailMonthList.stream().forEach(profitDetailMonthTemp -> {
                BigDecimal sumAmt = profitDetailMonthTemp.getProfitSumAmt();
                // 退单补款+
                sumAmt = sumAmt.add(getTdSupplyAmt(profitDetailMonthTemp));
                // 其他补款+
                profitDetailMonthTemp.setOtherSupplyAmt(profitComputerService.total_supply(profitDetailMonthTemp.getAgentPid(), null));
                sumAmt = sumAmt.add(profitDetailMonthTemp.getOtherSupplyAmt());
                // POS考核奖励
                 getPosReward(profitDetailMonthTemp, parentPosReward);
                 sumAmt = sumAmt.add(profitDetailMonthTemp.getPosRewardAmt()).subtract(profitDetailMonthTemp.getPosRewardDeductionAmt());
                //退单扣款-
                if (!profitDetailMonthTemp.getAgentId().startsWith("6000")) {
                    sumAmt = doTdDeductionAmt(profitDetailMonthTemp, sumAmt);
                }
                // 机具扣款-
                sumAmt = doToolDeduction(profitDetailMonthTemp, sumAmt);
                Map<String, Object> param = new HashMap<>(5);
                param.put("profitAmt", sumAmt);
                param.put("agentPid",  profitDetailMonthTemp.getAgentPid());
//               param.put("parentAgentPid",  profitDetailMonthTemp.getParentAgentPid());
                param.put("remark", "POS考核扣款（新国都、瑞易送）");
                //POS考核扣款（新国都、瑞易送）-
                profitDetailMonthTemp.setPosKhDeductionAmt(profitDeductionServiceImpl.otherDeductionByType(param));
                sumAmt = sumAmt.subtract(profitDetailMonthTemp.getPosKhDeductionAmt());

                param.put("profitAmt", sumAmt);
                param.put("remark", "手刷考核扣款（小蓝牙、MPOS）");
                //手刷考核扣款（小蓝牙、MPOS）-
                profitDetailMonthTemp.setMposKhDeductionAmt(profitDeductionServiceImpl.otherDeductionByType(param));
                sumAmt = sumAmt.subtract(profitDetailMonthTemp.getMposKhDeductionAmt());
                //保理扣款-
                profitDetailMonthTemp.setBuDeductionAmt(profitComputerService.total_factor(profitDetailMonthTemp.getAgentPid(), null));
                sumAmt = sumAmt.subtract(profitDetailMonthTemp.getBuDeductionAmt());

                param.put("profitAmt", sumAmt);
                param.put("remark", "1");
                //其他扣款-
                profitDetailMonthTemp.setOtherDeductionAmt(profitDeductionServiceImpl.otherDeductionByType(param));
                sumAmt = sumAmt.subtract(profitDetailMonthTemp.getOtherDeductionAmt());
                // 实发分润
                profitDetailMonthTemp.setBasicsProfitAmt(sumAmt);
                profitDetailMonthMapper.updateByPrimaryKeySelective(profitDetailMonthTemp);
            });
        }else{
            LOG.error("没有分润数据。");
        }
    }

    @Override
    public void payMoney() {
        String profitDate = LocalDate.now().plusMonths(-1).format(DateTimeFormatter.BASIC_ISO_DATE).substring(0,6);
        profitDetailMonthMapper.payMoney(profitDate);
    }

    /***
    /***
     * @Description: 获取pos奖励
     * @Param:
     * @return:
     * @Author: zhaodw
     * @Date: 2018/8/14
     */
    private void getPosReward(ProfitDetailMonth profitDetailMonthTemp, Map<String, BigDecimal> parentPosReward) {
        TransProfitDetail detail = new TransProfitDetail();
        detail.setAgentId(profitDetailMonthTemp.getAgentId());
        detail.setBusCode("100003");
        List<TransProfitDetail> transProfitDetails = transProfitDetailService.getTransProfitDetailList(detail);
        if (transProfitDetails.size() > 0) {
            detail = transProfitDetails.get(0);
            Map<String, Object> map = new HashMap<>(10);
            map.put("agentType", detail.getAgentType());
            map.put("agentId", detail.getBusNum());
            map.put("agentPid", detail.getAgentId());
            map.put("posTranAmt", detail.getPosCreditAmt());
            map.put("posJlTranAmt", detail.getPosRewardAmt());
            try {
                map = posProfitComputeServiceImpl.execut(map);
                BigDecimal oldAmt = profitDetailMonthTemp.getPosRewardAmt()==null?BigDecimal.ZERO: profitDetailMonthTemp.getPosRewardAmt();
                profitDetailMonthTemp.setPosRewardAmt(oldAmt.add((BigDecimal) map.get("posRewardAmt")));
                profitDetailMonthTemp.setPosRewardDeductionAmt( (BigDecimal) map.get("posAssDeductAmt"));
                if (!"0".equals(map.get("parentDeductPosRewardAmt").toString())) {
                    parentPosReward.put(map.get("parentAgentPid").toString(), (BigDecimal) map.get("parentDeductPosRewardAmt"));
                }
                // 判断是否存在奖励
                if (parentPosReward.containsKey(profitDetailMonthTemp.getAgentPid())) {
                    profitDetailMonthTemp.setPosRewardAmt(profitDetailMonthTemp.getPosRewardAmt().add(parentPosReward.get(profitDetailMonthTemp.getAgentPid())));
                }
            } catch (Exception e) {
                e.printStackTrace();
                LOG.error("获取pos奖励失败");
                throw new RuntimeException("获取pos奖励失败");
            }
        }else{
            profitDetailMonthTemp.setPosRewardAmt(BigDecimal.ZERO);
        }
    }

    /***
     * @Description: 执行机具扣款
     * @Param:  profitDetailMonthTemp 月分润信息
     * @Param:  agentProfitAmt 分润金额
     * @return: 扣款金额
     * @Author: zhaodw
     * @Date: 2018/8/13
     */
    private BigDecimal doToolDeduction(ProfitDetailMonth profitDetailMonthTemp, BigDecimal agentProfitAmt) {
        Map<String, Object> map = new HashMap<>(10);
        map.put("agentPid", profitDetailMonthTemp.getAgentId()); //业务平台编号
        map.put("paltformNo", "5000");      //瑞和宝
        map.put("deductDate", LocalDate.now().plusMonths(-1).toString().substring(0,7));   //扣款月份
        map.put("agentProfitAmt", agentProfitAmt);     //代理商分润
        try {
            profitToolsDeductService.execut(map);
            profitDetailMonthTemp.setRhbDgMustDeductionAmt((BigDecimal) map.get("mustDeductionAmtSum"));
            profitDetailMonthTemp.setRhbDgRealDeductionAmt((BigDecimal) map.get("actualDeductionAmtSum"));
            agentProfitAmt = agentProfitAmt.subtract(profitDetailMonthTemp.getRhbDgRealDeductionAmt());

            map.put("paltformNo", "100003");      //POS
            map.put("agentProfitAmt", agentProfitAmt);     //代理商分润
            profitToolsDeductService.execut(map);
            profitDetailMonthTemp.setPosDgMustDeductionAmt((BigDecimal) map.get("mustDeductionAmtSum"));
            profitDetailMonthTemp.setPosDgRealDeductionAmt((BigDecimal) map.get("actualDeductionAmtSum"));
            agentProfitAmt = agentProfitAmt.subtract(profitDetailMonthTemp.getPosDgRealDeductionAmt());

            map.put("paltformNo", "100002");      //ZPOS
            map.put("agentProfitAmt", agentProfitAmt);     //代理商分润
            profitToolsDeductService.execut(map);
            profitDetailMonthTemp.setZposDgMustDeductionAmt((BigDecimal) map.get("mustDeductionAmtSum"));
            profitDetailMonthTemp.setZposTdRealDeductionAmt((BigDecimal) map.get("actualDeductionAmtSum"));
            agentProfitAmt = agentProfitAmt.subtract(profitDetailMonthTemp.getZposTdRealDeductionAmt());
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("机具扣款失败");
            throw new RuntimeException("机具扣款失败");
        }
        return agentProfitAmt;
    }

    /*** 
     * @Description: 获取退单补款
     * @Param:  分润明细
     * @return:  退单补款
     * @Author: zhaodw
     * @Date: 2018/8/12
     */
    private BigDecimal getTdSupplyAmt(ProfitDetailMonth profitDetailMonthTemp) {
        // pos退单补款
        BigDecimal posSupply = profitDeductionServiceImpl.getSupplyAmt(profitDetailMonthTemp.getAgentId(),"02", profitDetailMonthTemp.getParentAgentId());
        profitDetailMonthTemp.setPosTdSupplyAmt(posSupply);
        // mpos退单补款
        BigDecimal mposSupply = profitDeductionServiceImpl.getSupplyAmt(profitDetailMonthTemp.getAgentId(),"01", profitDetailMonthTemp.getParentAgentId());
        profitDetailMonthTemp.setMposTdSupplyAmt(mposSupply);
        return posSupply.add(mposSupply);
    }

    /***
     * @Description: 获取退单补款
     * @Param:  分润明细
     * @return:  退单补款
     * @Author: zhaodw
     * @Date: 2018/8/12
     */
    private BigDecimal doTdDeductionAmt(ProfitDetailMonth profitDetailMonthTemp, BigDecimal sumAmt) {
        ProfitDeduction profitDeduction = new ProfitDeduction();
        profitDeduction.setAgentPid(profitDetailMonthTemp.getAgentId());
        profitDeduction.setDeductionDate(LocalDate.now().plusMonths(-1).toString().substring(0,7));
        profitDeduction.setSourceId("02");
        // pos退单应扣款
        BigDecimal posMustDeductionAmt = profitDeductionServiceImpl.getSettleErrDeductionAmt(profitDeduction);
        posMustDeductionAmt = posMustDeductionAmt==null? BigDecimal.ZERO: posMustDeductionAmt;
        profitDetailMonthTemp.setPosTdMustDeductionAmt(posMustDeductionAmt);
        Map<String, Object> param = new HashMap<>(5);
        param.put("profitAmt", sumAmt);
        param.put("sourceId", "02");
        param.put("agentPid",  profitDetailMonthTemp.getAgentId());
//        param.put("parentAgentPid",  profitDetailMonthTemp.getParentAgentPid());
        BigDecimal realDeductionAmt = BigDecimal.ZERO;
        if (posMustDeductionAmt.doubleValue() > 0) {
            realDeductionAmt = profitDeductionServiceImpl.settleErrDeduction(param);
            profitDetailMonthTemp.setPosTdRealDeductionAmt(realDeductionAmt);
            sumAmt=sumAmt.subtract(realDeductionAmt);
        }else{
            profitDetailMonthTemp.setPosTdRealDeductionAmt(BigDecimal.ZERO);
        }
        // mpos退单扣款
        profitDeduction.setSourceId("01");
        BigDecimal mposMustDeductionAmt = profitDeductionServiceImpl.getSettleErrDeductionAmt(profitDeduction);
        mposMustDeductionAmt = mposMustDeductionAmt==null? BigDecimal.ZERO: mposMustDeductionAmt;
        profitDetailMonthTemp.setMposTdMustDeductionAmt(mposMustDeductionAmt);

        if (mposMustDeductionAmt.doubleValue() > 0) {
            param.put("profitAmt", sumAmt);
            param.put("sourceId", "01");
            realDeductionAmt = profitDeductionServiceImpl.settleErrDeduction(param);
            profitDetailMonthTemp.setMposTdRealDeductionAmt(realDeductionAmt);
            sumAmt=sumAmt.subtract(realDeductionAmt);
        }else{
            profitDetailMonthTemp.setMposTdRealDeductionAmt(BigDecimal.ZERO);
        }
        return sumAmt;
    }


}
