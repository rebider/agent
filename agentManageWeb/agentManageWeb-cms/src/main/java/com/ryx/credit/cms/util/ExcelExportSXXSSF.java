package com.ryx.credit.cms.util;

import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
/**
 * excel导出的封装类
 * juf
 */
public class ExcelExportSXXSSF {

	private SXSSFWorkbook wb;

	private Sheet sh;

	private int flushRows;

	private int rownum;

	private int colnum;

	private String filePath;

	private String fileWebPath;

	private String filePrefix;

	private String fileAllPath;

	private List<String> fieldNames;

	private List<String> fieldCodes;

	private ExcelExportSXXSSF() {

	}

	public static ExcelExportSXXSSF start(String filePath, String fileWebPath,String filePrefix,
			List<String> fieldNames,List<String> fieldCodes, int flushRows) throws Exception {
		ExcelExportSXXSSF excelExportSXXSSF = new ExcelExportSXXSSF();
		excelExportSXXSSF.setFilePath(filePath);
		excelExportSXXSSF.setFileWebPath(fileWebPath);
		excelExportSXXSSF.setFilePrefix(filePrefix);
		excelExportSXXSSF.setFieldNames(fieldNames);
		excelExportSXXSSF.setFieldCodes(fieldCodes);
		excelExportSXXSSF.setWb(new SXSSFWorkbook(flushRows));//创建workbook
		excelExportSXXSSF.setSh(excelExportSXXSSF.getWb().createSheet());//创建sheet
		excelExportSXXSSF.writeTitles();
		return excelExportSXXSSF;
	}

	/**
	 * 设置导入文件的标题
	 * 开始生成导出excel的标题
	 * @throws Exception
	 */
	private void writeTitles() throws Exception {
		rownum = 0;//第0行
		colnum = fieldNames.size();//根据列标题得出列数
		Row row = sh.createRow(rownum);
		for (int cellnum = 0; cellnum < colnum; cellnum++) {
			Cell cell = row.createCell(cellnum);
			cell.setCellValue(fieldNames.get(cellnum));
		}
	}

	public void writeDatasByObject(List datalist) throws Exception {

		for (int j = 0; j < datalist.size(); j++) {
			rownum = rownum + 1;
			Row row = sh.createRow(rownum);
			for (int cellnum = 0; cellnum < fieldCodes.size(); cellnum++) {
				Object owner = datalist.get(j);
				Object value = invokeMethod(owner, fieldCodes.get(cellnum),
						new Object[] {});
				Cell cell = row.createCell(cellnum);
				cell.setCellValue(value!=null?value.toString():"");
			}

		}

	}

	public void writeDatasByString(List<String> datalist) throws Exception {
			rownum = rownum + 1;
			Row row = sh.createRow(rownum);
			int datalist_size = datalist.size();
			for (int cellnum = 0; cellnum < colnum; cellnum++) {
				Cell cell = row.createCell(cellnum);
				if(datalist_size>cellnum){
					cell.setCellValue(datalist.get(cellnum));
				}else{
					cell.setCellValue("");
				}
				
			}
	}


	public void flush(int flushNum) throws Exception {
		((SXSSFSheet) sh).flushRows(flushNum);
	}

	/**
	 * 导出文件
	 * 
	 * @throws Exception
	 */
	public String exportFile() throws Exception {
		String filename = filePrefix+"_"+MyUtil.getCurrentTimeStr() + ".xlsx";
		FileOutputStream out = new FileOutputStream(filePath + filename);
		wb.write(out);
		out.flush();
		out.close();
		setFileAllPath(fileWebPath + filename);
		return filePath + filename;
	}

	private Object invokeMethod(Object owner, String fieldname, Object[] args)
			throws Exception {

		String methodName = "get" + fieldname.substring(0, 1).toUpperCase()
				+ fieldname.substring(1);
		Class ownerClass = owner.getClass();

		Class[] argsClass = new Class[args.length];

		for (int i = 0, j = args.length; i < j; i++) {
			argsClass[i] = args[i].getClass();
		}

		Method method = ownerClass.getMethod(methodName, argsClass);
		return method.invoke(owner, args);
	}

	public SXSSFWorkbook getWb() {
		return wb;
	}

	public void setWb(SXSSFWorkbook wb) {
		this.wb = wb;
	}

	public Sheet getSh() {
		return sh;
	}

	public void setSh(Sheet sh) {
		this.sh = sh;
	}


	public int getFlushRows() {
		return flushRows;
	}

	public void setFlushRows(int flushRows) {
		this.flushRows = flushRows;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileWebPath() {
		return fileWebPath;
	}

	public void setFileWebPath(String fileWebPath) {
		this.fileWebPath = fileWebPath;
	}

	public List<String> getFieldNames() {
		return fieldNames;
	}

	public void setFieldNames(List<String> fieldNames) {
		this.fieldNames = fieldNames;
	}

	public List<String> getFieldCodes() {
		return fieldCodes;
	}

	public void setFieldCodes(List<String> fieldCodes) {
		this.fieldCodes = fieldCodes;
	}

	public int getRownum() {
		return rownum;
	}

	public String getFilePrefix() {
		return filePrefix;
	}

	public void setFilePrefix(String filePrefix) {
		this.filePrefix = filePrefix;
	}

	public int getColnum() {
		return colnum;
	}

	public String getFileAllPath() {
		return fileAllPath;
	}

	public void setFileAllPath(String fileAllPath) {
		this.fileAllPath = fileAllPath;
	}
	
}
