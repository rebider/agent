package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.dao.ProfitDirectMapper;
import com.ryx.credit.profit.pojo.ProfitDirect;
import com.ryx.credit.profit.pojo.ProfitDirectExample;
import com.ryx.credit.profit.service.IProfitDirectService;
import com.ryx.credit.service.dict.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author Lihl
 * @Date 2018/08/02
 * 分润管理：商业保理
 */
@Service("profitDirectService")
public class ProfitDirectServiceImpl implements IProfitDirectService {

    private static Logger logger = LoggerFactory.getLogger(ProfitDirectServiceImpl.class);

    @Autowired
    private ProfitDirectMapper directMapper;
    @Autowired
    private IdService idService;

    /**
     * 1、列表查询
     */
    @Override
    public PageInfo getProfitDirectList(Map<String, Object> param, PageInfo pageInfo) {
        Long count = directMapper.getProfitDirectCount(param);
        List<Map<String, Object>> list = directMapper.getProfitDirectList(param);
        pageInfo.setTotal(count.intValue());
        pageInfo.setRows(list);
        System.out.println("查询============================================" + JSONObject.toJSON(list));
        return pageInfo;
    }

    @Override
    public long countByExample(ProfitDirectExample example) {
        return directMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(ProfitDirectExample example) {
        return directMapper.deleteByExample(example);
    }

    @Override
    public int insert(ProfitDirect record) {
        return directMapper.insert(record);
    }

    @Override
    public int insertSelective(ProfitDirect record) {
        return directMapper.insertSelective(record);
    }

    @Override
    public List<ProfitDirect> selectByExample(ProfitDirectExample example) {
        return directMapper.selectByExample(example);
    }

    @Override
    public List<ProfitDirect> selectByMonth(ProfitDirect record) {
        return directMapper.selectByMonth(record);
    }

    @Override
    public ProfitDirect selectByPrimaryKey(String id) {
        return directMapper.selectByPrimaryKey(id);
    }



    @Override
    public ProfitDirect selectByAgentAndMon(ProfitDirect record) {
        return directMapper.selectByAgentAndMon(record);
    }


    @Override
    public int updateByPrimaryKeySelective(ProfitDirect record) {
        return directMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ProfitDirect record) {
        return directMapper.updateByPrimaryKey(record);
    }

    @Override
    public BigDecimal getSubBuckleByMonth(ProfitDirect record) {
        return directMapper.getSubBuckleByMonth(record);
    }


}
