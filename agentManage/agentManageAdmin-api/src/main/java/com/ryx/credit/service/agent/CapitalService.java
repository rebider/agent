package com.ryx.credit.service.agent;

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

    PageInfo getCapitalSummaryList(Map<String,Object> param, PageInfo pageInfo);

    PageInfo queryCapitalList(Capital capital, Page page, String dataRole, Long userId);

    void disposeCapital(List<Capital> capitals, BigDecimal amt, String srcId, String cUser,
                        String agentId, String agentName,String remark)throws Exception;
}
