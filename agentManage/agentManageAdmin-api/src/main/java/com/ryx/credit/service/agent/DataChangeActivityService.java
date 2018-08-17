package com.ryx.credit.service.agent;

import com.ryx.credit.common.util.ResultVO;

import java.math.BigDecimal;

/**
 * Created by cx on 2018/6/6.
 * 数据修改申请服务类
 */
public interface DataChangeActivityService {

    /**
     * 启动数据变更审批服务
     * @param dataChangeId
     * @return
     */
    public ResultVO startDataChangeActivity(String dataChangeId,String userId);

    /**
     * 收款账户修改 审批完成处理
     * @param proIns
     * @param agStatus
     * @return
     */
    public ResultVO compressColInfoDataChangeActivity(String proIns, String agStatus)throws Exception;




}
