package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.DataHistoryType;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.DataHistoryMapper;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.vo.AgentBusInfoVo;
import com.ryx.credit.pojo.admin.vo.AgentColinfoVo;
import com.ryx.credit.pojo.admin.vo.AgentContractVo;
import com.ryx.credit.pojo.admin.vo.CapitalVo;
import com.ryx.credit.service.agent.AgentDataHistoryService;
import com.ryx.credit.service.dict.IdService;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private DataHistoryMapper dataHistoryMapper;
    @Autowired
    private IdService idService;

    @Override
    public AgentResult saveDataHistory(Object object, String dataType) {

        AgentResult result = new AgentResult(500, "参数错误", "");
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
        dataHistory.setId(idService.genId(TabId.data_history));
        dataHistory.setDataType(dataType);
        dataHistory.setDataCotent(dataCotent);
        dataHistory.setcTime(new Date());
        dataHistory.setStatus(Status.STATUS_1.status);
        int insert = dataHistoryMapper.insert(dataHistory);
        if (insert == 1) {
            return AgentResult.ok();
        }
        result.setMsg("服务器错误");
        return result;
    }

    @Override
    public PageInfo selectAll(Page page, DataHistory dataHistory,String time) {
        Map<String, Object> map = new HashMap<>();
        if (null != dataHistory.getDataType()) {
            map.put("dataType", dataHistory.getDataType());
        }
        if (null != time && time!="") {
            String reltime = time.substring(0, 10);
            map.put("time",reltime);
        }
        List<Map<String,Object>>dataList = dataHistoryMapper.selectAll(map, page);
        if (null!=dataList && dataList.size()>0){
            for (Map<String, Object> maps : dataList) {
                maps.put("DATA_TYPE",DataHistoryType.getContentByValue(String.valueOf(maps.get("DATA_TYPE"))));
            }
        }
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(dataList);
        pageInfo.setTotal(dataHistoryMapper.getCount(map));
        return pageInfo;
    }
}
