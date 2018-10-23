package com.ryx.credit.profit.service.impl;

import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.profit.dao.ProfitAdjustMMapper;
import com.ryx.credit.profit.pojo.ProfitAdjustM;
import com.ryx.credit.profit.service.PAdjustMService;
import com.ryx.credit.service.dict.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("pAdjustMService")
public class PAdjustMServiceImpl implements PAdjustMService {
    Logger logger = LoggerFactory.getLogger(PAdjustMServiceImpl.class);
    @Autowired
    private ProfitAdjustMMapper profitAdjustMMapper;
    @Autowired
    private IdService idService;
    @Override
    public int insertSelective(ProfitAdjustM profitAdjustM) {
        profitAdjustM.setId(idService.genId(TabId.PROFIT_ADJUST_M));
        return profitAdjustMMapper.insertSelective(profitAdjustM);
    }
}
