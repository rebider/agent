import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.Conver10ToConver33Utils;
import com.ryx.credit.pojo.admin.order.OPaymentDetail;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
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

        for (int i = 0; i < 10; i++) {
            try {
                int m = 0, t = 1;
                int r = 1 / 0;
                System.out.println(i);
            } catch (Exception e) {
                e.printStackTrace();
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
