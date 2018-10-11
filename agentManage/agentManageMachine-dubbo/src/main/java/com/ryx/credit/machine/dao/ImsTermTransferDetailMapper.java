package com.ryx.credit.machine.dao;

import com.ryx.credit.machine.entity.ImsTermTransferDetail;
import com.ryx.credit.machine.entity.ImsTermTransferDetailExample;
import java.util.List;

public interface ImsTermTransferDetailMapper {
    long countByExample(ImsTermTransferDetailExample example);

    int deleteByExample(ImsTermTransferDetailExample example);

    int insert(ImsTermTransferDetail record);

    int insertSelective(ImsTermTransferDetail record);

    List<ImsTermTransferDetail> selectByExample(ImsTermTransferDetailExample example);

    ImsTermTransferDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ImsTermTransferDetail record);

    int updateByPrimaryKey(ImsTermTransferDetail record);
}