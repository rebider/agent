import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.Conver10ToConver33Utils;
import org.apache.commons.lang.StringUtils;

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
        List<String> list = new ArrayList<String>();
        String a="1003332";
        String b="1003342";
        System.out.println(a.substring(2,7));
//        Map digit = getDigit(a, b);
//        String end = (String) digit.get("lastSn");
//        String start = (String) digit.get("firstSn");
//        Integer  finish = (Integer) digit.get("length");
//        Integer begins = (Integer) digit.get("num");
//
//        Pattern p = Pattern.compile("[a-zA-z]");
//        if (p.matcher(start).find() || p.matcher(end).find()) {
//            list = getBetweenValues(a, b);
//            System.out.println("含有英文字符" + "----起始位数:" + begins + "---结束位数:" + finish);
//        } else {
//            System.out.println("不含英文字符");
//            int begin = begins - 1;
//            String sSub = start.substring(begin, finish);
//            String eSub = end.substring(begin, finish);
//            int num = Integer.parseInt(sSub);
//            int w = finish - begin;
//            for (int j = Integer.parseInt(eSub) - Integer.parseInt(sSub); j >= 0; j--) {
//                int x = num++;
//                String format = String.format("%0" + w + "d", x);
//                String c = start.substring(0, begin) + format + start.substring(finish);
//                list.add(c);
//            }
//            System.out.println(list);
//        }
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
