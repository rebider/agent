package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.BusType;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.dao.ProfitSupplyTaxMapper;
import com.ryx.credit.profit.service.ProfitSupplyTaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("profitSupplyTaxService")
public class ProfitSupplyTaxServiceImpl implements ProfitSupplyTaxService {
    @Autowired
    private ProfitSupplyTaxMapper profitSupplyTaxMapper;
    @Override
    public PageInfo getProfitSupplyTaxList(Map<String, Object> param, PageInfo pageInfo) {
        Long count = 0L;
       List<Map<String, Object>> listAll;

        if ("1".equals(param.get("chekbox"))){
                count = profitSupplyTaxMapper.getProfitSupplyTaxCount(param);
                listAll = profitSupplyTaxMapper.getClassificationList(param);
            /*if ("".equals(param.get("SUPPLY_TAX_AGENT_NAME"))&&("".equals(param.get("SUPPLY_TAX_AGENT_ID")))){
                param.put("SUPPLY_TAX_TYPE", BusType.JG.key);
                List<Map<String, Object>> list1 = profitSupplyTaxMapper.getProfitSupplyTaxList(param);
                param.put("SUPPLY_TAX_TYPE",BusType.BZYD.key);
                List<Map<String, Object>> list2 = profitSupplyTaxMapper.getProfitSupplyTaxList(param);
                list=new ArrayList<>();
                list.addAll(list1);
                list.addAll(list2);
            }else{
                 list = profitSupplyTaxMapper.getProfitSupplyTaxList(param);
            }

             listAll = new ArrayList<>();
             if(list!=null){
               for(int i = 0;i< list.size();i++){
                   listAll.add(list.get(i));
                   String supplyTaxSubId = (String) list.get(i).get("SUPPLY_TAX_SUB_ID");
                   Map<String,Object> mapOne =  new TreeMap<>();
                   mapOne.put("SUPPLY_TAX_AGENT_ID",supplyTaxSubId);
                   mapOne.put("SUPPLY_TAX_PLATFORM","01");
                  List<Map<String, Object>> listTwo = profitSupplyTaxMapper.getProfitSupplyTaxList(mapOne);
                  if (listTwo != null){
                      for (int j = 0;j< listTwo.size();j++){
                          listAll.add(listTwo.get(j));
                          String supplyTaxSubId2 =  (String)listTwo.get(j).get("SUPPLY_TAX_SUB_ID");
                          Map<String,Object> mapTwo =  new TreeMap<>();
                          mapTwo.put("SUPPLY_TAX_AGENT_ID",supplyTaxSubId2);
                          mapTwo.put("SUPPLY_TAX_PLATFORM","01");
                          List<Map<String, Object>> listTh = profitSupplyTaxMapper.getProfitSupplyTaxList(mapTwo);

                     if (listTh !=null){
                         listAll.addAll(listTh);
                     }else{
                         continue;
                     }
                      }
                  }else {
                      continue;
                  }

               }
                 count = Long.valueOf(listAll.size());
             }else{
                 throw new ProcessException("没有此代理商！");
             }*/
        }else{
            count = profitSupplyTaxMapper.getProfitSupplyTaxCount(param);
            listAll = profitSupplyTaxMapper.getProfitSupplyTaxList(param);
        }
        pageInfo.setTotal(count.intValue());
        pageInfo.setRows(listAll);
        System.out.println("查询============================================" + JSONObject.toJSON(listAll));
        return pageInfo;
    }


}
