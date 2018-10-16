package com.ryx.credit.profit.service;

import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.profit.pojo.PAgentMerge;

import java.util.Map;

/**
 * 代理商合并Service
 * @version V1.0
 * @Description:
 * @author: LiuQY
 * @date: 2018/9/27 09:30
 */
public interface IProfitAgentMergerService {
    /**代理商合并查询*/
    PageInfo getProfitAgentMergeList(Map<String, Object> param, PageInfo pageInfo);
    /**代理商合并提交存库*/
    void agentMergeTaxEnterIn(PAgentMerge record,Long  userId)throws ProcessException;
    /**代理商提交初*/
    //PAgentMerge getMergeById(String var1);
}
