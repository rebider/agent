package com.ryx.credit.profit.service.impl;

import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.dao.ProfitDeductionMapper;
import com.ryx.credit.profit.enums.DeductionStatus;
import com.ryx.credit.profit.enums.DeductionType;
import com.ryx.credit.profit.exceptions.DeductionException;
import com.ryx.credit.profit.exceptions.StagingException;
import com.ryx.credit.profit.pojo.ImportDeductionDetail;
import com.ryx.credit.profit.pojo.ProfitDeduction;
import com.ryx.credit.profit.pojo.ProfitDeductionExample;
import com.ryx.credit.profit.pojo.ProfitStagingDetail;
import com.ryx.credit.profit.service.ImportDeductionDetailService;
import com.ryx.credit.profit.service.ProfitDeductionService;
import com.ryx.credit.profit.service.StagingService;
import com.ryx.credit.service.dict.IdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    private ImportDeductionDetailService importDeductionDetailServiceImpl;

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
        if (StringUtils.isNotBlank(profitDeduction.getSourceId())){
            criteria.andSourceIdEqualTo(profitDeduction.getSourceId());
        }
        List<ProfitDeduction> profitDeductions = profitDeductionMapper.selectByExample(example);
        if(profitDeductions != null && !profitDeductions.isEmpty()){
            return profitDeductions;
        }
        return null;
    }
}
