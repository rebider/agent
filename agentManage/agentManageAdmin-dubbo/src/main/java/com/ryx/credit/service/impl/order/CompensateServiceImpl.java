package com.ryx.credit.service.impl.order;

import com.ryx.credit.service.order.CompensateService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 补差价处理
 * Created by RYX on 2018/7/24.
 */
@Service("compensateService")
public class CompensateServiceImpl implements CompensateService {


    public void getOrderMsgByExcel(List<Object> excelList){
       String num =  String.valueOf(excelList.get(0));


    }
}
