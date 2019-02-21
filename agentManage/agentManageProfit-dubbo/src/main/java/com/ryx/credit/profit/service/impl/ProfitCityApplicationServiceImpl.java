package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.DateUtils;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.profit.dao.PCityApplicationDetailMapper;
import com.ryx.credit.profit.dao.ProfitDeductionMapper;
import com.ryx.credit.profit.dao.ProfitSupplyMapper;
import com.ryx.credit.profit.enums.DeductionStatus;
import com.ryx.credit.profit.enums.DeductionType;
import com.ryx.credit.profit.pojo.*;
import com.ryx.credit.profit.service.IProfitCityApplicationService;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.agent.AgentEnterService;
import com.ryx.credit.service.agent.BusActRelService;
import com.ryx.credit.service.agent.TaskApprovalService;
import com.ryx.credit.service.dict.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("profitCityApplicationService")
public class ProfitCityApplicationServiceImpl implements IProfitCityApplicationService {

    private static Logger logger = LoggerFactory.getLogger(ProfitCityApplicationServiceImpl.class);


    @Autowired
    private IdService idService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private TaskApprovalService taskApprovalService;
    @Autowired
    private PCityApplicationDetailMapper pCityApplicationDetailMapper;
    @Autowired
    private AgentEnterService agentEnterService;
    @Autowired
    private BusActRelService busActRelService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private ProfitDeductionMapper profitDeductionMapper;
    @Autowired
    private ProfitSupplyMapper pProfitSupplyMapper;

    /**
     * 获得其他扣款申请数据
     * @param
     * @return
     */
    @Override
    public PageInfo getDeductionAppList(Page page, String userId, PCityApplicationDetail pCityApplicationDetail) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("type","1");
        map.put("userId",userId);
        map.put("agentId",pCityApplicationDetail.getAgentId());
        map.put("agentName",pCityApplicationDetail.getAgentName());
        List<Map<String,String>> list = pCityApplicationDetailMapper.getDeductionAppList(map,page);
        Long count =  pCityApplicationDetailMapper.getDeductionAppListCount(map);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(list);
        pageInfo.setTotal(Integer.parseInt(count.toString()));
        return pageInfo;
    }

    /**
     * 省区其他扣款申请：进行审批流
     */
    @Override
    public void applyOtherDeduction(PCityApplicationDetail pCityApplicationDetail, String userId, String workId,String cUser) throws Exception {
        if(StringUtils.isNotBlank(pCityApplicationDetail.getId())){
            //修改信息
            pCityApplicationDetail.setApplicationType("1");
            pCityApplicationDetailMapper.updateByPrimaryKey(pCityApplicationDetail);
        }else{
            //将数据插入到记录表中
            pCityApplicationDetail.setId(idService.genId(TabId.P_CITYAPPLICATION_DETAIL));
            pCityApplicationDetail.setApplicationType("1"); //代表其他扣款申请
            pCityApplicationDetailMapper.insert(pCityApplicationDetail);
        }


        logger.info("序列ID......"+idService.genId(TabId.P_CITYAPPLICATION_DETAIL));

        Map startPar = agentEnterService.startPar(cUser);
        if (null == startPar) {
            logger.info("========用户{}{}启动部门参数为空",  cUser);
            throw new MessageException("启动部门参数为空!");
        }

        //启动审批流
        String proceId = activityService.createDeloyFlow(null, workId, null, null, startPar);
        if (proceId == null) {
            //启动失败，要删除对应数据
            PCityApplicationDetailExample pExample = new PCityApplicationDetailExample();
            pExample.createCriteria().andIdEqualTo(pCityApplicationDetail.getId());
            pCityApplicationDetailMapper.deleteByExample(pExample);
            logger.error("其他扣款申请审批流启动失败，代理商ID：{}", pCityApplicationDetail.getAgentId());
            throw new ProcessException("其他扣款申请审批流启动失败!");
        }
        //启动审批流成功
        BusActRel record = new BusActRel();
        record.setBusId(pCityApplicationDetail.getId());
        record.setActivId(proceId);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(userId);
        record.setAgentId(pCityApplicationDetail.getAgentId());
        record.setAgentName(pCityApplicationDetail.getAgentName());
        record.setBusType(BusActRelBusType.CityApplyDeduction.name());
        try{
            taskApprovalService.addABusActRel(record);
            logger.info("其他扣款申请审批流启动成功");
        }catch (Exception e){
            e.printStackTrace();
            logger.info("其他扣款申请审批流启动失败");
            throw new ProcessException("其他扣款申请审批流启动失败!:{}",e.getMessage());
        }

        pCityApplicationDetail.setApplicationStatus(Status.STATUS_0.status.toString()); //申请中
        pCityApplicationDetailMapper.updateByPrimaryKeySelective(pCityApplicationDetail);
    }

    /**
     * 根据id获得数据
     */
    @Override
    public PCityApplicationDetail getDataById(String id) {
        return pCityApplicationDetailMapper.selectByPrimaryKey(id);
    }

    /**
     * 处理审批任务
     */
    @Override
    public AgentResult approvalTask(AgentVo agentVo, String userId) throws ProcessException {
        logger.info("审批对象：{}", JSONObject.toJSON(agentVo));
        AgentResult result = new AgentResult(500, "系统异常", "");
        Map<String, Object> reqMap = new HashMap<>();
        if(StringUtils.isNotBlank(agentVo.getOrderAprDept())){
            reqMap.put("dept", agentVo.getOrderAprDept());
        }
        //通过
        if(Objects.equals("pass",agentVo.getApprovalResult())
                && StringUtils.isBlank(agentVo.getOrderAprDept())){
            reqMap.put("dept", "finish");
            String id = agentVo.getSid();
            PCityApplicationDetail pCityApplicationDetail = pCityApplicationDetailMapper.selectByPrimaryKey(id);
            pCityApplicationDetail.setApplicationStatus("0");
            pCityApplicationDetailMapper.updateByPrimaryKeySelective(pCityApplicationDetail);
        }
        if("reject".equals(agentVo.getApprovalResult())
                && StringUtils.isBlank(agentVo.getOrderAprDept())){
            reqMap.put("dept", "finish");
        }
        //设置为退回状态
        if("back".equals(agentVo.getApprovalResult())){
            //根据id过的对应数据，设置状态为退回 3
            String id = agentVo.getSid();
            PCityApplicationDetail pCityApplicationDetail = pCityApplicationDetailMapper.selectByPrimaryKey(id);
            pCityApplicationDetail.setApplicationStatus("3");
            pCityApplicationDetailMapper.updateByPrimaryKeySelective(pCityApplicationDetail);
        }


        reqMap.put("rs", agentVo.getApprovalResult());
        reqMap.put("approvalOpinion", agentVo.getApprovalOpinion());
        reqMap.put("approvalPerson", userId);
        reqMap.put("createTime", DateUtils.dateToStringss(new Date()));
        reqMap.put("taskId", agentVo.getTaskId());

        logger.info("创建下一审批流对象：{}", reqMap.toString());
        Map resultMap = activityService.completeTask(agentVo.getTaskId(), reqMap);
        Boolean rs = (Boolean) resultMap.get("rs");
        String msg = String.valueOf(resultMap.get("msg"));
        if (resultMap == null) {
            return result;
        }
        if (!rs) {
            result.setMsg(msg);
            return result;
        }
        return AgentResult.ok(resultMap);
    }

    /**
     * 审批流回调方法
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public void completeTaskEnterActivity(String insid, String status) {
        BusActRel busActRel = new BusActRel();
        busActRel.setActivId(insid);
        try {
            BusActRel rel =  taskApprovalService.queryBusActRel(busActRel);
            if (rel != null) {
                PCityApplicationDetail pCityApplicationDetail = pCityApplicationDetailMapper.selectByPrimaryKey(rel.getBusId());
                pCityApplicationDetail.setApplicationStatus(status); //通过
                // 获取时间状态，终审前通过审批设置为分润月，终审后 设置为下一分润月  0  未终审   1 终审
                String finalStatus = redisService.getValue("commitFinal");
                if ( "1".equals(finalStatus)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
                    pCityApplicationDetail.setApplicationMonth(sdf.format(new Date()));  //下一分润月
                }else{
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new Date());
                    calendar.add(Calendar.MONTH, -1);
                    SimpleDateFormat simpleDateFormatMonth = new SimpleDateFormat("yyyyMM");
                    String profitMonth = simpleDateFormatMonth.format(calendar.getTime());
                    pCityApplicationDetail.setApplicationMonth(profitMonth);  //分润月
                }
                pCityApplicationDetailMapper.updateByPrimaryKeySelective(pCityApplicationDetail);
                //审批通过 ：其他扣款  存入其他扣款表
                if("1".equals(pCityApplicationDetail.getApplicationType()) && "1".equals(status)){
                    this.insertOtherDeduction(pCityApplicationDetail);
                }else if("2".equals(pCityApplicationDetail.getApplicationType()) && "1".equals(status)){
                    this.insertOtherSupply(pCityApplicationDetail);
                }

                logger.info("2更新审批流与业务对象");
                rel.setActivStatus(AgStatus.Approved.name());
                taskApprovalService.updateABusActRel(rel);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("其他扣款申请审批流回调异常，activId：{}"+insid);
        }
    }

    /**
     * 修改信息
     * @param pCityApplicationDetail
     * @throws Exception
     */
    @Override
    public void editCheckRegect(PCityApplicationDetail pCityApplicationDetail) throws Exception {
        logger.info("审批拒绝，修改信息");
        pCityApplicationDetailMapper.updateByPrimaryKeySelective(pCityApplicationDetail);
    }


    /**
     * 省区其他补款申请
     * @throws Exception
     */
    @Override
    public void applyOtherSupply(PCityApplicationDetail pCityApplicationDetail, String userId, String workId, String cUser) throws Exception {
        if(StringUtils.isNotBlank(pCityApplicationDetail.getId())){
            //修改信息
            pCityApplicationDetail.setApplicationType("2");
            pCityApplicationDetailMapper.updateByPrimaryKey(pCityApplicationDetail);
        }else{
            //将数据插入到记录表中
            pCityApplicationDetail.setId(idService.genId(TabId.P_CITYAPPLICATION_DETAIL));
            pCityApplicationDetail.setApplicationType("2"); //代表其他补款申请
            pCityApplicationDetailMapper.insert(pCityApplicationDetail);
        }
        logger.info("序列ID......"+idService.genId(TabId.P_CITYAPPLICATION_DETAIL));

        Map startPar = agentEnterService.startPar(cUser);
        if (null == startPar) {
            logger.info("========用户{}{}启动部门参数为空",  cUser);
            throw new MessageException("启动部门参数为空!");
        }

        //启动审批流
        String proceId = activityService.createDeloyFlow(null, workId, null, null, startPar);
        if (proceId == null) {
            //启动失败，要删除对应数据
            PCityApplicationDetailExample pExample = new PCityApplicationDetailExample();
            pExample.createCriteria().andIdEqualTo(pCityApplicationDetail.getId());
            pCityApplicationDetailMapper.deleteByExample(pExample);
            logger.error("其他补款申请审批流启动失败，代理商ID：{}", pCityApplicationDetail.getAgentId());
            throw new ProcessException("其他补款申请审批流启动失败!");
        }
        //启动审批流成功
        BusActRel record = new BusActRel();
        record.setBusId(pCityApplicationDetail.getId());
        record.setActivId(proceId);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(userId);
        record.setAgentId(pCityApplicationDetail.getAgentId());
        record.setAgentName(pCityApplicationDetail.getAgentName());
        record.setBusType(BusActRelBusType.CityApplySupply.name());
        try{
            taskApprovalService.addABusActRel(record);
            logger.info("其他补款申请审批流启动成功");
        }catch (Exception e){
            e.printStackTrace();
            logger.info("其他补款申请审批流启动失败");
            throw new ProcessException("其他补款申请审批流启动失败!:{}",e.getMessage());
        }

        pCityApplicationDetail.setApplicationStatus(Status.STATUS_0.status.toString()); //申请中
        pCityApplicationDetailMapper.updateByPrimaryKeySelective(pCityApplicationDetail);
    }

    @Override
    public PageInfo getSupplyAppList(Page page, String userId, PCityApplicationDetail pCityApplicationDetail) {
        PCityApplicationDetailExample example = new PCityApplicationDetailExample();
        example.setPage(page);
        PCityApplicationDetailExample.Criteria criteria = example.createCriteria();
        criteria.andApplicationTypeEqualTo("2");
        if(StringUtils.isNotBlank(pCityApplicationDetail.getAgentId())){
            criteria.andAgentIdEqualTo(pCityApplicationDetail.getAgentId());
        }
        if(StringUtils.isNotBlank(pCityApplicationDetail.getAgentName())){
            criteria.andAgentNameEqualTo(pCityApplicationDetail.getAgentName());
        }
        if(StringUtils.isNotBlank(userId)){
            criteria.andCreateNameEqualTo(userId);
        }
        example.setOrderByClause("CREATE_DATE desc");
        List<PCityApplicationDetail> list = pCityApplicationDetailMapper.selectByExample(example);
        Long count = pCityApplicationDetailMapper.countByExample(example);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(list);
        pageInfo.setTotal(Integer.parseInt(count.toString()));
        return pageInfo;
    }

    /**
     * 将省区申请通过的其他扣款插入扣款表中
     * @param pCityApplicationDetail
     */
    private void insertOtherDeduction(PCityApplicationDetail pCityApplicationDetail){
        ProfitDeduction profitDeduction = new ProfitDeduction();
        profitDeduction.setId(idService.genId(TabId.P_DEDUCTION));
        profitDeduction.setAgentId(pCityApplicationDetail.getAgentId());
        profitDeduction.setAgentName(pCityApplicationDetail.getAgentName());
        profitDeduction.setDeductionType(DeductionType.OTHER.getType());
        profitDeduction.setParentAgentId(pCityApplicationDetail.getParentAgentId());
        profitDeduction.setParentAgentName(pCityApplicationDetail.getParentAgentName());
        profitDeduction.setDeductionDate(pCityApplicationDetail.getApplicationMonth());
        profitDeduction.setAddDeductionAmt(pCityApplicationDetail.getApplicationAmt());
        profitDeduction.setMustDeductionAmt(pCityApplicationDetail.getApplicationAmt());
        profitDeduction.setSumDeductionAmt(pCityApplicationDetail.getApplicationAmt());
        profitDeduction.setRemark(pCityApplicationDetail.getDeductionRemark());
        profitDeduction.setDeductionStatus("0");//未扣款
        profitDeduction.setStagingStatus(DeductionStatus.NOT_APPLIED.getStatus());//未分期
        profitDeduction.setCreateDateTime(new Date());
        profitDeduction.setUserId(pCityApplicationDetail.getCreateName());
        profitDeduction.setSourceId("88");  //表示线上申请通过的其他扣款
        //插入数据表中保存
        profitDeductionMapper.insert(profitDeduction);
    }

    /**
     * 将省区申请通过的其他补款插入补款表中
     */
    private void insertOtherSupply(PCityApplicationDetail pCityApplicationDetail){
        ProfitSupply profitSupply = new ProfitSupply();
        profitSupply.setAgentId(pCityApplicationDetail.getAgentId());
        profitSupply.setAgentName(pCityApplicationDetail.getAgentName());
        profitSupply.setSupplyDate(pCityApplicationDetail.getApplicationMonth());
        profitSupply.setSupplyType(pCityApplicationDetail.getDeductionRemark());
        profitSupply.setBusBigType("99");
        List<ProfitSupply> ps = pProfitSupplyMapper.getProfitSuppList(profitSupply);
        if(ps.size()>0){
            //如果存在，补款金额相加
            ProfitSupply profitSupply1 = ps.get(0);
            BigDecimal amt = profitSupply1.getSupplyAmt().add(pCityApplicationDetail.getApplicationAmt());
            profitSupply1.setSupplyAmt(amt);
            pProfitSupplyMapper.updateByPrimaryKeySelective(profitSupply1);
        }else{
            //如果为空 插入新的数据
            profitSupply.setId(idService.genId(TabId.p_profit_supply));
            profitSupply.setParentAgentId(pCityApplicationDetail.getParentAgentId());
            profitSupply.setParentAgentName(pCityApplicationDetail.getParentAgentName());
            profitSupply.setSupplyAmt(pCityApplicationDetail.getApplicationAmt());
            profitSupply.setRemerk(pCityApplicationDetail.getRemark());
            profitSupply.setBusType(BusActRelBusType.CityApplySupply.name());
            profitSupply.setBusBigType("99");
            //如果不为空 在原来的基础上金额相加
            pProfitSupplyMapper.insertSelective(profitSupply);
        }
    }




}
