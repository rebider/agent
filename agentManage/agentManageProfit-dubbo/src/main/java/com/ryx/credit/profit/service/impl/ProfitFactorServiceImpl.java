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
    @Override
    public void addList(List<List<Object>> data, String userId) throws MessageException {
        if(data == null && data.size() == 0){
            throw new MessageException("导入数据为空");
        }
        for (List<Object> list: data) {
            if(list.size() == 9){
                if(list.get(0).toString() == null || "".equals(list.get(0).toString())){
                    throw new MessageException("月份不能为空");
                }
                if(list.get(1).toString() == null || "".equals(list.get(1).toString())){
                    throw new MessageException("代理商唯一码不能为空");
                }
                if(list.get(2).toString() == null || "".equals(list.get(2).toString())){
                    throw new MessageException("代理商名称不能为空");
                }
                if(list.get(5).toString() == null || "".equals(list.get(5).toString())){
                    throw new MessageException("应扣款不能为空");
                }
                if(list.get(6).toString() == null || "".equals(list.get(6).toString())){
                    throw new MessageException("已扣款不能为空");
                }
                if(list.get(7).toString() == null || "".equals(list.get(7).toString())){
                    throw new MessageException("未扣款不能为空");
                }
                if(list.get(8).toString() == null || "".equals(list.get(8).toString())){
                    throw new MessageException("备注不能为空");
                }
            }
        }
        for (List<Object> list: data) {
            PProfitFactor profitFactor = new PProfitFactor();
            profitFactor.setAgentId(list.get(1).toString());
            String str = list.get(0).toString();
            if (str.indexOf(".") != -1){
                str = str.substring(0,str.indexOf("."));
            }
            profitFactor.setFactorMonth(str);
            PProfitFactor profit = selectByData(profitFactor);
            if(profit == null){
               try{
                   profitFactor.setFactorDate(Calendar.getInstance().getTime());
                   profitFactor.setId(idService.genId(TabId.p_profit_factor));
                   profitFactor.setAgentName(list.get(2).toString());//代理商名称
                   profitFactor.setParentAgentId(null != list.get(3) ? list.get(3).toString() : "");//上级代理商编号
                   profitFactor.setParentAgentName(null != list.get(4) ? list.get(4).toString() : "");//上级代理商名称
                   profitFactor.setTatolAmt(new BigDecimal(list.get(5).toString()));//应还款
                   profitFactor.setBuckleAmt(new BigDecimal(String.valueOf(list.get(6).toString())));//已扣款
                   profitFactor.setSurplusAmt(new BigDecimal(String.valueOf(list.get(7).toString())));//未扣足
                   profitFactor.setRemark(list.get(8).toString());//备注

                   insertImportData(profitFactor);
               }catch (Exception e){
                   e.printStackTrace();
                   throw new MessageException("请检查数据格式!");
               }
            }else{
                logger.info(profitFactor.getAgentId() + "此条数据已存在！");
                throw new MessageException(profitFactor.getAgentId() + "此条数据已存在！");
            }
        }
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
