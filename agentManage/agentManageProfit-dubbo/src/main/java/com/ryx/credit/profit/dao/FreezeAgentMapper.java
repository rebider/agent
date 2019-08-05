package com.ryx.credit.profit.dao;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.profit.pojo.FreezeAgent;
import com.ryx.credit.profit.pojo.FreezeAgentExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface FreezeAgentMapper {
    long countByExample(FreezeAgentExample example);

    int deleteByExample(FreezeAgentExample example);

    int insert(FreezeAgent record);

    int insertSelective(FreezeAgent record);

    List<FreezeAgent> selectByExample(FreezeAgentExample example);

    FreezeAgent selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FreezeAgent record);

    int updateByPrimaryKey(FreezeAgent record);

    //正常查询不冻洁
    List<FreezeAgent> selectAllNotFreeze(Map<String, Object> perms);

    Integer selectAllNotFreezeCount(Map<String, Object> perms);

    //不冻结查询下级
    List<FreezeAgent> selectAllNotFreezeLower(Map<String, Object> perms);

    Integer selectAllNotFreezeLowerCount(Map<String, Object> perms);

    //查询冻结代理商并下级
    List<FreezeAgent> selectAllFreezeWithSubordinate(@Param("freezeAgent") FreezeAgent freezeAgent, @Param("page") Page page, @Param("orgId") String orgId);

    long countAllFreezeWithSubordinate(@Param("freezeAgent") FreezeAgent freezeAgent, @Param("orgId") String orgId);

    List<FreezeAgent> selectByExampleWithCity(@Param("freezeAgent") FreezeAgent freezeAgent, @Param("page") Page page, @Param("orgId") String orgId);

    long countByExampleWithCity(@Param("freezeAgent") FreezeAgent freezeAgent, @Param("orgId") String orgId);

    List<FreezeAgent> getThawDataByBatch(String batch);

    Map<String, Object> getThawOperator(String batch);

    void updateThawAgentByBatch(@Param("batch") String batch, @Param("status") String status);

    List<Map<String, Object>> freezeDetail(Map<String, Object> parm);

    Integer freezeDetailCount(Map<String, Object> parm);

    List<Map<String, Object>> freezeDetailLower(Map<String, Object> parm);

    Integer freezeDetailLowerCount(Map<String, Object> parm);

    int delteThawOperationById(String freezeAgentId);


    List<Map<String, Object>> queryBumId(@Param("BUS_NUM") String BUS_NUM);

    Integer queryDayFreezeCount();

    List<FreezeAgent> queryDayFreezeDate(@Param("startNum") Integer startNum, @Param("endNum") Integer endNum);

    Integer deleteInvoiceAgent(String id);
}