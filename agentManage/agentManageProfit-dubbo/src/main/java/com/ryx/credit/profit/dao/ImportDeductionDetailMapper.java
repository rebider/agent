package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.ImportDeductionDetail;
import com.ryx.credit.profit.pojo.ImportDeductionDetailExample;
import java.util.List;

public interface ImportDeductionDetailMapper {
    int countByExample(ImportDeductionDetailExample example);

    int deleteByExample(ImportDeductionDetailExample example);

    int insert(ImportDeductionDetail record);

    int insertSelective(ImportDeductionDetail record);

    List<ImportDeductionDetail> selectByExample(ImportDeductionDetailExample example);

    ImportDeductionDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ImportDeductionDetail record);

    int updateByPrimaryKey(ImportDeductionDetail record);
}