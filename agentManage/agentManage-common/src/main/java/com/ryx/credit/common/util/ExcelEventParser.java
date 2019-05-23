package com.ryx.credit.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.util.SAXHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/***
 * 大数据excel文件解析
 * @Author liudh
 * @Description //TODO
 * @Date 2019/5/23 11:21
 * @Param
 * @return
 **/
public class ExcelEventParser {
    /**
     * Uses the XSSF Event SAX helpers to do most of the work
     * of parsing the Sheet XML, and outputs the contents
     * as a (basic) CSV.
     */
    private class SheetToCSV implements SheetContentsHandler {
        private boolean firstCellOfRow = false;
        private int currentRow = -1;
        private int currentCol = -1;

        private void outputMissingRows(int number) {
            for (int i = 0; i < number; i++) {
                curstr = new ArrayList<String>();
                for (int j = 0; j < minColumns; j++) {
                    curstr.add(null);
                }
                output.add(curstr);
            }
        }

        @Override
        public void startRow(int rowNum) {
            curstr = new ArrayList<String>();
            outputMissingRows(rowNum - currentRow - 1);
            firstCellOfRow = true;
            currentRow = rowNum;
            currentCol = -1;
        }

        @Override
        public void endRow(int rowNum) {
            if(rowNum==0)return;
            for (int i = currentCol; i < minColumns ; i++) {
                curstr.add(null);
            }
            output.add(curstr);
        }

        @Override
        public void cell(String cellReference, String formattedValue,
                         XSSFComment comment) {
            if (cellReference == null) {
                cellReference = new CellAddress(currentRow, currentCol).formatAsString();
            }

            int thisCol = (new CellReference(cellReference)).getCol();
            int missedCols = thisCol - currentCol - 1;
            for (int i = 0; i < missedCols; i++) {
                curstr.add(null);
            }
            currentCol = thisCol;

            try {
                Double.parseDouble(formattedValue);
                curstr.add(formattedValue);
            } catch (NumberFormatException e) {
//                if(StringUtils.isNotBlank(formattedValue.trim())){
                    curstr.add(formattedValue);
//                }
            }
        }

        @Override
        public void headerFooter(String text, boolean isHeader, String tagName) {
            // Skip, no headers or footers in CSV
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

    private List<List<String>> output;
    private List<String> curstr;

    public  List<List<String>> get_output(){
        return output;
    }

    /**
     * Creates a new XLSX -> CSV converter
     */
    public ExcelEventParser(OPCPackage pkg, int minColumns) {
        this.xlsxPackage = pkg;
        this.minColumns = minColumns;
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
        DataFormatter formatter = new DataFormatter();
        InputSource sheetSource = new InputSource(sheetInputStream);
        try {
            XMLReader sheetParser = SAXHelper.newXMLReader();
            ContentHandler handler = new XSSFSheetXMLHandler(
                    styles, null, strings, sheetHandler, formatter, false);
            sheetParser.setContentHandler(handler);
            sheetParser.parse(sheetSource);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("SAX parser appears to be broken - " + e.getMessage());
        }
    }

    /**
     * Initiates the processing of the XLS workbook file to CSV.
     *
     * @throws IOException
     * @throws OpenXML4JException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public void process()
            throws IOException, OpenXML4JException, ParserConfigurationException, SAXException {
        ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(this.xlsxPackage);
        XSSFReader xssfReader = new XSSFReader(this.xlsxPackage);
        StylesTable styles = xssfReader.getStylesTable();
        XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
        int index = 0;
        while (iter.hasNext()) {
            output = new ArrayList<>();
            InputStream stream = iter.next();
            String sheetName = iter.getSheetName();
            System.out.println("正在读取sheet： "+sheetName + " [index=" + index + "]:");
            processSheet(styles, strings, new SheetToCSV(), stream);
            System.out.println("sheet 读取完成!");
            stream.close();
            ++index;
        }
    }

}
