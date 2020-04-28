package com.ryx.credit.profit.service.impl;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.dao.PBalanceSerialMapper;
import com.ryx.credit.profit.pojo.PBalanceSerial;
import com.ryx.credit.profit.pojo.PBalanceSerialExample;
import com.ryx.credit.profit.service.PBalanceSerialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("pBalanceSerialService")
public class PBalanceSerialServiceImpl implements PBalanceSerialService {

    @Autowired
    private PBalanceSerialMapper pBalanceSerialMapper;

    @Override
    public PageInfo getListByMap(Map<String, String> param, Page page) {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(pBalanceSerialMapper.getListByMap(param,page));
        pageInfo.setTotal((int)pBalanceSerialMapper.getCountByMap(param));
        return pageInfo;
    }

    @Override
    public List<Map<String, String>> getRefundLog(String balanceId) throws MessageException {
        PBalanceSerialExample example = new PBalanceSerialExample();
        PBalanceSerialExample.Criteria criteria = example.createCriteria();
        criteria.andBalanceIdEqualTo(balanceId);
        List<PBalanceSerial> list = pBalanceSerialMapper.selectByExample(example);
        if(list.size() != 1){
            throw new MessageException("获取日志信息异常，请联系管理员！");
        }
        List<Map<String,String>> mapList = pBalanceSerialMapper.getRefundLog(balanceId);
        if("02".equals(list.get(0).getBalanceStatus())){
            Map<String,String> map = new HashMap<>();
            map.put("FIRST_BALANCE_ID",list.get(0).getBalanceId());
            map.put("CK_TIME",list.get(0).getSyncTime());
            map.put("REFUND_REMARK",list.get(0).getReconRemark());
            map.put("BALANCE_ID",list.get(0).getBalanceId());
            map.put("BALANCE_STATUS",list.get(0).getBalanceStatus());
            if(mapList.size() != 0){
                map.put("PRE_BALANCE_ID",mapList.get(mapList.size()-1).get("BALANCE_ID"));
            }else {
                map.put("PRE_BALANCE_ID",null);
            }
        }
        return mapList;
    }

    @Override
    public List<Map<String, String>> getListToDownload(Map<String, String> param) {
        return pBalanceSerialMapper.getListByMap(param,null);
    }

    @Override
    public Map<String, Object> statisticalData(Map<String, String> param) {
        return pBalanceSerialMapper.statisticalData(param);
    }
}
