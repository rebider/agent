package com.ryx.credit.common.log;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Priority;

/**
 * 
 * log文件分级
 * 
 * @author wangqi
 * @version 1.0 
 * @date 2015年7月3日 下午3:42:25
 * @since 1.0
 */
public class LogAppender extends DailyRollingFileAppender {

    @Override
    public boolean isAsSevereAsThreshold(Priority priority) {
        // 只判断是否相等，而不判断优先级
        return this.getThreshold().equals(priority);
    }
}