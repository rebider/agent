package com.ryx.credit.profit.service.impl;/**
 * @Auther: zhaodw
 * @Date: 2018/8/23 09:57
 * @Description:
 */

import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.dao.TransProfitDetailMapper;
import com.ryx.credit.profit.pojo.ProfitMonth;
import com.ryx.credit.profit.pojo.TransProfitDetail;
import com.ryx.credit.profit.pojo.TransProfitDetailExample;
import com.ryx.credit.profit.service.TransProfitDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 分润交易接口实现
 * @author zhaodw
 * @create 2018/8/23
 * @since 1.0.0
 */
@Service
public class TransProfitDetailServiceImpl implements TransProfitDetailService {

    @Autowired
    private TransProfitDetailMapper transProfitDetailMapper;

    @Override
    public void insert(TransProfitDetail transProfitDetail) {
        transProfitDetailMapper.insert(transProfitDetail);
    }

    @Override
    public List<TransProfitDetail> getTransProfitDetailList(TransProfitDetail transProfitDetail) {
        TransProfitDetailExample example = new TransProfitDetailExample();
        TransProfitDetailExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(transProfitDetail.getAgentId())) {
            criteria.andAgentIdEqualTo(transProfitDetail.getAgentId());
        }
        if (StringUtils.isNotBlank(transProfitDetail.getBusCode())) {
            criteria.andBusCodeEqualTo(transProfitDetail.getBusCode());
        }
        if (StringUtils.isNotBlank(transProfitDetail.getParentAgentId())) {
            criteria.andParentAgentIdEqualTo(transProfitDetail.getParentAgentId());
        }else{
            criteria.andParentAgentIdIsNull();
        }
        if (StringUtils.isNotBlank(transProfitDetail.getProfitDate())) {
            criteria.andProfitDateEqualTo(transProfitDetail.getProfitDate());
        }
        List<TransProfitDetail> transProfitDetails = transProfitDetailMapper.selectByExample(example);
        return transProfitDetails==null?Collections.EMPTY_LIST:transProfitDetails;
    }

    @Override
    public List<TransProfitDetail> getPosTransProfitDetailSumList(String prfitDate) {
        return transProfitDetailMapper.getPosTransProfitDetailSumList(prfitDate);
    }
}
