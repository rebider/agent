package com.ryx.credit.profit.unitmain;

import com.alibaba.dubbo.common.json.JSON;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/***
 * @Author renshenghao
 * @Description //TODO
 * @Date 2020/04/28
 * @Param
 * @return
 **/
public class BigDataExcelToMapUtils {

    public static List<List<Map<String,String>>>  bigDataGetExcel(String fileUrl,String importType,String uploadTime,String uploadUser){
        List<List<Map<String,String>>> table = new ArrayList<>();
        try {
            OPCPackage p;
            p = OPCPackage.open(fileUrl, PackageAccess.READ);
            ExcelEventParserToMap xlsx2csv = new ExcelEventParserToMap(p, 60); // 20代表最大列数
            xlsx2csv.process(importType, uploadTime, uploadUser);
            table = xlsx2csv.get_output();
            p.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return table;
    }

    public static void main(String[] args){

        List<List<Map<String,String>>> lists = bigDataGetExcel("G:/excelFile/test.xlsx","01","2020/04/28 00:00:00","567");
        String json = null;
        try {
            json = JSON.json(lists);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(json);

    }
}
