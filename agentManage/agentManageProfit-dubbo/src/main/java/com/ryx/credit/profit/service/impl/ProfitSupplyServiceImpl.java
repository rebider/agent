package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.DateUtils;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author Lihl
 * @Date 2018/08/02
 * 分润管理：补款数据维护
 */
@Service("profitSupplyService")
public class ProfitSupplyServiceImpl implements ProfitSupplyService {

    private static Logger logger = LoggerFactory.getLogger(ProfitSupplyServiceImpl.class);

    @Autowired
    private ProfitSupplyMapper pProfitSupplyMapper;
    @Autowired
    private RedisService redisService;
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
     * 清除本月导入
     * @return
     */
    @Override
    public int resetData() {
        // 终审后不能清除
        String finalStatus = redisService.getValue("commitFinal");
        if (StringUtils.isBlank(finalStatus)) {
            if("1".equals(finalStatus)){
                logger.info("终审状态不能清除！");
                throw new ProcessException("终审状态不能清除！");
            }
        }
        return pProfitSupplyMapper.resetData();
    }

    /**
     * 补款数据维护:
     * 1、导入补款数据
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public List<String> importSupplyList(List<List<Object>> data) throws Exception {
        List<String> list = new ArrayList<>();
        for (List<Object> supply : data) {
            ProfitSupply profitSupply = new ProfitSupply();
            profitSupply.setId(idService.genId(TabId.p_profit_supply));// ID序列号
            profitSupply.setSourceId(DateUtils.dateToStrings(new Date()));// 录入日期
            try {
                profitSupply.setAgentId(null!=supply.get(0)?String.valueOf(supply.get(0)):"");//代理商编码
                profitSupply.setAgentName(null!=supply.get(1)?String.valueOf(supply.get(1)):"");//代理商名称
                profitSupply.setParentAgentId(null!=supply.get(2)?String.valueOf(supply.get(2)):"");//上级代理商编号
                profitSupply.setParentAgentName(null!=supply.get(3)?String.valueOf(supply.get(3)):"");//上级代理商名称
                profitSupply.setSupplyDate(null!=supply.get(4)?String.valueOf(supply.get(4)):"");//月份
                profitSupply.setSupplyType(null!=supply.get(5)?String.valueOf(supply.get(5)):"");//补款类型
                profitSupply.setSupplyAmt(new BigDecimal(String.valueOf(supply.get(6))));//补款金额
                profitSupply.setSupplyCode(null!=supply.get(7)?String.valueOf(supply.get(7)):"");//补款码
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }

            logger.info("补款数据信息-------------------------------------" , JSONObject.toJSON(supply));
            if (1 != insertSelective(profitSupply)) {
                logger.info("插入失败！");
                throw new MessageException("代理商编号为:"+profitSupply.getAgentId()+"插入补款数据失败");
            }
            list.add(profitSupply.getId());
        }
        return list;
    }

}
