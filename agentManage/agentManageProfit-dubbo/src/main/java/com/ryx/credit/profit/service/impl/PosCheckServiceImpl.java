package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.BusActRelBusType;
import com.ryx.credit.common.enumc.RewardStatus;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.DateUtils;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.profit.dao.PosCheckMapper;
import com.ryx.credit.profit.pojo.PosCheck;
import com.ryx.credit.profit.pojo.PosCheckExample;
import com.ryx.credit.profit.service.IPosCheckService;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.agent.TaskApprovalService;
import com.ryx.credit.service.dict.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    Logger logger = LoggerFactory.getLogger(PosCheckServiceImpl.class);
    @Autowired
    private PosCheckMapper checkMapper;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private TaskApprovalService taskApprovalService;
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
    public PageInfo PosCheckList(PosCheck record, Page page) {
        PosCheckExample example = checkEqualsTo(record);
        example.setOrderByClause("APP_DATE "+Page.ORDER_DIRECTION_DESC);
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
        if(StringUtils.isNotBlank(check.getAgentPid())){
            criteria.andAgentPidEqualTo(check.getAgentPid());
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

    /**
     * @author: Lihl
     * @desc 分润比例考核申请，进行审批流
     * @param posCheck
     * @param userId
     * @param workId
     */
    @Override
    public void applyPosCheck(PosCheck posCheck, String userId, String workId) throws ProcessException {
        LocalDate date = LocalDate.now();
        posCheck.setId((idService.genId(TabId.p_pos_check)));
        posCheck.setAppDate(date.plusMonths(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        System.out.println("序列ID---------------------"+idService.genId(TabId.p_pos_check));
        checkMapper.insertSelective(posCheck);

        //启动审批流
        String proceId = activityService.createDeloyFlow(null, workId, null, null, null);
        if (proceId == null) {
            PosCheckExample posCheckExample = new PosCheckExample();
            posCheckExample.createCriteria().andIdEqualTo(posCheck.getId());
            checkMapper.deleteByExample(posCheckExample);
            logger.error("分润比例考核审批流启动失败，代理商ID：{}", posCheck.getAgentId());
            throw new ProcessException("分润比例考核审批流启动失败!");
        }
        BusActRel record = new BusActRel();
        record.setBusId(posCheck.getId());
        record.setActivId(proceId);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(userId);
        record.setBusType(BusActRelBusType.POSCHECK.name());
        record.setAgentId(posCheck.getAgentId());
        record.setAgentName(posCheck.getAgentName());
        try {
            taskApprovalService.addABusActRel(record);
            logger.info("分润比例考核审批流启动成功");
        } catch (Exception e) {
            e.getStackTrace();
            logger.error("分润比例考核审批流启动失败{}");
            throw new ProcessException("分润比例考核审批流启动失败!:{}",e.getMessage());
        }
        posCheck.setCheckStatus(RewardStatus.REVIEWING.getStatus());   // REVIEWING 0:审核中
        checkMapper.updateByPrimaryKeySelective(posCheck);
    }

    @Override
    public AgentResult approvalTask(AgentVo agentVo, String userId) throws ProcessException {
        logger.info("审批对象：{}", JSONObject.toJSON(agentVo));
        AgentResult result = new AgentResult(500, "系统异常", "");
        Map<String, Object> reqMap = new HashMap<>();
        if(StringUtils.isNotBlank(agentVo.getOrderAprDept())){
            reqMap.put("dept", agentVo.getOrderAprDept());
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

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public void completeTaskEnterActivity(String insid, String status) {
        BusActRel busActRel = new BusActRel();
        busActRel.setActivId(insid);
        try {
            BusActRel rel =  taskApprovalService.queryBusActRel(busActRel);
            if (rel != null) {
                PosCheck posCheck = checkMapper.selectByPrimaryKey(rel.getBusId());
                posCheck.setCheckStatus(RewardStatus.PASS.getStatus());   // PASS 1:生效
                checkMapper.updateByPrimaryKeySelective(posCheck);
                logger.info("2更新审批流与业务对象");
                rel.setStatus(Status.STATUS_2.status);
                taskApprovalService.updateABusActRel(rel);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("分润比例考核审批流回调异常，activId：{}"+insid);
        }
    }

    @Override
    public PosCheck getPosCheckById(String id) {
        if (StringUtils.isNotBlank(id)) {
            return checkMapper.selectByPrimaryKey(id);
        }else{
            return null;
        }
    }

    @Override
    public List<PosCheck> getPosCheckByDataId(String id) {
        if(StringUtils.isNotBlank(id)){
            PosCheckExample posCheckExample = new PosCheckExample();
            PosCheckExample.Criteria criteria = posCheckExample.createCriteria();
            criteria.andIdEqualTo(id);
            return checkMapper.selectByExample(posCheckExample);
        }
        return null;
    }

    @Override
    public void editCheckRegect(PosCheck posCheck) throws Exception {
        try {
            checkMapper.updateByPrimaryKeySelective(posCheck);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }


}
