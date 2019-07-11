import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.Conver10ToConver33Utils;
import com.ryx.credit.pojo.admin.order.OPaymentDetail;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.ryx.credit.common.util.Conver10ToConver33Utils.getBetweenValues;

/**
 * Created by cx on 2018/6/12.
 */
public class TestMain {

    public static void main(String[] args) {
        BigDecimal count = new BigDecimal(4);
        try {
            List<BigDecimal> divideList = new ArrayList<>();
            BigDecimal money = new BigDecimal(1800).subtract(new BigDecimal(850));
            BigDecimal stage = money.divide(count,2,BigDecimal.ROUND_HALF_UP);
            BigDecimal tt = BigDecimal.ZERO;
            for (int i = 0; i < count.intValue(); i++) {

                if(i==count.intValue()-1){
                    if(0!=money.subtract(tt).compareTo(stage)){
                        stage =   money.subtract(tt);
                    }
                }
                tt=tt.add(stage);
                divideList.add(stage);
            }
            System.out.println(divideList);
        } catch (Exception e) {


        }
    }


    public static Map getDigit(String firstSn, String lastSn) {
        char[] chars = firstSn.toCharArray();
        char[] charss = lastSn.toCharArray();
        int num=0;
        for (int i=1;i<= chars.length;i++){
            if (chars[i]!=charss[i]){
                num=i;
                break;
            }
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("num",num);
        map.put("length",chars.length);
        map.put("firstSn",firstSn);
        map.put("lastSn",lastSn);
        return map;
    }
}
