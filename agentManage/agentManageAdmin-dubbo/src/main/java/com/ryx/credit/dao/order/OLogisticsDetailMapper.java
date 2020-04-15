package com.ryx.credit.dao.order;

import com.ryx.credit.pojo.admin.order.OLogisticsDetail;
import com.ryx.credit.pojo.admin.order.OLogisticsDetailExample;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface OLogisticsDetailMapper {
    long countByExample(OLogisticsDetailExample example);

    int deleteByExample(OLogisticsDetailExample example);

    int insert(OLogisticsDetail record);

    int insertSelective(OLogisticsDetail record);

    List<OLogisticsDetail> selectByExample(OLogisticsDetailExample example);

    OLogisticsDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OLogisticsDetail record);

    int updateByPrimaryKey(OLogisticsDetail record);

    List<Map<String, Object>> queryCompensateLList(Map<String, Object> param);

    int updateCompensate(Map<String, Object> param);

    List<String> querySnLList(Map<String, Object> param);

    int querySnCount(Map<String, Object> param);

    Map  selectSn(OLogisticsDetail oLogisticsDetail);

    Long getOLogisticsDetailCount(Map<String, Object> param);

    List<Map<String,Object>> getOLogisticsDetailList(Map<String, Object> param);

    List<OLogisticsDetail> queryHistoryOrder();

    List<OLogisticsDetail> querySnCountObj(Map<String, Object> param);

    int updateByLogisticsId(OLogisticsDetail oLogisticsDetail);

    /**
     * 查询物流明细发送状态
     * @param param
     * @return
     */
    List<BigDecimal> selectSendStatusByMap(Map<String, Object> param);
}