package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AgentBusinfoFreezeMapper;
import com.ryx.credit.dao.agent.AgentFreezeMapper;
import com.ryx.credit.pojo.admin.agent.AgentBusinfoFreeze;
import com.ryx.credit.pojo.admin.agent.AgentFreeze;
import com.ryx.credit.pojo.admin.agent.PlatForm;
import com.ryx.credit.service.agent.AgentBusinfoFreezeService;
import com.ryx.credit.service.agent.PlatFormService;
import com.ryx.credit.service.dict.IdService;
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
import java.util.Map;

/**
 * @Auther: lrr
 * @Date: 2020/6/3 15:14
 * @Description:
 */
@Service("agentBusinfoFreezeService")
public class AgentBusinfoFreezeServiceImpl implements AgentBusinfoFreezeService {

    private static Logger logger = LoggerFactory.getLogger(AgentBusinfoFreezeServiceImpl.class);

    @Autowired
    private AgentBusinfoFreezeMapper agentBusinfoFreezeMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private AgentFreezeMapper agentFreezeMapper;
    @Autowired
    private PlatFormService platFormService;


    @Override
    public PageInfo abfreezeList(Page page, Map map) {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(agentBusinfoFreezeMapper.queryAbfreezeListView(map,page));
        pageInfo.setTotal(agentBusinfoFreezeMapper.queryAbfreezeListCount(map));
        return pageInfo;
    }

    @Transactional(rollbackFor = Exception.class,isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRES_NEW)
    @Override
    public AgentResult AgentBusinfoFreeze(AgentFreeze agentFreeze,String userId){
        if(null!=agentFreeze && StringUtils.isNotBlank(agentFreeze.getAgentId())){
            //汇总冻结表的数据
            List<AgentFreeze> agentFreeList = agentFreezeMapper.queryFreeByAgentId(agentFreeze.getAgentId());
            logger.info("冻结数据查询结果{}",agentFreeList != null && !agentFreeList.isEmpty() ?agentFreeList.size() :0);
            if(null!=agentFreeList && agentFreeList.size()>0){
                //删除原有数据
                agentBusinfoFreezeMapper.deleteByAgentId(agentFreeze.getAgentId());
                for (AgentFreeze freeze : agentFreeList) {
                    AgentBusinfoFreeze agentBusinfoFreeze = new AgentBusinfoFreeze();
                    agentBusinfoFreeze.setId(idService.genAbFreezeId(TabId.a_agent_businfofreeze,Integer.valueOf(userId)));
                    agentBusinfoFreeze.setFreezeType(freeze.getFreezeType());
                    agentBusinfoFreeze.setBusId(freeze.getBusId());
                    agentBusinfoFreeze.setBusNum(freeze.getBusNum());
                    agentBusinfoFreeze.setPlatId(freeze.getBusPlatform());
                    PlatForm platForm = platFormService.selectByPlatformNum(freeze.getBusPlatform());
                    if(null!=platForm){
                        agentBusinfoFreeze.setPlatType(platForm.getPlatformType());
                        agentBusinfoFreeze.setBusPlatform(platForm.getBusplatform());
                    }
                    agentBusinfoFreeze.setAgId(freeze.getAgentId());

                    if(freeze.getBusFreeze().compareTo(new BigDecimal(0))==1){//业务冻结
                        agentBusinfoFreeze.setBusFreeze(Status.STATUS_1.status);
                    }else
                        agentBusinfoFreeze.setBusFreeze(Status.STATUS_0.status);

                    if(freeze.getProfitFreeze().compareTo(new BigDecimal(0))==1){//分润冻结
                        agentBusinfoFreeze.setProfitFreeze(Status.STATUS_1.status);
                    }else
                        agentBusinfoFreeze.setProfitFreeze(Status.STATUS_0.status);

                    if(freeze.getReflowFreeze().compareTo(new BigDecimal(0))==1){//返现冻结
                        agentBusinfoFreeze.setReflowFreeze(Status.STATUS_1.status);
                    }else
                        agentBusinfoFreeze.setReflowFreeze(Status.STATUS_0.status);

                    if(freeze.getMonthlyFreeze().compareTo(new BigDecimal(0))==1){//月结冻结
                        agentBusinfoFreeze.setMonthlyFreeze(Status.STATUS_1.status);
                    }else
                        agentBusinfoFreeze.setMonthlyFreeze(Status.STATUS_0.status);

                    if(freeze.getDailyFreeze().compareTo(new BigDecimal(0))==1){//日结冻结
                        agentBusinfoFreeze.setDailyFreeze(Status.STATUS_1.status);
                    }else
                        agentBusinfoFreeze.setDailyFreeze(Status.STATUS_0.status);

                    if(freeze.getStopProfitFreeze().compareTo(new BigDecimal(0))==1){//停发冻结
                        agentBusinfoFreeze.setStopProfitFreeze(Status.STATUS_1.status);
                    }else
                        agentBusinfoFreeze.setStopProfitFreeze(Status.STATUS_0.status);

                    if(freeze.getCashFreeze().compareTo(new BigDecimal(0))==1){//提现冻结
                        agentBusinfoFreeze.setCashFreeze(Status.STATUS_1.status);
                    }else
                        agentBusinfoFreeze.setCashFreeze(Status.STATUS_0.status);

                    if(freeze.getStopCount().compareTo(new BigDecimal(0))==1){//停算冻结
                        agentBusinfoFreeze.setStopCount(Status.STATUS_1.status);
                    }else
                        agentBusinfoFreeze.setStopCount(Status.STATUS_0.status);
                    agentBusinfoFreeze.setcTime(new Date());
                    agentBusinfoFreeze.setuTime(new Date());
                    agentBusinfoFreeze.setStatus(Status.STATUS_1.status);
                    agentBusinfoFreeze.setVersion(Status.STATUS_1.status);
                    if(1!=agentBusinfoFreezeMapper.insertSelective(agentBusinfoFreeze)){
                        logger.info("添加业务冻结失败");
                        return  AgentResult.fail("添加业务冻结失败");
                    }
                }
            }else{
                logger.info("查询无数据");
                return    AgentResult.fail("查询无数据");
            }
        }else{
            logger.info("请传入代理商唯一编码");
            return   AgentResult.fail("请传入代理商唯一编码");
        }
        return AgentResult.ok("成功");
    }
}