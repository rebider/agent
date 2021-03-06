package com.ryx.credit.profit.service;

import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.profit.pojo.PTaxAdjust;
import com.ryx.credit.profit.pojo.PTaxAdjustExample;
import com.ryx.credit.profit.pojo.ProfitDay;
import com.ryx.credit.profit.pojo.ProfitDayExample;

import javax.print.DocFlavor;
import java.util.List;
import java.util.Map;


/**
 * IPTaxAdjustService
 * Created by IntelliJ IDEA.
 *
 * @Author Wang y
 * @Date 2017/7/31
 * @Time: 16:42
 * To change this template use File | Settings | File Templates.
 */

public interface IPTaxAdjustService {

    PageInfo PTaxAdjustList(PTaxAdjust record, Page page, Map<String,Object> map);

    PTaxAdjust selectByAgentId(String agentId);

    /**
     * 新增并启动一个税点调整审批
     * @param record
     * @return
     */
    ResultVO posTaxEnterIn(PTaxAdjust record)throws ProcessException;

    void completeTaskEnterActivity(String insid, String status);

    AgentResult approvalTask(AgentVo agentVo, String userId) throws ProcessException;

    /**
     * 获取税点调整信息
     * @param id 不能为空
     * @return POS奖励信息
     */
    PTaxAdjust getPosTaxById(String id);

    /**
     * 根据ID 查询数据信息
     * @param id
     * @return
     */
    public List<PTaxAdjust> getPosTaxByDataId(String id);

    /**
     * 审批退回，修改申请信息
     * @param pTaxAdjust
     * @throws Exception
     */
    public void editPosTaxRegect(PTaxAdjust pTaxAdjust)throws Exception;
}
