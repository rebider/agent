package com.ryx.credit.profit.service.impl;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.dao.ProfitDayMapper;
import com.ryx.credit.profit.pojo.ProfitDay;
import com.ryx.credit.profit.pojo.ProfitDayExample;
import com.ryx.credit.profit.service.IProfitDService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

   @Override
    public PageInfo profitDList(Map<String,String> map, Page page) {
        ProfitDayExample example = new ProfitDayExample();
        example.setPage(page);
        ProfitDayExample.Criteria criteria = example.createCriteria();
        if (com.ryx.credit.commons.utils.StringUtils.isNotBlank(map.get("agentName"))) {
            criteria.andAgentNameEqualTo(map.get("agentName"));
        }
        if (com.ryx.credit.commons.utils.StringUtils.isNotBlank(map.get("agentId"))) {
            criteria.andAgentIdEqualTo(map.get("agentId"));
        }
        String startDate = map.get("startDate");
        String endDate = map.get("endDate");

        if(StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)){
            criteria.andRemitDateBetween(startDate,endDate);
        }else if(StringUtils.isNotBlank(startDate)){
            criteria.andRemitDateEqualTo(startDate);
        }else if(StringUtils.isNotBlank(endDate)){
            criteria.andRemitDateEqualTo(endDate);
        }
        List<Map<String,Object>> profitD = profitDMapper.selectIncludePayComByExample(example);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(profitD);
        pageInfo.setTotal(profitDMapper.countByExample(example));
        return pageInfo;
    }
   @Override
   public PageInfo profitDayList(Map<String, Object> map, PageInfo pageInfo) {
       List<Map<String, Object>> mapList = profitDMapper.profitDayLList(map);
       Integer count = profitDMapper.profitDayListCount(map);
       pageInfo.setRows(mapList);
       pageInfo.setTotal(count);

       return pageInfo;
   }

    @Override
    public List<ProfitDay> exportProfitD(ProfitDay profitD) {
        List<ProfitDay> profitDs = profitDMapper.selectByWhere(profitD);

        return profitDs;
    }

}
