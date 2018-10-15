package com.ryx.credit.machine.dao;

import com.ryx.credit.machine.entity.ImsTermTransfer;
import com.ryx.credit.machine.entity.ImsTermTransferExample;
import java.util.List;

public interface ImsTermTransferMapper {
    long countByExample(ImsTermTransferExample example);

    int deleteByExample(ImsTermTransferExample example);

    int insert(ImsTermTransfer record);

    int insertSelective(ImsTermTransfer record);

    List<ImsTermTransfer> selectByExample(ImsTermTransferExample example);

    ImsTermTransfer selectByPrimaryKey(String transferId);

    int updateByPrimaryKeySelective(ImsTermTransfer record);

    int updateByPrimaryKey(ImsTermTransfer record);
}