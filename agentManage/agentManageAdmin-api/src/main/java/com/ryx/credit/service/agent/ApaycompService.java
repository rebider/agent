package com.ryx.credit.service.agent;

import com.ryx.credit.pojo.admin.agent.PayComp;

import java.util.List;

/**
 * Created by cx on 2018/5/29.
 */
public interface ApaycompService {
    /**
     * 打款公司列表
     * @return
     */
    List<PayComp> compList();

    /**
     * 收款款公司列表
     * @return
     */
    List<PayComp> recCompList();

    PayComp selectById(String id);
}
