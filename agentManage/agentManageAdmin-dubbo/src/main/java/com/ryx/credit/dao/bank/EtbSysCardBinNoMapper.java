package com.ryx.credit.dao.bank;

import com.ryx.credit.pojo.admin.bank.EtbSysCardBinNo;
import com.ryx.credit.pojo.admin.bank.EtbSysCardBinNoExample;

import java.util.List;

public interface EtbSysCardBinNoMapper {
    int countByExample(EtbSysCardBinNoExample example);

    int deleteByExample(EtbSysCardBinNoExample example);

    int insert(EtbSysCardBinNo record);

    int insertSelective(EtbSysCardBinNo record);

    List<EtbSysCardBinNo> selectByExample(EtbSysCardBinNoExample example);
}