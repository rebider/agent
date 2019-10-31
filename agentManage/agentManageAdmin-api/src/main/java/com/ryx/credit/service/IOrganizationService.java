package com.ryx.credit.service;

import com.baomidou.mybatisplus.service.IService;
import com.ryx.credit.commons.result.Tree;
import com.ryx.credit.pojo.admin.COrganization;
import com.ryx.credit.pojo.admin.vo.COrganizationVo;

import java.util.List;
import java.util.Map;

/**
 *
 * COrganization 表数据服务层接口
 *
 */
public interface IOrganizationService extends IService<COrganization> {

    List<Tree> selectTree();

    List<COrganization> selectTreeGrid();

    COrganization selectByPrimaryKey(Integer id);

    List<COrganizationVo> selectPorg();

    List<COrganizationVo> selectOrgsByUserId(Long userId);

    List<COrganization> selectPubOrgs(Map map);

    List<COrganization> selectMaintainOrg();
}