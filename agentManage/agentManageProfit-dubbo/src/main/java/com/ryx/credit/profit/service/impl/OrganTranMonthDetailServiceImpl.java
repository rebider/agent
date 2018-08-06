package com.ryx.credit.profit.service.impl;/**
 * @Auther: zhaodw
 * @Date: 2018/8/6 14:10
 * @Description:
 */

import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.profit.dao.OrganTranMonthDetailMapper;
import com.ryx.credit.profit.pojo.OrganTranMonthDetail;
import com.ryx.credit.profit.service.OrganTranMonthDetailService;
import com.ryx.credit.service.dict.IdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    }
}
