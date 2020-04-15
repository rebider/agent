package com.ryx.credit.vo;

import com.ryx.credit.pojo.admin.agent.BusActRel;

import java.io.Serializable;

/**
 * 作者：cx
 * 时间：2020/3/9
 * 描述：工作流事件
 */
public class EventSysAct implements Serializable {

    public static final String EVENT_END_OF_MOUNTH ="EVENT_END_OF_MOUNTH";

    public EventSysAct(String eventKey, BusActRel busActRel) {
        this.eventKey = eventKey;
        this.busActRel = busActRel;
    }

    private String eventKey;

    private BusActRel busActRel;


    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public BusActRel getBusActRel() {
        return busActRel;
    }

    public void setBusActRel(BusActRel busActRel) {
        this.busActRel = busActRel;
    }
}
