package com.ryx.credit.dao.order;

import com.ryx.credit.pojo.admin.order.OAccountAdjustDetail;
import com.ryx.credit.pojo.admin.order.OAccountAdjustDetailExample;
import java.util.List;

public interface OAccountAdjustDetailMapper {
    long countByExample(OAccountAdjustDetailExample example);

    int deleteByExample(OAccountAdjustDetailExample example);

    int insert(OAccountAdjustDetail record);

    int insertSelective(OAccountAdjustDetail record);

    List<OAccountAdjustDetail> selectByExample(OAccountAdjustDetailExample example);

    OAccountAdjustDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OAccountAdjustDetail record);

    int updateByPrimaryKey(OAccountAdjustDetail record);
}