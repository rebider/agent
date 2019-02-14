package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.profit.dao.*;
import com.ryx.credit.profit.enums.DeductionType;
import com.ryx.credit.profit.pojo.*;
import com.ryx.credit.profit.service.*;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.agent.TaskApprovalService;
import com.ryx.credit.service.dict.IdService;
import org.activiti.engine.runtime.Execution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * @author yangmx
 * @desc 月分润数据展示服务实现
 */
@Service("profitMonthService")
public class ProfitMonthServiceImpl implements ProfitMonthService {
    Logger LOG = LoggerFactory.getLogger(ProfitMonthServiceImpl.class);

    private static final ForkJoinPool FORK_JOIN_POOL = new ForkJoinPool();
    private static Runtime RUN = Runtime.getRuntime();
    private static Map<String, List<Map<String, Object>>> profitAmtMap = new ConcurrentHashMap<>();
    private static List<ProfitDetailMonth> notDeductionList = new ArrayList<>(10);
    private static List<ProfitDetailMonth> toolNotDeductionList = new ArrayList<>(10);
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

    @Autowired
    private ProfitBalanceSerialService profitBalanceSerialServiceImpl;
    @Resource
    ITaxDeductionService taxDeductionService;
    @Resource
    ProfitSupplyTaxService profitSupplyTaxService;

    @Autowired
    private PAgentMergeMapper agentMergeMapper;
    @Autowired
    private ProfitSupplyMapper profitSupplyMapper;
    @Resource
    IOwnInvoiceService ownInvoiceService;
    @Resource
    ProfitFactorService profitFactorService;

    @Autowired
    private IProfitDirectService profitDirectService;

    @Autowired
    private BusiPlatService busiPlatService;

    public final static Map<String, Map<String, Object>> temp = new HashMap<>();


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
        ProfitDetailMonthExample profitDetailMonthExample = this.profitDetailMonthEqualsTo(null, profitDetailMonth);
        if (page != null) {
            profitDetailMonthExample.setPage(page);
        }
        return profitDetailMonthMapper.selectByExample(profitDetailMonthExample);
    }


    private ProfitMonthExample profitMonthEqualsTo(ProfitMonth profitMonth) {
        ProfitMonthExample profitMonthExample = new ProfitMonthExample();
        if (profitMonth == null) {
            return profitMonthExample;
        }
        ProfitMonthExample.Criteria criteria = profitMonthExample.createCriteria();
        // 月份按开始到结束查询
        if (StringUtils.isNotBlank(profitMonth.getProfitDateStart()) && StringUtils.isNotBlank(profitMonth.getProfitDateEnd())) {
            criteria.andProfitDateBetween(profitMonth.getProfitDateStart(), profitMonth.getProfitDateEnd());
        } else if (StringUtils.isNotBlank(profitMonth.getProfitDateStart())) {
            criteria.andProfitDateEqualTo(profitMonth.getProfitDateStart());
        } else if (StringUtils.isNotBlank(profitMonth.getProfitDateEnd())) {
            criteria.andProfitDateEqualTo(profitMonth.getProfitDateEnd());
        }
        if (StringUtils.isNotBlank(profitMonth.getAgentName())) {
            criteria.andAgentNameEqualTo(profitMonth.getAgentName());
        }
        if (StringUtils.isNotBlank(profitMonth.getAgentId())) {
            criteria.andAgentIdEqualTo(profitMonth.getAgentId());
        }
        if (StringUtils.isNotBlank(profitMonth.getStatus())) {
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
    @Override
    public int getProfitMonthCount(ProfitDetailMonth profitDetailMonth) {
        ProfitDetailMonthExample profitDetailMonthExample = this.profitDetailMonthEqualsTo(null, profitDetailMonth);
        return profitDetailMonthMapper.countByExample(profitDetailMonthExample);
    }

    //月分润
    @Override
    public List<ProfitDetailMonth> getProfitDetailMonthList(Map<String, Object> department, Page page, ProfitDetailMonth profitDetailMonth) {
        ProfitDetailMonthExample profitDetailMonthExample = profitDetailMonthEqualsTo(department, profitDetailMonth);
        profitDetailMonthExample.setOrderByClause(" AGENT_ID ");
        if (page != null) {
            profitDetailMonthExample.setPage(page);
        }
        return profitDetailMonthMapper.selectByExample(profitDetailMonthExample);
    }

    private ProfitDetailMonthExample profitDetailMonthEqualsTo(Map<String, Object> department, ProfitDetailMonth profitDetailMonth) {
        ProfitDetailMonthExample profitDetailMonthExample = new ProfitDetailMonthExample();
        if (profitDetailMonth == null) {
            return profitDetailMonthExample;
        }
        ProfitDetailMonthExample.Criteria criteria = profitDetailMonthExample.createCriteria();
        if (department != null) {
            profitDetailMonthExample.setInnerJoinDepartment(department.get("ORGANIZATIONCODE").toString(), department.get("ORGID").toString());
        }
        if (StringUtils.isNotBlank(profitDetailMonth.getAgentId())) {
            criteria.andAgentIdEqualTo(profitDetailMonth.getAgentId());
        }
        if (StringUtils.isNotBlank(profitDetailMonth.getAgentName())) {
            criteria.andAgentNameLike("%" + profitDetailMonth.getAgentName() + "%");
        }
        if (StringUtils.isNotBlank(profitDetailMonth.getAgentPid())) {
            criteria.andAgentPidEqualTo(profitDetailMonth.getAgentPid());
        }
        if (StringUtils.isNotBlank(profitDetailMonth.getProfitId())) {
            criteria.andProfitIdEqualTo(profitDetailMonth.getProfitId());
        }
        if (StringUtils.isNotBlank(profitDetailMonth.getProfitDate())) {
            criteria.andProfitDateEqualTo(profitDetailMonth.getProfitDate());
        }
        if (StringUtils.isNotBlank(profitDetailMonth.getStatus())) {
            if (profitDetailMonth.getStatus().contains(",")) {
                criteria.andPayStatusIn(Arrays.asList(profitDetailMonth.getStatus().split(",")));
            } else {
                criteria.andStatusEqualTo(profitDetailMonth.getStatus());
            }
        }
        if (StringUtils.isNotBlank(profitDetailMonth.getProfitDateStart()) && StringUtils.isNotBlank(profitDetailMonth.getProfitDateEnd())) {
            criteria.andProfitDateBetween(profitDetailMonth.getProfitDateStart(),
                    profitDetailMonth.getProfitDateEnd());
        } else if (StringUtils.isNotBlank(profitDetailMonth.getProfitDateStart())) {
            criteria.andProfitDateEqualTo(profitDetailMonth.getProfitDateStart());
        } else if (StringUtils.isNotBlank(profitDetailMonth.getProfitDateEnd())) {
            criteria.andProfitDateEqualTo(profitDetailMonth.getProfitDateEnd());
        }
        return profitDetailMonthExample;
    }

    //月分润
    @Override
    public int getProfitDetailMonthCount(Map<String, Object> department, ProfitDetailMonth profitDetailMonth) {
        ProfitDetailMonthExample profitDetailMonthExample = profitDetailMonthEqualsTo(department, profitDetailMonth);
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
        if (StringUtils.isNotBlank(id)) {
            return profitDetailMonthMapper.selectByPrimaryKey(id);
        }
        return null;
    }

    @Override
    public void updateProfitMonth(ProfitDetailMonth profitMonth) {
        if (profitMonth != null) {
            profitDetailMonthMapper.updateByPrimaryKeySelective(profitMonth);
        }
    }

    @Override
    public void insertProfitMonth(ProfitMonth profitMonth) {
        if (profitMonth != null) {
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
     *
     * @param profitUnfreeze
     */
    @Override
    public void apptlyProfitUnfreeze(ProfitUnfreeze profitUnfreeze, String userId, String workId) throws ProcessException {
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
        record.setStatus(Status.STATUS_1.status);
        record.setBusType(BusActRelBusType.THAW.name());
        record.setAgentId(profitUnfreeze.getAgentId());
        record.setAgentName(profitUnfreeze.getAgentName());
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

            BusActRel rel = taskApprovalService.queryBusActRel(busActRel);
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
                    directMapper.updateFristAgentStatus(profitUnfreeze.getAgentId());
                    LOG.info("3更新审批流与业务对象");
                    rel.setActivStatus(AgStatus.Approved.name());
                    taskApprovalService.updateABusActRel(rel);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ProfitDetailMonth> getAgentProfit(String agentId, String profitDate, String parentAgentId) {
        if (StringUtils.isNotBlank(agentId) && StringUtils.isNotBlank(profitDate)) {
            ProfitDetailMonthExample profitDetailMonthExample = new ProfitDetailMonthExample();
            ProfitDetailMonthExample.Criteria criteria = profitDetailMonthExample.createCriteria();
            criteria.andAgentIdEqualTo(agentId);
            criteria.andProfitDateEqualTo(profitDate);
            if (StringUtils.isNotBlank(parentAgentId)) {
                criteria.andParentAgentIdEqualTo(parentAgentId);
            }
            List<ProfitDetailMonth> list = profitDetailMonthMapper.selectByExample(profitDetailMonthExample);
            return list;
        }
        return null;
    }

    @Override
    public void computeProfitAmt() {
        String profitDate = LocalDate.now().plusMonths(-1).format(DateTimeFormatter.BASIC_ISO_DATE).substring(0, 6);
        profitDetailMonthMapper.clearComputData(profitDate);
        profitToolsDeductService.clearDetail();
        comput("1");
    }

    @Override
    public Map<String, Object> getDbProfitAmt(String agentId, String parentAgentId, String computType) {
        ProfitDetailMonth profitDetailMonth = new ProfitDetailMonth();
        profitDetailMonth.setAgentId(agentId);
        profitDetailMonth.setParentAgentId(parentAgentId);
        profitDetailMonth.setProfitDate(LocalDate.now().plusMonths(-1).format(DateTimeFormatter.BASIC_ISO_DATE).substring(0, 6));
        List<ProfitDetailMonth> profitDetailMonthList = getProfitDetailMonthList(null, null, profitDetailMonth);
        if (profitDetailMonthList != null && profitDetailMonthList.size() > 0) {
            ProfitDetailMonth profitDetailMonthTemp = profitDetailMonthList.get(0);
            BigDecimal basicAmt = getComputAmt(profitDetailMonthTemp, computType);
            Map<String, Object> idMap = new HashMap<>(5);
            idMap.put("id", profitDetailMonthTemp.getId());
            idMap.put("basicAmt", basicAmt);
            temp.put(profitDetailMonthTemp.getId(), idMap);
            return idMap;
        } else {
            return null;
        }
    }

    /**
     * @Author: Zhang Lei
     * @Description: 分润计算流程
     * @Date: 13:49 2019/1/15
     */
    private void comput(String computType) {

        // 获取所有代理商月度分润明细
        String profitDate = LocalDate.now().plusMonths(-1).format(DateTimeFormatter.BASIC_ISO_DATE).substring(0, 6);
        ProfitDetailMonth profitDetailMonth = new ProfitDetailMonth();
        profitDetailMonth.setProfitDate(profitDate);

        Map<String, Object> params = new HashMap<>();
        params.put("profitMonth", profitDate);

        int count = this.getProfitDetailMonthCount(null, profitDetailMonth);
        if (count > 0) {

            //更新代理商税点
            LOG.info("更新代理商税点开始，{}月",profitDate);
            profitDetailMonthMapper.updateAgentInfoBeforeComput(profitDate);
            LOG.info("更新代理商税点结束");

            // 其他扣款
            FORK_JOIN_POOL.invoke(new ProfitMonthServiceImpl.ComputStep(0, count, profitDetailMonth, computType));

            // 合并代理商扣分润计算
            notDeductionList.parallelStream().forEach(profitDetailMonthTemp -> {
                List<Map<String, Object>> hbList = getAgentIdProfitAmt(profitDetailMonthTemp.getAgentId(), profitAmtMap);
                if (hbList != null && hbList.size() > 0) {
                    doHbDeduction(profitDetailMonthTemp, computType, hbList);
                }
            });

            // 机具扣款未扣足，扣担保代理商
            toolNotDeductionList.parallelStream().forEach(profitDetailMonthTemp -> {
                doHbToolDeduction(profitDetailMonthTemp, computType, null, "3");
            });

            // 扣税
            taxDeductionService.taxDeductionComputer();

            // 补税点
            profitSupplyTaxService.taxSupplyComputer(params);

            // 欠票计算
            ownInvoiceService.invoiceOwnComputer(params);

            // 更新实发分润
            profitDetailMonthMapper.updateRealProfitAmt(params);

            /*// 计算税点及实发分润
            try {
                long sstart = System.currentTimeMillis();
                profitComputerService.new_computerTax(computType);
                long send = System.currentTimeMillis();
                System.out.println("实发处理时间"+(send-sstart));
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            LOG.error("执行完毕");
            temp.clear();
            profitAmtMap.clear();
        } else {
            LOG.error("没有获取到分润明细");
        }
    }

    /*** 
     * @Description: 执行合并代理商扣款
     * @Param:
     * @return:
     * @Author: zhaodw
     * @Date: 2018/10/17
     */
    private void doHbDeduction(ProfitDetailMonth profitDetailMonth, String computType, List<Map<String, Object>> hbList) {

        //退单扣款
        hbList = doHbTdDeductionAmt(profitDetailMonth, computType, hbList);
        if (!hbList.isEmpty()) {
            // 机具扣款
            hbList = doHbToolDeduction(profitDetailMonth, computType, hbList, "2");
            if (!hbList.isEmpty()) {
                //POS考核扣款（新国都、瑞易送）-
                Map<String, Object> param = new HashMap<>(5);
                param.put("agentId", profitDetailMonth.getAgentId());
                param.put("computeType", computType);
                param.put("parentAgentId", profitDetailMonth.getParentAgentId());
                param.put("sourceId", "1");
                param.put("hbList", hbList);     //合并代理商
                param.put("deductionStatus", "1");
                //POS考核扣款（新国都、瑞易送）-
                param = profitDeductionServiceImpl.otherDeductionHbByType(param);
                List<Map<String, Object>> delList = ((List) param.get("delList"));
                if (!delList.isEmpty()) {
                    delHb(delList);
                }
                if (!((List) param.get("hbList")).isEmpty()) {
                    //手刷考核扣款（小蓝牙、MPOS）-
                    param.put("sourceId", "2");
                    param = profitDeductionServiceImpl.otherDeductionHbByType(param);
                    delList = ((List) param.get("delList"));
                    if (!delList.isEmpty()) {
                        delHb(delList);
                    }
                    if (!((List) param.get("hbList")).isEmpty()) {
                        //保理扣款-
                        BigDecimal bl = profitComputerService.total_factor(profitDetailMonth.getAgentId(), null);
                        BigDecimal diff = bl.subtract(profitDetailMonth.getBuDeductionAmt());
                        if (diff.doubleValue() != 0) {
                            hbList = ((List) param.get("hbList"));
                            delList = new ArrayList<>();
                            for (Map<String, Object> hb : hbList) {
                                BigDecimal basicAmt = (BigDecimal) hb.get("basicAmt");
                                if (basicAmt.doubleValue() >= diff.doubleValue()) {
                                    if (basicAmt.equals(diff.doubleValue())) {
                                        delList.add(hb);
                                    } else {
                                        hb.put("basicAmt", basicAmt.subtract(diff));
                                    }
                                    break;
                                } else {
                                    delList.add(hb);
                                    diff = diff.subtract(basicAmt);
                                }
                                if (delList.size() > 0) {
                                    hbList.remove(delList);
                                }
                                if (hbList.isEmpty()) {
                                    break;
                                }
                            }
                            delList = ((List) param.get("delList"));
                            if (!delList.isEmpty()) {
                                delHb(delList);
                            }
                            param.put("hbList", hbList);     //代理商分润
                        }

                        //其他扣款-
                        param.put("sourceId", "3");
                        param = profitDeductionServiceImpl.otherDeductionHbByType(param);
                        delList = ((List) param.get("delList"));
                        if (!delList.isEmpty()) {
                            delHb(delList);
                        }
                    }
                }
            }

        }
        profitDetailMonth.setBasicsProfitAmt(BigDecimal.ZERO);
        profitDetailMonthMapper.updateByPrimaryKeySelective(profitDetailMonth);
        updateHb(hbList);
    }

    private List<Map<String, Object>> doHbTdDeductionAmt(ProfitDetailMonth profitDetailMonthTemp, String computeType, List<Map<String, Object>> hbList) {
        Map<String, Object> param = new HashMap<>(5);
        param.put("agentId", profitDetailMonthTemp.getAgentId());
        param.put("computeType", computeType);
        param.put("parentAgentId", profitDetailMonthTemp.getParentAgentId());
        param.put("hbList", hbList);     //合并代理商
        param.put("deductionStatus", "1");
        //退单扣款-pos 未扣足
        if (!profitDetailMonthTemp.getPosTdMustDeductionAmt().equals(profitDetailMonthTemp.getPosTdRealDeductionAmt())) {
            param.put("sourceId", "02");
            param = profitDeductionServiceImpl.settleErrHbDeduction(param);
            List<Map<String, Object>> delList = ((List) param.get("delList"));
            if (!delList.isEmpty()) {
                delHb(delList);
            }
        }
        if (!((List) param.get("hbList")).isEmpty() && !profitDetailMonthTemp.getMposTdMustDeductionAmt().equals(profitDetailMonthTemp.getMposTdRealDeductionAmt())) {
            param.put("sourceId", "01");
            param = profitDeductionServiceImpl.settleErrHbDeduction(param);
            List<Map<String, Object>> delList = ((List) param.get("delList"));
            if (!delList.isEmpty()) {
                delHb(delList);
            }
        }
        return ((List) param.get("hbList"));
    }

    private void updateHb(List<Map<String, Object>> hbList) {
        hbList.forEach(hbMap -> {
            ProfitDetailMonth update = profitDetailMonthMapper.selectByPrimaryKey((String) hbMap.get("id"));
            if (hbMap != null) {
                update.setOtherDeductionAmt(update.getOtherDeductionAmt().add(update.getBasicsProfitAmt().subtract((BigDecimal) hbMap.get("basicAmt"))));
                update.setBasicsProfitAmt((BigDecimal) hbMap.get("basicAmt"));
                profitDetailMonthMapper.updateByPrimaryKeySelective(update);
            }
        });
    }

    private void delHb(List<Map<String, Object>> delList) {
        delList.forEach(delMap -> {
            ProfitDetailMonth update = profitDetailMonthMapper.selectByPrimaryKey((String) delMap.get("id"));
            update.setOtherDeductionAmt(update.getOtherDeductionAmt().add((BigDecimal) delMap.get("basicAmt")));
            update.setBasicsProfitAmt(BigDecimal.ZERO);
            profitDetailMonthMapper.updateByPrimaryKeySelective(update);
        });
    }

    private List<Map<String, Object>> doHbToolDeduction(ProfitDetailMonth profitDetailMonthTemp, String computType,
                                                        List<Map<String, Object>> hbList, String rotation) {
        Map<String, Object> map = new HashMap<>(10);
        map.put("agentPid", profitDetailMonthTemp.getAgentId()); //业务平台编号
        map.put("agentProfitAmt", profitDetailMonthTemp.getBasicsProfitAmt());
        map.put("deductDate", LocalDate.now().plusMonths(-1).toString().substring(0, 7));   //扣款月份
        map.put("hbList", hbList);     //代理商分润
        map.put("computType", computType);
        map.put("rotation", rotation);
        try {
            map = profitToolsDeductService.execut(map);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("机具扣款失败");
            throw new RuntimeException("机具扣款失败");
        }
        return ((List) map.get("hbList"));
    }


    private BigDecimal getComputAmt(ProfitDetailMonth profitDetailMonthTemp, String computType) {
        BigDecimal sumAmt = profitDetailMonthTemp.getProfitSumAmt() == null ? BigDecimal.ZERO : profitDetailMonthTemp.getProfitSumAmt();

        // pos退单补款
        sumAmt = sumAmt.add(profitDetailMonthTemp.getPosTdSupplyAmt()==null?BigDecimal.ZERO:profitDetailMonthTemp.getPosTdSupplyAmt());
        // mpos退单补款
        sumAmt = sumAmt.add(profitDetailMonthTemp.getMposTdSupplyAmt()==null?BigDecimal.ZERO:profitDetailMonthTemp.getMposTdSupplyAmt());
        // 其他补款
        profitDetailMonthTemp.setOtherSupplyAmt(profitComputerService.new_total_supply(profitDetailMonthTemp.getAgentId(), profitDetailMonthTemp.getParentAgentId(), null));
        sumAmt = sumAmt.add(profitDetailMonthTemp.getOtherSupplyAmt());
        //机具返现
//        profitDetailMonthTemp.setOtherSupplyAmt(profitComputerService.new_total_supply(profitDetailMonthTemp.getAgentId(), profitDetailMonthTemp.getParentAgentId(), null));
        sumAmt = sumAmt.add(profitDetailMonthTemp.getToolsReturnAmt());

        // 考核奖励
        sumAmt = sumAmt.add(profitDetailMonthTemp.getPosRewardAmt() == null ? BigDecimal.ZERO : profitDetailMonthTemp.getPosRewardAmt());
        //.subtract(profitDetailMonthTemp.getPosRewardDeductionAmt())
        //退单扣款-
        if (!profitDetailMonthTemp.getAgentId().startsWith("6000")) {
            sumAmt = doTdDeductionAmt(profitDetailMonthTemp, sumAmt, computType);
        }
//        BigDecimal otherAmt = BigDecimal.ZERO;
        Map<String, Object> param = new HashMap<>(5);
        param.put("agentId", profitDetailMonthTemp.getAgentId());
        param.put("parentAgentId", profitDetailMonthTemp.getParentAgentId());
        param.put("deductionDate", profitDetailMonthTemp.getProfitDate());
        param.put("profitAmt", sumAmt);
        param.put("sourceId", "4");// 罚款
        BigDecimal fk = profitDeductionServiceImpl.otherDeductionByType(param);
//        otherAmt = otherAmt.add(fk);
        sumAmt = sumAmt.subtract(fk);

        param.put("profitAmt", sumAmt);
        param.put("sourceId", "5"); // 预发分润
        BigDecimal yfk = profitDeductionServiceImpl.otherDeductionByType(param);
        profitDetailMonthTemp.setZnposProfitAmt(yfk);
        sumAmt = sumAmt.subtract(yfk);

        /*param.put("profitAmt", sumAmt);
        param.put("sourceId", "1");
        //POS考核扣款（新国都、瑞易送）-
        profitDetailMonthTemp.setPosKhDeductionAmt(profitDeductionServiceImpl.otherDeductionByType(param));
        BigDecimal posk = profitDeductionServiceImpl.otherDeductionByType(param);
        sumAmt = sumAmt.subtract(profitDetailMonthTemp.getPosKhDeductionAmt());*/
//        otherAmt = otherAmt.add(posk);

        /*param.put("profitAmt", sumAmt);
        param.put("sourceId", "2");
        //手刷考核扣款（小蓝牙、MPOS）-
        BigDecimal mposk = profitDeductionServiceImpl.otherDeductionByType(param);
        profitDetailMonthTemp.setMposKhDeductionAmt(profitDeductionServiceImpl.otherDeductionByType(param));
        sumAmt = sumAmt.subtract(profitDetailMonthTemp.getMposKhDeductionAmt());*/
//        otherAmt = otherAmt.add(mposk);

        //pos奖励考核扣款
        //sumAmt = sumAmt.subtract(profitDetailMonthTemp.getPosRewardDeductionAmt()==null?BigDecimal.ZERO:profitDetailMonthTemp.getPosRewardDeductionAmt());
        sumAmt = doRewardDuction(profitDetailMonthTemp, sumAmt, computType);

        //其它考核扣款
        sumAmt = doKhDuction(profitDetailMonthTemp, sumAmt, computType);

        //保理扣款-
        //profitDetailMonthTemp.setBuDeductionAmt(profitComputerService.total_factor(profitDetailMonthTemp.getAgentId(), null));
        //sumAmt = sumAmt.subtract(profitDetailMonthTemp.getBuDeductionAmt());
        sumAmt = doBLDuction(profitDetailMonthTemp, sumAmt, computType);

        //其他扣款-
        param.put("profitAmt", sumAmt);
        param.put("sourceId", "3");
        long qkstart = System.currentTimeMillis();
        BigDecimal qt = profitDeductionServiceImpl.otherDeductionByType(param);
        profitDetailMonthTemp.setOtherDeductionAmt(qt.add(fk));
        sumAmt = sumAmt.subtract(qt);
//        otherAmt = otherAmt.add(qt);

        // 机具扣款-
        long jkstart = System.currentTimeMillis();
        sumAmt = doToolDeduction(profitDetailMonthTemp, sumAmt, computType);
        long jkend = System.currentTimeMillis();
        //System.out.println("机具扣款处理时间" + (jkend - jkstart));

        //基础分润
        profitDetailMonthTemp.setBasicsProfitAmt(sumAmt);
        long updatestart = System.currentTimeMillis();
        profitDetailMonthMapper.updateByPrimaryKeySelective(profitDetailMonthTemp);
        long updateend = System.currentTimeMillis();
        //System.out.println("修改处理时间" + (updateend - updatestart));
        return sumAmt;
    }

    /**
     * @Author: Zhang Lei
     * @Description: 保理扣款计算
     * @Date: 16:53 2019/1/14
     */
    private BigDecimal doBLDuction(ProfitDetailMonth profitDetailMonthTemp, BigDecimal sumAmt, String computType) {
        String deductionDate = LocalDate.now().plusMonths(-1).toString().substring(0, 7).replace("-", "");
        Map<String, Object> param = new HashMap<>();
        param.put("profitAmt", sumAmt);
        param.put("computeType", computType);
        param.put("agentId", profitDetailMonthTemp.getAgentId());
        param.put("parentAgentId", profitDetailMonthTemp.getParentAgentId());
        param.put("factorMonth", deductionDate);
        param.put("deductionStatus", "0");

        // 保理扣款实扣
        BigDecimal realDeductionAMt = profitFactorService.blDeduction(param);
        profitDetailMonthTemp.setBuDeductionAmt(realDeductionAMt);
        sumAmt = sumAmt.subtract(realDeductionAMt);
        return sumAmt;
    }

    /**
     * @Author: Zhang Lei
     * @Description: 05-考核扣款系统计算部分
     * @Date: 15:00 2019/1/14
     */
    private BigDecimal doRewardDuction(ProfitDetailMonth profitDetailMonthTemp, BigDecimal sumAmt, String computType) {
        String deductionDate = LocalDate.now().plusMonths(-1).toString().substring(0, 7).replace("-", "");
        Map<String, Object> param = new HashMap<>();
        param.put("profitAmt", sumAmt);
        param.put("computeType", computType);
        param.put("agentId", profitDetailMonthTemp.getAgentId());
        param.put("parentAgentId", profitDetailMonthTemp.getParentAgentId());
        param.put("deductionDate", deductionDate);
        param.put("deductionType", DeductionType.POS_REWARD_CALCU_DEDUCT.getType());
        param.put("deductionStatus", "0");

        // 考核扣款实扣
        BigDecimal realDeductionAMt = profitDeductionServiceImpl.khDeduction(param);
        profitDetailMonthTemp.setPosRewardDeductionAmt(realDeductionAMt);
        sumAmt = sumAmt.subtract(realDeductionAMt);
        return sumAmt;
    }

    /**
     * @Author: Zhang Lei
     * @Description: 04-考核扣款导入部分
     * @Date: 16:23 2019/1/4
     */
    public BigDecimal doKhDuction(ProfitDetailMonth profitDetailMonthTemp, BigDecimal sumAmt, String computType) {
        String deductionDate = LocalDate.now().plusMonths(-1).toString().substring(0, 7).replace("-", "");
        Map<String, Object> param = new HashMap<>();
        param.put("profitAmt", sumAmt);
        param.put("computeType", computType);
        param.put("agentId", profitDetailMonthTemp.getAgentId());
        param.put("parentAgentId", profitDetailMonthTemp.getParentAgentId());
        param.put("deductionDate", deductionDate);
        param.put("deductionType", DeductionType.POS_REWARD_DEDUCT.getType());
        param.put("deductionStatus", "0");

        /*if(profitDetailMonthTemp.getAgentId().equals("AG20181121000000000012621")){
            System.out.print(profitDetailMonthTemp.getParentAgentId());
        }*/

        // 考核扣款实扣
        BigDecimal realDeductionAMt = profitDeductionServiceImpl.khDeduction(param);
        profitDetailMonthTemp.setPosKhDeductionAmt(realDeductionAMt);
        sumAmt = sumAmt.subtract(realDeductionAMt);
        return sumAmt;
    }

    @Override
    public void testComputeProfitAmt() {
        String profitDate = LocalDate.now().plusMonths(-1).format(DateTimeFormatter.BASIC_ISO_DATE).substring(0, 6);
        profitDetailMonthMapper.clearComputData(profitDate);
        comput("2");
    }


    @Override
    public void payMoney() {

        String profitDate = LocalDate.now().plusMonths(-1).format(DateTimeFormatter.BASIC_ISO_DATE).substring(0, 6);
        ProfitDetailMonth detailMonth = new ProfitDetailMonth();
        detailMonth.setStatus("4,6");
        detailMonth.setProfitDate(profitDate);
        List<ProfitDetailMonth> profitDetailMonthList = getProfitDetailMonthList(null, null, detailMonth);
        if (profitDetailMonthList != null && profitDetailMonthList.size() > 0) {
            String paytDate = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
            profitDetailMonthList.parallelStream().forEach(profitDetailMonth -> {
                insertBalaceSerial(profitDetailMonth, paytDate);
            });
        }

        profitDetailMonthMapper.payMoney(profitDate);

    }

    /***
     * @Description: 插入出款流水表
     * @Author: zhaodw
     * @Date: 2018/8/29
     */
    private void insertBalaceSerial(ProfitDetailMonth profitDetailMonth, String paytDate) {
        ProfitBalanceSerial profitBalanceSerial = new ProfitBalanceSerial();
        profitBalanceSerial.setBalanceId(idService.genId(TabId.PBSL));
        profitBalanceSerial.setPayDate(paytDate);
        profitBalanceSerial.setProfitAmt(profitDetailMonth.getRealProfitAmt());
        profitBalanceSerial.setCardNo(profitDetailMonth.getAccountId());//卡号
        profitBalanceSerial.setAccountName(profitDetailMonth.getAccountName());//户名
        profitBalanceSerial.setChildBankCode(profitDetailMonth.getBankCode());//支行号
        profitBalanceSerial.setChildBankName(profitDetailMonth.getOpenBankName());//支行名
        profitBalanceSerial.setBalanceRcvType("1".equals(profitDetailMonth.getPayStatus()) ? "2" : "0");
        profitBalanceSerial.setAgentId(profitDetailMonth.getAgentId());
        profitBalanceSerial.setParentAgentId(profitDetailMonth.getParentAgentId());
        profitBalanceSerial.setProfitId(profitDetailMonth.getId());
        profitBalanceSerial.setInputTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        profitBalanceSerial.setStatus("0");//默认出款成功
        profitBalanceSerial.setPayCompany(profitDetailMonth.getPayCompany());//打款公司
        profitBalanceSerialServiceImpl.insert(profitBalanceSerial);
    }


    private void getPosReward(ProfitDetailMonth profitDetailMonthTemp, String computType) {
        String currentDate = LocalDate.now().plusMonths(-1).format(DateTimeFormatter.BASIC_ISO_DATE).substring(0, 6);
        try {
            Map<String, Object> map = new HashMap<>(10);
            map.put("agentId", profitDetailMonthTemp.getAgentId());
            map.put("currentDate", currentDate);
            map = posProfitComputeServiceImpl.execut(map);
            BigDecimal posReward = profitDetailMonthTemp.getPosRewardAmt() == null ? BigDecimal.ZERO : profitDetailMonthTemp.getPosRewardAmt();
            profitDetailMonthTemp.setPosRewardAmt(posReward.add((BigDecimal) map.get("posRewardAmt")));
            profitDetailMonthTemp.setPosRewardDeductionAmt((BigDecimal) map.get("posAssDeductAmt"));
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("获取pos奖励失败");
            throw new RuntimeException("获取pos奖励失败");
        }
    }

    /***
     * @Description: 执行机具扣款
     * @Param: profitDetailMonthTemp 月分润信息
     * @Param: agentProfitAmt 分润金额
     * @return: 扣款金额
     * @Author: zhaodw
     * @Date: 2018/8/13
     */
    private BigDecimal doToolDeduction(ProfitDetailMonth profitDetailMonthTemp, BigDecimal agentProfitAmt, String computType) {
        Map<String, Object> map = new HashMap<>(10);
        map.put("agentPid", profitDetailMonthTemp.getAgentId()); //业务平台编号
        map.put("deductDate", LocalDate.now().plusMonths(-1).toString().substring(0, 7).replaceAll("-",""));   //扣款月份
        map.put("agentProfitAmt", agentProfitAmt);     //代理商分润
        map.put("computType", computType);
        map.put("rotation", "1");

        try {
            profitToolsDeductService.execut(map);
            profitDetailMonthTemp.setRhbDgMustDeductionAmt((BigDecimal) map.get("RhbDgMustDeductionAmt"));
            profitDetailMonthTemp.setRhbDgRealDeductionAmt((BigDecimal) map.get("RhbDgRealDeductionAmt"));
            profitDetailMonthTemp.setZposDgMustDeductionAmt((BigDecimal) map.get("ZposDgMustDeductionAmt"));
            profitDetailMonthTemp.setZposTdRealDeductionAmt((BigDecimal) map.get("ZposTdRealDeductionAmt"));
            profitDetailMonthTemp.setPosDgMustDeductionAmt((BigDecimal) map.get("PosDgMustDeductionAmt"));
            profitDetailMonthTemp.setPosDgRealDeductionAmt((BigDecimal) map.get("PosDgRealDeductionAmt"));
            if (!Objects.equals(profitDetailMonthTemp.getRhbDgMustDeductionAmt(), profitDetailMonthTemp.getRhbDgRealDeductionAmt())
                    || !Objects.equals(profitDetailMonthTemp.getZposDgMustDeductionAmt(), profitDetailMonthTemp.getZposTdRealDeductionAmt())
                    || !Objects.equals(profitDetailMonthTemp.getPosDgMustDeductionAmt(), profitDetailMonthTemp.getPosDgRealDeductionAmt())) {
                toolNotDeductionList.add(profitDetailMonthTemp);
            }
            agentProfitAmt = (BigDecimal) map.get("agentProfitAmt");

        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("机具扣款失败");
            throw new RuntimeException("机具扣款失败");
        }
        return agentProfitAmt;
    }


    /***
     * @Description: 获取退单扣款
     * @Param: 分润明细
     * @return: 退单补款
     * @Author: zhaodw
     * @Date: 2018/8/12
     */
    private BigDecimal doTdDeductionAmt(ProfitDetailMonth profitDetailMonthTemp, BigDecimal sumAmt, String type) {
        ProfitDeduction profitDeduction = new ProfitDeduction();
        profitDeduction.setAgentId(profitDetailMonthTemp.getAgentId());
        profitDeduction.setParentAgentId(profitDetailMonthTemp.getParentAgentId());
        profitDeduction.setDeductionDate(LocalDate.now().plusMonths(-1).toString().substring(0, 7).replaceAll("-",""));
        profitDeduction.setDeductionType(DeductionType.SETTLE_ERR.getType());
        profitDeduction.setSourceId("02");
        // pos退单应扣款
        BigDecimal posMustDeductionAmt = profitDeductionServiceImpl.getSettleErrDeductionAmt(profitDeduction);
        posMustDeductionAmt = posMustDeductionAmt == null ? BigDecimal.ZERO : posMustDeductionAmt;
        profitDetailMonthTemp.setPosTdMustDeductionAmt(posMustDeductionAmt);

        // pos实扣
        Map<String, Object> param = new HashMap<>(5);
        param.put("profitAmt", sumAmt);
        param.put("sourceId", "02");
        param.put("agentId", profitDetailMonthTemp.getAgentId());
        param.put("computeType", type);
        param.put("parentAgentId", profitDetailMonthTemp.getParentAgentId());
        BigDecimal realDeductionAmt = BigDecimal.ZERO;
        if (posMustDeductionAmt.doubleValue() > 0) {
            realDeductionAmt = profitDeductionServiceImpl.settleErrDeduction(param);
            profitDetailMonthTemp.setPosTdRealDeductionAmt(realDeductionAmt);
            sumAmt = sumAmt.subtract(realDeductionAmt);
        } else {
            profitDetailMonthTemp.setPosTdRealDeductionAmt(BigDecimal.ZERO);
        }


        // mpos退单应扣款
        profitDeduction.setSourceId("01");
        BigDecimal mposMustDeductionAmt = profitDeductionServiceImpl.getSettleErrDeductionAmt(profitDeduction);
        mposMustDeductionAmt = mposMustDeductionAmt == null ? BigDecimal.ZERO : mposMustDeductionAmt;
        profitDetailMonthTemp.setMposTdMustDeductionAmt(mposMustDeductionAmt);

        // mpos退单实扣款
        if (mposMustDeductionAmt.doubleValue() > 0) {
            param.put("profitAmt", sumAmt);
            param.put("sourceId", "01");
            realDeductionAmt = profitDeductionServiceImpl.settleErrDeduction(param);
            profitDetailMonthTemp.setMposTdRealDeductionAmt(realDeductionAmt);
            sumAmt = sumAmt.subtract(realDeductionAmt);
        } else {
            profitDetailMonthTemp.setMposTdRealDeductionAmt(BigDecimal.ZERO);
        }
        return sumAmt;
    }

    /***
     * @Description: 获取代理商合并后的分润数据
     * @Param:
     * @return:
     * @Author: zhaodw
     * @Date: 2018/10/16
     */
    private List<Map<String, Object>> getAgentIdProfitAmt(String agentId, Map<String, List<Map<String, Object>>> profitAmtMap) {
        List<PAgentMerge> merges = agentMergeMapper.selectByAgentId(agentId);
        if (merges != null && merges.size() > 0) {
            Set<String> keys = profitAmtMap.keySet();
            List<Map<String, Object>> list = new ArrayList<>(10);
            merges.forEach(merg -> {
                String deductionAgentId = null;
                if (agentId.equals(merg.getMainAgentId())) {
                    deductionAgentId = merg.getSubAgentId();
                } else {
                    deductionAgentId = merg.getMainAgentId();
                }

                if (profitAmtMap.containsKey(deductionAgentId)) {
                    list.addAll(profitAmtMap.get(deductionAgentId));
                }
            });
            return list;
        }
        return null;
    }

    /**
     * 导出数据
     *
     * @param profitDetailMonth
     * @return
     */
    @Override
    public List<ProfitDirect> exportByFinance(ProfitDetailMonth profitDetailMonth) {
        return profitDetailMonthMapper.exportByFinance(profitDetailMonth);
    }


    class ComputStep extends RecursiveAction {

        private static final int LIMIT = 2000;
        private int start;
        private int end;
        private ProfitDetailMonth profitDetailMonth;
        private String computType;

        public ComputStep(int start, int end, ProfitDetailMonth profitDetailMonth, String computType) {
            this.start = start;
            this.end = end;
            this.profitDetailMonth = profitDetailMonth;
            this.computType = computType;
        }


        @Override
        protected void compute() {
            // 判断是否达到处理的数据量
            boolean canCompute = this.end - this.start <= LIMIT;
            if (canCompute) {
                Map<String, Object> param = new HashMap<>();
                param.put("profitDate", profitDetailMonth.getProfitDate());
                param.put("start", start);
                param.put("end", end);
                List<ProfitDetailMonth> profitDetailMonthList = profitDetailMonthMapper.getProfitDetailMonthListByParam(param);
                if (profitDetailMonthList != null && profitDetailMonthList.size() > 0) {
                    profitDetailMonthList.parallelStream().forEach(profitDetailMonthTemp -> {
                        if (profitDetailMonthTemp.getAgentId() != null) {
                            BigDecimal basicAmt = BigDecimal.ZERO;
                            Map<String, Object> idMap = null;
                            if (temp.containsKey(profitDetailMonthTemp.getId())) {
                                idMap = temp.get(profitDetailMonthTemp.getId());
                                basicAmt = (BigDecimal) idMap.get("basicAmt");  //应发分润
                            } else {
                                basicAmt = getComputAmt(profitDetailMonthTemp, computType);
                            }
                            if (basicAmt.doubleValue() == 0) {
                                notDeductionList.add(profitDetailMonthTemp);
                            } else {
                                if (idMap == null) {
                                    idMap = new HashMap<>(5);
                                    idMap.put("id", profitDetailMonthTemp.getId());
                                    idMap.put("basicAmt", basicAmt);
                                }
                                if (profitAmtMap.containsKey(profitDetailMonthTemp.getAgentId())) {
                                    profitAmtMap.get(profitDetailMonthTemp.getAgentId()).add(idMap);
                                } else {
                                    List<Map<String, Object>> list = new ArrayList<>(1);
                                    list.add(idMap);
                                    profitAmtMap.put(profitDetailMonthTemp.getAgentId(), list);
                                    list = null;
                                    idMap = null;
                                }
                            }
                        }
                    });
                }
            } else {
                int middle = (this.end + this.start) / 2;
                invokeAll(new ComputStep(this.start, middle, profitDetailMonth, computType),
                        new ComputStep(middle, end, profitDetailMonth, computType));
            }
        }
    }

    @Override
    public void initPosRowardDetail() throws Exception {
        ProfitDetailMonth month = new ProfitDetailMonth();
        month.setProfitDate(LocalDate.now().plusMonths(-1).format(DateTimeFormatter.BASIC_ISO_DATE).substring(0, 6));
        int count = this.getProfitDetailMonthCount(null, month);
        if (count > 0) {
            try {
                posProfitComputeServiceImpl.otherOperate();
            } catch (Exception e) {
                e.printStackTrace();
                LOG.error("初始化POS奖励基础数据失败，", e);
                throw new Exception("初始化POS奖励基础数据失败，" + e.getMessage());
            }
        } else {
            throw new Exception("请先初始化基础分润数据");
        }
    }

    @Override
    public PageInfo queryProfitDetailMonthList(Map<String, Object> param, PageInfo pageInfo, ProfitDetailMonth profitDetailMonth) {
        Long count = 0L;
        List<Map<String, Object>> list;
        if ("1".equals(param.get("chekbox"))) {
            count=profitDetailMonthMapper.queryProfitDetailLowerMonthCount(param);
            list = profitDetailMonthMapper.queryProfitDetailLowerMonthList(param);
        }else{
             count = profitDetailMonthMapper.queryProfitDetailMonthCount(param);
           list = profitDetailMonthMapper.queryProfitDetailMonthList(param);
        }

        pageInfo.setTotal(count.intValue());
        pageInfo.setRows(list);
        return pageInfo;
    }

    /**
     * 导出数据
     * @param param
     * @return
     */
    @Override
    public List<Map<String,Object>> exportByF(Map<String, Object> param){
        List<Map<String,Object>> list;
        if ("1".equals(param.get("chekbox"))) {  //包含下级
            list = profitDetailMonthMapper.exportByFHaveChild(param);
        }else{ //不包含下级
            list = profitDetailMonthMapper.exportByFNoChild(param);
        }
        return list;
    }



    @Override
    public Map<String,Object> profitCount(Map<String, Object> param) {
        String checkbox=param.get("checkbox").toString();
        Map<String,Object> map=null;
        if("1".equals(checkbox)){
            map=profitDetailMonthMapper.profitCountWithSubordinate(param);
        }else{
            map=profitDetailMonthMapper.profitCount(param);
        }
        return map;
    }

    @Override
    public ProfitDetailMonth getByAgentId(String agentId) {
        return profitDetailMonthMapper.selectByAgentId(agentId);
    }




    /**
     * 代理商日分润冻结
     * @param  agentId 代理商唯一码
     */
    @Override
    public Map<String,String> doFrozenByAgent(String agentId) {
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("agencyBlack_type", "1");
        map.put("type", "1");
        map.put("unfreeze", "0");
        map.put("flag", "4");
        map.put("batchIds",agentId);
        String params = JsonUtil.objectToJson(map);
        String res = HttpClientUtil.doPostJson
                (AppConfig.getProperty("busiPlat.refuse"), params);
        LOG.debug("请求信息：" + res);
        Map<String,String> map1 = new HashMap<String,String>();
        map1.put(JSONObject.parseObject(res).get("respCode").toString(),
                JSONObject.parseObject(res).get("respMsg").toString());
        return map1;
    }

    /**
     * 代理商日分润解冻
     * @param  agentId 代理商唯一码
     */
    @Override
    public Map<String,String> doUnFrozenAgentProfit(String agentId) {
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("agencyBlack_type","0");
        map.put("type","1");
        map.put("unfreeze","0");
        map.put("flag","0");
        map.put("batchIds",agentId);//AG码list
        String params = JsonUtil.objectToJson(map);
        String res = HttpClientUtil.doPostJson
                (AppConfig.getProperty("busiPlat.refuse"),params);

        Map<String,String> map1 = new HashMap<String,String>();
        map1.put(JSONObject.parseObject(res).get("respCode").toString(),
                JSONObject.parseObject(res).get("respMsg").toString());
        return map1;

    }

}