package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AgentMapper;
import com.ryx.credit.dao.agent.BusActRelMapper;
import com.ryx.credit.dao.order.TerminalTransferDetailMapper;
import com.ryx.credit.dao.order.TerminalTransferMapper;
import com.ryx.credit.machine.vo.ChangeActMachineVo;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.agent.AgentEnterService;
import com.ryx.credit.service.order.TerminalTransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/***
 * @Author liudh
 * @Description //TODO 
 * @Date 2018/12/20 10:57
 * @Param
 * @return
 **/
@Service("terminalTransferService")
public class TerminalTransferServiceImpl implements TerminalTransferService {

    private static Logger log = LoggerFactory.getLogger(TerminalTransferServiceImpl.class);
    @Autowired
    private TerminalTransferMapper terminalTransferMapper;
    @Autowired
    private TerminalTransferDetailMapper terminalTransferDetailMapper;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private BusActRelMapper busActRelMapper;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private AgentEnterService agentEnterService;



    @Override
    public PageInfo terminalTransferList(TerminalTransfer terminalTransfer, Page page) {

        TerminalTransferExample example = new TerminalTransferExample();
        TerminalTransferExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        example.setPage(page);
        List<TerminalTransfer> terminalTransferList = terminalTransferMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(terminalTransferList);
        pageInfo.setTotal((int)terminalTransferMapper.countByExample(example));
        return pageInfo;
    }

    @Override
    public PageInfo terminalTransferDetailList(TerminalTransferDetail terminalTransferDetail, Page page) {

        Map<String,Object> reqMap = new HashMap<>();
        List<Map<String,Object>> terminalTransferList = terminalTransferDetailMapper.selectTerminalTransferDetailList(reqMap,page);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(terminalTransferList);
        pageInfo.setTotal(terminalTransferDetailMapper.selectTerminalTransferDetailCount(reqMap));
        return pageInfo;
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult startTerminalTransferActivity(String id, String cuser, String agentId) throws Exception {

        if (StringUtils.isBlank(id)) {
            log.info("终端划拨提交审批,订单ID为空{}:{}", id, cuser);
            return AgentResult.fail("终端划拨提交审批，订单ID为空");
        }
        if (StringUtils.isBlank(cuser)) {
            log.info("终端划拨提交审批,操作用户为空{}:{}", id, cuser);
            return AgentResult.fail("终端划拨审批中，操作用户为空");
        }
        //更新审批中
        TerminalTransfer terminalTransfer = terminalTransferMapper.selectByPrimaryKey(id);
        if (terminalTransfer.getReviewStatus().equals(AgStatus.Approving.name())) {
            log.info("终端划拨提交审批,禁止重复提交审批{}:{}", id, cuser);
            return AgentResult.fail("终端划拨提交审批，禁止重复提交审批");
        }

        if (!terminalTransfer.getStatus().equals(Status.STATUS_1.status)) {
            log.info("终端划拨提交审批,代理商信息已失效{}:{}", id, cuser);
            return AgentResult.fail("终端划拨信息已失效");
        }

        TerminalTransfer upTerminalTransfer = new TerminalTransfer();
        upTerminalTransfer.setVersion(terminalTransfer.getVersion());
        upTerminalTransfer.setId(id);
        upTerminalTransfer.setReviewStatus(AgStatus.Approving.status);
        int i = terminalTransferMapper.updateByPrimaryKeySelective(upTerminalTransfer);
        if (1 != i) {
            log.info("终端划拨提交审批，更新订单基本信息失败{}:{}", id, cuser);
            throw new MessageException("终端划拨提交审批，更新终端划拨基本信息失败");
        }

        //启动审批
        String proce = activityService.createDeloyFlow(null, "agentTerminal", null, null, null);
        if (proce == null) {
            log.info("终端划拨提交审批，审批流启动失败{}:{}", id, cuser);
            throw new MessageException("审批流启动失败!");
        }
        //代理商业务视频关系
        BusActRel record = new BusActRel();
        record.setBusId(id);
        record.setActivId(proce);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(cuser);
        record.setStatus(Status.STATUS_1.status);
        record.setBusType(BusActRelBusType.COMPENSATE.name());
        record.setActivStatus(AgStatus.Approving.name());
        record.setAgentId(agentId);
        Agent agent = agentMapper.selectByPrimaryKey(agentId);
        if(null!=agent)
            record.setAgentName(agent.getAgName());
        if (1 != busActRelMapper.insertSelective(record)) {
            log.info("订单提交审批，启动审批异常，添加审批关系失败{}:{}", id, proce);
            throw new MessageException("审批流启动失败:添加审批关系失败");
        }
        return AgentResult.ok();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentResult approvalTerminalTransferTask(AgentVo agentVo, String userId) throws Exception{
        try {
            if(agentVo.getApprovalResult().equals("pass")){

            }
            AgentResult result = agentEnterService.completeTaskEnterActivity(agentVo,userId);
            if(!result.isOK()){
                log.error(result.getMsg());
                throw new ProcessException("工作流处理任务异常");
            }
        } catch (ProcessException e) {
            e.printStackTrace();
            throw new ProcessException("catch工作流处理任务异常!");
        }
        return AgentResult.ok();
    }


    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult compressTerminalTransferActivity(String proIns, BigDecimal agStatus)throws Exception{

        BusActRelExample example = new BusActRelExample();
        example.or().andActivIdEqualTo(proIns).andStatusEqualTo(Status.STATUS_1.status).andActivStatusEqualTo(AgStatus.Approving.name());
        List<BusActRel> list = busActRelMapper.selectByExample(example);
        if (list.size() != 1) {
            log.info("审批任务结束{}{}，未找到审批中的审批和数据关系", proIns, agStatus);
            throw new MessageException("审批和数据关系有误");
        }
        BusActRel busActRel = list.get(0);
        TerminalTransfer terminalTransfer = terminalTransferMapper.selectByPrimaryKey(busActRel.getBusId());
        terminalTransfer.setReviewStatus(agStatus);
        terminalTransfer.setuTime(new Date());
        int i = terminalTransferMapper.updateByPrimaryKeySelective(terminalTransfer);
        if(i!=1) {
            log.info("审批任务结束{}{}，终端划拨更新失败", proIns, agStatus);
            throw new MessageException("终端划拨更新失败");
        }
        return AgentResult.ok();
    }
}
