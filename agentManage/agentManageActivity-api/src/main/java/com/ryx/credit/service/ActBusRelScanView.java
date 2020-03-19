package com.ryx.credit.service;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.vo.EventSysAct;

/**
 * 作者：cx
 * 时间：2020/3/9
 * 描述：扫描整个系统中运行中的工作流，并逐个调用扫描处理类进行相关业务处理
 */
public interface ActBusRelScanView {


    /**
     * 触发月末事件
     */
    public void triggerMothEndEvent();



    /**
     * 业务处理
     * @param eventSysAct
     * @return
     */
    public void triggerEventSysAct(EventSysAct eventSysAct)throws MessageException;




}
