package com.ryx.credit.profit.service.impl;

import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.dao.ProfitDeducttionDetailMapper;
import com.ryx.credit.profit.pojo.ProfitDeduction;
import com.ryx.credit.profit.pojo.ProfitDeducttionDetail;
import com.ryx.credit.profit.pojo.ProfitDeducttionDetailExample;
import com.ryx.credit.profit.service.ProfitDeducttionDetailService;
import com.ryx.credit.service.dict.IdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author yangmx
 * @desc
 */
@Service("profitDeducttionDetailService")
public class ProfitDeducttionDetailServiceImpl implements ProfitDeducttionDetailService {

    @Autowired
    private ProfitDeducttionDetailMapper profitDeducttionDetailMapper;
    @Autowired
    private IdService idService;

    @Override
    public ProfitDeducttionDetail getProfitDeducttionDetail(ProfitDeduction profitDeduction) {
        ProfitDeducttionDetailExample example = new ProfitDeducttionDetailExample();
        ProfitDeducttionDetailExample.Criteria criteria = example.createCriteria();
        if(profitDeduction != null){
            if(StringUtils.isNotBlank(profitDeduction.getId())){
                criteria.andDeductionIdEqualTo(profitDeduction.getId());
                example.setOrderByClause("CREATE_DATE_TIME DESC");
                List<ProfitDeducttionDetail> list = profitDeducttionDetailMapper.selectByExample(example);
                return list != null && !list.isEmpty() ? list.get(0) : null;
            }
        }
        return null;
    }

    @Override
    public void insertDeducttionDetail(ProfitDeduction profitDeduction){
        ProfitDeducttionDetail profitDeducttionDetail = new ProfitDeducttionDetail();
        profitDeducttionDetail.setAgentId(profitDeduction.getAgentId());
        profitDeducttionDetail.setAgentName(profitDeduction.getAgentName());
        profitDeducttionDetail.setAgentPid(profitDeduction.getAgentId());
        profitDeducttionDetail.setCreateDateTime(new Date());
        profitDeducttionDetail.setDeductionDate(profitDeduction.getDeductionDate());
        profitDeducttionDetail.setDeductionDesc(profitDeduction.getDeductionDesc());
        profitDeducttionDetail.setDeductionId(profitDeduction.getId());
        profitDeducttionDetail.setDeductionType(profitDeduction.getDeductionType());
        profitDeducttionDetail.setId(UUID.randomUUID().toString());
        profitDeducttionDetail.setDeductionAmt(profitDeduction.getActualDeductionAmt());
        profitDeducttionDetail.setMustDeductionAmt(profitDeduction.getMustDeductionAmt());
        profitDeducttionDetail.setNotDeductionAmt(profitDeduction.getNotDeductionAmt());
        profitDeducttionDetail.setParentAgentName(profitDeduction.getParentAgentName());
        profitDeducttionDetail.setParentAgentId(profitDeduction.getParentAgentId());
        profitDeducttionDetail.setParentAgentPid(profitDeduction.getParentAgentId());
        profitDeducttionDetail.setRemark(profitDeduction.getRemark());
        profitDeducttionDetail.setUserId(profitDeduction.getUserId());
        profitDeducttionDetailMapper.insertSelective(profitDeducttionDetail);
    }
}
