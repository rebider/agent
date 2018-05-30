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



}
