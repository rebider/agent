package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.DataHistoryType;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.DataHistoryMapper;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.vo.*;
import com.ryx.credit.service.agent.AgentDataHistoryService;
import com.ryx.credit.service.dict.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/6/8 10:37
 */
@Service("agentDataHistoryService")
public class AgentDataHistoryServiceImpl implements AgentDataHistoryService {
    private static final Logger logger = LoggerFactory.getLogger(AgentDataHistoryServiceImpl.class);
    @Autowired
    private DataHistoryMapper dataHistoryMapper;
    @Autowired
    private IdService idService;

    @Override
    public AgentResult saveDataHistory(Object object, String dataType) {
        AgentResult result = new AgentResult(500, "参数错误", "");
        try {
            if (object == null) {
                return result;
            }
            if (StringUtils.isBlank(DataHistoryType.getContentByValue(dataType))) {
                return result;
            }
            DataHistory dataHistory = new DataHistory();
            String dataCotent = JsonUtil.objectToJson(object);
            if (dataType.equals(DataHistoryType.BASICS.getValue())) {
                Agent agent = JsonUtil.jsonToPojo(dataCotent, Agent.class);
                dataHistory.setDataId(agent.getId());
                dataHistory.setcUser(agent.getcUser());
                dataHistory.setDataVersion(null == agent.getVersion() ? Status.STATUS_0.status : agent.getVersion());
            }
            if (dataType.equals(DataHistoryType.BUSINESS.getValue())) {
                AgentBusInfoVo agentBusInfo = JsonUtil.jsonToPojo(dataCotent, AgentBusInfoVo.class);
                dataHistory.setDataId(agentBusInfo.getId());
                dataHistory.setcUser(agentBusInfo.getcUser());
                dataHistory.setDataVersion(null == agentBusInfo.getVersion() ? Status.STATUS_0.status : agentBusInfo.getVersion());
            }
            if (dataType.equals(DataHistoryType.CONTRACT.getValue())) {
                AgentContractVo agentContract = JsonUtil.jsonToPojo(dataCotent, AgentContractVo.class);
                dataHistory.setDataId(agentContract.getId());
                dataHistory.setcUser(agentContract.getcUser());
                dataHistory.setDataVersion(null == agentContract.getVersion() ? Status.STATUS_0.status : agentContract.getVersion());
            }
            if (dataType.equals(DataHistoryType.PAYMENT.getValue())) {
                CapitalVo capital = JsonUtil.jsonToPojo(dataCotent, CapitalVo.class);
                dataHistory.setDataId(capital.getId());
                dataHistory.setcUser(capital.getcUser());
                dataHistory.setDataVersion(null == capital.getVersion() ? Status.STATUS_0.status : capital.getVersion());
            }
            if (dataType.equals(DataHistoryType.GATHER.getValue())) {
                AgentColinfoVo agentColinfo = JsonUtil.jsonToPojo(dataCotent, AgentColinfoVo.class);
                dataHistory.setDataId(agentColinfo.getId());
                dataHistory.setcUser(agentColinfo.getcUser());
                dataHistory.setDataVersion(null == agentColinfo.getVarsion() ? Status.STATUS_0.status : agentColinfo.getVarsion());
            }
            if (dataType.equals(DataHistoryType.ORDER.getValue())) {
                OrderFormVo orderFormVo = JsonUtil.jsonToPojo(dataCotent, OrderFormVo.class);
                dataHistory.setDataId(orderFormVo.getId());
                dataHistory.setcUser(orderFormVo.getUserId());
                dataHistory.setDataVersion(null == orderFormVo.getVersion() ? Status.STATUS_0.status : orderFormVo.getVersion());
            }
            dataHistory.setId(idService.genId(TabId.data_history));
            dataHistory.setDataType(dataType);
            dataHistory.setDataCotent(dataCotent);
            dataHistory.setcTime(new Date());
            dataHistory.setStatus(Status.STATUS_1.status);
            int insert = dataHistoryMapper.insert(dataHistory);
            if (insert == 1) {
                return AgentResult.ok();
            }
        } catch (Exception e) {
            result.setMsg("服务器错误");
            logger.info("服务器错误");
        }
        return result;
    }

    @Override
    public AgentResult saveDataHistory(Object object, String id, String dataType, String user, BigDecimal version) {
        try {

            DataHistory dataHistory = new DataHistory();
            String dataCotent = JsonUtil.objectToJson(object);
            dataHistory.setDataId(id);
            dataHistory.setcUser(user);
            dataHistory.setDataVersion(version);
            dataHistory.setId(idService.genId(TabId.data_history));
            dataHistory.setDataType(dataType);
            dataHistory.setDataCotent(dataCotent);
            dataHistory.setcTime(new Date());
            dataHistory.setStatus(Status.STATUS_1.status);
            int insert = dataHistoryMapper.insert(dataHistory);
            if (insert == 1) {
                logger.info("历史数据插入成功，{}，{},{}",dataType,id,dataCotent);
                return AgentResult.ok();
            }else{
                return AgentResult.fail("历史数据插入错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("历史数据插入错误");
            return AgentResult.fail("历史数据插入错误");
        }
    }

    @Override
    public PageInfo selectAll(Page page, DataHistory dataHistory, String time) {

        DataHistoryExample dataHistoryExample = new DataHistoryExample();
        DataHistoryExample.Criteria criteria = dataHistoryExample.createCriteria();
        if(StringUtils.isNotBlank(dataHistory.getDataType())){
            criteria.andDataTypeEqualTo(dataHistory.getDataType());
        }
        if(StringUtils.isNotBlank(dataHistory.getDataId())){
            criteria.andDataIdEqualTo(dataHistory.getDataId());
        }

        dataHistoryExample.setPage(page);
        dataHistoryExample.setOrderByClause(" c_time desc ");
        List<DataHistory> dataHistories = dataHistoryMapper.selectByExampleWithBLOBs(dataHistoryExample);
        for (DataHistory history : dataHistories) {
            history.setDataTypeName(DataHistoryType.getContentByValue(history.getDataType()));
        }
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(dataHistories);
        pageInfo.setTotal(dataHistoryMapper.countByExample(dataHistoryExample));
        return pageInfo;
    }

    @Override
    public List<DataHistory> selectHistory(String dataId, String dataType) {

        DataHistoryExample dataHistoryExample = new DataHistoryExample();
        DataHistoryExample.Criteria criteria = dataHistoryExample.createCriteria();
        criteria.andDataTypeEqualTo(dataType);
        criteria.andDataIdEqualTo(dataId);
        dataHistoryExample.setOrderByClause(" c_time desc ");
        List<DataHistory> dataHistories = dataHistoryMapper.selectByExampleWithBLOBs(dataHistoryExample);

        return dataHistories;
    }
}
