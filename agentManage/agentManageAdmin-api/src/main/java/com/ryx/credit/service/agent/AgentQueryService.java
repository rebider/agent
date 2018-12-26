package com.ryx.credit.service.agent;

import com.ryx.credit.pojo.admin.agent.*;

import java.util.List;
import java.util.Map;

public interface AgentQueryService {
    /**
     * 查询基本信息
     */
    public Agent informationQuery(String id);


    /**
     * 查询收款信息
     */
    public List<AgentColinfo> proceedsQuery(String id);


    /**
     * 查询缴纳款项
     */
    public List<Capital> paymentQuery(String id);

    /**
     * 缴款查询
     * @param agentId
     * @param type
     * @return
     */
    public List<Capital> capitalQuery(String agentId,String type);

    /**
     * 查询合同信息
     */
    public List<AgentContract> compactQuery(String id);


    /**
     * 查询业务信息
     */
    public List<AgentBusInfo> businessQuery(String id);


    /**
     * 查询附件信息
     */
    public List<Attachment> accessoryQuery(String id,String busType);


    /**
     * 根据流程实例Id查询流程对应信息
     * @param proid
     * @return
     */
    public Map<String,Object> queryInfoByProInsId(String proid);


    /**
     * 查询给定的代理商
     * @param ids
     * @return
     */
    public List<Agent> queryAgentListByIds(List<String> ids);


    /**
     * 获取dPosRegionName 根据ids
     * @param ids
     * @return
     */
    public String dPosRegionNameFromDposIds(String...ids);

    /**
     * 获取dRegionName 根据ids
     * @param ids
     * @return
     */
    public String dRegionNameFromIds(String... ids);

    /**
     * 根据业务ID获取代理商名称
     * @param busId
     * @return
     */
    public String getAgentNameByBusId(String busId);

    /**
     * 加载缓存
     */
    public void loadCach();


    List<AgentBusInfo> businessQuery(String agentId,String isZpos);

    /**
     * 查询用户收款账户
     * @param agentId
     * @return
     */
    AgentColinfo queryUserColinfo(String agentId);
}
