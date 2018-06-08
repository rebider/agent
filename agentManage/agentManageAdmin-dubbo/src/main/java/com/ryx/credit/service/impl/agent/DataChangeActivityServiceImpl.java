package com.ryx.credit.service.impl.agent;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.BusActRelMapper;
import com.ryx.credit.dao.agent.DateChangeRequestMapper;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.pojo.admin.agent.BusActRelExample;
import com.ryx.credit.pojo.admin.agent.DateChangeRequest;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.agent.*;
import com.ryx.credit.service.dict.DictOptionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

/**
 * Created by cx on 2018/6/6.
 */
@Service("dataChangeActivityService")
public class DataChangeActivityServiceImpl implements DataChangeActivityService {

    private static Logger logger = LoggerFactory.getLogger(AgentEnterServiceImpl.class);

    @Autowired
    private AgentService agentService;
    @Autowired
    private AgentContractService agentContractService;
    @Autowired
    private AccountPaidItemService accountPaidItemService;
    @Autowired
    private AgentBusinfoService agentBusinfoService;
    @Autowired
    private AgentColinfoService agentColinfoService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private AgentAssProtocolService agentAssProtocolService;
    @Autowired
    private DateChangeRequestMapper dateChangeRequestMapper;
    @Autowired
    private BusActRelMapper busActRelMapper;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private AgentEnterService agentEnterService;


    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public ResultVO startDataChangeActivity(String dataChangeId,String userId) {
        logger.info("========用户{}启动数据修改申请{}",userId,dataChangeId);
        DateChangeRequest dateChangeRequest = dateChangeRequestMapper.selectByPrimaryKey(dataChangeId);

        BusActRelExample example = new BusActRelExample();
        example.or().andBusIdEqualTo(dateChangeRequest.getId()).andActivStatusEqualTo(AgStatus.Approving.name()).andStatusEqualTo(Status.STATUS_1.status);
        List<BusActRel> list = busActRelMapper.selectByExample(example);
        if(list.size()>0){
            logger.info("========用户{}启动数据修改申请{}{}",dataChangeId,userId,"申请进行中，禁止重复提交");
            return ResultVO.fail("申请进行中，禁止重复提交");
        }
        //不同的业务类型找到不同的启动流程
        List<Dict> actlist = dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.DATA_CACTIVITY_TYPE.name());
        String workId = null;
        for (Dict dict : actlist) {
            if(dict.getdItemvalue().equals(dateChangeRequest.getDataType())){
                workId = dict.getdItemname();
            }
        }
        dateChangeRequest.setAppyStatus(AgStatus.Approving.status);
        if(1!=dateChangeRequestMapper.updateByPrimaryKeySelective(dateChangeRequest)){
            logger.info("代理商审批，启动审批异常，更新记录状态{}:{}",dateChangeRequest.getId(),userId);
            throw  new ProcessException("更新记录状态异常");
        }
        if(StringUtils.isEmpty(workId)) {
            logger.info("========用户{}启动数据修改申请{}{}",dataChangeId,userId,"审批流启动失败字典中未配置部署流程");
            throw new ProcessException("审批流启动失败字典中未配置部署流程!");
        }
        String proce = activityService.createDeloyFlow(null,workId,null,null);
        if(proce==null){
            logger.info("========用户{}启动数据修改申请{}{}",dataChangeId,userId,"数据修改审批，审批流启动失败");
            logger.info("数据修改审批，审批流启动失败{}:{}",dataChangeId,userId);
            throw new ProcessException("审批流启动失败!");
        }
        //代理商业务视频关系
        BusActRel record = new BusActRel();
        record.setBusId(dateChangeRequest.getId());
        record.setActivId(proce);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(userId);
        record.setStatus(Status.STATUS_1.status);
        record.setBusType(dateChangeRequest.getDataType());//流程关系类型是数据申请类型
        record.setActivStatus(AgStatus.Approving.name());
        if(1!=busActRelMapper.insertSelective(record)){
            logger.info("代理商审批，启动审批异常，添加审批关系失败{}:{}",dateChangeRequest.getId(),proce);
            throw  new ProcessException("添加审批关系失败");

        }

        return ResultVO.success(null);
    }


    /**
     * 收款账户修改 审批完成处理
     * @param proIns
     * @param agStatus
     * @return
     */
    @Override
    public ResultVO compressColInfoDataChangeActivity(String proIns, String agStatus) {
        try {
            BusActRelExample example = new BusActRelExample();
            example.or().andStatusEqualTo(Status.STATUS_1.status).andActivIdEqualTo(proIns).andActivStatusEqualTo(AgStatus.Approving.name());
            List<BusActRel> resl =  busActRelMapper.selectByExample(example);
            if(resl.size()==0){
                logger.info("========审批流完成{}状态{}未找到数据与审批流关系",proIns,agStatus);
                return ResultVO.fail("未找到数据与审批流关系");
            }
            BusActRel rel = resl.get(0);
            logger.info("========审批流完成{}业务{}状态{}",proIns,rel.getBusType(),agStatus);
            DateChangeRequest dr = dateChangeRequestMapper.selectByPrimaryKey(rel.getBusId());
            if(dr==null){
                logger.info("========审批流完成{}业务{}状态{} 未找到数据",proIns,rel.getBusType(),agStatus);
                throw new ProcessException("更新数据申请失败");
            }
            try {
                if(AgStatus.Approved.name().equals(agStatus)){
                    //收款账户修改
                    if(DataChangeApyType.DC_Colinfo.name().equals(dr.getDataType())) {
                        //更新入库
                        AgentVo vo = JSONObject.parseObject(dr.getDataContent(), AgentVo.class);
                        ResultVO res = agentColinfoService.updateAgentColinfoVo(vo.getColinfoVoList(), vo.getAgent());
                        logger.info("========审批流完成{}业务{}状态{},结果{}", proIns, rel.getBusType(), agStatus, res.getResInfo());
                        //更新数据状态为审批成功
                        if(res.isSuccess()){
                            dr.setAppyStatus(AgStatus.Approved.status);
                            dr.setcUpdate(Calendar.getInstance().getTime());
                            logger.info("========审批流完成{}业务{}状态{},结果{}",proIns,rel.getBusType(),agStatus,"更新数据申请失败");
                            if(1!=dateChangeRequestMapper.updateByPrimaryKeySelective(dr)){
                                throw new ProcessException("更新数据申请失败");
                            }
                        }
                    //代理商新修改
                    }else if(DataChangeApyType.DC_Agent.name().equals(dr.getDataType())){
                        //更新入库
                        AgentVo vo = JSONObject.parseObject(dr.getDataContent(), AgentVo.class);
                        ResultVO res = agentEnterService.updateAgentVo(vo,rel.getcUser());
                        logger.info("========审批流完成{}业务{}状态{},结果{}", proIns, rel.getBusType(), agStatus, res.getResInfo());
                        //更新数据状态为审批成功
                        if(res.isSuccess()){
                            dr.setAppyStatus(AgStatus.Approved.status);
                            dr.setcUpdate(Calendar.getInstance().getTime());
                            logger.info("========审批流完成{}业务{}状态{},结果{}",proIns,rel.getBusType(),agStatus,"更新数据申请失败");
                            if(1!=dateChangeRequestMapper.updateByPrimaryKeySelective(dr)){
                                throw new ProcessException("更新数据申请失败");
                            }
                        }
                    }
                //拒绝更新数据状态
                }else if(AgStatus.Refuse.name().equals(agStatus)){
                    dr.setAppyStatus(AgStatus.Refuse.status);
                    dr.setcUpdate(Calendar.getInstance().getTime());
                    logger.info("========审批流完成{}业务{}状态{},结果{}",proIns,rel.getBusType(),agStatus,"更新数据申请失败");
                    if(1!=dateChangeRequestMapper.updateByPrimaryKeySelective(dr)){
                        throw new ProcessException("更新数据申请失败");
                    }
                }
                rel.setActivStatus(agStatus);
                if(1!=busActRelMapper.updateByPrimaryKeySelective(rel)){
                    throw new ProcessException("更新数据申请失败");
                }
            } catch (ProcessException e) {
                e.printStackTrace();
                return ResultVO.fail(e.getMessage());
            }
            return ResultVO.success(dr);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.fail(e.getMessage());
        }
    }
}
