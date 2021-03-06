package com.ryx.internet.dao;

import com.ryx.credit.common.util.Page;
import com.ryx.internet.pojo.OInternetCard;
import com.ryx.internet.pojo.OInternetCardExample;
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


    int updateByPrimaryKeySelectiveNotNull(OInternetCard record);


    int selectInternetCardStopCount(@Param("map")Map<String,Object> map);


    int updateInternetCardStop(@Param("map")Map<String,Object> map);

    /**
     * 通过sn查询流量卡是否存在
     * @param iccidNum
     * @return
     */
    OInternetCard selectBySnNum(String iccidNum);


    List<OInternetCard> internetCardList(OInternetCardExample example);


    int internetCardCount(OInternetCardExample example);


    List<OInternetCard> selectUpdateCardStatus(@Param("map")Map<String,Object> map,@Param("page") Page page);

}