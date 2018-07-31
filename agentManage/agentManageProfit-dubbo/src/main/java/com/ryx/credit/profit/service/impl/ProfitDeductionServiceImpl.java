package com.ryx.credit.profit.service.impl;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.dao.ProfitDeductionMapper;
import com.ryx.credit.profit.exceptions.StagingException;
import com.ryx.credit.profit.pojo.ProfitDeduction;
import com.ryx.credit.profit.pojo.ProfitDeductionExample;
import com.ryx.credit.profit.service.ProfitDeductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void updateStagingStatusById(String id, String status) {
        if (StringUtils.isNotBlank(id)) {
            ProfitDeduction deduction = new ProfitDeduction();
            deduction.setId(id);
            deduction.setStagingStatus(status);
             profitDeductionMapper.updateByPrimaryKeySelective(deduction);
        }else{
            throw new StagingException("修改状态失败。");
        }
    }
}
