package com.ryx.credit.common.util;

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
        long start = System.currentTimeMillis();
        final List<List<String>> table = new ArrayList<>();
        new ExcelEventParser(fileUrl).setHandler(new ExcelEventParser.SimpleSheetContentsHandler(){
            private List<String> fields;
            @Override
            public void endRow(int rowNum) {
                if(rowNum == 0){
                    fields = row;
                }else {
                    // 数据
                    table.add(row);
                }
            }
        }).parse();
        long end = System.currentTimeMillis();
        System.err.println(table.size());
        System.err.println(end - start);
        return table;
    }

}
