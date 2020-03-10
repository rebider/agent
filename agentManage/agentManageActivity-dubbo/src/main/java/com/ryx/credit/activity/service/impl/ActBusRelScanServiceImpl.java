package com.ryx.credit.activity.service.impl;

import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.service.ActBusRelScanService;
import com.ryx.credit.service.ActBusRelScanView;
import com.ryx.credit.service.agent.BusActRelService;
import com.ryx.credit.spring.MySpringContextHandler;
import com.ryx.credit.vo.EventSysAct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 作者：cx
 * 时间：2020/3/9
 * 描述：扫描整个系统中运行中的工作流，并逐个调用扫描处理类进行相关业务处理
 */
@Service("actBusRelScanView")
public class ActBusRelScanServiceImpl implements ActBusRelScanView, ApplicationContextAware {

    private static Logger logger = LoggerFactory.getLogger(ActBusRelScanServiceImpl.class);

    private Map<String, ActBusRelScanService>  actBusRelScanServices;
    @Autowired
    private BusActRelService busActRelService;
    @Autowired
    private ActBusRelScanView actBusRelScanView;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        actBusRelScanServices = applicationContext.getBeansOfType(ActBusRelScanService.class);
        System.out.println(actBusRelScanServices);
    }

    @Override
    public void triggerEventSysAct(EventSysAct eventSysAct) throws MessageException {
        Set<String> keySet = actBusRelScanServices.keySet();
        for (String beanId : keySet) {
            logger.info("触发流程事件：{} {}",eventSysAct.getEventKey(),beanId);
            try {
                ActBusRelScanService service =   actBusRelScanServices.get(beanId);
                if(service.checkEventSysAct(eventSysAct)){
                   FastMap res =  service.dealEvent(eventSysAct);
                    logger.info("触发流程事件：{} {} 结果 {}",eventSysAct.getEventKey(),beanId,res.get("msg"));
                }
            } catch (MessageException e) {
                logger.info("触发流程事件：{} {} 异常 {}",eventSysAct.getEventKey(),beanId,e.getMsg());
                e.printStackTrace();
            } catch (Exception e) {
                logger.info("触发流程事件：{} {} 异常 {}",eventSysAct.getEventKey(),beanId,e.getMessage());
                e.printStackTrace();
            }
        }
    }


    @Override
    public void triggerMothEndEvent() {
        List<BusActRel> busActRelList =  busActRelService.queryBysBusTypeAndStatus(null, AgStatus.Approving.name());
        for (BusActRel busActRel : busActRelList) {
            try {
                EventSysAct eventSysAct = new EventSysAct(EventSysAct.EVENT_END_OF_MOUNTH,busActRel);
                actBusRelScanView.triggerEventSysAct(eventSysAct);
            } catch (MessageException e) {
                e.printStackTrace();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
