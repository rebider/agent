package com.ryx.credit.dao.order;

import com.ryx.credit.pojo.admin.order.TerminalTransfer;
import com.ryx.credit.pojo.admin.order.TerminalTransferExample;

import java.util.List;

public interface TerminalTransferMapper {
    long countByExample(TerminalTransferExample example);

    int deleteByExample(TerminalTransferExample example);

    int insert(TerminalTransfer record);

    int insertSelective(TerminalTransfer record);

    List<TerminalTransfer> selectByExample(TerminalTransferExample example);

    TerminalTransfer selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TerminalTransfer record);

    int updateByPrimaryKey(TerminalTransfer record);

}