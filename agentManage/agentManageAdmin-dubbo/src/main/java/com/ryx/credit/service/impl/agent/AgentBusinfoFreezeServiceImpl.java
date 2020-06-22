package com.ryx.credit.service.impl.agent;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AgentBusInfoMapper;
import com.ryx.credit.dao.agent.AgentBusinfoFreezeMapper;
import com.ryx.credit.dao.agent.AgentFreezeMapper;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.service.AgentKafkaService;
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
import sun.management.resources.agent;

import java.math.BigDecimal;
import java.util.*;

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
    @Autowired
    private AgentKafkaService agentKafkaService;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;

    @Override
    public PageInfo abfreezeList(Page page, Map map) {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(agentBusinfoFreezeMapper.queryAbfreezeListView(map,page));
        pageInfo.setTotal(agentBusinfoFreezeMapper.queryAbfreezeListCount(map));
        return pageInfo;
    }

    @Transactional(rollbackFor = Exception.class,isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRES_NEW)
    @Override
    public AgentResult agentBusinfoFreeze(AgentFreeze agentFreeze,String userId){
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
                        if(StringUtils.isNotBlank(platForm.getBusplatform())){
                            agentBusinfoFreeze.setBusPlatform(platForm.getBusplatform());
                        }
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
                AgentResult result = queryagentBusinfoFreeze(agentFreeze.getAgentId());
                if (result.isOK()){
                    logger.info("kafka消息分发成功");
                    return AgentResult.ok("kafka消息分发成功");
                }else {
                    logger.info("kafka消息分发失败");
                    return AgentResult.fail("kafka消息分发失败");
                }

            }else{
                logger.info("查询无数据");
                return    AgentResult.fail("查询无数据");
            }
        }else{
            logger.info("请传入代理商唯一编码");
            return   AgentResult.fail("请传入代理商唯一编码");
        }
    }


    /**
     * 代理商冻结发送消息通知
     *
     *
     *  0：否  1：是
     *
     *     瑞大宝平台 （RDBPOS）
     *     手刷平台    (MPOS)
     *     实时POS平台（SSPOS）
     *     月结POS平台 (POS)
     *     瑞+平台 (RJPOS)
     *     瑞花宝平台 (RHPOS)
     *     智慧平台 （ZHPOS）
     *    智能POS平台 （ZPOS）
     * @return
     * {
     *  "msg": "成功",
     *  "code":"0000",
     *   data{
     *      "agId": "AG19103701221",            //Ag码
     *      "busFreeze": 1,                     //业务冻结
     *      "busId": "AB20191015000000000022540",//业务平台ID
     *      "busNum": "O00000000160744",        //业务平台编码
     *      "busPlatform": "000",               //平台号
     *      "cTime": 1592191686000,             //创建时间
     *      "cashFreeze": 0,                    //提现冻结
     *      "dailyFreeze": 0,                   //日结冻结
     *      "freezeType": 1,                    //冻结层级 (1:本级代理商 2：非直签下级代理商)
     *      "monthlyFreeze": 1,                 //月结冻结
     *      "platId": "100003",                 //平台ID
     *      "platType": "POS",                  //平台类型
     *      "profitFreeze": 0,                  //分润冻结
     *      "reflowFreeze": 1,                  //返现冻结
     *      "stopCount": 0,                     //停算冻结
     *      "stopProfitFreeze": 0,              //停发冻结
     *      "uTime": 1592191687000,             //修改时间
     *     }
     * }
     */
    private AgentResult queryagentBusinfoFreeze(String agentId){
        if(StringUtils.isBlank(agentId)){
            logger.info("请传入代理商Id{}",agentId);
            AgentResult.fail("请传入代理商Id");
        }
        //查询汇总的代理商信息
        AgentBusinfoFreezeExample agentBusinfoFreezeExample = new AgentBusinfoFreezeExample();
        AgentBusinfoFreezeExample.Criteria criteria = agentBusinfoFreezeExample.createCriteria().andStatusEqualTo(Status.STATUS_1.status).andAgIdEqualTo(agentId);
        List<AgentBusinfoFreeze> agentBusinfoFreezeList = agentBusinfoFreezeMapper.selectByExample(agentBusinfoFreezeExample);
        logger.info("业务冻结数据查询结果{}",agentBusinfoFreezeList != null && !agentBusinfoFreezeList.isEmpty() ?agentBusinfoFreezeList.size() :0);

        ArrayList<String> agentBusFreeList = new ArrayList<>();
        if(null!=agentBusinfoFreezeList || agentBusinfoFreezeList.size()>0){
            for (AgentBusinfoFreeze agentBusinfoFreeze : agentBusinfoFreezeList) {
                agentBusFreeList.add(agentBusinfoFreeze.getBusId());
                try {
                    logger.info("开始执行kafka消息分发");
                    AgentResult result = agentKafkaService.sendPayMentMessage(agentBusinfoFreeze.getAgId(),
                            agentBusinfoFreeze.getId(),
                            agentBusinfoFreeze.getBusId(),
                            agentBusinfoFreeze.getBusNum(),
                            KafkaMessageType.FREEZE,
                            KafkaMessageTopic.agent_Freeze.code,
                            JSONObject.toJSONString(agentBusinfoFreeze)
                    );
                    logger.info("结束kafka消息分发");
                } catch (Exception e) {
                    logger.info("kafka接口调用失败 代理商业务id {}",agentBusinfoFreeze.getBusId());
                    e.printStackTrace();
                }
            }
        }
        //查询代理商下所有的业务平台
        AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
        agentBusInfoExample.createCriteria().andStatusEqualTo(Status.STATUS_1.status)
                .andAgentIdEqualTo(agentId)
                .andCloReviewStatusEqualTo(AgStatus.Approved.status)
                .andBusStatusIn(Arrays.asList(BusinessStatus.Enabled.status,BusinessStatus.inactive.status,BusinessStatus.lock.status,BusinessStatus.pause.status))
                .andBusNumIsNotNull();
        List<AgentBusInfo> busInfoList = agentBusInfoMapper.selectByExample(agentBusInfoExample);
        logger.info("业务平台数据查询结果{}",busInfoList != null && !busInfoList.isEmpty() ?busInfoList.size() :0);
        ArrayList<String> busList = new ArrayList<>();
        if(null!=busInfoList || busInfoList.size()>0){
            for (AgentBusInfo agentBusInfo : busInfoList) {
                busList.add(agentBusInfo.getId());
            }
            //取差集
            boolean b = busList.removeAll(agentBusFreeList);
            if(busList.size()>0){
                for (String busId : busList) {
                    AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(busId);
                    try {
                        AgentBusinfoFreeze agentBusinfoFreeze = new AgentBusinfoFreeze();
                        agentBusinfoFreeze.setAgId(agentBusInfo.getAgentId());
                        agentBusinfoFreeze.setBusId(agentBusInfo.getId());
                        agentBusinfoFreeze.setBusNum(agentBusInfo.getBusNum());
                        if(StringUtils.isNotBlank(agentBusInfo.getBusPlatform())){
                            agentBusinfoFreeze.setPlatId(agentBusInfo.getBusPlatform());
                            PlatForm platForm = platFormService.selectByPlatformNum(agentBusInfo.getBusPlatform());
                            if(null!=platForm){
                                agentBusinfoFreeze.setPlatType(platForm.getPlatformType());
                                if(StringUtils.isNotBlank(platForm.getBusplatform()))
                                    agentBusinfoFreeze.setBusPlatform(platForm.getBusplatform());
                            }
                        }
                        agentBusinfoFreeze.setBusFreeze(Status.STATUS_0.status);
                        agentBusinfoFreeze.setProfitFreeze(Status.STATUS_0.status);
                        agentBusinfoFreeze.setReflowFreeze(Status.STATUS_0.status);
                        agentBusinfoFreeze.setMonthlyFreeze(Status.STATUS_0.status);
                        agentBusinfoFreeze.setDailyFreeze(Status.STATUS_0.status);
                        agentBusinfoFreeze.setStopProfitFreeze(Status.STATUS_0.status);
                        agentBusinfoFreeze.setCashFreeze(Status.STATUS_0.status);
                        agentBusinfoFreeze.setStopCount(Status.STATUS_0.status);
                        agentBusinfoFreeze.setcTime(new Date());
                        agentBusinfoFreeze.setuTime(new Date());
                        logger.info("开始执行kafka消息分发");
                        agentKafkaService.sendPayMentMessage(agentBusinfoFreeze.getAgId(),
                                agentBusinfoFreeze.getBusId(),
                                agentBusinfoFreeze.getBusId(),
                                agentBusinfoFreeze.getBusNum(),
                                KafkaMessageType.FREEZE,
                                KafkaMessageTopic.agent_Freeze.code,
                                JSONObject.toJSONString(agentBusinfoFreeze)
                        );
                        logger.info("结束kafka消息分发");
                    } catch (Exception e) {
                        logger.info("kafka接口调用失败 代理商业务id {}",agentBusInfo.getId()
                        );
                        e.printStackTrace();
                    }
                }
            }
        }
        return AgentResult.ok(agentBusinfoFreezeList);
    }
}