package com.ryx.credit.profit.service.impl;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.dao.ProfitSettleErrLsMapper;
import com.ryx.credit.profit.pojo.ProfitSettleErrLs;
import com.ryx.credit.profit.pojo.ProfitSettleErrLsExample;
import com.ryx.credit.profit.service.ProfitSettleErrLsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhaodw
 * @Title: ProfitSettleErrLsServiceImpl
 * @ProjectName agentManage
 * @Description: TODO
 * @date 2018/7/2616:46
 */
@Service("profitSettleErrLsServiceImpl")
public class ProfitSettleErrLsServiceImpl implements ProfitSettleErrLsService {

    @Autowired
    private ProfitSettleErrLsMapper profitSettleErrLsMapper;

    @Override
    public PageInfo getProfitSettleErrList(ProfitSettleErrLs settleErr, Page page) {
        ProfitSettleErrLsExample example = new ProfitSettleErrLsExample();
        example.setPage(page);
        ProfitSettleErrLsExample.Criteria criteria = example.createCriteria();
        // 月份按开始到结束查询
        if (StringUtils.isNotBlank(settleErr.getTranDateStart()) && StringUtils.isNotBlank(settleErr.getTranDateEnd()))
        {
            criteria.andTranDateBetween(settleErr.getTranDateStart(),settleErr.getTranDateEnd());
        }else if (StringUtils.isNotBlank(settleErr.getTranDateStart())){
            criteria.andTranDateEqualTo(settleErr.getTranDateStart());
        }else if (StringUtils.isNotBlank(settleErr.getTranDateEnd())){
            criteria.andTranDateEqualTo(settleErr.getTranDateEnd());
        }

        if (StringUtils.isNotBlank(settleErr.getSourceId())) {
            criteria.andSourceIdEqualTo(settleErr.getSourceId());
        }
        List<ProfitSettleErrLs> settleErrs = profitSettleErrLsMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(settleErrs);
        pageInfo.setTotal(profitSettleErrLsMapper.countByExample(example));
        return pageInfo;
    }

    @Override
    public void inset(ProfitSettleErrLs insert) {
         profitSettleErrLsMapper.insert(insert);

    }

}
