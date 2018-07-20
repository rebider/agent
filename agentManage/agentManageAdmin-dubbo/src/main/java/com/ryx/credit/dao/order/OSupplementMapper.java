package com.ryx.credit.dao.order;



import com.ryx.credit.pojo.admin.order.OSupplement;
import com.ryx.credit.pojo.admin.order.OSupplementExample;

import java.util.List;

public interface OSupplementMapper {
    int countByExample(OSupplementExample example);

    int deleteByExample(OSupplementExample example);

    int insert(OSupplement record);

    int insertSelective(OSupplement record);

    List<OSupplement> selectByExample(OSupplementExample example);

    OSupplement selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OSupplement record);

    int updateByPrimaryKey(OSupplement record);
}