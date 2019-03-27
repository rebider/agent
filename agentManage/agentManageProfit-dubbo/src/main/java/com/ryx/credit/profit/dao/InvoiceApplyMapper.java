package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.InvoiceApply;
import com.ryx.credit.profit.pojo.InvoiceApplyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InvoiceApplyMapper {
    long countByExample(InvoiceApplyExample example);

    int deleteByExample(InvoiceApplyExample example);

    int insert(InvoiceApply record);

    int insertSelective(InvoiceApply record);

    List<InvoiceApply> selectByExample(InvoiceApplyExample example);

    InvoiceApply selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") InvoiceApply record, @Param("example") InvoiceApplyExample example);

    int updateByExample(@Param("record") InvoiceApply record, @Param("example") InvoiceApplyExample example);

    int updateByPrimaryKeySelective(InvoiceApply record);

    int updateByPrimaryKey(InvoiceApply record);
}