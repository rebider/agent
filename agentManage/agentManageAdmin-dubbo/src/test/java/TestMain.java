import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.Conver10ToConver33Utils;
import com.ryx.credit.pojo.admin.order.OPaymentDetail;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        //页面传的实际付款金额
        BigDecimal bigDecimal = new BigDecimal(500);
        ArrayList<BigDecimal> list = new ArrayList<>();
        //数据库查询的应还金额
        list.add(new BigDecimal(428.58));
        list.add(new BigDecimal(428.58));
        list.add(new BigDecimal(428.58));
        list.add(new BigDecimal(428.58));

        if(null!=list && list.size()>1){
            boolean flag=true;
            boolean f=true;
            BigDecimal residue=bigDecimal;
            for (BigDecimal decimal : list) {
                if(f==false){
                    break;
                }
                if(residue.compareTo(new BigDecimal(0))==0 ||flag==false){
                    //如果销账金额已抵扣完销账则停止循环
                    f=false;
                    break;
                }
                   BigDecimal initialize = new BigDecimal(0);
                   if(residue.compareTo(decimal)==0){
                       initialize=decimal;
                       flag=false;
                       System.out.println("还款-------:"+initialize);
                   }else if(residue.compareTo(decimal)==-1){
                       initialize.add(residue);
                       flag=false;
                       System.out.println("还款-------:"+initialize.add(residue));
                   }else if(residue.compareTo(decimal)==1){
                       residue = residue.subtract(decimal);
                       System.out.println("还款--------:"+decimal);
                   }
            }

        }
    }


    public static Map getDigit(String firstSn, String lastSn) {
        char[] chars = firstSn.toCharArray();
        char[] charss = lastSn.toCharArray();
        int num = 0;
        for (int i = 1; i <= chars.length; i++) {
            if (chars[i] != charss[i]) {
                num = i;
                break;
            }
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("num", num);
        map.put("length", chars.length);
        map.put("firstSn", firstSn);
        map.put("lastSn", lastSn);
        return map;
    }
}
