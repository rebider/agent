package com.ryx.credit.dao.order;


import com.ryx.credit.common.util.Page;
import com.ryx.credit.pojo.admin.order.OReceiptOrder;
import com.ryx.credit.pojo.admin.order.OReceiptOrderExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OReceiptOrderMapper {
    long countByExample(OReceiptOrderExample example);

    int deleteByExample(OReceiptOrderExample example);

    int insert(OReceiptOrder record);

    int insertSelective(OReceiptOrder record);

    List<OReceiptOrder> selectByExample(OReceiptOrderExample example);

    OReceiptOrder selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OReceiptOrder record);

    int updateByPrimaryKey(OReceiptOrder record);

    List<Map<String,Object>> queryPlannerList(@Param("par") Map<String,Object> par,@Param("page") Page page);

    int queryPlannerCount(@Param("par") Map<String,Object> par);

    List<Map<String,Object>> queryPlannerAll(@Param("par")Map<String, Object> reqMap, @Param("page")Page page);
}