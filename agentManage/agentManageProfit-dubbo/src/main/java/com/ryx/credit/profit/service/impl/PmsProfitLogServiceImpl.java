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
    public List<Map<String, Object>> checkoutData(String agentId, String busCode) {
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
      /*  if (StringUtils.isNotBlank(example.getUploadUser())) {
            criteria.andUploadUserEqualTo(example.getUploadUser());
        }*/
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


}
