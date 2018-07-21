package com.ryx.credit.service.dict;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.PayComp;
import java.util.List;

/**
 * @author yangmx
 * @desc 打款公司基础数据维护服务
 */
public interface PayCompService {

    /**
     * 获取全部打款公司列表
     * @param page
     * @param payComp
     * @return
     */
    PageInfo getPayCompList(Page page, PayComp payComp);

    /**
     * 根据ID修改一条打款公司信息
     * @param id
     * @return
     */
    PayComp  getPayCompById(String id);

    /**
     * 获取有效成功状态的打款公司
     * @return
     */
    List<PayComp> getPayCompSuccessList();

    /**
     * 新增打款公司
     * @param payComp
     */
    void insertPayComp(PayComp payComp);

    /**
     * 修改打款公司信息
     * @param payComp
     */
    void updatePayComp(PayComp payComp);
}
