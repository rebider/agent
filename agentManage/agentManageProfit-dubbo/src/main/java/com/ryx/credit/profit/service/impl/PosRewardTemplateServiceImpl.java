package com.ryx.credit.profit.service.impl;

import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.dao.PosRewardTemplateMapper;
import com.ryx.credit.profit.pojo.PosRewardTemplate;
import com.ryx.credit.profit.service.PosRewardTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author yangmx
 * @desc pos奖励通用模板API
 */
@Service("posRewardTemplateService")
public class PosRewardTemplateServiceImpl implements PosRewardTemplateService{

    @Autowired
    private PosRewardTemplateMapper posRewardTemplateMapper;

    @Override
    public PageInfo getPosRewardTemplateList() {
        long count = posRewardTemplateMapper.countByExample(null);
        List<PosRewardTemplate> posRewardTemplates = posRewardTemplateMapper.selectByExample(null);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setTotal((int) count);
        pageInfo.setRows(posRewardTemplates);
        return pageInfo;
    }

    @Override
    public PosRewardTemplate getPosRewardTemplate(String id) {
        return posRewardTemplateMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updatePosRewardTemplate(PosRewardTemplate posRewardTemplate) {
        posRewardTemplateMapper.updateByPrimaryKeySelective(posRewardTemplate);
    }

    @Override
    public BigDecimal computePosReward(BigDecimal tranTotal) {
        if(tranTotal != null){
            BigDecimal finalTranTotal = tranTotal.divide(new BigDecimal(10000));
            List<PosRewardTemplate> posRewardTemplates = posRewardTemplateMapper.selectByExample(null);
            for (PosRewardTemplate posRewardTemplate : posRewardTemplates) {
                if(posRewardTemplate.getTranTotal().contains("~")) {
                    String[] spl = posRewardTemplate.getTranTotal().trim().split("~");
                    if (finalTranTotal.compareTo(new BigDecimal(spl[0])) >= 0 && finalTranTotal.compareTo(new BigDecimal(spl[1])) <= 0) {
                        return posRewardTemplate.getProportion();
                    }
                }
            }
        }
        return BigDecimal.ZERO;
    }
}