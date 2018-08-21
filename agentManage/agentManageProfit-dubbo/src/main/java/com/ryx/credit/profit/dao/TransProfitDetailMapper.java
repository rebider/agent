package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.TransProfitDetail;
import com.ryx.credit.profit.pojo.TransProfitDetailExample;
import java.util.List;

public interface TransProfitDetailMapper {
    long countByExample(TransProfitDetailExample example);

    int deleteByExample(TransProfitDetailExample example);

    int insert(TransProfitDetail record);

    int insertSelective(TransProfitDetail record);

    List<TransProfitDetail> selectByExample(TransProfitDetailExample example);

    List<TransProfitDetail> selectListByDate(String profitDate);
}