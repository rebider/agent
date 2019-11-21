package com.ryx.internet.service.impl;

import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.enumc.ApprovalType;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.service.agent.AgentEnterService;
import com.ryx.credit.service.agent.BusActRelService;
import com.ryx.internet.dao.InternetLogoutMapper;
import com.ryx.internet.pojo.InternetLogout;
import com.ryx.internet.pojo.InternetLogoutDetail;
import com.ryx.internet.service.InternetCardLogoutService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/***
 * 流量卡注销
 * @Author liudh
 * @Description //TODO 
 * @Date 2019/11/21 10:14
 * @Param
 * @return
 **/
@Service("internetCardLogoutService")
public class InternetCardLogoutServiceImpl implements InternetCardLogoutService {

    private static Logger log = LoggerFactory.getLogger(InternetCardLogoutServiceImpl.class);
    @Autowired
    private AgentEnterService agentEnterService;
    @Autowired
    private BusActRelService busActRelService;
    @Autowired
    private InternetLogoutMapper internetLogoutMapper;


    @Override
    public PageInfo internetCardLogoutList(InternetLogout internetLogout, Page page, String agentId, Long userId){

        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(null);
        pageInfo.setTotal(0);
        return pageInfo;
    }

    @Override
    public PageInfo internetCardLogoutDetailList(InternetLogoutDetail internetLogoutDetail, Page page, String agentId, Long userId){

        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(null);
        pageInfo.setTotal(0);
        return pageInfo;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public AgentResult saveAndApprove(InternetLogout internetLogout, List<String> iccids, String cUser)throws MessageException {

        return AgentResult.ok();
    }

    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentResult approvalTask(AgentVo agentVo, String userId) throws Exception {
        try {
            if (agentVo.getApprovalResult().equals(ApprovalType.PASS.getValue())) {
                //开启独立事务，审批通过需处理

            }
            AgentResult result = agentEnterService.completeTaskEnterActivity(agentVo,userId);
            if(!result.isOK()){
                log.error(result.getMsg());
                throw new MessageException("工作流处理任务异常");
            }
        } catch (MessageException e) {
            e.printStackTrace();
            throw new MessageException(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            throw new MessageException(e.getLocalizedMessage());
        }
        return AgentResult.ok();
    }


    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult compressCompensateActivity(String proIns, BigDecimal agStatus)throws Exception{

        BusActRel busActRel = busActRelService.findByProIns(proIns);
        if (busActRel==null) {
            log.info("审批任务结束{}{}，未找到审批中的审批和数据关系", proIns, agStatus);
            throw new MessageException("查询关系表失败");
        }
        InternetLogout internetLogout = internetLogoutMapper.selectByPrimaryKey(busActRel.getBusId());
        if(internetLogout==null){
            throw new MessageException("查询注销记录失败");
        }
        internetLogout.setReviewStatus(agStatus);
        if(agStatus.compareTo(AgStatus.Approved.getValue())==0){
            internetLogout.setReviewPassTime(new Date());
        }

        return AgentResult.ok();
    }
}
