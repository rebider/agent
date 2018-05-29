package com.ryx.credit.service.impl.agent;

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
@Service("ApaycompService")
public class ApaycompServiceImpl implements com.ryx.credit.service.agent.ApaycompService {

    @Autowired
    private PayCompMapper payCompMapper;


    /**
     * 打款公司列表
     * @return
     */
    @Override
    public List<PayComp> compList(){
        PayCompExample example = new PayCompExample();
        example.or().andStatusEqualTo(Status.STATUS_1.status);
        return payCompMapper.selectByExample(example);
    }

}
