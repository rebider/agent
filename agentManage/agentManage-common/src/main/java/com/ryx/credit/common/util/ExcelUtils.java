package com.ryx.credit.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelUtils {


    /**
     *tempPath 模板文件路径
     *path 文件路径
     *list 集合数据
     */
    public static void exportExcel(String tempPath, String path, List<HashMap<String,String>> list) {
        File newFile = createNewFile(tempPath, path);
        InputStream is = null;
        XSSFWorkbook workbook = null;
        XSSFSheet sheet = null;
        try {
            is = new FileInputStream(newFile);// 将excel文件转为输入流
            workbook = new XSSFWorkbook(is);// 创建个workbook，
            // 获取第一个sheet
            sheet = workbook.getSheetAt(0);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        if (sheet != null) {
            try {
                // 写数据
                FileOutputStream fos = new FileOutputStream(newFile);
                XSSFRow row = sheet.getRow(0);
                if (row == null) {
                    row = sheet.createRow(0);
                }
                XSSFCell cell = row.getCell(0);
                if (cell == null) {
                    cell = row.createCell(0);
                }
                cell.setCellValue("我是标题");

                for (int i = 0; i < list.size(); i++) {
                	HashMap<String,String> vo = list.get(i);
                    row = sheet.createRow(i+1); //从第2行开始

                    //这里就可以使用sysUserMapper，做相应的操作
                    //User user = excelUtils.sysUserMapper.selectByPrimaryKey(vo.getId());                  

                    //根据excel模板格式写入数据....
                    createRowAndCell(vo.get("subProductId"), row, cell, 0);//产品编号
                    createRowAndCell(vo.get("agentId"), row, cell, 1);//代理商
                    createRowAndCell(vo.get("repayType"), row, cell, 2);//还款状态
                    createRowAndCell(vo.get("endDate"), row, cell, 3);//到期日期
                    createRowAndCell(vo.get("contractId"), row, cell, 4);//合同编号
                    createRowAndCell(vo.get("payDate"), row, cell, 5);//借款日期
                    createRowAndCell(vo.get("repayDate"), row, cell, 6);//还款日期
                    createRowAndCell(vo.get("custId"), row, cell, 7);//身份证号
                    createRowAndCell(vo.get("custName"), row, cell, 8);//姓名
                    createRowAndCell(vo.get("peroid"), row, cell, 9);//当前期数
                    createRowAndCell(vo.get("loanAmt"), row, cell, 10);//合同金额
                    createRowAndCell(vo.get("payY"), row, cell, 11);//已还本金
                    createRowAndCell(vo.get("payP"), row, cell, 12);//已还罚息
                    createRowAndCell(vo.get("payAll"), row, cell, 13);//已还总额
                    createRowAndCell(vo.get("surplusY"), row, cell, 14);//剩余应还本金
                    createRowAndCell(vo.get("surplusAll"), row, cell, 15);//剩余应还总额
                    createRowAndCell(vo.get("unpayS"), row, cell, 16);//未还服务费
                    createRowAndCell(vo.get("unpayT"), row, cell, 17);//未还利息
                    createRowAndCell(vo.get("payS"), row, cell, 18);//已还服务费
                    createRowAndCell(vo.get("payT"), row, cell, 19);//已还利息
                    createRowAndCell(vo.get("payG"), row, cell, 20);//溢交款金额
                    createRowAndCell(vo.get("overdueDays"), row, cell, 21);//逾期天数
                    //.....
                }
                workbook.write(fos);
                fos.flush();
                fos.close();

                // 下载
                InputStream fis = new BufferedInputStream(new FileInputStream(
                        newFile));
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != is) {
                        is.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        // 删除创建的新文件
        deleteFile(newFile);
    }

    /**
     *根据当前row行，来创建index标记的列数,并赋值数据
     */
    private static void createRowAndCell(Object obj, XSSFRow row, XSSFCell cell, int index) {
        cell = row.getCell(index);
        if (cell == null) {
            cell = row.createCell(index);
        }

        if (obj != null)
            cell.setCellValue(obj.toString());
        else 
            cell.setCellValue("");
    }

    /**
     * 复制文件
     * 
     * @param s
     *            源文件
     * @param t
     *            复制到的新文件
     */

    public static void fileChannelCopy(File s, File t) {
        try {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = new BufferedInputStream(new FileInputStream(s), 1024);
                out = new BufferedOutputStream(new FileOutputStream(t), 1024);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
            } finally {
                if (null != in) {
                    in.close();
                }
                if (null != out) {
                    out.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 读取excel模板，并复制到新文件中供写入和下载
     * 
     * @return
     */
    public static File createNewFile(String tempPath, String rPath) {
        // 读取模板，并赋值到新文件************************************************************
        // 文件模板路径
        String path = (tempPath);
        File file = new File(path);
        // 保存文件的路径
        String realPath = rPath;
        // 新的文件名
        String newFileName = DateUtil.getDateToString(new Date()) + "_daily.xlsx";
        // 判断路径是否存在
        File dir = new File(realPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 写入到新的excel
        File newFile = new File(realPath, newFileName);
        try {
            newFile.createNewFile();
            // 复制模板到新文件
            fileChannelCopy(file, newFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newFile;
    }

    /**
     * 下载成功后删除
     * 
     * @param files
     */
    private static void deleteFile(File... files) {
        for (File file : files) {
            if (file.exists()) {
                //file.delete();
            }
        }
    }
    
    public static void main(String args[]){
    	List list = new ArrayList<>();
    	Map<String, String> map1 = new HashMap<String,String>();
    	map1.put("a", "122");
    	map1.put("b", "01.02");
    	map1.put("c", "已结清");
    	map1.put("d", "2018/3/8");
    	map1.put("e", "CO201802080000007094");
    	map1.put("f", "2018/2/8");
    	map1.put("g", "2018/3/8");
    	map1.put("h", "370105198608021123");
    	
    	Map<String,String> map2 = new HashMap<String,String>();
    	map2.put("a", "122");
    	map2.put("b", "01.02");
    	map2.put("c", "未结清");
    	map2.put("d", "2018/3/8");
    	map2.put("e", "CO201802080000007094");
    	map2.put("f", "2018/2/8");
    	map2.put("g", "");
    	map2.put("h", "370105198608021123");
    	list.add(map1);
    	list.add(map2);
    	exportExcel("D:/dayinfo.xlsx","D:/",list);
    }
}