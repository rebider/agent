package com.ryx.credit.service.agent;

import com.ryx.credit.common.enumc.PlatformType;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.PlatForm;
import com.ryx.credit.pojo.admin.order.Organization;

import java.util.List;

/**
 * @Author Lihl
 * @Date 2018/07/19
 * 业务平台
 */
public interface PlatFormService {

    Object platFormList(PageInfo pageInfo);

    PlatForm selectByPrimaryKey(String id);

    boolean insertPlatForm(PlatForm platForm);

    int updateByPrimaryKeySelective(PlatForm record);  // 删除（编辑）状态

    int updateByPrimaryKey(PlatForm record);

    PlatForm selectByPlatformNum(String platformNum);
    public PlatformType byPlatformCode(String platformCode);

    /**
     * 获取机构下拉数据
     * @param platId 业务平台id
     * @param orgParent 机构上级
     * @param orgType 机构类型
     * @return
     */
    List<Organization> queryByOrganName(String platId, String orgParent, String orgType);
}
