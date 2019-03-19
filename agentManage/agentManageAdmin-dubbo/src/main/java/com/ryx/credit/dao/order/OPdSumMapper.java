package com.ryx.credit.dao.order;

import com.ryx.credit.pojo.admin.order.OPdSum;
import com.ryx.credit.pojo.admin.order.OPdSumExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OPdSumMapper {
    long countByExample(OPdSumExample example);

    int deleteByExample(OPdSumExample example);

    int insert(OPdSum record);

    int insertSelective(OPdSum record);

    List<OPdSum> selectByExample(OPdSumExample example);

    OPdSum selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") OPdSum record, @Param("example") OPdSumExample example);

    int updateByExample(@Param("record") OPdSum record, @Param("example") OPdSumExample example);

    int updateByPrimaryKeySelective(OPdSum record);

    int updateByPrimaryKey(OPdSum record);


}