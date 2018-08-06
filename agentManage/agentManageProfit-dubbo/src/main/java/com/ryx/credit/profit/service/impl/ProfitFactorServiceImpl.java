package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.PageInfo;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @Author Lihl
 * @Date 2018/08/02
 * 分润管理：商业保理
 */
@Service("profitFactorService")
public class ProfitFactorServiceImpl implements ProfitFactorService{

    private static Logger logger = LoggerFactory.getLogger(ProfitFactorServiceImpl.class);

    @Autowired
    private PProfitFactorMapper pProfitFactorMapper;
    @Autowired
    private IdService idService;

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
        System.out.println("查询============================================" + JSONObject.toJSON(list));
        return pageInfo;
    }

    @Override
    public PProfitFactor selectByData(PProfitFactor profitFactor) {
        return pProfitFactorMapper.selectByData(profitFactor);
    }

    @Override
    public int insertImportData(PProfitFactor pProfitFactor) {
        return pProfitFactorMapper.insertSelective(pProfitFactor);
    }

    /**
     * 商业保理：
     * 1、导入保理数据
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public List<String> addList(List<List<Object>> data) throws Exception {
        List<String> list = new ArrayList<>();
        for (List<Object> objectList : data) {
            PProfitFactor profitFactor = new PProfitFactor();
            profitFactor.setFactorDate(Calendar.getInstance().getTime());       // 导入时间
            profitFactor.setId(idService.genId(TabId.p_profit_factor));      // ID序列号
            try {
                profitFactor.setFactorMonth(null != objectList.get(0) ? String.valueOf(objectList.get(0)) : "");   // 月份
                profitFactor.setAgentPid(null != objectList.get(1) ? String.valueOf(objectList.get(1)) : "");      // 代理商唯一码
                profitFactor.setAgentName(null != objectList.get(2) ? String.valueOf(objectList.get(2)) : "");     // 代理商名称
                profitFactor.setAgentId(null != objectList.get(3) ? String.valueOf(objectList.get(3)) : "");   // 代理商编号
                profitFactor.setTatolAmt(new BigDecimal(String.valueOf(objectList.get(4))));    // 应还款
                profitFactor.setBuckleAmt(new BigDecimal(String.valueOf(objectList.get(5))));   // 已扣款
                profitFactor.setSurplusAmt(new BigDecimal(String.valueOf(objectList.get(6))));  // 未扣足
                profitFactor.setRemark(null != objectList.get(7) ? String.valueOf(objectList.get(7)) : "");   // 备注
            } catch (Exception e) {
                logger.info("Excel参数错误！");
                throw new ProcessException("Excel参数错误！");
            }

            PProfitFactor profit = selectByData(profitFactor);
            System.out.println("selectByData============================================" + profit);
            if (profit != null) {
                logger.info("此条数据已存在！");
                continue;
            } else {
                if (1 != insertImportData(profitFactor)) {
                    logger.info("插入失败！");
                    throw new ProcessException("插入失败！");
                }
                System.out.println("导入保理数据============================================" + JSONObject.toJSON(profitFactor));
            }
            list.add(profitFactor.getId());
        }
        return list;
    }


}
