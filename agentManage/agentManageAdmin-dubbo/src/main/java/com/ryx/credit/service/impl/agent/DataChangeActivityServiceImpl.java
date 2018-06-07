package com.ryx.credit.service.impl.agent;

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

    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public ResultVO startDataChangeActivity(String dataChangeId,String userId) {
        logger.info("========用户{}启动数据修改申请{}",dataChangeId,userId);
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
        dateChangeRequest.setAppyStatus(AgStatus.Approving.status);
        if(1!=dateChangeRequestMapper.updateByPrimaryKeySelective(dateChangeRequest)){
            logger.info("代理商审批，启动审批异常，更新记录状态{}:{}",dateChangeRequest.getId(),proce);
            throw  new ProcessException("更新记录状态异常");
        }
        return ResultVO.success(null);
    }
}
