package com.ryx.credit.profit.service.impl;

import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.enumc.BusActRelBusType;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.profit.dao.ProfitStagingDetailMapper;
import com.ryx.credit.profit.dao.ProfitStagingMapper;
import com.ryx.credit.profit.enums.DeductionStatus;
import com.ryx.credit.profit.enums.StagingDetailStatus;
import com.ryx.credit.profit.exceptions.StagingException;
import com.ryx.credit.profit.pojo.*;
import com.ryx.credit.profit.service.ProfitDeductionService;
import com.ryx.credit.profit.service.StagingService;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.agent.TaskApprovalService;
import com.ryx.credit.service.dict.IdService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    @Autowired
    private ActivityService activityService;

    @Autowired
    private TaskApprovalService taskApprovalService;

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
        }
        if (StringUtils.isNotBlank(profitStagingDetail.getSourceId())){
            criteria.andSourceIdEqualTo(profitStagingDetail.getSourceId());
        }
        if (StringUtils.isNotBlank(profitStagingDetail.getDeductionDate())){
            criteria.andDeductionDateEqualTo(profitStagingDetail.getDeductionDate());
        }
        example.setOrderByClause("DEDUCTION_DATE");
        profitDeductions = profitStagingDetailMapper.selectByExample(example);
        total = profitStagingDetailMapper.countByExample(example);
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
    public ProfitStaging getStagingBySourceId(String sourceId) {

        ProfitStagingExample example = new ProfitStagingExample();
        ProfitStagingExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(sourceId)) {
            criteria.andSourceIdEqualTo(sourceId);
            List<ProfitStaging> stags = profitStagingMapper.selectByExample(example);
            if (stags != null && stags.size() > 0) {
                return stags.get(0);
            }
        }

        return null;
    }

    /**
     * 删除分期
     * @param id 分期id
     */
    private void deleteStaging(String id) {
        LOG.info("删除审核拒绝的分期，id："+id);
        ProfitStagingExample example = new ProfitStagingExample();
        ProfitStagingExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(id)){
            criteria.andIdEqualTo(id);
        } else {
            LOG.error("删除分期失败。");
            throw new StagingException("删除失败");
        }
        profitStagingMapper.deleteByExample(example);
    }

    /**
     * 删除分期
     * @param stagId 分期id
     */
    private void deleteStagDetail(String stagId) {
        LOG.info("删除审核拒绝的分期明细，id："+stagId);
        ProfitStagingDetailExample example = new ProfitStagingDetailExample();
        ProfitStagingDetailExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(stagId)){
            criteria.andStagIdEqualTo(stagId);
        } else {
            LOG.error("删除审核拒绝的分期明细失败。");
            throw new StagingException("删除审核拒绝的分期明细失败");
        }
        profitStagingDetailMapper.deleteByExample(example);
    }

    @Override
    public void addStaging(ProfitStaging profitStaging, String workId) {
        LOG.info("新建分期");
        validate(profitStaging);
        ProfitDeduction deduction = new ProfitDeduction();
        deduction.setId(profitStaging.getSourceId());
        deduction.setStagingStatus(DeductionStatus.REVIEWING.getStatus());
        profitDeductionServiceImpl.updateProfitDeduction(deduction);
        profitStaging.setId(idService.genId(TabId.P_STAGING));
        profitStagingMapper.insert(profitStaging);
        startActivity(profitStaging, workId);
        // 增加扣款分期状态为分期审核中
        LOG.info("生成分期明细");
        splitStaging(profitStaging);

    }

    /**
     * 校验参数
     * @param profitStaging 分期对象
     */
    private void validate(ProfitStaging profitStaging) {
        String profitnew = LocalDate.now().plusMonths(-1).format(DateTimeFormatter.ofPattern("yyyy-MM"));
        if (profitStaging.getSumAmt()== null || StringUtils.isBlank(profitStaging.getSumAmt().toString())) {
            throw new StagingException("分期总金额不能为空");
        }
        if (profitStaging.getStagCount()==null || StringUtils.isBlank(profitStaging.getStagCount().toString())) {
            throw new StagingException("分期期数不能为空");
        }
        if (profitStaging.getSourceId()==null || StringUtils.isBlank(profitStaging.getSourceId())) {
            throw new StagingException("分期原始产品流水不能为空");
        }
        ProfitDeduction profitDeduction = profitDeductionServiceImpl.getProfitDeductionById(profitStaging.getSourceId());
        if (profitDeduction==null) {
            throw new StagingException("分期原始产品不存在");
        }
        if (profitDeduction==null) {
            throw new StagingException("分期原始产品不存在");
        }
        if (!"0".equals(profitDeduction.getStagingStatus()) && !"4".equals(profitDeduction.getStagingStatus())) {
            throw new StagingException("已经存在分期");
        }else if (!profitnew.equals(profitDeduction.getDeductionDate())) {
            throw new StagingException("该数据不能生成分期，日期过期或下期处理。");
        }
    }

    /**
     *启动审批流
     * @param  profitStaging 分期对象
     */
    private void startActivity(ProfitStaging profitStaging, String workId) {
            //启动审批
            String proceId = activityService.createDeloyFlow(null, workId, null, null, null);
            if (proceId == null) {
                LOG.error("审批流启动失败{}");
                throw new ProcessException("审批流启动失败!");
            }
            addABusActRel(profitStaging,proceId,workId);

    }

    @Override
    public void completeTaskEnterActivity(String insid, String status, String remark) throws ProcessException {

        BusActRel busActRel = new BusActRel();
        busActRel.setActivId(insid);
        try {

            BusActRel rel =  taskApprovalService.queryBusActRel(busActRel);
            if (rel != null) {
                ProfitStaging staging = getStagingById(rel.getBusId());
                if (staging !=null) {
                    rel.setStatus(Status.STATUS_2.status);
                    ProfitDeduction deduction = profitDeductionServiceImpl.getProfitDeductionById(staging.getSourceId());
                    deduction.setStagingStatus(DeductionStatus.PASS.getStatus());
                    //拒绝
                    if ("reject_end".equals(status)) {
                        LOG.info("1.更新扣款分期状态为审核不通过");
                        deduction.setStagingStatus(DeductionStatus.UN_PASS.getStatus());
                        rel.setStatus(Status.STATUS_3.status);
                        LOG.info("2.删除分期");
                        deleteStaging(staging.getId());
                        LOG.info("3.删除分期明细");
                        deleteStagDetail(staging.getId());
                    }else{
                        BigDecimal upperNotDeductionAmt = deduction.getUpperNotDeductionAmt()==null?BigDecimal.ZERO:deduction.getUpperNotDeductionAmt();
                        deduction.setMustDeductionAmt(upperNotDeductionAmt.add(staging.getStagAmt()));
                    }
//                    deduction.setRemark(remark);
                    profitDeductionServiceImpl.updateProfitDeduction(deduction);
                    LOG.info("更新审批流与业务对象");
                    taskApprovalService.updateABusActRel(rel);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.info("任务执行完成处理业务逻辑报错。");
            throw new ProcessException("任务执行完成处理业务逻辑报错!");
        }

    }

    @Override
    public void insetStagingDetail(ProfitStagingDetail stagingDetail) {
        if (stagingDetail.getId() ==null) {
            stagingDetail.setId(idService.genId(TabId.P_STAGING_DETAIL));
        }
        profitStagingDetailMapper.insert(stagingDetail);
    }

    @Override
    public void editStagingDetail(ProfitStagingDetail stagingDetail) {
        profitStagingDetailMapper.updateByPrimaryKeySelective(stagingDetail);
    }

    @Override
    public void editStaging(ProfitStaging profitStaging) {
        if (StringUtils.isNotBlank(profitStaging.getId())) {
            profitStagingMapper.updateByPrimaryKeySelective(profitStaging);
            // 删除原始分期明细信息
            deleteStagDetail(profitStaging.getId());
            //新增分析明细
            splitStaging(profitStaging);
        }else {
            throw new StagingException("修改失败。");
        }
    }

    @Override
    public BigDecimal getNotDeductionAmt(Map<String, Object> param){
        ProfitStaging profitStaging = profitStagingMapper.getNotDeductionAmt(param);

        if (profitStaging != null) {
            return profitStaging.getStagAmt();
        }
        return BigDecimal.ZERO;
    }

    /**
     * 审批流与业务关联对象
     * @param profitStaging 分期对象
     * @param proce 审批实例
     * @param workId 模板id
     */
    private void addABusActRel(ProfitStaging profitStaging, String proce, String workId) {
        BusActRel record = new BusActRel();
        record.setBusId(profitStaging.getId());
        record.setActivId(proce);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(profitStaging.getUserId());
        record.setStatus(Status.STATUS_1.status);
        if("otherDeductAgent".equals(workId) || "otherDeductCity".equals(workId)) {
            record.setBusType(BusActRelBusType.OTHER_DEDUCTION.name());
        }else{
            record.setBusType(BusActRelBusType.STAGING.name());
        }
        record.setActivStatus(AgStatus.Approving.name());
        try {
            taskApprovalService.addABusActRel(record);
        } catch (Exception e) {
            LOG.error("审批流启动失败{}");
            throw new ProcessException("审批流启动失败!");
        }
    }


    /**
     * 按照分期信息对金额进行生成分期明细
     * @param profitStaging 分期明细
     */
    private void splitStaging(ProfitStaging profitStaging) {
        ProfitStagingDetail detail = null;
        LocalDate date = LocalDate.now();
        // 对分期生成明细
        try{
            int stagCount = profitStaging.getStagCount().intValue();
            BigDecimal mustAmt = profitStaging.getSumAmt().divide(profitStaging.getStagCount(), 2, BigDecimal.ROUND_HALF_EVEN);
            for (int i=1; i<=stagCount; i++){
                detail = new ProfitStagingDetail();
                detail.setId(idService.genId(TabId.P_STAGING_DETAIL));
                detail.setCurrentStagCount(i);
                detail.setDeductionDate(date.plusMonths(i-1).format(DateTimeFormatter.ofPattern("yyyy-MM")));
                detail.setMustAmt(mustAmt);
                detail.setStagId(profitStaging.getId());
                detail.setSourceId(profitStaging.getSourceId());
                detail.setStatus(StagingDetailStatus.N.getStatus());
                if (i==stagCount) {
                    detail.setMustAmt(mustAmt.add(profitStaging.getSumAmt().subtract(mustAmt.multiply(profitStaging.getStagCount()))));
                }
                profitStagingDetailMapper.insert(detail);
            }
        }catch (Exception e) {
            LOG.error("新建分期明细失败。");
            e.printStackTrace();
            throw new StagingException("新建分期明细失败");
        }
    }

}