package com.ryx.internet.service;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.vo.OCashReceivablesVo;
import com.ryx.internet.pojo.OInternetRenew;
import com.ryx.internet.pojo.OInternetRenewDetail;

import java.util.List;

/***
 * @Author liudh
 * @Description //TODO 
 * @Date 2019/7/1 19:15
 * @Param
 * @return
 **/
public interface OInternetRenewService {

    PageInfo internetRenewList(OInternetRenew internetRenew, Page page);

    PageInfo internetRenewDetailList(OInternetRenewDetail internetRenewDetail, Page page);

    AgentResult saveAndApprove(OInternetRenew internetRenew, List<String> iccids, String cUser,
                               List<OCashReceivablesVo> oCashReceivablesVoList)throws MessageException;

    OInternetRenew selectByPrimaryKey(String id);
}
