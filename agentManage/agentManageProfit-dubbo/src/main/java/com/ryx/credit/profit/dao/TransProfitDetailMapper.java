package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.TransProfitDetail;
import com.ryx.credit.profit.pojo.TransProfitDetailExample;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface TransProfitDetailMapper {
    long countByExample(TransProfitDetailExample example);

    int deleteByExample(TransProfitDetailExample example);

    int insert(TransProfitDetail record);

    int insertSelective(TransProfitDetail record);

    List<TransProfitDetail> selectByExample(TransProfitDetailExample example);

    int updateByExampleSelective(@Param("record") TransProfitDetail record, @Param("example") TransProfitDetailExample example);

    int updateByExample(@Param("record") TransProfitDetail record, @Param("example") TransProfitDetailExample example);

    List<TransProfitDetail> selectListByDate(String profitDate);

    List<TransProfitDetail> selectCompanyByDoubleId(TransProfitDetail record);

    List<TransProfitDetail> selectListByDoubleId(TransProfitDetail record);

    BigDecimal selectSumTotalDay(TransProfitDetail record);

    /***
     * @Description: 汇总交易分润明细
     * @Param: prfitDate 分润日期
     * @return: 交易分润明细
     * @Author: zhaodw
     * @Date: 2018/8/27
     */
    List<TransProfitDetail> getPosTransProfitDetailSumList(String prfitDate);

    List<TransProfitDetail> selectByIdAndMonth(TransProfitDetail record);

    int updateByPrimaryKeySelective(TransProfitDetail record);

    BigDecimal selectAmtBySummary(String profitDate);

    void deleteBySourceIdAndMonth(@Param("sourceInfo") String sourceInfo, @Param("transDate") String transDate);

    List<TransProfitDetail> selectListByParams(Map<String,Object> params);

    List<Map<String,Object>> baseProfitList(Map<String,Object> params);
    Integer baseProfitCount (Map<String,Object> params);


    List<Map<String,Object>> baseProfitLowerList(Map<String,Object> params);

    Integer  baseProfitLowerCount (Map<String,Object> params);

    List<Map<String,Object>> queryBusNum(@Param("type") String type);

    List<Map<String,String>> selectByBusNum(@Param("date") String date, @Param("busNum")String busNum);

}