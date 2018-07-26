package com.ryx.credit.profit.service.impl;

import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.dao.ProfitStagingDetailMapper;
import com.ryx.credit.profit.dao.ProfitStagingMapper;
import com.ryx.credit.profit.enums.DeductionStatus;
import com.ryx.credit.profit.enums.StagingDetailStatus;
import com.ryx.credit.profit.exceptions.StagingException;
import com.ryx.credit.profit.pojo.ProfitDeduction;
import com.ryx.credit.profit.pojo.ProfitStaging;
import com.ryx.credit.profit.pojo.ProfitStagingDetail;
import com.ryx.credit.profit.pojo.ProfitStagingDetailExample;
import com.ryx.credit.profit.service.ProfitDeductionService;
import com.ryx.credit.profit.service.StagingService;
import com.ryx.credit.service.dict.IdService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaodw
 * @Title: StagingServiceImpl
 * @ProjectName agentManage
 * @Description: TODO
 * @date 2018/7/2514:18
 */
@Service
public class StagingServiceImpl implements StagingService {

    private static  final  Logger LOG = Logger.getLogger(StagingServiceImpl.class);

    @Autowired
    private ProfitDeductionService profitDeductionServiceImpl;

    @Autowired
    private ProfitStagingMapper profitStagingMapper;

    @Autowired
    private IdService idService;

    @Autowired
    private ProfitStagingDetailMapper profitStagingDetailMapper;

    @Override
    public PageInfo getStagingList(ProfitStaging profitStaging, Page page) {
        return null;
    }

    @Override
    public PageInfo getStagingDetailList(ProfitStagingDetail profitStagingDetail, Page page) {
        LOG.info("获取分期明细列表");
        ProfitStagingDetailExample example = new ProfitStagingDetailExample();
        example.setPage(page);
        ProfitStagingDetailExample.Criteria criteria = example.createCriteria();
        List<ProfitStagingDetail> profitDeductions = new ArrayList<>();
        int total = 0;
        LOG.info("分期id："+profitStagingDetail.getStagId());
        if (StringUtils.isNotBlank(profitStagingDetail.getStagId())) {
            criteria.andStagIdEqualTo(profitStagingDetail.getStagId());
            profitDeductions = profitStagingDetailMapper.selectByExample(example);
            total = profitStagingDetailMapper.countByExample(example);
        }
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(profitDeductions);
        pageInfo.setTotal(total);
        return pageInfo;
    }

    @Override
    public ProfitStaging getStagingById(String id) {
        if (StringUtils.isNotBlank(id)) {
            return profitStagingMapper.selectByPrimaryKey(id);
        }else{
            return null;
        }
    }

    @Override
    public void addStaging(ProfitStaging profitStaging) {
        LOG.info("新建分期");
        try {
            if (profitStaging.getSumAmt()== null || StringUtils.isBlank(profitStaging.getSumAmt().toString())) {
                throw new StagingException("分期总金额不能为空");
            }
            if (profitStaging.getStagCount()==null || StringUtils.isBlank(profitStaging.getStagCount().toString())) {
                throw new StagingException("分期期数不能为空");
            }
            if (profitStaging.getSourceId()==null || StringUtils.isBlank(profitStaging.getSourceId())) {
                throw new StagingException("分期原始产品流水不能为空");
            }
            // 增加扣款分期状态为分期审核中
            profitDeductionServiceImpl.updateStagingStatusById(profitStaging.getSourceId(), DeductionStatus.UNREVIEWED.getStatus());
            profitStaging.setId(idService.genId(TabId.P_STAGING));
            profitStagingMapper.insert(profitStaging);
        }catch (StagingException e){
            LOG.error(e.getMessage());
            e.printStackTrace();
            throw new StagingException(e.getMessage());
        }catch (Exception e){
            LOG.error("新建分期失败。");
            e.printStackTrace();
            throw new StagingException("新建分期失败");
        }

        LOG.info("生成分期明细");
        splitStaging(profitStaging);
    }

    /**
     * 按照分期信息对金额进行生成分期明细
     * @param profitStaging 分期明细
     */
    private void splitStaging(ProfitStaging profitStaging) {
        ProfitStagingDetail detail = null;
        LocalDate date = LocalDate.now();
        BigDecimal mustAmt = profitStaging.getSumAmt().divide(profitStaging.getStagCount());
        int stagCount = profitStaging.getStagCount().intValue();
        // 对分期生成明细
        try{
            for (int i=1; i<=stagCount; i++){
                detail = new ProfitStagingDetail();
                detail.setId(idService.genId(TabId.P_STAGING_DETAIL));
                detail.setCurrentStagCount(i);
                detail.setDeductionDate(date.plusMonths(i-1).format(DateTimeFormatter.ofPattern("yyyy-MM")));
                detail.setMustAmt(mustAmt);
                detail.setStagId(profitStaging.getId());
                detail.setSourceId(profitStaging.getSourceId());
                detail.setStatus(StagingDetailStatus.N.getStatus());
                profitStagingDetailMapper.insert(detail);
            }
        }catch (Exception e) {
            LOG.error("新建分期明细失败。");
            e.printStackTrace();
            throw new StagingException("新建分期明细失败");
        }
    }

}