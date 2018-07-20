package com.ryx.credit.common.util.agentUtil;

import com.ryx.credit.common.util.FastMap;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by RYX on 2018/7/20.
 */
public class StageUtil {

    public static List<Map> stageOrder(BigDecimal amount, int count, Date date, int day){
        BigDecimal item =  amount.divide(new BigDecimal(count),0,BigDecimal.ROUND_HALF_UP);
        BigDecimal temp = new BigDecimal(0);
        for (int i=1;i<= count;i++){
            temp = temp.add(item);
        }
        if(temp.compareTo(item) < 0){
            temp = item.subtract(temp);
        }else{
            temp = new BigDecimal(0);
        }
        List<Map> data = new ArrayList<>();
        for (int i=1;i<= count;i++){
            FastMap map = FastMap.fastSuccessMap();
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.set(Calendar.DAY_OF_MONTH,day);
            map.putKeyV("date",c.getTime());
            map.putKeyV("count",new BigDecimal(i));
            map.putKeyV("item",i==1?item.add(temp):item);
            data.add(map);
        }
        return data;
    }
}
