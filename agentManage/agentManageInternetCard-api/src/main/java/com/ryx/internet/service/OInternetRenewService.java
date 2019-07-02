package com.ryx.internet.service;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.pojo.admin.vo.OCashReceivablesVo;
import com.ryx.internet.pojo.OInternetRenew;

import java.util.List;

/***
 * @Author liudh
 * @Description //TODO 
 * @Date 2019/7/1 19:15
 * @Param
 * @return
 **/
public interface OInternetRenewService {

    AgentResult saveAndApprove(OInternetRenew internetRenew, List<String> iccids, String cUser,
                               List<OCashReceivablesVo> oCashReceivablesVoList)throws MessageException;
}
