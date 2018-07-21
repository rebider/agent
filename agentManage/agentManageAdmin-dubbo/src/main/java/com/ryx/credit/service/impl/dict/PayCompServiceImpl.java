package com.ryx.credit.service.impl.dict;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.PayCompMapper;
import com.ryx.credit.pojo.admin.agent.PayComp;
import com.ryx.credit.pojo.admin.agent.PayCompExample;
import com.ryx.credit.service.dict.PayCompService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author yangmx
 * @desc
 */
@Service("payCompService")
public class PayCompServiceImpl implements PayCompService {
    private static BigDecimal SUCC_STATUS = BigDecimal.ONE;
    @Autowired
    private PayCompMapper payCompMapper;

    @Override
    public PageInfo getPayCompList(Page page, PayComp payComp) {
        PayCompExample example = new PayCompExample();
        PayCompExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(payComp.getComName())){
            criteria.andComNameLike(payComp.getComName());
        }
        if(StringUtils.isNotBlank(payComp.getStatus())){
            criteria.andStatusNotEqualTo(new BigDecimal(payComp.getStatus()));
        }
        int cont = payCompMapper.countByExample(example);
        List<PayComp> list = payCompMapper.selectByExample(example);
        PageInfo info = new PageInfo();
        info.setTotal(cont);
        info.setRows(list);
        return info;
    }

    @Override
    public PayComp getPayCompById(String id) {
        return payCompMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<PayComp> getPayCompSuccessList() {
        PayCompExample example = new PayCompExample();
        PayCompExample.Criteria criteria = example.createCriteria();
        criteria.andStatusNotEqualTo(SUCC_STATUS);
        return payCompMapper.selectByExample(example);
    }

    @Override
    public void insertPayComp(PayComp payComp) {
        if(payComp != null){
            payComp.setcTime(new Date());
            payComp.setcUtime(new Date());
            payCompMapper.insertSelective(payComp);
        }
    }

    @Override
    public void updatePayComp(PayComp payComp) {
        if(payComp != null){
            payComp.setcUtime(new Date());
            payCompMapper.updateByPrimaryKeySelective(payComp);
        }
    }
}
