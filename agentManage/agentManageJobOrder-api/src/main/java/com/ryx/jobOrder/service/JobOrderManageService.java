package com.ryx.jobOrder.service;

import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.pojo.admin.order.Organization;
import com.ryx.jobOrder.pojo.JoCustomKey;
import com.ryx.jobOrder.pojo.JoKeyManage;

import java.util.List;
import java.util.Map;

/**
 * 关键词管理
 */
public interface JobOrderManageService {

    PageInfo keywordList(Page page, JoKeyManage joKeyManage);

    boolean keywordAdd(JoKeyManage joKeyManage, String userId) throws Exception;

    AgentResult keywordDelete(String id);

    ResultVO keywordEdit(JoKeyManage joKeyManage) throws Exception;

    JoKeyManage queryKeywordDialog(String id);

    List<JoKeyManage> queryKeywordByJoStatus(JoKeyManage joKeyManage);

    /**
     * 查询一级二级列表
     */
    List selectLevel();

    public List selectCustomListBySedType(JoCustomKey joCustomKey);

    public List selectCustomListMapBySedType(JoCustomKey joCustomKey);


    /**
     * 根据工单类型查询关键词(二级)
     * @param joCustomKey
     * @return
     */
    List<Map> selectKeyWord(JoCustomKey joCustomKey);

    PageInfo joCustomKeyList(Page page, Map map);
}