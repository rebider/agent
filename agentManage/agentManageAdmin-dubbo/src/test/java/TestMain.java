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
        String a = "OD190618820157829,"+
                "OD190625040182844,"+
                "OD190625170181900,"+
                "OD190702630201122,"+
                "OD190702660200079,"+
                "OD190702860198963,"+
                "OD190703040205672,"+
                "OD190703770205034,"+
                "OD190704260208642,"+
                "OD190704770209204,"+
                "OD190704840209183,"+
                "OD190704860207469,"+
                "OD190705030214150,"+
                "OD190705050212226,"+
                "OD190705060214484,"+
                "OD190705220212331,"+
                "OD190705220214775,"+
                "OD190705220215597,"+
                "OD190705220215774,"+
                "OD190705310213663,"+
                "OD190705400215335,"+
                "OD190705820215025,"+
                "OD190705820215275,"+
                "OD190708060218559,"+
                "OD190708070222228,"+
                "OD190708170218681,"+
                "OD190708220217665,"+
                "OD190708220217665,"+
                "OD190708220218145,"+
                "OD190708220222996,"+
                "OD190708310217823,"+
                "OD190708720219902,"+
                "OD190708820220187,"+
                "OD190709220225651,"+
                "OD190709620229510,"+
                "OD190709820227297,"+
                "OD190709820228143,"+
                "OD190710040231106,"+
                "OD190710080234879,"+
                "OD190710120235664,"+
                "OD190710170231925,"+
                "OD190710170231925,"+
                "OD190710170231925,"+
                "OD190710220235205,"+
                "OD190710220235735,"+
                "OD190710220235735,"+
                "OD190710450230967,"+
                "OD190710450230967,"+
                "OD190710530231035,"+
                "OD190710530231035,"+
                "OD190710530231035,"+
                "OD190710530231035,"+
                "OD190710540235444,"+
                "OD190710610234607,"+
                "OD190710620236307,"+
                "OD190710630233810,"+
                "OD190710810234168,"+
                "OD190710820230772,"+
                "OD190710920236566,"+
                "OD190711170239536,"+
                "OD190711200240972,"+
                "OD190711220239914,"+
                "OD190711480242832,"+
                "OD190711680238611,"+
                "OD190712450244139,"+
                "OD190712790250436,"+
                "OD190712960245017,"+
                "OD190715130255814,"+
                "OD190715130255814,"+
                "OD190729030307920,"+
                "OD190729720304479,"+
                "OD190729720304479,"+
                "OD190731810322677,"+
                "OD190801680329781,"+
                "OD190801680329781,"+
                "OD190801720325606,"+
                "OD190802150333478,"+
                "OD190805040342352,"+
                "OD190805180342444,"+
                "OD190805180343542,"+
                "OD190805200341418,"+
                "OD190805220343472,"+
                "OD190805220343472,"+
                "OD190805670343630,"+
                "OD190805670343860,"+
                "OD190806660348850,"+
                "OD190806720344886,"+
                "OD190806720344886,"+
                "OD190806720344886,"+
                "OD190806720344886,"+
                "OD190807180351633,"+
                "OD190807220353458,"+
                "OD190808200356530,"+
                "OD190808200356530,"+
                "OD190808510356680,"+
                "OD190809170360982,"+
                "OD190809170360982,"+
                "OD190809170360982,"+
                "OD190809720364383,"+
                "OD190809720364383,"+
                "OD190809720364383,"+
                "OD190809720364383,"+
                "OD190809720364383,"+
                "OD190809720364383,"+
                "OD190809720364383,"+
                "OD190809720364383,"+
                "OD190809720364383,"+
                "OD190809720364383,"+
                "OD190812210369090";
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
