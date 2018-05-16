package com.ryx.credit.service;

import com.baomidou.mybatisplus.service.IService;
import com.ryx.credit.commons.result.Tree;
import com.ryx.credit.pojo.admin.COrganization;

import java.util.List;

/**
 *
 * COrganization 表数据服务层接口
 *
 */
public interface IOrganizationService extends IService<COrganization> {

    List<Tree> selectTree();

    List<COrganization> selectTreeGrid();

}