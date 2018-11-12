package com.ryx.credit.profit.service.impl;

import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.BeanUtils;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.dao.ProfitDeductionMapper;
import com.ryx.credit.profit.dao.ProfitDetailMonthMapper;
import com.ryx.credit.profit.dao.ProfitStagingDetailMapper;
import com.ryx.credit.profit.enums.DeductionStatus;
import com.ryx.credit.profit.enums.DeductionType;
import com.ryx.credit.profit.enums.StagingDetailStatus;
import com.ryx.credit.profit.exceptions.DeductionException;
import com.ryx.credit.profit.exceptions.StagingException;
import com.ryx.credit.profit.jobs.RefundJob;
import com.ryx.credit.profit.pojo.ProfitDeduction;
import com.ryx.credit.profit.pojo.ProfitDeductionExample;
import com.ryx.credit.profit.pojo.ProfitDeducttionDetail;
import com.ryx.credit.profit.pojo.ProfitStagingDetail;
import com.ryx.credit.profit.service.ProfitDeductionService;
import com.ryx.credit.profit.service.ProfitDeducttionDetailService;
import com.ryx.credit.profit.service.ProfitSupplyService;
import com.ryx.credit.profit.service.StagingService;
import com.ryx.credit.service.dict.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhaodw
 * @Title: ProfitDeductionServiceImpl
 * @ProjectName agentManage
 * @Description: TODO
 * @date 2018/7/2417:32
 */
@Service
public class ProfitDeductionServiceImpl implements ProfitDeductionService {
    private static Logger logger = LoggerFactory.getLogger(ProfitDeductionServiceImpl.class);

    @Autowired
    private ProfitDetailMonthMapper profitDetailMonthMapper;
    @Autowired
    private ProfitDeductionMapper profitDeductionMapper;
    @Autowired
    private StagingService stagingServiceImpl;

    @Autowired
    private ProfitStagingDetailMapper stagingDetailMapper;

    @Autowired
    private IdService idService;
    @Autowired
    private ProfitDeducttionDetailService profitDeducttionDetailServiceImpl;
    @Autowired
    private ProfitSupplyService profitSupplyServiceImpl;
    @Autowired
    private RedisService redisService;

    @Override
    public PageInfo getProfitDeductionList(Map<String, Object> department, ProfitDeduction profitDeduction, Page page) {
        ProfitDeductionExample example = new ProfitDeductionExample();
        example.setPage(page);
        ProfitDeductionExample.Criteria criteria = example.createCriteria();
        // 月份按开始到结束查询
        if (StringUtils.isNotBlank(profitDeduction.getDeductionDateStart()) && StringUtils.isNotBlank(profitDeduction.getDeductionDateEnd()))
        {
            criteria.andDeductionDateBetween(profitDeduction.getDeductionDateStart(),profitDeduction.getDeductionDateEnd());
        }else if (StringUtils.isNotBlank(profitDeduction.getDeductionDateStart())){
            criteria.andDeductionDateEqualTo(profitDeduction.getDeductionDateStart());
        }else if (StringUtils.isNotBlank(profitDeduction.getDeductionDateEnd())){
            criteria.andDeductionDateEqualTo(profitDeduction.getDeductionDateEnd());
        }
        if (StringUtils.isNotBlank(profitDeduction.getAgentId())){
            criteria.andAgentIdEqualTo(profitDeduction.getAgentId());
        }
        if (StringUtils.isNotBlank(profitDeduction.getParentAgentId())){
            criteria.andAgentIdEqualTo(profitDeduction.getParentAgentId());
        }

        if (StringUtils.isNotBlank(profitDeduction.getDeductionType())){
            criteria.andDeductionTypeEqualTo(profitDeduction.getDeductionType());
        }
        if (StringUtils.isNotBlank(profitDeduction.getSourceId())){
            criteria.andSourceIdEqualTo(profitDeduction.getSourceId());
        }
        if(StringUtils.isNotBlank(profitDeduction.getAgentName())){
            criteria.andAgentNameLike(profitDeduction.getAgentName());
        }
        if(department != null){
            List<String> agentList = departmentAgentList(department);
            criteria.andAgentIdIn(agentList);
        }
        example.setOrderByClause("CREATE_DATE_TIME DESC ");
        List<ProfitDeduction> profitDeductions = profitDeductionMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(profitDeductions);
        pageInfo.setTotal(profitDeductionMapper.countByExample(example));
        return pageInfo;
    }

    /**
     * 根据当前部门信息查询分润信息
     * @param department
     */
    private List<String> departmentAgentList(Map<String, Object> department) {
        if(Objects.equals("south", department.get("ORGANIZATIONCODE")) || Objects.equals("north", department.get("ORGANIZATIONCODE"))){
            return profitDetailMonthMapper.getDistrictAgent(department.get("ORGID").toString());
        } else if(department.get("ORGANIZATIONCODE").toString().contains("south") || department.get("ORGANIZATIONCODE").toString().contains("north")){
            return profitDetailMonthMapper.getProAgent(department.get("ORGID").toString());
        }
        return null;
    }

    @Override
    public ProfitDeduction getProfitDeductionById(String id) {
        if (StringUtils.isNotBlank(id)) {
            return profitDeductionMapper.selectByPrimaryKey(id);
        }else{
            return null;
        }
    }

    @Override
    public void updateProfitDeduction(ProfitDeduction deduction) {
        if (deduction != null) {
             profitDeductionMapper.updateByPrimaryKeySelective(deduction);
        }else{
            throw new StagingException("修改状态失败。");
        }
    }

    @Override
    public void insert(ProfitDeduction deduction) {
        if(StringUtils.isBlank(deduction.getId())){
            deduction.setId(idService.genId(TabId.P_DEDUCTION));
        }
        profitDeductionMapper.insertSelective(deduction);
    }

    @Override
    public void batchInsertOtherDeduction(List<List<Object>> deductionist, String userId) {
        if(deductionist != null && deductionist.size() > 0 ) {
            deductionist.stream().filter(list->list.get(0) != null && list.get(1) != null && list.get(4) != null).forEach(list->{
                insertDeduction(list, userId);
            });


        }
    }

    /***
    * @Description: 插入其他扣款
    * @Param:  list 导入的数据
    * @Param:  userId 用户id
    * @Author: zhaodw
    * @Date: 2018/8/9
    */
    private void insertDeduction(List list, String userId) {
        BigDecimal amt = list.get(4)==null?BigDecimal.ZERO:new BigDecimal(list.get(4).toString());
        ProfitDeduction deduction = new ProfitDeduction();
        deduction.setDeductionType(DeductionType.OTHER.getType());
        deduction.setAddDeductionAmt(amt);
        deduction.setSumDeductionAmt(amt);
        deduction.setMustDeductionAmt(amt);
        deduction.setStagingStatus(DeductionStatus.NOT_APPLIED.getStatus());
        deduction.setId(idService.genIdInTran(TabId.P_DEDUCTION) );
        deduction.setAgentId(list.get(0).toString());
        deduction.setParentAgentId(list.get(1).toString());
        deduction.setAgentName(list.get(2).toString());
        deduction.setRemark(list.get(3).toString());
        deduction.setDeductionDate(LocalDate.now().plusMonths(-1).format(DateTimeFormatter.ISO_DATE).substring(0,7));
        deduction.setCreateDateTime(new Date());
        deduction.setUserId(userId);
        this.insert(deduction);
    }


    @Override
    public int getProfitDeductionCount(String deductType, String deductDate) {
        ProfitDeductionExample example = new ProfitDeductionExample();
        ProfitDeductionExample.Criteria criteria = example.createCriteria();
        criteria.andDeductionTypeEqualTo(deductType);
        criteria.andDeductionDateEqualTo(deductDate);
        return profitDeductionMapper.countByExample(example);
    }

    @Override
    public List<Map<String, Object>> getDeductDetail(String deductDate) {
        return profitDeductionMapper.getDeductDetail(deductDate);
    }

    @Override
    public List<ProfitDeduction> getProfitDeduction(ProfitDeduction profitDeduction) {
        ProfitDeductionExample example = new ProfitDeductionExample();
        ProfitDeductionExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(profitDeduction.getAgentId())){
            criteria.andAgentIdEqualTo(profitDeduction.getAgentId());
        }
        if (StringUtils.isNotBlank(profitDeduction.getDeductionType())){
            criteria.andDeductionTypeEqualTo(profitDeduction.getDeductionType());
        }
        if (StringUtils.isNotBlank(profitDeduction.getDeductionDate())){
            criteria.andDeductionDateEqualTo(profitDeduction.getDeductionDate());
        }
        if (StringUtils.isNotBlank(profitDeduction.getAgentId())){
            criteria.andAgentIdEqualTo(profitDeduction.getAgentId());
        }
        if (StringUtils.isNotBlank(profitDeduction.getAgentPid())){
            criteria.andAgentPidEqualTo(profitDeduction.getAgentPid());
        }

        if (StringUtils.isNotBlank(profitDeduction.getParentAgentId())){
                criteria.andParentAgentIdEqualTo(profitDeduction.getParentAgentId());
        }
        if (StringUtils.isNotBlank(profitDeduction.getDeductionStatus())){
            if ("N6".equals(profitDeduction.getDeductionStatus())) {
                criteria.andDeductionStatusNotEqualTo("6");
            }else {
                criteria.andDeductionStatusEqualTo(profitDeduction.getDeductionStatus());
            }
        }else {
            criteria.andDeductionStatusIsNull();
        }
        if (StringUtils.isNotBlank(profitDeduction.getSourceId())){
            criteria.andSourceIdEqualTo(profitDeduction.getSourceId());
        }
        if (profitDeduction.getActualDeductionAmt() != null && profitDeduction.getActualDeductionAmt().doubleValue()==0){
            criteria.andActualDeductionAmtIsNotNull();
        }
        if (StringUtils.isNotBlank(profitDeduction.getRemark())){
            if (!"POS考核扣款（新国都、瑞易送）".equals(profitDeduction.getRemark()) && !"手刷考核扣款（小蓝牙、MPOS）".equals(profitDeduction.getRemark())) {
                criteria.andRemarkNotIn(Arrays.asList(new String[]{"POS考核扣款（新国都、瑞易送）", "手刷考核扣款（小蓝牙、MPOS）"}));
            }else {
                criteria.andRemarkEqualTo(profitDeduction.getRemark());
            }
        }
        if (StringUtils.isNotBlank(profitDeduction.getDeductionDesc())){
            criteria.andDeductionDescEqualTo(profitDeduction.getDeductionDesc());
        }
        if (StringUtils.isNotBlank(profitDeduction.getStagingStatus())){
            criteria.andStagingStatusEqualTo(profitDeduction.getStagingStatus());
        }
        List<ProfitDeduction> profitDeductions = profitDeductionMapper.selectByExample(example);
        if(profitDeductions != null && !profitDeductions.isEmpty()){
            return profitDeductions;
        }
        return null;
    }


    @Override
    public BigDecimal otherDeductionByType(Map<String, Object> param) throws DeductionException {
        String deductionDate = LocalDate.now().plusMonths(-1).toString().substring(0,7);
        try {
            param.put("deductionDate", deductionDate);
            return otherDeduction(param);
        }catch ( Exception e) {
            e.printStackTrace();
            throw new DeductionException("扣款失败。");
        }
    }

    @Override
    public Map<String, Object> otherDeductionHbByType(Map<String, Object> param) throws DeductionException {

        String deductionDate = LocalDate.now().plusMonths(-1).toString().substring(0,7);
        param.put("deductionDate", deductionDate);
        param.put("type", DeductionType.OTHER.getType());
        List<ProfitDeduction> deductionList = getProfitDeductionListByType(param);
        return doHbDeduction(param, deductionList);
    }

    @Override
    public BigDecimal settleErrDeduction(Map<String, Object> param) throws DeductionException {
        // 获取退单扣款
        String deductionDate = LocalDate.now().plusMonths(-1).toString().substring(0,7);
        param.put("deductionDate", deductionDate);
        param.put("type", DeductionType.SETTLE_ERR.getType());
        param.put("deductionStatus", "N6");
        List<ProfitDeduction> deductionList = getProfitDeductionListByType(param);
        if (deductionList != null && deductionList.size() > 0) {
            BigDecimal profitAmt = (BigDecimal)param.get("profitAmt");
            return getDeductionAmt(deductionList, profitAmt, (String) param.get("computeType"));
        }
        return  BigDecimal.ZERO;
    }

    @Override
    public Map<String, Object> settleErrHbDeduction(Map<String, Object> param) throws DeductionException {
        // 获取退单扣款
        String deductionDate = LocalDate.now().plusMonths(-1).toString().substring(0,7);
        param.put("deductionDate", deductionDate);
        param.put("type", DeductionType.SETTLE_ERR.getType());
        param.put("deductionStatus", "N6");
        List<ProfitDeduction> deductionList = getProfitDeductionListByType(param);
        return doHbDeduction(param, deductionList);
    }

    private Map<String, Object> doHbDeduction(Map<String, Object> param, List<ProfitDeduction> deductionList) {
        List<Map<String, Object>> delList = new ArrayList<>(5);
        BigDecimal result =  BigDecimal.ZERO;
        if (deductionList != null && deductionList.size() > 0) {
            List<Map<String, Object>> hbList = ((List)param.get("hbList"));
            for (ProfitDeduction profitDeductionTemp : deductionList) {
                BigDecimal notDeductionAmt = profitDeductionTemp.getNotDeductionAmt();
                if (notDeductionAmt !=null && notDeductionAmt.doubleValue() >0) {
                    for (Map<String, Object> hb : hbList) {
                        BigDecimal basicAmt = (BigDecimal)hb.get("basicAmt");
                        if(basicAmt.doubleValue() >= notDeductionAmt.doubleValue()) {
                            result=result.add(notDeductionAmt);
                            if (basicAmt.equals(notDeductionAmt.doubleValue())) {
                                delList.add(hb);
                            }else{
                                hb.put("basicAmt", basicAmt.subtract(notDeductionAmt));
                            }
                            notDeductionAmt = BigDecimal.ZERO;
                            break;
                        }else{
                            delList.add(hb);
                            notDeductionAmt = notDeductionAmt.subtract(basicAmt);
                            result=result.add(basicAmt);
                        }
                    }
                    profitDeductionTemp.setNotDeductionAmt(notDeductionAmt);
                    profitDeductionTemp.setActualDeductionAmt(profitDeductionTemp.getMustDeductionAmt().subtract(notDeductionAmt));
                    profitDeductionMapper.updateByPrimaryKeySelective(profitDeductionTemp);
                    // 已扣足
                    if (notDeductionAmt.doubleValue()==0) {
                        // 不存在分期，直接删除下期扣款
                        if (profitDeductionTemp.getMustDeductionAmt().equals(profitDeductionTemp.getSumDeductionAmt())) {
                            delDeduction(profitDeductionTemp.getNextId());
                        }else{
                            updateNextDeduction(profitDeductionTemp.getNextId(), notDeductionAmt);
                        }
                    }else{
                        updateNextDeduction(profitDeductionTemp.getNextId(), notDeductionAmt);
                    }
                    if (delList.size() > 0) {
                        hbList.remove(delList);
                    }
                    if (hbList.isEmpty()) {
                        break;
                    }
                }
            }
        }
        param.put("actualDeductionAmtSum", result);
        param.put("delList", delList);
        return param;
    }

    private void updateNextDeduction(String nextId, BigDecimal notDeductionAmt) {
        ProfitDeduction profitDeduction = profitDeductionMapper.selectByPrimaryKey(nextId);
        profitDeduction.setUpperNotDeductionAmt(BigDecimal.ZERO);
        profitDeduction.setSumDeductionAmt(profitDeduction.getSumDeductionAmt().subtract(notDeductionAmt));
        profitDeduction.setMustDeductionAmt(profitDeduction.getSumDeductionAmt());
        profitDeductionMapper.updateByPrimaryKey(profitDeduction);
    }

    @Override
    public BigDecimal getSettleErrDeductionAmt(ProfitDeduction profitDeduction) {
        return profitDeductionMapper.getSettleErrDeductionAmt(profitDeduction);
    }

    private void  delDeduction(String id) {
        profitDeductionMapper.deleteById(id);
    }

    /*** 
    * @Description: 获取扣款列表
    * @Param:  param 扣款查询参数
    * @return: 扣款列表
    * @Author: zhaodw 
    * @Date: 2018/8/21 
    */ 
    private List<ProfitDeduction> getProfitDeductionListByType(Map<String, Object> param) {
        ProfitDeduction profitDeduction = new ProfitDeduction();
        profitDeduction.setAgentId((String)param.get("agentId"));
        profitDeduction.setDeductionType((String)param.get("type"));
        profitDeduction.setDeductionDate((String)param.get("deductionDate"));
        profitDeduction.setSourceId((String)param.get("sourceId"));
        profitDeduction.setRemark((String)param.get("remark"));
        profitDeduction.setParentAgentId((String)param.get("parentAgentId"));
        profitDeduction.setDeductionStatus((String)param.get("deductionStatus"));
        return this.getProfitDeduction(profitDeduction);
    }

    /***
    * @Description: 其他扣款处理
    * @Param:  profitAmt 分润金额
    * @Param:  agentId 机构id
    * @Param:  deductionDate 分润月份
    * @return:
    * @Author: zhaodw
    * @Date: 2018/8/8
    */
    private  BigDecimal otherDeduction(Map<String, Object> param) {
        // 获取代理商所有其它扣款信息
        param.put("type", DeductionType.OTHER.getType());
        List<ProfitDeduction> deductionList = getProfitDeductionListByType(param);
        return getDeductionAmt(deductionList, (BigDecimal) param.get("profitAmt"), (String) param.get("computeType"));
    }

    /***
    * @Description: 进行扣款处理
    * @Param:  deductionList 扣款列表
    * @Param:  profitAmt 分润金额
    * @return:  总扣款金额
    * @Author: zhaodw
    * @Date: 2018/8/9
    */
    private BigDecimal getDeductionAmt( List<ProfitDeduction> deductionList, BigDecimal profitAmt, String computeType) {
        BigDecimal result =  BigDecimal.ZERO;
        if (deductionList != null && deductionList.size() > 0) {
            for (ProfitDeduction profitDeductionTemp : deductionList) {
                result = deduction(result, profitAmt, profitDeductionTemp, computeType);
            }
        }
        return  result;
    }

    /***
    * @Description:  扣款业务实现
     * @Param:  result 扣款总金额
     * @Param:  profitAmt 分润金额
     * @Param:  profitDeductionTemp 扣款信息
    * @return:
    * @Author: zhaodw
    * @Date: 2018/8/9
    */
    private BigDecimal deduction(BigDecimal result, BigDecimal profitAmt, ProfitDeduction profitDeductionTemp, String computeType) {
        BigDecimal currentProfit = null;
        if (profitAmt.doubleValue() > 0) {
            currentProfit = profitAmt;
            profitAmt = profitAmt.subtract(profitDeductionTemp.getMustDeductionAmt());
            if (profitAmt.doubleValue() > 0) {
                result = result.add(profitDeductionTemp.getMustDeductionAmt());
            } else {
                result = result.add(currentProfit);
            }
        } else if (profitAmt.doubleValue() <= 0) {
            currentProfit = BigDecimal.ZERO;
            profitAmt = BigDecimal.ZERO;
        }
        if ("1".equals(computeType)) {
            // 申请分期
            if ("3".equals(profitDeductionTemp.getStagingStatus())) {
                stagingDeal(currentProfit, profitAmt, profitDeductionTemp);
            } else {
                generalDeal(currentProfit, profitAmt, profitDeductionTemp);
            }
        }
        return result;
    }

    /***
    * @Description: 含分期的扣款处理
    * @Param:  profitAmt 当前分润
    * @Param:  resultAmt 扣款结果
    * @Param:  profitDeductionTemp 扣款对象
    * @return:
    * @Author: zhaodw
    * @Date: 2018/8/8
    */
    private void stagingDeal(BigDecimal profitAmt,BigDecimal resultAmt, ProfitDeduction profitDeductionTemp) {
        // 扣足
        profitDeductionTemp.setDeductionStatus("1");//已扣款
        if (resultAmt.doubleValue() >= 0 && profitAmt.doubleValue() > 0) {
            profitDeductionTemp.setActualDeductionAmt(profitDeductionTemp.getMustDeductionAmt());
            profitDeductionTemp.setNotDeductionAmt(BigDecimal.ZERO);
        }else{
            if (resultAmt.doubleValue() < 0) {
                profitDeductionTemp.setActualDeductionAmt(profitAmt);
                profitDeductionTemp.setNotDeductionAmt(resultAmt.abs());
            }else{
                profitDeductionTemp.setActualDeductionAmt(BigDecimal.ZERO);
                profitDeductionTemp.setNotDeductionAmt(profitDeductionTemp.getMustDeductionAmt());
            }
        }
        BigDecimal stagAmt = getNextStagAmt(profitDeductionTemp);
        // 未扣足或分期还存在，进行下期扣款生成
        if (resultAmt.doubleValue() < 0 || profitAmt.doubleValue()==0 || stagAmt!=null) {
            // 将当期扣款对象存入历史
            createHisDeduction(profitDeductionTemp);
            profitDeductionTemp.setUpperNotDeductionAmt(profitDeductionTemp.getNotDeductionAmt());//上月未扣足
            if (stagAmt == null) {
                stagAmt = BigDecimal.ZERO;
            }
            // 更新当期未下期扣款
            profitDeductionTemp.setDeductionDate(LocalDate.now().toString().substring(0,7));
            profitDeductionTemp.setSumDeductionAmt(stagAmt.add(profitDeductionTemp.getUpperNotDeductionAmt()));
            profitDeductionTemp.setMustDeductionAmt(profitDeductionTemp.getSumDeductionAmt());
            profitDeductionTemp.setNotDeductionAmt(BigDecimal.ZERO); // 未扣足请0
            profitDeductionTemp.setActualDeductionAmt(BigDecimal.ZERO);// 实扣清0
            profitDeductionTemp.setAddDeductionAmt(BigDecimal.ZERO);
            profitDeductionTemp.setActualDeductionAmt(BigDecimal.ZERO);
            profitDeductionTemp.setDeductionStatus("0");//未扣款
            profitDeductionTemp.setCreateDateTime(new Date());
            updateStagingDetail(profitAmt, profitDeductionTemp);
        }
        profitDeductionMapper.updateByPrimaryKeySelective(profitDeductionTemp);
    }

    /***
    * @Description: 获取下月分期
    * @Param:  profitDeductionTemp 扣款对象
    * @Author: zhaodw
    * @Date: 2018/8/8
    */
    private BigDecimal getNextStagAmt(ProfitDeduction profitDeductionTemp) {
        ProfitStagingDetail profitStagingDetail = new ProfitStagingDetail();
        profitStagingDetail.setDeductionDate(LocalDate.now().toString().substring(0,7));
        profitStagingDetail.setSourceId(profitDeductionTemp.getId());
        profitStagingDetail.setStatus(StagingDetailStatus.N.getStatus());
        PageInfo pageInfo = stagingServiceImpl.getStagingDetailList(profitStagingDetail,null);
        if (pageInfo != null && pageInfo.getTotal()>0) {
           List<ProfitStagingDetail> profitStagingDetailList = pageInfo.getRows();
            return profitStagingDetailList.stream().map(profitStagingDetailTemp->profitStagingDetailTemp.getMustAmt()).reduce(BigDecimal::add).get();//((ProfitStagingDetail)pageInfo.getRows().get(0)).getMustAmt();
        }
        return null;
    }

    /***
     * @Description: 分期处理
     * @Param:  profitAmt 当前分润
     * @Param:  profitDeductionTemp 扣款对象
     * @Author: zhaodw
     * @Date: 2018/8/8
     */
    private void updateStagingDetail(BigDecimal profitAmt, ProfitDeduction profitDeductionTemp) {
        // 获取代理商本月分期
        ProfitStagingDetail profitStagingDetail = new ProfitStagingDetail();
        profitStagingDetail.setDeductionDate(LocalDate.now().plusMonths(-1).toString().substring(0,7));
        profitStagingDetail.setSourceId(profitDeductionTemp.getId());
        PageInfo pageInfo = stagingServiceImpl.getStagingDetailList(profitStagingDetail,null);
        if (pageInfo != null && pageInfo.getTotal()>0) {
            List<ProfitStagingDetail> profitStagingDetailList =  pageInfo.getRows();
            for (ProfitStagingDetail profitStagingDetailTemp : profitStagingDetailList) {
                if (profitAmt.doubleValue() < profitStagingDetailTemp.getMustAmt().doubleValue() ) {
                    profitStagingDetailTemp.setRealAmt(profitAmt);
                }else {
                    profitStagingDetailTemp.setRealAmt(profitStagingDetailTemp.getMustAmt());
                }
                profitStagingDetailTemp.setStatus(StagingDetailStatus.Y.getStatus());
                stagingServiceImpl.editStagingDetail(profitStagingDetailTemp);
            }
        }
    }

    /***
    * @Description: 一般扣款处理
    * @Param:  resultAmt 扣减后金额
    * @Author: zhaodw
    * @Date: 2018/8/8
    */
    private void generalDeal(BigDecimal profitAmt,BigDecimal resultAmt, ProfitDeduction profitDeductionTemp) {
        profitDeductionTemp.setDeductionStatus("1");//已扣款
        if (resultAmt.doubleValue() >= 0 && profitAmt.doubleValue() > 0) {
            profitDeductionTemp.setActualDeductionAmt(profitDeductionTemp.getMustDeductionAmt());
            profitDeductionTemp.setNotDeductionAmt(BigDecimal.ZERO);
        }else {
            if (resultAmt.doubleValue() < 0) {
                profitDeductionTemp.setActualDeductionAmt(profitAmt);
                profitDeductionTemp.setNotDeductionAmt(resultAmt.abs());
            }else{
                profitDeductionTemp.setActualDeductionAmt(BigDecimal.ZERO);
                profitDeductionTemp.setNotDeductionAmt(profitDeductionTemp.getMustDeductionAmt());
            }
//            if ("03".equals(profitDeductionTemp.getDeductionType())) {
            // 将当期扣款对象存入历史
            createHisDeduction(profitDeductionTemp);
            // 更新当期为下期扣款
            profitDeductionTemp.setDeductionDate(LocalDate.now().toString().substring(0, 7));
            profitDeductionTemp.setUpperNotDeductionAmt(profitDeductionTemp.getNotDeductionAmt());//上月未扣足=未扣足
            profitDeductionTemp.setMustDeductionAmt(profitDeductionTemp.getNotDeductionAmt());
            profitDeductionTemp.setSumDeductionAmt(profitDeductionTemp.getNotDeductionAmt());
            profitDeductionTemp.setNotDeductionAmt(null); // 未扣足请0
            profitDeductionTemp.setActualDeductionAmt(null);// 实扣清0
            profitDeductionTemp.setAddDeductionAmt(null);
            profitDeductionTemp.setCreateDateTime(new Date());
            profitDeductionTemp.setDeductionStatus("0");//未扣款
//            }
        }
        profitDeductionMapper.updateByPrimaryKeySelective(profitDeductionTemp);
    }

    /***
    * @Description: 创建一条扣款历史数据
    * @Param:  profitDeductionTemp 当前对象
    * @Author: zhaodw
    * @Date: 2018/8/8
    */
    private void createHisDeduction(ProfitDeduction profitDeductionTemp) {
        // 创建一条历史扣款记录
        ProfitDeduction deduction = new ProfitDeduction();
        BeanUtils.copy(profitDeductionTemp, deduction);
        deduction.setId(idService.genId(TabId.P_DEDUCTION));
        deduction.setStagingStatus("5");
        deduction.setNextId(profitDeductionTemp.getId());
        profitDeductionMapper.insert(deduction);
    }

    @Override
    public BigDecimal totalBuckleByMonth(ProfitDeduction profitDeduction) {
        return profitDeductionMapper.totalBuckleByMonth(profitDeduction);
    }

    @Override
    public BigDecimal getNotDeductionSum(String agentId) {
        // 获取代理商未扣足分期总金额
        Map<String, Object> param = new HashMap<>(2);
        param.put("agentId", agentId);
        BigDecimal stagNotDeductionSumAmt = stagingServiceImpl.getNotDeductionAmt(param);

        // 获取本月新增
        String deductionDate = LocalDate.now().plusMonths(-1).toString().substring(0,7);
        ProfitDeduction profitDeduction = new ProfitDeduction();
        profitDeduction.setAgentId(agentId);
        profitDeduction.setDeductionDate(deductionDate);
        BigDecimal deductionAmt = profitDeductionMapper.getCurrentDeductionAmtSum(profitDeduction);
        return (stagNotDeductionSumAmt==null?BigDecimal.ZERO:stagNotDeductionSumAmt).add((deductionAmt==null?BigDecimal.ZERO:deductionAmt));
    }

    /**
     * 清除本月导入
     * @return
     */
    @Override
    public int resetDataDeduction() {
        // 终审后不能清除
        String finalStatus = redisService.getValue("commitFinal");
        if (StringUtils.isBlank(finalStatus)) {
            if("1".equals(finalStatus)){
                logger.info("终审状态不能清除！");
                throw new ProcessException("终审状态不能清除！");
            }
        }
        return profitDeductionMapper.resetDataDeduction();
    }

    @Override
    public void updateProfitDeductionByMap(Map<String, BigDecimal> deductionMap) {
        Set<String> keys = deductionMap.keySet();

        for (String sourceId : keys) {
           ProfitDeduction deduction = profitDeductionMapper.selectByPrimaryKey(sourceId);
           if (deduction != null) {
                BigDecimal canelAmt = deductionMap.get(sourceId);
                if ("3".equals(deduction.getStagingStatus())) {
                    // 查询分期明细
                    ProfitStagingDetail profitStagingDetail = new ProfitStagingDetail();
                    profitStagingDetail.setSourceId(sourceId);
                    profitStagingDetail.setStatus(StagingDetailStatus.N.getStatus());
                    PageInfo pageInfo = stagingServiceImpl.getStagingDetailList(profitStagingDetail, null);
                    if (pageInfo != null) {
                        List<ProfitStagingDetail> profitStagingDetailList = pageInfo.getRows();
                        for ( int i=profitStagingDetailList.size()-1; i<=0; i--) {
                            profitStagingDetail = profitStagingDetailList.get(i);
                            canelAmt = canelAmt.subtract(profitStagingDetail.getMustAmt());
                            if (canelAmt.doubleValue() >= 0) {
                                profitStagingDetail.setStatus(StagingDetailStatus.C.getStatus());
                                stagingDetailMapper.updateByPrimaryKey(profitStagingDetail);
                                if (canelAmt.doubleValue()==0){
                                    break;
                                }
                            }else{
                                profitStagingDetail.setMustAmt(canelAmt.abs());
                                stagingDetailMapper.updateByPrimaryKey(profitStagingDetail);
                                break;
                            }
                        }
                    }
                    if (canelAmt.doubleValue() >= 0) {
                        deduction.setDeductionStatus(DeductionStatus.CANCEL.getStatus());
                    }
                }else{
                    canelAmt = canelAmt.subtract(deduction.getSumDeductionAmt());
                    if (deduction.getSumDeductionAmt().doubleValue()==0) {
                        deduction.setDeductionStatus(DeductionStatus.CANCEL.getStatus());
                    }else{
                        deduction.setSumDeductionAmt(canelAmt.abs());
                        deduction.setMustDeductionAmt(deduction.getSumDeductionAmt());
                    }
                }
               profitDeductionMapper.updateByPrimaryKey(deduction);
           }
        }
    }

}
