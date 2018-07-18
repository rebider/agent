package com.ryx.credit.service.agent;

import com.ryx.credit.pojo.admin.agent.AssProtoCol;
import com.ryx.credit.pojo.admin.agent.AssProtoColRel;

import java.util.List;

/**
 * Created by cx on 2018/6/5.
 * com.ryx.credit.service.agent.AgentAssProtocolService
 * 分管考核协议服务类
 */
public interface AgentAssProtocolService {


    /**
     * 查询分管协议
     * @param id
     * @param plat
     * @return
     */
    public List<AssProtoCol> queryProtocol(String id,String plat);


    /**
     * 添加关系
     * @param rel
     * @param userId
     * @return
     */
    public int addProtocolRel(AssProtoColRel rel,String userId);

    /**
     * 查询业务相关的服务协议
     * @param busId
     * @return
     */
    public List<AssProtoCol> queryProtoColByBusId(String busId);

    public List<AssProtoColRel>  queryProtoColByBusIds(List<String> busId);

    public int updateAssProtoColRel(AssProtoColRel rel);




}
