package com.ryx.credit.service.impl.agent;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.InterfaceRequsetType;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.InterfaceRequestRecordMapper;
import com.ryx.credit.pojo.admin.agent.InterfaceRequestRecordWithBLOBs;
import com.ryx.credit.service.agent.BusinessCAService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.iom.plugin.entinfo.proxy.IEntInfoProxy;


@Service("businessCAService")
public class BusinessCAServiceImpl implements BusinessCAService{

    private static Logger logger = LoggerFactory.getLogger(BusinessCAServiceImpl.class);

    @Autowired
    private InterfaceRequestRecordMapper mapper;

    @Autowired
    private IEntInfoProxy iEntInfoProxy;
    
    @Autowired
    private IdService idService;
   

	@Override
	public ResultVO agentBusinessCA(String agentBusinfoName, String cUser) {
		
		if (StringUtils.isBlank(agentBusinfoName)) {
			logger.info("代理商工商认证:{}","代理商名称为空");
			throw new ProcessException("代理商名称不能为空");
		}
		
		Map<String, String> repMap = iEntInfoProxy.queryEntInfo(null, agentBusinfoName, "1");
		
		InterfaceRequestRecordWithBLOBs record = new InterfaceRequestRecordWithBLOBs();
		record.setId(idService.genId(TabId.d_InterfaceRequest_Record));
		record.setType(InterfaceRequsetType.MERCHANT_CA.getValue());
		record.setUrl("com.ryx.iom.plugin.entinfo.proxy.IEntInfoProxy.queryEntInfo");
		record.setReqJson(agentBusinfoName);
		record.setRepJson(JSONObject.toJSONString(repMap));
		record.setcTime(new Date());
		record.setcUser(cUser);
		record.setRepStatus(Status.STATUS_1.status);
		record.setStatus(Status.STATUS_1.status);
		mapper.insertSelective(record);
		
		ResultVO resultVO = new ResultVO();
		if ("0000".equals(repMap.get("resultCode"))) {
			resultVO.setResCode(ResultVO.SUCCESS);
		} else {
			resultVO.setResCode(ResultVO.FAIL);
		}
		resultVO.setObj(repMap);
		return resultVO;
	}
}
