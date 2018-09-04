package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.dao.agent.PayCompMapper;
import com.ryx.credit.pojo.admin.agent.PayComp;
import com.ryx.credit.pojo.admin.agent.PayCompExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by cx on 2018/5/29.
 */
@Service("apaycompService")
public class ApaycompServiceImpl implements com.ryx.credit.service.agent.ApaycompService {

    @Autowired
    private PayCompMapper payCompMapper;


    /**
     * 打款公司列表
     *
     * @return
     */
    @Override
    public List<PayComp> compList() {
        PayCompExample example = new PayCompExample();
        example.or().andStatusEqualTo(Status.STATUS_1.status);
        example.setOrderByClause("C_TIME desc");
        return payCompMapper.selectByExample(example);
    }

    /**
     * 收款公司
     *
     * @return
     */
    @Override
    public List<PayComp> recCompList() {
        PayCompExample example = new PayCompExample();
        example.or().andStatusEqualTo(Status.STATUS_2.status);
        example.setOrderByClause("C_TIME desc");
        return payCompMapper.selectByExample(example);
    }

    @Override
    public PayComp selectById(String id) {
        PayCompExample example = new PayCompExample();
        example.or().andStatusEqualTo(Status.STATUS_1.status).andIdEqualTo(id);
        List<PayComp> payComps = payCompMapper.selectByExample(example);
        if (1 != payComps.size()) {
            return null;
        }

        return payComps.get(0);
    }
}
