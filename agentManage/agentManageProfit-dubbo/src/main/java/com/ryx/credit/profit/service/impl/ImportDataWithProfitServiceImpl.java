package com.ryx.credit.profit.service.impl;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.dao.ImportDataWithProfitMapper;
import com.ryx.credit.profit.dao.PmsProfitLogMapper;
import com.ryx.credit.profit.pojo.ImportDataWithProfitExample;
import com.ryx.credit.profit.pojo.PmsProfitLog;
import com.ryx.credit.profit.service.IImportDataWithProfitService;
import com.ryx.credit.profit.unitmain.BigDataExcelToMapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;

@Service("importDataWithProfitService")
public class ImportDataWithProfitServiceImpl implements IImportDataWithProfitService {

    Logger logger = LoggerFactory.getLogger(ImportDataWithProfitServiceImpl.class);

    private static final int importNum = 800;

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
    public int insertProfitData(List<Map<String, String>> profitDatas) {    //批量插入，每次插入800条数据

        int num = profitDatas.size();
        int resNum = 0;
        int temp = num / importNum;
        for (int i = 0; i <= temp; i++) {
            int fromIndex = importNum*i;
            int toIndex = importNum*(i+1);
            if (toIndex>num)
                toIndex = num;
            List<Map<String, String>> list = profitDatas.subList(fromIndex, toIndex);
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

        //1. 根据batchCode找到导入记录
        PmsProfitLog log =  getImportLogByBatchCode(batchCode);
        if (log==null){
            throw new MessageException("未找到该记录");
        }

        //2.解析Excel文件 封装数据
        List<List<Map<String,String>>> lists;
        try {
            lists = BigDataExcelToMapUtils.bigDataGetExcel(log.getUploadPath(),log.getImportType(),log.getUploadTime(),log.getUploadUser());
        }catch (Exception e){
            pmsProfitLogMapper.updataNoteAndStatusByBatchCode(batchCode,"1","数据导入失败，文件类型异常！");
            return "文件解析失败。";
        }
        Map<String, String> resultMap;
        //3.校验数据完整性，固定列数据
        resultMap = checkExcelData(lists);
        logger.info("====================checkExcelData()Excel结构检查完成。");
        if ("Error".equals(resultMap.get("resultCode"))){    //存在异常数据，生成备注更新导入结果
            String errMsg = resultMap.get("errMsg");
            pmsProfitLogMapper.updataNoteAndStatusByBatchCode(batchCode,"1",errMsg);
            return "数据导入失败,请检查导入文件";
        }

        //2.校验代理商信息
        resultMap = checkAgentData(lists,log.getMonth());
        logger.info("====================checkAgentData()数据完整性检查完成。");
        if ("Error".equals(resultMap.get("resultCode"))){    //存在异常数据，生成备注更新导入结果
            String errMsg = resultMap.get("errMsg");
            pmsProfitLogMapper.updataNoteAndStatusByBatchCode(batchCode,"1",errMsg);
            return "数据导入失败,请确认导入数据的完整性";
        }



        /*

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


        logger.info("====================读取文件成功。");

        //校验数据

        Map<String, String> resultMap;
        //1.校验数据完整性，固定列数据
        resultMap = checkExcelData(data);
        logger.info("====================checkExcelData()Excel结构检查完成。");
        if ("Error".equals(resultMap.get("resultCode"))){    //存在异常数据，生成备注更新导入结果
            String errMsg = resultMap.get("errMsg");
            pmsProfitLogMapper.updataNoteAndStatusByBatchCode(batchCode,"1",errMsg);
            return "数据导入失败,请检查导入文件";
        }
        //2.校验代理商信息
        resultMap = checkAgentData(data,log.getMonth());
        logger.info("====================checkAgentData()数据完整性检查完成。");
        if ("Error".equals(resultMap.get("resultCode"))){    //存在异常数据，生成备注更新导入结果
            String errMsg = resultMap.get("errMsg");
            pmsProfitLogMapper.updataNoteAndStatusByBatchCode(batchCode,"1",errMsg);
            return "数据导入失败,请确认导入数据的完整性";
        }

        */

        importDataWithProfitMapper.deleteProfitDataByBatchCode(batchCode);//插入数据之前先删除已存在的数据

        //插入数据
        int numOfSheets = lists.size();
        Executor executor = new ThreadPoolExecutor(numOfSheets, numOfSheets,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        List<FutureTask<Map<String,String>>> taskList = new ArrayList();

        for (int i = 0; i < numOfSheets; i++) {
            List<Map<String,String>> sheet = lists.get(i);
            String sheetName = sheet.get(0).get("sheetName");
            SheetThead sheetThead = new SheetThead(sheet,sheetName);
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
        int temp = num / importNum;
        List<Map<String,String>> result = new ArrayList<Map<String, String>>();
        for (int i = 0; i <= temp; i++) {
            int fromIndex = importNum*i;
            int toIndex = importNum*(i+1);
            if (toIndex>num)
                toIndex = num;
            List<Map<String,String>> list = datas.subList(fromIndex, toIndex);
            List<Map<String, String>> re = importDataWithProfitMapper.checkDataAll(list);

            result.addAll(re);
        }

        return result;
    }

    /*public Map<String,String> checkExcelData(Workbook data){
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
            for (int j = 1; j <= rowNum; j++) {

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
    }*/

    public Map<String,String> checkExcelData(List<List<Map<String,String>>> list){
        Map<String,String> result = new HashMap<String,String>();

        if (list == null || list.size()<1){
            result.put("resultCode","Error");
            result.put("errMsg","数据校验异常，数据读取失败，请检查上传文件是否正确。");
            return result;
        }

        StringBuffer errMsg = new StringBuffer();
        int sheetsNum = list.size();
        for (int i = 0; i < sheetsNum; i++) {

            List<Map<String, String>> sheetList = list.get(i);

            //1.判断表头
            if (sheetList == null || sheetList.size()<1){
                errMsg.append("文件中第"+(i+1)+"页为空sheet页！\n");
                continue;
            }
            Map<String,String> title = sheetList.get(0);//表头
            if (title == null || title.size()<1){
                errMsg.append("文件中第"+(i+1)+"页为空sheet页！\n");
                continue;
            }
            String sheetName = title.get("sheetName");

            String cellValue01 = title.get("agentId");
            String cellValue02 = title.get("agentName");
            String cellValue03= title.get("month");
            String cellValue04 = title.get("brandCode");

            if (!"AG码".equals(cellValue01)||!"代理商名称".equals(cellValue02)||!"月份".equals(cellValue03)||!"品牌码".equals(cellValue04)){
                errMsg.append(sheetName+"页表头格式有误，请确认前四列以此为：AG码、代理商名称、月份、品牌码！\n");
            }
            int colNum = Integer.valueOf(title.get("sheetColumn")) + 2; //除去前四个固定列以及其他附属字段之外的列数
            for (int num = 7; num <= colNum; num++){
                String f = title.get("f" + (num));
                if (StringUtils.isBlank(f)){
                    errMsg.append(sheetName+"页表头有空格！\n");
                    break;
                }
            }


            //前四列数据非空
            int rowNum = sheetList.size();
            for (int j = 1; j < rowNum; j++) {

                if (errMsg.length()>1200){
                    break;
                }

                int tempNum = j+1;  //行号

                Map<String, String> row = sheetList.get(j);
                if (row==null){
                    errMsg.append(sheetName+"页第"+tempNum+"行为空行！\n");
                    continue;
                }

                String agentId = row.get("agentId");
                String agentName = row.get("agentName");
                String month = row.get("month");
                String brandCode = row.get("brandCode");

                if (StringUtils.isBlank(agentId))
                    errMsg.append(sheetName+"页第"+tempNum+"行AG码为空！\n");
                if (StringUtils.isBlank(agentName))
                    errMsg.append(sheetName+"页第"+tempNum+"行代理商名称为空！\n");
                if (StringUtils.isBlank(month))
                    errMsg.append(sheetName+"页第"+tempNum+"行月份为空！\n");
                if (StringUtils.isBlank(brandCode))
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
    /*public Map<String,String> checkAgentData(Workbook data,String logMonth){
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

        }

        if (errMsg.length()==0){
            result.put("resultCode","Success");
        }else{
            result.put("resultCode","Error");
            result.put("errMsg",errMsg.toString());
        }
        return result;
    }*/

    public Map<String,String> checkAgentData(List<List<Map<String,String>>> list,String logMonth){
        Map<String,String> result = new HashMap<String,String>();

        if (list == null){
            result.put("resultCode","Error");
            result.put("errMsg","数据校验异常，数据读取失败，请检查上传文件是否正确。");
            return result;
        }

        StringBuffer errMsg = new StringBuffer();

        int sheetsNum = list.size();
        List<Map<String, String>> errorDatas = new ArrayList<Map<String, String>>(); //问题数据
        boolean isTooLong = false;
        for (int i = 0; i < sheetsNum; i++) {
            if (isTooLong)
                break;
            List<Map<String, String>> sheet = list.get(i);
            Map<String, String> title = sheet.get(0);
            String sheetName = title.get("sheetName");
            title.put("month",logMonth);
            int rowsNum = sheet.size();//获取该sheet页中数据的实际行数
            List<Map<String, String>> errorSheetData;

            for (int rowNum = 1; rowNum < rowsNum; rowNum++) {
                if (isTooLong)
                    break;
                Map<String,String> row = sheet.get(rowNum);
                String month = row.get("month");
                if (!month.equals(logMonth)){
                    errMsg.append(sheetName+"页,第"+(rowNum+1)+"行的月份与上传月份不匹配。\n");
                }

                if (errMsg.length()>1200)
                    isTooLong = true;

            }

            errorSheetData = checkDataAll(sheet.subList(1,rowsNum));
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

        }

        if (errMsg.length()==0){
            result.put("resultCode","Success");
        }else{
            result.put("resultCode","Error");
            result.put("errMsg",errMsg.toString());
        }
        return result;
    }


    /*public static String getCellValue(Cell cell) {
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
                        //cellValue = String.valueOf(cell.getStringCellValue());
                        cellValue = String.valueOf(((XSSFCell)cell).getRawValue ());
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
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
    }*/

    class SheetThead  implements Callable<Map<String,String>>{
        private List<Map<String,String>> sheet;
        private String sheetName;

        SheetThead( List<Map<String,String>> sheet, String sheetName){
            this.sheet=sheet;
            this.sheetName=sheetName;
        }

        @Override
        public Map<String, String> call() {
            logger.info("========数据导入开始sheet："+sheetName);
            Map<String, String> result = doInsertData(sheet, sheetName);
            return result;
        }
    }

    private Map<String,String> doInsertData(List<Map<String,String>> sheet, String sheetName) {
        Map<String,String> result = new HashMap<String,String>();
        int numberOfRows = sheet.size();//行数
        if (numberOfRows<=0){
            result.put("resultCode","Success");
            return result;
        }
        try {
            insertProfitData(sheet);
        }catch (Exception e){
            e.printStackTrace();
            result.put("resultCode","Error");
            result.put("errMsg",sheetName+"页数据插入失败:"+e.getMessage()+"\n");
            return result;
        }

        result.put("resultCode","Success");
        return result;
    }

    /*private String getUUIDForId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }*/

}
