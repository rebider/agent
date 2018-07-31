package com.ryx.credit.profit.service.impl;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.dao.PosCheckMapper;
import com.ryx.credit.profit.dao.PosCheckMapper;
import com.ryx.credit.profit.pojo.PosCheck;
import com.ryx.credit.profit.pojo.PosCheck;
import com.ryx.credit.profit.pojo.PosCheckExample;
import com.ryx.credit.profit.service.IPosCheckService;
import com.ryx.credit.profit.service.IPosCheckService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * PosCheckServiceImpl
 * Created by IntelliJ IDEA.
 *
 * @Author Wang y
 * @Date 2017/7/31
 * @Time: 17:44
 * To change this template use File | Settings | File Templates.
 */
@Service("iPosCheckService")
public class PosCheckServiceImpl implements IPosCheckService {
    protected Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private PosCheckMapper checkMapper;



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
    public PageInfo PosCheckList(PosCheck record, Page page) {
        PosCheckExample example = checkEqualsTo(record);
        List<PosCheck> profitD = checkMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(profitD);
        pageInfo.setTotal((int)checkMapper.countByExample(example));
        return pageInfo;
    }

    private PosCheckExample checkEqualsTo(PosCheck check) {
        PosCheckExample posCheckExample = new PosCheckExample();
        if(check == null ){
            return posCheckExample;
        }
        PosCheckExample.Criteria criteria = posCheckExample.createCriteria();
        if(StringUtils.isNotBlank(check.getAgentName())){
            criteria.andAgentNameEqualTo(check.getAgentName());
        }
        if(StringUtils.isNotBlank(check.getAgentId())){
            criteria.andAgentIdEqualTo(check.getAgentId());
        }
        return posCheckExample;
    }

    @Override
    public long countByExample(PosCheckExample example) {
        return checkMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(PosCheckExample example) {
        return checkMapper.deleteByExample(example);
    }

    @Override
    public int insert(PosCheck record) {
        return checkMapper.insert(record);
    }

    @Override
    public int insertSelective(PosCheck record) {
        return checkMapper.insertSelective(record);
    }

    @Override
    public List<PosCheck> selectByExample(PosCheckExample example) {
        return checkMapper.selectByExample(example);
    }

    @Override
    public PosCheck selectByPrimaryKey(String id) {
        return checkMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(PosCheck record) {
        return checkMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(PosCheck record) {
        return checkMapper.updateByPrimaryKey(record);
    }
}
