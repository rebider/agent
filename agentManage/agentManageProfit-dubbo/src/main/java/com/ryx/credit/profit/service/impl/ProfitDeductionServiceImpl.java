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
import com.ryx.credit.profit.pojo.ProfitDeduction;
import com.ryx.credit.profit.pojo.ProfitDeductionExample;
import com.ryx.credit.profit.pojo.ProfitStagingDetail;
import com.ryx.credit.profit.service.ProfitDeductionService;
import com.ryx.credit.profit.service.StagingService;
import com.ryx.credit.service.dict.IdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
        if (StringUtils.isNotBlank(profitDeduction.getDeductionType())){
            criteria.andDeductionTypeEqualTo(profitDeduction.getDeductionType());
        }
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
            deductionist.stream().filter(list->list.get(0) != null && list.get(4) != null).forEach(list->{
                insertDeduction(list, userId);
            });


        }
    }

    private void insertDeduction(List list, String userId) {
        BigDecimal amt = list.get(4)==null?BigDecimal.ZERO:new BigDecimal(list.get(4).toString());
        ProfitDeduction deduction = new ProfitDeduction();
        deduction.setDeductionType(DeductionType.OTHER.getType());
        deduction.setAddDeductionAmt(amt);
        deduction.setSumDeductionAmt(amt);
        deduction.setMustDeductionAmt(amt);
        deduction.setStagingStatus(DeductionStatus.NOT_APPLIED.getStatus());
        deduction.setId(idService.genIdInTran(TabId.P_DEDUCTION) );
        deduction.setAgentId(list.get(0).toString());
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
        List<ProfitDeduction> profitDeductions = profitDeductionMapper.selectByExample(example);
        if(profitDeductions != null && !profitDeductions.isEmpty()){
            return profitDeductions;
        }
        return null;
    }

    @Override
    public BigDecimal deductionAmt(BigDecimal profitAmt, String deductionType, String agentId) throws DeductionException {
        String deductionDate = LocalDate.now().plusMonths(-1).toString().substring(0,7);
        // 其它扣款
        if ("03".equals(deductionType)) {
            return deductionOther(profitAmt, agentId, deductionDate);
        }
        // 退单扣款
        else  if ("01".equals(deductionType)) {

        }
        return BigDecimal.ZERO;
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
    private  BigDecimal deductionOther(BigDecimal profitAmt, String agentId, String deductionDate) {
        // 获取代理商所有其它扣款信息
        ProfitDeduction profitDeduction = new ProfitDeduction();
        profitDeduction.setAgentId(agentId);
        profitDeduction.setDeductionType(DeductionType.OTHER.getType());
        profitDeduction.setDeductionDate(deductionDate);
        List<ProfitDeduction> deductionList = this.getProfitDeduction(profitDeduction);

        BigDecimal result =  BigDecimal.ZERO;
        if (deductionList != null && deductionList.size() > 0) {
            BigDecimal currentProfit = null;
            for (ProfitDeduction profitDeductionTemp : deductionList){
                if (profitAmt.doubleValue() > 0) {
                    currentProfit = profitAmt;
                    profitAmt = profitAmt.subtract(profitDeductionTemp.getMustDeductionAmt());
                    if (profitAmt.doubleValue() > 0) {
                        result = result.add(profitDeductionTemp.getMustDeductionAmt());
                    }else {
                        result = result.add(currentProfit);
                    }
                }else  if (profitAmt.doubleValue() < 0){
                    profitAmt = BigDecimal.ZERO;
                }

                // 申请分期
                if ("3".equals(profitDeductionTemp.getStagingStatus())) {
                    stagingDeal(currentProfit, profitAmt, profitDeductionTemp);
                }else{
                    generalDeal(currentProfit, profitAmt,profitDeductionTemp);
                }
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
        profitDeductionTemp.setStagingStatus(DeductionStatus.YES_WITHHOLD.getStatus());
        if (resultAmt.doubleValue() > 0) {
            profitDeductionTemp.setActualDeductionAmt(profitDeductionTemp.getMustDeductionAmt());
        }else{
            if (resultAmt.doubleValue() < 0) {
                profitDeductionTemp.setActualDeductionAmt(profitAmt);
                profitDeductionTemp.setNotDeductionAmt(resultAmt.abs());
            }else{
                profitDeductionTemp.setActualDeductionAmt(BigDecimal.ZERO);
                profitDeductionTemp.setNotDeductionAmt(profitDeductionTemp.getMustDeductionAmt());
            }
        }
        // 将当期扣款对象存入历史
        createHisDeduction(profitDeductionTemp);

        BigDecimal stagAmt = getNextStagAmt(profitDeductionTemp);
        if (stagAmt != null) {
            profitDeductionTemp.setUpperNotDeductionAmt(profitDeductionTemp.getNotDeductionAmt());//上月未扣足=未扣足+下月分期
        }
        // 更新当期未下期扣款
        profitDeductionTemp.setDeductionDate(LocalDate.now().toString().substring(0,7));
        profitDeductionTemp.setSumDeductionAmt(stagAmt.add(profitDeductionTemp.getUpperNotDeductionAmt()));
        profitDeductionTemp.setMustDeductionAmt(profitDeductionTemp.getSumDeductionAmt());
        profitDeductionTemp.setNotDeductionAmt(BigDecimal.ZERO); // 未扣足请0
        profitDeductionTemp.setActualDeductionAmt(BigDecimal.ZERO);// 实扣清0
        profitDeductionTemp.setAddDeductionAmt(BigDecimal.ZERO);
        profitDeductionTemp.setActualDeductionAmt(BigDecimal.ZERO);
        profitDeductionTemp.setStagingStatus("3");
        updateStagingDetail(profitAmt, profitDeductionTemp);
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
        // 扣足
        profitDeductionTemp.setStagingStatus(DeductionStatus.YES_WITHHOLD.getStatus());
        if (resultAmt.doubleValue() > 0) {
           profitDeductionTemp.setActualDeductionAmt(profitDeductionTemp.getMustDeductionAmt());
        }else{
            if (resultAmt.doubleValue() < 0) {
                profitDeductionTemp.setActualDeductionAmt(profitAmt);
                profitDeductionTemp.setNotDeductionAmt(resultAmt.abs());
            }else{
                profitDeductionTemp.setNotDeductionAmt(profitDeductionTemp.getMustDeductionAmt());
            }
            // 将当期扣款对象存入历史
            createHisDeduction(profitDeductionTemp);
            // 更新当期未下期扣款
            profitDeductionTemp.setDeductionDate(LocalDate.now().toString().substring(0,7));
            profitDeductionTemp.setUpperNotDeductionAmt(profitDeductionTemp.getNotDeductionAmt());//上月未扣足=未扣足
            profitDeductionTemp.setMustDeductionAmt(profitDeductionTemp.getNotDeductionAmt());
            profitDeductionTemp.setSumDeductionAmt(profitDeductionTemp.getNotDeductionAmt());
            profitDeductionTemp.setNotDeductionAmt(BigDecimal.ZERO); // 未扣足请0
            profitDeductionTemp.setActualDeductionAmt(BigDecimal.ZERO);// 实扣清0
            profitDeductionTemp.setAddDeductionAmt(BigDecimal.ZERO);
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
        profitDeductionMapper.insert(deduction);
    }
}
