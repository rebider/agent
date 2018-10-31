package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.BusActRelBusType;
import com.ryx.credit.common.enumc.RewardStatus;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.DateUtils;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.AgentColinfo;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.profit.dao.PTaxAdjustMapper;
import com.ryx.credit.profit.pojo.*;
import com.ryx.credit.profit.service.IPTaxAdjustService;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.agent.AgentColinfoService;
import com.ryx.credit.service.agent.AgentService;
import com.ryx.credit.service.agent.TaskApprovalService;
import com.ryx.credit.service.dict.IdService;
import org.apache.poi.ss.formula.functions.Days360;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    @Autowired
    private ActivityService activityService;
    @Autowired
    private TaskApprovalService taskApprovalService;
    @Autowired
    private AgentColinfoService agentColinfoService;
    @Autowired
    private AgentService agentService;

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
        example.setOrderByClause("VALID_DATE "+Page.ORDER_DIRECTION_DESC);
        List<PTaxAdjust> profitD = adjustMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(profitD);
        pageInfo.setTotal((int)adjustMapper.countByExample(example));
        return pageInfo;
    }

    @Override
    public PTaxAdjust selectByAgentId(String agentId) {
        return adjustMapper.selectByAgentId(agentId);
    }

    @Override
    public ResultVO posTaxEnterIn(PTaxAdjust tax) throws ProcessException {
        tax.setId(idService.genId(TabId.p_profit_adjust));
        adjustMapper.insertSelective(tax);

        //启动审批流
        String proceId = activityService.createDeloyFlow(null, "taxPoint", null, null, null);
        if (proceId == null) {
            logger.error("税点调整审批流启动失败，代理商ID：{}", tax.getAgentId());
            throw new ProcessException("代理商合并审批流启动失败!");
        }
        BusActRel record = new BusActRel();
        record.setBusId(tax.getId());
        record.setActivId(proceId);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(tax.getUserId());
        record.setBusType(BusActRelBusType.POSTAX.name());
        record.setAgentId(tax.getAgentId());
        record.setAgentName(tax.getAgentName());
        try {
            taskApprovalService.addABusActRel(record);
            logger.info("税点调整申请审批流启动成功");
        } catch (Exception e) {
            e.getStackTrace();
            logger.error("税点调整申请审批流启动失败{}");
            throw new ProcessException("税点调整申请审批流启动失败!:{}",e.getMessage());
        }
        return ResultVO.success(record);
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public void completeTaskEnterActivity(String insid, String status) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        BusActRel busActRel = new BusActRel();
        busActRel.setActivId(insid);
        try {
            BusActRel rel =  taskApprovalService.queryBusActRel(busActRel);
            if (rel != null) {
                PTaxAdjust taxAdjust = adjustMapper.selectByPrimaryKey(rel.getBusId());
                taxAdjust.setTaxStatus(RewardStatus.PASS.getStatus());//PASS 1:生效
                taxAdjust.setValidDate(df.format(new Date()));//生效日期
                logger.info("1.更新税点调整申请状态为通过，有效");
                logger.info("审批通过，原税点：{},现税点：{}",taxAdjust.getTaxOld(),taxAdjust.getTaxIng());
                adjustMapper.updateByPrimaryKeySelective(taxAdjust);

                String agentId = taxAdjust.getAgentId();//代理商编码

                //更新收款账户表的 税点值
                AgentColinfo colinfo = new AgentColinfo();
                colinfo.setAgentId(agentId);
                AgentColinfo agentColinfo = agentColinfoService.queryPoint(colinfo);//查询数据
                if (agentColinfo != null) {
                    BigDecimal colinfoPoint = taxAdjust.getTaxIng();
                    agentColinfo.setCloTaxPoint(colinfoPoint);
                    if (agentColinfoService.updateByPrimaryKeySelective(agentColinfo)!=1) {
                        throw new MessageException("更新收款账户税点值失败！");
                    }
                }

                // 更新代理商表的 税点值
                Agent agent = agentService.getAgentById(agentId);//查询数据
                if (agent != null) {
                    BigDecimal agentPoint = taxAdjust.getTaxIng();
                    agent.setCloTaxPoint(agentPoint);
                    if (agentService.updateByPrimaryKeySelective(agent)!=1) {
                        throw new MessageException("更新代理商税点值失败！");
                    }
                }

                logger.info("2.更新审批流与业务对象");
                rel.setStatus(Status.STATUS_2.status);
                taskApprovalService.updateABusActRel(rel);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("税点调整审批流回调异常，activId：{}" + insid);
        }
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

    @Override
    public PTaxAdjust getPosTaxById(String id) {
        if (StringUtils.isNotBlank(id)) {
            return adjustMapper.selectByPrimaryKey(id);
        }else{
            return null;
        }
    }

    @Override
    public List<PTaxAdjust> getPosTaxByDataId(String id) {
        if(StringUtils.isNotBlank(id)){
            PTaxAdjustExample taxAdjustExample = new PTaxAdjustExample();
            PTaxAdjustExample.Criteria criteria = taxAdjustExample.createCriteria();
            criteria.andIdEqualTo(id);
            return adjustMapper.selectByExample(taxAdjustExample);
        }
        return null;
    }

    @Override
    public void editPosTaxRegect(PTaxAdjust pTaxAdjust) throws Exception {
        try {
            adjustMapper.updateByPrimaryKeySelective(pTaxAdjust);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
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
