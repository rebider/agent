package com.ryx.credit.service.order;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;

import com.ryx.credit.pojo.admin.order.OLogistics;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author Lihl
 * @Date 2018/07/23
 * 排单管理：物流信息
 */
public interface OLogisticsService {

    PageInfo getOLogisticsList(Map<String, Object> param, PageInfo pageInfo);

    List<Map<String, Object>> getLogisticsBySn(String startSn, String endSn, String agentId) throws ProcessException;

    ResultVO insertLogisticsDetail(String startSn, String endSn, Integer begins, Integer finish,String logisticsId, String cUser, String planId) throws MessageException;

    ResultVO updateLogisticsDetail(List<String> idList,String logisticsId, String cUser, String planId) throws MessageException;

    List<String> addList(List<List<Object>> data, String user)throws Exception;

    List<String> idList(String startSn, String endSn, Integer begins, Integer finish,String proCom) throws MessageException;

    List<String> idList(String startSn, String endSn) throws MessageException;

    AgentResult isInSnSegment(String snStart,String snEnd,String isInStart,String isInEnd);

    AgentResult addListItem(List<Object> data, String user) throws Exception;

    int insertImportData(OLogistics oLogistics);

    int updateSnStatus(String orderId, String startSn, String endSn, BigDecimal code, BigDecimal recordStatus,String returnId)throws Exception;

    Map<String, Object> getLogisticsBySn(String sn, String agentId) throws ProcessException;

    List<String> addSn(List<List<String>>  data, String user)throws Exception;

    PageInfo getOLogisticsDetailList(Map<String, Object> param, PageInfo pageInfo);

    AgentResult checkRecordPlan(List<Object> excel,Map<String,Object> db);

    /**
     * 发送物流到业务系统
     * @param lgcId
     * @return
     */
    AgentResult sendLgcInfoToBusSystem(String lgcId,String userId)throws Exception;

    /**
     * 删除物流（sn上传错误等。。）
     * @param lgcId
     * @return
     */
    AgentResult delLogistcstInfo(String lgcId,String userId)throws Exception;

    /**
     * 物流明细-批量退转发
     * @param logsDetailId
     * @param userId
     * @return
     * @throws Exception
     */
    AgentResult updateByIdSnInfo(String logsDetailId, String userId) throws Exception;

    /**
     * 处理上传的物流明细
     * @param data
     * @param user
     * @return
     * @throws Exception
     */
    List<String> upLogisticsDetailList(List<List<Object>> data, String user)throws Exception;

    /**
     * 单条物流明细上传处理
     * @param data
     * @param user
     * @return
     * @throws Exception
     */
    AgentResult upLogisticsDetailListItem(List<Object> data, String user) throws MessageException;
}
