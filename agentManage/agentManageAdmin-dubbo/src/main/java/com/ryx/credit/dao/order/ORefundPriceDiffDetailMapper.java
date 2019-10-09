package com.ryx.credit.dao.order;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.pojo.admin.order.ORefundPriceDiffDetail;
import com.ryx.credit.pojo.admin.order.ORefundPriceDiffDetailExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ORefundPriceDiffDetailMapper {
    long countByExample(ORefundPriceDiffDetailExample example);

    int deleteByExample(ORefundPriceDiffDetailExample example);

    int insert(ORefundPriceDiffDetail record);

    int insertSelective(ORefundPriceDiffDetail record);

    List<ORefundPriceDiffDetail> selectByExample(ORefundPriceDiffDetailExample example);

    ORefundPriceDiffDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ORefundPriceDiffDetail record);

    int updateByPrimaryKey(ORefundPriceDiffDetail record);

    List<Map> selectByExampleExtends(@Param("par") Map map,@Param("page") Page page);
}