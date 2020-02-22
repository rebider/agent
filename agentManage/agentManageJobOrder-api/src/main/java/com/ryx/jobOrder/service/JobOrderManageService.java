package com.ryx.jobOrder.service;

import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.jobOrder.pojo.JoKeyManage;

/**
 * 关键词管理
 */
public interface JobOrderManageService {

    PageInfo keywordList(Page page, JoKeyManage joKeyManage);

    boolean keywordAdd(JoKeyManage joKeyManage) throws Exception;

    AgentResult keywordDelete(String id);

    ResultVO keywordEdit(JoKeyManage joKeyManage) throws Exception;

    JoKeyManage queryKeywordDialog(String id);


}
