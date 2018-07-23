package com.ryx.credit.service.order;

import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.order.OPaymentDetail;
import com.ryx.credit.pojo.admin.order.OSupplement;
import com.ryx.credit.pojo.admin.vo.OsupplementVo;

/**
 * 订单补款
 */
public interface OSupplementService {
    /**
     * 查询补款列表
     * @param page
     * @param oSupplement
     * @param time
     * @return
     */
    PageInfo selectAll(Page page, OSupplement oSupplement, String time);

    /**
     * 查询补款详情
     * @param id
     * @return
     */
    public OPaymentDetail selectById(String id);

    /**
     * 添加补款
     */
    public ResultVO supplementSave(OsupplementVo osupplementVo);

    /**
     * 提交审批
     */
    public ResultVO startSuppActivity(String id,String userId)throws ProcessException;

    /**
     * 查询基本信息
     */
    public OSupplement informationQuery(String id);
}
