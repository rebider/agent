package com.ryx.credit.profit.dao;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.profit.pojo.InvoiceSum;
import com.ryx.credit.profit.pojo.InvoiceSumExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface InvoiceSumMapper {
    long countByExample(InvoiceSumExample example);

    int deleteByExample(InvoiceSumExample example);

    int insert(InvoiceSum record);

    int insertSelective(InvoiceSum record);

    List<InvoiceSum> selectByExample(InvoiceSumExample example);

    InvoiceSum selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(InvoiceSum record);

    int updateByPrimaryKey(InvoiceSum record);

    List<Map<String,Object>> getListByMap(@Param("page") Page page, @Param("map") Map<String,String> map);

    int getCountByMap(@Param("map")Map<String,String> map);

    List<Map> exports(@Param("map") Map<String,String> map);

}