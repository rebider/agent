package com.ryx.credit.profit.service.impl;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.dao.PosRewardMapper;
import com.ryx.credit.profit.pojo.*;
import com.ryx.credit.profit.pojo.PosReward;
import com.ryx.credit.profit.pojo.PosRewardExample;
import com.ryx.credit.profit.service.IPosRewardService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ProfitDServiceImpl
 * Created by IntelliJ IDEA.
 *
 * @Author Wang y
 * @Date 2017/7/31
 * @Time: 17:44
 * To change this template use File | Settings | File Templates.
 */
@Service("iPosRewardService")
public class PosRewardServiceImpl implements IPosRewardService {
    protected Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private PosRewardMapper rewardMapper;



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
    public PageInfo posRewardList(PosReward record, Page page) {
        PosRewardExample example = rewardEqualsTo(record);
        List<PosReward> profitD = rewardMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(profitD);
        pageInfo.setTotal((int)rewardMapper.countByExample(example));
        return pageInfo;
    }

    private PosRewardExample rewardEqualsTo(PosReward reward) {
        PosRewardExample posRewardExample = new PosRewardExample();
        if(reward == null ){
            return posRewardExample;
        }
        PosRewardExample.Criteria criteria = posRewardExample.createCriteria();
        if(StringUtils.isNotBlank(reward.getAgentName())){
            criteria.andAgentNameEqualTo(reward.getAgentName());
        }
        if(StringUtils.isNotBlank(reward.getAgentId())){
            criteria.andAgentIdEqualTo(reward.getAgentId());
        }
        return posRewardExample;
    }

    @Override
    public long countByExample(PosRewardExample example) {
        return rewardMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(PosRewardExample example) {
        return rewardMapper.deleteByExample(example);
    }

    @Override
    public int insert(PosReward record) {
        return rewardMapper.insert(record);
    }

    @Override
    public int insertSelective(PosReward record) {
        return rewardMapper.insertSelective(record);
    }

    @Override
    public List<PosReward> selectByExample(PosRewardExample example) {
        return rewardMapper.selectByExample(example);
    }

    @Override
    public PosReward selectByPrimaryKey(String id) {
        return rewardMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(PosReward record) {
        return rewardMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(PosReward record) {
        return rewardMapper.updateByPrimaryKey(record);
    }
}
