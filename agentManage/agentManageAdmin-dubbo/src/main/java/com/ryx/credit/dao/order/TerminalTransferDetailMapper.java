package com.ryx.credit.dao.order;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.pojo.admin.order.TerminalTransferDetail;
import com.ryx.credit.pojo.admin.order.TerminalTransferDetailExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TerminalTransferDetailMapper {
    long countByExample(TerminalTransferDetailExample example);

    int deleteByExample(TerminalTransferDetailExample example);

    int insert(TerminalTransferDetail record);

    int insertSelective(TerminalTransferDetail record);

    List<TerminalTransferDetail> selectByExample(TerminalTransferDetailExample example);

    TerminalTransferDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TerminalTransferDetail record);

    int updateByPrimaryKey(TerminalTransferDetail record);

    List<Map<String,Object>> selectTerminalTransferDetailList(@Param("map") Map<String, Object> map, @Param("page") Page page);

    List<Map<String,Object>> exprotTerminalTransferDetails(@Param("map") Map<String, Object> map);

    int selectTerminalTransferDetailCount(@Param("map") Map<String, Object> map);

    int updateStatusByTerminalTransferId(@Param("map") Map<String, Object> map);
}