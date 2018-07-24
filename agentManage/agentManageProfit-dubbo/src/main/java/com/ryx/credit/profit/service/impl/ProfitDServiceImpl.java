package com.ryx.credit.profit.service.impl;

import com.ryx.credit.common.util.ConvertUtils;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.commons.utils.BeanUtils;
import com.ryx.credit.pojo.admin.*;
import com.ryx.credit.profit.dao.ProfitDayMapper;
import com.ryx.credit.profit.pojo.ProfitDay;
import com.ryx.credit.profit.pojo.ProfitDayExample;
import com.ryx.credit.profit.service.IProfitDService;
import com.ryx.credit.service.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ProfitDServiceImpl
 * Created by IntelliJ IDEA.
 *
 * @Author Wang y
 * @Date 2017/7/31
 * @Time: 17:44
 * To change this template use File | Settings | File Templates.
 */
@Service("iProfitDService")
public class ProfitDServiceImpl implements IProfitDService {
    protected Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private ProfitDayMapper profitDMapper;



    /**
     * 处理分页用到的信息
     *
     * @return
     */
    protected Page pageProcessAll(int size) {
        int numPerPage = size;
        int currentPage = 1;
        Page page = new Page();
        page.setCurrent(currentPage);
        page.setLength(numPerPage);
        page.setBegin((currentPage - 1) * numPerPage);
        page.setEnd(currentPage * numPerPage);
        return page;
    }

    @Override
    public int countByExample(ProfitDayExample example) {
        return profitDMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(ProfitDayExample example) {
        return profitDMapper.deleteByExample(example);
    }

    @Override
    public int insert(ProfitDay record) {
        return profitDMapper.insert(record);
    }

    @Override
    public int insertSelective(ProfitDay record) {
        return profitDMapper.insertSelective(record);
    }

    @Override
    public List<ProfitDay> selectByExample(ProfitDayExample example) {
        return profitDMapper.selectByExample(example);
    }

    @Override
    public ProfitDay selectByPrimaryKey(String id) {
        return profitDMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ProfitDay record) {
        return profitDMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ProfitDay record) {
        return profitDMapper.updateByPrimaryKey(record);
    }
}
