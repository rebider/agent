package com.ryx.credit.service.profit;

import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;

/**
 * @Author: zhaodw
 * @Description: 机构服务
 * @Date: 20:19 2018/7/31
 */
public interface PosOrganDataService {
    
    /*** 
    * @Description: 获取所有子集
    * @return:  结果
    * @Author: zhaodw 
    * @Date: 2018/8/10 
    */ 
    AgentResult getAllChild(String orgId) throws ProcessException;


    /***
     * @Description: 获取一级代理商
     * @return:  结果
     * @Author: zhaodw
     * @Date: 2018/8/10
     */
    AgentResult getFirstAgent(String orgId) throws ProcessException;
}
