package com.ryx.credit.common.util;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * @author yangmx
 * @desc execl导出
 */
public class ExcelUtil {
    private static SXSSFWorkbook ssWork;

    @SuppressWarnings("unchecked")
    public static void downLoadExcel (Map<String, Object> param) {
        String path = (String)param.get("path");
        String title = (String)param.get("title");
        String column = (String)param.get("column");
        List<Map<String, Object>> list = (List<Map<String, Object>>) param.get("dataList");
        HttpServletResponse response = (HttpServletResponse)param.get("response");
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 文件全路径
        String fileName = path + File.separator + System.nanoTime() + ".xlsx";

        File f = new File(fileName);

        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            ExcelUtil.exportExcel(fileName, ExcelUtil.getDatasForExcel(list, column), Arrays.asList(title.split(",")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        ExcelUtil.downloadFile(fileName, response);
    }

    /**
     * 转换成excel数据集合
     * @author : zhaodw
     * @datetime : 2017年7月26日 下午4:28:50
     */
    public static List<List<String>> getDatasForExcel(List<Map<String, Object>> list, String columns) {
        List<List<String>> datas = new ArrayList<>();

        if (list.size() > 0) {
            String[] names = columns.split(",");
            for (Map<String, Object> dataMap : list) {
                List<String> data = new ArrayList<>();
                for (String name : names) {
                    data.add(dataMap.get(name) == null ? "" : dataMap.get(name).toString());
                }
                datas.add(data);
                data = null;
            }
        }
        return datas;
    }

    public static void exportExcel(String path, List<List<String>> datas,
                                   List<String> columnNames) throws Exception {
        ssWork = new SXSSFWorkbook(5000);
        FileOutputStream out = new FileOutputStream(path);
        setValue(datas, columnNames);
        ssWork.write(out);
        if (out != null) {
            out.flush();
            out.close();
        }

    }

    public static void setBorder(CellStyle cellStyle) {
        cellStyle.setBorderBottom(BorderStyle.HAIR);
        cellStyle.setBorderLeft(BorderStyle.HAIR);
        cellStyle.setBorderTop(BorderStyle.HAIR);
        cellStyle.setBorderRight(BorderStyle.HAIR);
    }

    public static void setValue(List<List<String>> datas,
                                List<String> columnNames) throws Exception {
        CellStyle cellStyle = ssWork.createCellStyle();
        setBorder(cellStyle);
        SXSSFSheet sheet = ssWork.createSheet();
        if (columnNames != null && columnNames.size() > 0) {
            Row index = sheet.createRow(0);

            for (int i$ = 0; i$ < columnNames.size(); ++i$) {
                Cell data = index.createCell(i$);
                data.setCellStyle(cellStyle);
                data.setCellValue((String) columnNames.get(i$));
            }
        }

        if (datas != null && datas.size() > 0) {
            int arg9 = 1;

            for (Iterator arg10 = datas.iterator(); arg10.hasNext(); ++arg9) {
                List arg11 = (List) arg10.next();
                Row row = sheet.createRow(arg9);

                for (int j = 0; j < columnNames.size(); ++j) {
                    Cell cell = row.createCell(j);
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(arg11.get(j) == null
                            ? ""
                            : (String) arg11.get(j));
                }
            }
        }
    }

    public static void downloadFile(String path, HttpServletResponse response) {
        try {
            File ex = new File(path);
            String filename = ex.getName();
            BufferedInputStream fis = new BufferedInputStream(
                    new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename="
                    + new String(filename.getBytes("UTF-8"), "iso-8859-1"));
            response.addHeader("Content-Length", "" + ex.length());
            BufferedOutputStream toClient = new BufferedOutputStream(
                    response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException arg6) {
            arg6.printStackTrace();
        }
    }
}
