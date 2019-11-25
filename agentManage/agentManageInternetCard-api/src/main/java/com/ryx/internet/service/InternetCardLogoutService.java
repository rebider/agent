package com.ryx.internet.service;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.internet.pojo.InternetLogout;
import com.ryx.internet.pojo.InternetLogoutDetail;

import java.math.BigDecimal;
import java.util.List;

/***
 * @Author liudh
 * @Description //TODO 
 * @Date 2019/11/21 10:15
 * @Param
 * @return
 **/
public interface InternetCardLogoutService {

    PageInfo internetCardLogoutList(InternetLogout internetLogout, Page page, String agentId, Long userId);

    PageInfo internetCardLogoutDetailList(InternetLogoutDetail internetLogoutDetail, Page page, String agentId, Long userId);

    Integer queryInternetLogoutDetailCount(InternetLogoutDetail internetLogoutDetail,String agentId,Long userId);

    AgentResult saveAndApprove(InternetLogout internetLogout, List<String> iccids, String cUser)throws MessageException;

    AgentResult approvalTask(AgentVo agentVo, String userId) throws Exception;

    AgentResult compressCompensateActivity(String proIns, BigDecimal agStatus)throws Exception;

}
