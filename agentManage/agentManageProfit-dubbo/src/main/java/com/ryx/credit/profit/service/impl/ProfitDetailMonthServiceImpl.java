package com.ryx.credit.profit.service.impl;/**
 * @Auther: zhaodw
 * @Date: 2018/8/6 09:08
 * @Description:
 */

import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.dao.ProfitDetailMonthMapper;
import com.ryx.credit.profit.dao.TransProfitDetailMapper;
import com.ryx.credit.profit.pojo.ProfitDetailMonth;
import com.ryx.credit.profit.pojo.ProfitDetailMonthExample;
import com.ryx.credit.profit.pojo.TransProfitDetail;
import com.ryx.credit.profit.pojo.TransProfitDetailExample;
import com.ryx.credit.profit.service.ProfitDetailMonthService;
import com.ryx.credit.service.dict.IdService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 月度分润明细业务接口实现
 * @author zhaodw
 * @create 2018/8/6
 * @since 1.0.0
 */
@Service("profitDetailMonthServiceImpl")
public class ProfitDetailMonthServiceImpl implements ProfitDetailMonthService {

    private static final Logger LOG = Logger.getLogger(ProfitDetailMonthServiceImpl.class);
    @Autowired
    private ProfitDetailMonthMapper profitDetailMonthMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private TransProfitDetailMapper transProfitDetailMapper;

    @Override
    public void insert(ProfitDetailMonth profitDetailMonth) {
        if(profitDetailMonth.getId() == null) {
            profitDetailMonth.setId(idService.genId(TabId.P_PROFIT_DETAIL_M));
        }
        profitDetailMonthMapper.insert(profitDetailMonth);
    }

    @Override
    public void update(ProfitDetailMonth profitDetailMonth) {
        if(profitDetailMonth.getId() == null) {
            LOG.error("没有主键。");
            return;
        }
        profitDetailMonthMapper.updateByPrimaryKeySelective(profitDetailMonth);
    }

    @Override
    public int countByExample(ProfitDetailMonthExample example) {
        return profitDetailMonthMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(ProfitDetailMonthExample example) {
        return profitDetailMonthMapper.deleteByExample(example);
    }

    @Override
    public int insertSelective(ProfitDetailMonth record) {
        return profitDetailMonthMapper.insertSelective(record);
    }

    @Override
    public List<ProfitDetailMonth> selectByExample(ProfitDetailMonthExample example) {
        return profitDetailMonthMapper.selectByExample(example);
    }

    @Override
    public ProfitDetailMonth selectByPrimaryKey(String id) {
        return profitDetailMonthMapper.selectByPrimaryKey(id);
    }

    @Override
    public ProfitDetailMonth selectByPIdAndMonth(ProfitDetailMonth record) {
        return profitDetailMonthMapper.selectByPIdAndMonth(record);
    }

    @Override
    public int updateByPrimaryKeySelective(ProfitDetailMonth record) {
        return profitDetailMonthMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ProfitDetailMonth record) {
        return profitDetailMonthMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<TransProfitDetail> getTransProfitDetailList(String agentId, String agentPid, String profitDate) {
        TransProfitDetailExample transProfitDetailExample = new TransProfitDetailExample();
        TransProfitDetailExample.Criteria criteria = transProfitDetailExample.createCriteria();
        criteria.andProfitDateEqualTo(profitDate);
        criteria.andBusNumEqualTo(agentId);
        criteria.andAgentIdEqualTo(agentPid);
        return transProfitDetailMapper.selectByExample(transProfitDetailExample);
    }

    @Override
    public TransProfitDetail getTransProfitDetail(String agentId, String profitDate, String agentType) {
        if(StringUtils.isBlank(agentId)){
            return null;
        }
        TransProfitDetailExample transProfitDetailExample = new TransProfitDetailExample();
        TransProfitDetailExample.Criteria criteria = transProfitDetailExample.createCriteria();
        criteria.andProfitDateEqualTo(profitDate);
        criteria.andAgentIdEqualTo(agentId);
        criteria.andAgentTypeEqualTo(agentType);
        criteria.andBusCodeEqualTo("100003");
        List<TransProfitDetail> transProfitDetails = transProfitDetailMapper.selectByExample(transProfitDetailExample);
        if(transProfitDetails != null && !transProfitDetails.isEmpty()){
            return transProfitDetails.get(0);
        }
        return null;
    }

    @Override
    public List<TransProfitDetail> getChildTransProfitDetailList(String agentId, List<String> profitDate, String agentType) {
        if(StringUtils.isBlank(agentId)){
            return null;
        }
        TransProfitDetailExample transProfitDetailExample = new TransProfitDetailExample();
        TransProfitDetailExample.Criteria criteria = transProfitDetailExample.createCriteria();
        criteria.andProfitDateIn(profitDate);
        criteria.andAgentIdEqualTo(agentId);
        criteria.andAgentTypeEqualTo(agentType);
        criteria.andBusCodeEqualTo("100003");
        List<TransProfitDetail> transProfitDetails = transProfitDetailMapper.selectByExample(transProfitDetailExample);
        return transProfitDetails;
    }

    @Override
    public List<TransProfitDetail> getChildTransProfitDetailList(List<String> agentId, String profitDate, String agentType) {
        TransProfitDetailExample transProfitDetailExample = new TransProfitDetailExample();
        TransProfitDetailExample.Criteria criteria = transProfitDetailExample.createCriteria();
        criteria.andProfitDateEqualTo(profitDate);
        criteria.andAgentIdIn(agentId);
        criteria.andAgentTypeEqualTo(agentType);
        criteria.andBusCodeEqualTo("100003");
        List<TransProfitDetail> transProfitDetails = transProfitDetailMapper.selectByExample(transProfitDetailExample);
        return transProfitDetails;
    }

    @Override
    public List<TransProfitDetail> getChildTransProfitDetailList(List<String> agentId, List<String> profitDate, String agentType) {
        TransProfitDetailExample transProfitDetailExample = new TransProfitDetailExample();
        TransProfitDetailExample.Criteria criteria = transProfitDetailExample.createCriteria();
        criteria.andProfitDateIn(profitDate);
        criteria.andAgentIdIn(agentId);
        criteria.andAgentTypeEqualTo(agentType);

        List<TransProfitDetail> transProfitDetails = transProfitDetailMapper.selectByExample(transProfitDetailExample);
        return transProfitDetails;
    }


}
