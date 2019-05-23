package com.ryx.credit.common.util;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;

import java.util.ArrayList;
import java.util.List;

/***
 * @Author liudh
 * @Description //TODO 
 * @Date 2019/5/20 14:52
 * @Param
 * @return
 **/
public class BigDataExcelUtils {

    public static List<List<String>>  bigDataGetExcel(String fileUrl){
        List<List<String>> table = new ArrayList<>();
        try {
            OPCPackage p;
            p = OPCPackage.open(fileUrl, PackageAccess.READ);
            ExcelEventParser xlsx2csv = new ExcelEventParser(p, 20); // 20代表最大列数
            xlsx2csv.process();
            table = xlsx2csv.get_output();
            p.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return table;
    }

}
