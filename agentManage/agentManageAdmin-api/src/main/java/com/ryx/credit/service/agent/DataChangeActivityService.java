package com.ryx.credit.service.agent;

import com.ryx.credit.common.util.ResultVO;

/**
 * Created by cx on 2018/6/6.
 * 数据修改申请服务类
 */
public interface DataChangeActivityService {

    /**
     *
     * @param dataChangeId
     * @return
     */
    public ResultVO startDataChangeActivity(String dataChangeId,String userId);


}
