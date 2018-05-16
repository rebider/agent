package com.ryx.credit.service;

import com.baomidou.mybatisplus.service.IService;
import com.ryx.credit.commons.utils.PageInfo;
import com.ryx.credit.pojo.admin.CRole;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * CRole 表数据服务层接口
 *
 */
public interface IRoleService extends IService<CRole> {

    PageInfo selectDataGrid(PageInfo pageInfo);

    Object selectTree();

    List<Long> selectResourceIdListByRoleId(Long id);

    void updateRoleResource(Long id, String resourceIds);

    Map<String, Set<String>> selectResourceMapByUserId(Long userId);

}