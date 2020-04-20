package com.ryx.credit.profit.service.impl;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.dao.ImportDataWithProfitMapper;
import com.ryx.credit.profit.dao.PmsProfitLogMapper;
import com.ryx.credit.profit.pojo.ImportDataWithProfitExample;
import com.ryx.credit.profit.pojo.PmsProfitLog;
import com.ryx.credit.profit.service.IImportDataWithProfitService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

@Service("importDataWithProfitService")
public class ImportDataWithProfitServiceImpl implements IImportDataWithProfitService {

    Logger logger = LoggerFactory.getLogger(ImportDataWithProfitServiceImpl.class);


    @Autowired
    private PmsProfitLogMapper pmsProfitLogMapper;
    @Autowired
    private ImportDataWithProfitMapper importDataWithProfitMapper;

    @Override
    public int insertImportLog(PmsProfitLog importLog) {
        return pmsProfitLogMapper.insert(importLog);
    }

    @Override
    public PmsProfitLog getImportLogByBatchCode(String batchCode) {
        return pmsProfitLogMapper.selectByPrimaryKey(batchCode);
    }

    @Override
    public int insertProfitData(List<Map<String, Object>> profitDatas) {    //批量插入，每次插入1000条数据

        int num = profitDatas.size();
        int resNum = 0;
        int temp = num / 1000;
        for (int i = 0; i <= temp; i++) {
            int fromIndex = 1000*i;
            int toIndex = 1000*(i+1);
            if (toIndex>num)
                toIndex = num;
            List<Map<String, Object>> list = profitDatas.subList(fromIndex, toIndex);
            int re = importDataWithProfitMapper.insertProfitData(list);
            resNum+=re;
        }
        return resNum;

    }

    @Override
    public int deleteProfitDataByProfitInfo(Map<String, Object> profitInfo) {
        ImportDataWithProfitExample example = new ImportDataWithProfitExample();
        ImportDataWithProfitExample.Criteria criteria = example.createCriteria();
        criteria.andMonthEqualTo(profitInfo.get("month").toString());
        criteria.andUploadUserEqualTo(profitInfo.get("uploadUser").toString());
        criteria.andImportTypeEqualTo(profitInfo.get("importType").toString());
        return importDataWithProfitMapper.deleteByExample(example);
    }

    @Override
    public int deleteProfitDataByBatchCode(String batchCode) {
        return importDataWithProfitMapper.deleteProfitDataByBatchCode(batchCode);
    }

    @Override
    public String insertProfitDataByBatchCode(String batchCode) throws Exception {
        //1. 根据batchCode找到文件路径
        PmsProfitLog log =  getImportLogByBatchCode(batchCode);
        if (log==null){
            throw new MessageException("未找到该记录");
        }

        String path = log.getUploadPath();
        File file = new File(path);
        
        //2.读取文件
        FileInputStream inputStream = new FileInputStream(file);

        String extensionName = path.substring(path.lastIndexOf(".")+1);//扩展名
        Workbook data = null;
        if ("xls".equals(extensionName)){
            data = new HSSFWorkbook(inputStream);
        }else if ("xlsx".equals(extensionName)){
            data = new XSSFWorkbook(inputStream);
        }else{
            //文件类型错误，更新记录
            pmsProfitLogMapper.updataNoteAndStatusByBatchCode(batchCode,"1","数据导入失败，文件类型异常！");
            return "数据导入失败，请检查文件类型。";
        }

        //校验数据

        Map<String, String> resultMap;
        //1.校验数据完整性，固定列数据
        resultMap = checkExcelData(data);
        if ("Error".equals(resultMap.get("resultCode"))){    //存在异常数据，生成备注更新导入结果
            String errMsg = resultMap.get("errMsg");
            pmsProfitLogMapper.updataNoteAndStatusByBatchCode(batchCode,"1",errMsg);
            return "数据导入失败,请检查导入文件";
        }
        //2.校验代理商信息
        resultMap = checkAgentData(data,log.getMonth());
        if ("Error".equals(resultMap.get("resultCode"))){    //存在异常数据，生成备注更新导入结果
            String errMsg = resultMap.get("errMsg");
            pmsProfitLogMapper.updataNoteAndStatusByBatchCode(batchCode,"1",errMsg);
            return "数据导入失败,请确认导入数据的完整性";
        }


        importDataWithProfitMapper.deleteProfitDataByBatchCode(batchCode);//插入数据之前先删除已存在的数据

        //插入数据
        int numOfSheets = data.getNumberOfSheets();
        Executor executor = new ThreadPoolExecutor(numOfSheets, numOfSheets,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        List<FutureTask<Map<String,String>>> taskList = new ArrayList();

        for (int i = 0; i < numOfSheets; i++) {
            Sheet sheet = data.getSheetAt(i);
            SheetThead sheetThead = new SheetThead(log,sheet,i+1);
            FutureTask<Map<String,String>> thread = new FutureTask<>(sheetThead);
            taskList.add(thread);
            executor.execute(thread);
        }

        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
        threadPoolExecutor.shutdown();
        while (true) {  //等待线程执行完成
            if (threadPoolExecutor.getActiveCount() <= 0) {
                break;
            }
        }

        int size = taskList.size();
        if (size!=numOfSheets){ //进程任务数量与sheet页个数不一致
            importDataWithProfitMapper.deleteProfitDataByBatchCode(batchCode);//手动回滚
            pmsProfitLogMapper.updataNoteAndStatusByBatchCode(batchCode,"1","进程任务数量与sheet页个数不一致");
            return "数据导入失败";
        }

        boolean isSuccess = true;//任务结果标记
        StringBuffer tempBuffer = new StringBuffer();
        for (int i = 0; i < size; i++) {
            FutureTask<Map<String, String>> task = taskList.get(i);
            Map<String, String> taskResult = task.get();
            String resultCode = taskResult.get("resultCode");
            String errMsg = null;
            if (!"Success".equals(resultCode)){
                isSuccess = false;
                errMsg = taskResult.get("errMsg");
            }
            tempBuffer.append(errMsg);

            if (tempBuffer.length()>1200){
                String substring = tempBuffer.substring(0, 1200);
                tempBuffer = new StringBuffer(substring);
                tempBuffer.append("...等其他异常数据");
                break;
            }
        }

        if (!isSuccess){
            importDataWithProfitMapper.deleteProfitDataByBatchCode(batchCode);//手动回滚
            pmsProfitLogMapper.updataNoteAndStatusByBatchCode(batchCode,"1",tempBuffer.toString());
            return "数据导入失败";
        }

        //数据导入成功
        pmsProfitLogMapper.updataNoteAndStatusByBatchCode(batchCode,"0","");

        return "数据导入成功";
    }


    public List<Map<String,String>> checkDataAll(List<Map<String,String>> datas){

        int num = datas.size();
        int temp = num / 1000;
        List<Map<String,String>> result = new ArrayList<Map<String, String>>();
        for (int i = 0; i <= temp; i++) {
            int fromIndex = 1000*i;
            int toIndex = 1000*(i+1);
            if (toIndex>num)
                toIndex = num;
            List<Map<String,String>> list = datas.subList(fromIndex, toIndex);
            List<Map<String, String>> re = importDataWithProfitMapper.checkDataAll(list);

            result.addAll(re);
        }

        return result;
    }

    public Map<String,String> checkExcelData(Workbook data){
        Map<String,String> result = new HashMap<String,String>();

        if (data == null){
            result.put("resultCode","Error");
            result.put("errMsg","数据校验异常，数据读取失败，请检查上传文件是否正确。");
            return result;
        }

        StringBuffer errMsg = new StringBuffer();
        int sheetsNum = data.getNumberOfSheets();
        for (int i = 0; i < sheetsNum; i++) {
            Sheet sheet = data.getSheetAt(i);
            String sheetName = sheet.getSheetName();

            //1.判断表头
            Row title = sheet.getRow(0);//表头

            if (title == null){
                errMsg.append(sheetName+"页为空sheet页！\n");
                continue;
            }

            if (title.getLastCellNum()!=title.getPhysicalNumberOfCells()){  //表头有空单元格
                errMsg.append(sheetName+"页表头有空格！\n");
            }

            Cell cell01 = title.getCell(0);
            Cell cell02 = title.getCell(1);
            Cell cell03 = title.getCell(2);
            Cell cell04 = title.getCell(3);

            String cellValue01 = getCellValue(cell01);
            String cellValue02 = getCellValue(cell02);
            String cellValue03 = getCellValue(cell03);
            String cellValue04 = getCellValue(cell04);
            if (!"AG码".equals(cellValue01)||!"代理商名称".equals(cellValue02)||!"月份".equals(cellValue03)||!"品牌码".equals(cellValue04)){
                errMsg.append(sheetName+"页表头格式有误，请确认前四列以此为：AG码、代理商名称、月份、品牌码！\n");
            }

            //判断空行与前四列数据非空
            int rowNum = sheet.getLastRowNum();
            for (int j = 1; j < rowNum; j++) {

                if (errMsg.length()>1200){
                    break;
                }

                int tempNum = j+1;
                Row row = sheet.getRow(j);
                if (row==null){
                    errMsg.append(sheetName+"页第"+tempNum+"行为空行！\n");
                    continue;
                }

                Cell agentId = row.getCell(0);
                Cell agentName = row.getCell(1);
                Cell month = row.getCell(2);
                Cell busCode = row.getCell(3);

                String cellValueAgentId = getCellValue(agentId);
                String cellValueAgentName = getCellValue(agentName);
                String cellValueMonth = getCellValue(month);
                String cellValueBusCode = getCellValue(busCode);
                if (StringUtils.isBlank(cellValueAgentId))
                errMsg.append(sheetName+"页第"+tempNum+"行AG码为空！\n");
                if (StringUtils.isBlank(cellValueAgentName))
                errMsg.append(sheetName+"页第"+tempNum+"行代理商名称为空！\n");
                if (StringUtils.isBlank(cellValueMonth))
                errMsg.append(sheetName+"页第"+tempNum+"行月份为空！\n");
                if (StringUtils.isBlank(cellValueBusCode))
                errMsg.append(sheetName+"页第"+tempNum+"行品牌码为空！\n");

            }

            if (errMsg.length()>1200){
                errMsg.append("...等其他异常数据");
                break;
            }
        }
        if (errMsg.length()>0){
            result.put("resultCode","Error");
            result.put("errMsg",errMsg.toString());
        }else{
            result.put("resultCode","Success");
        }
        return result;
    }

    //校验代理商信息
    public Map<String,String> checkAgentData(Workbook data,String logMonth){
        Map<String,String> result = new HashMap<String,String>();

        if (data == null){
            result.put("resultCode","Error");
            result.put("errMsg","数据校验异常，数据读取失败，请检查上传文件是否正确。");
            return result;
        }

        StringBuffer errMsg = new StringBuffer();

        int sheetsNum = data.getNumberOfSheets();
        List<Map<String, String>> errorDatas = new ArrayList<Map<String, String>>(); //问题数据
        boolean isTooLong = false;
        for (int i = 0; i < sheetsNum; i++) {
            if (isTooLong)
                break;
            Sheet sheet = data.getSheetAt(i);
            String sheetName = sheet.getSheetName();
            List<Map<String,String>> mapList= new ArrayList<Map<String, String>>();//单sheet页数据

            int rowsNum = sheet.getPhysicalNumberOfRows();//获取该sheet页中数据的实际行数
            List<Map<String, String>> errorSheetData;

            for (int rowNum = 1; rowNum < rowsNum; rowNum++) {
                if (isTooLong)
                    break;
                /*Row firstRow = sheet.getRow(0);
                int cellsNum = firstRow.getPhysicalNumberOfCells();//列数*/
                Row row = sheet.getRow(rowNum);
                Cell agentIdCell = row.getCell(0);//代理商Id
                String agentId = getCellValue(agentIdCell);
                Cell brandCell = row.getCell(3);//品牌码
                String busCode = getCellValue(brandCell);
                Cell monthCell = row.getCell(2);//月份
                String month = getCellValue(monthCell);
                if (!month.equals(logMonth)){
                    errMsg.append(sheetName+"页,第"+(rowNum+1)+"行的月份与上传月份不匹配。\n");
                }

                Map<String,String> map= new HashMap<String,String>();

                map.put("agentId",agentId);
                map.put("busCode",busCode);
                map.put("rowNum",(rowNum+1)+"");
                map.put("sheetName",sheetName);

                mapList.add(map);

                if (errMsg.length()>1200)
                    isTooLong = true;

            }

            errorSheetData = checkDataAll(mapList);
            errorDatas.addAll(errorSheetData);
        }

        if (errorDatas.size()>0){

            for (int i = 0; i < errorDatas.size(); i++) {
                if (isTooLong)
                    break;
                Map<String, String> errData = errorDatas.get(i);
                String agentId = errData.get("AGENT_ID");
                String busPlatform = errData.get("BUS_PLATFORM");
                String rowNum = errData.get("ROW_NUM");
                String sheetName = errData.get("SHEET_NAME");

                errMsg.append(sheetName);
                errMsg.append("页中第");
                errMsg.append(rowNum);
                errMsg.append("行数据，代理商ID");
                errMsg.append(agentId);
                errMsg.append("与品牌码");
                errMsg.append(busPlatform);
                errMsg.append("不匹配");

                if (errMsg.length()>1200){
                    errMsg.append("...等其他异常数据");
                    break;
                }else {
                    errMsg.append("\n");
                }
            }

            result.put("resultCode","Error");
            result.put("errMsg",errMsg.toString());
        }else{
            result.put("resultCode","Success");
        }
        return result;

    }

    /**
     * 自Cell 中取值
     * @param cell
     * @return
     *
     * 本方法为陈良所写，此处借用
     */
    public static String getCellValue(Cell cell) {
        String cellValue = "";
        if (null != cell) {
            // 以下是判断数据的类型
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_NUMERIC: // 数字
                    if (0 == cell.getCellType()) {// 判断单元格的类型是否则NUMERIC类型
                        if (HSSFDateUtil.isCellDateFormatted(cell)) {// 判断是否为日期类型
                            Date date = cell.getDateCellValue();
                            DateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
                            cellValue = formater.format(date);
                        } else {
                            // 有些数字过大，直接输出使用的是科学计数法： 2.67458622E8 要进行处理
                            DecimalFormat df = new DecimalFormat("####.########");
                            cellValue = df.format(cell.getNumericCellValue());
                        }
                    }
                    break;
                case HSSFCell.CELL_TYPE_STRING: // 字符串
                    cellValue = cell.getStringCellValue();
                    break;
                case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
                    cellValue = cell.getBooleanCellValue() + "";
                    break;
                case HSSFCell.CELL_TYPE_FORMULA: // 公式
                    try {
                        // 如果公式结果为字符串
                        cellValue = String.valueOf(cell.getStringCellValue());
                    } catch (IllegalStateException e) {
                        if (HSSFDateUtil.isCellDateFormatted(cell)) {// 判断是否为日期类型
                            Date date = cell.getDateCellValue();
                            DateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
                            cellValue = formater.format(date);
                        } else {
                            FormulaEvaluator evaluator = cell.getSheet().getWorkbook().getCreationHelper()
                                    .createFormulaEvaluator();
                            evaluator.evaluateFormulaCell(cell);
                            // 有些数字过大，直接输出使用的是科学计数法： 2.67458622E8 要进行处理
                            DecimalFormat df = new DecimalFormat("####.########");
                            cellValue = df.format(cell.getNumericCellValue());
                        }
                    }
                    break;
                case HSSFCell.CELL_TYPE_BLANK: // 空值
                    cellValue = "";
                    break;
                case HSSFCell.CELL_TYPE_ERROR: // 故障
                    cellValue = "非法字符";
                    break;
                default:
                    cellValue = "未知类型";
                    break;
            }
        }
        return cellValue.trim();
    }

    class SheetThead  implements Callable<Map<String,String>>{
        private PmsProfitLog log;
        private Sheet sheet;
        private int sheetOrder;

        SheetThead(PmsProfitLog log, Sheet sheet, int sheetOrder){
            this.log=log;
            this.sheet=sheet;
            this.sheetOrder=sheetOrder;
        }

        @Override
        public Map<String, String> call() throws Exception {

            String sheetName = this.sheet.getSheetName();
            logger.info("========数据导入开始sheet："+sheetName);
            Map<String, String> result = doInsertData(log, sheet, sheetName, sheetOrder);
            return result;
        }
    }

    private Map<String,String> doInsertData(PmsProfitLog log, Sheet sheet, String sheetName, int sheetOrder) {
        Map<String,String> result = new HashMap<String,String>();
        int numberOfRows = sheet.getPhysicalNumberOfRows();//行数
        if (numberOfRows<=0){
            result.put("resultCode","Success");
            return result;
        }
        int numberOfCells = sheet.getRow(0).getPhysicalNumberOfCells();//列数

        List<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();

        for (int rowNum = 0; rowNum < numberOfRows; rowNum++) {
            Row row = sheet.getRow(rowNum);
            Map<String,Object> data = new HashMap<String,Object>();
            String id = getUUIDForId();
            data.put("id",id);//id 主键
            data.put("importType",log.getImportType());//导入类型
            data.put("sheetName",sheet.getSheetName());//sheet名
            data.put("sheetColumn",numberOfCells+"");//列数
            data.put("uploadUser",log.getUploadUser());//上传人
            data.put("uploadTime",log.getUploadTime());//上传时间
            data.put("sheetOrder",sheetOrder+"");//sheet页号
            data.put("rowOrder",(rowNum+1)+"");//行号

            for (int colNum = 0; colNum < numberOfCells; colNum++) {

                Cell cell = row.getCell(colNum);
                String value = getCellValue(cell);
                switch (colNum){
                    case 0:
                        data.put("agentId",value);
                        break;
                    case 1:
                        data.put("agentName",value);
                        break;
                    case 2:
                        data.put("month",log.getMonth());
                        break;
                    case 3:
                        data.put("brandCode",value);
                        break;
                    default:
                        data.put("f"+(colNum+3),value);
                        break;
                }
            }
            datas.add(data);
        }
        try {
            insertProfitData(datas);
        }catch (Exception e){
           e.printStackTrace();
           result.put("resultCode","Error");
           result.put("errMsg",sheetName+"页数据插入失败:"+e.getMessage()+"\n");
           return result;
        }


        result.put("resultCode","Success");
        return result;
    }

    private String getUUIDForId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
