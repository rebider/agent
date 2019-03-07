package com.ryx.credit.service.order;

import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.pojo.admin.vo.OldOrderReturnSubmitProVo;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 作者：cx
 * 时间：2019/3/6
 * 描述：
 */
public interface OldOrderReturnService {
    AgentResult saveOldReturnOrder(String userId, String agentId, String remark, List<OldOrderReturnSubmitProVo> oldOrderReturnSubmitProVoList)throws Exception;
}
