package com.ryx.credit.activity.service.impl.scan;

import com.ryx.credit.activity.service.impl.ActBusRelScanServiceImpl;
import com.ryx.credit.common.enumc.BusActRelBusType;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.DateUtils;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.CUser;
import com.ryx.credit.pojo.admin.vo.UserVo;
import com.ryx.credit.service.ActBusRelScanService;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.order.OrderService;
import com.ryx.credit.vo.EventSysAct;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：cx
 * 时间：2020/3/9
 * 描述：订单调整审批流程事件触发处理类
 */
@Service("orderAdjustScanServiceImpl")
public class OrderAdjustScanServiceImpl implements ActBusRelScanService {

    private static Logger logger = LoggerFactory.getLogger(ActBusRelScanServiceImpl.class);

    @Autowired
    private ActivityService activityService;
    @Autowired
    private OrderService orderService;

    @Autowired
    private IUserService iUserService;

    private static Long systemId =null;

    @PostConstruct
    public void init(){
        if(null == systemId) {
            UserVo userVo = new UserVo();
            userVo.setLoginName("system");
            List<CUser> cUsers = iUserService.selectByLoginName(userVo);
            if(cUsers.size()==1){
                systemId = cUsers.get(0).getId();
            }else{
                systemId = -1L;
            }
        }
    }
    @Override
    public boolean checkEventSysAct(EventSysAct eventSysAct) throws MessageException {
        if(eventSysAct ==null || eventSysAct.getBusActRel()==null || eventSysAct.getEventKey()==null || StringUtils.isBlank(eventSysAct.getBusActRel().getBusType()))
            return false;
        return BusActRelBusType.orderAdjust.name().equals(eventSysAct.getBusActRel().getBusType());
    }



    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public FastMap dealEvent(EventSysAct eventSysAct) throws MessageException {

            switch (eventSysAct.getEventKey()){
                case EventSysAct.EVENT_END_OF_MOUNTH:
                    try {
                        //检查是否可以终止
                        AgentResult res =  orderService.enableOrderAdjFinish(eventSysAct.getBusActRel().getBusId());
                        //运行结束
                        if(res.isOK()){
                            //查询任务
                            List<Task> list = activityService.getProcessEngine().getTaskService()
                                    .createTaskQuery()
                                    .processInstanceId(eventSysAct.getBusActRel().getActivId())
                                    .active()
                                    .list();

                            for (Task task : list) {
                                //结束流程
                                Map<String,Object> reqMap = new HashMap<>();
                                reqMap.put("rs", "reject");
                                reqMap.put("approvalOpinion", "系统自动终止");
                                reqMap.put("approvalPerson", systemId);
                                reqMap.put("createTime", DateUtils.dateToStringss(new Date()));
                                reqMap.put("taskId", task.getId());
                                activityService.completeTask(task.getId(),reqMap);
                            }

                        }
                        return FastMap.fastSuccessMap();
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new MessageException(e.getMessage());
                    }
                default:
                    return FastMap.fastSuccessMap();
            }

    }
}
