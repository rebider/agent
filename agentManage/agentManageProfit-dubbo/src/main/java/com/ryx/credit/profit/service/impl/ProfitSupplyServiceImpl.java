package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.dao.ProfitSupplyMapper;
import com.ryx.credit.profit.pojo.ProfitSupply;
import com.ryx.credit.profit.pojo.ProfitSupplyExample;
import com.ryx.credit.profit.service.ProfitSupplyService;
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
@Service("profitSupplyService")
public class ProfitSupplyServiceImpl implements ProfitSupplyService {

    private static Logger logger = LoggerFactory.getLogger(ProfitSupplyServiceImpl.class);

    @Autowired
    private ProfitSupplyMapper pProfitSupplyMapper;
    @Autowired
    private IdService idService;

    /**
     * 1、列表查询
     */
    @Override
    public PageInfo getProfitSupplyList(Map<String, Object> param, PageInfo pageInfo) {
        Long count = pProfitSupplyMapper.getProfitSupplyCount(param);
        List<Map<String, Object>> list = pProfitSupplyMapper.getProfitSupplyList(param);
        pageInfo.setTotal(count.intValue());
        pageInfo.setRows(list);
        System.out.println("查询============================================" + JSONObject.toJSON(list));
        return pageInfo;
    }

    @Override
    public int countByExample(ProfitSupplyExample example) {
        return pProfitSupplyMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(ProfitSupplyExample example) {
        return pProfitSupplyMapper.deleteByExample(example);
    }

    @Override
    public int insert(ProfitSupply record) {
        return pProfitSupplyMapper.insert(record);
    }

    @Override
    public int insertSelective(ProfitSupply record) {
        return pProfitSupplyMapper.insertSelective(record);
    }

    @Override
    public List<ProfitSupply> selectByExample(ProfitSupplyExample example) {
        return pProfitSupplyMapper.selectByExample(example);
    }

    @Override
    public ProfitSupply selectByPrimaryKey(String id) {
        return pProfitSupplyMapper.selectByPrimaryKey(id);
    }

    @Override
    public ProfitSupply selectByAgentMonth(ProfitSupply record) {
        return pProfitSupplyMapper.selectByAgentMonth(record);
    }

    @Override
    public BigDecimal getTotalByMonthAndPid(ProfitSupply record) {
        return pProfitSupplyMapper.getTotalByMonthAndPid(record);
    }


    @Override
    public int updateByPrimaryKeySelective(ProfitSupply record) {
        return pProfitSupplyMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ProfitSupply record) {
        return pProfitSupplyMapper.updateByPrimaryKey(record);
    }

    /**
     * 商业保理：
     * 1、导入保理数据
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public List<String> importSupplyList(List<List<Object>> data) throws Exception {
        List<String> list = new ArrayList<>();
        for (List<Object> objectList : data) {
            ProfitSupply profitSupply = new ProfitSupply();
            profitSupply.setSourceId(DateUtil.getDays());       // 导入时间
            profitSupply.setId(idService.genId(TabId.p_profit_supply));      // ID序列号
            try {
                profitSupply.setAgentPid(null != objectList.get(0) ? String.valueOf(objectList.get(0)) : "");      // 代理商唯一码
                profitSupply.setAgentName(null != objectList.get(1) ? String.valueOf(objectList.get(1)) : "");     // 代理商名称
                profitSupply.setSupplyDate(null != objectList.get(2) ? String.valueOf(objectList.get(2)) : "");   // 月份
                //profitSupply.setAgentId(null != objectList.get(3) ? String.valueOf(objectList.get(3)) : "");   // 代理商编号
                profitSupply.setSupplyType(null != objectList.get(3) ? String.valueOf(objectList.get(3)) : "");    // 补款类型
                profitSupply.setSupplyAmt(new BigDecimal(String.valueOf(objectList.get(4))));   // 补款金额
                profitSupply.setSupplyCode(null != objectList.get(5) ? String.valueOf(objectList.get(5)) : "");   // 补款码

                //profitSupply.setRemark(null != objectList.get(7) ? String.valueOf(objectList.get(5)) : "");   // 备注
            } catch (Exception e) {
                logger.info("Excel参数错误！");
                throw new ProcessException("Excel参数错误！");
            }

            /*ProfitSupply supply = selectByAgentMonth(profitSupply);
            if (supply != null) {
                logger.info("此条数据已存在！");
                continue;
            } else {

            }*/
            if (1 != insertSelective(profitSupply)) {
                logger.info("插入失败！");
                throw new ProcessException("插入失败！");
            }
            list.add(profitSupply.getId());
        }
        return list;
    }

}
