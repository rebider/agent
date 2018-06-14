package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.DateChangeRequestMapper;
import com.ryx.credit.pojo.admin.agent.DateChangeRequest;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.service.agent.DateChangeReqService;
import com.ryx.credit.service.dict.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @ClassName DateChangeReqServiceImpl
 * @Description lrr
 * @Author RYX
 * @Date 2018/6/1
 **/
@Service("dateChangeReqService")
public class DateChangeReqServiceImpl implements DateChangeReqService{
    private static Logger logger = LoggerFactory.getLogger(DateChangeReqServiceImpl.class);
    @Autowired
    private DateChangeRequestMapper dateChangeRequestMapper;
    @Autowired
    private IdService idService;

    @Override
    public ResultVO dateChangeReqIn(String json,String oldJson,String srcId,String type,String userId) {
        try {
            logger.info("开始数据变更");
            DateChangeRequest changeRequest = new DateChangeRequest();
            Date date = Calendar.getInstance().getTime();
            changeRequest.setId(idService.genId(TabId.data_change_request));
            changeRequest.setDataId(srcId);
            changeRequest.setDataType(type);
            changeRequest.setDataContent(json);
            changeRequest.setDataPreContent(oldJson);
            changeRequest.setcUser(userId);
            changeRequest.setcTime(date);
            changeRequest.setcUpdate(date);
            changeRequest.setAppyStatus(AgStatus.Create.status);
            changeRequest.setStatus(Status.STATUS_1.status);
            changeRequest.setVersion(Status.STATUS_1.status);
             if(1==dateChangeRequestMapper.insertSelective(changeRequest)){
                 return ResultVO.success(changeRequest);
             }else{
                 throw new ProcessException("数据变更申请失败");
             }
        }catch (Exception e){
            logger.info("数据变更失败");
            e.printStackTrace();
            throw new ProcessException("数据变更失败");
        }
    }





    @Override
    public DateChangeRequest getById(String id){
        return dateChangeRequestMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo queryData(Page page, DateChangeRequest dateChangeRequest) {
        Map<String, Object> map = new HashMap<>();
        if(null!=dateChangeRequest.getAppyStatus()){
            map.put("appStatus",dateChangeRequest.getAppyStatus());
        }
        if(!StringUtils.isBlank(dateChangeRequest.getDataType())){
            map.put("dataType",dateChangeRequest.getDataType());
        }
        List<Map<String, Object>> dateChangeReqList = dateChangeRequestMapper.queryData(map,page);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(dateChangeReqList);
        pageInfo.setTotal(dateChangeRequestMapper.queryDataCount(map));
        return pageInfo;
    }
}
