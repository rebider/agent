package com.ryx.credit.service.agent;

import com.ryx.credit.common.enumc.PayType;
import com.ryx.credit.common.enumc.SrcType;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.Capital;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author Lihl
 * @Date 2018/10/11
 * 保证金
 */
public interface CapitalService {

    List<Capital> queryCapital(String agentId);

    List<Capital> queryCapital(String agentId,String cPayType);

    List<Capital> queryByAgentId(String agentId);

    PageInfo getCapitalSummaryList(Map<String,Object> param, PageInfo pageInfo,String dataRole,Long userId);

    PageInfo queryCapitalList(Capital capital, Page page, String dataRole, Long userId);

    void disposeCapital(String capitalType, BigDecimal amt, String srcId, String cUser,
                        String agentId, String agentName,String remark,SrcType srcType,PayType payType)throws Exception;

    void approvedDeduct(String srcId, SrcType srcType, String uUser)throws Exception;

    void refuseUnfreeze(String srcId,SrcType srcType,String uUser)throws Exception;
}
