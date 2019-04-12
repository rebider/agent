import com.ryx.credit.common.util.Conver10ToConver33Utils;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by cx on 2018/6/12.
 */
public class TestMain {

    public static void main(String[] args) {
        String a="1003A30";
        String b="1003S30";
        Map digit = Conver10ToConver33Utils.getDigit(a, b);
        String start=(String)digit.get("start");
        String end=(String)digit.get("end");
        Integer length=(Integer)digit.get("length");
        Integer num=(Integer)digit.get("num");
        Pattern p = Pattern.compile("[a-zA-z]");
        if(p.matcher(start).find() || p.matcher(end).find())
        {
            System.out.println("含有英文字符"+"----起始位数:"+num+"---结束位数:"+length);
        }else{
            System.out.println("不含英文字符");
        }

    }
}
