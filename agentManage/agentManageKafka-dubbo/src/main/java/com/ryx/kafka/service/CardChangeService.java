package com.ryx.kafka.service;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.util.FastMap;

/**
 * 作者：cx
 * 时间：2020/6/15
 * 描述：结算卡变更通知处理类
 */
public interface CardChangeService {

    /**
     * 接收kafka消息通知，进行消息分发
     * @param key
     * @param msg
     * @return
     */
    public FastMap notifyCardChange(String key,String msg,String agName)throws MessageException;

}
