package com.ryx.credit.profit.service.impl;

import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.dao.PTaxAdjustMapper;
import com.ryx.credit.profit.pojo.PTaxAdjust;
import com.ryx.credit.profit.pojo.PTaxAdjustExample;
import com.ryx.credit.profit.service.IPTaxAdjustService;
import com.ryx.credit.service.dict.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * PTaxAdjustServiceImpl
 * Created by IntelliJ IDEA.
 *
 * @Author Wang y
 * @Date 2017/7/31
 * @Time: 17:44
 * To change this template use File | Settings | File Templates.
 */
@Service("iPTaxAdjustService")
public class PTaxAdjustServiceImpl implements IPTaxAdjustService {
    private Logger logger = LoggerFactory.getLogger(PTaxAdjustServiceImpl.class);
    @Autowired
    private PTaxAdjustMapper adjustMapper;

    @Autowired
    private IdService idService;

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
    public PageInfo PTaxAdjustList(PTaxAdjust record, Page page) {
        PTaxAdjustExample example = adjustEqualsTo(record);
        List<PTaxAdjust> profitD = adjustMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(profitD);
        pageInfo.setTotal((int)adjustMapper.countByExample(example));
        return pageInfo;
    }

    @Override
    public ResultVO posTaxEnterIn(PTaxAdjust record) throws ProcessException {
        record.setId(idService.genId(TabId.p_profit_adjust));
        adjustMapper.insertSelective(record);
        return ResultVO.success(record);
    }

    @Override
    public ResultVO startTaxEnterActivity(String agentPid, String userId) throws ProcessException {
        if (StringUtils.isBlank(agentPid)) {
            logger.info("代理商ID为空{}:{}", agentPid, userId);
            return ResultVO.fail("代理商审批中，代理商ID为空");
        }
        if (StringUtils.isBlank(userId)) {
            logger.info("操作用户为空{}:{}", agentPid, userId);
            return ResultVO.fail("代理商审批中，操作用户为空");
        }

        return null;
    }

    private PTaxAdjustExample adjustEqualsTo(PTaxAdjust adjust) {
        PTaxAdjustExample PTaxAdjustExample = new PTaxAdjustExample();
        if(adjust == null ){
            return PTaxAdjustExample;
        }
        PTaxAdjustExample.Criteria criteria = PTaxAdjustExample.createCriteria();
        if(StringUtils.isNotBlank(adjust.getAgentName())){
            criteria.andAgentNameEqualTo(adjust.getAgentName());
        }
        if(StringUtils.isNotBlank(adjust.getAgentId())){
            criteria.andAgentIdEqualTo(adjust.getAgentId());
        }
        return PTaxAdjustExample;
    }

    @Override
    public long countByExample(PTaxAdjustExample example) {
        return adjustMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(PTaxAdjustExample example) {
        return adjustMapper.deleteByExample(example);
    }

    @Override
    public int insert(PTaxAdjust record) {
        return adjustMapper.insert(record);
    }

    @Override
    public int insertSelective(PTaxAdjust record) {
        return adjustMapper.insertSelective(record);
    }

    @Override
    public List<PTaxAdjust> selectByExample(PTaxAdjustExample example) {
        return adjustMapper.selectByExample(example);
    }

    @Override
    public PTaxAdjust selectByPrimaryKey(String id) {
        return adjustMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(PTaxAdjust record) {
        return adjustMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(PTaxAdjust record) {
        return adjustMapper.updateByPrimaryKey(record);
    }


}
