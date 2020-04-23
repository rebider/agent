package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.ProfitDataImportType;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.dao.PmsProfitLogMapper;
import com.ryx.credit.profit.dao.PmsProfitMapper;
import com.ryx.credit.profit.dao.PmsProfitTempMapper;
import com.ryx.credit.profit.pojo.*;
import com.ryx.credit.profit.service.IPmsProfitLogService;
import com.ryx.credit.service.dict.IdService;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;


@Service("pmsProfitLogService")
public class PmsProfitLogServiceImpl implements IPmsProfitLogService {
    private final Logger logger = Logger.getLogger(PmsProfitLogServiceImpl.class);
    @Autowired
    PmsProfitLogMapper pmsProfitLogMapper;
    @Autowired
    PmsProfitTempMapper pmsProfitTempMapper;
    @Autowired
    PmsProfitMapper pmsProfitMapper;

    @Autowired
    IdService idService;


    @Override
    public long countByExample(PmsProfitLogExample example) {
        return pmsProfitLogMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(PmsProfitLogExample example) {
        return pmsProfitLogMapper.deleteByExample(example);
    }

    @Override
    public int deletePmsProfitTemp(String month, String user) {
        PmsProfitTempExample pmsProfitTempExample = new PmsProfitTempExample();
        PmsProfitTempExample.Criteria pmsProfitTempExampleCriteria = pmsProfitTempExample.createCriteria();
        pmsProfitTempExampleCriteria.andMonthEqualTo(month);
        pmsProfitTempExampleCriteria.andImportPersonEqualTo(user);
        return pmsProfitTempMapper.deleteByExample(pmsProfitTempExample);
    }

    @Override
    public int deletePmsProfit(String month, String user) {
        PmsProfitExample pmsProfitExample = new PmsProfitExample();
        PmsProfitExample.Criteria pmsProfitExampleCriteria = pmsProfitExample.createCriteria();
        pmsProfitExampleCriteria.andSettleMonthEqualTo(month);
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
    public int save(PmsProfit record) {
        return pmsProfitLogMapper.save(record);
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
    public List<Map<String, Object>> checkoutData(String agentId, String busCode) {
        return pmsProfitLogMapper.checkoutData(agentId, busCode);
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
            criteria.andUploadTimeBetween(example.getUploadTime() + " " + "00:00:00", example.getUploadTime() + " " + "23:59:59");
        }

        if (StringUtils.isNotBlank(example.getMonth())) {
            criteria.andMonthEqualTo(example.getMonth());
        }
        if (StringUtils.isNotBlank(example.getStatus())) {
            criteria.andStatusEqualTo(example.getStatus());
        }
      /*  if (StringUtils.isNotBlank(example.getUploadUser())) {
            criteria.andUploadUserEqualTo(example.getUploadUser());
        }*/
        pmsProfitLogExample.setOrderByClause("UPLOAD_TIME DESC");
        pmsProfitLogExample.setPage(page);

        List<PmsProfitLog> pmsProfitLogs = pmsProfitLogMapper.selectByExample(pmsProfitLogExample);
        for (PmsProfitLog pmsProfitLog : pmsProfitLogs) {
            Map<String, Object> map = pmsProfitLogMapper.getLoginName(pmsProfitLog.getUploadUser());
            pmsProfitLog.setUploadUser(map.get("NAME").toString());
        }

        Long count = pmsProfitLogMapper.countByExample(pmsProfitLogExample);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(pmsProfitLogs);
        pageInfo.setTotal(count.intValue());
        return pageInfo;
    }

    /**
     * 处理上传的Excel
     *
     * @param pmsProfitLog 上传实例
     * @param path         上传绝对路径
     * @throws MessageException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> disposeUploadExcel(PmsProfitLog pmsProfitLog, String path) throws MessageException {
        Map resultMap = new HashMap();
        String uploadPath = path + pmsProfitLog.getUploadPath();
        try {
            Workbook wookbook = getWookBook(uploadPath);
            /*sheet数量*/
            int sheetCount = 0;
            sheetCount = wookbook.getNumberOfSheets();
            String successResultPath = null;
            for (int i = 0; i < sheetCount; i++) {
                Sheet sheet = wookbook.getSheetAt(i);
                String sheetName = sheet.getSheetName();
                /*列数*/
                /*校验列数是否大于五列*/
                int columnNum = 0;
                int rows = sheet.getPhysicalNumberOfRows();
                if (rows > 0) {
                    Row hssfRow = sheet.getRow(0);
                    columnNum = hssfRow.getPhysicalNumberOfCells();
                }
                if (columnNum < 5) {
                    throw new MessageException("Sheet[" + i + "][" + sheetName + "]页列数不足! ");
                }
                /*判断是否有汇总sheet页*/
                boolean tf = false;
                //汇总页面写入出款流水并且输出
                if ("汇总".equals(sheetName)) {
                    for (int j = 1; j < rows; j++) {
                        Row row = sheet.getRow(j);
                        if (row == null) {
                            continue;
                        }

                        String sheetRow = sheetName + ((j + 2));
                        if (ProfitDataImportType.DYDL.key.equals(pmsProfitLog.getImportType())) {
                            Cell cell = row.getCell(4);
                            cell.setCellValue(idService.getPPTId());
                        } else if (ProfitDataImportType.DEDL.key.equals(pmsProfitLog.getImportType())) {
                            if (null != getCellValue(row.getCell(4)).trim() || "".equals(getCellValue(row.getCell(4)).trim()) || ProfitDataImportType.DSDL.key.equals(pmsProfitLog.getImportType())) {
                                String agId = getCellValue(row.getCell(0)).trim();
                                String month = getCellValue(row.getCell(2)).trim();
                                String busCode = getCellValue(row.getCell(3)).trim();
                                PmsProfitExample pmsProfitExample = new PmsProfitExample();

                                if (ProfitDataImportType.DSDL.key.equals(pmsProfitLog.getImportType())) {
                                    pmsProfitExample.or().andUniqueFlagEqualTo(agId).andSettleMonthEqualTo(month).andBusCodeEqualTo(busCode).andSheetNameEqualTo(sheetName).andProfitTypeEqualTo(ProfitDataImportType.DSDL.key);
                                }
                                pmsProfitExample.or().andUniqueFlagEqualTo(agId).andSettleMonthEqualTo(month).andBusCodeEqualTo(busCode).andSheetNameEqualTo(sheetName);
                                List<PmsProfit> pmsProfits = pmsProfitMapper.selectByExample(pmsProfitExample);
                                if (pmsProfits.size() != 1) {
                                    resultMap.put(sheetRow, sheetName + "sheet页第" + ((j + 2)) + "修改数据并不唯一");
                                }
                                if (pmsProfits.get(0).equals(getCellValue(row.getCell(4)).trim())) {
                                    continue;
                                } else {
                                    resultMap.put(sheetRow, sheetName + "sheet页第" + ((j + 2)) + "流水号与所生成不符合");
                                }
                            } else {
                                Cell cell = row.getCell(4);
                                cell.setCellValue(idService.getPPTId());
                            }

                        }
                    }
                    FileOutputStream out = null;
                    String[] splitResultPath = pmsProfitLog.getUploadPath().split("\\.");
                    successResultPath = path + splitResultPath[0] + "success." + splitResultPath[1];
                    pmsProfitLog.setResultPath(splitResultPath[0] + "success." + splitResultPath[1]);
                    pmsProfitLog.setResultName("(生成出款流水)" + pmsProfitLog.getUploadName());
                    try {
                        out = new FileOutputStream(successResultPath);
                        out.flush();
                        wookbook.write(out);
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new MessageException("IO流异常--插入流水后存取本地异常");
                    } finally {
                        out.close();
                    }
                    tf = true;
                }
                if (!tf) {
                    throw new MessageException("导入的Excel中没有汇总sheet页");
                }
            }
            resultMap.putAll(disposeUploadExcelSuccess(pmsProfitLog, path));
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MessageException(e.getMessage());
        }
    }

    /**
     * 处理增加流水号的上传Excel
     *
     * @param pmsProfitLog
     * @param successResultPath
     * @throws MessageException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> disposeUploadExcelSuccess(PmsProfitLog pmsProfitLog, String successResultPath) throws MessageException {
        //第一次导入删除之前导入
        if (ProfitDataImportType.DYDL.key.equals(pmsProfitLog.getImportType())) {
            //根据 月份、导入人删除
            deletePmsProfitTemp(pmsProfitLog.getMonth(), pmsProfitLog.getUploadUser());
            deletePmsProfit(pmsProfitLog.getMonth(), pmsProfitLog.getUploadUser());
        }
        Workbook wookbook = getWookBook(successResultPath);
        //sheet数
        int sheetCount = wookbook.getNumberOfSheets();
        Map<String, Object> showMap = new HashMap<>();
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
                Map<String, Object> pmsProfitLogMap = disposeSheet(rsList, sheetName, columnNum, pmsProfitLog.getMonth(), pmsProfitLog.getUploadUser(), i, pmsProfitLog.getImportType());
                if (pmsProfitLogMap != null && pmsProfitLogMap.size() != 0) {
                    showMap.putAll(pmsProfitLogMap);
                }
                updateByPrimaryKeySelective(pmsProfitLog);
            } catch (Exception e) {
                logger.error("调用disposeSheet方法异常：" + e.getMessage());
                e.printStackTrace();
                //根据 月份、导入人删除
                deletePmsProfitTemp(pmsProfitLog.getMonth(), pmsProfitLog.getUploadUser());
                deletePmsProfit(pmsProfitLog.getMonth(), pmsProfitLog.getUploadUser());
                pmsProfitLog.setStatus("1");
                pmsProfitLog.setNote("读取excel文件失败!2");
                try {
                    updateByPrimaryKeySelective(pmsProfitLog);
                } catch (Exception e1) {
                    e1.printStackTrace();
                    logger.error("失败后更新数据库异常：" + e1.getMessage());
                    throw new RuntimeException("失败后更新数据库异常");
                }
                throw e;
            }

        }
        return showMap;

    }

    public Workbook getWookBook(String successResultPath) throws MessageException {
        MultipartFile file = null;
        try {
            file = GetMultipartFile.createMultipartFile(successResultPath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new MessageException("IO流异常--读取本地文件时");
        }
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
            throw new MessageException("读取excel文件失败!1 ");
        }
        return wookbook;
    }


    public String callMapToXML(Map map) {
        StringBuffer sb = new StringBuffer();
        sb.append("<data>");
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

    }

    @Transactional
    public List<Map<String, Object>> saveSheet(List<Map<String, String>> list, String sheetName, int columnNum, String month, String userId, int sheetOrder, List<Map<String, String>> listOne, int theadi, int count, String type) {

        List<Map<String, Object>> saveSheetList = new ArrayList<>();
        if (listOne == null) {
            listOne = new ArrayList<>();
            listOne.add(list.get(0));
            list.removeAll(listOne);
        }
        for (int i = 0; i < list.size(); i++) {
            String sheetRow = sheetName + ((i + 2) + (theadi * count));

            if (list.get(i).get("Cell1") == null || "".equals(list.get(i).get("Cell1"))) {
                Map<String, Object> saveSheetMap = new HashMap<>();
                saveSheetMap.put(sheetRow, sheetName + "sheet页第" + ((i + 2) + (theadi * count)) + "行代理商名称为空");
                saveSheetList.add(saveSheetMap);
                continue;
            }
            if (list.get(i).get("Cell3") == null || "".equals(list.get(i).get("Cell3"))) {
                Map<String, Object> saveSheetMap = new HashMap<>();
                saveSheetMap.put(sheetRow, sheetName + "sheet页第" + ((i + 2) + (theadi * count)) + "行品牌码为空");
                saveSheetList.add(saveSheetMap);
                continue;
            }

            PmsProfitTempWithBLOBs pmsProfitTempWithBLOBs = new PmsProfitTempWithBLOBs();
            pmsProfitTempWithBLOBs.setMonth(month);
            pmsProfitTempWithBLOBs.setUniqueFlag((list.get(i).get("Cell0")));
            pmsProfitTempWithBLOBs.setAgentName((list.get(i).get("Cell1")));
            pmsProfitTempWithBLOBs.setBusCode(list.get(i).get("Cell3").trim());
            pmsProfitTempWithBLOBs.setSheetHead(callMapToXML(listOne.get(0)));
            pmsProfitTempWithBLOBs.setSheetData(callMapToXML(list.get(i)));
            pmsProfitTempWithBLOBs.setSheetName(sheetName);
            pmsProfitTempWithBLOBs.setImportPerson(userId);
            pmsProfitTempWithBLOBs.setOrderNumber(new BigDecimal((list.get(i).get("rowNum"))));


            PmsProfit pf = new PmsProfit();
            pf.setSettleMonth(month);
            pf.setProfitType(type);
            /*第几个sheet*/
            pf.setSheetOrder(new BigDecimal(sheetOrder));
            pf.setBillStatus("01");// 未打款
            try {
                pf.setBalanceAmt(new BigDecimal(list.get(i).get("Cell5")));
            } catch (Exception e) {
                Map<String, Object> saveSheetMap = new HashMap<>();
                saveSheetMap.put(sheetRow, sheetName + "sheet页第" + ((i + 2) + (theadi * count)) + "行解析分润金额出错");
                saveSheetList.add(saveSheetMap);
                e.printStackTrace();
                continue;
            }
            pf.setImportPerson(userId);
            pf.setImportTime(DateUtils.dateToStringss(new Date()));
            pf.setSheetName(sheetName);
            pf.setSheetColumn(new BigDecimal(columnNum));
            pf.setUpdatePerson(userId);
            pf.setUpdateTime(pf.getImportTime());
            pf.setOrderNumber(new BigDecimal((list.get(i).get("rowNum"))));

            if ("汇总".equals(sheetName)) {
                if (null != list.get(i).get("Cell4") && !"".equals(list.get(i).get("Cell4"))) {
                    pmsProfitTempWithBLOBs.setId(list.get(i).get("Cell4"));
                    pf.setBatchNo(list.get(i).get("Cell4"));
                } else {
                    Map<String, Object> saveSheetMap = new HashMap<>();
                    saveSheetMap.put(sheetRow, sheetName + "sheet页第" + ((i + 2) + (theadi * count)) + "行未获取到流水号，请联系研发人员");
                    saveSheetList.add(saveSheetMap);
                }
            } else {
                pmsProfitTempWithBLOBs.setId(idService.getPPTId());
                pf.setBalanceId(pmsProfitTempWithBLOBs.getId());
            }

            String agentId = list.get(i).get("Cell0").trim();
            pf.setUniqueFlag(agentId);

            String busCode = list.get(i).get("Cell3").trim();

            String agentIdrealy = list.get(i).get("Cell6").trim();

            pf.setUniqueFlag(agentId);
            List<Map<String, Object>> mapData;
            List<Map<String, Object>> mapre;
            try {
                mapData = checkoutData(agentId, busCode);
                mapre = checkoutData(agentIdrealy, null);
            } catch (RuntimeException e) {
                throw new RuntimeException("数据查询失败");
            }
            if (mapData.size() < 1) {
                Map<String, Object> saveSheetMap = new HashMap<>();
                saveSheetMap.put(sheetRow, sheetName + "sheet页第" + ((i + 2) + (theadi * count)) + "行的平台下" + busCode + "不存在此代理商唯一码" + agentId);
                saveSheetList.add(saveSheetMap);
                continue;

            }
            if (mapre.size() < 1) {
                Map<String, Object> saveSheetMap = new HashMap<>();
                saveSheetMap.put(sheetRow, sheetName + "sheet页第" + ((i + 2) + (theadi * count)) + "行" + busCode + "不存在此实际收款代理商唯一码" + agentId);
                saveSheetList.add(saveSheetMap);
                continue;

            }
            if (!month.equals((list.get(i).get("Cell2")))) {
                Map<String, Object> saveSheetMap = new HashMap<>();
                saveSheetMap.put(sheetRow, sheetName + "sheet页第" + ((i + 2) + (theadi * count)) + "行月份与选择不匹配");
                saveSheetList.add(saveSheetMap);
                continue;

            }

            pf.setAgentName((list.get(i).get("Cell1")));
            pf.setBusCode(list.get(i).get("Cell3").trim());


            list.get(i).remove("rowNum");
            try {
                if (ProfitDataImportType.DYDL.key.equals(type)) {
                    insertSelective(pmsProfitTempWithBLOBs);
                    save(pf);
                } else if (ProfitDataImportType.DEDL.key.equals(type) || ProfitDataImportType.DSDL.key.equals(type)) {
                    PmsProfitExample pmsProfitExample = new PmsProfitExample();
                    if (ProfitDataImportType.DSDL.key.equals(pf.getProfitType())) {
                        pmsProfitExample.or().andUniqueFlagEqualTo(pf.getUniqueFlag()).andSettleMonthEqualTo(pf.getSettleMonth()).andBusCodeEqualTo(pf.getBusCode()).andSheetNameEqualTo(sheetName).andProfitTypeEqualTo(ProfitDataImportType.DSDL.key);
                    }
                    pmsProfitExample.or().andUniqueFlagEqualTo(pf.getUniqueFlag()).andSettleMonthEqualTo(pf.getSettleMonth()).andBusCodeEqualTo(pf.getBusCode()).andSheetNameEqualTo(sheetName);
                    List<PmsProfit> pmsProfits = pmsProfitMapper.selectByExample(pmsProfitExample);
                    if (pmsProfits != null && pmsProfits.size() == 1) {
                        pmsProfitMapper.deleteByExample(pmsProfitExample);
                        PmsProfitTempExample pmsProfitTempExample = new PmsProfitTempExample();
                        pmsProfitTempExample.or().andUniqueFlagEqualTo(pmsProfits.get(0).getBalanceId());
                        pmsProfitTempMapper.deleteByExample(pmsProfitTempExample);
                        insertSelective(pmsProfitTempWithBLOBs);
                        save(pf);
                    } else {
                        insertSelective(pmsProfitTempWithBLOBs);
                        save(pf);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                e.getMessage();
                logger.error("保存数据失败，sheet:" + pf.getSheetName() + "第" + (pf.getOrderNumber().add(new BigDecimal(1))) + "行:" + JSONObject.toJSONString(pf));
                throw new RuntimeException(e.toString() + pf.getSheetName() + "第" + pf.getOrderNumber() + "行");
            }

        }
        return saveSheetList;
    }

    @Override
    public int updateByPrimaryKey(PmsProfitLog record) {
        return pmsProfitLogMapper.updateByPrimaryKeySelective(record);
    }

    public static Executor newFixedThreadPool(int nThreads) {
        return new ThreadPoolExecutor(nThreads, nThreads,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
    }

    /**
     * 取得单元格内容.
     *
     * @param cell
     * @return
     * @throws Exception
     * @author chenliang
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
        return cellValue;
    }

    @Override
    @Transactional
    public Map<String, Object> disposeSheet(List<Map<String, String>> sheetlists, String sheetName, int columnNum, String month, String userId, int sheetOrder, String type) {
        Map<String, Object> reMap = new HashMap<>();

        if (sheetlists.size() > 0 && sheetlists.size() < 11) {
            reMap.put(sheetName, saveSheet(sheetlists, sheetName, columnNum, month, userId, sheetOrder, null, 0, 0, type));
            return reMap;
        } else {
            List<String> taskNameList = new ArrayList<String>();
            List<FutureTask<Map<String, Object>>> taskList = new ArrayList();

            Executor executor = newFixedThreadPool(10);

            List<Map<String, String>> listOne = new ArrayList<>();
            listOne.add(sheetlists.get(0));
            sheetlists.removeAll(listOne);


            int count = sheetlists.size() / 10;
            int yu = sheetlists.size() % 10;
            for (int z = 0; z < 10; z++) {
                List<Map<String, String>> list;
                if (z == 9) {
                    list = sheetlists.subList(z * count, count * (z + 1) + yu);
                } else {
                    list = sheetlists.subList(z * count, count * (z + 1));
                }
                try {
                    SheetThead sheetThead = new SheetThead(list, sheetName, columnNum, month, userId, sheetOrder, listOne, z, count, type);
                    FutureTask<Map<String, Object>> thread = new FutureTask<>(sheetThead);
                    taskNameList.add("{ SheetThead====" + sheetName + z + "}");
                    taskList.add(thread);
                    executor.execute(thread);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e.getMessage());
                }

            }

            // 等待线程执行结束
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
            threadPoolExecutor.shutdown();
            while (true) {
                if (threadPoolExecutor.getActiveCount() <= 0) {
                    break;
                }
            }

            Map<String, Object> resultMap = FutureTaskUtils.getTaskResult(taskList, taskNameList, logger);
            // 获取线程执行结果
            if ("fail".equals(resultMap.get("result"))) {
                throw new RuntimeException(String.valueOf(resultMap.get("Err")));
            }
            if ("dispose".equals(resultMap.get("result"))) {
                resultMap.remove("result");
                return resultMap;
            }
        }
        return reMap;
    }


    class SheetThead implements Callable<Map<String, Object>> {

        private List<Map<String, String>> sheetlists;
        private String month;
        private String userId;
        private int sheetOrder;
        private PmsProfitLog pmsProfitLog;
        private String sheetName;
        int columnNum;
        private List<Map<String, String>> listOne;
        int i;
        int count;
        String type;

        SheetThead(List<Map<String, String>> sheetlists, String sheetName, int columnNum, String month, String userId, int sheetOrder, List<Map<String, String>> listOne, int i, int count, String type) {
            this.sheetlists = sheetlists;
            this.month = month;
            this.userId = userId;
            this.sheetOrder = sheetOrder;
            this.sheetName = sheetName;
            this.columnNum = columnNum;
            this.listOne = listOne;
            this.i = i;
            this.count = count;
            this.type = type;
        }


        @Override
        public Map<String, Object> call() {
            logger.info("分润导入线程 执行开始-当前线程：{" + this.i + "}");
            Map<String, Object> rMap = new HashMap<>();
            try {
                List<Map<String, Object>> saveSheetList = saveSheet(sheetlists, sheetName, columnNum, month, userId, sheetOrder, listOne, i, count, type);
                if (saveSheetList == null || saveSheetList.size() == 0) {
                    rMap.put("result", "success");
                } else {
                    rMap.put("result", "dispose");
                    rMap.put("disposeResult", saveSheetList);
                }
            } catch (Exception e) {
                e.getStackTrace();
                logger.error("分润导入线程 执行结束-当前线程：{" + this.i + "}" + "错误：" + e.getMessage());
                rMap.put("result", "fail");
                rMap.put("Err", "读取Excel文件失败");
            }
            logger.info("分润导入线程 执行结束-当前线程：{" + this.i + "}");
            return rMap;
        }
    }

}
