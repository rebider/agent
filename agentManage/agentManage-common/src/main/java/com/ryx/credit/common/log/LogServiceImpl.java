package com.ryx.credit.common.log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;

public class LogServiceImpl implements ILogService{

	private static final Logger log = Logger.getLogger(LogServiceImpl.class);
	
	@Override
    public void log() {
		log.info("*************Log*******************");
    }
    
    //有参无返回值的方法
    public void logArg(JoinPoint point) {
        //此方法返回的是一个数组，数组中包括request以及ActionCofig等类对象
        Object[] args = point.getArgs();
        log.info("目标参数列表：");
        if (args != null) {
            for (Object obj : args) {
            	log.info(obj + ",");
            }
        }
    }

    //有参并有返回值的方法
    public void logArgAndReturn(JoinPoint point, Object returnObj) {
        //此方法返回的是一个数组，数组中包括request以及ActionCofig等类对象
        try {
            Object[] args = point.getArgs();
            if (args != null) {
                log.info("方法名为"+point.getTarget()+
                        point.getSignature().getName()+"的目标参数列表：");
                for (Object obj : args) {
                    log.info(JSON.toJSON(obj) + ",");
                }
                if(returnObj!=null){
                    log.info("执行结果是：" + JSON.toJSON(returnObj));
                }
            }
        } catch (Exception e) {
            log.error("logArgAndReturn error",e);
        }
    }
}
