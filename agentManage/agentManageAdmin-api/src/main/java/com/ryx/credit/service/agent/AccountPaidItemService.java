package com.ryx.credit.service.agent;

import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.Capital;
import com.ryx.credit.pojo.admin.vo.CapitalVo;

import java.util.List;

/**
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/5/23 14:23
 */
public interface AccountPaidItemService {

    AgentResult insertAccountPaid(Capital capital, List<String> fileIdList, String cUser)throws Exception;

    int removeAccountPaid(String id);

    public ResultVO updateListCapitalVo(List<CapitalVo> capitalVoList, Agent agent)throws Exception;
}
