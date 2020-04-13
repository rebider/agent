package com.ryx.credit.profit.service.impl;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.profit.dao.ImportDataWithProfitLogMapper;
import com.ryx.credit.profit.dao.ImportDataWithProfitMapper;
import com.ryx.credit.profit.pojo.ImportDataWithProfit;
import com.ryx.credit.profit.pojo.ImportDataWithProfitExample;
import com.ryx.credit.profit.pojo.ImportDataWithProfitLog;
import com.ryx.credit.profit.pojo.ImportDataWithProfitLogExample;
import com.ryx.credit.profit.service.IImportDataWithProfitService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class ImportDataWithProfitServiceImpl implements IImportDataWithProfitService {

    Logger logger = LoggerFactory.getLogger(ImportDataWithProfitServiceImpl.class);


    @Autowired
    private ImportDataWithProfitLogMapper importDataWithProfitLogMapper;
    @Autowired
    private ImportDataWithProfitMapper importDataWithProfitMapper;

    @Override
    public int insertImportLog(ImportDataWithProfitLog importLog) {
        return importDataWithProfitLogMapper.insert(importLog);
    }

    @Override
    public List<ImportDataWithProfitLog> getImportLogByBatchCode(String batchCode) {
        ImportDataWithProfitLogExample example = new ImportDataWithProfitLogExample();
        ImportDataWithProfitLogExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(batchCode);
        return importDataWithProfitLogMapper.selectByExample(example);
    }

    @Override
    public int insertProfitData(List<Map<String, Object>> profitDatas) {
        return importDataWithProfitMapper.insertProfitData(profitDatas);
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
        List<ImportDataWithProfitLog> logs = getImportLogByBatchCode(batchCode);
        if (logs.size()<1){
            throw new MessageException("");
        }
        ImportDataWithProfitLog log = logs.get(0);

        String path = log.getUploadPath();
        File file = new File(path);
        
        //2.读取文件
        String extensionName = path.substring(path.lastIndexOf("."));//扩展名

        if ("xls".equals(extensionName)){

        }else if ("xlsx".equals(extensionName)){

        }else{
            //文件类型错误
        }


        return null;
    }
}
