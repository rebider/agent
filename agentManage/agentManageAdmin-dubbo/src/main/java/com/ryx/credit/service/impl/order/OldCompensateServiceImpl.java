package com.ryx.credit.service.impl.order;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.DictGroup;
import com.ryx.credit.common.enumc.PlatformType;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.machine.service.TermMachineService;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.pojo.admin.order.OProduct;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.order.OldCompensateService;
import com.ryx.credit.service.order.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 旧订单补差价处理
 * Created by liudh on 2019/3/13.
 */
@Service("oldCompensateService")
public class OldCompensateServiceImpl implements OldCompensateService {

    private static Logger log = LoggerFactory.getLogger(OldCompensateServiceImpl.class);

    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private ProductService productService;
    @Autowired
    private TermMachineService termMachineService;

    @Override
    public List<Map<String,Object>> getOrderMsgByExcel(List<List<Object>> excelList){

        List<Map<String,Object>> resultList = new ArrayList<>();
        for (List<Object> excel : excelList) {
            String snBegin = "";
            String snEnd = "";
            String count = "";
            String proModel = "";
            try {
                snBegin =  String.valueOf(excel.get(0));
                snEnd =  String.valueOf(excel.get(1));
                count =  String.valueOf(excel.get(2));
                proModel =  String.valueOf(excel.get(3));
            } catch (Exception e) {
                throw new ProcessException("导入解析文件失败");
            }
            Dict modelType = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.MODEL_TYPE.name(),proModel);
            if(modelType==null){
                throw new ProcessException("导入类型错误");
            }
            if(proModel.equals("MPOS")){
                try {
                    AgentResult agentResult = termMachineService.querySnMsg(PlatformType.MPOS,snBegin, snEnd);
                    if(!agentResult.isOK()){
                        throw new ProcessException("查询手刷失败");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                try {
                    AgentResult agentResult = termMachineService.querySnMsg(PlatformType.POS,"001", "012");
                    if(!agentResult.isOK()){
                        throw new ProcessException("查询pos失败");
                    }
                    JSONObject jsonObject = JSONObject.parseObject(agentResult.getMsg());
                    JSONObject data = JSONObject.parseObject(String.valueOf(jsonObject.get("data")));
                    System.out.println(String.valueOf(data.get("termMachineList")));
                    List<Map<String,Object>> termMachineListMap = (List<Map<String,Object>>)JSONArray.parse(String.valueOf(data.get("termMachineList")));
                    System.out.println(termMachineListMap);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            OProduct product = new OProduct();
            product.setProType(modelType.getdItemvalue());
            List<Map> proMaps = productService.queryGroupByProCode(product);
            for (Map proMap : proMaps) {
                if(String.valueOf(proMap.get("proName")).equals("流量卡")){
                    proMaps.remove(proMap);
                    break;
                }
            }
            Map<String,Object> map = new HashMap();
            map.put("snBegin",snBegin);
            map.put("snEnd",snEnd);
            map.put("count",count);
            map.put("proMaps",proMaps);
            resultList.add(map);
        }
        return resultList;

    }




    public static void main(String[] args){
        try {
//            AgentResult agentResult = querySnMsgService.httpForPos("001", "002");
//            System.out.println(agentResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
