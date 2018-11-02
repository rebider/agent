package com.ryx.credit.dao.order;



import com.ryx.credit.pojo.admin.order.OCashReceivables;
import com.ryx.credit.pojo.admin.order.OCashReceivablesExample;

import java.util.List;

public interface OCashReceivablesMapper {
    Integer countByExample(OCashReceivablesExample example);

    int deleteByExample(OCashReceivablesExample example);

    int insert(OCashReceivables record);

    int insertSelective(OCashReceivables record);

    List<OCashReceivables> selectByExample(OCashReceivablesExample example);

    OCashReceivables selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OCashReceivables record);

    int updateByPrimaryKey(OCashReceivables record);
}