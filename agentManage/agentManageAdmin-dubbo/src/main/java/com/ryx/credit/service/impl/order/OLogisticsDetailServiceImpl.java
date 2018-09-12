package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.OLogisticsDetailStatus;
import com.ryx.credit.dao.order.OLogisticsDetailMapper;
import com.ryx.credit.service.order.OLogisticsDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by RYX on 2018/9/11.
 */
@Service("oLogisticsDetailService")
public class OLogisticsDetailServiceImpl implements OLogisticsDetailService {

    @Autowired
    private OLogisticsDetailMapper logisticsDetailMapper;

    @Override
    public List<Map<String, Object>> queryCompensateLList(String beginSn,String endSn){

        Map<String, Object> reqParam = new HashMap<>();
        reqParam.put("snBegin",beginSn);
        reqParam.put("snEnd",endSn);
        reqParam.put("status", OLogisticsDetailStatus.STATUS_FH.code);
        ArrayList<Object> recordStatusList = new ArrayList<>();
        recordStatusList.add(OLogisticsDetailStatus.RECORD_STATUS_VAL.code);
        reqParam.put("recordStatusList",recordStatusList);
        List<Map<String, Object>> resultListMap = logisticsDetailMapper.queryCompensateLList(reqParam);
        return resultListMap;
    }

    @Override
    public List<String> querySnLList(String beginSn,String endSn){
        Map<String, Object> reqParam = new HashMap<>();
        reqParam.put("snBegin",beginSn);
        reqParam.put("snEnd",endSn);
        reqParam.put("status", OLogisticsDetailStatus.STATUS_FH.code);
        List<String> resultList = logisticsDetailMapper.querySnLList(reqParam);
        return resultList;
    }

    @Override
    public int querySnCount(String beginSn,String endSn){
        Map<String, Object> reqParam = new HashMap<>();
        reqParam.put("snBegin",beginSn);
        reqParam.put("snEnd",endSn);
        reqParam.put("status", OLogisticsDetailStatus.STATUS_FH.code);
        int result = logisticsDetailMapper.querySnCount(reqParam);
        return result;
    }

}
