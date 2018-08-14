package com.ryx.credit.profit.service.impl;/**
 * @Auther: zhaodw
 * @Date: 2018/8/6 14:10
 * @Description:
 */

import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.dao.OrganTranMonthDetailMapper;
import com.ryx.credit.profit.pojo.ImportDeductionDetail;
import com.ryx.credit.profit.pojo.ImportDeductionDetailExample;
import com.ryx.credit.profit.pojo.OrganTranMonthDetail;
import com.ryx.credit.profit.pojo.OrganTranMonthDetailExample;
import com.ryx.credit.profit.service.OrganTranMonthDetailService;
import com.ryx.credit.service.dict.IdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 代理商月交易明细业务接口实现
 * @author zhaodw
 * @create 2018/8/6
 * @since 1.0.0
 */
@Service("organTranMonthDetailServiceImpl")
public class OrganTranMonthDetailServiceImpl implements OrganTranMonthDetailService {

    @Autowired
    private OrganTranMonthDetailMapper organTranMonthDetailMapper;

    @Autowired
    private IdService idService;

    @Override
    public void insert(OrganTranMonthDetail organTranMonthDetail) {
        if (organTranMonthDetail.getId()== null) {
            organTranMonthDetail.setId(idService.genId(TabId.P_ORGAN_TRAN_MONTH_DETAIL));
        }
        organTranMonthDetailMapper.insert(organTranMonthDetail);
    }

    @Override
    public void update(OrganTranMonthDetail organTranMonthDetail) {
        organTranMonthDetailMapper.updateByPrimaryKeySelective(organTranMonthDetail);
    }

    @Override
    public List<OrganTranMonthDetail> getOrganTranMonthDetailList(OrganTranMonthDetail organTranMonthDetail) {
        OrganTranMonthDetailExample example = new OrganTranMonthDetailExample();
        OrganTranMonthDetailExample.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotBlank(organTranMonthDetail.getProfitDate())){
            criteria.andProfitDateEqualTo(organTranMonthDetail.getProfitDate());
        }
        if (StringUtils.isNotBlank(organTranMonthDetail.getAgentType())){
            if (organTranMonthDetail.getAgentType().contains(",")) {
                criteria.andAgentIdIn(Arrays.asList(organTranMonthDetail.getAgentType().split(",")));
            }else {
                criteria.andAgentTypeEqualTo(organTranMonthDetail.getAgentType());
            }
        }
        if (StringUtils.isNotBlank(organTranMonthDetail.getProfitId())){
            criteria.andProfitIdEqualTo(organTranMonthDetail.getProfitId());
        }
        if (StringUtils.isNotBlank(organTranMonthDetail.getAgentId())){
            criteria.andAgentIdEqualTo(organTranMonthDetail.getAgentId());
        }
        return organTranMonthDetailMapper.selectByExample(example);

    }

    @Override
    public OrganTranMonthDetail getChildSumTranAmt(Map<String, Object> param) {
        return organTranMonthDetailMapper.getChildSumTranAmt(param);
    }
}
