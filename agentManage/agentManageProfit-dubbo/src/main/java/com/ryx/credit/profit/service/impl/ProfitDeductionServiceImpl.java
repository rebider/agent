package com.ryx.credit.profit.service.impl;

import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.dao.ProfitDeductionMapper;
import com.ryx.credit.profit.enums.DeductionStatus;
import com.ryx.credit.profit.enums.DeductionType;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    public void batchInsertOtherDeduction(List<List<Object>> deductionist) {
        if(deductionist != null && deductionist.size() > 0 ) {
            Map<String, String> deductionIdMap = new HashMap<>();
            Map<String, String> agentNameMap = new HashMap<>();
            Map<String, BigDecimal> agentSumAmtMap = new HashMap<>();
            deductionist.stream().filter(list->list.get(1) != null && list.get(3) != null).forEach(list->{
                if (deductionIdMap.containsKey(list.get(1))) {
                    agentSumAmtMap.put(list.get(1).toString(), agentSumAmtMap.get(list.get(1).toString()).add(new BigDecimal(list.get(3).toString())));
                }else{
                    agentSumAmtMap.put(list.get(1).toString(), new BigDecimal(list.get(3).toString()));
                    deductionIdMap.put(list.get(1).toString(),idService.genIdInTran(TabId.P_STAGING_DETAIL) );
                    agentNameMap.put(list.get(1).toString(),list.get(0).toString());
                }
                insertDetail( deductionIdMap.get(list.get(1).toString()), new BigDecimal(list.get(3).toString()), (String)list.get(2));
            });

            Set<String> keys = agentSumAmtMap.keySet();
            for (String key : keys) {
                ProfitDeduction deduction = new ProfitDeduction();
                deduction.setDeductionType(DeductionType.OTHER.getType());
                deduction.setAddDeductionAmt(agentSumAmtMap.get(key));
                deduction.setSumDeductionAmt(agentSumAmtMap.get(key));
                deduction.setMustDeductionAmt(agentSumAmtMap.get(key));
                deduction.setStagingStatus(DeductionStatus.NOT_APPLIED.getStatus());
                deduction.setId(deductionIdMap.get(key));
                deduction.setAgentId(key);
                deduction.setAgentName(agentNameMap.get(key));
                deduction.setDeductionDate(LocalDate.now().plusMonths(-1).format(DateTimeFormatter.ISO_DATE).substring(0,7));
                this.insert(deduction);
                deduction = null;
            }
        }
    }

    /*** 
    * @Description: 添加明细
    * @Param:  sourceId 原始对象id
    * @Param:  mustAmt 应扣
    * @Param:  remark 扣款类型
    * @Author: zhaodw
    * @Date: 2018/7/31 
    */ 
    private void insertDetail(String sourceId, BigDecimal mustAmt, String remark) {
        ImportDeductionDetail detail = new ImportDeductionDetail();
        detail.setId(idService.genId(TabId.P_IMPORT_DEDUCTION_DETAIL));
        detail.setSourceId(sourceId);
        detail.setMustAmt(mustAmt);
        detail.setDeductionDate(LocalDate.now().plusMonths(-1).format(DateTimeFormatter.ISO_DATE).substring(0,7));
        detail.setRemark(remark);
        importDeductionDetailServiceImpl.insetImportDeductionDetail(detail);
        detail = null;
    }

}
