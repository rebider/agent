package com.ryx.credit.service.order;

import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.order.OAddress;
import com.ryx.credit.pojo.admin.order.OProduct;

/**
 * Created by RYX on 2018/7/13.
 * 地址管理
 */
public interface AddressService {
    /**
     * 地址信息查询信息
     * @param page
     * @param oAddress
     * @return
     */
    PageInfo queryAddressList(PageInfo page, OAddress oAddress);

    AgentResult saveAddress(OAddress oAddress,String user);

    AgentResult updateAddress(OAddress oAddress,String user);

    OAddress  queryById(String id);





}
