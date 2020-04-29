package com.ryx.credit.profit.unitmain;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.util.SAXHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/***
 * 大数据excel文件解析
 * @Author renshenghao
 * @Description //TODO
 * @Date 2020/04/28
 * @Param
 * @return
 **/
public class ExcelEventParserToMap {
    /**
     * Uses the XSSF Event SAX helpers to do most of the work
     * of parsing the Sheet XML, and outputs the contents
     * as a (basic) CSV.
     */

    private static String[] keys = {"agentId","agentName",
            "month","brandCode", "f7","f8","f9","f10",
            "f11","f12","f13","f14","f15","f16","f17","f18","f19","f20",
            "f21","f22","f23","f24","f25","f26","f27","f28","f29","f30",
            "f31","f32","f33","f34","f35","f36","f37","f38","f39","f40",
            "f41","f42","f43","f44","f45","f46","f47","f48","f49","f50",
            "f51","f52","f53","f54","f55","f56","f57","f58","f59","f60"};


    private String importType;
    private String sheetName;
    private String sheetOrder;
    private String uploadUser;
    private String uploadTime;

    private class SheetToCSV implements SheetContentsHandler {
        private String numberOfCells = "0";
        private int currentRow = -1;
        private int currentCol = -1;
        @Override
        public void startRow(int rowNum) {
            curstr = new HashMap<>();
            curstr.put("id",getUUIDForId());//id 主键
            curstr.put("importType",importType);//导入类型
            curstr.put("sheetName",sheetName);//sheet名
            curstr.put("uploadUser",uploadUser);//上传人
            curstr.put("uploadTime",uploadTime);//上传时间
            curstr.put("sheetOrder",sheetOrder);//sheet页号
            curstr.put("rowOrder",(rowNum+1)+"");//行号
            currentCol = -1;
            currentRow = rowNum;
        }

        @Override
        public void endRow(int rowNum) {
            if (rowNum == 0){
                numberOfCells = String.valueOf(currentCol+1);
            }
            curstr.put("sheetColumn",numberOfCells);//列数
            if(curstr.size()>8)
                output.add(curstr);
        }

        @Override
        public void cell(String cellReference, String formattedValue,
                         XSSFComment comment) {
            if(StringUtils.isNotBlank(formattedValue.trim())){


                if (cellReference == null) {
                    cellReference = new CellAddress(currentRow, currentCol).formatAsString();
                }

                int thisCol = (new CellReference(cellReference)).getCol();

                int missedCols = thisCol - currentCol - 1;
                for (int i = 0; i < missedCols; i++) {
                    curstr.put(keys[thisCol],null);
                }
                currentCol = thisCol;
                if (currentRow != 0 && thisCol >= Integer.valueOf(numberOfCells))
                    return;

                try {
                    Double.parseDouble(formattedValue);
                    curstr.put(keys[thisCol],formattedValue);
                } catch (NumberFormatException e) {
                    curstr.put(keys[thisCol],formattedValue);
                }
            }
        }

        @Override
        public void headerFooter(String text, boolean isHeader, String tagName) {
            // Skip, no headers or footers in CSV
        }

        private String getUUIDForId() {
            return UUID.randomUUID().toString().replaceAll("-", "");
        }
    }


    private final OPCPackage xlsxPackage;

    /**
     * Number of columns to read starting with leftmost
     */
    private final int minColumns;

    /**
     * Destination for data
     */

    private List<List<Map<String,String>>> data;
    private List<Map<String,String>> output;
    private Map<String,String> curstr;


    public  List<List<Map<String,String>>> get_output(){
        return data;
    }

    /**
     * Creates a new XLSX -> CSV converter
     */

    public ExcelEventParserToMap(OPCPackage pkg, int minColumns) {
        this.xlsxPackage = pkg;
        this.minColumns = minColumns;
    }
    private SheetContentsHandler handler;

    public ExcelEventParserToMap setHandler(SheetContentsHandler handler) {
        this.handler = handler;
        return this;
    }

    /**
     * Parses and shows the content of one sheet
     * using the specified styles and shared-strings tables.
     *
     * @param styles
     * @param strings
     * @param sheetInputStream
     */
    public void processSheet(
            StylesTable styles,
            ReadOnlySharedStringsTable strings,
            SheetContentsHandler sheetHandler,
            InputStream sheetInputStream)
            throws IOException, ParserConfigurationException, SAXException {
        XMLReader sheetParser = SAXHelper.newXMLReader();

        if(handler != null){
            sheetParser.setContentHandler(new XSSFSheetXMLHandler(styles, strings, handler, false));
        }else{
            sheetParser.setContentHandler(new XSSFSheetXMLHandler(styles, strings, new ExcelEventParserToMap.SheetToCSV(), false));
        }
        sheetParser.parse(new InputSource(sheetInputStream));
    }

    /**
     * Initiates the processing of the XLS workbook file to CSV.
     *
     * @throws IOException
     * @throws OpenXML4JException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public void process(String importType,String uploadTime,String uploadUser)
            throws IOException, OpenXML4JException, ParserConfigurationException, SAXException {
        ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(this.xlsxPackage);
        XSSFReader xssfReader = new XSSFReader(this.xlsxPackage);
        StylesTable styles = xssfReader.getStylesTable();
        XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
        data = new ArrayList<>();
        this.importType = importType;
        this.uploadTime = uploadTime;
        this.uploadUser = uploadUser;
        int index = 0;
        while (iter.hasNext()) {
            sheetOrder = String.valueOf(index+1);
            output = new ArrayList<>();
            InputStream stream = iter.next();
            sheetName = iter.getSheetName();
            processSheet(styles, strings, new SheetToCSV(), stream);
            data.add(output);
            stream.close();
            ++index;
        }
    }

}
