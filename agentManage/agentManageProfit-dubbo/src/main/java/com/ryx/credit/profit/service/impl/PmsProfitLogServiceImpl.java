package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.util.DateUtils;
import com.ryx.credit.common.util.FutureTaskUtils;
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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;


@Service("pmsProfitLogService")
public class PmsProfitLogServiceImpl implements IPmsProfitLogService{
    private final Logger logger = Logger.getLogger(PmsProfitLogServiceImpl.class);
    @Autowired
    PmsProfitLogMapper pmsProfitLogMapper;
    @Autowired
    PmsProfitTempMapper pmsProfitTempMapper;
    @Autowired
    PmsProfitMapper pmsProfitMapper;

    @Override
    public PageInfo selectByMap(Map<String,String> param,Page page){
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(pmsProfitLogMapper.selectByMap(param,page));
        pageInfo.setTotal((int) pmsProfitLogMapper.getCountByMap(param));
        return pageInfo;
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

    }

    @Transactional
    public void saveSheet(List<Map<String, String>> list, String sheetName, int columnNum, String month, String userId, int sheetOrder,List<Map<String, String>> listOne,int theadi,int count) {
        if(listOne ==null){
            listOne = new ArrayList<>();
            listOne.add(list.get(0));
            list.removeAll(listOne);
        }

        for (int i = 0; i < list.size(); i++) {

            PmsProfitTempWithBLOBs pmsProfitTempWithBLOBs = new PmsProfitTempWithBLOBs();
            pmsProfitTempWithBLOBs.setMonth(month);
            pmsProfitTempWithBLOBs.setUniqueFlag((list.get(i).get("Cell0")));
            pmsProfitTempWithBLOBs.setAgentId((list.get(i).get("Cell1")));
            pmsProfitTempWithBLOBs.setBusCode(list.get(i).get("Cell3").trim());
            pmsProfitTempWithBLOBs.setBusName(list.get(i).get("Cell4").trim());
            pmsProfitTempWithBLOBs.setSheetHead(callMapToXML(listOne.get(0)));
            pmsProfitTempWithBLOBs.setSheetData(callMapToXML(list.get(i)));
            pmsProfitTempWithBLOBs.setSheetName(sheetName);
            pmsProfitTempWithBLOBs.setImportPerson(userId);
            pmsProfitTempWithBLOBs.setOrderNumber(new BigDecimal((list.get(i).get("rowNum"))));


            PmsProfit pf = new PmsProfit();
            pf.setMonth(month);
            pf.setSheetOrder(new BigDecimal(sheetOrder));
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
            pf.setOrderNumber(new BigDecimal((list.get(i).get("rowNum"))));
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            pf.setId(uuid);
            if (logger.isDebugEnabled()) {
                logger.debug(pmsProfitTempWithBLOBs.getSheetData());
            }
            String agentId = list.get(i).get("Cell0").trim();
            pf.setUniqueFlag(agentId);

            String busCode = list.get(i).get("Cell3").trim();
            List<Map<String, Object>> mapData;


            try {
                mapData = checkoutData(agentId, busCode);
            } catch (RuntimeException e) {
                throw new RuntimeException("数据查询失败");
            }
            if (mapData.size() < 1) {
                throw new RuntimeException(sheetName + "sheet页第" + ((i+2)+(theadi*count)) + "行的平台下" + busCode + "不存在此行唯一码" + agentId);
            }
         /*   if (!list.get(i).get("Cell1").trim().equals(mapData.get(0).get("AG_NAME").toString().trim())) {
                logger.info("mapData=======================================" + mapData + "+++++++++++" + mapData.get(0).get("AG_NAME").toString().trim());
                logger.info("mapData=======================================" + list.get(i).get("Cell1").trim());
                throw new RuntimeException(sheetName + "sheet页第" + i + "行唯一标识与代理商名称不匹配");
            }*/
            if (!month.equals((list.get(i).get("Cell2")))) {
                throw new RuntimeException(sheetName + "sheet页第" + ((i+2)+(theadi*count)) + "行月份与选择不匹配");
            }
/*
            if (mapData.get(0).get("PLATFORM_NAME").toString().trim().indexOf(list.get(i).get("Cell4").trim()) == -1) {
                logger.info("mapData=======================================" + mapData + "+++++++++++" + mapData.get(0).get("PLATFORM_NAME").toString().trim());
                logger.info("mapData=======================================" + list.get(i).get("Cell4").trim());
                throw new RuntimeException(sheetName + "sheet页第" + i + "行平台码与平台名称不匹配");
            }*/


            pf.setAgentId((list.get(i).get("Cell1")));
            pf.setBusCode(list.get(i).get("Cell3").trim());
            pf.setBusName(list.get(i).get("Cell4").trim());


            list.get(i).remove("rowNum");
            try {
                insertSelective(pmsProfitTempWithBLOBs);
                save(pf);
            } catch (Exception e) {
                e.printStackTrace();
                e.getMessage();
                logger.error("保存数据失败，sheet:" + pf.getSheetName() + "第" + (pf.getOrderNumber().add(new BigDecimal(1))) + "行:" + JSONObject.toJSONString(pf));
                throw new RuntimeException(e.toString() + pf.getSheetName() + "第" + pf.getOrderNumber() + "行");
            }

        }


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

    @Override
    @Transactional
    public void disposeSheet(List<Map<String, String>> sheetlists, String sheetName, int columnNum, String month, String userId, int sheetOrder) {
        if (sheetlists.size() > 0 && sheetlists.size() < 11) {
            saveSheet(sheetlists, sheetName, columnNum, month, userId, sheetOrder,null,0,0);

        } else {

            List<String> taskNameList = new ArrayList<String>();
            List<FutureTask<Map<String,String>>> taskList = new ArrayList();

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
                    SheetThead sheetThead = new SheetThead(list, sheetName, columnNum, month, userId, sheetOrder,listOne,z,count);
                    FutureTask<Map<String,String>> thread = new FutureTask<>(sheetThead);
                    taskNameList.add("{ SheetThead================================================================-" + sheetName+z + "}");
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

            Map<String,String> resultMap  = FutureTaskUtils.getTaskResult(taskList, taskNameList, logger);
            // 获取线程执行结果
            if ("fail".equals(resultMap.get("result"))) {
                throw new RuntimeException(resultMap.get("Err"));
            }
        }
    }



    class SheetThead  implements Callable<Map<String,String>> {

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

        SheetThead(List<Map<String, String>> sheetlists, String sheetName, int columnNum, String month, String userId, int sheetOrder,List<Map<String, String>> listOne,int i,int count) {
            this.sheetlists = sheetlists;
            this.month = month;
            this.userId = userId;
            this.sheetOrder = sheetOrder;
            this.sheetName = sheetName;
            this.columnNum = columnNum;
           this.listOne =listOne;
            this.i =i;
            this.count = count;
        }



        @Override
        public Map<String,String> call(){
            logger.info("分润导入线程 执行开始-当前线程：{" + this.i + "}");
            Map<String,String> rMap = new HashMap<>();
            try {
                saveSheet(sheetlists, sheetName, columnNum, month, userId, sheetOrder,listOne,i,count);
                rMap.put("result","success");
            }catch (Exception e){
                rMap.put("result","fail");
                rMap.put("Err",e.getMessage());
            }
            logger.info("分润导入线程 执行结束-当前线程：{" + this.i + "}");
            return rMap;
        }
    }

}
