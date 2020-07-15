package com.ryx.credit.pft.month.service.impl;

import com.ryx.credit.common.enumc.BillStatus;
import com.ryx.credit.common.enumc.ProfitDataImportType;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pft.month.dao.PmsProfitLogMapper;
import com.ryx.credit.pft.month.dao.PmsProfitMapper;
import com.ryx.credit.pft.month.dao.PmsProfitTempMapper;
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

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.SQLException;
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
    public PageInfo selectByMap(Map<String, String> param, Page page) {
        PageInfo pageInfo = new PageInfo();
        List<Map<String, Object>> selectByMaps = pmsProfitLogMapper.selectByMap(param, page);
        selectByMaps.forEach(map -> {
            if (map.get("NOTE") != null) {
                Clob clob = (Clob) map.get("NOTE");
                try {
                    String cString = ClobToString(clob);
                    map.put("NOTE", cString);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
        pageInfo.setRows(selectByMaps);
        pageInfo.setTotal((int) pmsProfitLogMapper.getCountByMap(param));
        return pageInfo;
    }

    public String ClobToString(Clob clob) throws SQLException, IOException {
        String reString = "";
        Reader is = clob.getCharacterStream();
        BufferedReader br = new BufferedReader(is);
        String s = br.readLine();
        StringBuffer sb = new StringBuffer();
        while (s != null) {
            sb.append(s);
            s = br.readLine();
        }
        reString = sb.toString();
        if (br != null) {
            br.close();
        }
        if (is != null) {
            is.close();
        }
        return reString;
    }


    @Override
    public long countByExample(PmsProfitLogExample example) {
        return pmsProfitLogMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(PmsProfitLogExample example) {
        return pmsProfitLogMapper.deleteByExample(example);
    }

    @Override
    public int deletePmsProfitTemp(PmsProfitTempExample pmsProfitTempExample) {
        return pmsProfitTempMapper.deleteByExample(pmsProfitTempExample);
    }

    @Override
    public int deletePmsProfit(PmsProfitExample pmsProfitExample) {
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
    public int insertSelectiveTemp(PmsProfitTempWithBLOBs record) {
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
    public int updateByPrimaryKeySelectiveTemp(PmsProfitTempWithBLOBs record) {
        return pmsProfitTempMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(PmsProfit record) {
        return pmsProfitMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<Map<String, Object>> checkoutData(Map<String, Object> param) {
        return pmsProfitLogMapper.checkoutData(param);
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
     * 不在事务中进行删除
     *
     * @param pmsProfitLog 上传实例
     * @param path         上传绝对路径
     * @throws MessageException
     */
    @Override
    public Map<String, Object> disposeUploadExcel(PmsProfitLog pmsProfitLog, String path) throws MessageException {
        //第一次导入删除之前导入
        //根据 月份、导入人，导入批次删除
        if (ProfitDataImportType.DYDL.key.equals(pmsProfitLog.getImportType()) || ProfitDataImportType.MXDL.key.equals(pmsProfitLog.getImportType())) {
            PmsProfitTempExample pmsProfitTempExample = new PmsProfitTempExample();
            pmsProfitTempExample.or().andMonthEqualTo(pmsProfitLog.getMonth()).andImportPersonEqualTo(pmsProfitLog.getUploadUser()).andImportTypeEqualTo((pmsProfitLog.getImportType()));
            deletePmsProfitTemp(pmsProfitTempExample);
            PmsProfitExample pmsProfitExample = new PmsProfitExample();
            pmsProfitExample.or().andSettleMonthEqualTo(pmsProfitLog.getMonth()).andImportPersonEqualTo(pmsProfitLog.getUploadUser()).andProfitTypeEqualTo((pmsProfitLog.getImportType()));
            deletePmsProfit(pmsProfitExample);
        }
        if (ProfitDataImportType.MXDL.key.equals(pmsProfitLog.getImportType())) {
            return disposeUploadExcelSuccess(pmsProfitLog, path);
        }
        Map<String, Object> stringObjectMap = disposeUploadExcel2(pmsProfitLog, path);

        return stringObjectMap;

    }

    /**
     * 处理上传的Excel
     *
     * @param pmsProfitLog 上传实例
     * @param path         上传绝对路径
     * @throws MessageException
     */
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> disposeUploadExcel2(PmsProfitLog pmsProfitLog, String path) throws MessageException {
        Map resultMap = new HashMap();
        String uploadPath = path + pmsProfitLog.getUploadPath();
        try {
            Workbook wookbook = getWookBook(uploadPath);
            /*sheet数量*/
            int sheetCount  = wookbook.getNumberOfSheets();
            if(ProfitDataImportType.DYDL.key.equals(pmsProfitLog.getImportType())||ProfitDataImportType.DEDL.key.equals(pmsProfitLog.getImportType())||ProfitDataImportType.DSDL.key.equals(pmsProfitLog.getImportType())) {
                if(sheetCount!=1){
                    throw new MessageException("一次请款,二次请款以及补出款只能有一个sheet页面，并且sheet名为--汇总");
                }
            }
            String successResultPath = null;
            /*判断是否有汇总sheet页*/
            boolean tf = false;
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

                //汇总页面写入出款流水并且输出
                if ("汇总".equals(sheetName)) {
                    List<Map<String, Object>> resultList = new ArrayList<>();
                    tf = true;
                    for (int j = 1; j < rows; j++) {
                        Row row = sheet.getRow(j);
                        if (row == null) {
                            continue;
                        }

                        String sheetRow = sheetName + ((j + 1));
                        if (ProfitDataImportType.DYDL.key.equals(pmsProfitLog.getImportType())) {

                            try {
                                Cell cell =null;
                                if (row.getCell(4)==null){
                                    cell = row.createCell(4);
                                }else{
                                    cell = row.getCell(4);
                                }
                                logger.info("获得的对象"+cell);
                                cell.setCellValue(idService.getPPTId());
                            } catch (Exception e) {
                                logger.info(sheetName + "sheet页第" + ((j + 1)) + "行");
                                e.printStackTrace();
                                throw e;
                            }
                        } else if (ProfitDataImportType.DEDL.key.equals(pmsProfitLog.getImportType()) || ProfitDataImportType.DSDL.key.equals(pmsProfitLog.getImportType())) {
                            String agId = getCellValue(row.getCell(0)).trim();
                            String month = getCellValue(row.getCell(2)).trim();
                            String orgId = getCellValue(row.getCell(8)).trim();
                            PmsProfitExample pmsProfitExample = new PmsProfitExample();
                            pmsProfitExample.or().andUniqueFlagEqualTo(agId).andSettleMonthEqualTo(month).andOrgIdEqualTo(orgId).andSheetNameEqualTo(sheetName);

                            if (ProfitDataImportType.DSDL.key.equals(pmsProfitLog.getImportType())) {
                                pmsProfitExample.or().andUniqueFlagEqualTo(agId).andSettleMonthEqualTo(month).andOrgIdEqualTo(orgId).andSheetNameEqualTo(sheetName).andProfitTypeEqualTo(ProfitDataImportType.DSDL.key);
                            } else {
                                pmsProfitExample.or().andUniqueFlagEqualTo(agId).andSettleMonthEqualTo(month).andOrgIdEqualTo(orgId).andSheetNameEqualTo(sheetName);
                            }
                            List<PmsProfit> pmsProfits = pmsProfitMapper.selectByExample(pmsProfitExample);
                            if (null != getCellValue(row.getCell(4)) && !"".equals(getCellValue(row.getCell(4)).trim())) {
                                if (pmsProfits.size() == 0) {
                                    Map<String, Object> SheetMap = new HashMap<>();
                                    SheetMap.put(sheetRow, sheetName + "sheet页第" + ((j + 1)) + "修改数据输入有误此流水对应信息不存在");
                                    resultList.add(SheetMap);
                                    continue;
                                } else if (pmsProfits.size() > 1) {
                                    Map<String, Object> SheetMap = new HashMap<>();
                                    SheetMap.put(sheetRow, sheetName + "sheet页第" + ((j + 1)) + "行查询到一次请款或补出款的多条记录，请确定是否不是同一人操作，避免重复出款");
                                    resultList.add(SheetMap);
                                    continue;
                                }
                                if (pmsProfits.get(0).getBalanceId().equals(getCellValue(row.getCell(4)).trim())) {
                                    continue;
                                } else {
                                    Map<String, Object> SheetMap = new HashMap<>();
                                    SheetMap.put(sheetRow, sheetName + "sheet页第" + ((j + 1)) + "流水号与所生成不符合");
                                    resultList.add(SheetMap);
                                    continue;
                                }
                            } else {
                                if (pmsProfits.size() > 0) {
                                    Map<String, Object> SheetMap = new HashMap<>();
                                    SheetMap.put(sheetRow, sheetName + "sheet页第" + ((j + 1)) + "已经存在此条记录，无法新增，请检查并填入流水号进行修改。");
                                    resultList.add(SheetMap);
                                    continue;
                                }
                                Cell cell = row.getCell(4);
                                cell.setCellValue(idService.getPPTId());
                            }

                        }
                    }
                    if (resultList.size() > 0) {
                        resultMap.put("sheetFail", resultList);
                    }

                }
                if (!tf) {
                    throw new MessageException("导入的Excel中没有汇总sheet页");
                }


            }
            if (resultMap.size() > 0 && !ProfitDataImportType.DYDL.key.equals(pmsProfitLog.getImportType())) {
                return resultMap;
            } else {
                /*成功sheet*/
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


                resultMap.putAll(disposeUploadExcelSuccess(pmsProfitLog, path));
                return resultMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new MessageException(e.getMessage());
        }
    }

    /**
     * 处理增加流水号的上传Excel
     *
     * @param pmsProfitLog
     * @param path
     * @throws MessageException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> disposeUploadExcelSuccess(PmsProfitLog pmsProfitLog, String path) throws MessageException {
        String uploadPath = null;
        boolean hzIsNo = false;
        if (ProfitDataImportType.MXDL.key.equals(pmsProfitLog.getImportType())) {
            uploadPath = path + pmsProfitLog.getUploadPath();
            hzIsNo = true;
        } else {
            uploadPath = path + pmsProfitLog.getResultPath();
        }
        Workbook wookbook = getWookBook(uploadPath);
        //sheet数
        int sheetCount = wookbook.getNumberOfSheets();
        Map<String, Object> showMap = new HashMap<>();
        for (int i = 0; i < sheetCount; i++) {
            int rowNum = 0;
            Sheet sheet = wookbook.getSheetAt(i);
            String sheetName = sheet.getSheetName();
            if(hzIsNo &&"汇总".equals(sheetName)){
                throw new RuntimeException("分润明细导入中不允许有汇总sheet页");
            }
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
                Map<String, Object> pmsProfitLogMap = disposeSheet(rsList, sheetName, columnNum, pmsProfitLog, i);
                if (pmsProfitLogMap != null && pmsProfitLogMap.size() != 0) {
                    showMap.putAll(pmsProfitLogMap);
                }
                updateByPrimaryKeySelective(pmsProfitLog);
            } catch (Exception e) {
                logger.error("调用disposeSheet方法异常：" + e.getMessage());
                e.printStackTrace();
                if ((pmsProfitLog.getImportType().equals(ProfitDataImportType.DYDL.key)) || (pmsProfitLog.getImportType().equals(ProfitDataImportType.MXDL.key))) {
                    //根据 月份、导入人导入人删除
                    PmsProfitTempExample pmsProfitTempExample = new PmsProfitTempExample();
                    pmsProfitTempExample.or().andMonthEqualTo(pmsProfitLog.getMonth()).andImportPersonEqualTo(pmsProfitLog.getUploadUser());
                    deletePmsProfitTemp(pmsProfitTempExample);
                    PmsProfitExample pmsProfitExample = new PmsProfitExample();
                    pmsProfitExample.or().andSettleMonthEqualTo(pmsProfitLog.getMonth()).andImportPersonEqualTo(pmsProfitLog.getUploadUser());
                    deletePmsProfit(pmsProfitExample);
                } else {
                    //导入批次删除
                    PmsProfitTempExample pmsProfitTempExample = new PmsProfitTempExample();
                    pmsProfitTempExample.or().andImportBatchEqualTo(pmsProfitLog.getBatchNo());
                    deletePmsProfitTemp(pmsProfitTempExample);
                    PmsProfitExample pmsProfitExample = new PmsProfitExample();
                    pmsProfitExample.or().andImportBatchEqualTo(pmsProfitLog.getBatchNo());
                    deletePmsProfit(pmsProfitExample);

                }

                pmsProfitLog.setStatus("1");
                pmsProfitLog.setNote("读取excel文件失败!2" + e.getMessage());
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
        if (showMap != null && showMap.size() != 0) {
            /* 根据 月份、导入人删除第一次请款*/
            if (ProfitDataImportType.DYDL.key.equals(pmsProfitLog.getImportType())) {
                //根据 月份、导入人导入人删除
                PmsProfitTempExample pmsProfitTempExample = new PmsProfitTempExample();
                pmsProfitTempExample.or().andMonthEqualTo(pmsProfitLog.getMonth()).andImportPersonEqualTo(pmsProfitLog.getUploadUser());
                deletePmsProfitTemp(pmsProfitTempExample);
                PmsProfitExample pmsProfitExample = new PmsProfitExample();
                pmsProfitExample.or().andSettleMonthEqualTo(pmsProfitLog.getMonth()).andImportPersonEqualTo(pmsProfitLog.getUploadUser());
                deletePmsProfit(pmsProfitExample);
            } else {
                //导入批次删除
                PmsProfitTempExample pmsProfitTempExample = new PmsProfitTempExample();
                pmsProfitTempExample.or().andImportBatchEqualTo(pmsProfitLog.getBatchNo());
                deletePmsProfitTemp(pmsProfitTempExample);
                PmsProfitExample pmsProfitExample = new PmsProfitExample();
                pmsProfitExample.or().andImportBatchEqualTo(pmsProfitLog.getBatchNo());
                deletePmsProfit(pmsProfitExample);
            }

        }
        return showMap;
    }

    @Override
    public Map<String, Object> btnIsNo(String MONTH) {
        return pmsProfitLogMapper.btnIsNo(MONTH);
    }

    @Override
    public Map<String, Object> btnInsert(Map<String, String> param) {
        return pmsProfitLogMapper.btnInsert(param);
    }

    @Override
    public Map<String, Object> btnUpdate(Map<String, String> param) {
        return pmsProfitLogMapper.btnUpdate(param);
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
    public List<Map<String, Object>> saveSheet(List<Map<String, String>> list, String sheetName, int columnNum, PmsProfitLog pmsProfitLog, int sheetOrder, List<Map<String, String>> listOne, int theadi, int count) throws MessageException {

        List<Map<String, Object>> saveSheetList = new ArrayList<>();
        if (listOne == null) {
            listOne = new ArrayList<>();
            listOne.add(list.get(0));
            list.removeAll(listOne);
        }
        for (int i = 0; i < list.size(); i++) {
            String sheetRow = sheetName + ((i + 2) + (theadi * count));

            if (list.get(i).get("Cell0") == null || "".equals(list.get(i).get("Cell0"))) {
                Map<String, Object> saveSheetMap = new HashMap<>();
                saveSheetMap.put(sheetRow, sheetName + "sheet页第" + ((i + 2) + (theadi * count)) + "行代理商AG码为空");
                saveSheetList.add(saveSheetMap);
                continue;
            }

            if (list.get(i).get("Cell1") == null || "".equals(list.get(i).get("Cell1"))) {
                Map<String, Object> saveSheetMap = new HashMap<>();
                saveSheetMap.put(sheetRow, sheetName + "sheet页第" + ((i + 2) + (theadi * count)) + "行代理商名称为空");
                saveSheetList.add(saveSheetMap);
                continue;
            }
            if (list.get(i).get("Cell2") == null || "".equals(list.get(i).get("Cell2"))) {
                Map<String, Object> saveSheetMap = new HashMap<>();
                saveSheetMap.put(sheetRow, sheetName + "sheet页第" + ((i + 2) + (theadi * count)) + "行月份为空");
                saveSheetList.add(saveSheetMap);
                continue;
            }

//            if (list.get(i).get("Cell3") == null || "".equals(list.get(i).get("Cell3"))) {
//                Map<String, Object> saveSheetMap = new HashMap<>();
//                saveSheetMap.put(sheetRow, sheetName + "sheet页第" + ((i + 2) + (theadi * count)) + "行品牌码为空");
//                saveSheetList.add(saveSheetMap);
//                continue;
//            }


            if (!pmsProfitLog.getMonth().equals((list.get(i).get("Cell2")))) {
                Map<String, Object> saveSheetMap = new HashMap<>();
                saveSheetMap.put(sheetRow, sheetName + "sheet页第" + ((i + 2) + (theadi * count)) + "行月份与选择不匹配");
                saveSheetList.add(saveSheetMap);
                continue;

            }

            try {
                List agentIdList = checkoutData(FastMap.fastMap("agentId", String.valueOf(list.get(i).get("Cell0"))));
                if (agentIdList.size() < 1) {
                    Map<String, Object> saveSheetMap = new HashMap<>();
                    saveSheetMap.put(sheetRow, sheetName + "sheet页第" + ((i + 2) + (theadi * count)) + "行不存在此代理商唯一码" + list.get(i).get("Cell0"));
                    saveSheetList.add(saveSheetMap);
                    continue;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new MessageException(sheetName + "实际代理商数据查询失败");
            }



            PmsProfitTempWithBLOBs pmsProfitTempWithBLOBs = new PmsProfitTempWithBLOBs();
            pmsProfitTempWithBLOBs.setMonth(pmsProfitLog.getMonth());
            pmsProfitTempWithBLOBs.setUniqueFlag((list.get(i).get("Cell0")));
            pmsProfitTempWithBLOBs.setAgentName((list.get(i).get("Cell1")));
            pmsProfitTempWithBLOBs.setBusCode("#");
            pmsProfitTempWithBLOBs.setSheetHead(callMapToXML(listOne.get(0)));
            pmsProfitTempWithBLOBs.setSheetData(callMapToXML(list.get(i)));
            pmsProfitTempWithBLOBs.setSheetName(sheetName);
            pmsProfitTempWithBLOBs.setImportPerson(pmsProfitLog.getUploadUser());
            pmsProfitTempWithBLOBs.setOrderNumber(new BigDecimal((list.get(i).get("rowNum"))));
            pmsProfitTempWithBLOBs.setImportType(pmsProfitLog.getImportType());
            pmsProfitTempWithBLOBs.setImportBatch(pmsProfitLog.getBatchNo());


            PmsProfit pf = new PmsProfit();
            pf.setSettleMonth(pmsProfitLog.getMonth());
            pf.setProfitType(pmsProfitLog.getImportType());
            /*第几个sheet*/
            pf.setSheetOrder(new BigDecimal(sheetOrder));
            pf.setBillStatus(BillStatus.WCK.key);// 未打款

            pf.setImportPerson(pmsProfitLog.getUploadUser());
            pf.setImportTime(DateUtils.dateToStringss(new Date()));
            pf.setSheetName(sheetName);
            pf.setSheetColumn(new BigDecimal(columnNum));
            pf.setUpdatePerson(pmsProfitLog.getUploadUser());
            pf.setUpdateTime(pf.getImportTime());
            pf.setOrderNumber(new BigDecimal((list.get(i).get("rowNum"))));
            pf.setImportBatch(pmsProfitLog.getBatchNo());
            pf.setBusCode("#");

            String agentId = list.get(i).get("Cell0").trim();
            String orgID =null;
            if ("汇总".equals(sheetName)) {
                pmsProfitTempWithBLOBs.setBusCode(list.get(i).get("Cell3"));
                if(pmsProfitTempWithBLOBs.getBusCode() ==null||"".equals(pmsProfitTempWithBLOBs.getBusCode())){
                    pmsProfitTempWithBLOBs.setBusCode("#");
                }
                 orgID = list.get(i).get("Cell8").trim();

                if (null != list.get(i).get("Cell4") && !"".equals(list.get(i).get("Cell4"))) {
                    pmsProfitTempWithBLOBs.setId(list.get(i).get("Cell4"));
                    pf.setBalanceId(list.get(i).get("Cell4"));
                } else {
                    Map<String, Object> saveSheetMap = new HashMap<>();
                    saveSheetMap.put(sheetRow, sheetName + "sheet页第" + ((i + 2) + (theadi * count)) + "行未获取到流水号，请联系研发人员");
                    saveSheetList.add(saveSheetMap);
                    continue;
                }
                if (list.get(i).get("Cell6") == null || "".equals(list.get(i).get("Cell6"))) {
                    Map<String, Object> saveSheetMap = new HashMap<>();
                    saveSheetMap.put(sheetRow, sheetName + "sheet页第" + ((i + 2) + (theadi * count)) + "行实际收款代理商AG为空");
                    saveSheetList.add(saveSheetMap);
                    continue;
                }
                if (list.get(i).get("Cell7") == null || "".equals(list.get(i).get("Cell7"))) {
                    Map<String, Object> saveSheetMap = new HashMap<>();
                    saveSheetMap.put(sheetRow, sheetName + "sheet页第" + ((i + 2) + (theadi * count)) + "行实际收款代理商名称为空");
                    saveSheetList.add(saveSheetMap);
                    continue;
                }
                if (list.get(i).get("Cell8") == null || "".equals(list.get(i).get("Cell8"))) {
                    Map<String, Object> saveSheetMap = new HashMap<>();
                    saveSheetMap.put(sheetRow, sheetName + "sheet页第" + ((i + 2) + (theadi * count)) + "行代理商平台码为空");
                    saveSheetList.add(saveSheetMap);
                    continue;
                }
                List<Map<String, Object>> mapre = new ArrayList<>();
                String agentIdrealy = list.get(i).get("Cell6").trim();
                try {
                    mapre = checkoutData(FastMap.fastMap("agentId", agentIdrealy));
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new MessageException(sheetName + "实际代理商数据查询失败");
                }
                if (mapre.size() < 1) {
                    Map<String, Object> saveSheetMap = new HashMap<>();
                    saveSheetMap.put(sheetRow, sheetName + "sheet页第" + ((i + 2) + (theadi * count)) + "行不存在此实际收款代理商唯一码" + agentIdrealy);
                    saveSheetList.add(saveSheetMap);
                    continue;
                }
                try {
                    pf.setBalanceAmt(new BigDecimal(list.get(i).get("Cell5")));
                } catch (Exception e) {
                    Map<String, Object> saveSheetMap = new HashMap<>();
                    saveSheetMap.put(sheetRow, sheetName + "sheet页第" + ((i + 2) + (theadi * count)) + "行解析分润金额出错");
                    saveSheetList.add(saveSheetMap);
                    e.printStackTrace();
                    continue;
                }
                pf.setRealityAgId((list.get(i).get("Cell6")));
                pf.setRealityAgName(list.get(i).get("Cell7").trim());

                List<Map<String, Object>> mapData = checkoutData(FastMap.fastMap("agentId", agentId).putKeyV("orgID", orgID));
                if (mapData.size() < 1) {
                    Map<String, Object> saveSheetMap = new HashMap<>();
                    saveSheetMap.put(sheetRow, sheetName + "sheet页第" + ((i + 2) + (theadi * count)) + "行O码" + orgID + "不属于代理商唯一码" + agentId);
                    saveSheetList.add(saveSheetMap);
                    continue;

                }

                pf.setBusCode(pmsProfitTempWithBLOBs.getBusCode());
                pf.setOrgId(orgID);
                pf.setRenitStatus(list.get(i).get("Cell9"));
            } else {
                pmsProfitTempWithBLOBs.setId(idService.getPPTId());
                pf.setBalanceId(pmsProfitTempWithBLOBs.getId());
            }


//
//            if (mapData.size() < 1) {
//                Map<String, Object> saveSheetMap = new HashMap<>();
//                saveSheetMap.put(sheetRow, sheetName + "sheet页第" + ((i + 2) + (theadi * count)) + "行的平台下" + busCode + "不存在此代理商唯一码" + agentId);
//                saveSheetList.add(saveSheetMap);
//                continue;
//
//            }

            pf.setUniqueFlag(agentId);
            pf.setAgentName((list.get(i).get("Cell1")).trim());



            try {
                if (ProfitDataImportType.DYDL.key.equals(pmsProfitLog.getImportType()) || ProfitDataImportType.MXDL.key.equals(pmsProfitLog.getImportType())) {
                    insertSelectiveTemp(pmsProfitTempWithBLOBs);
                    save(pf);
                } else if (ProfitDataImportType.DEDL.key.equals(pmsProfitLog.getImportType()) || ProfitDataImportType.DSDL.key.equals(pmsProfitLog.getImportType())) {
                    PmsProfitExample pmsProfitExample = new PmsProfitExample();

                    if (ProfitDataImportType.DSDL.key.equals(pf.getProfitType())) {
                        pmsProfitExample.or().andUniqueFlagEqualTo(pf.getUniqueFlag()).andSettleMonthEqualTo(pf.getSettleMonth()).andOrgIdEqualTo(pf.getBusCode()).andSheetNameEqualTo(sheetName).andProfitTypeEqualTo(ProfitDataImportType.DSDL.key).andBusCodeEqualTo(list.get(i).get("Cell3"));
                    } else {
                        pmsProfitExample.or().andUniqueFlagEqualTo(pf.getUniqueFlag()).andSettleMonthEqualTo(pf.getSettleMonth()).andOrgIdEqualTo(pf.getOrgId()).andSheetNameEqualTo(sheetName);
                    }
                    List<PmsProfit> pmsProfits = pmsProfitMapper.selectByExample(pmsProfitExample);
                    if (pmsProfits != null && pmsProfits.size() > 0) {
                        if (sheetName.equals("汇总")) {
                            if (pmsProfits.get(0).getBalanceId().equals((list.get(i).get("Cell4")))) {
                                if (ProfitDataImportType.DSDL.key.equals(pmsProfitLog.getImportType())) {
                                    if (BillStatus.WCK.key.equals(pmsProfits.get(0).getBillStatus()) || BillStatus.CKSB.key.equals(pmsProfits.get(0).getBillStatus()) || BillStatus.SPSB.key.equals(pmsProfits.get(0).getBillStatus()) || BillStatus.FRDF.key.equals(pmsProfits.get(0).getBillStatus()) || BillStatus.FRTF.key.equals(pmsProfits.get(0).getBillStatus())) {
                                        pmsProfitMapper.deleteByExample(pmsProfitExample);
                                        PmsProfitTempExample pmsProfitTempExample = new PmsProfitTempExample();
                                        pmsProfitTempExample.or().andIdEqualTo(pmsProfits.get(0).getBalanceId());
                                        pmsProfitTempMapper.deleteByExample(pmsProfitTempExample);
                                        insertSelectiveTemp(pmsProfitTempWithBLOBs);
                                        pf.setBillStatus(pmsProfits.get(0).getBillStatus());
                                        save(pf);
                                    } else {
                                        Map<String, Object> saveSheetMap = new HashMap<>();
                                        saveSheetMap.put(sheetRow, sheetName + "sheet页第" + ((i + 2) + (theadi * count)) + "行导入已经进入审批，出款中，或者出款成功");
                                        saveSheetList.add(saveSheetMap);
                                        continue;
                                    }
                                } else if (ProfitDataImportType.DEDL.key.equals(pmsProfitLog.getImportType())) {
                                    pmsProfitMapper.deleteByExample(pmsProfitExample);
                                    PmsProfitTempExample pmsProfitTempExample = new PmsProfitTempExample();
                                    pmsProfitTempExample.or().andIdEqualTo(pmsProfits.get(0).getBalanceId());
                                    pmsProfitTempMapper.deleteByExample(pmsProfitTempExample);
                                    insertSelectiveTemp(pmsProfitTempWithBLOBs);
                                    pf.setBillStatus(pmsProfits.get(0).getBillStatus());
                                    save(pf);
                                }

                            } else {
                                Map<String, Object> saveSheetMap = new HashMap<>();
                                saveSheetMap.put(sheetRow, sheetName + "sheet页第" + ((i + 2) + (theadi * count)) + "行导入的出款流水与已知流水不对应");
                                saveSheetList.add(saveSheetMap);
                                continue;
                            }
                        } else {
                            pmsProfitMapper.deleteByExample(pmsProfitExample);
                            PmsProfitTempExample pmsProfitTempExample = new PmsProfitTempExample();
                            pmsProfitTempExample.or().andIdEqualTo(pmsProfits.get(0).getBalanceId());
                            pmsProfitTempMapper.deleteByExample(pmsProfitTempExample);
                            insertSelectiveTemp(pmsProfitTempWithBLOBs);
                            pf.setBillStatus(pmsProfits.get(0).getBillStatus());
                            save(pf);
                        }
                    } else {
                        insertSelectiveTemp(pmsProfitTempWithBLOBs);
                        save(pf);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                e.getMessage();
                logger.error("saveSheet方法调用失败" + e.getMessage());
                throw new MessageException("saveSheet方法调用失败" + e.getMessage());
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
    public Map<String, Object> disposeSheet(List<Map<String, String>> sheetlists, String sheetName, int columnNum, PmsProfitLog pmsProfitLog, int sheetOrder) throws MessageException {
        Map<String, Object> reMap = new HashMap<>();

        if (sheetlists.size() > 0 && sheetlists.size() < 11) {
            try {
                List<Map<String, Object>> maps = saveSheet(sheetlists, sheetName, columnNum, pmsProfitLog, sheetOrder, null, 0, 0);
                if (maps != null && maps.size() > 0) {
                    reMap.put(sheetName, maps);
                }
            } catch (MessageException e) {
                reMap.put("result", "fail");
                reMap.put("Err", e.getMsg());
                e.printStackTrace();
            }
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
                    SheetThead sheetThead = new SheetThead(list, sheetName, columnNum, pmsProfitLog, sheetOrder, listOne, z, count);
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
                throw new MessageException(String.valueOf(resultMap.get("Err")));
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
        private int sheetOrder;
        private PmsProfitLog pmsProfitLog;
        private String sheetName;
        int columnNum;
        private List<Map<String, String>> listOne;
        int i;
        int count;

        SheetThead(List<Map<String, String>> sheetlists, String sheetName, int columnNum, PmsProfitLog pmsProfitLog, int sheetOrder, List<Map<String, String>> listOne, int i, int count) {
            this.sheetlists = sheetlists;
            this.pmsProfitLog = pmsProfitLog;
            this.sheetOrder = sheetOrder;
            this.sheetName = sheetName;
            this.columnNum = columnNum;
            this.listOne = listOne;
            this.i = i;
            this.count = count;

        }


        @Override
        public Map<String, Object> call() {
            logger.info("分润导入线程" + sheetName + " 执行开始-当前线程：{" + this.i + "}");
            Map<String, Object> rMap = new HashMap<>();
            try {
                List<Map<String, Object>> saveSheetList = saveSheet(sheetlists, sheetName, columnNum, pmsProfitLog, sheetOrder, listOne, i, count);
                if (saveSheetList == null || saveSheetList.size() == 0) {
                    rMap.put("result", "success");
                } else {
                    rMap.put("result", "dispose");
                    rMap.put("disposeResult", saveSheetList);
                }
            } catch (Exception e) {
                e.getStackTrace();
                logger.error("分润导入线程" + sheetName + " 执行结束-当前线程：{" + this.i + "}" + "错误：" + e.getMessage());
                rMap.put("result", "fail");
                rMap.put("Err", e.getMessage());
            }
            logger.info("分润导入线程" + sheetName + " 执行结束-当前线程：{" + this.i + "}");
            return rMap;
        }
    }

}
