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
        String a = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAroeVFRrOIyY5pipVxmJk"+
                "fLPsciyxzpR6ux8nftyviGSe2D5gPgeTbcPmJ4YC7oKGzG6jHTRFkMrmsM6q3iMq"+
                "TV4IkVVtyoJb/dr6CLUy0zABkqnXJ4D14dee8pPUkRCuYbii/DLH50zCGY+ttlG5"+
                "tnWbyHrhsUFQ+nJz4xVlRegmAsMHLinzVN3jkmVsSVeeWkB4ooglRAe/vo9/vW4t"+
                "uGxp2Pmnu141YgyjEAtlMYUNpduUv/aAjyHM/DCi/aUePCaUa/2rhyDXanMh9oI4"+
                "F/ooqNBIxfx0fiyJzDFGR6Kmc2iU5c5HUDe7KufepsLJ8N5VAg8qsGygpDdj6W6T"+
                "UwIDAQAB";
        System.out.println(a);
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
