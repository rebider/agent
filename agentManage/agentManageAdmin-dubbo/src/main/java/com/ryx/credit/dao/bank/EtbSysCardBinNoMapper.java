package com.ryx.credit.dao.bank;

import com.ryx.credit.pojo.admin.bank.EtbSysCardBinNo;
import com.ryx.credit.pojo.admin.bank.EtbSysCardBinNoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EtbSysCardBinNoMapper {
    int countByExample(EtbSysCardBinNoExample example);

    int deleteByExample(EtbSysCardBinNoExample example);

    int insert(EtbSysCardBinNo record);

    int insertSelective(EtbSysCardBinNo record);

    List<EtbSysCardBinNo> selectByExample(EtbSysCardBinNoExample example);

    /**
     * 根据卡号前几位查询卡Bin信息
     * @param card_no
     * @return
     */
    List<EtbSysCardBinNo> getCardBinByCardNo(@Param("card_no") String card_no);
}