package com.ryx.credit.service.agent;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.pojo.admin.vo.AgentColinfoVo;

import java.util.List;
import java.util.Map;

/**
 * 结算卡管理
 *  Created by Chen Qiutian on 2019/7/31.
 */
public interface AgentDebitCardService {

    PageInfo getDebitCardList(Map<String,String> map, Page page);

    List<Map<String,Object>> exports(Map<String,String> map);

    List<Map<String,String>> getBusInfoById(String id);

    Map<String,String> getColAndAgentById(String id);

    void updateSuggestStatusById(String id,String statu) throws MessageException;

    PageInfo getNoticeList(String orgId,Page page);


}
