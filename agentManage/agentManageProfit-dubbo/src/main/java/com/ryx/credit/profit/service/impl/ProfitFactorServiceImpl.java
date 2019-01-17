package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.BeanUtils;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.dao.PProfitFactorMapper;
import com.ryx.credit.profit.pojo.PProfitFactor;
import com.ryx.credit.profit.service.ProfitFactorService;
import com.ryx.credit.service.dict.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * @Author Lihl
 * @Date 2018/08/02
 * 分润管理：商业保理
 */
@Service("profitFactorService")
public class ProfitFactorServiceImpl implements ProfitFactorService {

    private static Logger logger = LoggerFactory.getLogger(ProfitFactorServiceImpl.class);

    @Autowired
    private PProfitFactorMapper pProfitFactorMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private RedisService redisService;

    /**
     * 商业保理:
     * 1、列表查询
     */
    @Override
    public PageInfo getProfitFactorList(Map<String, Object> param, PageInfo pageInfo) {
        Long count = pProfitFactorMapper.getProfitFactorCount(param);
        List<Map<String, Object>> list = pProfitFactorMapper.getProfitFactorList(param);
        pageInfo.setTotal(count.intValue());
        pageInfo.setRows(list);
//        System.out.println("查询============================================" + JSONObject.toJSON(list));
        return pageInfo;
    }

    @Override
    public PProfitFactor selectByData(PProfitFactor profitFactor) {
        return pProfitFactorMapper.selectByData(profitFactor);
    }

    /**
     * 清除本月导入数据
     */
    @Override
    public int resetDataFactor() {
        //终审后数据不能清除
        String finalStatus = redisService.getValue("commitFinal");
        if (StringUtils.isBlank(finalStatus)) {
            if ("1".equals(finalStatus)) {
                logger.info("终审状态不能清除！");
                throw new ProcessException("终审状态不能清除！");
            }
        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        Date date = calendar.getTime();
        String dateStr = new SimpleDateFormat("yyyyMM").format(date);
        return pProfitFactorMapper.resetDataFactor(dateStr);
    }

    @Override
    public Map<String, Object> profitCount(Map<String, Object> param) {
        return pProfitFactorMapper.profitCount(param);
    }

    @Override
    public int insertImportData(PProfitFactor pProfitFactor) {
        return pProfitFactorMapper.insertSelective(pProfitFactor);
    }

    /**
     * 商业保理：
     * 1、导入保理数据
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public List<String> addList(List<List<String>> data, String userId) throws Exception {
        List<String> list = new ArrayList<>();
        if (null == data && data.size() == 0) {
            logger.info("导入数据为空");
            throw new MessageException("导入数据为空");
        }
        for (List<String> factor : data) {
            PProfitFactor profitFactor = new PProfitFactor();
            profitFactor.setFactorDate(Calendar.getInstance().getTime());//导入时间
            profitFactor.setId(idService.genId(TabId.p_profit_factor));//ID序列号
            try {
                profitFactor.setFactorMonth(null != factor.get(0) ? String.valueOf(factor.get(0)).substring(0, 6) : "");//月份
                profitFactor.setAgentPid(null != factor.get(1) ? String.valueOf(factor.get(1)) : "");//代理商唯一码(因业务中无pid，现将pid取值更改为AG码)
                profitFactor.setAgentName(null != factor.get(2) ? String.valueOf(factor.get(2)) : "");//代理商名称
                profitFactor.setAgentId(null != factor.get(1) ? String.valueOf(factor.get(1)) : "");//代理商编号
                profitFactor.setParentAgentId(null != factor.get(3) ? String.valueOf(factor.get(3)) : "");//上级代理商编号
                profitFactor.setParentAgentName(null != factor.get(4) ? String.valueOf(factor.get(4)) : "");//上级代理商名称
                profitFactor.setTatolAmt(new BigDecimal(String.valueOf(factor.get(5))));//应还款
                profitFactor.setBuckleAmt(new BigDecimal(String.valueOf(factor.get(6))));//已扣款
                profitFactor.setSurplusAmt(new BigDecimal(String.valueOf(factor.get(7))));//未扣足
                profitFactor.setRemark(null != factor.get(8) ? String.valueOf(factor.get(8)) : "");//备注
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
            PProfitFactor profit = selectByData(profitFactor);//查询列表中是否有重复数据
            if (profit != null) {
                logger.info(profitFactor.getAgentId() + "此条数据已存在！");
                throw new MessageException(profitFactor.getAgentId() + "此条数据已存在！");
            } else {
                if (insertImportData(profitFactor) == 0) {
                    logger.info("导入失败！");
                    throw new MessageException(factor.toString() + "导入失败！");
                }
                logger.info("保理数据信息：" + JSONObject.toJSON(profitFactor));
            }
            list.add(profitFactor.getId());
        }
        return list;
    }


    /**
     * @Author: Zhang Lei
     * @Description: 保理扣款
     * @Date: 16:58 2019/1/14
     */
    @Override
    public BigDecimal blDeduction(Map<String, Object> param) {
        //查询所有保理未扣款记录
        List<PProfitFactor> deductionList = pProfitFactorMapper.selectBlDeductListByParams(param);

        if (deductionList != null && deductionList.size() > 0) {
            return getDeductionAmt(deductionList, param);
        }
        return BigDecimal.ZERO;
    }

    private BigDecimal getDeductionAmt(List<PProfitFactor> deductionList, Map<String, Object> param) {
        BigDecimal result = BigDecimal.ZERO;
        if (deductionList != null && deductionList.size() > 0) {
            for (PProfitFactor profitDeductionTemp : deductionList) {
                result = deduction(result, profitDeductionTemp, param);
            }
        }
        return result;
    }


    /***
     * @Description: 扣款业务实现
     * @Param: result 扣款总金额
     * @Param: profitAmt 分润金额
     * @Param: profitDeductionTemp 扣款信息
     * @return: 返回总扣款金额
     * @Author: zhaodw
     * @Date: 2018/8/9
     */
    private BigDecimal deduction(BigDecimal result, PProfitFactor profitFactorTemp, Map<String, Object> param) {
        BigDecimal profitAmt = (BigDecimal) param.get("profitAmt");
        String computeType = (String) param.get("computeType");
        BigDecimal currentProfit = null;
        BigDecimal realDeductionAmt = BigDecimal.ZERO;
        if (profitAmt.doubleValue() > 0) {
            currentProfit = profitAmt;
            profitAmt = profitAmt.subtract(profitFactorTemp.getBuckleAmt());
            if (profitAmt.doubleValue() > 0) {
                realDeductionAmt = profitFactorTemp.getBuckleAmt();
            } else {
                realDeductionAmt = currentProfit;
            }
            result = result.add(realDeductionAmt);
        } else if (profitAmt.doubleValue() <= 0) {
            currentProfit = BigDecimal.ZERO;
            profitAmt = BigDecimal.ZERO;
        }
        if ("1".equals(computeType)) {
            generalDeal(currentProfit, profitAmt, profitFactorTemp);
        }
        param.put("profitAmt", profitAmt);
        return result;
    }

    /***
     * @Description: 一般扣款处理
     * @Param: resultAmt 扣减后金额
     * @Author: zhaodw
     * @Date: 2018/8/8
     */
    private void generalDeal(BigDecimal profitAmt, BigDecimal resultAmt, PProfitFactor profitFactorTemp) {

        //有分润并且扣足
        if (resultAmt.doubleValue() >= 0 && profitAmt.doubleValue() > 0) {
            profitFactorTemp.setActualDeductionAmt(profitFactorTemp.getMustDeductionAmt());
            profitFactorTemp.setNotDeductionAmt(BigDecimal.ZERO);
            profitFactorTemp.setDeductionStatus("1");//已扣款
        } else {
            //有分润，但是不够扣
            if (profitAmt.doubleValue() > 0 && resultAmt.doubleValue() < 0) {
                //扣减后分润金额小于0，实际扣款为剩余分润金额
                profitFactorTemp.setActualDeductionAmt(profitAmt);
                profitFactorTemp.setNotDeductionAmt(resultAmt.abs());
            } else {//无分润
                profitFactorTemp.setActualDeductionAmt(BigDecimal.ZERO);
                profitFactorTemp.setNotDeductionAmt(profitFactorTemp.getMustDeductionAmt());
            }
            // 将当期扣款对象存入历史
            createHisDeduction(profitFactorTemp);

            // 更新当期为下期扣款
            profitFactorTemp.setFactorMonth(LocalDate.now().toString().substring(0, 7).replaceAll("-", ""));
            profitFactorTemp.setUpperNotDeductionAmt(profitFactorTemp.getNotDeductionAmt());//上月未扣足=未扣足
            profitFactorTemp.setBuckleAmt(profitFactorTemp.getNotDeductionAmt());
            profitFactorTemp.setMustDeductionAmt(profitFactorTemp.getNotDeductionAmt());
            profitFactorTemp.setSumDeductionAmt(profitFactorTemp.getNotDeductionAmt());
            profitFactorTemp.setNotDeductionAmt(null); // 未扣足请0
            profitFactorTemp.setActualDeductionAmt(null);// 实扣清0
            profitFactorTemp.setAddDeductionAmt(null);
            profitFactorTemp.setFactorDate(new Date());
            profitFactorTemp.setDeductionDesc("上期未扣足移到本月");
            profitFactorTemp.setStagingStatus("6");//变为下期扣款标记
            profitFactorTemp.setDeductionStatus("0");//未扣款
        }
        pProfitFactorMapper.updateByPrimaryKeySelective(profitFactorTemp);
    }


    /***
     * @Description: 创建一条扣款历史数据
     * @Param: profitDeductionTemp 当前对象
     * @Author: zhaodw
     * @Date: 2018/8/8
     */
    private void createHisDeduction(PProfitFactor profitFactorTemp) {
        // 创建一条历史扣款记录
        PProfitFactor newFactor = new PProfitFactor();
        BeanUtils.copy(profitFactorTemp, newFactor);
        newFactor.setId(StringUtils.getUUId());
        newFactor.setStagingStatus("5");
        newFactor.setNextId(profitFactorTemp.getId());
        newFactor.setDeductionDesc("本期存在未扣足已累计下月");
        newFactor.setDeductionStatus("1");//已扣款
        pProfitFactorMapper.insert(newFactor);
    }

}
