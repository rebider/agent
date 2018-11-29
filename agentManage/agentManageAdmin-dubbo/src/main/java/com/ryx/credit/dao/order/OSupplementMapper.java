package com.ryx.credit.dao.order;



import com.ryx.credit.common.util.Page;
import com.ryx.credit.pojo.admin.order.OSupplement;
import com.ryx.credit.pojo.admin.order.OSupplementExample;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface OSupplementMapper {
    int countByExample(OSupplementExample example);

    int deleteByExample(OSupplementExample example);

    int insert(OSupplement record);

    int insertSelective(OSupplement record);

    List<OSupplement> selectByExample(OSupplementExample example);

    OSupplement selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OSupplement record);

    int updateByPrimaryKey(OSupplement record);

    List<Map<String,Object>> selectAll(@Param("map") Map<String, Object> map, @Param("page") Page page);

    int getCount(@Param("map")Map<String, Object> map);

    BigDecimal selectPayAmout(@Param("srcid")String srcid, @Param("pkType")String pkType);
}