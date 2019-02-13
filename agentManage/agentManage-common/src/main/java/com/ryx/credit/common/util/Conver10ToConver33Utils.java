package com.ryx.credit.common.util;

import com.ryx.credit.common.enumc.BinaryConversionEnum;
import com.ryx.credit.common.exception.ProcessException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lrr
 * @Title: Conver10ToConver33Utils <br>
 * @Description: <br>
 * @date 2019/1/29 11:32
 */
public class Conver10ToConver33Utils {
    private static Logger log = LoggerFactory.getLogger(Conver10ToConver33Utils.class);
    /**
     * 获取两个参数之间的值（不包含两个参数）
     *
     * @param param1
     * @param param2
     * @return 流水码的集合
     */
    public static List<String> getBetweenValues(String param1, String param2) {
        List<String> list = new ArrayList<>();
      try {
          if (StringUtils.isBlank(param1) || StringUtils.isBlank(param2)){
              log.info("流水号为空");
              throw new ProcessException("流水号为空");
          }

          if (param1.length()!=param2.length()){
              log.info("联迪的SN码位数不一致");
              throw new ProcessException("联迪的SN码位数不一致");
          }
          String sn1= param1.substring(0,param1.length() - 5);
          String sn2= param2.substring(0,param2.length() - 5);
          if (!sn1.equals(sn2)){
              log.info("联迪的SN码一个号码段除了后五位可以不一致,前面必须相同");
              throw new ProcessException("联迪的SN码一个号码段除了后五位可以不一致,前面必须相同");
          }

          String par1 = param1.substring(param1.length() - 5, param1.length());
          String par2 = param2.substring(param2.length() - 5, param2.length());
          //1.两个参数比较大小
          int i1 = StringSubTo10(par1);
          int i2 = StringSubTo10(par2);
          int min = i1;
          int max = i2;
          if (i1 > i2) {
              min = i2;
              max = i1;
          }
          list.add(sn1+par1);
          list.add(sn1+par2);
          for (int i = min + 1; i < max; i++) {
              String sub = StringSubTo33(i);
              list.add(sn1+sub);
          }
      }catch (Exception e){
          e.printStackTrace();
      }
        return list;
    }


    /**
     * 转换成10进制的数字
     *
     * @param param
     * @return
     */
    private static int StringSubTo10(String param) {
        int numByNumStr=0;
        String substring="";
        try {
            if (StringUtils.isBlank(param)){
                log.info("流水号为空");
                throw new ProcessException("流水号为空");
            }
            StringBuilder stringBuilder = new StringBuilder(param);
            StringBuilder reverse = stringBuilder.reverse();
            System.out.println(reverse.toString());
            //获取后四位10进制的值
            StringBuilder builder = new StringBuilder(reverse.substring(0, 4)).reverse();
            substring = builder.toString();
            System.out.println("'--截取的值" + substring);
            StringBuilder delete = reverse.delete(0, 4).reverse();
            System.out.println("'--剩余的值" + delete);
            //将参数的第一位33进制的值转换成10进制
             numByNumStr = BinaryConversionEnum.getNumByNumStr(delete.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return Integer.parseInt(numByNumStr + substring);
    }

    /**
     * 转换成33进制的数字
     *
     * @param param
     * @return
     */
    private static String StringSubTo33(int param) {
        String numStr="";
        String sub="";
        try {
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(param));
            StringBuilder reverse = stringBuilder.reverse();
            StringBuilder builder = new StringBuilder(reverse.substring(0, 4)).reverse();
            sub = builder.toString();
            String delete = reverse.delete(0, 4).reverse().toString();
            //输入一个参数获取对应的枚举类的值
            numStr = BinaryConversionEnum.getNumStrByNum(Integer.parseInt(delete));

        }catch (Exception e){
            e.printStackTrace();
        }
        return numStr + sub;
    }

    public static void main(String[] args) {
      /*  String param1="1922AA8M9998";
        String sn1= param1.substring(0,param1.length() - 5);
        System.out.println(sn1);*/
        List<String> betweenValues = getBetweenValues("1922AA8B9998", "1922AA8C1000");
        System.out.println(betweenValues);

    }
}
