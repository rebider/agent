package com.ryx.credit.cms.util;

import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @date 2018/9/18
 * @description POI导出工具类
 */
public class PoiUtil {
 
    private final static Logger logger = LoggerFactory.getLogger(PoiUtil.class);
    /**
     * 每个sheet存储的记录数 100W
     */
//    public static Integer PER_SHEET_ROW_COUNT = 1000000;
    /**
     * 每次向EXCEL写入的记录数(查询每页数据大小) 200
     */
//    public static final Integer PER_WRITE_ROW_COUNT = 200;
    /**
     * 每个sheet的写入次数 5
     */
//    public static final Integer PER_SHEET_WRITE_COUNT = PER_SHEET_ROW_COUNT / PER_WRITE_ROW_COUNT;


    /**
     * 初始化EXCEL(sheet个数和标题)
     *
     * @param totalRowCount 总记录数
     * @param titles        标题集合
     * @return XSSFWorkbook对象
     */
    public static SXSSFWorkbook initExcel(Integer totalRowCount, String[] titles,Integer perSheetRowCount) {
 
        // 在内存当中保持 100 行 , 超过的数据放到硬盘中在内存当中保持 100 行 , 超过的数据放到硬盘中
        SXSSFWorkbook wb = new SXSSFWorkbook(100);
 
        Integer sheetCount = ((totalRowCount % perSheetRowCount == 0) ?
                (totalRowCount / perSheetRowCount) : (totalRowCount / perSheetRowCount + 1));
 
        // 根据总记录数创建sheet并分配标题
        for (int i = 0; i < sheetCount; i++) {
            SXSSFSheet sheet = wb.createSheet("sheet" + (i + 1));
            SXSSFRow headRow = sheet.createRow(0);
 
            for (int j = 0; j < titles.length; j++) {
                SXSSFCell headRowCell = headRow.createCell(j);
                headRowCell.setCellValue(titles[j]);
            }
        }
 
        return wb;
    }
 
 
    /**
     * 下载EXCEL到本地指定的文件夹
     *
     * @param wb         EXCEL对象SXSSFWorkbook
     * @param exportPath 导出路径
     */
    public static void downLoadExcelToLocalPath(SXSSFWorkbook wb, String exportPath) {
        FileOutputStream fops = null;
        try {
            fops = new FileOutputStream(exportPath);
            wb.write(fops);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != wb) {
                try {
                    wb.dispose();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (null != fops) {
                try {
                    fops.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
 
 
    /**
     * 下载EXCEL到浏览器
     *
     * @param wb       EXCEL对象XSSFWorkbook
     * @param response
     * @param fileName 文件名称
     * @throws IOException
     */
    public static void downLoadExcelToWebsite(SXSSFWorkbook wb, HttpServletResponse response, String fileName) throws IOException {
 
        response.setHeader("Content-disposition", "attachment; filename="
                + new String((fileName + ".xlsx").getBytes("utf-8"), "ISO8859-1"));//设置下载的文件名
 
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            wb.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != wb) {
                try {
                    wb.dispose();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
 
 
    /**
     * 导出Excel到本地指定路径
     *
     * @param totalRowCount           总记录数
     * @param titles                  标题
     * @param exportPath              导出路径
     * @param writeExcelDataDelegated 向EXCEL写数据/处理格式的委托类 自行实现
     * @throws Exception
     */
    public static final void exportExcelToLocalPath(Integer totalRowCount, String[] titles, String exportPath, WriteExcelDataDelegated writeExcelDataDelegated,Integer perSheetRowCount,Integer perWriteRowCount) throws Exception {

        Integer perSheetWriteCount = perSheetRowCount / perWriteRowCount;
        // 初始化EXCEL
        SXSSFWorkbook wb = PoiUtil.initExcel(totalRowCount, titles,perSheetRowCount);
        // 调用委托类分批写数据
        int sheetCount = wb.getNumberOfSheets();
        for (int i = 0; i < sheetCount; i++) {
            SXSSFSheet eachSheet = wb.getSheetAt(i);
            for (int j = 1; j <= perSheetWriteCount; j++) {
                int currentPage = i * perSheetWriteCount + j;
                int pageSize = perWriteRowCount;
                int startRowCount = (j - 1) * perWriteRowCount + 1;
                int endRowCount = startRowCount + pageSize - 1;
                writeExcelDataDelegated.writeExcelData(eachSheet, startRowCount, endRowCount, currentPage, pageSize);
            }
        }
        // 下载EXCEL
        PoiUtil.downLoadExcelToLocalPath(wb, exportPath);
    }


    /**
     * 导出Excel到浏览器
     *
     * @param response
     * @param totalRowCount           总记录数
     * @param fileName                文件名称
     * @param titles                  标题
     * @param writeExcelDataDelegated 向EXCEL写数据/处理格式的委托类 自行实现
     * @throws Exception
     */
    public static final void exportExcelToWebsite(HttpServletResponse response, Integer totalRowCount, String fileName, String[] titles, WriteExcelDataDelegated writeExcelDataDelegated,
                                                  Integer perSheetRowCount,Integer perWriteRowCount) throws Exception {

        Integer perSheetWriteCount = perSheetRowCount / perWriteRowCount;
        // 初始化EXCEL
        SXSSFWorkbook wb = PoiUtil.initExcel(totalRowCount, titles,perSheetRowCount);
        // 调用委托类分批写数据
        int sheetCount = wb.getNumberOfSheets();
        for (int i = 0; i < sheetCount; i++) {
            SXSSFSheet eachSheet = wb.getSheetAt(i);
            for (int j = 1; j <= perSheetWriteCount+1; j++) {
                int currentPage = i * perSheetWriteCount + j;
                int pageSize = perWriteRowCount;
                int startRowCount = (j - 1) * perWriteRowCount + 1;
                int endRowCount = startRowCount + pageSize - 1;
                writeExcelDataDelegated.writeExcelData(eachSheet, startRowCount, endRowCount, currentPage, pageSize);
            }
        }
        // 下载EXCEL
        PoiUtil.downLoadExcelToWebsite(wb, response, fileName);
    }
 
}