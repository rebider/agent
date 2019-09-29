package com.ryx.credit.profit.service.impl;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.dao.PmsProfitLogMapper;
import com.ryx.credit.profit.dao.PmsProfitMapper;
import com.ryx.credit.profit.dao.PmsProfitTempMapper;
import com.ryx.credit.profit.pojo.*;
import com.ryx.credit.profit.service.IPmsProfitLogService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("pmsProfitLogService")
public class PmsProfitLogServiceImpl implements IPmsProfitLogService {
    private final Logger logger = Logger.getLogger(PmsProfitLogServiceImpl.class);
    @Autowired
    PmsProfitLogMapper pmsProfitLogMapper;
    @Autowired
    PmsProfitTempMapper pmsProfitTempMapper;
    @Autowired
    PmsProfitMapper pmsProfitMapper;


    @Override
    public long countByExample(PmsProfitLogExample example) {
        return pmsProfitLogMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(PmsProfitLogExample example) {
        return pmsProfitLogMapper.deleteByExample(example);
    }

    @Override
    public int deletePmsProfitTemp(String month,String user){
        PmsProfitTempExample pmsProfitTempExample = new PmsProfitTempExample();
        PmsProfitTempExample.Criteria pmsProfitTempExampleCriteria = pmsProfitTempExample.createCriteria();
        pmsProfitTempExampleCriteria.andMonthEqualTo(month);
        pmsProfitTempExampleCriteria.andImportPersonEqualTo(user);
        return pmsProfitTempMapper.deleteByExample(pmsProfitTempExample);
    }

    @Override
    public int deletePmsProfit(String month,String user) {
        PmsProfitExample pmsProfitExample = new PmsProfitExample();
        PmsProfitExample.Criteria pmsProfitExampleCriteria = pmsProfitExample.createCriteria();
        pmsProfitExampleCriteria.andMonthEqualTo(month);
        pmsProfitExampleCriteria.andImportPersonEqualTo(user);
        return pmsProfitMapper.deleteByExample(pmsProfitExample);
    }

    @Override
    public PmsProfitLog selectByPrimaryKey(String batchNo) {
        return pmsProfitLogMapper.selectByPrimaryKey(batchNo);
    }

    @Override
    public int insertSelective(PmsProfitLog record) {
        return pmsProfitLogMapper.insertSelective(record);
    }
    @Override
    public int insertSelective(PmsProfitTempWithBLOBs record) {
        return pmsProfitTempMapper.insertSelective(record);
    }
    @Override
    public int insertSelective(PmsProfit record) {
        return pmsProfitMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(PmsProfitLog record) {
        return pmsProfitLogMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(PmsProfitTempWithBLOBs record) {
        return pmsProfitTempMapper.updateByPrimaryKeySelective(record);
    }
    @Override
    public int updateByPrimaryKeySelective(PmsProfit record) {
        return pmsProfitMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public Map<String, Object> checkoutData(String agentId, String busCode) {
        return pmsProfitLogMapper.checkoutData(agentId,busCode);
    }


    @Override
    public int insert(PmsProfitLog record) {
        return pmsProfitLogMapper.insert(record);
    }



    /**
     * chenliang
     * 20190926
     *
     * @param example
     * @param page
     * @return
     */
    @Override
    public PageInfo selectByExample(PmsProfitLog example, Page page) {
        PmsProfitLogExample pmsProfitLogExample = new PmsProfitLogExample();
        PmsProfitLogExample.Criteria criteria = pmsProfitLogExample.createCriteria();
        if (StringUtils.isNotBlank(example.getBatchNo())) {
            criteria.andBatchNoEqualTo(example.getBatchNo());
        }
        if (StringUtils.isNotBlank(example.getUploadTime())) {
            criteria.andUploadTimeEqualTo(example.getUploadTime());
        }
        if (StringUtils.isNotBlank(example.getMonth())) {
            criteria.andMonthEqualTo(example.getMonth());
        }
        if (StringUtils.isNotBlank(example.getStatus())) {
            criteria.andStatusEqualTo(example.getStatus());
        }
        pmsProfitLogExample.setOrderByClause("UPLOAD_TIME DESC");
        pmsProfitLogExample.setPage(page);

        List<PmsProfitLog> pmsProfitLogs = pmsProfitLogMapper.selectByExample(pmsProfitLogExample);
        for (PmsProfitLog pmsProfitLog:pmsProfitLogs ) {
            Map<String,Object> map = pmsProfitLogMapper.getLoginName(pmsProfitLog.getUploadUser());
            pmsProfitLog.setUploadUser(map.get("NAME").toString());
        }

        Long count = pmsProfitLogMapper.countByExample(pmsProfitLogExample);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(pmsProfitLogs);
        pageInfo.setTotal(count.intValue());
        return pageInfo;
    }



    @Override
    public int updateByPrimaryKey(PmsProfitLog record) {
        return pmsProfitLogMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * chenliang
     * 20190927
     *
     * @param file
     * @param month
     * @param user
     *//*
    @Override
    public void profitImportImportFile(MultipartFile file, String month, String user) {

        String path = getUploadRealPath();
        String names = String.valueOf(System.currentTimeMillis());
        Map<String, String> result = null;
        try {
            result = upload(file, path, File.separator + names + File.separator, user);
        } catch (Exception e) {
            new RuntimeException(e);
            e.printStackTrace();
        }

        PmsProfitLog pmsProfitLog = new PmsProfitLog();

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        pmsProfitLog.setBatchNo(uuid);
        pmsProfitLog.setUploadPath(result.get("filePath"));
        pmsProfitLog.setUploadName(result.get("fileName"));
        // pl.setResultPath(File.separator+names+File.separator+pl.getBatchNo()+"_result"+".xls");
        pmsProfitLog.setResultName(pmsProfitLog.getBatchNo());
        pmsProfitLog.setUploadTime(DateUtil.getDateToString(new Date()));
        pmsProfitLog.setUploadUser(user);
        pmsProfitLog.setStatus("2");
        //月份
        pmsProfitLog.setMonth(month);
        pmsProfitLogMapper.insertSelective(pmsProfitLog);
        try {
            // String fileName=file.getOriginalFilename();
            // String suffix="";
            // if(fileName.split("\\.").length>1){
            // suffix = fileName.split("\\.")[0];
            // }else{
            // throw new ServiceException("文件命名格式错误 ");
            // }
            // if(suffix.split("_").length>1){
            // String fileNames[] =suffix.split("_");
            // if(!fileNames[0].equals("分润数据")||!fileNames[1].equals(month)){
            // throw new ServiceException("文件命名格式或选取月份错误 ");
            // }
            // }else{
            // throw new ServiceException("文件命名格式错误 ");
            // }
            // 按照月份删除
			*//*drofitTempService.deleteByMonth(month);
			profitManageMapper.deleteByMonth(month);*//*
            //根据 月份、导入人删除
            PmsProfitTempExample pmsProfitTempExample = new PmsProfitTempExample();
            PmsProfitTempExample.Criteria pmsProfitTempExampleCriteria = pmsProfitTempExample.createCriteria();
            pmsProfitTempExampleCriteria.andMonthEqualTo(month);
            pmsProfitTempExampleCriteria.andImportPersonEqualTo(user);
            pmsProfitTempMapper.deleteByExample(pmsProfitTempExample);


            PmsProfitExample pmsProfitExample = new PmsProfitExample();
            PmsProfitExample.Criteria pmsProfitExampleCriteria = pmsProfitExample.createCriteria();
            pmsProfitExampleCriteria.andMonthEqualTo(month);
            pmsProfitExampleCriteria.andImportPersonEqualTo(user);
            pmsProfitMapper.deleteByExample(pmsProfitExample);
            Workbook wookbook = null;
            try {
                // 创建对Excel工作簿文件的引用
                String name = file.getOriginalFilename();
                String fileType = name.substring(name.lastIndexOf(".") + 1,
                        name.length());
                if (fileType.equals("xlsx")) {
                    try {
                        wookbook = new XSSFWorkbook(file.getInputStream());
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else if (fileType.equals("xls")) {
                    wookbook = new HSSFWorkbook(file.getInputStream());
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("读取excel文件失败! ");
            }
            // sheetshu
            int sheetCount = 0;
            sheetCount = wookbook.getNumberOfSheets();

            for (int i = 0; i < sheetCount; i++) {
                int rowNum = 0;
                Sheet sheet = wookbook.getSheetAt(i);
                String sheetName = sheet.getSheetName();
                int columnNum = 0;
                List<Map<String, String>> rsList = new ArrayList<>();
                // 获取到Excel文件中的所有行数
                int rows = sheet.getPhysicalNumberOfRows();
                if (rows > 0) {
                    Row hssfRow = sheet.getRow(0);
                    columnNum = hssfRow.getPhysicalNumberOfCells();
                }
                if (columnNum <= 5) {
                    throw new RuntimeException("Sheet[" + i + "][" + sheetName + "]页列数不足! ");
//					return;
                }
                // 遍历行 从第1行开始遍历
                for (int j = 0; j < rows; j++) {
                    Row row = sheet.getRow(j);
                    rowNum = j;
                    // 行不能为空
                    if (row == null) {
                        continue;
                    }

                    Map<String, String> map = new HashMap<>();
                    // 用于判断是否此行所有字段都为空
                    boolean rownull = true;

                    // 遍历每列
                    for (int k = 0; k < columnNum; k++) {
                        map.put("Cell" + k, "");

                        Cell cell = row.getCell(k);
                        if (cell == null) {
                            continue;
                        }
                        String cellValue = "";
                        try {
                            cellValue = getCellValue(cell).trim();
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new RuntimeException("数据读取有误，" + sheetName + "页" + ",第" + j + "行，第" + k + "列数据异常");
                        }
                        // 如果字段值不为空，设置行空为否
                        if (rownull && org.apache.commons.lang.StringUtils.isNotEmpty(cellValue)) {
                            rownull = false;
                        }
                        if (j == 0) {
                            if (cellValue == null || cellValue.equals("")) {
                                columnNum = k;
                                break;
                            }
                        }
                        map.put("Cell" + k, cellValue);
                        map.put("rowNum", String.valueOf(rowNum));
                    }

                    // 如果此行所有字段都不为空值加入结果集合
                    if (!rownull) {
                        rsList.add(map);
                    }
                }
                try {
                    saveSheet(rsList, sheetName, columnNum, month, user, i);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("数据有误，保存失败，可能存在重复数据或数据过长，sheet:" + sheetName);
                }
            }
        } catch (RuntimeException e) {
            pmsProfitLog.setStatus("1");
            pmsProfitLog.setNote(e.getMessage());
            pmsProfitLogMapper.updateByPrimaryKeySelective(pmsProfitLog);
            throw e;
        } catch (Exception e) {
            pmsProfitLog.setStatus("1");
            pmsProfitLog.setNote("上传失败");
            pmsProfitLogMapper.updateByPrimaryKeySelective(pmsProfitLog);
            throw e;
        }
        pmsProfitLog.setStatus("0");
        pmsProfitLogMapper.updateByPrimaryKeySelective(pmsProfitLog);
    }


    *//**
     * @author chenliang
     *//*

    public String getUploadRealPath() {

        String path = readUploadRealPath("importConfig.properties", "l.profit.upload");
        Properties p = System.getProperties();
        String s = p.getProperty("os.name");
        if (s.startsWith("Win")) {
            path = readUploadRealPath("importConfig.properties", "w.profit.upload");
        }
        return path;
    }

    *//**
     * @author chenliang
     *//*
    public String readUploadRealPath(String config, String key) {

        Properties props = new Properties();
        try {
            props.load(Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream(config));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("找不到配置" + config + "文件", e);
        } catch (IOException e) {
            throw new RuntimeException("读取配置" + config + "文件出错", e);
        }
        String path = props.getProperty(key);
        return path;
    }


    *//**
     * 上传文件.
     *
     * @param file     上传的文件
     * @param realPath 文件上传的真实路径
     * @param path     文件相对路径
     * @return map{fileName:文件名称，filePath:文件路径}
     * @throws Exception
     * @author chenliang
     *//*
    public static Map<String, String> upload(MultipartFile file, String realPath, String path, String prefix) throws Exception {

        try {
            // 验证文件是不是空
            if (file == null) {
                throw new RuntimeException("上传文件为空！");
            }

            String filepath = realPath + path;


            // 创建保存文件的目录
            File filedirectory = new File(filepath);
            if (!filedirectory.exists() && !filedirectory.isDirectory()) {
                filedirectory.mkdirs();
            }
            // 生成文件名，年月日+4位流水号
            String fileName = file.getOriginalFilename();
            String suffix = "";
            if (fileName.split("\\.").length > 1) {
                suffix = fileName.substring(fileName.lastIndexOf("."));
            }
            String name = "";
            String dateString = System.currentTimeMillis() + "";
            int i = 0;

            while (true) {
                name = prefix + dateString + suffix;
                if (new File(filepath + name).exists()) {
                    i++;
                } else {
                    break;
                }
            }
            // 生成本地文件
            File localFile = new File(filepath + name);
            if (localFile.exists()) {
                while (true) {
                    name = prefix + dateString + suffix;
                    if (new File(filepath + name).exists()) {
                        i++;
                    } else {
                        break;
                    }
                }
                localFile = new File(filepath + name);
            }
            file.transferTo(localFile);

            Map<String, String> fileData = new HashMap<>();
            fileData.put("fileName", file.getOriginalFilename());
            fileData.put("filePath", path + name);
            return fileData;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("上传文件失败！");
        }
    }


    *//**
     * 取得单元格内容.
     *
     * @param cell
     * @return
     * @throws Exception
     * @author chenliang
     *//*
    private static String getCellValue(Cell cell) throws Exception {
        // 日期或数字
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                return DateUtils.dateToStrings(cell.getDateCellValue());
            }
            //数字
            DecimalFormat df = new DecimalFormat();
            df.setGroupingUsed(false);
            return df.format(cell.getNumericCellValue());
        }
        // 空
        if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
            return cell.getRichStringCellValue().getString();
        }
        // 字符
        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            return cell.getRichStringCellValue().getString();
        }
        // 格式化
        if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
            return String.valueOf(cell.getNumericCellValue());
        }

        return "";
    }


    public void saveSheet(List<Map<String, String>> list, String sheetName,
                          int columnNum, String month, String userId, int sheetOrder) {
        if (list.size() <= 1)
            return;
        PmsProfit pf = new PmsProfit();
        pf.setMonth(month);
        pf.setSheetOrder(new BigDecimal(sheetOrder));
        pf.setSheetHead(this.callMapToXML(list.get(0)));
        pf.setPayCondition("1");// 未打款
        pf.setStatus("0");// 解冻
        pf.setPayTranMoney(new BigDecimal("0"));
        pf.setProfitHz("");
        pf.setProfitType("");
        pf.setImportPerson(userId);
        pf.setImportTime(DateUtils.dateToStringss(new Date()));
        pf.setSheetName(sheetName);
        pf.setSheetColumn(new BigDecimal(columnNum));
        pf.setUpdatePerson(userId);
        pf.setUpdateTime(pf.getImportTime());
        //pf.setOrderNumber(rowNum);
        for (int i = 1; i < list.size(); i++) {
            pf.setOrderNumber(new BigDecimal((list.get(i).get("rowNum"))));
            list.get(i).remove("rowNum");
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            pf.setId(uuid);
            pf.setSheetData(this.callMapToXML(list.get(i)));
            if (logger.isDebugEnabled()) {
                logger.debug(pf.getSheetData());
            }
            pf.setUniqueFlag((list.get(i).get("Cell0")));
            Map<String, Object> mapData = pmsProfitLogMapper.checkoutData(list.get(i).get("Cell0").trim(), list.get(i).get("Cell3").trim());
            if (mapData == null) {
                throw new RuntimeException(sheetName + "sheet页第" + i + "行的平台下" + list.get(i).get("Cell3") + "不存在此行唯一码" + list.get(i).get("Cell0"));
            }
            if (!list.get(i).get("Cell1").trim().equals(mapData.get("AG_NAME").toString().trim())) {
                throw new RuntimeException(sheetName + "sheet页第" + i + "行唯一标识与代理商名称不匹配");
            }
            if (!month.equals((list.get(i).get("Cell2")))) {
                throw new RuntimeException(sheetName + "sheet页第" + i + "月份与选择不匹配");
            }

            if (!list.get(i).get("Cell4").trim().equals(mapData.get("AG_NAME").toString().trim())) {
                throw new RuntimeException(sheetName + "sheet页第" + i + "行平台码与平台名称不匹配");
            }
            pf.setAgentId((list.get(i).get("Cell1")));
            pf.setBusCode(list.get(i).get("Cell3").trim());
            pf.setBusCode(list.get(i).get("Cell4").trim());


            PmsProfitTempWithBLOBs pmsProfitTempWithBLOBs = new PmsProfitTempWithBLOBs();
            pmsProfitTempWithBLOBs.setMonth(month);
            pmsProfitTempWithBLOBs.setUniqueFlag((list.get(i).get("Cell0")));
            pmsProfitTempWithBLOBs.setAgentId((list.get(i).get("Cell1")));
            pmsProfitTempWithBLOBs.setBusCode(list.get(i).get("Cell3").trim());
            pmsProfitTempWithBLOBs.setBusCode(list.get(i).get("Cell4").trim());
            pmsProfitTempWithBLOBs.setSheetHead(this.callMapToXML(list.get(0)));
            pmsProfitTempWithBLOBs.setSheetData(this.callMapToXML(list.get(i)));
            pmsProfitTempWithBLOBs.setSheetName(sheetName);
            pmsProfitTempWithBLOBs.setImportPerson(userId);
            pmsProfitTempWithBLOBs.setOrderNumber(new BigDecimal((list.get(i).get("rowNum"))));
            try {
                pmsProfitMapper.insertSelective(pf);
                pmsProfitTempMapper.insertSelective(pmsProfitTempWithBLOBs);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("保存数据失败，sheet:" + pf.getSheetName() + "第" + (pf.getOrderNumber().add(new BigDecimal(1))) + "行:" + JSONObject.toJSONString(pf));
                throw new RuntimeException("保存数据失败，sheet:" + pf.getSheetName() + "第" + pf.getOrderNumber() + "行");
            }

        }
    }

    public String callMapToXML(Map map) {
        StringBuffer sb = new StringBuffer();
        sb.append("<data>");
        // sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><data>");
        mapToXML(map, sb);
        sb.append("</data>");
        try {
            return sb.toString();
        } catch (Exception e) {
        }
        return null;
    }

    private void mapToXML(Map<String, String> map, StringBuffer sb) {
        int sum = 0;
        for (String key : map.keySet()) {
            sum++;
            if (logger.isDebugEnabled()) {
                logger.debug(key + ":\t" + sum);
            }
        }

        for (int i = 0; i < sum; i++) {
            String key = "Cell" + i;
            String value = map.get(key);
            sb.append("<" + key + ">" + value + "</" + key + ">");
        }

    }*/

}
