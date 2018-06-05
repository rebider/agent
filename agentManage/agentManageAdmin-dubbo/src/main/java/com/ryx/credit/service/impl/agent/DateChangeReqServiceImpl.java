package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.dao.agent.DateChangeRequestMapper;
import com.ryx.credit.pojo.admin.agent.DateChangeRequest;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.service.agent.DateChangeReqService;
import com.ryx.credit.service.dict.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

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
    public ResultVO dateChangeReqIn(String json) {
        try {
            logger.info("开始数据变更");
            DateChangeRequest changeRequest = new DateChangeRequest();
            Date date = Calendar.getInstance().getTime();
            changeRequest.setId(idService.genId(TabId.data_change_request));
            changeRequest.setcTime(date);
            changeRequest.setcUpdate(date);
            changeRequest.setStatus(Status.STATUS_1.status);
            changeRequest.setVersion(Status.STATUS_1.status);
            changeRequest.setDataContent(json);
             dateChangeRequestMapper.insertSelective(changeRequest);
            return ResultVO.success(json);
        }catch (Exception e){
            logger.info("数据变更失败");
            e.printStackTrace();
            throw new ProcessException("数据变更失败");
        }
    }
}
