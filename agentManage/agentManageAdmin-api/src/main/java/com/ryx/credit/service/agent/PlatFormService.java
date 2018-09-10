package com.ryx.credit.service.agent;

import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.PlatForm;

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
}
