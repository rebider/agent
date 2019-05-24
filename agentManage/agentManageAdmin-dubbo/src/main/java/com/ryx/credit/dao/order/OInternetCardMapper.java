package com.ryx.credit.dao.order;

import com.ryx.credit.pojo.admin.order.OInternetCard;
import com.ryx.credit.pojo.admin.order.OInternetCardExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OInternetCardMapper {
    long countByExample(OInternetCardExample example);

    int deleteByExample(OInternetCardExample example);

    int insert(OInternetCard record);

    int insertSelective(OInternetCard record);

    List<OInternetCard> selectByExample(OInternetCardExample example);

    OInternetCard selectByPrimaryKey(String iccidNum);

    int updateByPrimaryKeySelective(OInternetCard record);

    int updateByPrimaryKey(OInternetCard record);

    int selectInternetCardExpireCount(@Param("map")Map<String,Object> map);

    int updateInternetCardExpire(@Param("map")Map<String,Object> map);

    /**
     * 查询要续费的数据
     * @param map
     * @return
     */
    List<OInternetCard> selectInternetCardRenew(@Param("map")Map<String,Object> map);


    List<OInternetCard> queryInternetCardList(OInternetCardExample example);
}