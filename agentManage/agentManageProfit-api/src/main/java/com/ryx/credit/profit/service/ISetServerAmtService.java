package com.ryx.credit.profit.service;

import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.pojo.SetServerAmt;
import com.ryx.credit.profit.pojo.SetServerAmtExample;

import java.util.List;
import java.util.Map;

/**
 * 代理商服务费相关业务
 * auth：chenliang
 * 2019/5/10
 */
public interface ISetServerAmtService {

    List<Map<String,Object>> queryBumCode();


    List<SetServerAmt> selectByExample(SetServerAmtExample example);


    PageInfo setServerAmtList(Map<String,Object> param, PageInfo pageInfo);

    List<Map<String,Object>> queryD(String bumId);
    int insertSelective(SetServerAmt record);
    int updateByPrimaryKeySelective(SetServerAmt record);

    int clearServerAmtDetailData(String profitDate);
    void calculateServerAmt(String profitDate);
}
