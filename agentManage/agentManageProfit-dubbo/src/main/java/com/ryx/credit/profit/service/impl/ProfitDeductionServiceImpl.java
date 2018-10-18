package com.ryx.credit.profit.service.impl;

import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.BeanUtils;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.dao.ProfitDeductionMapper;
import com.ryx.credit.profit.enums.DeductionStatus;
import com.ryx.credit.profit.enums.DeductionType;
import com.ryx.credit.profit.enums.StagingDetailStatus;
import com.ryx.credit.profit.exceptions.DeductionException;
import com.ryx.credit.profit.exceptions.StagingException;
import com.ryx.credit.profit.jobs.RefundJob;
import com.ryx.credit.profit.pojo.ProfitDeduction;
import com.ryx.credit.profit.pojo.ProfitDeductionExample;
import com.ryx.credit.profit.pojo.ProfitDeducttionDetail;
import com.ryx.credit.profit.pojo.ProfitStagingDetail;
import com.ryx.credit.profit.service.ProfitDeductionService;
import com.ryx.credit.profit.service.ProfitDeducttionDetailService;
import com.ryx.credit.profit.service.ProfitSupplyService;
import com.ryx.credit.profit.service.StagingService;
import com.ryx.credit.service.dict.IdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhaodw
 * @Title: ProfitDeductionServiceImpl
 * @ProjectName agentManage
 * @Description: TODO
 * @date 2018/7/2417:32
 */
@Service
public class ProfitDeductionServiceImpl implements ProfitDeductionService {

    @Autowired
    private ProfitDeductionMapper profitDeductionMapper;

    @Autowired
    private StagingService stagingServiceImpl;

    @Autowired
    private IdService idService;

    @Autowired
    private ProfitDeducttionDetailService profitDeducttionDetailServiceImpl;

    @Autowired
    private ProfitSupplyService profitSupplyServiceImpl;

    @Override
    public PageInfo getProfitDeductionList(ProfitDeduction profitDeduction, Page page) {
        ProfitDeductionExample example = new ProfitDeductionExample();
        example.setPage(page);
        ProfitDeductionExample.Criteria criteria = example.createCriteria();
        // 月份按开始到结束查询
        if (StringUtils.isNotBlank(profitDeduction.getDeductionDateStart()) && StringUtils.isNotBlank(profitDeduction.getDeductionDateEnd()))
        {
            criteria.andDeductionDateBetween(profitDeduction.getDeductionDateStart(),profitDeduction.getDeductionDateEnd());
        }else if (StringUtils.isNotBlank(profitDeduction.getDeductionDateStart())){
            criteria.andDeductionDateEqualTo(profitDeduction.getDeductionDateStart());
        }else if (StringUtils.isNotBlank(profitDeduction.getDeductionDateEnd())){
            criteria.andDeductionDateEqualTo(profitDeduction.getDeductionDateEnd());
        }
        if (StringUtils.isNotBlank(profitDeduction.getAgentId())){
            criteria.andAgentIdEqualTo(profitDeduction.getAgentId());
        }
        if (StringUtils.isNotBlank(profitDeduction.getParentAgentId())){
            criteria.andAgentIdEqualTo(profitDeduction.getParentAgentId());
        }
        if (StringUtils.isNotBlank(profitDeduction.getAgentPid())){
            criteria.andAgentPidEqualTo(profitDeduction.getAgentPid());
        }
        if (StringUtils.isNotBlank(profitDeduction.getParentAgentPid())){
            criteria.andAgentPidEqualTo(profitDeduction.getParentAgentPid());
        }
        if (StringUtils.isNotBlank(profitDeduction.getDeductionType())){
            criteria.andDeductionTypeEqualTo(profitDeduction.getDeductionType());
        }
        if (StringUtils.isNotBlank(profitDeduction.getSourceId())){
            criteria.andSourceIdEqualTo(profitDeduction.getSourceId());
        }
        example.setOrderByClause("CREATE_DATE_TIME DESC ");
        List<ProfitDeduction> profitDeductions = profitDeductionMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(profitDeductions);
        pageInfo.setTotal(profitDeductionMapper.countByExample(example));
        return pageInfo;
    }

    @Override
    public ProfitDeduction getProfitDeductionById(String id) {
        if (StringUtils.isNotBlank(id)) {
            return profitDeductionMapper.selectByPrimaryKey(id);
        }else{
            return null;
        }
    }

    @Override
    public void updateProfitDeduction(ProfitDeduction deduction) {
        if (deduction != null) {
             profitDeductionMapper.updateByPrimaryKeySelective(deduction);
        }else{
            throw new StagingException("修改状态失败。");
        }
    }

    @Override
    public void insert(ProfitDeduction deduction) {
        if(StringUtils.isBlank(deduction.getId())){
            deduction.setId(idService.genId(TabId.P_DEDUCTION));
        }
        profitDeductionMapper.insertSelective(deduction);
    }

    @Override
    public void batchInsertOtherDeduction(List<List<Object>> deductionist, String userId) {
        if(deductionist != null && deductionist.size() > 0 ) {
            deductionist.stream().filter(list->list.get(0) != null && list.get(1) != null && list.get(4) != null).forEach(list->{
                insertDeduction(list, userId);
            });


        }
    }

    /***
    * @Description: 插入其他扣款
    * @Param:  list 导入的数据
    * @Param:  userId 用户id
    * @Author: zhaodw
    * @Date: 2018/8/9
    */
    private void insertDeduction(List list, String userId) {
        BigDecimal amt = list.get(4)==null?BigDecimal.ZERO:new BigDecimal(list.get(4).toString());
        ProfitDeduction deduction = new ProfitDeduction();
        deduction.setDeductionType(DeductionType.OTHER.getType());
        deduction.setAddDeductionAmt(amt);
        deduction.setSumDeductionAmt(amt);
        deduction.setMustDeductionAmt(amt);
        deduction.setStagingStatus(DeductionStatus.NOT_APPLIED.getStatus());
        deduction.setId(idService.genIdInTran(TabId.P_DEDUCTION) );
        deduction.setAgentPid(list.get(0).toString());
        deduction.setParentAgentPid(list.get(1).toString());
        deduction.setAgentName(list.get(2).toString());
        deduction.setRemark(list.get(3).toString());
        deduction.setDeductionDate(LocalDate.now().plusMonths(-1).format(DateTimeFormatter.ISO_DATE).substring(0,7));
        deduction.setCreateDateTime(new Date());
        deduction.setUserId(userId);
        this.insert(deduction);
    }


    @Override
    public int getProfitDeductionCount(String deductType, String deductDate) {
        ProfitDeductionExample example = new ProfitDeductionExample();
        ProfitDeductionExample.Criteria criteria = example.createCriteria();
        criteria.andDeductionTypeEqualTo(deductType);
        criteria.andDeductionDateEqualTo(deductDate);
        return profitDeductionMapper.countByExample(example);
    }

    @Override
    public List<Map<String, Object>> getDeductDetail(String deductDate) {
        return profitDeductionMapper.getDeductDetail(deductDate);
    }

    @Override
    public List<ProfitDeduction> getProfitDeduction(ProfitDeduction profitDeduction) {
        ProfitDeductionExample example = new ProfitDeductionExample();
        ProfitDeductionExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(profitDeduction.getAgentId())){
            criteria.andAgentIdEqualTo(profitDeduction.getAgentId());
        }
        if (StringUtils.isNotBlank(profitDeduction.getDeductionType())){
            criteria.andDeductionTypeEqualTo(profitDeduction.getDeductionType());
        }
        if (StringUtils.isNotBlank(profitDeduction.getDeductionDate())){
            criteria.andDeductionDateEqualTo(profitDeduction.getDeductionDate());
        }
        if (StringUtils.isNotBlank(profitDeduction.getAgentPid())){
            criteria.andAgentPidEqualTo(profitDeduction.getAgentPid());
        }
        if (StringUtils.isNotBlank(profitDeduction.getParentAgentPid())){
                criteria.andParentAgentPidEqualTo(profitDeduction.getParentAgentPid());
        }else {
            criteria.andParentAgentPidIsNull();
        }
        if (StringUtils.isNotBlank(profitDeduction.getDeductionStatus())){
            criteria.andDeductionStatusEqualTo(profitDeduction.getDeductionStatus());
        }else {
            criteria.andDeductionStatusIsNull();
        }
        if (StringUtils.isNotBlank(profitDeduction.getSourceId())){
            criteria.andSourceIdEqualTo(profitDeduction.getSourceId());
        }
        if (StringUtils.isNotBlank(profitDeduction.getRemark())){
            if (!"POS考核扣款（新国都、瑞易送）".equals(profitDeduction.getRemark()) && !"手刷考核扣款（小蓝牙、MPOS）".equals(profitDeduction.getRemark())) {
                criteria.andRemarkNotIn(Arrays.asList(new String[]{"POS考核扣款（新国都、瑞易送）", "手刷考核扣款（小蓝牙、MPOS）"}));
            }else {
                criteria.andRemarkEqualTo(profitDeduction.getRemark());
            }
        }
        if (StringUtils.isNotBlank(profitDeduction.getDeductionDesc())){
            criteria.andDeductionDescEqualTo(profitDeduction.getDeductionDesc());
        }
        List<ProfitDeduction> profitDeductions = profitDeductionMapper.selectByExample(example);
        if(profitDeductions != null && !profitDeductions.isEmpty()){
            return profitDeductions;
        }
        return null;
    }

    @Override
    public BigDecimal getSupplyAmt(String agentPid, String bussType, String parentAgentId) {
        Map<String, Object> param = new HashMap<>(3);
        String supplyDate = LocalDate.now().plusMonths(-1).format(DateTimeFormatter.BASIC_ISO_DATE).substring(0,6);
        param.put("AGENT_PID", agentPid);
        param.put("SUPPLY_TYPE", RefundJob.SUPPLY_DESC);
        param.put("SUPPLY_DATE", supplyDate);
        param.put("PARENT_AGENT_ID", parentAgentId);
        PageInfo pageInfo = new PageInfo();
        pageInfo = profitSupplyServiceImpl.getProfitSupplyList(param, pageInfo);
        if (pageInfo != null && pageInfo.getTotal() > 0) {
            List<Map<String, Object>> supplys = pageInfo.getRows();
            return supplys.stream().map(supply->new BigDecimal(supply.get("SUPPLY_AMT")==null?"0":supply.get("SUPPLY_AMT").toString())).reduce(BigDecimal::add).get();
        }
        return BigDecimal.ZERO;
    }

    @Override
    public BigDecimal otherDeductionByType(Map<String, Object> param) throws DeductionException {
        String deductionDate = LocalDate.now().plusMonths(-1).toString().substring(0,7);
        try {
            param.put("deductionDate", deductionDate);
            return otherDeduction(param);
        }catch ( Exception e) {
            e.printStackTrace();
            throw new DeductionException("扣款失败。");
        }
    }

    @Override
    public BigDecimal settleErrDeduction(Map<String, Object> param) throws DeductionException {
        // 获取退单扣款
        String deductionDate = LocalDate.now().plusMonths(-1).toString().substring(0,7);
        param.put("deductionDate", deductionDate);
        param.put("type", DeductionType.SETTLE_ERR.getType());
        List<ProfitDeduction> deductionList = getProfitDeductionListByType(param);
        if (deductionList != null && deductionList.size() > 0) {
            BigDecimal profitAmt = (BigDecimal)param.get("profitAmt");
           if ("02".equals((String)param.get("sourceId"))) {
              return getDeductionAmt(deductionList, profitAmt, (String) param.get("computeType"));
           }else{
              return getMposDeductionAmt(deductionList, profitAmt, (String) param.get("computeType"));
           }
        }
        return  BigDecimal.ZERO;
    }

    @Override
    public BigDecimal getSettleErrDeductionAmt(ProfitDeduction profitDeduction) {
        return profitDeductionMapper.getSettleErrDeductionAmt(profitDeduction);
    }

    /***
    * @Description: 获取mpos扣款金额
    * @Param:   deductionList 扣款金额
    * @Param:   profitAmt 分润金额
    * @Param:   agentId 分润金额
    * @return: 总扣款金额
    * @Author: zhaodw
    * @Date: 2018/8/9
    */
    private BigDecimal getMposDeductionAmt( List<ProfitDeduction> deductionList, BigDecimal profitAmt, String computeType) {
        BigDecimal result =  BigDecimal.ZERO;
        if (deductionList != null && deductionList.size() > 0) {
            BigDecimal currentProfit = null;
            for (ProfitDeduction profitDeductionTemp : deductionList) {
                  result = deduction(result, profitAmt, profitDeductionTemp, computeType);
            }
        }
        return  result;
    }

    /*** 
    * @Description: 获取扣款列表
    * @Param:  param 扣款查询参数
    * @return: 扣款列表
    * @Author: zhaodw 
    * @Date: 2018/8/21 
    */ 
    private List<ProfitDeduction> getProfitDeductionListByType(Map<String, Object> param) {
        ProfitDeduction profitDeduction = new ProfitDeduction();
        profitDeduction.setAgentPid((String)param.get("agentPid"));
        profitDeduction.setDeductionType((String)param.get("type"));
        profitDeduction.setDeductionDate((String)param.get("deductionDate"));
        profitDeduction.setSourceId((String)param.get("sourceId"));
        profitDeduction.setRemark((String)param.get("remark"));
        profitDeduction.setParentAgentPid((String)param.get("parentAgentPid"));
        profitDeduction.setDeductionStatus((String)param.get("deductionStatus"));
        return this.getProfitDeduction(profitDeduction);
    }

    /***
    * @Description: 其他扣款处理
    * @Param:  profitAmt 分润金额
    * @Param:  agentId 机构id
    * @Param:  deductionDate 分润月份
    * @return:
    * @Author: zhaodw
    * @Date: 2018/8/8
    */
    private  BigDecimal otherDeduction(Map<String, Object> param) {
        // 获取代理商所有其它扣款信息
        param.put("type", DeductionType.OTHER.getType());
        List<ProfitDeduction> deductionList = getProfitDeductionListByType(param);
        return getDeductionAmt(deductionList, (BigDecimal) param.get("profitAmt"), (String) param.get("computeType"));
    }

    /***
    * @Description: 进行扣款处理
    * @Param:  deductionList 扣款列表
    * @Param:  profitAmt 分润金额
    * @return:  总扣款金额
    * @Author: zhaodw
    * @Date: 2018/8/9
    */
    private BigDecimal getDeductionAmt( List<ProfitDeduction> deductionList, BigDecimal profitAmt, String computeType) {
        BigDecimal result =  BigDecimal.ZERO;
        if (deductionList != null && deductionList.size() > 0) {
            for (ProfitDeduction profitDeductionTemp : deductionList) {
                result = deduction(result, profitAmt, profitDeductionTemp, computeType);
            }
        }
        return  result;
    }
    /***
    * @Description:  扣款业务实现
     * @Param:  result 扣款总金额
     * @Param:  profitAmt 分润金额
     * @Param:  profitDeductionTemp 扣款信息
    * @return:
    * @Author: zhaodw
    * @Date: 2018/8/9
    */
    private BigDecimal deduction(BigDecimal result, BigDecimal profitAmt, ProfitDeduction profitDeductionTemp, String computeType) {
        BigDecimal currentProfit = null;
        if (profitAmt.doubleValue() > 0) {
            currentProfit = profitAmt;
            profitAmt = profitAmt.subtract(profitDeductionTemp.getMustDeductionAmt());
            if (profitAmt.doubleValue() > 0) {
                result = result.add(profitDeductionTemp.getMustDeductionAmt());
            } else {
                result = result.add(currentProfit);
            }
        } else if (profitAmt.doubleValue() <= 0) {
            currentProfit = BigDecimal.ZERO;
            profitAmt = BigDecimal.ZERO;
        }
        if ("1".equals(computeType)) {
            // 申请分期
            if ("3".equals(profitDeductionTemp.getStagingStatus())) {
                stagingDeal(currentProfit, profitAmt, profitDeductionTemp);
            } else {
                generalDeal(currentProfit, profitAmt, profitDeductionTemp);
            }
        }
        return result;
    }

    /***
    * @Description: 含分期的扣款处理
    * @Param:  profitAmt 当前分润
    * @Param:  resultAmt 扣款结果
    * @Param:  profitDeductionTemp 扣款对象
    * @return:
    * @Author: zhaodw
    * @Date: 2018/8/8
    */
    private void stagingDeal(BigDecimal profitAmt,BigDecimal resultAmt, ProfitDeduction profitDeductionTemp) {
        // 扣足
        profitDeductionTemp.setDeductionStatus("1");//已扣款
        if (resultAmt.doubleValue() >= 0 && profitAmt.doubleValue() > 0) {
            profitDeductionTemp.setActualDeductionAmt(profitDeductionTemp.getMustDeductionAmt());
            profitDeductionTemp.setNotDeductionAmt(BigDecimal.ZERO);
        }else{
            if (resultAmt.doubleValue() < 0) {
                profitDeductionTemp.setActualDeductionAmt(profitAmt);
                profitDeductionTemp.setNotDeductionAmt(resultAmt.abs());
            }else{
                profitDeductionTemp.setActualDeductionAmt(BigDecimal.ZERO);
                profitDeductionTemp.setNotDeductionAmt(profitDeductionTemp.getMustDeductionAmt());
            }
        }
        BigDecimal stagAmt = getNextStagAmt(profitDeductionTemp);
        // 未扣足或分期还存在，进行下期扣款生成
        if (resultAmt.doubleValue() < 0 || profitAmt.doubleValue()==0 || stagAmt!=null) {
            // 将当期扣款对象存入历史
            createHisDeduction(profitDeductionTemp);
            profitDeductionTemp.setUpperNotDeductionAmt(profitDeductionTemp.getNotDeductionAmt());//上月未扣足
            if (stagAmt == null) {
                stagAmt = BigDecimal.ZERO;
            }
            // 更新当期未下期扣款
            profitDeductionTemp.setDeductionDate(LocalDate.now().toString().substring(0,7));
            profitDeductionTemp.setSumDeductionAmt(stagAmt.add(profitDeductionTemp.getUpperNotDeductionAmt()));
            profitDeductionTemp.setMustDeductionAmt(profitDeductionTemp.getSumDeductionAmt());
            profitDeductionTemp.setNotDeductionAmt(BigDecimal.ZERO); // 未扣足请0
            profitDeductionTemp.setActualDeductionAmt(BigDecimal.ZERO);// 实扣清0
            profitDeductionTemp.setAddDeductionAmt(BigDecimal.ZERO);
            profitDeductionTemp.setActualDeductionAmt(BigDecimal.ZERO);
            profitDeductionTemp.setDeductionStatus("0");//未扣款
            profitDeductionTemp.setCreateDateTime(new Date());
            updateStagingDetail(profitAmt, profitDeductionTemp);
        }
        profitDeductionMapper.updateByPrimaryKeySelective(profitDeductionTemp);
    }

    /***
    * @Description: 获取下月分期
    * @Param:  profitDeductionTemp 扣款对象
    * @Author: zhaodw
    * @Date: 2018/8/8
    */
    private BigDecimal getNextStagAmt(ProfitDeduction profitDeductionTemp) {
        ProfitStagingDetail profitStagingDetail = new ProfitStagingDetail();
        profitStagingDetail.setDeductionDate(LocalDate.now().plusMonths(1).toString().substring(0,7));
        profitStagingDetail.setSourceId(profitDeductionTemp.getId());
        PageInfo pageInfo = stagingServiceImpl.getStagingDetailList(profitStagingDetail,null);
        if (pageInfo != null && pageInfo.getTotal()>0) {
           List<ProfitStagingDetail> profitStagingDetailList = pageInfo.getRows();
            return profitStagingDetailList.stream().map(profitStagingDetailTemp->profitStagingDetailTemp.getMustAmt()).reduce(BigDecimal::add).get();//((ProfitStagingDetail)pageInfo.getRows().get(0)).getMustAmt();
        }
        return null;
    }

    /***
     * @Description: 分期处理
     * @Param:  profitAmt 当前分润
     * @Param:  profitDeductionTemp 扣款对象
     * @Author: zhaodw
     * @Date: 2018/8/8
     */
    private void updateStagingDetail(BigDecimal profitAmt, ProfitDeduction profitDeductionTemp) {
        // 获取代理商本月分期
        ProfitStagingDetail profitStagingDetail = new ProfitStagingDetail();
        profitStagingDetail.setDeductionDate(LocalDate.now().toString().substring(0,7));
        profitStagingDetail.setSourceId(profitDeductionTemp.getId());
        PageInfo pageInfo = stagingServiceImpl.getStagingDetailList(profitStagingDetail,null);
        if (pageInfo != null && pageInfo.getTotal()>0) {
            List<ProfitStagingDetail> profitStagingDetailList =  pageInfo.getRows();
            for (ProfitStagingDetail profitStagingDetailTemp : profitStagingDetailList) {
                if (profitAmt.doubleValue() < profitStagingDetailTemp.getMustAmt().doubleValue() ) {
                    profitStagingDetailTemp.setRealAmt(profitAmt);
                }else {
                    profitStagingDetailTemp.setRealAmt(profitStagingDetailTemp.getMustAmt());
                }
                profitStagingDetailTemp.setStatus(StagingDetailStatus.Y.getStatus());
                stagingServiceImpl.editStagingDetail(profitStagingDetailTemp);
            }
        }
    }

    /***
    * @Description: 一般扣款处理
    * @Param:  resultAmt 扣减后金额
    * @Author: zhaodw
    * @Date: 2018/8/8
    */
    private void generalDeal(BigDecimal profitAmt,BigDecimal resultAmt, ProfitDeduction profitDeductionTemp) {
        profitDeductionTemp.setDeductionStatus("1");//已扣款
        if (resultAmt.doubleValue() >= 0 && profitAmt.doubleValue() > 0) {
            profitDeductionTemp.setActualDeductionAmt(profitDeductionTemp.getMustDeductionAmt());
            profitDeductionTemp.setNotDeductionAmt(BigDecimal.ZERO);
        }else {
            if (resultAmt.doubleValue() < 0) {
                profitDeductionTemp.setActualDeductionAmt(profitAmt);
                profitDeductionTemp.setNotDeductionAmt(resultAmt.abs());
            }else{
                profitDeductionTemp.setActualDeductionAmt(BigDecimal.ZERO);
                profitDeductionTemp.setNotDeductionAmt(profitDeductionTemp.getMustDeductionAmt());
            }
            if ("03".equals(profitDeductionTemp.getDeductionType())) {
                // 将当期扣款对象存入历史
                createHisDeduction(profitDeductionTemp);
                // 更新当期未下期扣款
                profitDeductionTemp.setDeductionDate(LocalDate.now().toString().substring(0, 7));
                profitDeductionTemp.setUpperNotDeductionAmt(profitDeductionTemp.getNotDeductionAmt());//上月未扣足=未扣足
                profitDeductionTemp.setMustDeductionAmt(profitDeductionTemp.getNotDeductionAmt());
                profitDeductionTemp.setSumDeductionAmt(profitDeductionTemp.getNotDeductionAmt());
                profitDeductionTemp.setNotDeductionAmt(BigDecimal.ZERO); // 未扣足请0
                profitDeductionTemp.setActualDeductionAmt(BigDecimal.ZERO);// 实扣清0
                profitDeductionTemp.setAddDeductionAmt(BigDecimal.ZERO);
                profitDeductionTemp.setCreateDateTime(new Date());
                profitDeductionTemp.setDeductionStatus("0");//未扣款
            }
        }
        profitDeductionMapper.updateByPrimaryKeySelective(profitDeductionTemp);
    }

    /***
    * @Description: 创建一条扣款历史数据
    * @Param:  profitDeductionTemp 当前对象
    * @Author: zhaodw
    * @Date: 2018/8/8
    */
    private void createHisDeduction(ProfitDeduction profitDeductionTemp) {
        // 创建一条历史扣款记录
        ProfitDeduction deduction = new ProfitDeduction();
        BeanUtils.copy(profitDeductionTemp, deduction);
        deduction.setId(idService.genId(TabId.P_DEDUCTION));
        deduction.setStagingStatus("5");
        profitDeductionMapper.insert(deduction);
    }

    @Override
    public BigDecimal totalBuckleByMonth(ProfitDeduction profitDeduction) {
        return profitDeductionMapper.totalBuckleByMonth(profitDeduction);
    }

    @Override
    public BigDecimal getNotDeductionSum(String agentId) {
        // 获取代理商未扣足分期总金额
        Map<String, Object> param = new HashMap<>(2);
        param.put("agentPid", agentId);
        BigDecimal stagNotDeductionSumAmt = stagingServiceImpl.getNotDeductionAmt(param);

        // 获取本月新增
        String deductionDate = LocalDate.now().plusMonths(-1).toString().substring(0,7);
        ProfitDeduction profitDeduction = new ProfitDeduction();
        profitDeduction.setAgentPid(agentId);
        profitDeduction.setDeductionDate(deductionDate);
        BigDecimal deductionAmt = profitDeductionMapper.getCurrentDeductionAmtSum(profitDeduction);
        return (stagNotDeductionSumAmt==null?BigDecimal.ZERO:stagNotDeductionSumAmt).add((deductionAmt==null?BigDecimal.ZERO:deductionAmt));
    }
}
