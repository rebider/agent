package com.ryx.credit.profit.service;

import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.pojo.PosRewardTemplate;

import java.math.BigDecimal;

/**
 * @author yangmx
 * @desc pos奖励通用模板API
 */
public interface PosRewardTemplateService {

    /**
     * 查询通用模板列表
     * @return
     */
    public PageInfo getPosRewardTemplateList();

    /**
     * 查询一条模板数据
     * @param id
     * @return
     */
    public PosRewardTemplate getPosRewardTemplate(String id);

    /**
     * 修改模板
     * @param posRewardTemplate
     */
    public void updatePosRewardTemplate(PosRewardTemplate posRewardTemplate);

    /**
     * 计算POS分润奖励
     * @param tranTotal 交易总额
     * @return POS奖励比例
     */
    public BigDecimal computePosReward(BigDecimal tranTotal);
}
