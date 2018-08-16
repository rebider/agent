package com.ryx.credit.common.util.agentUtil;

import com.ryx.credit.common.util.FastMap;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by RYX on 2018/7/20.
 */
public class StageUtil {

    public static List<Map> stageOrder(BigDecimal amount, int count, Date date, int day){
        BigDecimal item =  amount.divide(new BigDecimal(count),8,BigDecimal.ROUND_HALF_UP);
        item = item.setScale(2,BigDecimal.ROUND_HALF_UP);
        BigDecimal temp = new BigDecimal(0);
        for (int i=1;i<= count;i++){
            temp = temp.add(item);
        }
        if(temp.compareTo(amount) != 0){
            temp = amount.subtract(temp);
        }else{
            temp = new BigDecimal(0);
        }
        List<Map> data = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH,day);
        for (int i=1;i<= count;i++){
            //第一个月
            FastMap map = FastMap.fastSuccessMap();
            map.putKeyV("date",c.getTime());
            map.putKeyV("count",new BigDecimal(i));
            map.putKeyV("item",i==1?item.add(temp):item);
            data.add(map);
            //累加1个月
            c.add(Calendar.MONTH,1);
        }
        return data;
    }
}
